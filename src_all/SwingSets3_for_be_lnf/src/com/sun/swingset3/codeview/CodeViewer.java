/*
 * Copyright 2007-2008 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.swingset3.codeview;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import com.sun.swingset3.utilities.RoundedBorder;
import com.sun.swingset3.utilities.RoundedPanel;
import com.sun.swingset3.utilities.Utilities;

/**
 * GUI component for viewing a set of one or more Java source code files,
 * providing the user with the ability to easily highlight specific code fragments.
 * A tabbedpane is used to control which source code file is shown within the set
 * if more than one file is loaded.
 * <p>
 * Example usage:
 * <pre><code>
 *    CodeViewer codeViewer = new CodeViewer();
 *    codeViewer.setSourceFiles(mySourceURLs);
 *    frame.add(codeViewer);
 * </code></pre>
 * 
 * <p>
 * When loading the source code, this viewer will automatically parse the files for
 * any source fragments which are marked with &quot;snippet&quot; start/end tags that
 * are embedded within Java comments.  The viewer will allow the user to highlight 
 * these code snippets for easier inspection of specific code.
 * <p>
 * The text following immediately after the start tag will be used
 * as the key for that snippet.  Multiple snippets may share the same 
 * key, defining a &quot;snippet set&quot;.  Snippet sets may even span across 
 * multiple source files.
 * The key for each snippet set is displayed in a combobox to allow the user to 
 * select which snippet set should be highlighted.  For example:<p>
 * <pre><code>
 *    //<snip>Create dog array
 *    ArrayList dogs = new ArrayList();
 *    //</snip>
 *
 *    [other code...]
 *
 *    //<snip>Create dog array
 *    dogs.add("Labrador");
 *    dogs.add("Golden Retriever");
 *    dogs.add("Australian Shepherd");
 *    //</snip>
 * </code></pre>
 * The above code would create a snippet set (containing 2 snippets) with the key 
 * &quot;Create dog array&quot;.
 * <p>
 * The viewer will allow the user to easily navigate across the currently highlighted
 * snippet set by pressing the navigation buttons or using accelerator keys.
 * 
 * @author aim
 */
public class CodeViewer extends JPanel {
    public static final String SOURCES_JAVA = ".+\\.java";

    public static final String SOURCES_TEXT = ".+\\.properties|.+\\.txt|.+\\.html|.+\\.xml";

    public static final String SOURCES_IMAGES = ".+\\.jpg|.+\\.gif|.+\\.png";
    
    private static final Color DEFAULT_HIGHLIGHT_COLOR = new Color(255,255,176); 
    private static BufferedImage SNIPPET_GLYPH;
    private static String NO_SNIPPET_SELECTED;

    static final Logger logger = Logger.getLogger(CodeViewer.class.getName());
    
    static {
        try {
            URL imageURL = CodeViewer.class.getResource("resources/images/snippetglyph.png");
            SNIPPET_GLYPH = ImageIO.read(imageURL);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    // Cache all processed code files in case they are reloaded later
    private final Map<URL,CodeFileInfo> codeCache = new HashMap<URL,CodeFileInfo>();
    
    private JComponent codeHighlightBar;
    private JComboBox snippetComboBox;
    private JComponent codePanel;
    private JLabel noCodeLabel;
    private JTabbedPane codeTabbedPane;
    
    private Color highlightColor;
    private Highlighter.HighlightPainter snippetPainter;
    
    private ResourceBundle bundle;

    // Current code file set
    private Map<URL, CodeFileInfo> currentCodeFilesInfo; 
    private List<URL> additionalSourceFiles; 
    
    // Map of all snippets in current code file set    
    private final SnippetMap snippetMap = new SnippetMap();
    
    private Action firstSnippetAction;
    private Action nextSnippetAction;
    private Action previousSnippetAction;
    private Action lastSnippetAction;
    
    /**
     * Creates a new instance of CodeViewer
     */
    public CodeViewer() {
        setHighlightColor(DEFAULT_HIGHLIGHT_COLOR);
        
        initActions();

        setLayout(new BorderLayout());
        codeHighlightBar = createCodeHighlightBar();
        codeHighlightBar.setVisible(false);
        add(codeHighlightBar, BorderLayout.NORTH);
        codePanel = createCodePanel();
        add(codePanel, BorderLayout.CENTER);
        
        applyDefaults();
    }
                
    protected JComponent createCodeHighlightBar() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel bar = new JPanel(gridbag);
        
        bar.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        NO_SNIPPET_SELECTED = getString("CodeViewer.snippets.selectOne", 
                                        "Select One");
       
        JLabel snippetSetsLabel = new JLabel(getString("CodeViewer.snippets.highlightCode",
                                                       "Highlight code to: "));
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0;
        gridbag.addLayoutComponent(snippetSetsLabel, c);
        bar.add(snippetSetsLabel);
        
        snippetComboBox = new JComboBox();
        snippetComboBox.setMaximumRowCount(20);
        snippetComboBox.setRenderer(new SnippetCellRenderer(snippetComboBox.getRenderer()));
        snippetComboBox.addActionListener(new SnippetActivator());
        snippetSetsLabel.setLabelFor(snippetComboBox);
        c.gridx++;
        c.weightx = 1;
        gridbag.addLayoutComponent(snippetComboBox, c);
        bar.add(snippetComboBox);
            
        SnippetNavigator snippetNavigator = new SnippetNavigator(snippetMap);
        snippetNavigator.setNavigateNextAction(nextSnippetAction);
        snippetNavigator.setNavigatePreviousAction(previousSnippetAction);
        c.gridx++;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 0;
        gridbag.addLayoutComponent(snippetNavigator, c);
        bar.add(snippetNavigator);
        
        return bar;              
    }
    
    protected JComponent createCodePanel() {
        
        JPanel panel = new RoundedPanel(new BorderLayout(), 10);
        panel.setBorder(new RoundedBorder(10));
        
        noCodeLabel = new JLabel(getString("CodeViewer.noCodeLoaded", "no code loaded"));
        noCodeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(noCodeLabel, BorderLayout.CENTER);
        return panel;
    }
    
    @Override
    public void updateUI() {
        super.updateUI();
        applyDefaults();        
    }
    
    protected void applyDefaults() {
        if (noCodeLabel != null) {
            noCodeLabel.setOpaque(false);
            noCodeLabel.setFont(UIManager.getFont("Label.font").deriveFont(24f));
            noCodeLabel.setForeground(
                    Utilities.deriveColorAlpha(UIManager.getColor("Label.foreground"), 110));
        }
        if (codePanel != null) {
            Color base = UIManager.getColor("Panel.background");
            codePanel.setBackground(Utilities.deriveColorHSB(base, 0, 0, -.06f));
        }
        if (snippetComboBox != null) {
            // Now that the look and feel has changed, we need to wrap the new delegate
            snippetComboBox.setRenderer(new SnippetCellRenderer(
                    new JComboBox().getRenderer()));
        }
        if (currentCodeFilesInfo != null) {
            Collection<CodeFileInfo> codeFiles = currentCodeFilesInfo.values();
            for(CodeFileInfo cfi : codeFiles) {
                makeSelectionTransparent(cfi.textPane, 180);
            }
        }
    }
    
    private void makeSelectionTransparent(JEditorPane textPane, int alpha) {
        Color c = textPane.getSelectionColor();
        textPane.setSelectionColor(
                new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
    }
    
    protected String getString(String key, String fallback) {
        String value = fallback;
        if (bundle == null) {
            String bundleName = getClass().getPackage().getName()+".resources."+getClass().getSimpleName();
            bundle = ResourceBundle.getBundle(bundleName);
        }
        try {
            value = bundle != null? bundle.getString(key) : fallback;
        
        } catch (MissingResourceException e) {
            logger.log(Level.WARNING, "missing String resource " + key + 
                    "; using fallback \"" +fallback + "\"");
        }
        return value;
    }
    
    protected void initActions() {
        firstSnippetAction = new FirstSnippetAction();
        nextSnippetAction = new NextSnippetAction();
        previousSnippetAction = new PreviousSnippetAction();
        lastSnippetAction = new LastSnippetAction();
        
        firstSnippetAction.setEnabled(false);
        nextSnippetAction.setEnabled(false);
        previousSnippetAction.setEnabled(false);
        lastSnippetAction.setEnabled(false);
        
        getActionMap().put("NextSnippet", nextSnippetAction);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl N"),"NextSnippet");
        
        getActionMap().put("PreviousSnippet", previousSnippetAction);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl P"),"PreviousSnippet");
    }
    
    public void setHighlightColor(Color highlight) {
        if (!highlight.equals(highlightColor)) {
            highlightColor = highlight;
            snippetPainter = new SnippetHighlighter.SnippetHighlightPainter(highlightColor);
            if (getCurrentSnippetKey() != null) {
                repaint();
            }
        }
    }
    
    public Color getHighlightColor() {
        return highlightColor;
    }
    
    public void setSourceFiles(URL sourceFiles[]) {
        if (currentCodeFilesInfo != null && additionalSourceFiles != null && sourceFiles != null &&
                currentCodeFilesInfo.size() + additionalSourceFiles.size() == sourceFiles.length) {
            List<URL> list = Arrays.asList(sourceFiles);

            if (list.containsAll(currentCodeFilesInfo.keySet()) && list.containsAll(additionalSourceFiles)) {
                // already loaded
                return;
            }
        }

        // clear everything
        clearAllSnippetHighlights();
        snippetMap.clear();
        
        if (sourceFiles == null) {
            // being reset to having no source files; need to clear everything
            currentCodeFilesInfo = null;
            additionalSourceFiles = null;
            configureCodePane(false);
            configureSnippetSetsComboBox();
 
        } else {
            // Use LinkedHashMap to save source order
            currentCodeFilesInfo = new LinkedHashMap<URL, CodeFileInfo>();
            additionalSourceFiles = new ArrayList<URL>();

            boolean needProcessing = false;
            for (URL sourceFile : sourceFiles) {
                if (sourceFile.getFile().matches(SOURCES_JAVA)) {
                    // look in cache first to avoid unnecessary processing
                    CodeFileInfo cachedFilesInfo = codeCache.get(sourceFile);

                    currentCodeFilesInfo.put(sourceFile, cachedFilesInfo);

                    if (cachedFilesInfo == null) {
                        needProcessing = true;
                    }
                } else {
                    additionalSourceFiles.add(sourceFile);
                }
            }
            configureCodePane(true);

            if (needProcessing) {
                // Do it on a separate thread
                new SourceProcessor(currentCodeFilesInfo).execute();
            } else {
                for (CodeFileInfo codeFileInfo : currentCodeFilesInfo.values()) {
                    registerSnippets(codeFileInfo);
                    createCodeFileTab(codeFileInfo);
                }

                createAdditionalTabs();
                configureSnippetSetsComboBox();
            }
        }
    }

    private void createAdditionalTabs() {
        JPanel pnImages = null;

        for (URL sourceFile : additionalSourceFiles) {
            String sourcePath = sourceFile.getPath();

            int i = sourcePath.indexOf('!');

            if (i >= 0) {
                sourcePath = sourcePath.substring(i + 1);
            }

            if (sourceFile.getFile().matches(SOURCES_IMAGES)) {
                if (pnImages == null) {
                    pnImages = new JPanel();

                    pnImages.setLayout(new BoxLayout(pnImages, BoxLayout.Y_AXIS));
                }

                JLabel label = new JLabel();

                label.setIcon(new ImageIcon(sourceFile));
                label.setBorder(new EmptyBorder(10, 0, 40, 0));

                pnImages.add(new JLabel(sourcePath));
                pnImages.add(label);
            }

            if (sourceFile.getFile().matches(SOURCES_TEXT)) {
                BufferedReader reader = null;

                try {
                    reader = new BufferedReader(new InputStreamReader(sourceFile.openStream()));

                    StringBuilder content = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        content.append(line).append('\n');

                    }

                    JTextArea textArea = new JTextArea(content.toString());
                    Font font = textArea.getFont();
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", font.getStyle(), font.getSize()));

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setBorder(null);

                    codeTabbedPane.addTab(Utilities.getURLFileName(sourceFile), scrollPane);
                } catch (IOException e) {
                    System.err.println(e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    }
                }
            }
        }

        if (pnImages != null) {
            JScrollPane scrollPane = new JScrollPane(pnImages);
            scrollPane.setBorder(null);

            codeTabbedPane.addTab(getString("CodeViewer.images", "Images"), scrollPane);
        }
    }

    private class SourceProcessor extends SwingWorker<Void, CodeFileInfo> {
        private final Map<URL, CodeFileInfo> codeFilesInfo;

        public SourceProcessor(Map<URL, CodeFileInfo> codeFilesInfo) {
            this.codeFilesInfo = codeFilesInfo;
        }

        public Void doInBackground() {
            for (Map.Entry<URL, CodeFileInfo> entry : codeFilesInfo.entrySet()) {
                // if not already fetched from cache, then process source code
                if (entry.getValue() == null) {
                    entry.setValue(initializeCodeFileInfo(entry.getKey()));
                }
                
                // We don't publish intermediate to avoid tab mixing 
                codeCache.put(entry.getKey(), entry.getValue());
            }
            
            return null;
        }
        
        private CodeFileInfo initializeCodeFileInfo(URL sourceFile) {
            CodeFileInfo codeFileInfo = new CodeFileInfo();
            codeFileInfo.url = sourceFile;
            codeFileInfo.styled = loadSourceCode(sourceFile);
            codeFileInfo.textPane = new JEditorPane();
            codeFileInfo.textPane.setHighlighter(new SnippetHighlighter());
            makeSelectionTransparent(codeFileInfo.textPane, 180);
            codeFileInfo.veneer = new CodeVeneer(codeFileInfo);
            Stacker layers = new Stacker(codeFileInfo.textPane);
            layers.add(codeFileInfo.veneer, JLayeredPane.POPUP_LAYER);
            codeFileInfo.textPane.setContentType("text/html");
            codeFileInfo.textPane.setEditable(false); // HTML won't display correctly without this!
            codeFileInfo.textPane.setText(codeFileInfo.styled);
            codeFileInfo.textPane.setCaretPosition(0);

            // MUST parse AFTER textPane Document has been created to ensure
            // snippet offsets are relative to the editor pane's Document model
            codeFileInfo.snippets = SnippetParser.parse(codeFileInfo.textPane.getDocument());
            return codeFileInfo;
        }

        protected void done() {
            try {
                // It's possible that by now another set of source files has been loaded.
                // so check first before adding the source tab;'
                if (currentCodeFilesInfo == codeFilesInfo) {
                    for (CodeFileInfo codeFileInfo : currentCodeFilesInfo.values()) {
                        registerSnippets(codeFileInfo);
                        createCodeFileTab(codeFileInfo);
                    }
                } else {
                    logger.log(Level.FINEST, "source files changed before sources was processed.");
                }

                createAdditionalTabs();
                configureSnippetSetsComboBox();
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    } // SourceProcessor

    // Called from Source Processing Thread in SwingWorker
 
    private void configureCodePane(boolean hasCodeFiles) {
        if (hasCodeFiles) {
            if (codeTabbedPane == null) {
                codeTabbedPane = new JTabbedPane();
                codePanel.remove(noCodeLabel);
                codePanel.add(codeTabbedPane);
                revalidate();
            } else {
                codeTabbedPane.removeAll();
            }
        } else {
            // No code files
            if (codeTabbedPane != null) {
                codePanel.remove(codeTabbedPane);
                codeTabbedPane = null;
                codePanel.add(noCodeLabel);
                revalidate();               
            }
        }
    }
    
    private void createCodeFileTab(CodeFileInfo codeFileInfo) {
        JLayeredPane layeredPane = JLayeredPane.getLayeredPaneAbove(codeFileInfo.textPane);
        JScrollPane scrollPane = new JScrollPane(layeredPane);
        scrollPane.setBorder(null);
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());
        
        tabPanel.add(scrollPane, BorderLayout.CENTER);
        
        codeTabbedPane.addTab(Utilities.getURLFileName(codeFileInfo.url), tabPanel);
    }

    private void registerSnippets(CodeFileInfo codeFileInfo) {
        for(String snippetKey: codeFileInfo.snippets.keySet()) {
            List<Snippet> snippetCodeList = codeFileInfo.snippets.get(snippetKey);
            for(Snippet snippet: snippetCodeList) {
                snippetMap.add(snippetKey, codeFileInfo.url, snippet);
            }
        }
    }
    
    private void configureSnippetSetsComboBox() {        
        TreeSet sortedSnippets = new TreeSet(snippetMap.keySet());
        String snippetSetKeys[] = (String[])sortedSnippets.toArray(new String[0]);
        
        DefaultComboBoxModel snippetModel = new DefaultComboBoxModel();
        for(String snippetKey : snippetSetKeys) {
            snippetModel.addElement(snippetKey);
        }
        snippetModel.insertElementAt(NO_SNIPPET_SELECTED, 0);
        snippetModel.setSelectedItem(NO_SNIPPET_SELECTED);
        snippetComboBox.setModel(snippetModel);
        codeHighlightBar.setVisible(snippetModel.getSize() > 1);
        
    }

    /**
     * Reads the java source file at the specified URL and returns an
     * HTML version stylized for display
     */
    protected String loadSourceCode(URL sourceUrl) {
        InputStreamReader isr = null;
        CodeStyler cv = new CodeStyler();        
        String styledCode = "<html><body bgcolor=\"#ffffff\"><pre>";
        
        try {
            isr = new InputStreamReader(sourceUrl.openStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            
            // Read one line at a time, htmlizing using super-spiffy
            // html java code formating utility from www.CoolServlets.com
            String line = reader.readLine();
            while(line != null) {
                styledCode += cv.syntaxHighlight(line) + " \n ";
                line = reader.readLine();
            }
            styledCode += "</pre></body></html>";
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Could not load file from: " + sourceUrl;
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
        return styledCode;
    }
    
    public void clearAllSnippetHighlights() {
        if (currentCodeFilesInfo != null) {
            snippetMap.setCurrentSet(null);
            for(CodeFileInfo code : currentCodeFilesInfo.values()) {
                if (code != null && code.textPane != null) {
                    Highlighter highlighter = code.textPane.getHighlighter();
                    highlighter.removeAllHighlights();
                    code.textPane.repaint();
                    code.veneer.repaint();
                }
            }
        }
    }
    
    public void highlightSnippetSet(String snippetKey) {

        clearAllSnippetHighlights();
        snippetMap.setCurrentSet(snippetKey);
        
        URL files[] = snippetMap.getFilesForSet(snippetKey);
        CodeFileInfo firstCodeFileInfo = null;
        Snippet firstSnippet = null;
        for(URL file : files) {
            CodeFileInfo codeFileInfo = codeCache.get(file);
            Highlighter highlighter = codeFileInfo.textPane.getHighlighter();
            // now add highlight for each snippet in this file associated 
            // with the key
            Snippet snippets[] = snippetMap.getSnippetsForFile(snippetKey, file);
            if (firstCodeFileInfo == null) {
                firstCodeFileInfo = codeFileInfo;
                firstSnippet = snippets[0];
            }
            for (Snippet snippet : snippets) {
                try {
                    highlighter.addHighlight(snippet.startLine,
                            snippet.endLine, snippetPainter );
                    codeFileInfo.veneer.repaint();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        scrollToSnippet(firstCodeFileInfo, firstSnippet);
        snippetComboBox.setSelectedItem(snippetKey);
    }
    
    protected void scrollToSnippet(CodeFileInfo codeFileInfo, Snippet snippet) {
        if (!codeFileInfo.textPane.isShowing()) {
            // Need to switch tabs to source file with first snippet
            // remind: too brittle - need to find component some other way
            codeTabbedPane.setSelectedComponent(
                    JLayeredPane.getLayeredPaneAbove(codeFileInfo.textPane).getParent().getParent().getParent());
        }
        try {
            Rectangle r1 = codeFileInfo.textPane.modelToView(snippet.startLine);
            Rectangle r2 = codeFileInfo.textPane.modelToView(snippet.endLine);
            codeFileInfo.textPane.scrollRectToVisible(
                    SwingUtilities.computeUnion(r1.x, r1.y,
                    r1.width, r1.height, r2));
        } catch (BadLocationException e) {
            System.err.println(e);
        }
        nextSnippetAction.setEnabled(snippetMap.nextSnippetExists());
        previousSnippetAction.setEnabled(snippetMap.previousSnippetExists());
    }
    
    protected String getCurrentSnippetKey() {
        String key = snippetMap.getCurrentSet();
        return key != null? key : NO_SNIPPET_SELECTED;
    }
    
    protected Snippet getCurrentSnippet() {
        return snippetMap.getCurrentSnippet();
    }
    
    protected void moveToFirstSnippet() {
       Snippet firstSnippet = snippetMap.firstSnippet();    
       if (firstSnippet != null) {
            CodeFileInfo codeFileInfo = codeCache.get(snippetMap.getFileForSnippet(firstSnippet));
            scrollToSnippet(codeFileInfo, firstSnippet);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }        
    }
    
    protected void moveToNextSnippet() {
        Snippet nextSnippet = snippetMap.nextSnippet();    
        if (nextSnippet != null) {
            CodeFileInfo codeFileInfo = codeCache.get(snippetMap.getFileForSnippet(nextSnippet));
            scrollToSnippet(codeFileInfo, nextSnippet);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
    protected void moveToPreviousSnippet() {
        Snippet previousSnippet = snippetMap.previousSnippet();
        if (previousSnippet != null) {
            CodeFileInfo codeFileInfo = codeCache.get(snippetMap.getFileForSnippet(previousSnippet));
            scrollToSnippet(codeFileInfo, previousSnippet);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }         
  
    }
 
    protected void moveToLastSnippet() {
       Snippet lastSnippet = snippetMap.lastSnippet();    
       if (lastSnippet != null) {
            CodeFileInfo codeFileInfo = codeCache.get(snippetMap.getFileForSnippet(lastSnippet));
            scrollToSnippet(codeFileInfo, lastSnippet);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }        
    }
    
    private class SnippetActivator implements ActionListener {        
        public void actionPerformed(ActionEvent e) {
            String snippetKey = (String)snippetComboBox.getSelectedItem();
            if (!snippetKey.equals(NO_SNIPPET_SELECTED)) {
                logger.log(Level.FINEST, "highlighting new snippet:"+snippetKey+".");
                highlightSnippetSet(snippetKey);
            } else {
                clearAllSnippetHighlights();
            }
        }
    }
    
    private abstract class SnippetAction extends AbstractAction {
        public SnippetAction(String name, String shortDescription) {
            super(name);
            putValue(AbstractAction.SHORT_DESCRIPTION, shortDescription);
        }      
    }
    private class FirstSnippetAction extends SnippetAction {
        public FirstSnippetAction() {
            super("FirstSnippet",
                    getString("CodeViewer.snippets.navigateFirst",
                              "move to first code snippet within highlighted set"));
        } 
        public void actionPerformed(ActionEvent e) {
            moveToFirstSnippet();
        }        
    }
    
    private class NextSnippetAction extends SnippetAction {
        public NextSnippetAction() {
            super("NextSnippet", 
                    getString("CodeViewer.snippets.navigateNext",
                              "move to next code snippet within highlighted set"));
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            moveToNextSnippet();
        }
    }
    
    private class PreviousSnippetAction extends SnippetAction {
        public PreviousSnippetAction() {
            super("PreviousSnippet", 
                    getString("CodeViewer.snippets.navigatePrevious",
                              "move to previous code fragment within highlighted set"));
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            moveToPreviousSnippet();
        }
    }
  
    private class LastSnippetAction extends SnippetAction {
        public LastSnippetAction() {
            super("LastSnippet", 
                    getString("CodeViewer.snippets.navigateLast",
                              "move to last code snippet within highlighted set"));
        } 
        public void actionPerformed(ActionEvent e) {
            moveToLastSnippet();
        }        
    }
    
    private class SnippetCellRenderer implements ListCellRenderer {
        private JLabel delegate;
        
        public SnippetCellRenderer(ListCellRenderer delegate) {
            this.delegate = (JLabel)delegate;
        }
        public Component getListCellRendererComponent(JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            
            JLabel renderer = (JLabel)((ListCellRenderer)delegate).getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            
            int count = snippetMap.getSnippetCountForSet((String)value);
            
            Color foreground = renderer.getForeground();
            Color countForeground = Utilities.deriveColorHSB(foreground, 
                    0, 0, isSelected? .5f : .4f);
            
            String text = "<html><font color=\"" +
                    Utilities.getHTMLColorString(foreground) + "\">" + value + 
                    "</font>" +
                    "<font color=\"" +
                    Utilities.getHTMLColorString(countForeground) + "\">" +
                    (count > 0? " (" + count + (count > 1? " snippets)" : " snippet)") : "") +
                    "</font></html>";

            renderer.setText(text);
            return renderer;
        }
    }
    

 
    private static class CodeFileInfo {
        public URL url;
        public String styled;
        public HashMap<String, List<Snippet>> snippets = new HashMap<String, List<Snippet>>();
        public JEditorPane textPane;
        public JPanel veneer;
    }
    
    private static class Stacker extends JLayeredPane {
        private Component master; // dictates sizing, scrolling
        
        public Stacker(Component master) {
            this.master = master;
            setLayout(null);
            add(master, JLayeredPane.DEFAULT_LAYER);
        }
        
        public Dimension getPreferredSize() {
            return master.getPreferredSize();         
        }
        
        public void doLayout() {
            // ensure all layers are sized the same
            Dimension size = getSize();
            Component layers[] = getComponents();
            for(Component layer : layers) {
                layer.setBounds(0, 0, size.width, size.height);
            }                      
        } 
    }
    
    private class CodeVeneer extends JPanel {        
        private CodeFileInfo codeFileInfo;
        
        public CodeVeneer(CodeFileInfo codeFileInfo) {
            this.codeFileInfo = codeFileInfo;
            setOpaque(false);
            setLayout(null);            
        }
        
        @Override
        protected void paintComponent(Graphics g) {

            String snippetKey = getCurrentSnippetKey();
            if (snippetKey != NO_SNIPPET_SELECTED) { 
                // Count total number of snippets for key
                int snippetTotal = 0;
                int snippetIndex = 0;
                List<Snippet> snippetList = null;
                URL files[] = snippetMap.getFilesForSet(snippetKey);               
                for(URL file : files) {
                    CodeFileInfo codeFileInfo = codeCache.get(file);
                    if (this.codeFileInfo == codeFileInfo) {
                        snippetList = codeFileInfo.snippets.get(snippetKey);
                        snippetIndex = snippetTotal + 1;
                    }
                    snippetTotal += (codeFileInfo.snippets.get(snippetKey).size());
                }
                
                if (snippetList != null) {
                    Snippet currentSnippet = snippetMap.getCurrentSnippet();
                    CodeFileInfo currentSnippetCodeFileInfo = codeCache.get(
                            snippetMap.getFileForSnippet(currentSnippet));
                    
                    Font font = g.getFont();
                    g.setFont(font.deriveFont(10f));
                    FontMetrics metrics = g.getFontMetrics();
                    
                    g.setColor(getHighlightColor());
                    
                    Graphics2D g2Alpha = null; // cache composite                  
                    for(Snippet snippet : snippetList) {
                        Graphics2D g2 = (Graphics2D)g;

                        try {
                            if (currentSnippetCodeFileInfo != codeFileInfo ||
                                    currentSnippet != snippet) {
                                // if not painting the "current" snippet, then fade the glyph
                                if (g2Alpha == null) {
                                    // first time, so create composite
                                    g2Alpha = (Graphics2D)g2.create();                                
                                    g2Alpha.setComposite(AlphaComposite.getInstance(
                                            AlphaComposite.SRC_OVER, 0.6f));
                                }
                                g2 = g2Alpha;
                            }
                            Rectangle snipRect = codeFileInfo.textPane.modelToView(snippet.startLine);
                            
                            //String glyphLabel = snippetIndex++ + "/" + snippetTotal;
                            String glyphLabel = "" + snippetIndex++;
                            Rectangle labelRect = metrics.getStringBounds(glyphLabel, g2).getBounds();
      
                            g2.drawImage(SNIPPET_GLYPH, 0, snipRect.y, this);
                            g2.setColor(Color.black);
                            g2.drawString(glyphLabel,
                                    (SNIPPET_GLYPH.getWidth(this) - labelRect.width)/2,
                                    snipRect.y +
                                    (SNIPPET_GLYPH.getHeight(this) - labelRect.height)/2 +
                                    metrics.getAscent());
                            
                        } catch (BadLocationException e) {
                            System.err.println(e);
                        }
                    }
                }
            }
        }
    }
}

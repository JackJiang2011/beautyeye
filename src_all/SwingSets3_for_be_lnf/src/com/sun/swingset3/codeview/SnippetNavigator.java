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

import com.sun.swingset3.utilities.ArrowIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

/**
 *
 * @author Amy Fowler
 */
// This is more complicated thn it needs to be because it was an attempt to
// create a seamless control where the label was integrated into the buttons;
// this integration is temporarily turned off due to visual problems with focus
// and enabled state of the buttons
public class SnippetNavigator extends JPanel {
    private String NO_SNIPPET;
    private SnippetMap snippetMap;
    private JLabel statusLabel;
    private JButton prevButton;
    private JButton nextButton;
    private int arrowSize = 8;
    private int overlap = 0;
    private int labelPad = 6;
    private boolean needLabelBorder = true;

    public SnippetNavigator(SnippetMap snippetMap) {
        this.snippetMap = snippetMap;
        snippetMap.addPropertyChangeListener(new SnippetHighlightListener());

        setLayout(null);

        NO_SNIPPET = getString("CodeViewer.snippets.noCodeHighlighted",
                "No Code highlight selected");

        statusLabel = new JLabel(NO_SNIPPET);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(statusLabel);

        prevButton = (JButton) add(new NavButton());
        prevButton.setDefaultCapable(false);
        prevButton.setVisible(false);

        nextButton = (JButton) add(new NavButton());
        nextButton.setDefaultCapable(false);
        nextButton.setVisible(false);

        applyDefaults();

    }
    
    protected String getString(String key, String fallback) {
        String value = fallback;
        String bundleName = getClass().getPackage().getName()+".resources.CodeViewer";
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);        
        try {
            value = bundle != null? bundle.getString(key) : fallback;
        
        } catch (MissingResourceException e) {
            CodeViewer.logger.log(Level.WARNING, "missing String resource " + key + 
                    "; using fallback \"" +fallback + "\"");
        }
        return value;
    }
    
    @Override
    public void doLayout() {
        Dimension size = getSize();
        Insets insets = getInsets();
        Dimension labelSize = statusLabel.getPreferredSize();

        if (prevButton.isVisible()) {
            if (needLabelBorder) {
                matchLabelBorderToButtons();
            }
            Dimension buttonSize = prevButton.getPreferredSize();

            prevButton.setBounds(insets.left, insets.top,
                    buttonSize.width, size.height - insets.top - insets.bottom);

            statusLabel.setBounds(insets.left + buttonSize.width - overlap,
                    insets.top,
                    labelSize.width + (2 * overlap) + labelPad,
                    size.height - insets.top - insets.bottom);

            nextButton.setBounds(size.width - buttonSize.width,
                    insets.top,
                    buttonSize.width, size.height - insets.top - insets.bottom);
        } else {
            statusLabel.setBounds(insets.left, insets.top,
                    size.width - insets.left - insets.right,
                    size.height - insets.top - insets.bottom);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension prefSize;
        Insets insets = getInsets();
        Dimension buttonSize = prevButton.getPreferredSize();
        Dimension labelSize = statusLabel.getPreferredSize();
        if (prevButton.isVisible()) {
            prefSize = new Dimension(buttonSize.width * 2 + labelSize.width +
                    labelPad +
                    insets.left + insets.right,
                    buttonSize.height + insets.top + insets.bottom);
        } else {
            prefSize = new Dimension(labelSize.width + insets.left + insets.right,
                    buttonSize.height + insets.top + insets.bottom);
        }
        return prefSize;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        applyDefaults();
    }

    protected void applyDefaults() {
        if (prevButton != null) {
            Color arrowColor = UIManager.getColor("Label.foreground");
            Color inactiveColor = UIManager.getColor("Label.disabledText");
            Dimension buttonSize = new Dimension(arrowSize + 12 + overlap,
                    arrowSize + 12);

            prevButton.setIcon(new ArrowIcon(ArrowIcon.WEST, arrowSize, arrowColor));
            prevButton.setDisabledIcon(new ArrowIcon(ArrowIcon.WEST, arrowSize, inactiveColor));
            prevButton.setPreferredSize(buttonSize);
            nextButton.setIcon(new ArrowIcon(ArrowIcon.EAST, arrowSize, arrowColor));
            nextButton.setDisabledIcon(new ArrowIcon(ArrowIcon.EAST, arrowSize, inactiveColor));
            nextButton.setPreferredSize(buttonSize);

            statusLabel.setOpaque(true);
            statusLabel.setFont(UIManager.getFont("Label.font").deriveFont(12f));
            
            //needLabelBorder = true;
            needLabelBorder = false;
        }
    }
    // this doesn't get called because label integration is disabled
    private void matchLabelBorderToButtons() {
        Insets insets = new Insets(4, 6, 4, 6);
        Dimension buttonSize = prevButton.getPreferredSize();
        prevButton.setSize(buttonSize);

        BufferedImage image = GraphicsUtilities.
                createCompatibleTranslucentImage(
                     buttonSize.width, buttonSize.height);
        prevButton.paint(image.getGraphics());
        BufferedImage labelTopImage = GraphicsUtilities.
                createCompatibleTranslucentImage(
                     buttonSize.width - insets.left - insets.right,
                     insets.top);
        labelTopImage.getGraphics().drawImage(image, 0, 0,
                buttonSize.width - insets.left - insets.right,
                insets.top,
                insets.left, 0,
                buttonSize.width - insets.right, insets.top,
                null,
                null);

        BufferedImage labelBottomImage = GraphicsUtilities.createCompatibleTranslucentImage(buttonSize.width - insets.left - insets.right,
                insets.bottom);
        labelBottomImage.getGraphics().drawImage(image, 0, 0,
                buttonSize.width - insets.left - insets.right, insets.top,
                insets.left, buttonSize.height - insets.bottom,
                buttonSize.width - insets.right, buttonSize.height,
                null,
                null);

        statusLabel.setBorder(new CenterLabelBorder(labelTopImage, labelBottomImage));
        needLabelBorder = false;
    }

    public void setNavigatePreviousAction(Action action) {
        setButtonAction(prevButton, action);
    }

    public void setNavigateNextAction(Action action) {
        setButtonAction(nextButton, action);
    }

    private void setButtonAction(JButton button, Action action) {
        Icon icon = button.getIcon();
        button.setAction(action);
        button.setHideActionText(true);
        button.setIcon(icon); // icon gets obliterated when action set!
    }

    private class SnippetHighlightListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName.equals("currentSet")) {
                String key = (String) e.getNewValue();
                setComponentState(key);

            } else if (propertyName.equals("currentSnippet")) {
                setComponentState(snippetMap.getCurrentSet());
            }
        }

        private void setComponentState(String currentKey) {
            if (currentKey == null) {
                statusLabel.setText(NO_SNIPPET);

            } else {

                String place = "<html><b>" +
                        snippetMap.getIndexForSnippet(snippetMap.getCurrentSnippet()) +
                        "</b>" +
                        " of " + snippetMap.getSnippetCountForSet(currentKey) + "</html>";
                statusLabel.setText(place);

            }
            boolean moreThanOne = snippetMap.getSnippetCountForSet(currentKey) > 1;

            prevButton.setVisible(moreThanOne);
            nextButton.setVisible(moreThanOne);
        }
    }
    
    private class NavButton extends JButton {
        public NavButton() {
            super();
        }        
        protected void paintComponent(Graphics g) {
            // need to make sure overlapping label paints after buttons otherwise
            // the buttons paint over the label when they gain focus (which is really
            // a Swing bug, as the clip should be properly set based on zorder
            // within the container)
            super.paintComponent(g);
            statusLabel.repaint();
        }        
    }

    private class CenterLabelBorder implements Border {
        private Image topImage;
        private Image bottomImage;

        public CenterLabelBorder(Image topImage, Image bottomImage) {
            this.topImage = topImage;
            this.bottomImage = bottomImage;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(topImage.getHeight(null),
                    0, bottomImage.getHeight(null), 0);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            if (prevButton.isVisible()) {
                int xx = x;
                int imageWidth = topImage.getWidth(c);
                while (xx < x + w) {
                    g.drawImage(topImage, xx, y, c);
                    xx += imageWidth;
                }
                xx = x;
                imageWidth = bottomImage.getWidth(c);
                while (xx < x + w) {
                    g.drawImage(bottomImage, xx, y + h - bottomImage.getHeight(c), c);
                    xx += imageWidth;
                }
            }
        }
    }
}
    
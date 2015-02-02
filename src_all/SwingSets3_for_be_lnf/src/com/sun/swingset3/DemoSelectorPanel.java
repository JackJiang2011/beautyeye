/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
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

package com.sun.swingset3;

import com.sun.swingset3.utilities.GradientPanel;
import com.sun.swingset3.utilities.CollapsiblePanel;
import com.sun.swingset3.utilities.ArrowIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import com.sun.swingset3.utilities.Utilities;

/**
 *
 * @author Administrator
 */
public class DemoSelectorPanel extends JPanel {    
    private static final Border chiselBorder = new ChiselBorder();
    private static final Border panelBorder = new CompoundBorder(
            chiselBorder, new EmptyBorder(6,8,6,0));
    private static final Border categoryBorder = new CompoundBorder(
            chiselBorder, new EmptyBorder(0,0,10,0));    
    private static final Border buttonBorder = new CompoundBorder(
            new DemoButtonBorder(), new EmptyBorder(0, 18, 0, 0)); 
    
    private GradientPanel titlePanel;
    private JLabel demoListLabel;
    private JPanel viewPanel;
    private JScrollPane scrollPane;
    // need to track components that have defaults customizations
    private final List<CollapsiblePanel> collapsePanels = new ArrayList<CollapsiblePanel>();
    private Icon expandedIcon;
    private Icon collapsedIcon;
    private Color visitedForeground;
    private Color failedForeground;
       
    private ButtonGroup group;
    private final ActionListener demoActionListener = new DemoActionListener();
    private int buttonHeight = 0;
    
    private Demo selectedDemo;
    
    public DemoSelectorPanel(String demoSetTitle, List<Demo> demoSet) {
        super(new BorderLayout());
        
        visitedForeground = new Color(100, 100, 150);
        failedForeground = new Color(245, 20, 80);
        
        // only one demo may be selected at a time
        group = new ButtonGroup();
        
        // create demo set title area at top
        add(createTitleArea(demoSetTitle), BorderLayout.NORTH);
        
        // create scrollable demo panel at bottom
        JComponent selector = createDemoSelector(demoSet);
        scrollPane = new JScrollPane(selector);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        
        applyDefaults();
    }
    
    protected JComponent createTitleArea(String demoSetTitle) {
        JPanel titleAreaPanel = new JPanel(new BorderLayout());
        titlePanel = new GradientPanel(
                UIManager.getColor(SwingSet3.TITLE_GRADIENT_COLOR1_KEY),
                UIManager.getColor(SwingSet3.TITLE_GRADIENT_COLOR2_KEY));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(panelBorder);
        demoListLabel = new JLabel(demoSetTitle);
        demoListLabel.setOpaque(false);
        demoListLabel.setHorizontalAlignment(JLabel.LEADING);
        titlePanel.add(demoListLabel, BorderLayout.CENTER);
        titleAreaPanel.add(titlePanel, BorderLayout.NORTH);        
        
        // Add panel with view combobox
        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.X_AXIS));
        viewPanel.setBorder(new CompoundBorder(chiselBorder,
                new EmptyBorder(12,8,12,8)));
        JLabel viewLabel = new JLabel("View:");
        viewPanel.add(viewLabel);
        viewPanel.add(Box.createHorizontalStrut(6));
        JComboBox viewComboBox = new JComboBox();
        viewComboBox.addItem("by category");
        viewPanel.add(viewComboBox);
        titleAreaPanel.add(viewPanel, BorderLayout.CENTER);
        
        return titleAreaPanel;
    }
        
    protected JComponent createDemoSelector(List<Demo> demoSet) {
        JPanel selectorPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        selectorPanel.setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints(); 
        c.gridx = c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
               
        HashMap<String,JPanel> categoryMap = new HashMap<String,JPanel>();
        GridBagLayout categoryGridbag = null;
        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = cc.gridy = 0;
        cc.weightx = 1;
        cc.fill = GridBagConstraints.HORIZONTAL;
        CollapsiblePanel collapsePanel;
        for(Demo demo: demoSet) {
            String category = demo.getCategory();
            JPanel categoryPanel = categoryMap.get(category);
            if (categoryPanel == null) {
                // Create category collapsible panel
                categoryPanel = new JPanel();
                categoryGridbag = new GridBagLayout();
                categoryPanel.setLayout(categoryGridbag);                
                collapsePanel = new CollapsiblePanel(categoryPanel, category, 
                        "click to expand or contract category");
                collapsePanels.add(collapsePanel);
                collapsePanel.setBorder(categoryBorder);
                categoryMap.put(category, categoryPanel);
                gridbag.addLayoutComponent(collapsePanel, c);
                selectorPanel.add(collapsePanel);
                c.gridy++;
            }
            DemoButton demoButton = new DemoButton(demo);
            categoryGridbag.addLayoutComponent(demoButton, cc);
            cc.gridy++;
            group.add(demoButton);
            categoryPanel.add(demoButton);
            if (buttonHeight == 0) {
                buttonHeight = demoButton.getPreferredSize().height;
            }
        }
        // add empty component to take up any extra room on bottom
        JPanel trailer = new JPanel();
        c.weighty = 1.0;
        gridbag.addLayoutComponent(trailer, c);
        selectorPanel.add(trailer);
        
        applyDefaults();
        
        return selectorPanel;
    }
   
    public void updateUI() {
        super.updateUI();
        applyDefaults();
    }
        
    protected void applyDefaults() {
        
        expandedIcon = new ArrowIcon(ArrowIcon.SOUTH,
                UIManager.getColor(SwingSet3.TITLE_FOREGROUND_KEY));
        collapsedIcon = new ArrowIcon(ArrowIcon.EAST,
                UIManager.getColor(SwingSet3.TITLE_FOREGROUND_KEY));
        
        setBorder(new MatteBorder(0,0,0,1, 
                UIManager.getColor(SwingSet3.CONTROL_MID_SHADOW_KEY)));
        
        if (titlePanel != null) {
            titlePanel.setGradientColor1(
                UIManager.getColor(SwingSet3.TITLE_GRADIENT_COLOR1_KEY));
            titlePanel.setGradientColor2(
                UIManager.getColor(SwingSet3.TITLE_GRADIENT_COLOR2_KEY));
        }

        if (demoListLabel != null) {
           demoListLabel.setForeground(UIManager.getColor(SwingSet3.TITLE_FOREGROUND_KEY));
           demoListLabel.setFont(UIManager.getFont(SwingSet3.TITLE_FONT_KEY));
        }
        if (viewPanel != null) {
            viewPanel.setBackground(UIManager.getColor(SwingSet3.SUB_PANEL_BACKGROUND_KEY));
        }        
        if (collapsePanels != null) {
            for (CollapsiblePanel collapsePanel : collapsePanels) {
                collapsePanel.setFont(
                        UIManager.getFont("CheckBox.font").deriveFont(Font.BOLD));
                collapsePanel.setForeground(UIManager.getColor(SwingSet3.TITLE_FOREGROUND_KEY));
                collapsePanel.setExpandedIcon(expandedIcon);
                collapsePanel.setCollapsedIcon(collapsedIcon);
            }
        }
        revalidate();
    }
    
    public Demo getSelectedDemo() {
        return selectedDemo;
    }
    
    protected void setSelectedDemo(Demo demo) {
        Demo oldSelectedDemo = selectedDemo;
        selectedDemo = demo;
        firePropertyChange("selectedDemo", oldSelectedDemo, demo);
    }
    
    private class DemoButton extends JToggleButton {
        private Demo demo;
        
        public DemoButton(Demo demo) {
            super();
            this.demo = demo;
            String demoName = demo.getName();
            if (demoName.endsWith("Demo")) {
                setText(demoName.substring(0, demoName.indexOf("Demo")));
            } else {
                setText(demoName);
            }
            setIcon(demo.getIcon());
            setIconTextGap(10);
            setHorizontalTextPosition(JToggleButton.TRAILING);
            setHorizontalAlignment(JToggleButton.LEADING);
            setOpaque(false);
            setBorder(buttonBorder);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setToolTipText(demo.getShortDescription());
            addActionListener(demoActionListener);
        }
        
        @Override
        public void updateUI() {
            super.updateUI();
            // some look and feels replace our border, so take it back
            setBorder(buttonBorder);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            if (isSelected()) {
                setBackground(UIManager.getColor("Tree.selectionBackground"));
                g.setColor(UIManager.getColor("Tree.selectionBackground"));
                Dimension size = getSize();
                g.fillRect(0, 0, size.width, size.height); 
                setForeground(UIManager.getColor("Tree.selectionForeground"));
            } else {
                setBackground(UIManager.getColor("ToggleButton.background"));
                Color foreground = UIManager.getColor("ToggleButton.foreground");
                switch(demo.getState()) {
                    case STOPPED: {
                        foreground = visitedForeground;
                        break;
                    }
                    case FAILED: {
                        foreground = failedForeground;
                    }
                }
                setForeground(foreground);
            }
            super.paintComponent(g);
        }
        
        public Demo getDemo() {
            return demo;
        }
    }
    
    private class DemoActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            DemoButton demoButton = (DemoButton)event.getSource();
            setSelectedDemo(demoButton.getDemo());
        }
    }
    
    private static class DemoButtonBorder implements Border {
        private Insets insets = new Insets(2, 1, 1, 1);
        
        public DemoButtonBorder() {            
        }
        
        public Insets getBorderInsets(Component c) {
            return insets;
        }
        public boolean isBorderOpaque() {
            return true;
        }
         public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            AbstractButton b = (AbstractButton)c;
            if (b.isSelected()) {
                Color color = c.getBackground();
                g.setColor(Utilities.deriveColorHSB(color, 0, 0, -.20f));
                g.drawLine(x, y, x + width, y);
                g.setColor(Utilities.deriveColorHSB(color, 0, 0, -.10f));
                g.drawLine(x, y + 1, x + width, y + 1);
                g.drawLine(x, y + 2, x, y + height - 2);
                g.setColor(Utilities.deriveColorHSB(color, 0, 0, .24f));
                g.drawLine(x, y + height - 1, x + width, y + height-1);
            }
        }
    }
    
    private static class ChiselBorder implements Border {
        private Insets insets = new Insets(1, 0, 1, 0);
        
        public ChiselBorder() {            
        }
        
        public Insets getBorderInsets(Component c) {
            return insets;
        }
        public boolean isBorderOpaque() {
            return true;
        }
         public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Color color = c.getBackground();
            // render highlight at top
            g.setColor(Utilities.deriveColorHSB(color, 0, 0, .2f));
            g.drawLine(x, y, x + width, y);
            // render shadow on bottom
            g.setColor(Utilities.deriveColorHSB(color, 0, 0, -.2f));
            g.drawLine(x, y + height - 1, x + width, y + height - 1);
        }
    }
}

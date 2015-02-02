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

package com.sun.swingset3.demos.button;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.JHyperlink;

/**
 *
 * @author aim
 */
@DemoProperties(
        value = "JButton Demo",
        category = "Controls",
        description = "Demonstrates the many uses of JButton, Swing's push button component.",
        sourceFiles = {
                "com/sun/swingset3/demos/button/ButtonDemo.java",
                "com/sun/swingset3/demos/JHyperlink.java",
                "com/sun/swingset3/demos/button/resources/ButtonDemo.html",
                "com/sun/swingset3/demos/button/resources/images/blogs.png",
                "com/sun/swingset3/demos/button/resources/images/ButtonDemo.gif",
                "com/sun/swingset3/demos/button/resources/images/document-print.png",
                "com/sun/swingset3/demos/button/resources/images/earth_day.gif",
                "com/sun/swingset3/demos/button/resources/images/earth_night.gif",
                "com/sun/swingset3/demos/button/resources/images/edit-find.png",
                "com/sun/swingset3/demos/button/resources/images/redbutton.png",
                "com/sun/swingset3/demos/button/resources/images/redbutton_dark.png",
                "com/sun/swingset3/demos/button/resources/images/redbutton_glow.png"
                }
)
public class ButtonDemo extends JPanel {

    public ButtonDemo() {
        setToolTipText("Demonstrates JButton, Swing's push button component.");
        initComponents();
        setOpaque(false);
    }

    protected void initComponents() {
        setLayout(new GridLayout(0, 1));

        add(createSimpleButtonPanel());
        add(createCreativeButtonPanel());
    }

    protected JPanel createSimpleButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 8));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "Simple Buttons"));

        //<snip>Create simple button
        final JButton simpleButton = new JButton("Do it");
        simpleButton.setToolTipText("simple button");
        //</snip>
        //<snip>Add action listener using anonymous inner class
        // This style is useful when the action code is tied to a
        // single button instance and it's useful for simplicity
        // sake to keep the action code located near the button.
        // More global application actions should be implemented
        // using Action classes instead.
        simpleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                simpleButton.setText("Do it again");
                // Need to force toplevel to relayout to accommodate new button size
                SwingUtilities.getWindowAncestor(simpleButton).validate();
            }
        });
        //</snip>
        simpleButton.putClientProperty("snippetKey", "Create simple button");
        panel.add(simpleButton);

        //<snip>Create image button
        // Image is from the Java Look and Feel Graphics Repository:
        // http://java.sun.com/developer/techDocs/hi/repository 
        JButton button = new JButton(new ImageIcon(getClass().
                getResource("resources/images/document-print.png")));
        button.setToolTipText("image button");
        //</snip>
        button.putClientProperty("snippetKey", "Create image button");
        panel.add(button);

        //<snip>Create button with text and image
        // Image is from the Java Look and Feel Graphics Repository:
        // http://java.sun.com/developer/techDocs/hi/repository 
        button = new JButton("Find",
                new ImageIcon(getClass().
                        getResource("resources/images/edit-find.png")));
        button.setToolTipText("button with text and image");
        button.setHorizontalTextPosition(JButton.LEADING);
        button.setIconTextGap(6);
        //</snip>
        button.putClientProperty("snippetKey", "Create button with text and image");
        panel.add(button);

        //<snip>Create button with background color
        button = new JButton("Go");
        button.setBackground(Color.green);
        button.setContentAreaFilled(true);
        button.setOpaque(false);
        button.setToolTipText("button with background color");
        //</snip>
        button.putClientProperty("snippetKey", "Create button with background color");
        panel.add(button);

        return panel;
    }

    protected JPanel createCreativeButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 8));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "More Interesting Buttons"));

        //<snip>Create button with no border
        JButton button = new JButton();
        button.setText("Connect");
        button.setIcon(new ImageIcon(getClass().getResource("resources/images/earth_day.gif")));
        button.setPressedIcon(new ImageIcon(getClass().getResource("resources/images/earth_night.gif")));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setIconTextGap(0);
        button.setToolTipText("button with no border");
        //</snip>
        button.putClientProperty("snippetKey", "Create button with no border");
        panel.add(button);

        //<snip>Create image button with rollover image
        button = new JButton();
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setIcon(new ImageIcon(getClass().getResource("resources/images/redbutton.png")));
        button.setRolloverEnabled(true);
        button.setRolloverIcon(new ImageIcon(getClass().getResource("resources/images/redbutton_glow.png")));
        button.setPressedIcon(new ImageIcon(getClass().getResource("resources/images/redbutton_dark.png")));
        button.setToolTipText("button with rollover image");
        //</snip>
        button.putClientProperty("snippetKey", "Create image button with rollover image");
        panel.add(button);

        //<snip>Create HTML hyperlink        
        JHyperlink hyperlink;
        try {
            hyperlink = new JHyperlink("Get More Info", "http://java.sun.com/j2se");
        } catch (URISyntaxException use) {
            use.printStackTrace();
            hyperlink = new JHyperlink("Get More Info");
        }
        //</snip>
        hyperlink.putClientProperty("snippetKey", "Create HTML hyperlink");
        panel.add(hyperlink);

        //<snip>Create HTML image hyperlink
        try {
            hyperlink = new JHyperlink(
                    new ImageIcon(getClass().getResource("resources/images/blogs.png")),
                    "http://weblogs.java.net");
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }
        //</snip>
        button.putClientProperty("snippetKey", "Create HTML image hyperlink");
        panel.add(hyperlink);

        return panel;
    }

    public static void main(String args[]) {
        final ButtonDemo buttonDemo = new ButtonDemo();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("JButton Demo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(buttonDemo);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}

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

package com.sun.swingset3.demos.optionpane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.*;

import com.sun.swingset3.demos.ResourceManager;
import com.sun.swingset3.DemoProperties;

/**
 * JOptionPaneDemo
 *
 * @author Jeff Dinkins
 * @version 1.11 11/17/05
 */
@DemoProperties(
        value = "JOptionPane Demo",
        category = "Choosers",
        description = "Demonstrates JOptionPane, a component which displays standard message dialogs (question, warning, error, etc).",
        sourceFiles = {
                "com/sun/swingset3/demos/optionpane/OptionPaneDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/optionpane/resources/OptionPaneDemo.properties",
                "com/sun/swingset3/demos/optionpane/resources/images/bottle.gif",
                "com/sun/swingset3/demos/optionpane/resources/images/OptionPaneDemo.gif"
                }
)
public class OptionPaneDemo extends JPanel {
    private static final Dimension VGAP15 = new Dimension(1, 15);
    private static final Dimension VGAP30 = new Dimension(1, 30);

    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(OptionPaneDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new OptionPaneDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * OptionPaneDemo Constructor
     */
    public OptionPaneDemo() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel bp = new JPanel() {
            public Dimension getMaximumSize() {
                return new Dimension(getPreferredSize().width, super.getMaximumSize().height);
            }
        };
        bp.setLayout(new BoxLayout(bp, BoxLayout.Y_AXIS));

        bp.add(Box.createRigidArea(VGAP30));
        bp.add(Box.createRigidArea(VGAP30));

        bp.add(createInputDialogButton());
        bp.add(Box.createRigidArea(VGAP15));
        bp.add(createWarningDialogButton());
        bp.add(Box.createRigidArea(VGAP15));
        bp.add(createMessageDialogButton());
        bp.add(Box.createRigidArea(VGAP15));
        bp.add(createComponentDialogButton());
        bp.add(Box.createRigidArea(VGAP15));
        bp.add(createConfirmDialogButton());
        bp.add(Box.createVerticalGlue());

        add(Box.createHorizontalGlue());
        add(bp);
        add(Box.createHorizontalGlue());
    }

    private JButton createWarningDialogButton() {
        Action a = new AbstractAction(resourceManager.getString("OptionPaneDemo.warningbutton")) {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        OptionPaneDemo.this,
                        resourceManager.getString("OptionPaneDemo.warningtext"),
                        resourceManager.getString("OptionPaneDemo.warningtitle"),
                        JOptionPane.WARNING_MESSAGE
                );
            }
        };
        return createButton(a);
    }

    private JButton createMessageDialogButton() {
        Action a = new AbstractAction(resourceManager.getString("OptionPaneDemo.messagebutton")) {
            final URL img = getClass().getResource("resources/images/bottle.gif");
            final String imagesrc = "<img src=\"" + img + "\" width=\"284\" height=\"100\">";
            final String message = resourceManager.getString("OptionPaneDemo.messagetext");

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        OptionPaneDemo.this,
                        "<html>" + imagesrc + "<br><center>" + message + "</center><br></html>"
                );
            }
        };
        return createButton(a);
    }

    private JButton createConfirmDialogButton() {
        Action a = new AbstractAction(resourceManager.getString("OptionPaneDemo.confirmbutton")) {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.confirmquestion"));
                if (result == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.confirmyes"));
                } else if (result == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.confirmno"));
                }
            }
        };
        return createButton(a);
    }

    private JButton createInputDialogButton() {
        Action a = new AbstractAction(resourceManager.getString("OptionPaneDemo.inputbutton")) {
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.inputquestion"));
                if ((result != null) && (result.length() > 0)) {
                    JOptionPane.showMessageDialog(OptionPaneDemo.this,
                            result + ": " +
                                    resourceManager.getString("OptionPaneDemo.inputresponse"));
                }
            }
        };
        return createButton(a);
    }

    private JButton createComponentDialogButton() {
        Action a = new AbstractAction(resourceManager.getString("OptionPaneDemo.componentbutton")) {
            public void actionPerformed(ActionEvent e) {
                // In a ComponentDialog, you can show as many message components and
                // as many options as you want:

                // Messages
                Object[] message = new Object[4];
                message[0] = resourceManager.getString("OptionPaneDemo.componentmessage");
                message[1] = new JTextField(resourceManager.getString("OptionPaneDemo.componenttextfield"));

                JComboBox cb = new JComboBox();
                cb.addItem(resourceManager.getString("OptionPaneDemo.component_cb1"));
                cb.addItem(resourceManager.getString("OptionPaneDemo.component_cb2"));
                cb.addItem(resourceManager.getString("OptionPaneDemo.component_cb3"));
                message[2] = cb;

                message[3] = resourceManager.getString("OptionPaneDemo.componentmessage2");

                // Options
                String[] options = {
                        resourceManager.getString("OptionPaneDemo.component_op1"),
                        resourceManager.getString("OptionPaneDemo.component_op2"),
                        resourceManager.getString("OptionPaneDemo.component_op3"),
                        resourceManager.getString("OptionPaneDemo.component_op4"),
                        resourceManager.getString("OptionPaneDemo.component_op5")
                };
                int result = JOptionPane.showOptionDialog(
                        OptionPaneDemo.this,                        // the parent that the dialog blocks
                        message,                                    // the dialog message array
                        resourceManager.getString("OptionPaneDemo.componenttitle"), // the title of the dialog window
                        JOptionPane.DEFAULT_OPTION,                 // option type
                        JOptionPane.INFORMATION_MESSAGE,            // message type
                        null,                                       // optional icon, use null to use the default icon
                        options,                                    // options string array, will be made into buttons
                        options[3]                                  // option that should be made into a default button
                );
                switch (result) {
                    case 0: // yes
                        JOptionPane.showMessageDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.component_r1"));
                        break;
                    case 1: // no
                        JOptionPane.showMessageDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.component_r2"));
                        break;
                    case 2: // maybe
                        JOptionPane.showMessageDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.component_r3"));
                        break;
                    case 3: // probably
                        JOptionPane.showMessageDialog(OptionPaneDemo.this, resourceManager.getString("OptionPaneDemo.component_r4"));
                        break;
                    default:
                        break;
                }

            }
        };
        return createButton(a);
    }

    private JButton createButton(Action a) {
        JButton b = new JButton() {
            public Dimension getMaximumSize() {
                int width = Short.MAX_VALUE;
                int height = super.getMaximumSize().height;
                return new Dimension(width, height);
            }
        };
        // setting the following client property informs the button to show
        // the action text as it's name. The default is to not show the
        // action text.
        b.putClientProperty("displayActionText", Boolean.TRUE);
        b.setAction(a);
        // b.setAlignmentX(JButton.CENTER_ALIGNMENT);
        return b;
    }

}
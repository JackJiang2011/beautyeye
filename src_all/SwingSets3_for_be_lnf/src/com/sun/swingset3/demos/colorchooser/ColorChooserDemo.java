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

package com.sun.swingset3.demos.colorchooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sun.swingset3.demos.JGridPanel;
import com.sun.swingset3.demos.ResourceManager;
import com.sun.swingset3.DemoProperties;


/**
 * JColorChooserDemo
 *
 * @author Jeff Dinkins
 * @version 1.1 07/16/99
 */
@DemoProperties(
        value = "JColorChooser Demo",
        category = "Choosers",
        description = "Demonstrates JColorChooser, a component which allows the user to pick a color.",
        sourceFiles = {
                "com/sun/swingset3/demos/colorchooser/ColorChooserDemo.java",
                "com/sun/swingset3/demos/colorchooser/BezierAnimationPanel.java",
                "com/sun/swingset3/demos/JGridPanel.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/colorchooser/resources/ColorChooserDemo.properties",
                "com/sun/swingset3/demos/colorchooser/resources/images/ColorChooserDemo.gif"
                }
)
public class ColorChooserDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    private final BezierAnimationPanel bezAnim = new BezierAnimationPanel();

    private final JButton outerColorButton = new JButton(resourceManager.getString("ColorChooserDemo.outer_line"));

    private final JButton backgroundColorButton = new JButton(resourceManager.getString("ColorChooserDemo.background"));

    private final JButton gradientAButton = new JButton(resourceManager.getString("ColorChooserDemo.grad_a"));

    private final JButton gradientBButton = new JButton(resourceManager.getString("ColorChooserDemo.grad_b"));

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(ColorChooserDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ColorChooserDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * ColorChooserDemo Constructor
     */
    public ColorChooserDemo() {
        setLayout(new BorderLayout());

        outerColorButton.setIcon(new ColorSwatch(BezierAnimationPanel.BezierColor.OUTER));

        backgroundColorButton.setIcon(new ColorSwatch(BezierAnimationPanel.BezierColor.BACKGROUND));

        gradientAButton.setIcon(new ColorSwatch(BezierAnimationPanel.BezierColor.GRADIENT_A));

        gradientBButton.setIcon(new ColorSwatch(BezierAnimationPanel.BezierColor.GRADIENT_B));

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();

                final BezierAnimationPanel.BezierColor bezierColor =
                        ((ColorSwatch) button.getIcon()).getBezierColor();

                Color current = bezAnim.getBezierColor(bezierColor);

                final JColorChooser chooser = new JColorChooser(current != null ?
                        current :
                        Color.WHITE);

                ActionListener colorChooserListener = new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        bezAnim.setBezierColor(bezierColor, chooser.getColor());
                    }
                };

                JDialog dialog = JColorChooser.createDialog(ColorChooserDemo.this,
                        resourceManager.getString("ColorChooserDemo.chooser_title"),
                        true,
                        chooser,
                        colorChooserListener,
                        null);

                dialog.setVisible(true);
            }
        };

        outerColorButton.addActionListener(l);
        backgroundColorButton.addActionListener(l);
        gradientAButton.addActionListener(l);
        gradientBButton.addActionListener(l);

        // Add control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 15, 0));

        buttonPanel.add(backgroundColorButton);
        buttonPanel.add(gradientAButton);
        buttonPanel.add(gradientBButton);
        buttonPanel.add(outerColorButton);

        // Add everything to the panel
        JGridPanel pnContent = new JGridPanel(1, 0, 1);

        pnContent.cell(buttonPanel, JGridPanel.Layout.CENTER).
                cell(bezAnim);

        pnContent.setBorder(new EmptyBorder(10, 0, 0, 0));

        add(pnContent);
    }

    private class ColorSwatch implements Icon {
        private final BezierAnimationPanel.BezierColor bezierColor;

        public ColorSwatch(BezierAnimationPanel.BezierColor bezierColor) {
            this.bezierColor = bezierColor;
        }

        public int getIconWidth() {
            return 11;
        }

        public int getIconHeight() {
            return 11;
        }

        public BezierAnimationPanel.BezierColor getBezierColor() {
            return bezierColor;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.black);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
            g.setColor(bezAnim.getBezierColor(bezierColor));
            g.fillRect(x + 2, y + 2, getIconWidth() - 4, getIconHeight() - 4);
        }
    }
}

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

package com.sun.swingset3.demos.tooltip;

import java.awt.*;
import javax.swing.*;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * ToolTip Demo
 *
 * @version 1.9 11/17/05
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "ToolTips Demo",
        category = "General",
        description = "Demonstrates how tooltips can be easily added to Swing GUI components",
        sourceFiles = {
                "com/sun/swingset3/demos/tooltip/ToolTipDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/tooltip/resources/ToolTipDemo.properties",
                "com/sun/swingset3/demos/tooltip/resources/images/tooltip_background.png",
                "com/sun/swingset3/demos/tooltip/resources/images/ToolTipDemo.gif"
                }
)
public class ToolTipDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(ToolTipDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ToolTipDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * ToolTipDemo Constructor
     */
    public ToolTipDemo() {
        setLayout(new BorderLayout());

        // Create a panel which contains specific tooltip regions.
        Toolbox toolbox = new Toolbox();

        add(toolbox, BorderLayout.CENTER);
    }

    public class Toolbox extends JPanel {
        private final Rectangle plainRect = new Rectangle(44, 0, 186, 128);
        private final Rectangle htmlRect = new Rectangle(240, 134, 186, 186);
        private final Rectangle styledRect = new Rectangle(45, 327, 188, 134);

        private final JLabel background;
        private final JComponent plainToolTipRegion;
        private final JComponent htmlToolTipRegion;
        private final JComponent styledToolTipRegion;

        public Toolbox() {
            setLayout(null);

            background = new JLabel(resourceManager.createImageIcon("tooltip_background.png",
                    resourceManager.getString("ToolTipDemo.toolbox")));

            background.setVerticalAlignment(JLabel.TOP);
            background.setHorizontalAlignment(JLabel.LEFT);

            // Note: tooltip text isn't retrieved from properties file in order
            // to make this code easier to understand

            //<snip>Create region for displaying plain tooltip
            plainToolTipRegion = createToolTipRegion(resourceManager.getString("ToolTipDemo.plain"));
            plainToolTipRegion.setToolTipText("A simple one line tip.");
            //</snip>

            //<snip>Create region for displaying HTML tooltip
            htmlToolTipRegion = createToolTipRegion(resourceManager.getString("ToolTipDemo.html"));
            htmlToolTipRegion.setToolTipText("<html><body bgcolor=\"#AABBFF\">In case you thought that tooltips had to be<p>" +
                    "boring, one line descriptions, the <font color=blue size=+2>Swing!</font> team<p>" +
                    "is happy to shatter your illusions.<p>" +
                    "In Swing, you can use HTML to <ul><li>Have Lists<li><b>Bold</b> text<li><em>emphasized</em>" +
                    "text<li>text with <font color=red>Color</font><li>text in different <font size=+3>sizes</font>" +
                    "<li>and <font face=AvantGarde>Fonts</font></ul>Oh, and they can be multi-line, too.</body></html>");
            //</snip>

            //<snip>Create region for displaying styled tooltip            
            styledToolTipRegion = createToolTipRegion(resourceManager.getString("ToolTipDemo.styled"));
            styledToolTipRegion.setToolTipText("<html>Tips can be styled to be" +
                    "<br><b>interesting</b> and <i>fun</i></html>");
            //</snip>

            add(htmlToolTipRegion);
            add(styledToolTipRegion);
            add(plainToolTipRegion);

            add(background);
        }

        public void doLayout() {
            background.setBounds(0, 0, getWidth(), getHeight());
            plainToolTipRegion.setBounds(plainRect);
            htmlToolTipRegion.setBounds(htmlRect);
            styledToolTipRegion.setBounds(styledRect);
        }

        private JComponent createToolTipRegion(String text) {
            JLabel region = new JLabel(text);

            region.setForeground(Color.white);
            region.setFont(getFont().deriveFont(18f));
            region.setHorizontalAlignment(JLabel.CENTER);
            region.setVerticalAlignment(JLabel.CENTER);

            return region;
        }
    }
}

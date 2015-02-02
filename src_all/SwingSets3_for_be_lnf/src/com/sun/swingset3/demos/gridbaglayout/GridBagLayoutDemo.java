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
package com.sun.swingset3.demos.gridbaglayout;

import java.awt.*;
import javax.swing.*;

import com.sun.swingset3.demos.JGridPanel;
import com.sun.swingset3.demos.ResourceManager;
import com.sun.swingset3.DemoProperties;

/**
 * GridBagLayout Demo
 *
 * @author Pavel Porvatov
 */
@DemoProperties(
        value = "GridBagLayout Demo",
        category = "Containers",
        description = "Demonstrates GridBagLayout, a layout which allows to arrange components in containers.",
        sourceFiles = {
                "com/sun/swingset3/demos/gridbaglayout/GridBagLayoutDemo.java",
                "com/sun/swingset3/demos/gridbaglayout/Calculator.java",
                "com/sun/swingset3/demos/JGridPanel.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/gridbaglayout/resources/GridBagLayoutDemo.properties",
                "com/sun/swingset3/demos/gridbaglayout/resources/images/GridBagLayoutDemo.gif"
                }
)
public class GridBagLayoutDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    private final JLabel lbCaption = new JLabel("<html>" +
            resourceManager.getString("GridBagLayoutDemo.caption.text") + "</html>");

    private final Calculator calculator = new Calculator();

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(GridBagLayoutDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GridBagLayoutDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public GridBagLayoutDemo() {
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        JGridPanel pnContent = new JGridPanel(1, 0, 2);

        pnContent.setBorderEqual(10);

        pnContent.cell(lbCaption, JGridPanel.Layout.FILL).
                cell().
                cell(calculator, JGridPanel.Layout.CENTER, JGridPanel.Layout.FIRST).
                cell();

        add(pnContent);
    }
}

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

package com.sun.swingset3.demos.scrollpane;

import java.awt.*;
import javax.swing.*;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * Scroll Pane Demo
 *
 * @version 1.9 11/17/05
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "JScrollPane Demo",
        category = "Containers",
        description = "Demonstrates JScrollPane, a container for scrolling contents within a view port",
        sourceFiles = {
                "com/sun/swingset3/demos/scrollpane/ScrollPaneDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/scrollpane/resources/ScrollPaneDemo.properties",
                "com/sun/swingset3/demos/scrollpane/resources/images/colheader.jpg",
                "com/sun/swingset3/demos/scrollpane/resources/images/COPYRIGHT",
                "com/sun/swingset3/demos/scrollpane/resources/images/crayons.jpg",
                "com/sun/swingset3/demos/scrollpane/resources/images/lowerleft.jpg",
                "com/sun/swingset3/demos/scrollpane/resources/images/rowheader.jpg",
                "com/sun/swingset3/demos/scrollpane/resources/images/ScrollPaneDemo.gif",
                "com/sun/swingset3/demos/scrollpane/resources/images/upperleft.jpg",
                "com/sun/swingset3/demos/scrollpane/resources/images/upperright.jpg"}
)
public class ScrollPaneDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(ScrollPaneDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ScrollPaneDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * ScrollPaneDemo Constructor
     */
    public ScrollPaneDemo() {
        setLayout(new BorderLayout());

        ImageIcon crayons = resourceManager.createImageIcon("crayons.jpg",
                resourceManager.getString("ScrollPaneDemo.crayons"));
        add(new ImageScroller(crayons), BorderLayout.CENTER);
    }


    /**
     * ScrollPane class that demonstrates how to set the various column and row headers
     * and corners.
     */
    private class ImageScroller extends JScrollPane {
        public ImageScroller(Icon icon) {
            super();

            // Panel to hold the icon image
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JLabel(icon), BorderLayout.CENTER);
            getViewport().add(p);

            // Create and add a column header to the scrollpane
            JLabel colHeader = new JLabel(
                    resourceManager.createImageIcon("colheader.jpg", resourceManager.getString("ScrollPaneDemo.colheader")));
            setColumnHeaderView(colHeader);

            // Create and add a row header to the scrollpane
            JLabel rowHeader = new JLabel(
                    resourceManager.createImageIcon("rowheader.jpg", resourceManager.getString("ScrollPaneDemo.rowheader")));
            setRowHeaderView(rowHeader);

            // Create and add the upper left corner
            JLabel cornerUL = new JLabel(
                    resourceManager.createImageIcon("upperleft.jpg", resourceManager.getString("ScrollPaneDemo.upperleft")));
            setCorner(UPPER_LEFT_CORNER, cornerUL);

            // Create and add the upper right corner
            JLabel cornerUR = new JLabel(
                    resourceManager.createImageIcon("upperright.jpg", resourceManager.getString("ScrollPaneDemo.upperright")));
            setCorner(UPPER_RIGHT_CORNER, cornerUR);

            // Create and add the lower left corner
            JLabel cornerLL = new JLabel(
                    resourceManager.createImageIcon("lowerleft.jpg", resourceManager.getString("ScrollPaneDemo.lowerleft")));
            setCorner(LOWER_LEFT_CORNER, cornerLL);

            JScrollBar vsb = getVerticalScrollBar();
            JScrollBar hsb = getHorizontalScrollBar();

            vsb.setValue(icon.getIconHeight());
            hsb.setValue(icon.getIconWidth() / 10);
        }
    }

}
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

package com.sun.swingset3.demos.tree;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * JTree Demo
 *
 * @version 1.13 11/17/05
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "JTree Demo",
        category = "Data",
        description = "Demonstrates JTree, a component which supports display/editing of hierarchical data",
        sourceFiles = {
                "com/sun/swingset3/demos/tree/TreeDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/tree/resources/tree.txt",
                "com/sun/swingset3/demos/tree/resources/TreeDemo.properties",
                "com/sun/swingset3/demos/tree/resources/images/TreeDemo.gif"
                }
)
public class TreeDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(TreeDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TreeDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * TreeDemo Constructor
     */
    public TreeDemo() {
        setLayout(new BorderLayout());

        add(new JScrollPane(createTree()), BorderLayout.CENTER);
    }

    private JTree createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(resourceManager.getString("TreeDemo.music"));
        DefaultMutableTreeNode catagory = null;
        DefaultMutableTreeNode artist = null;
        DefaultMutableTreeNode record = null;

        // open tree data 
        URL url = getClass().getResource("resources/tree.txt");

        try {
            // convert url to buffered string
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(isr);

            // read one line at a time, put into tree
            String line = reader.readLine();
            while (line != null) {
                // System.out.println("reading in: ->" + line + "<-");
                char linetype = line.charAt(0);
                switch (linetype) {
                    case 'C':
                        catagory = new DefaultMutableTreeNode(line.substring(2));
                        top.add(catagory);
                        break;
                    case 'A':
                        if (catagory != null) {
                            catagory.add(artist = new DefaultMutableTreeNode(line.substring(2)));
                        }
                        break;
                    case 'R':
                        if (artist != null) {
                            artist.add(record = new DefaultMutableTreeNode(line.substring(2)));
                        }
                        break;
                    case 'S':
                        if (record != null) {
                            record.add(new DefaultMutableTreeNode(line.substring(2)));
                        }
                        break;
                    default:
                        break;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
        }

        JTree tree = new JTree(top) {
            public Insets getInsets() {
                return new Insets(5, 5, 5, 5);
            }
        };

        tree.setEditable(true);

        return tree;
    }
}

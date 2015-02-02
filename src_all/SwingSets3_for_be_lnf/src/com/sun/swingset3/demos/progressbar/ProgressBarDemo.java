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

package com.sun.swingset3.demos.progressbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * JProgressBar Demo
 *
 * @version 1.12 11/17/05
 * @author Jeff Dinkins
 # @author Peter Korn (accessibility support)
 */
@DemoProperties(
        value = "ProgressBar Demo",
        category = "Controls",
        description = "Demonstrates the JProgressBar, a control which displays progress to the user",
        sourceFiles = {
                "com/sun/swingset3/demos/progressbar/ProgressBarDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/progressbar/resources/ProgressBarDemo.properties",
                "com/sun/swingset3/demos/progressbar/resources/images/ProgressBarDemo.gif"
                }
)
public class ProgressBarDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(ProgressBarDemo.class.getAnnotation(DemoProperties.class).value());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ProgressBarDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * ProgressBarDemo Constructor
     */
    public ProgressBarDemo() {
        createProgressPanel();
    }

    private final javax.swing.Timer timer = new javax.swing.Timer(18, createTextLoadAction());
    private Action loadAction;
    private Action stopAction;
    private JProgressBar progressBar;
    private JTextArea progressTextArea;

    private void createProgressPanel() {
        setLayout(new BorderLayout());

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        textWrapper.setAlignmentX(LEFT_ALIGNMENT);
        progressTextArea = new MyTextArea();

        progressTextArea.getAccessibleContext().setAccessibleName(
                resourceManager.getString("ProgressBarDemo.accessible_text_area_name"));
        progressTextArea.getAccessibleContext().setAccessibleName(
                resourceManager.getString("ProgressBarDemo.accessible_text_area_description"));
        textWrapper.add(new JScrollPane(progressTextArea), BorderLayout.CENTER);

        add(textWrapper, BorderLayout.CENTER);

        JPanel progressPanel = new JPanel();
        add(progressPanel, BorderLayout.SOUTH);

        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, text.length()) {
            public Dimension getPreferredSize() {
                return new Dimension(300, super.getPreferredSize().height);
            }
        };
        progressBar.getAccessibleContext().setAccessibleName(
                resourceManager.getString("ProgressBarDemo.accessible_text_loading_progress"));

        progressPanel.add(progressBar);
        progressPanel.add(createLoadButton());
        progressPanel.add(createStopButton());
    }

    private JButton createLoadButton() {
        loadAction = new AbstractAction(resourceManager.getString("ProgressBarDemo.start_button")) {
            public void actionPerformed(ActionEvent e) {
                loadAction.setEnabled(false);
                stopAction.setEnabled(true);
                if (progressBar.getValue() == progressBar.getMaximum()) {
                    progressBar.setValue(0);
                    textLocation = 0;
                    progressTextArea.setText("");
                }
                timer.start();
            }
        };
        return createButton(loadAction);
    }

    private JButton createStopButton() {
        stopAction = new AbstractAction(resourceManager.getString("ProgressBarDemo.stop_button")) {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                loadAction.setEnabled(true);
                stopAction.setEnabled(false);
            }
        };
        return createButton(stopAction);
    }

    private static JButton createButton(Action a) {
        JButton b = new JButton();
        // setting the following client property informs the button to show
        // the action text as it's name. The default is to not show the
        // action text.
        b.putClientProperty("displayActionText", Boolean.TRUE);
        b.setAction(a);
        return b;
    }


    private int textLocation = 0;

    private final String text = resourceManager.getString("ProgressBarDemo.text");

    private Action createTextLoadAction() {
        return new AbstractAction("text load action") {
            public void actionPerformed(ActionEvent e) {
                if (progressBar.getValue() < progressBar.getMaximum()) {
                    progressBar.setValue(progressBar.getValue() + 1);
                    progressTextArea.append(text.substring(textLocation, textLocation + 1));
                    textLocation++;
                } else {
                    timer.stop();
                    loadAction.setEnabled(true);
                    stopAction.setEnabled(false);
                }
            }
        };
    }


    private static class MyTextArea extends JTextArea {
        private MyTextArea() {
            super(null, 0, 0);
            setEditable(false);
            setText("");
        }

        public float getAlignmentX() {
            return LEFT_ALIGNMENT;
        }

        public float getAlignmentY() {
            return TOP_ALIGNMENT;
        }
    }
}



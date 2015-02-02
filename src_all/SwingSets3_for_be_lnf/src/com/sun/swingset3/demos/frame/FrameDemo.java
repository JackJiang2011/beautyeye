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

package com.sun.swingset3.demos.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.DemoUtilities;

/**
 * Demo for Swing's JFrame toplevel component.
 *
 * @author aim
 */
@DemoProperties(
        value = "JFrame Demo",
        category = "Toplevel Containers",
        description = "Demonstrates JFrame, Swing's top-level primary window container.",
        sourceFiles = {
                "com/sun/swingset3/demos/frame/BusyGlass.java",
                "com/sun/swingset3/demos/frame/FrameDemo.java",
                "com/sun/swingset3/demos/DemoUtilities.java",
                "com/sun/swingset3/demos/frame/resources/FrameDemo.html",
                "com/sun/swingset3/demos/frame/resources/images/FrameDemo.gif"
                }
)
public class FrameDemo extends JPanel {
    //<snip>Ensure system menubar is used on Mac OSX
    static {
        // Property must be set *early* due to Apple Bug#3909714
        // ignored on other platforms
        if (System.getProperty("os.name").equals("Mac OS X")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
    }
    //</snip>

    // Toplevel frame component    
    private JFrame frame;

    private JComponent frameSpaceholder;

    public FrameDemo() {
        initComponents();
    }

    protected void initComponents() {
        frame = createFrame();

        setLayout(new BorderLayout());
        add(createControlPanel(), BorderLayout.WEST);
        frameSpaceholder = createFrameSpaceholder(frame);
        add(frameSpaceholder, BorderLayout.CENTER);
    }

    protected JComponent createControlPanel() {
        Box controlPanel = Box.createVerticalBox();
        controlPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Create button to control visibility of frame
        JButton showButton = new JButton("Show JFrame...");
        showButton.addActionListener(new ShowActionListener());
        controlPanel.add(showButton);

        // Create checkbox to control busy state of frame
        JCheckBox busyCheckBox = new JCheckBox("Frame busy");
        busyCheckBox.setSelected(false);
        busyCheckBox.addChangeListener(new BusyChangeListener());
        controlPanel.add(busyCheckBox);

        return controlPanel;
    }

    private static JComponent createFrameSpaceholder(JFrame frame) {
        JPanel framePlaceholder = new JPanel();
        Dimension prefSize = frame.getPreferredSize();
        prefSize.width += 12;
        prefSize.height += 12;
        framePlaceholder.setPreferredSize(prefSize);

        return framePlaceholder;
    }

    private static JFrame createFrame() {

        //<snip>Create frame and set simple properties
        JFrame frame = new JFrame("Demo JFrame");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //</snip>

        //<snip>Set Minimized/titlebar icon Image
        //Note: How the image is used is platform-dependent
        Image iconImage = null;
        try {
            // todo: swingingduke.gif doesn't exist 
            URL imageURL = FrameDemo.class.getResource("resources/images/swingingduke.gif");
            iconImage = ImageIO.read(imageURL);
        } catch (Exception e) {
            // handle image IO exception
        }
        frame.setIconImage(iconImage);
        //</snip>

        //<snip>Make toplevel "busy"
        // busy glasspane is initially invisible
        frame.setGlassPane(new BusyGlass());
        //</snip>

        //<snip>Add a menubar
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        JMenu menu = new JMenu("File");
        menubar.add(menu);
        menu.add("Open");
        menu.add("Save");
        //</snip>

        //<snip>Add a horizontal toolbar
        JToolBar toolbar = new JToolBar();
        frame.add(toolbar, BorderLayout.NORTH);
        toolbar.add(new JButton("Toolbar Button"));
        //</snip>

        //<snip>Add the content area
        JLabel label = new JLabel("I'm content but a little blue.");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(300, 160));
        label.setBackground(new Color(197, 216, 236));
        label.setOpaque(true); // labels non-opaque by default
        frame.add(label);
        //snip

        //<snip>Add a statusbar
        JLabel statusLabel = new JLabel("I show status.");
        statusLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
        statusLabel.setHorizontalAlignment(JLabel.LEADING);
        frame.add(statusLabel, BorderLayout.SOUTH);
        //</snip>

        //<snip>Initialize frame's size to fit it's content
        frame.pack();
        //</snip>

        return frame;
    }

    public void start() {
        DemoUtilities.setToplevelLocation(frame, frameSpaceholder, SwingConstants.CENTER);
        showFrame();
    }

    public void stop() {
        //<snip>Hide frame
        frame.setVisible(false);
        //</snip>
    }

    public void showFrame() {
        //<snip>Show frame
        // if frame already visible, then bring to the front
        if (frame.isShowing()) {
            frame.toFront();
        } else {
            frame.setVisible(true);
        }
        //</snip>
    }

    //<snip>Make toplevel "busy"  
    public void setFrameBusy(boolean busy) {
        frame.getGlassPane().setVisible(busy);
        // Must explicitly disable the menubar because on OSX it will be
        // in the system menubar and not covered by the glasspane
        frame.getJMenuBar().setEnabled(!busy);
    }

    public boolean isFrameBusy() {
        return frame.getGlassPane().isVisible();
    }
    //</snip

    // remind(aim): replace with Beans binding

    private class ShowActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            showFrame();
        }
    }

    private class BusyChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent changeEvent) {
            JCheckBox busyCheckBox = (JCheckBox) changeEvent.getSource();
            setFrameBusy(busyCheckBox.isSelected());
            showFrame(); // bring frame back to front for demo purposes
        }
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                FrameDemo demo = new FrameDemo();
                frame.add(demo);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                demo.start();
            }
        });
    }
}

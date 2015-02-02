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

package com.sun.swingset3.demos.internalframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;

import javax.swing.border.EmptyBorder;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;


/**
 * Internal Frames Demo
 *
 * @version 1.16 11/17/05
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "JInternalFrame Demo",
        category = "Containers",
        description = "Demonstrates JInternalFrame, a frame which can be embedded within another container to" +
                "implement an MDI style interface.",
        sourceFiles = {
                "com/sun/swingset3/demos/internalframe/InternalFrameDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/internalframe/resources/InternalFrameDemo.properties",
                "com/sun/swingset3/demos/internalframe/resources/images/bananas.png",
                "com/sun/swingset3/demos/internalframe/resources/images/bananas_small.png",
                "com/sun/swingset3/demos/internalframe/resources/images/globe.png",
                "com/sun/swingset3/demos/internalframe/resources/images/globe_small.png",
                "com/sun/swingset3/demos/internalframe/resources/images/InternalFrameDemo.gif",
                "com/sun/swingset3/demos/internalframe/resources/images/package.png",
                "com/sun/swingset3/demos/internalframe/resources/images/package_small.png",
                "com/sun/swingset3/demos/internalframe/resources/images/soccer_ball.png",
                "com/sun/swingset3/demos/internalframe/resources/images/soccer_ball_small.png"
                }
)
public class InternalFrameDemo extends JPanel {
    private static final Dimension HGAP5 = new Dimension(5, 1);
    private static final Dimension VGAP10 = new Dimension(1, 10);
    private static final Dimension HGAP15 = new Dimension(15, 1);
    private static final Dimension VGAP15 = new Dimension(1, 15);

    private static final int PALETTE_X = 20;
    private static final int PALETTE_Y = 20;

    private static final int PALETTE_WIDTH = 250;
    private static final int PALETTE_HEIGHT = 250;

    private static final int FRAME0_X = PALETTE_X + PALETTE_WIDTH + 20;
    private static final int FRAME0_Y = 20;

    private static final int FRAME0_WIDTH = 300;
    private static final int FRAME0_HEIGHT = 300;

    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 300;

    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    private int windowCount = 0;
    private JDesktopPane desktop = null;

    private final ImageIcon icon1;
    private final ImageIcon icon2;
    private final ImageIcon icon3;
    private final ImageIcon icon4;
    private final ImageIcon smIcon1;
    private final ImageIcon smIcon2;
    private final ImageIcon smIcon3;
    private final ImageIcon smIcon4;

    private final Integer DEMO_FRAME_LAYER = new Integer(2);
    private final Integer PALETTE_LAYER = new Integer(3);

    private JCheckBox windowResizable = null;
    private JCheckBox windowClosable = null;
    private JCheckBox windowIconifiable = null;
    private JCheckBox windowMaximizable = null;

    private JTextField windowTitleField = null;
    private JLabel windowTitleLabel = null;


    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(InternalFrameDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new InternalFrameDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * InternalFrameDemo Constructor
     */
    public InternalFrameDemo() {
        setLayout(new BorderLayout());

        // preload all the icons we need for this demo
        icon1 = resourceManager.createImageIcon("bananas.png",
                resourceManager.getString("InternalFrameDemo.bananas"));
        icon2 = resourceManager.createImageIcon("globe.png",
                resourceManager.getString("InternalFrameDemo.globe"));
        icon3 = resourceManager.createImageIcon("package.png",
                resourceManager.getString("InternalFrameDemo.package"));
        icon4 = resourceManager.createImageIcon("soccer_ball.png",
                resourceManager.getString("InternalFrameDemo.soccerball"));

        smIcon1 = resourceManager.createImageIcon("bananas_small.png",
                resourceManager.getString("InternalFrameDemo.bananas"));
        smIcon2 = resourceManager.createImageIcon("globe_small.png",
                resourceManager.getString("InternalFrameDemo.globe"));
        smIcon3 = resourceManager.createImageIcon("package_small.png",
                resourceManager.getString("InternalFrameDemo.package"));
        smIcon4 = resourceManager.createImageIcon("soccer_ball_small.png",
                resourceManager.getString("InternalFrameDemo.soccerball"));

        //<snip>Create desktop pane
        // The desktop pane will contain all the internal frames
        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);
        //</snip>

        // Create the "frame maker" palette
        createInternalFramePalette();

        // Create an initial internal frame to show
        JInternalFrame frame1 = createInternalFrame(icon2, DEMO_FRAME_LAYER, 1, 1);
        frame1.setBounds(FRAME0_X, FRAME0_Y, FRAME0_WIDTH, FRAME0_HEIGHT);

    }


    /**
     * Create an internal frame and add a scrollable imageicon to it
     */
    private JInternalFrame createInternalFrame(Icon icon, Integer layer, int width, int height) {
        //<snip>Create internal frame
        JInternalFrame internalFrame = new JInternalFrame();
        //</snip>

        if (!windowTitleField.getText().equals(resourceManager.getString("InternalFrameDemo.frame_label"))) {
            internalFrame.setTitle(windowTitleField.getText() + "  ");
        } else {
            internalFrame = new JInternalFrame(
                    resourceManager.getString("InternalFrameDemo.frame_label") + " " + windowCount + "  ");
        }

        //<snip>Set internal frame properties
        internalFrame.setClosable(windowClosable.isSelected());
        internalFrame.setMaximizable(windowMaximizable.isSelected());
        internalFrame.setIconifiable(windowIconifiable.isSelected());
        internalFrame.setResizable(windowResizable.isSelected());
        //</snip>

        internalFrame.setBounds(FRAME0_X + 20 * (windowCount % 10),
                FRAME0_Y + 20 * (windowCount % 10), width, height);
        internalFrame.setContentPane(new ImageScroller(icon));

        windowCount++;

        //<snip>Add internal frame to desktop pane
        desktop.add(internalFrame, layer);
        //</snip>

        //<snip>Set internal frame to be active
        try {
            internalFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e2) {
        }
        //</snip>

        internalFrame.show();

        return internalFrame;
    }

    private void createInternalFramePalette() {
        JInternalFrame palette = new JInternalFrame(
                resourceManager.getString("InternalFrameDemo.palette_label")
        );
        palette.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        palette.getContentPane().setLayout(new BorderLayout());
        palette.setBounds(PALETTE_X, PALETTE_Y, PALETTE_WIDTH, PALETTE_HEIGHT);
        palette.setResizable(true);
        palette.setIconifiable(true);
        desktop.add(palette, PALETTE_LAYER);

        // *************************************
        // * Create create frame maker buttons *
        // *************************************
        JButton b1 = new JButton(smIcon1);
        JButton b2 = new JButton(smIcon2);
        JButton b3 = new JButton(smIcon3);
        JButton b4 = new JButton(smIcon4);

        // add frame maker actions
        b1.addActionListener(new CreateFrameAction(this, icon1));
        b2.addActionListener(new CreateFrameAction(this, icon2));
        b3.addActionListener(new CreateFrameAction(this, icon3));
        b4.addActionListener(new CreateFrameAction(this, icon4));

        // add frame maker buttons to panel
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JPanel buttons1 = new JPanel();
        buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));

        JPanel buttons2 = new JPanel();
        buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));

        buttons1.add(b1);
        buttons1.add(Box.createRigidArea(HGAP15));
        buttons1.add(b2);

        buttons2.add(b3);
        buttons2.add(Box.createRigidArea(HGAP15));
        buttons2.add(b4);

        p.add(Box.createRigidArea(VGAP10));
        p.add(buttons1);
        p.add(Box.createRigidArea(VGAP15));
        p.add(buttons2);
        p.add(Box.createRigidArea(VGAP10));

        palette.getContentPane().add(p, BorderLayout.NORTH);

        // ************************************
        // * Create frame property checkboxes *
        // ************************************
        p = new JPanel();
        p.setBorder(new EmptyBorder(10, 15, 10, 5));
        p.setLayout(new GridLayout(1, 2));


        Box box = new Box(BoxLayout.Y_AXIS);
        windowResizable = new JCheckBox(resourceManager.getString("InternalFrameDemo.resizable_label"), true);
        windowIconifiable = new JCheckBox(resourceManager.getString("InternalFrameDemo.iconifiable_label"), true);

        box.add(Box.createGlue());
        box.add(windowResizable);
        box.add(windowIconifiable);
        box.add(Box.createGlue());
        p.add(box);

        box = new Box(BoxLayout.Y_AXIS);
        windowClosable = new JCheckBox(resourceManager.getString("InternalFrameDemo.closable_label"), true);
        windowMaximizable = new JCheckBox(resourceManager.getString("InternalFrameDemo.maximizable_label"), true);

        box.add(Box.createGlue());
        box.add(windowClosable);
        box.add(windowMaximizable);
        box.add(Box.createGlue());
        p.add(box);

        palette.getContentPane().add(p, BorderLayout.CENTER);

        // ************************************
        // *   Create Frame title textfield   *
        // ************************************
        p = new JPanel();
        p.setBorder(new EmptyBorder(0, 0, 10, 0));

        windowTitleField = new JTextField(resourceManager.getString("InternalFrameDemo.frame_label"));
        windowTitleLabel = new JLabel(resourceManager.getString("InternalFrameDemo.title_text_field_label"));

        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(Box.createRigidArea(HGAP5));
        p.add(windowTitleLabel, BorderLayout.WEST);
        p.add(Box.createRigidArea(HGAP5));
        p.add(windowTitleField, BorderLayout.CENTER);
        p.add(Box.createRigidArea(HGAP5));

        palette.getContentPane().add(p, BorderLayout.SOUTH);

        palette.show();
    }


    private class CreateFrameAction extends AbstractAction {
        final InternalFrameDemo demo;
        final Icon icon;

        public CreateFrameAction(InternalFrameDemo demo, Icon icon) {
            this.demo = demo;
            this.icon = icon;
        }

        public void actionPerformed(ActionEvent e) {
            demo.createInternalFrame(icon,
                    DEMO_FRAME_LAYER,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
            );
        }
    }

    private static class ImageScroller extends JScrollPane {

        public ImageScroller(Icon icon) {
            super();
            JPanel p = new JPanel();
            p.setBackground(Color.white);
            p.setLayout(new BorderLayout());

            p.add(new JLabel(icon), BorderLayout.CENTER);

            getViewport().add(p);
            getHorizontalScrollBar().setUnitIncrement(10);
            getVerticalScrollBar().setUnitIncrement(10);
        }

        public Dimension getMinimumSize() {
            return new Dimension(25, 25);
        }

    }
}

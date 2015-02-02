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

package com.sun.swingset3.demos.slider;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * JSlider Demo
 *
 * @version 1.9 11/17/05
 * @author Dave Kloba
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "Slider Demo",
        category = "Controls",
        description = "Demonstrates the JSlider, a control which supports linear adjustment",
        sourceFiles = {
                "com/sun/swingset3/demos/slider/SliderDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/slider/resources/SliderDemo.properties",
                "com/sun/swingset3/demos/slider/resources/images/SliderDemo.gif"
                }
)
public class SliderDemo extends JPanel {
    private static final Dimension HGAP5 = new Dimension(5, 1);
    private static final Dimension VGAP5 = new Dimension(1, 5);
    private static final Dimension HGAP10 = new Dimension(10, 1);
    private static final Dimension VGAP10 = new Dimension(1, 10);
    private static final Dimension HGAP20 = new Dimension(20, 1);
    private static final Dimension HGAP25 = new Dimension(25, 1);

    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(SliderDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SliderDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * SliderDemo Constructor
     */
    public SliderDemo() {
        createSliderDemo();
    }

    private void createSliderDemo() {
        JSlider s;
        JPanel hp;
        JPanel vp;
        GridLayout g;
        JPanel tp;
        JLabel tf;
        ChangeListener listener;

        setLayout(new BorderLayout());

        tf = new JLabel(resourceManager.getString("SliderDemo.slidervalue"));
        add(tf, BorderLayout.SOUTH);

        tp = new JPanel();
        g = new GridLayout(1, 2);
        g.setHgap(5);
        g.setVgap(5);
        tp.setLayout(g);
        add(tp, BorderLayout.CENTER);

        listener = new SliderListener(tf);

        BevelBorder border = new BevelBorder(BevelBorder.LOWERED);

        hp = new JPanel();
        hp.setLayout(new BoxLayout(hp, BoxLayout.Y_AXIS));
        hp.setBorder(new TitledBorder(
                border,
                resourceManager.getString("SliderDemo.horizontal"),
                TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP));
        tp.add(hp);

        vp = new JPanel();
        vp.setLayout(new BoxLayout(vp, BoxLayout.X_AXIS));
        vp.setBorder(new TitledBorder(
                border,
                resourceManager.getString("SliderDemo.vertical"),
                TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP));
        tp.add(vp);

        // Horizontal Slider 1
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.plain")));
        s = new JSlider(-10, 100, 20);
        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.plain"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.a_plain_slider"));
        s.addChangeListener(listener);

        p.add(Box.createRigidArea(VGAP5));
        p.add(s);
        p.add(Box.createRigidArea(VGAP5));
        hp.add(p);
        hp.add(Box.createRigidArea(VGAP10));

        // Horizontal Slider 2
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.majorticks")));
        s = new JSlider(100, 1000, 400);
        s.setPaintTicks(true);
        s.setMajorTickSpacing(100);
        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.majorticks"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.majorticksdescription"));
        s.addChangeListener(listener);

        p.add(Box.createRigidArea(VGAP5));
        p.add(s);
        p.add(Box.createRigidArea(VGAP5));
        hp.add(p);
        hp.add(Box.createRigidArea(VGAP10));

        // Horizontal Slider 3
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.ticks")));
        s = new JSlider(0, 11, 6);

        s.putClientProperty("JSlider.isFilled", Boolean.TRUE);

        s.setPaintTicks(true);
        s.setMajorTickSpacing(5);
        s.setMinorTickSpacing(1);

        s.setPaintLabels(true);
        s.setSnapToTicks(true);

        s.getLabelTable().put(new Integer(11), new JLabel(Integer.toString(11), JLabel.CENTER));
        s.setLabelTable(s.getLabelTable());

        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.minorticks"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.minorticksdescription"));

        s.addChangeListener(listener);

        p.add(Box.createRigidArea(VGAP5));
        p.add(s);
        p.add(Box.createRigidArea(VGAP5));
        hp.add(p);
        hp.add(Box.createRigidArea(VGAP10));

        // Horizontal Slider 4
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.disabled")));
        BoundedRangeModel brm = new DefaultBoundedRangeModel(80, 0, 0, 100);
        s = new JSlider(brm);
        s.setPaintTicks(true);
        s.setMajorTickSpacing(20);
        s.setMinorTickSpacing(5);
        s.setEnabled(false);
        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.disabled"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.disableddescription"));
        s.addChangeListener(listener);

        p.add(Box.createRigidArea(VGAP5));
        p.add(s);
        p.add(Box.createRigidArea(VGAP5));
        hp.add(p);

        //////////////////////////////////////////////////////////////////////////////

        // Vertical Slider 1
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.plain")));
        s = new JSlider(JSlider.VERTICAL, -10, 100, 20);
        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.plain"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.a_plain_slider"));
        s.addChangeListener(listener);
        p.add(Box.createRigidArea(HGAP10));
        p.add(s);
        p.add(Box.createRigidArea(HGAP10));
        vp.add(p);
        vp.add(Box.createRigidArea(HGAP10));

        // Vertical Slider 2
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.majorticks")));
        s = new JSlider(JSlider.VERTICAL, 100, 1000, 400);

        s.putClientProperty("JSlider.isFilled", Boolean.TRUE);

        s.setPaintTicks(true);
        s.setMajorTickSpacing(100);
        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.majorticks"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.majorticksdescription"));
        s.addChangeListener(listener);
        p.add(Box.createRigidArea(HGAP25));
        p.add(s);
        p.add(Box.createRigidArea(HGAP25));
        vp.add(p);
        vp.add(Box.createRigidArea(HGAP5));

        // Vertical Slider 3
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.minorticks")));
        s = new JSlider(JSlider.VERTICAL, 0, 100, 60);
        s.setPaintTicks(true);
        s.setMajorTickSpacing(20);
        s.setMinorTickSpacing(5);

        s.setPaintLabels(true);

        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.minorticks"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.minorticksdescription"));

        s.addChangeListener(listener);
        p.add(Box.createRigidArea(HGAP10));
        p.add(s);
        p.add(Box.createRigidArea(HGAP10));
        vp.add(p);
        vp.add(Box.createRigidArea(HGAP5));

        // Vertical Slider 4
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBorder(new TitledBorder(resourceManager.getString("SliderDemo.disabled")));
        s = new JSlider(JSlider.VERTICAL, 0, 100, 80);
        s.setPaintTicks(true);
        s.setMajorTickSpacing(20);
        s.setMinorTickSpacing(5);
        s.setEnabled(false);
        s.getAccessibleContext().setAccessibleName(resourceManager.getString("SliderDemo.disabled"));
        s.getAccessibleContext().setAccessibleDescription(resourceManager.getString("SliderDemo.disableddescription"));
        s.addChangeListener(listener);
        p.add(Box.createRigidArea(HGAP20));
        p.add(s);
        p.add(Box.createRigidArea(HGAP20));
        vp.add(p);
    }

    private class SliderListener implements ChangeListener {
        private final JLabel tf;

        public SliderListener(JLabel f) {
            tf = f;
        }

        public void stateChanged(ChangeEvent e) {
            JSlider s1 = (JSlider) e.getSource();
            tf.setText(resourceManager.getString("SliderDemo.slidervalue") + s1.getValue());
        }
    }
}


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

package com.sun.swingset3.demos.combobox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.accessibility.AccessibleRelation;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.sun.swingset3.demos.ResourceManager;
import com.sun.swingset3.DemoProperties;

/**
 * JComboBox Demo
 *
 * @author Jeff Dinkins
 * @version 1.13 11/17/05
 */
@DemoProperties(
        value = "JComboBox Demo",
        category = "Controls",
        description = "Demonstrates JComboBox, a control which allows the user to make a selection from a popup list",
        sourceFiles = {
                "com/sun/swingset3/demos/combobox/ComboBoxDemo.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/combobox/resources/ComboBoxDemo.properties",
                "com/sun/swingset3/demos/combobox/resources/images/brenteyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/brenthair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/brentmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/ComboBoxDemo.gif",
                "com/sun/swingset3/demos/combobox/resources/images/georgeseyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/georgeshair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/georgesmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/hanseyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/hanshair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/hansmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/howardeyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/howardhair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/howardmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jameseyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jameshair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jamesmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jeffeyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jeffhair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jeffmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/joneyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jonhair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/jonmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/laraeyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/larahair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/laramouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/larryeyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/larryhair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/larrymouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/lisaeyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/lisahair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/lisamouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/michaeleyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/michaelhair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/michaelmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/philipeyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/philiphair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/philipmouth.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/scotteyes.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/scotthair.jpg",
                "com/sun/swingset3/demos/combobox/resources/images/scottmouth.jpg"
                }
)
public class ComboBoxDemo extends JPanel implements ActionListener {
    private static final Dimension VGAP15 = new Dimension(1, 15);
    private static final Dimension HGAP20 = new Dimension(20, 1);
    private static final Dimension VGAP20 = new Dimension(1, 20);
    private static final Dimension HGAP30 = new Dimension(30, 1);
    private static final Dimension VGAP30 = new Dimension(1, 30);

    private final ResourceManager resourceManager = new ResourceManager(this.getClass());

    private Face face;
    private JLabel faceLabel;

    private JComboBox hairCB;
    private JComboBox eyesCB;
    private JComboBox mouthCB;

    private JComboBox presetCB;

    private final Map<String, Object> parts = new HashMap<String, Object>();

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(ComboBoxDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ComboBoxDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * ComboBoxDemo Constructor
     */
    public ComboBoxDemo() {
        createComboBoxDemo();
    }

    private void createComboBoxDemo() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

        add(Box.createRigidArea(VGAP20));
        add(innerPanel);
        add(Box.createRigidArea(VGAP20));

        innerPanel.add(Box.createRigidArea(HGAP20));

        // Create a panel to hold buttons
        JPanel comboBoxPanel = new JPanel() {
            public Dimension getMaximumSize() {
                return new Dimension(getPreferredSize().width, super.getMaximumSize().height);
            }
        };
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS));

        comboBoxPanel.add(Box.createRigidArea(VGAP15));

        JLabel l = (JLabel) comboBoxPanel.add(new JLabel(resourceManager.getString("ComboBoxDemo.presets")));
        l.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        presetCB = (JComboBox) comboBoxPanel.add(createPresetComboBox());
        presetCB.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        l.setLabelFor(presetCB);
        comboBoxPanel.add(Box.createRigidArea(VGAP30));

        l = (JLabel) comboBoxPanel.add(new JLabel(resourceManager.getString("ComboBoxDemo.hair_description")));
        l.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        hairCB = (JComboBox) comboBoxPanel.add(createHairComboBox());
        hairCB.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        l.setLabelFor(hairCB);
        comboBoxPanel.add(Box.createRigidArea(VGAP15));

        l = (JLabel) comboBoxPanel.add(new JLabel(resourceManager.getString("ComboBoxDemo.eyes_description")));
        l.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        eyesCB = (JComboBox) comboBoxPanel.add(createEyesComboBox());
        eyesCB.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        l.setLabelFor(eyesCB);
        comboBoxPanel.add(Box.createRigidArea(VGAP15));

        l = (JLabel) comboBoxPanel.add(new JLabel(resourceManager.getString("ComboBoxDemo.mouth_description")));
        l.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        mouthCB = (JComboBox) comboBoxPanel.add(createMouthComboBox());
        mouthCB.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        l.setLabelFor(mouthCB);
        comboBoxPanel.add(Box.createRigidArea(VGAP15));

        // Fill up the remaining space
        comboBoxPanel.add(new JPanel(new BorderLayout()));

        // Create and place the Face.

        face = new Face();
        JPanel facePanel = new JPanel();
        facePanel.setLayout(new BorderLayout());
        facePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        faceLabel = new JLabel(face);
        facePanel.add(faceLabel, BorderLayout.CENTER);
        // Indicate that the face panel is controlled by the hair, eyes and 
        // mouth combo boxes.
        Object[] controlledByObjects = new Object[3];
        controlledByObjects[0] = hairCB;
        controlledByObjects[1] = eyesCB;
        controlledByObjects[2] = mouthCB;
        AccessibleRelation controlledByRelation =
                new AccessibleRelation(AccessibleRelation.CONTROLLED_BY_PROPERTY,
                        controlledByObjects);
        facePanel.getAccessibleContext().getAccessibleRelationSet().add(controlledByRelation);

        // Indicate that the hair, eyes and mouth combo boxes are controllers
        // for the face panel.
        AccessibleRelation controllerForRelation =
                new AccessibleRelation(AccessibleRelation.CONTROLLER_FOR_PROPERTY,
                        facePanel);
        hairCB.getAccessibleContext().getAccessibleRelationSet().add(controllerForRelation);
        eyesCB.getAccessibleContext().getAccessibleRelationSet().add(controllerForRelation);
        mouthCB.getAccessibleContext().getAccessibleRelationSet().add(controllerForRelation);

        // add buttons and image panels to inner panel
        innerPanel.add(comboBoxPanel);
        innerPanel.add(Box.createRigidArea(HGAP30));
        innerPanel.add(facePanel);
        innerPanel.add(Box.createRigidArea(HGAP20));

        // load up the face parts 
        addFace("brent", resourceManager.getString("ComboBoxDemo.brent"));
        addFace("georges", resourceManager.getString("ComboBoxDemo.georges"));
        addFace("hans", resourceManager.getString("ComboBoxDemo.hans"));
        addFace("howard", resourceManager.getString("ComboBoxDemo.howard"));
        addFace("james", resourceManager.getString("ComboBoxDemo.james"));
        addFace("jeff", resourceManager.getString("ComboBoxDemo.jeff"));
        addFace("jon", resourceManager.getString("ComboBoxDemo.jon"));
        addFace("lara", resourceManager.getString("ComboBoxDemo.lara"));
        addFace("larry", resourceManager.getString("ComboBoxDemo.larry"));
        addFace("lisa", resourceManager.getString("ComboBoxDemo.lisa"));
        addFace("michael", resourceManager.getString("ComboBoxDemo.michael"));
        addFace("philip", resourceManager.getString("ComboBoxDemo.philip"));
        addFace("scott", resourceManager.getString("ComboBoxDemo.scott"));

        // set the default face
        presetCB.setSelectedIndex(0);
    }

    private void addFace(String name, String i18n_name) {
        ImageIcon i;
        String i18n_hair = resourceManager.getString("ComboBoxDemo.hair");
        String i18n_eyes = resourceManager.getString("ComboBoxDemo.eyes");
        String i18n_mouth = resourceManager.getString("ComboBoxDemo.mouth");

        parts.put(i18n_name, name); // i18n name lookup
        parts.put(name, i18n_name); // reverse name lookup

        i = resourceManager.createImageIcon(name + "hair.jpg", i18n_name + i18n_hair);
        parts.put(name + "hair", i);

        i = resourceManager.createImageIcon(name + "eyes.jpg", i18n_name + i18n_eyes);
        parts.put(name + "eyes", i);

        i = resourceManager.createImageIcon(name + "mouth.jpg", i18n_name + i18n_mouth);
        parts.put(name + "mouth", i);
    }

    private JComboBox createHairComboBox() {
        JComboBox cb = new JComboBox();
        fillComboBox(cb);
        cb.addActionListener(this);
        return cb;
    }

    private JComboBox createEyesComboBox() {
        JComboBox cb = new JComboBox();
        fillComboBox(cb);
        cb.addActionListener(this);
        return cb;
    }

    private JComboBox createMouthComboBox() {
        JComboBox cb = new JComboBox();
        fillComboBox(cb);
        cb.addActionListener(this);
        return cb;
    }

    private JComboBox createPresetComboBox() {
        JComboBox cb = new JComboBox();
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset1"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset2"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset3"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset4"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset5"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset6"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset7"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset8"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset9"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.preset10"));
        cb.addActionListener(this);
        return cb;
    }

    private void fillComboBox(JComboBox cb) {
        cb.addItem(resourceManager.getString("ComboBoxDemo.brent"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.georges"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.hans"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.howard"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.james"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.jeff"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.jon"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.lara"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.larry"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.lisa"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.michael"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.philip"));
        cb.addItem(resourceManager.getString("ComboBoxDemo.scott"));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hairCB) {
            String name = (String) parts.get((String) hairCB.getSelectedItem());
            face.setHair((ImageIcon) parts.get(name + "hair"));
            faceLabel.repaint();
        } else if (e.getSource() == eyesCB) {
            String name = (String) parts.get((String) eyesCB.getSelectedItem());
            face.setEyes((ImageIcon) parts.get(name + "eyes"));
            faceLabel.repaint();
        } else if (e.getSource() == mouthCB) {
            String name = (String) parts.get((String) mouthCB.getSelectedItem());
            face.setMouth((ImageIcon) parts.get(name + "mouth"));
            faceLabel.repaint();
        } else if (e.getSource() == presetCB) {
            String hair = null;
            String eyes = null;
            String mouth = null;
            switch (presetCB.getSelectedIndex()) {
                case 0:
                    hair = (String) parts.get("philip");
                    eyes = (String) parts.get("howard");
                    mouth = (String) parts.get("jeff");
                    break;
                case 1:
                    hair = (String) parts.get("jeff");
                    eyes = (String) parts.get("larry");
                    mouth = (String) parts.get("philip");
                    break;
                case 2:
                    hair = (String) parts.get("howard");
                    eyes = (String) parts.get("scott");
                    mouth = (String) parts.get("hans");
                    break;
                case 3:
                    hair = (String) parts.get("philip");
                    eyes = (String) parts.get("jeff");
                    mouth = (String) parts.get("hans");
                    break;
                case 4:
                    hair = (String) parts.get("brent");
                    eyes = (String) parts.get("jon");
                    mouth = (String) parts.get("scott");
                    break;
                case 5:
                    hair = (String) parts.get("lara");
                    eyes = (String) parts.get("larry");
                    mouth = (String) parts.get("lisa");
                    break;
                case 6:
                    hair = (String) parts.get("james");
                    eyes = (String) parts.get("philip");
                    mouth = (String) parts.get("michael");
                    break;
                case 7:
                    hair = (String) parts.get("philip");
                    eyes = (String) parts.get("lisa");
                    mouth = (String) parts.get("brent");
                    break;
                case 8:
                    hair = (String) parts.get("james");
                    eyes = (String) parts.get("philip");
                    mouth = (String) parts.get("jon");
                    break;
                case 9:
                    hair = (String) parts.get("lara");
                    eyes = (String) parts.get("jon");
                    mouth = (String) parts.get("scott");
                    break;
            }
            if (hair != null) {
                hairCB.setSelectedItem(hair);
                eyesCB.setSelectedItem(eyes);
                mouthCB.setSelectedItem(mouth);
                faceLabel.repaint();
            }
        }
    }

    private static class Face implements Icon {
        private ImageIcon hair;
        private ImageIcon eyes;
        private ImageIcon mouth;

        void setHair(ImageIcon i) {
            hair = i;
        }

        void setEyes(ImageIcon i) {
            eyes = i;
        }

        void setMouth(ImageIcon i) {
            mouth = i;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            int height = y;
            x = c.getWidth() / 2 - getIconWidth() / 2;

            if (hair != null) {
                hair.paintIcon(c, g, x, height);
                height += hair.getIconHeight();
            }

            if (eyes != null) {
                eyes.paintIcon(c, g, x, height);
                height += eyes.getIconHeight();
            }

            if (mouth != null) {
                mouth.paintIcon(c, g, x, height);
            }
        }

        public int getIconWidth() {
            return 344;
        }

        public int getIconHeight() {
            return 455;
        }
    }
}


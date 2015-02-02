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

package com.sun.swingset3.demos.togglebutton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.sun.swingset3.demos.ResourceManager;

/*
 * The LayoutControlPanel contains controls for setting an 
 * AbstractButton's horizontal and vertical text position and 
 * horizontal and vertical alignment.
 */

public class LayoutControlPanel extends JPanel implements SwingConstants {
    private static final Dimension VGAP20 = new Dimension(1, 20);

    private final boolean absolutePositions;
    private ToggleButtonDemo demo = null;

    // private ComponentOrientChanger componentOrientChanger = null;

    LayoutControlPanel(ToggleButtonDemo demo, ResourceManager resourceManager) {
        this.demo = demo;

        // this.componentOrientationChanger = componentOrientationChanger;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);

        JLabel l;

        // If SwingSet has a ComponentOrientationChanger, then include control
        // for choosing between absolute and relative positioning.  This will
        // only happen when we're running on JDK 1.2 or above.
        //
        // if(componentOrientationChanger != null ) {
        //     l = new JLabel("Positioning:");
        //     add(l);
        //
        //    ButtonGroup group = new ButtonGroup();
        //    PositioningListener positioningListener = new PositioningListener();
        //    JRadioButton absolutePos = new JRadioButton("Absolute");
        //    absolutePos.setMnemonic('a');
        //    absolutePos.setToolTipText("Text/Content positioning is independant of line direction");
        //    group.add(absolutePos);
        //    absolutePos.addItemListener(positioningListener);
        //    add(absolutePos);
        //
        //    JRadioButton relativePos = new JRadioButton("Relative");
        //    relativePos.setMnemonic('r');
        //    relativePos.setToolTipText("Text/Content positioning depends on line direction.");
        //    group.add(relativePos);
        //    relativePos.addItemListener(positioningListener);
        //    add(relativePos);
        //
        //    add(Box.createRigidArea(demo.VGAP20));
        //
        //    absolutePositions = false;
        //    relativePos.setSelected(true);
        //
        //    componentOrientationChanger.addActionListener( new OrientationChangeListener() );
        //} else {
        absolutePositions = true;
        //}

        DirectionPanel textPosition = new DirectionPanel(true, "E", new TextPositionListener());
        DirectionPanel labelAlignment = new DirectionPanel(true, "C", new LabelAlignmentListener());

        // Make sure the controls' text position and label alignment match
        // the initial value of the associated direction panel.
        for (JComponent control : demo.getCurrentControls()) {
            setPosition(control, RIGHT, CENTER);
            setAlignment(control, CENTER, CENTER);
        }

        l = new JLabel(resourceManager.getString("LayoutControlPanel.textposition_label"));
        add(l);
        add(textPosition);

        add(Box.createRigidArea(VGAP20));

        l = new JLabel(resourceManager.getString("LayoutControlPanel.contentalignment_label"));
        add(l);
        add(labelAlignment);

        add(Box.createGlue());
    }

    // Text Position Listener
    private class TextPositionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JRadioButton rb = (JRadioButton) e.getSource();
            if (!rb.isSelected()) {
                return;
            }
            String cmd = rb.getActionCommand();
            int hPos, vPos;
            if (cmd.equals("NW")) {
                hPos = LEFT;
                vPos = TOP;
            } else if (cmd.equals("N")) {
                hPos = CENTER;
                vPos = TOP;
            } else if (cmd.equals("NE")) {
                hPos = RIGHT;
                vPos = TOP;
            } else if (cmd.equals("W")) {
                hPos = LEFT;
                vPos = CENTER;
            } else if (cmd.equals("C")) {
                hPos = CENTER;
                vPos = CENTER;
            } else if (cmd.equals("E")) {
                hPos = RIGHT;
                vPos = CENTER;
            } else if (cmd.equals("SW")) {
                hPos = LEFT;
                vPos = BOTTOM;
            } else if (cmd.equals("S")) {
                hPos = CENTER;
                vPos = BOTTOM;
            } else /*if(cmd.equals("SE"))*/ {
                hPos = RIGHT;
                vPos = BOTTOM;
            }
            for (JComponent control : demo.getCurrentControls()) {
                setPosition(control, hPos, vPos);
            }
            demo.invalidate();
            demo.validate();
            demo.repaint();
        }
    }

    // Label Alignment Listener
    private class LabelAlignmentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JRadioButton rb = (JRadioButton) e.getSource();
            if (!rb.isSelected()) {
                return;
            }
            String cmd = rb.getActionCommand();
            int hPos, vPos;
            if (cmd.equals("NW")) {
                hPos = LEFT;
                vPos = TOP;
            } else if (cmd.equals("N")) {
                hPos = CENTER;
                vPos = TOP;
            } else if (cmd.equals("NE")) {
                hPos = RIGHT;
                vPos = TOP;
            } else if (cmd.equals("W")) {
                hPos = LEFT;
                vPos = CENTER;
            } else if (cmd.equals("C")) {
                hPos = CENTER;
                vPos = CENTER;
            } else if (cmd.equals("E")) {
                hPos = RIGHT;
                vPos = CENTER;
            } else if (cmd.equals("SW")) {
                hPos = LEFT;
                vPos = BOTTOM;
            } else if (cmd.equals("S")) {
                hPos = CENTER;
                vPos = BOTTOM;
            } else /*if(cmd.equals("SE"))*/ {
                hPos = RIGHT;
                vPos = BOTTOM;
            }
            for (JComponent control : demo.getCurrentControls()) {
                setAlignment(control, hPos, vPos);
                control.invalidate();
            }
            demo.invalidate();
            demo.validate();
            demo.repaint();
        }
    }

    // Position
    void setPosition(Component c, int hPos, int vPos) {
        boolean ltr = c.getComponentOrientation().isLeftToRight();
        if (absolutePositions) {
            if (hPos == LEADING) {
                hPos = ltr ? LEFT : RIGHT;
            } else if (hPos == TRAILING) {
                hPos = ltr ? RIGHT : LEFT;
            }
        } else {
            if (hPos == LEFT) {
                hPos = ltr ? LEADING : TRAILING;
            } else if (hPos == RIGHT) {
                hPos = ltr ? TRAILING : LEADING;
            }
        }
        if (c instanceof AbstractButton) {
            AbstractButton x = (AbstractButton) c;
            x.setHorizontalTextPosition(hPos);
            x.setVerticalTextPosition(vPos);
        } else if (c instanceof JLabel) {
            JLabel x = (JLabel) c;
            x.setHorizontalTextPosition(hPos);
            x.setVerticalTextPosition(vPos);
        }
    }

    void setAlignment(Component c, int hPos, int vPos) {
        boolean ltr = c.getComponentOrientation().isLeftToRight();
        if (absolutePositions) {
            if (hPos == LEADING) {
                hPos = ltr ? LEFT : RIGHT;
            } else if (hPos == TRAILING) {
                hPos = ltr ? RIGHT : LEFT;
            }
        } else {
            if (hPos == LEFT) {
                hPos = ltr ? LEADING : TRAILING;
            } else if (hPos == RIGHT) {
                hPos = ltr ? TRAILING : LEADING;
            }
        }
        if (c instanceof AbstractButton) {
            AbstractButton x = (AbstractButton) c;
            x.setHorizontalAlignment(hPos);
            x.setVerticalAlignment(vPos);
        } else if (c instanceof JLabel) {
            JLabel x = (JLabel) c;
            x.setHorizontalAlignment(hPos);
            x.setVerticalAlignment(vPos);
        }
    }
}

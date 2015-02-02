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

import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

/**
 * @version 1.8 11/17/05
 * @author Jeff Dinkins
 * @author Chester Rose
 * @author Brian Beck
 */ 

public class DirectionPanel extends JPanel {

    private final ButtonGroup group;

    public DirectionPanel(boolean enable, String selection, ActionListener l) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentY(TOP_ALIGNMENT);
        setAlignmentX(LEFT_ALIGNMENT);

        Box firstThree = Box.createHorizontalBox();
        Box secondThree = Box.createHorizontalBox();
        Box thirdThree = Box.createHorizontalBox();

        if (!enable) {
            selection = "None";
        }

        group = new ButtonGroup();
        DirectionButton b;
        b = (DirectionButton) firstThree.add(new DirectionButton(tl_dot, tldn_dot, "NW", "Sets the orientation to the North-West", l, group, selection.equals("NW")));
        b.setEnabled(enable);
        b = (DirectionButton) firstThree.add(new DirectionButton(tm_dot, tmdn_dot, "N", "Sets the orientation to the North", l, group, selection.equals("N")));
        b.setEnabled(enable);
        b = (DirectionButton) firstThree.add(new DirectionButton(tr_dot, trdn_dot, "NE", "Sets the orientation to the North-East", l, group, selection.equals("NE")));
        b.setEnabled(enable);
        b = (DirectionButton) secondThree.add(new DirectionButton(ml_dot, mldn_dot, "W", "Sets the orientation to the West", l, group, selection.equals("W")));
        b.setEnabled(enable);
        b = (DirectionButton) secondThree.add(new DirectionButton(c_dot, cdn_dot, "C", "Sets the orientation to the Center", l, group, selection.equals("C")));
        b.setEnabled(enable);
        b = (DirectionButton) secondThree.add(new DirectionButton(mr_dot, mrdn_dot, "E", "Sets the orientation to the East", l, group, selection.equals("E")));
        b.setEnabled(enable);
        b = (DirectionButton) thirdThree.add(new DirectionButton(bl_dot, bldn_dot, "SW", "Sets the orientation to the South-West", l, group, selection.equals("SW")));
        b.setEnabled(enable);
        b = (DirectionButton) thirdThree.add(new DirectionButton(bm_dot, bmdn_dot, "S", "Sets the orientation to the South", l, group, selection.equals("S")));
        b.setEnabled(enable);
        b = (DirectionButton) thirdThree.add(new DirectionButton(br_dot, brdn_dot, "SE", "Sets the orientation to the South-East", l, group, selection.equals("SE")));
        b.setEnabled(enable);

        add(firstThree);
        add(secondThree);
        add(thirdThree);
    }

    // Chester's way cool layout buttons 
    private final ImageIcon bl_dot = loadImageIcon("bl.gif", "bottom left layout button");
    private final ImageIcon bldn_dot = loadImageIcon("bldn.gif", "selected bottom left layout button");
    private final ImageIcon bm_dot = loadImageIcon("bm.gif", "bottom middle layout button");
    private final ImageIcon bmdn_dot = loadImageIcon("bmdn.gif", "selected bottom middle layout button");
    private final ImageIcon br_dot = loadImageIcon("br.gif", "bottom right layout button");
    private final ImageIcon brdn_dot = loadImageIcon("brdn.gif", "selected bottom right layout button");
    private final ImageIcon c_dot = loadImageIcon("c.gif", "center layout button");
    private final ImageIcon cdn_dot = loadImageIcon("cdn.gif", "selected center layout button");
    private final ImageIcon ml_dot = loadImageIcon("ml.gif", "middle left layout button");
    private final ImageIcon mldn_dot = loadImageIcon("mldn.gif", "selected middle left layout button");
    private final ImageIcon mr_dot = loadImageIcon("mr.gif", "middle right layout button");
    private final ImageIcon mrdn_dot = loadImageIcon("mrdn.gif", "selected middle right layout button");
    private final ImageIcon tl_dot = loadImageIcon("tl.gif", "top left layout button");
    private final ImageIcon tldn_dot = loadImageIcon("tldn.gif", "selected top left layout button");
    private final ImageIcon tm_dot = loadImageIcon("tm.gif", "top middle layout button");
    private final ImageIcon tmdn_dot = loadImageIcon("tmdn.gif", "selected top middle layout button");
    private final ImageIcon tr_dot = loadImageIcon("tr.gif", "top right layout button");
    private final ImageIcon trdn_dot = loadImageIcon("trdn.gif", "selected top right layout button");

    private ImageIcon loadImageIcon(String filename, String description) {
        String path = "resources/images/" + filename;
        return new ImageIcon(getClass().getResource(path), description);
    }


    private static class DirectionButton extends JRadioButton {

        /**
         * A layout direction button
         */
        public DirectionButton(Icon icon, Icon downIcon, String direction,
                String description, ActionListener l,
                ButtonGroup group, boolean selected) {
            super();
            this.addActionListener(l);
            setFocusPainted(false);
            setHorizontalTextPosition(CENTER);
            group.add(this);
            setIcon(icon);
            setSelectedIcon(downIcon);
            setActionCommand(direction);
            getAccessibleContext().setAccessibleName(direction);
            getAccessibleContext().setAccessibleDescription(description);
            setSelected(selected);
        }

        public boolean isFocusTraversable() {
            return false;
        }

        public void setBorder(Border b) {
        }
    }
}

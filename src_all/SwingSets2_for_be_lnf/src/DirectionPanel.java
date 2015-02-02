/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * DirectionPanel.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)DirectionPanel.java	1.8 05/11/17
 */

import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;


// TODO: Auto-generated Javadoc
/**
 * The Class DirectionPanel.
 *
 * @version 1.8 11/17/05
 * @author Jeff Dinkins
 * @author Chester Rose
 * @author Brian Beck
 */ 

public class DirectionPanel extends JPanel {

    /** The group. */
    private ButtonGroup group;

    /**
     * Instantiates a new direction panel.
     *
     * @param enable the enable
     * @param selection the selection
     * @param l the l
     */
    public DirectionPanel(boolean enable, String selection, ActionListener l) {
    this.setOpaque(false);//* add by jb2011：不需要使用默认色填充背景，否则与白色主面板不搭
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setAlignmentY(TOP_ALIGNMENT);
	setAlignmentX(LEFT_ALIGNMENT);

	Box firstThree = Box.createHorizontalBox();
	Box secondThree = Box.createHorizontalBox();
	Box thirdThree = Box.createHorizontalBox();

	if(!enable) {
	    selection = "None";
	}

        group = new ButtonGroup();
	DirectionButton b;
	b = (DirectionButton) firstThree.add(new DirectionButton(  tl_dot, tldn_dot, "NW", "Sets the orientation to the North-West", l, group, selection.equals("NW")));
	b.setEnabled(enable);
	b = (DirectionButton) firstThree.add(new DirectionButton(  tm_dot, tmdn_dot, "N",  "Sets the orientation to the North", l, group, selection.equals("N")));
	b.setEnabled(enable);
	b = (DirectionButton) firstThree.add(new DirectionButton(  tr_dot, trdn_dot, "NE", "Sets the orientation to the North-East", l, group, selection.equals("NE")));
	b.setEnabled(enable);
	b = (DirectionButton) secondThree.add(new DirectionButton( ml_dot, mldn_dot, "W", "Sets the orientation to the West", l, group, selection.equals("W")));
	b.setEnabled(enable);
	b = (DirectionButton) secondThree.add(new DirectionButton( c_dot,  cdn_dot,  "C", "Sets the orientation to the Center", l, group, selection.equals("C")));
	b.setEnabled(enable);
	b = (DirectionButton) secondThree.add(new DirectionButton( mr_dot, mrdn_dot, "E", "Sets the orientation to the East", l, group, selection.equals("E")));
	b.setEnabled(enable);
	b = (DirectionButton) thirdThree.add(new DirectionButton(  bl_dot, bldn_dot, "SW", "Sets the orientation to the South-West", l, group, selection.equals("SW")));
	b.setEnabled(enable);
	b = (DirectionButton) thirdThree.add(new DirectionButton(  bm_dot, bmdn_dot, "S", "Sets the orientation to the South", l, group, selection.equals("S")));
	b.setEnabled(enable);
	b = (DirectionButton) thirdThree.add(new DirectionButton(  br_dot, brdn_dot, "SE", "Sets the orientation to the South-East", l, group, selection.equals("SE")));
	b.setEnabled(enable);

	add(firstThree);
	add(secondThree);
	add(thirdThree);	
    }

    /**
     * Gets the selection.
     *
     * @return the selection
     */
    public String getSelection() {
        return group.getSelection().getActionCommand();
    }

    /**
     * Sets the selection.
     *
     * @param selection the new selection
     */
    public void setSelection( String selection  ) {
        Enumeration e = group.getElements();
        while( e.hasMoreElements() ) {
            JRadioButton b = (JRadioButton)e.nextElement();
            if( b.getActionCommand().equals(selection) ) {
               b.setSelected(true);
            }
        }
    }
    
    // Chester's way cool layout buttons 
    /** The bl_dot. */
    public ImageIcon bl_dot   = loadImageIcon("bl.gif","bottom left layout button");
    
    /** The bldn_dot. */
    public ImageIcon bldn_dot = loadImageIcon("bldn.gif","selected bottom left layout button");
    
    /** The bm_dot. */
    public ImageIcon bm_dot   = loadImageIcon("bm.gif","bottom middle layout button");
    
    /** The bmdn_dot. */
    public ImageIcon bmdn_dot = loadImageIcon("bmdn.gif","selected bottom middle layout button");
    
    /** The br_dot. */
    public ImageIcon br_dot   = loadImageIcon("br.gif","bottom right layout button");
    
    /** The brdn_dot. */
    public ImageIcon brdn_dot = loadImageIcon("brdn.gif","selected bottom right layout button");
    
    /** The c_dot. */
    public ImageIcon c_dot    = loadImageIcon("c.gif","center layout button");
    
    /** The cdn_dot. */
    public ImageIcon cdn_dot  = loadImageIcon("cdn.gif","selected center layout button");
    
    /** The ml_dot. */
    public ImageIcon ml_dot   = loadImageIcon("ml.gif","middle left layout button");
    
    /** The mldn_dot. */
    public ImageIcon mldn_dot = loadImageIcon("mldn.gif","selected middle left layout button");
    
    /** The mr_dot. */
    public ImageIcon mr_dot   = loadImageIcon("mr.gif","middle right layout button");
    
    /** The mrdn_dot. */
    public ImageIcon mrdn_dot = loadImageIcon("mrdn.gif","selected middle right layout button");
    
    /** The tl_dot. */
    public ImageIcon tl_dot   = loadImageIcon("tl.gif","top left layout button");
    
    /** The tldn_dot. */
    public ImageIcon tldn_dot = loadImageIcon("tldn.gif","selected top left layout button");
    
    /** The tm_dot. */
    public ImageIcon tm_dot   = loadImageIcon("tm.gif","top middle layout button");
    
    /** The tmdn_dot. */
    public ImageIcon tmdn_dot = loadImageIcon("tmdn.gif","selected top middle layout button");
    
    /** The tr_dot. */
    public ImageIcon tr_dot   = loadImageIcon("tr.gif","top right layout button");
    
    /** The trdn_dot. */
    public ImageIcon trdn_dot = loadImageIcon("trdn.gif","selected top right layout button");
    
    /**
     * Load image icon.
     *
     * @param filename the filename
     * @param description the description
     * @return the image icon
     */
    public ImageIcon loadImageIcon(String filename, String description) {
	String path = "/resources/images/buttons/" + filename;
	return new ImageIcon(getClass().getResource(path), description);
    }

    
    /**
     * The Class DirectionButton.
     */
    public class DirectionButton extends JRadioButton {
        
        /**
         * A layout direction button.
         *
         * @param icon the icon
         * @param downIcon the down icon
         * @param direction the direction
         * @param description the description
         * @param l the l
         * @param group the group
         * @param selected the selected
         */
        public DirectionButton(Icon icon, Icon downIcon, String direction,
                               String description, ActionListener l, 
                               ButtonGroup group, boolean selected)
        {
            super();
            this.addActionListener(l);
            this.setOpaque(false);//* add by jb2011：不需要使用默认色填充背景，否则与白色主面板不搭
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

        /* (non-Javadoc)
         * @see java.awt.Component#isFocusTraversable()
         */
        public boolean isFocusTraversable() {
            return false;
        }

        /* (non-Javadoc)
         * @see javax.swing.JComponent#setBorder(javax.swing.border.Border)
         */
        public void setBorder(Border b) {
        }
    }
}

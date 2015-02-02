/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * ToolTipDemo.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)ToolTipDemo.java	1.9 05/11/17
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * ToolTip Demo.
 *
 * @version 1.9 11/17/05
 * @author Jeff Dinkins
 */
public class ToolTipDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	ToolTipDemo demo = new ToolTipDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "提示";
    };

    /**
     * ToolTipDemo Constructor.
     *
     * @param swingset the swingset
     */
    public ToolTipDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "ToolTipDemo"
			, "toolbar/ToolTip.gif");

	// Set the layout manager.
	JPanel p = getDemoPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	p.setBackground(Color.white);

	// Create a Cow to put in the center of the panel.
	Cow cow = new Cow();
	cow.getAccessibleContext().setAccessibleName(getString("ToolTipDemo.accessible_cow"));

	// Set the tooltip text. Note, for fun, we also set more tooltip text
	// descriptions for the cow down below in the Cow.contains() method.
	cow.setToolTipText(getString("ToolTipDemo.cow"));

	// Add the cow midway down the panel
	p.add(Box.createRigidArea(new Dimension(1, 150)));
	p.add(cow);
    }


    /**
     * The Class Cow.
     */
    class Cow extends JLabel {
	
	/** The cowgon. */
	Polygon cowgon = new Polygon();
	
	/**
	 * Instantiates a new cow.
	 */
	public Cow() {
	    super(createImageIcon("tooltip/cow.gif", getString("ToolTipDemo.bessie")));
	    setAlignmentX(CENTER_ALIGNMENT);

	    // Set polygon points that define the outline of the cow.
	    cowgon.addPoint(3,20);    cowgon.addPoint(44,4);
	    cowgon.addPoint(79,15);   cowgon.addPoint(130,11);
	    cowgon.addPoint(252,5);   cowgon.addPoint(181,17);
	    cowgon.addPoint(301,45);  cowgon.addPoint(292,214);
	    cowgon.addPoint(269,209); cowgon.addPoint(266,142);
	    cowgon.addPoint(250,161); cowgon.addPoint(235,218);
	    cowgon.addPoint(203,206); cowgon.addPoint(215,137);
	    cowgon.addPoint(195,142); cowgon.addPoint(143,132);
	    cowgon.addPoint(133,189); cowgon.addPoint(160,200);
	    cowgon.addPoint(97,196);  cowgon.addPoint(107,182);
	    cowgon.addPoint(118,185); cowgon.addPoint(110,144);
	    cowgon.addPoint(59,77);   cowgon.addPoint(30,82);
	    cowgon.addPoint(30,35);   cowgon.addPoint(15,36);
	}
	
	/** The moo. */
	boolean moo = false;
	
	/** The milk. */
	boolean milk = false;
	
	/** The tail. */
	boolean tail = false;

	// Use the contains method to set the tooltip text depending
	// on where the mouse is over the cow.
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#contains(int, int)
	 */
	public boolean contains(int x, int y) {
	    if(!cowgon.contains(new Point(x, y))) {
		return false;
	    }
	    
	    if((x > 30) && (x < 60) && (y > 60) && (y < 85)) {
		if(!moo) {
		    setToolTipText("<html><center><font color=blue size=+2>" +
				   getString("ToolTipDemo.moo") + "</font></center></html>");
		    moo = true;
		    milk = false;
		    tail = false;
		}
	    } else if((x > 150) && (x < 260) && (y > 90) && (y < 145)) {
		if(!milk) {
		    setToolTipText("<html><center><font face=AvantGarde size=+1 color=white>" +
				   getString("ToolTipDemo.got_milk") + "</font></center></html>");
		    milk = true;
		    moo = false;
		    tail = false;
		}
	    } else if((x > 280) && (x < 300) && (y > 20) && (y < 175)) {
		if(!tail) {
		    setToolTipText("<html><em><b>" + getString("ToolTipDemo.tail") + "</b></em></html>");
		    tail = true;
		    moo = false;
		    milk = false;
		}
	    } else if(moo || milk || tail) {
		setToolTipText(getString("ToolTipDemo.tooltip_features"));
		moo = false;
		tail = false;
		milk = false;
	    }

	    return true;
	}
    }
    
}

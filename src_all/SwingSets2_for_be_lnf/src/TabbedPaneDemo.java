/*
 * @(#)TabbedPaneDemo.java	1.11 05/11/17
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)TabbedPaneDemo.java	1.11 05/11/17
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SingleSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// TODO: Auto-generated Javadoc
/**
 * JTabbedPane Demo.
 *
 * @version 1.11 11/17/05
 * @author Jeff Dinkins
 */
public class TabbedPaneDemo extends DemoModule implements ActionListener {
    
    /** The spin. */
    HeadSpin spin;

    /** The tabbedpane. */
    JTabbedPane tabbedpane;

    /** The group. */
    ButtonGroup group;

    /** The top. */
    JRadioButton top;
    
    /** The bottom. */
    JRadioButton bottom;
    
    /** The left. */
    JRadioButton left;
    
    /** The right. */
    JRadioButton right;

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	TabbedPaneDemo demo = new TabbedPaneDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "TabbedPane";
    };

    /**
     * TabbedPaneDemo Constructor.
     *
     * @param swingset the swingset
     */
    public TabbedPaneDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "TabbedPaneDemo"
			, "toolbar/JTabbedPane.gif");

	// create tab position controls
	JPanel tabControls = new JPanel();
	tabControls.add(new JLabel(getString("TabbedPaneDemo.label")));
	top    = (JRadioButton) tabControls.add(new JRadioButton(getString("TabbedPaneDemo.top")));
	left   = (JRadioButton) tabControls.add(new JRadioButton(getString("TabbedPaneDemo.left")));
	bottom = (JRadioButton) tabControls.add(new JRadioButton(getString("TabbedPaneDemo.bottom")));
	right  = (JRadioButton) tabControls.add(new JRadioButton(getString("TabbedPaneDemo.right")));
	getDemoPanel().add(tabControls, BorderLayout.NORTH);

	group = new ButtonGroup();
	group.add(top);
	group.add(bottom);
	group.add(left);
	group.add(right);

	top.setSelected(true);

	top.addActionListener(this);
	bottom.addActionListener(this);
	left.addActionListener(this);
	right.addActionListener(this);

	// create tab 
	tabbedpane = new JTabbedPane();
	getDemoPanel().add(tabbedpane, BorderLayout.CENTER);

	String name = getString("TabbedPaneDemo.laine");
	JLabel pix = new JLabel(createImageIcon("tabbedpane/laine.jpg", name));
	tabbedpane.add(name, pix);

	name = getString("TabbedPaneDemo.ewan");
	pix = new JLabel(createImageIcon("tabbedpane/ewan.jpg", name));
	tabbedpane.add(name, pix);

	name = getString("TabbedPaneDemo.hania");
	pix = new JLabel(createImageIcon("tabbedpane/hania.jpg", name));
	tabbedpane.add(name, pix);

	name = getString("TabbedPaneDemo.bounce");
	spin = new HeadSpin();
	tabbedpane.add(name, spin);
	
	
	tabbedpane.getModel().addChangeListener(
	   new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
		  SingleSelectionModel model = (SingleSelectionModel) e.getSource();
		  if(model.getSelectedIndex() == tabbedpane.getTabCount()-1) {
		      spin.go();
		  }
	      }
	   }
	);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == top) {
	    tabbedpane.setTabPlacement(JTabbedPane.TOP);
	} else if(e.getSource() == left) {
	    tabbedpane.setTabPlacement(JTabbedPane.LEFT);
	} else if(e.getSource() == bottom) {
	    tabbedpane.setTabPlacement(JTabbedPane.BOTTOM);
	} else if(e.getSource() == right) {
	    tabbedpane.setTabPlacement(JTabbedPane.RIGHT);
	}
    }

    /**
     * The Class HeadSpin.
     */
    class HeadSpin extends JComponent implements ActionListener {
	
	/** The animator. */
	javax.swing.Timer animator;
	
	/** The icon. */
	ImageIcon icon[] = new ImageIcon[6];

	/** The tmp scale. */
	int tmpScale;

	/** The Constant numImages. */
	final static int numImages = 6;

	/** The x. */
	double x[] = new double[numImages];
	
	/** The y. */
	double y[] = new double[numImages];

	/** The xh. */
	int xh[] = new int[numImages];
	
	/** The yh. */
	int yh[] = new int[numImages];

	/** The scale. */
	double scale[] = new double[numImages];

	/**
	 * Instantiates a new head spin.
	 */
	public HeadSpin() {
	    setBackground(Color.black);
	    icon[0] = createImageIcon("tabbedpane/ewan.gif", getString("TabbedPaneDemo.ewan"));
	    icon[1] = createImageIcon("tabbedpane/stephen.gif", getString("TabbedPaneDemo.stephen"));
	    icon[2] = createImageIcon("tabbedpane/david.gif", getString("TabbedPaneDemo.david"));
	    icon[3] = createImageIcon("tabbedpane/matthew.gif", getString("TabbedPaneDemo.matthew"));
	    icon[4] = createImageIcon("tabbedpane/blake.gif", getString("TabbedPaneDemo.blake"));
	    icon[5] = createImageIcon("tabbedpane/brooke.gif", getString("TabbedPaneDemo.brooke"));

	    /*
	    for(int i = 0; i < 6; i++) {
		x[i] = (double) rand.nextInt(500);
		y[i] = (double) rand.nextInt(500);
	    }
	    */
	}
	
	/**
	 * Go.
	 */
	public void go() {
	    animator = new javax.swing.Timer(22 + 22 + 22, this);
	    animator.start();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
	    g.setColor(getBackground());
	    g.fillRect(0, 0, getWidth(), getHeight());

	    for(int i = 0; i < numImages; i++) {
		if(x[i] > 3*i) {
		    nudge(i);
		    squish(g, icon[i], xh[i], yh[i], scale[i]);
		} else {
		    x[i] += .05;
		    y[i] += .05;
		}
	    }
	}

	/** The rand. */
	Random rand = new Random();

	/**
	 * Nudge.
	 *
	 * @param i the i
	 */
	public void nudge(int i) {
	    x[i] += (double) rand.nextInt(1000) / 8756;
	    y[i] += (double) rand.nextInt(1000) / 5432;
	    int tmpScale = (int) (Math.abs(Math.sin(x[i])) * 10);
	    scale[i] = (double) tmpScale / 10;
	    int nudgeX = (int) (((double) getWidth()/2) * .8);
	    int nudgeY = (int) (((double) getHeight()/2) * .60);
	    xh[i] = (int) (Math.sin(x[i]) * nudgeX) + nudgeX;
	    yh[i] = (int) (Math.sin(y[i]) * nudgeY) + nudgeY;
	}

	/**
	 * Squish.
	 *
	 * @param g the g
	 * @param icon the icon
	 * @param x the x
	 * @param y the y
	 * @param scale the scale
	 */
	public void squish(Graphics g, ImageIcon icon, int x, int y, double scale) {
	    if(isVisible()) {
		g.drawImage(icon.getImage(), x, y,
			    (int) (icon.getIconWidth()*scale),
			    (int) (icon.getIconHeight()*scale),
			    this);
	    } 
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    if(isVisible()) {
		repaint();
	    } else {
		animator.stop();
	    }
	}
    }
}


/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * InternalFrameDemo.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)InternalFrameDemo.java	1.16 05/11/17
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

// TODO: Auto-generated Javadoc
/**
 * Internal Frames Demo.
 *
 * @version 1.16 11/17/05
 * @author Jeff Dinkins
 */
public class InternalFrameDemo extends DemoModule {
    
    /** The window count. */
    int windowCount = 0;
    
    /** The desktop. */
    JDesktopPane desktop = null;

    /** The icon4. */
    ImageIcon icon1, icon2, icon3, icon4;
    
    /** The sm icon4. */
    ImageIcon smIcon1, smIcon2, smIcon3, smIcon4;

    /** The FIRS t_ fram e_ layer. */
    public Integer FIRST_FRAME_LAYER  = new Integer(1);
    
    /** The DEM o_ fram e_ layer. */
    public Integer DEMO_FRAME_LAYER   = new Integer(2);
    
    /** The PALETT e_ layer. */
    public Integer PALETTE_LAYER     = new Integer(3);

    /** The FRAM e0_ x. */
    public int FRAME0_X        = 15;
    
    /** The FRAM e0_ y. */
    public int FRAME0_Y        = 280;

    /** The FRAM e0_ width. */
    public int FRAME0_WIDTH    = 320;
    
    /** The FRAM e0_ height. */
    public int FRAME0_HEIGHT   = 230;

    /** The FRAM e_ width. */
    public int FRAME_WIDTH     = 225;
    
    /** The FRAM e_ height. */
    public int FRAME_HEIGHT    = 150;

    /** The PALETT e_ x. */
    public int PALETTE_X      = 375;
    
    /** The PALETT e_ y. */
    public int PALETTE_Y      = 20;

    /** The PALETT e_ width. */
    public int PALETTE_WIDTH  = 260;
    
    /** The PALETT e_ height. */
    public int PALETTE_HEIGHT = 260;

    /** The window resizable. */
    JCheckBox windowResizable   = null;
    
    /** The window closable. */
    JCheckBox windowClosable    = null;
    
    /** The window iconifiable. */
    JCheckBox windowIconifiable = null;
    
    /** The window maximizable. */
    JCheckBox windowMaximizable = null;

    /** The window title field. */
    JTextField windowTitleField = null;
    
    /** The window title label. */
    JLabel windowTitleLabel = null;


    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	InternalFrameDemo demo = new InternalFrameDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "内部窗";
    };

    /**
     * InternalFrameDemo Constructor.
     *
     * @param swingset the swingset
     */
    public InternalFrameDemo(SwingSet2 swingset) {
	super(swingset, "InternalFrameDemo"
			, "toolbar/JDesktop.gif");

	// preload all the icons we need for this demo
	icon1 = createImageIcon("ImageClub/misc/fish.gif", getString("InternalFrameDemo.fish"));
	icon2 = createImageIcon("ImageClub/misc/moon.gif", getString("InternalFrameDemo.moon"));
	icon3 = createImageIcon("ImageClub/misc/sun.gif",  getString("InternalFrameDemo.sun"));
	icon4 = createImageIcon("ImageClub/misc/cab.gif",  getString("InternalFrameDemo.cab"));

	smIcon1 = createImageIcon("ImageClub/misc/fish_small.gif", getString("InternalFrameDemo.fish"));
	smIcon2 = createImageIcon("ImageClub/misc/moon_small.gif", getString("InternalFrameDemo.moon"));
	smIcon3 = createImageIcon("ImageClub/misc/sun_small.gif",  getString("InternalFrameDemo.sun"));
	smIcon4 = createImageIcon("ImageClub/misc/cab_small.gif",  getString("InternalFrameDemo.cab"));

	// Create the desktop pane
	desktop = new JDesktopPane();
	getDemoPanel().add(desktop, BorderLayout.CENTER);

	// Create the "frame maker" palette
	createInternalFramePalette();

	// Create an initial internal frame to show
	JInternalFrame frame1 = createInternalFrame(icon1, FIRST_FRAME_LAYER, 1, 1);
	frame1.setBounds(FRAME0_X, FRAME0_Y, FRAME0_WIDTH, FRAME0_HEIGHT);

	// Create four more starter windows
	createInternalFrame(icon1, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
	createInternalFrame(icon3, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
	createInternalFrame(icon4, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
	createInternalFrame(icon2, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
    }



    /**
     * Create an internal frame and add a scrollable imageicon to it.
     *
     * @param icon the icon
     * @param layer the layer
     * @param width the width
     * @param height the height
     * @return the j internal frame
     */
    public JInternalFrame createInternalFrame(Icon icon, Integer layer, int width, int height) {
	JInternalFrame jif = new JInternalFrame();

	if(!windowTitleField.getText().equals(getString("InternalFrameDemo.frame_label"))) {
	    jif.setTitle(windowTitleField.getText() + "  ");
	} else {
	    jif = new JInternalFrame(getString("InternalFrameDemo.frame_label") + " " + windowCount + "  ");
	}

	// set properties
	jif.setClosable(windowClosable.isSelected());
	jif.setMaximizable(windowMaximizable.isSelected());
	jif.setIconifiable(windowIconifiable.isSelected());
	jif.setResizable(windowResizable.isSelected());

	jif.setBounds(20*(windowCount%10), 20*(windowCount%10), width, height);
	jif.setContentPane(new ImageScroller(this, icon, 0, windowCount));

	windowCount++;
	
	desktop.add(jif, layer);  

	// Set this internal frame to be selected

	try {
	    jif.setSelected(true);
	} catch (java.beans.PropertyVetoException e2) {
	}

	jif.show();

	return jif;
    }

    /**
     * Creates the internal frame palette.
     *
     * @return the j internal frame
     */
    public JInternalFrame createInternalFramePalette() {
	JInternalFrame palette = new JInternalFrame(
	    getString("InternalFrameDemo.palette_label")
	);
	//beautyEye_study外观下，本属性将失效(JInternalFrame.isPalett其实只在MetalLookAndFeel下有效果)
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
	b1.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
	b1.setForeground(Color.white);
	JButton b2 = new JButton(smIcon2);
	JButton b3 = new JButton(smIcon3);
	JButton b4 = new JButton(smIcon4);
	b4.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
	b4.setForeground(Color.white);

	// add frame maker actions
	b1.addActionListener(new ShowFrameAction(this, icon1));
	b2.addActionListener(new ShowFrameAction(this, icon2));
	b3.addActionListener(new ShowFrameAction(this, icon3));
	b4.addActionListener(new ShowFrameAction(this, icon4));

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
	p = new JPanel() {
	    Insets insets = new Insets(10,15,10,5);
	    public Insets getInsets() {
		return insets;
	    }
	};
	p.setLayout(new GridLayout(1,2));


        Box box = new Box(BoxLayout.Y_AXIS);
	windowResizable   = new JCheckBox(getString("InternalFrameDemo.resizable_label"), true);
	windowIconifiable = new JCheckBox(getString("InternalFrameDemo.iconifiable_label"), true);

        box.add(Box.createGlue());
	box.add(windowResizable);
	box.add(windowIconifiable);
        box.add(Box.createGlue());
        p.add(box);

        box = new Box(BoxLayout.Y_AXIS);
	windowClosable    = new JCheckBox(getString("InternalFrameDemo.closable_label"), true);
	windowMaximizable = new JCheckBox(getString("InternalFrameDemo.maximizable_label"), true);

        box.add(Box.createGlue());
	box.add(windowClosable);
	box.add(windowMaximizable);
        box.add(Box.createGlue());
        p.add(box);

	palette.getContentPane().add(p, BorderLayout.CENTER);


	// ************************************
	// *   Create Frame title textfield   *
	// ************************************
	p = new JPanel() {
	    Insets insets = new Insets(0,0,10,0);
	    public Insets getInsets() {
		return insets;
	    }
	};

	windowTitleField = new JTextField(getString("InternalFrameDemo.frame_label"));
	windowTitleLabel = new JLabel(getString("InternalFrameDemo.title_text_field_label"));

	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
	p.add(Box.createRigidArea(HGAP5));
	p.add(windowTitleLabel, BorderLayout.WEST);
	p.add(Box.createRigidArea(HGAP5));
	p.add(windowTitleField, BorderLayout.CENTER);
	p.add(Box.createRigidArea(HGAP5));

	palette.getContentPane().add(p, BorderLayout.SOUTH);

	palette.show();

	return palette;
    }


    /**
     * The Class ShowFrameAction.
     */
    class ShowFrameAction extends AbstractAction {
	
	/** The demo. */
	InternalFrameDemo demo;
	
	/** The icon. */
	Icon icon;
	
	
	/**
	 * Instantiates a new show frame action.
	 *
	 * @param demo the demo
	 * @param icon the icon
	 */
	public ShowFrameAction(InternalFrameDemo demo, Icon icon) {
	    this.demo = demo;
	    this.icon = icon;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    demo.createInternalFrame(icon,
				     getDemoFrameLayer(),
				     getFrameWidth(),
				     getFrameHeight()
	    );
	}
    }

    /**
     * Gets the frame width.
     *
     * @return the frame width
     */
    public int getFrameWidth() {
	return FRAME_WIDTH;
    }

    /**
     * Gets the frame height.
     *
     * @return the frame height
     */
    public int getFrameHeight() {
	return FRAME_HEIGHT;
    }

    /**
     * Gets the demo frame layer.
     *
     * @return the demo frame layer
     */
    public Integer getDemoFrameLayer() {
	return DEMO_FRAME_LAYER;
    }
    
    /**
     * The Class ImageScroller.
     */
    class ImageScroller extends JScrollPane {
	
	/**
	 * Instantiates a new image scroller.
	 *
	 * @param demo the demo
	 * @param icon the icon
	 * @param layer the layer
	 * @param count the count
	 */
	public ImageScroller(InternalFrameDemo demo, Icon icon, int layer, int count) {
	    super();
	    JPanel p = new JPanel();
	    p.setBackground(Color.white);
	    p.setLayout(new BorderLayout() );
	    
	    p.add(new JLabel(icon), BorderLayout.CENTER);
	    
	    getViewport().add(p);
            getHorizontalScrollBar().setUnitIncrement(10);
            getVerticalScrollBar().setUnitIncrement(10);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	public Dimension getMinimumSize() {
	    return new Dimension(25, 25);
	}
	
    }
    
    /* (non-Javadoc)
     * @see DemoModule#updateDragEnabled(boolean)
     */
    void updateDragEnabled(boolean dragEnabled) {
        windowTitleField.setDragEnabled(dragEnabled);
    }
    
}

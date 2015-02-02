/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * ButtonDemo.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)ButtonDemo.java	1.15 05/11/17
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SingleSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

// TODO: Auto-generated Javadoc
/**
 * JButton, JRadioButton, JToggleButton, JCheckBox Demos.
 *
 * @version 1.15 11/17/05
 * @author Jeff Dinkins
 */
public class ButtonDemo extends DemoModule implements ChangeListener {
    
    /** The tab. */
    JTabbedPane tab;

    /** The button panel. */
    JPanel buttonPanel = new JPanel();
    
    /** The checkbox panel. */
    JPanel checkboxPanel = new JPanel();
    
    /** The radio button panel. */
    JPanel radioButtonPanel = new JPanel();
    
    /** The toggle button panel. */
    JPanel toggleButtonPanel = new JPanel();

    /** The buttons. */
    Vector buttons = new Vector();
    
    /** The checkboxes. */
    Vector checkboxes = new Vector();
    
    /** The radiobuttons. */
    Vector radiobuttons = new Vector();
    
    /** The togglebuttons. */
    Vector togglebuttons = new Vector();

    /** The current controls. */
    Vector currentControls = buttons;

    /** The button. */
    JButton button;
    
    /** The check. */
    JCheckBox check;
    
    /** The radio. */
    JRadioButton radio;
    
    /** The toggle. */
    JToggleButton toggle;

    /** The border0. */
    EmptyBorder border0 = new EmptyBorder(0,0,0,0);// add by jb2011 2012-08-24
    
    /** The border5. */
    EmptyBorder border5 = new EmptyBorder(5,5,5,5);
    
    /** The border10. */
    EmptyBorder border10 = new EmptyBorder(10,10,10,10);

    /** The button display listener. */
    ItemListener buttonDisplayListener = null;
    
    /** The button pad listener. */
    ItemListener buttonPadListener = null;

    /** The insets0. */
    Insets insets0 = new Insets(0,0,0,0);
    
    /** The insets10. */
    Insets insets10 = new Insets(10,10,10,10);

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	ButtonDemo demo = new ButtonDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "按钮";
    };

    /**
     * ButtonDemo Constructor.
     *
     * @param swingset the swingset
     */
    public ButtonDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "ButtonDemo"
			, "toolbar/JButton.gif");

	tab = new JTabbedPane();
	tab.getModel().addChangeListener(this);

	JPanel demo = getDemoPanel();
	demo.setLayout(new BoxLayout(demo, BoxLayout.Y_AXIS));
	demo.add(tab);

	addButtons();
	addRadioButtons();
	addCheckBoxes();
	// addToggleButtons();
	currentControls = buttons;
    }

    /**
     * Adds the buttons.
     */
    public void addButtons() {
	tab.addTab(getString("ButtonDemo.buttons"), buttonPanel);
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.setBorder(border0);

	JPanel p1 = createVerticalPanel(true);
	p1.setAlignmentY(TOP_ALIGNMENT);
	buttonPanel.add(p1);
	p1.add(Box.createHorizontalGlue());// add by jb2011

	// Text Buttons
	JPanel jb2011p = new JPanel();
	jb2011p.setLayout(new BoxLayout(jb2011p, BoxLayout.Y_AXIS));
	JPanel p2 = createHorizontalPanel(false);
	createHorizonalHintBox(jb2011p, p2, getString("ButtonDemo.textbuttons"));
//	p1.add(p2);
	p1.add(jb2011p);
//	p2.setBorder(new CompoundBorder(new TitledBorder(null, getString("ButtonDemo.textbuttons"),
//							  TitledBorder.LEFT, TitledBorder.TOP), border5));

	JButton btn0 = new JButton(getString("ButtonDemo.button1"));
	btn0.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
	btn0.setForeground(Color.white);
	buttons.add(p2.add(btn0));
	p2.add(Box.createRigidArea(HGAP10));

	buttons.add(p2.add(new JButton(getString("ButtonDemo.button2"))));
	p2.add(Box.createRigidArea(HGAP10));

	JButton btn = new JButton("这是一个用来测试的长文本按钮");
	btn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
	btn.setForeground(Color.white);
	buttons.add(p2.add(btn));


	// Image Buttons
	p1.add(Box.createRigidArea(VGAP30));
	jb2011p = new JPanel();
	jb2011p.setLayout(new BoxLayout(jb2011p, BoxLayout.Y_AXIS));
	JPanel p3 = createHorizontalPanel(false);
	createHorizonalHintBox(jb2011p, p3, getString("ButtonDemo.imagebuttons"));
//	p1.add(p3);
	p1.add(jb2011p);
	p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
//	p3.setBorder(new TitledBorder(null, getString("ButtonDemo.imagebuttons"),
//					 TitledBorder.LEFT, TitledBorder.TOP));

	// home image button
	String description = getString("ButtonDemo.phone");
	button = new JButton(createImageIcon("buttons/b1.gif", description));
	button.setPressedIcon(createImageIcon("buttons/b1p.gif", description));
	button.setRolloverIcon(createImageIcon("buttons/b1r.gif", description));
	button.setDisabledIcon(createImageIcon("buttons/b1d.gif", description));
	button.setMargin(new Insets(0,0,0,0));
	button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
	button.setForeground(Color.white);
	p3.add(button);
	buttons.add(button);
	p3.add(Box.createRigidArea(HGAP10));

	// write image button
	description = getString("ButtonDemo.write");
	button = new JButton(createImageIcon("buttons/b2.gif", description));
	button.setPressedIcon(createImageIcon("buttons/b2p.gif", description));
	button.setRolloverIcon(createImageIcon("buttons/b2r.gif", description));
	button.setDisabledIcon(createImageIcon("buttons/b2d.gif", description));
	button.setMargin(new Insets(0,0,0,0));
	p3.add(button);
	buttons.add(button);
	p3.add(Box.createRigidArea(HGAP10));

	// write image button
	description = getString("ButtonDemo.peace");
	button = new JButton(createImageIcon("buttons/b3.gif", description));
	button.setPressedIcon(createImageIcon("buttons/b3p.gif", description));
	button.setRolloverIcon(createImageIcon("buttons/b3r.gif", description));
	button.setDisabledIcon(createImageIcon("buttons/b3d.gif", description));
	button.setMargin(new Insets(0,0,0,0));
	button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
	p3.add(button);
	buttons.add(button);

	p1.add(Box.createVerticalGlue());

//	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(Box.createHorizontalStrut(10));//modified by jb2011
	currentControls = buttons;
	buttonPanel.add(createControls());
    }

    /**
     * Adds the radio buttons.
     */
    public void addRadioButtons() {
	ButtonGroup group = new ButtonGroup();

	tab.addTab(getString("ButtonDemo.radiobuttons"), radioButtonPanel);
	radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.X_AXIS));
        radioButtonPanel.setBorder(border0);

	JPanel p1 = createVerticalPanel(true);
	p1.setAlignmentY(TOP_ALIGNMENT);
	radioButtonPanel.add(p1);
	p1.add(Box.createHorizontalGlue());// add by jb2011

	// Text Radio Buttons
	JPanel jb2011p = new JPanel();
	jb2011p.setLayout(new BoxLayout(jb2011p, BoxLayout.Y_AXIS));
	JPanel p2 = createHorizontalPanel(false);
	createHorizonalHintBox(jb2011p, p2, getString("ButtonDemo.textradiobuttons"));
//	p1.add(p2);
	p1.add(jb2011p);
//	p2.setBorder(new CompoundBorder(
//                      new TitledBorder(
//			null, getString("ButtonDemo.textradiobuttons"),
//			TitledBorder.LEFT, TitledBorder.TOP), border0)
//	);

        radio = (JRadioButton)p2.add(
                new JRadioButton(getString("ButtonDemo.radio1")));
        group.add(radio);
	radiobuttons.add(radio);
	p2.add(Box.createRigidArea(HGAP10));

	radio = (JRadioButton)p2.add(
                new JRadioButton(getString("ButtonDemo.radio2")));
        group.add(radio);
	radiobuttons.add(radio);
	p2.add(Box.createRigidArea(HGAP10));

	radio = (JRadioButton)p2.add(
                new JRadioButton(getString("ButtonDemo.radio3")));
        group.add(radio);
	radiobuttons.add(radio);

	// Image Radio Buttons
        group = new ButtonGroup();
	p1.add(Box.createRigidArea(VGAP30));
	jb2011p = new JPanel();
	jb2011p.setLayout(new BoxLayout(jb2011p, BoxLayout.Y_AXIS));
	JPanel p3 = createHorizontalPanel(false);
	createHorizonalHintBox(jb2011p, p3, getString("ButtonDemo.imageradiobuttons"));
//	p1.add(p2);
	p1.add(jb2011p);
//	JPanel p3 = createHorizontalPanel(false);
//	p1.add(p3);
//	p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
//	p3.setBorder(new TitledBorder(null, getString("ButtonDemo.imageradiobuttons"),
//					 TitledBorder.LEFT, TitledBorder.TOP));

	// image radio button 1
	String description = getString("ButtonDemo.customradio");
	String text = getString("ButtonDemo.radio1");
	radio = new JRadioButton(text, createImageIcon("buttons/rb.gif", description));
	radio.setPressedIcon(createImageIcon("buttons/rbp.gif", description));
	radio.setRolloverIcon(createImageIcon("buttons/rbr.gif", description));
	radio.setRolloverSelectedIcon(createImageIcon("buttons/rbrs.gif", description));
	radio.setSelectedIcon(createImageIcon("buttons/rbs.gif", description));
	radio.setMargin(new Insets(0,0,0,0));
	group.add(radio);
	p3.add(radio);
	radiobuttons.add(radio);
	p3.add(Box.createRigidArea(HGAP20));

	// image radio button 2
	text = getString("ButtonDemo.radio2");
	radio = new JRadioButton(text, createImageIcon("buttons/rb.gif", description));
	radio.setPressedIcon(createImageIcon("buttons/rbp.gif", description));
	radio.setRolloverIcon(createImageIcon("buttons/rbr.gif", description));
	radio.setRolloverSelectedIcon(createImageIcon("buttons/rbrs.gif", description));
	radio.setSelectedIcon(createImageIcon("buttons/rbs.gif", description));
	radio.setMargin(new Insets(0,0,0,0));
	group.add(radio);
	p3.add(radio);
	radiobuttons.add(radio);
	p3.add(Box.createRigidArea(HGAP20));

	// image radio button 3
	text = getString("ButtonDemo.radio3");
	radio = new JRadioButton(text, createImageIcon("buttons/rb.gif", description));
	radio.setPressedIcon(createImageIcon("buttons/rbp.gif", description));
	radio.setRolloverIcon(createImageIcon("buttons/rbr.gif", description));
	radio.setRolloverSelectedIcon(createImageIcon("buttons/rbrs.gif", description));
	radio.setSelectedIcon(createImageIcon("buttons/rbs.gif", description));
	radio.setMargin(new Insets(0,0,0,0));
	group.add(radio);
	radiobuttons.add(radio);
	p3.add(radio);

	// verticaly glue fills out the rest of the box
	p1.add(Box.createVerticalGlue());

	//modified by jb2011
	radioButtonPanel.add(Box.createHorizontalStrut(10));//Box.createHorizontalGlue());
	
	currentControls = radiobuttons;
	radioButtonPanel.add(createControls());
    }


    /**
     * Adds the check boxes.
     */
    public void addCheckBoxes() {
	tab.addTab(getString("ButtonDemo.checkboxes"), checkboxPanel);
	checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.X_AXIS));
        checkboxPanel.setBorder(border0);

	JPanel p1 = createVerticalPanel(true);
	p1.setAlignmentY(TOP_ALIGNMENT);
	checkboxPanel.add(p1);
	p1.add(Box.createHorizontalGlue());// add by jb2011

	// Text Radio Buttons
	JPanel jb2011p = new JPanel();
	jb2011p.setLayout(new BoxLayout(jb2011p, BoxLayout.Y_AXIS));
	JPanel p2 = createHorizontalPanel(false);
	createHorizonalHintBox(jb2011p, p2, getString("ButtonDemo.textcheckboxes"));
//	p1.add(p2);
	p1.add(jb2011p);
//	p2.setBorder(new CompoundBorder(
//                      new TitledBorder(
//			null, getString("ButtonDemo.textcheckboxes"),
//			TitledBorder.LEFT, TitledBorder.TOP), border0)
//	);

	checkboxes.add(p2.add(new JCheckBox(getString("ButtonDemo.check1"))));
	p2.add(Box.createRigidArea(HGAP10));

	checkboxes.add(p2.add(new JCheckBox(getString("ButtonDemo.check2"))));
	p2.add(Box.createRigidArea(HGAP10));

	checkboxes.add(p2.add(new JCheckBox(getString("ButtonDemo.check3"))));

	// Image Radio Buttons
	p1.add(Box.createRigidArea(VGAP30));
	jb2011p = new JPanel();
	jb2011p.setLayout(new BoxLayout(jb2011p, BoxLayout.Y_AXIS));
	JPanel p3 = createHorizontalPanel(false);
	createHorizonalHintBox(jb2011p, p3, getString("ButtonDemo.imagecheckboxes"));
//	p1.add(p2);
	p1.add(jb2011p);
//	p1.add(p3);
//	p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
//	p3.setBorder(new TitledBorder(null, getString("ButtonDemo.imagecheckboxes"),
//					 TitledBorder.LEFT, TitledBorder.TOP));

	// image checkbox 1
	String description = getString("ButtonDemo.customcheck");
	String text = getString("ButtonDemo.check1");
	check = new JCheckBox(text, createImageIcon("buttons/cb.gif", description));
	check.setRolloverIcon(createImageIcon("buttons/cbr.gif", description));
	check.setRolloverSelectedIcon(createImageIcon("buttons/cbrs.gif", description));
	check.setSelectedIcon(createImageIcon("buttons/cbs.gif", description));
	check.setMargin(new Insets(0,0,0,0));
	p3.add(check);
	checkboxes.add(check);
	p3.add(Box.createRigidArea(HGAP20));

	// image checkbox 2
	text = getString("ButtonDemo.check2");
	check = new JCheckBox(text, createImageIcon("buttons/cb.gif", description));
	check.setRolloverIcon(createImageIcon("buttons/cbr.gif", description));
	check.setRolloverSelectedIcon(createImageIcon("buttons/cbrs.gif", description));
	check.setSelectedIcon(createImageIcon("buttons/cbs.gif", description));
	check.setMargin(new Insets(0,0,0,0));
	p3.add(check);
	checkboxes.add(check);
	p3.add(Box.createRigidArea(HGAP20));

	// image checkbox 3
	text = getString("ButtonDemo.check3");
	check = new JCheckBox(text, createImageIcon("buttons/cb.gif", description));
	check.setRolloverIcon(createImageIcon("buttons/cbr.gif", description));
	check.setRolloverSelectedIcon(createImageIcon("buttons/cbrs.gif", description));
	check.setSelectedIcon(createImageIcon("buttons/cbs.gif", description));
	check.setMargin(new Insets(0,0,0,0));
	p3.add(check);
	checkboxes.add(check);

	// verticaly glue fills out the rest of the box
	p1.add(Box.createVerticalGlue());

	//modified by jb2011
	checkboxPanel.add(Box.createHorizontalStrut(10));//Box.createHorizontalGlue());
	currentControls = checkboxes;
	checkboxPanel.add(createControls());
    }

    /**
     * Adds the toggle buttons.
     */
    public void addToggleButtons() {
	tab.addTab(getString("ButtonDemo.togglebuttons"), toggleButtonPanel);
    }

    /**
     * Creates the controls.
     *
     * @return the j panel
     */
    public JPanel createControls() {
        JPanel controls = new JPanel() {
            public Dimension getMaximumSize() {
                return new Dimension(300, super.getMaximumSize().height);
            }
        };
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setAlignmentY(TOP_ALIGNMENT);
        controls.setAlignmentX(LEFT_ALIGNMENT);

        JPanel buttonControls = createHorizontalPanel(true);
        buttonControls.setAlignmentY(TOP_ALIGNMENT);
        buttonControls.setAlignmentX(LEFT_ALIGNMENT);

        JPanel leftColumn = createVerticalPanel(false);
        leftColumn.setAlignmentX(LEFT_ALIGNMENT);
        leftColumn.setAlignmentY(TOP_ALIGNMENT);

        JPanel rightColumn = new LayoutControlPanel(this);

        buttonControls.add(leftColumn);
        buttonControls.add(Box.createRigidArea(HGAP20));
        buttonControls.add(rightColumn);
        buttonControls.add(Box.createRigidArea(HGAP20));

        controls.add(buttonControls);

	createListeners();

        // Display Options
		//* modified by jb2011：改成一个灰色圆色背景的label
		//JLabel l = new JLabel(getString("ButtonDemo.controlpanel_label"));
        JLabel l = N9ComponentFactory.createLabel_style4(getString("ButtonDemo.controlpanel_label"));
        leftColumn.add(l);

        JCheckBox bordered = new JCheckBox(getString("ButtonDemo.paintborder"));
        bordered.setActionCommand("PaintBorder"); 
        bordered.setToolTipText(getString("ButtonDemo.paintborder_tooltip"));
        bordered.setMnemonic(getMnemonic("ButtonDemo.paintborder_mnemonic"));
	if (currentControls == buttons) {
	        bordered.setSelected(true);
	}
        bordered.addItemListener(buttonDisplayListener);
        leftColumn.add(bordered);

        JCheckBox focused = new JCheckBox(getString("ButtonDemo.paintfocus"));
        focused.setActionCommand("PaintFocus"); 
        focused.setToolTipText(getString("ButtonDemo.paintfocus_tooltip"));
        focused.setMnemonic(getMnemonic("ButtonDemo.paintfocus_mnemonic"));
        focused.setSelected(true);
        focused.addItemListener(buttonDisplayListener);
        leftColumn.add(focused);

        JCheckBox enabled = new JCheckBox(getString("ButtonDemo.enabled"));
        enabled.setActionCommand("Enabled"); 
        enabled.setToolTipText(getString("ButtonDemo.enabled_tooltip"));
        enabled.setSelected(true);
        enabled.addItemListener(buttonDisplayListener);
        enabled.setMnemonic(getMnemonic("ButtonDemo.enabled_mnemonic"));
        leftColumn.add(enabled);

        JCheckBox filled = new JCheckBox(getString("ButtonDemo.contentfilled"));
        filled.setActionCommand("ContentFilled"); 
        filled.setToolTipText(getString("ButtonDemo.contentfilled_tooltip"));
        filled.setSelected(true);
        filled.addItemListener(buttonDisplayListener);
        filled.setMnemonic(getMnemonic("ButtonDemo.contentfilled_mnemonic"));
        leftColumn.add(filled);

        leftColumn.add(Box.createRigidArea(VGAP20));
        
        //* modified by jb2011：改成一个灰色圆色背景的label
        // l = new JLabel(getString("ButtonDemo.padamount_label"));
        l = N9ComponentFactory.createLabel_style4(getString("ButtonDemo.padamount_label"));
        leftColumn.add(l);
        ButtonGroup group = new ButtonGroup();
        JRadioButton defaultPad = new JRadioButton(getString("ButtonDemo.default"));
        defaultPad.setToolTipText(getString("ButtonDemo.default_tooltip"));
        defaultPad.setMnemonic(getMnemonic("ButtonDemo.default_mnemonic"));
        defaultPad.addItemListener(buttonPadListener);
        group.add(defaultPad);
        defaultPad.setSelected(true);
        leftColumn.add(defaultPad);

        JRadioButton zeroPad = new JRadioButton(getString("ButtonDemo.zero"));
        zeroPad.setActionCommand("ZeroPad"); 
        zeroPad.setToolTipText(getString("ButtonDemo.zero_tooltip"));
        zeroPad.addItemListener(buttonPadListener);
        zeroPad.setMnemonic(getMnemonic("ButtonDemo.zero_mnemonic"));
        group.add(zeroPad);
        leftColumn.add(zeroPad);

        JRadioButton tenPad = new JRadioButton(getString("ButtonDemo.ten"));
        tenPad.setActionCommand("TenPad"); 
        tenPad.setMnemonic(getMnemonic("ButtonDemo.ten_mnemonic"));
        tenPad.setToolTipText(getString("ButtonDemo.ten_tooltip"));
        tenPad.addItemListener(buttonPadListener);
        group.add(tenPad);
        leftColumn.add(tenPad);

        leftColumn.add(Box.createRigidArea(VGAP20));
	return controls;
    }
    
    /**
     * Creates the listeners.
     */
    public void createListeners() {
	buttonDisplayListener = new ItemListener() {
		Component c;
		AbstractButton b;
		
		public void itemStateChanged(ItemEvent e) {
		    JCheckBox cb = (JCheckBox) e.getSource();
		    String command = cb.getActionCommand();
		    if(command == "Enabled") {
			for(int i = 0; i < currentControls.size(); i++) {
			    c = (Component) currentControls.elementAt(i);
			    c.setEnabled(cb.isSelected());
			    c.invalidate();
			}
		    } else if(command == "PaintBorder") {
			c = (Component) currentControls.elementAt(0);
			if(c instanceof AbstractButton) {
			    for(int i = 0; i < currentControls.size(); i++) {
				b = (AbstractButton) currentControls.elementAt(i);
				b.setBorderPainted(cb.isSelected());
				b.invalidate();
			    }
			}
		    } else if(command == "PaintFocus") {
			c = (Component) currentControls.elementAt(0);
			if(c instanceof AbstractButton) {
			    for(int i = 0; i < currentControls.size(); i++) {
				b = (AbstractButton) currentControls.elementAt(i);
				b.setFocusPainted(cb.isSelected());
				b.invalidate();
			    }
			}
		    } else if(command == "ContentFilled") {
			c = (Component) currentControls.elementAt(0);
			if(c instanceof AbstractButton) {
			    for(int i = 0; i < currentControls.size(); i++) {
				b = (AbstractButton) currentControls.elementAt(i);
				b.setContentAreaFilled(cb.isSelected());
				b.invalidate();
			    }
			}
		    }
		    invalidate();
		    validate();
		    repaint();
		}
	};

	buttonPadListener = new ItemListener() {
		Component c;
		AbstractButton b;
		
		public void itemStateChanged(ItemEvent e) {
		    // *** pad = 0
		    int pad = -1;
		    JRadioButton rb = (JRadioButton) e.getSource();
		    String command = rb.getActionCommand();
		    if(command == "ZeroPad" && rb.isSelected()) {
			pad = 0;
		    } else if(command == "TenPad" && rb.isSelected()) {
			pad = 10;
		    }
		    
		    for(int i = 0; i < currentControls.size(); i++) {
			b = (AbstractButton) currentControls.elementAt(i);
			if(pad == -1) {
			    b.setMargin(null);
			} else if(pad == 0) {
			    b.setMargin(insets0);
			} else {
			    b.setMargin(insets10);
			}
		    }
		    invalidate();
		    validate();
		    repaint();
		}
	};
    }
	
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
	SingleSelectionModel model = (SingleSelectionModel) e.getSource();
	if(model.getSelectedIndex() == 0) {
	    currentControls = buttons;
	} else if(model.getSelectedIndex() == 1) {
	    currentControls = radiobuttons;
	} else if(model.getSelectedIndex() == 2) {
	    currentControls = checkboxes;
	} else {
	    currentControls = togglebuttons;
	}
    }

    /**
     * Gets the current controls.
     *
     * @return the current controls
     */
    public Vector getCurrentControls() {
	return currentControls;
    }
    
    //------------------------------------------------------------------------
    //add by jb2011 START
    /**
     * Creates the horizonal hint box.
     *
     * @param parent the parent
     * @param c the c
     * @param txt the txt
     */
    public static void createHorizonalHintBox(JPanel parent,JComponent c, String txt)
    {
    	parent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	parent.setAlignmentX(Component.LEFT_ALIGNMENT);
    	c.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
    	c.setAlignmentX(Component.LEFT_ALIGNMENT);

    	JLabel l1 = N9ComponentFactory.createLabel_style4(txt);
    	l1.setAlignmentX(Component.LEFT_ALIGNMENT);
    	parent.add(l1);
    	
    	parent.add(c);
    }
}

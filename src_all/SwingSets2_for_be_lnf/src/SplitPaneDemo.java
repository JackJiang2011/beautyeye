/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * SplitPaneDemo.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)SplitPaneDemo.java	1.12 05/11/17
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// TODO: Auto-generated Javadoc
/**
 * Split Pane demo.
 *
 * @version 1.12 11/17/05
 * @author Scott Violet
 * @author Jeff Dinkins
 */
public class SplitPaneDemo extends DemoModule {

    /** The split pane. */
    JSplitPane splitPane = null;
    
    /** The earth. */
    JLabel earth = null;
    
    /** The moon. */
    JLabel moon = null;
    
    /** The div size. */
    JTextField divSize;
    
    /** The earth size. */
    JTextField earthSize;
    
    /** The moon size. */
    JTextField moonSize;
    
    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	SplitPaneDemo demo = new SplitPaneDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "SplitPane";
    };
    
    /**
     * SplitPaneDemo Constructor.
     *
     * @param swingset the swingset
     */
    public SplitPaneDemo(SwingSet2 swingset) {
	super(swingset, "SplitPaneDemo"
			, "toolbar/JSplitPane.gif");

	earth = new JLabel(
			createImageIcon("splitpane/earth.jpg", getString("SplitPaneDemo.earth"))
			);
	earth.setMinimumSize(new Dimension(20, 20));

	moon = new JLabel(
			createImageIcon("splitpane/moon.jpg", getString("SplitPaneDemo.moon"))
			);
	moon.setMinimumSize(new Dimension(20, 20));
	
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, earth, moon);
        splitPane.setContinuousLayout(true);
//	splitPane.setOneTouchExpandable(true);//commet by jb2011

        splitPane.setDividerLocation(350);//由jb2011 从200改成现在值

	getDemoPanel().add(splitPane, BorderLayout.CENTER);
//	getDemoPanel().setBackground(Color.black);

	getDemoPanel().add(createSplitPaneControls(), BorderLayout.SOUTH);
    }
    
    /**
     * Creates controls to alter the JSplitPane.
     *
     * @return the j panel
     */
    protected JPanel createSplitPaneControls() {
        JPanel wrapper = new JPanel();
        ButtonGroup group = new ButtonGroup();
        JRadioButton button;

        Box buttonWrapper = new Box(BoxLayout.X_AXIS);
	
        wrapper.setLayout(new GridLayout(0, 1));
	
        /* Create a radio button to vertically split the split pane. */
        button = new JRadioButton(getString("SplitPaneDemo.vert_split"));
        button.setMnemonic(getMnemonic("SplitPaneDemo.vert_split_mnemonic"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
            }
        });
        group.add(button);
        buttonWrapper.add(button);

        /* Create a radio button the horizontally split the split pane. */
        button = new JRadioButton(getString("SplitPaneDemo.horz_split"));
        button.setMnemonic(getMnemonic("SplitPaneDemo.horz_split_mnemonic"));
        button.setSelected(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            }
        });
        group.add(button);
        buttonWrapper.add(button);
	
        /* Create a check box as to whether or not the split pane continually
           lays out the component when dragging. */
        JCheckBox checkBox = new JCheckBox(getString("SplitPaneDemo.cont_layout"));
        checkBox.setMnemonic(getMnemonic("SplitPaneDemo.cont_layout_mnemonic"));
        checkBox.setSelected(true);
	
        checkBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                splitPane.setContinuousLayout(
		    ((JCheckBox)e.getSource()).isSelected());
            }
        });
        buttonWrapper.add(checkBox);
	
        /* Create a check box as to whether or not the split pane divider
           contains the oneTouchExpandable buttons. */
        checkBox = new JCheckBox(getString("SplitPaneDemo.one_touch_expandable"));
        checkBox.setMnemonic(getMnemonic("SplitPaneDemo.one_touch_expandable_mnemonic"));
//        checkBox.setSelected(true);// commet by jb2011
	
        checkBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                splitPane.setOneTouchExpandable(
		    ((JCheckBox) e.getSource()).isSelected());
	    }
	});
	buttonWrapper.add(checkBox);
	wrapper.add(buttonWrapper);
	
	/* Create a text field to change the divider size. */
	JPanel                   tfWrapper;
	JLabel                   label;
	
	divSize = new JTextField();
        divSize.setText(new Integer(splitPane.getDividerSize()).toString());
        divSize.setColumns(5);
        divSize.getAccessibleContext().setAccessibleName(getString("SplitPaneDemo.divider_size"));
        divSize.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String  value = ((JTextField)e.getSource()).getText();
		int newSize;
		
		try {
		    newSize = Integer.parseInt(value);
		} catch (Exception ex) {
		    newSize = -1;
		}
		if(newSize > 0) {
		    splitPane.setDividerSize(newSize);
		} else {
		    JOptionPane.showMessageDialog(splitPane,
						  getString("SplitPaneDemo.invalid_divider_size"),
						  getString("SplitPaneDemo.error"),
						  JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
	label = new JLabel(getString("SplitPaneDemo.divider_size"));
	tfWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	tfWrapper.add(label);
        tfWrapper.add(divSize);
        label.setLabelFor(divSize);
	label.setDisplayedMnemonic(getMnemonic("SplitPaneDemo.divider_size_mnemonic"));
	wrapper.add(tfWrapper);
	
	/* Create a text field that will change the preferred/minimum size
	   of the earth component. */
	earthSize = new JTextField(String.valueOf(earth.getMinimumSize().width));
        earthSize.setColumns(5);
        earthSize.getAccessibleContext().setAccessibleName(getString("SplitPaneDemo.first_component_min_size"));
        earthSize.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String           value = ((JTextField)e.getSource()).getText();
		int              newSize;
		
		try {
		    newSize = Integer.parseInt(value);
		} catch (Exception ex) {
		    newSize = -1;
		}
		if(newSize > 10) {
		    earth.setMinimumSize(new Dimension(newSize, newSize));
		} else {
		    JOptionPane.showMessageDialog(splitPane,
						  getString("SplitPaneDemo.invalid_min_size") +
						  getString("SplitPaneDemo.must_be_greater_than") + 10,
						  getString("SplitPaneDemo.error"),
						  JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
	label = new JLabel(getString("SplitPaneDemo.first_component_min_size"));
	tfWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	tfWrapper.add(label);
        tfWrapper.add(earthSize);
        label.setLabelFor(earthSize);
	label.setDisplayedMnemonic(getMnemonic("SplitPaneDemo.first_component_min_size_mnemonic"));
	wrapper.add(tfWrapper);
	
	/* Create a text field that will change the preferred/minimum size
	   of the moon component. */
	moonSize = new JTextField(String.valueOf(moon.getMinimumSize().width));
        moonSize.setColumns(5);
        moonSize.getAccessibleContext().setAccessibleName(getString("SplitPaneDemo.second_component_min_size"));
        moonSize.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String           value = ((JTextField)e.getSource()).getText();
		int              newSize;
		
		try {
		    newSize = Integer.parseInt(value);
		} catch (Exception ex) {
		    newSize = -1;
		}
		if(newSize > 10) {
		    moon.setMinimumSize(new Dimension(newSize, newSize));
		} else {
		    JOptionPane.showMessageDialog(splitPane,
						  getString("SplitPaneDemo.invalid_min_size") +
						  getString("SplitPaneDemo.must_be_greater_than") + 10,
						  getString("SplitPaneDemo.error"),
						  JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
	label = new JLabel(getString("SplitPaneDemo.second_component_min_size"));
	tfWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	tfWrapper.add(label);
        tfWrapper.add(moonSize);
        label.setLabelFor(moonSize);
	label.setDisplayedMnemonic(getMnemonic("SplitPaneDemo.second_component_min_size_mnemonic"));
	wrapper.add(tfWrapper);
        
	return wrapper;
    }
    
    /* (non-Javadoc)
     * @see DemoModule#updateDragEnabled(boolean)
     */
    void updateDragEnabled(boolean dragEnabled) {
        divSize.setDragEnabled(dragEnabled);
        earthSize.setDragEnabled(dragEnabled);
        moonSize.setDragEnabled(dragEnabled);
    }
    
}

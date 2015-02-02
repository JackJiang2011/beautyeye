/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * OptionPaneDemo.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)OptionPaneDemo.java	1.11 05/11/17
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

// TODO: Auto-generated Javadoc
/**
 * JOptionPaneDemo.
 *
 * @version 1.11 11/17/05
 * @author Jeff Dinkins
 */
public class OptionPaneDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	OptionPaneDemo demo = new OptionPaneDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "选项窗";
    };

    /**
     * OptionPaneDemo Constructor.
     *
     * @param swingset the swingset
     */
    public OptionPaneDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "OptionPaneDemo"
			, "toolbar/JOptionPane.gif");

	JPanel demo = getDemoPanel();

	demo.setLayout(new BoxLayout(demo, BoxLayout.X_AXIS));

	JPanel bp = new JPanel() {
	    public Dimension getMaximumSize() {
		return new Dimension(getPreferredSize().width, super.getMaximumSize().height);
	    }
	};
	bp.setLayout(new BoxLayout(bp, BoxLayout.Y_AXIS));

	bp.add(Box.createRigidArea(VGAP30));
	bp.add(Box.createRigidArea(VGAP30));

	JButton b = createInputDialogButton();
	b.setForeground(Color.white);
	b.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
	bp.add(b);      bp.add(Box.createRigidArea(VGAP15));
	bp.add(createWarningDialogButton());    bp.add(Box.createRigidArea(VGAP15));
	
	JButton b3 = createComponentDialogButton();
	b3.setForeground(Color.white);
	b3.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
	bp.add(b3);    bp.add(Box.createRigidArea(VGAP15));
	
	JButton b4 = createComponentDialogButton();
	b4.setForeground(Color.white);
	b4.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
	bp.add(b4);    bp.add(Box.createRigidArea(VGAP15));

	bp.add(createConfirmDialogButton());  bp.add(Box.createRigidArea(VGAP15));
	JButton b2 = createComponentDialogButton();
	b2.setForeground(Color.white);
	b2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
	bp.add(b2);    bp.add(Box.createVerticalGlue());

	demo.add(Box.createHorizontalGlue());
	demo.add(bp);
	demo.add(Box.createHorizontalGlue());
    }

    /**
     * Creates the warning dialog button.
     *
     * @return the j button
     */
    public JButton createWarningDialogButton() {
	Action a = new AbstractAction(getString("OptionPaneDemo.warningbutton")) {
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(
		    getDemoPanel(),
		    getString("OptionPaneDemo.warningtext"),
		    getString("OptionPaneDemo.warningtitle"),
		    JOptionPane.WARNING_MESSAGE
		);
	    }
	};
	return createButton(a);
    }

    /**
     * Creates the message dialog button.
     *
     * @return the j button
     */
    public JButton createMessageDialogButton() {
	Action a = new AbstractAction(getString("OptionPaneDemo.messagebutton")) {
	    URL img = getClass().getResource("/resources/images/optionpane/bottle.gif");
	    String imagesrc = "<img src=\"" + img + "\" width=\"284\" height=\"100\">";
	    String message = getString("OptionPaneDemo.messagetext");
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(
		    getDemoPanel(),
		    "<html>" + imagesrc + "<br><center>" + message + "</center><br></html>"
		);
	    }
	};
	return createButton(a);
    }

    /**
     * Creates the confirm dialog button.
     *
     * @return the j button
     */
    public JButton createConfirmDialogButton() {
	Action a = new AbstractAction(getString("OptionPaneDemo.confirmbutton")) {
	    public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(getDemoPanel(), getString("OptionPaneDemo.confirmquestion"));
                if(result == JOptionPane.YES_OPTION) {
		    JOptionPane.showMessageDialog(getDemoPanel(), getString("OptionPaneDemo.confirmyes"));
		} else if(result == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(getDemoPanel(), getString("OptionPaneDemo.confirmno"));
		}
	    }
	};
	return createButton(a);
    }

    /**
     * Creates the input dialog button.
     *
     * @return the j button
     */
    public JButton createInputDialogButton() {
	Action a = new AbstractAction(getString("OptionPaneDemo.inputbutton")) {
	    public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(getDemoPanel(), getString("OptionPaneDemo.inputquestion"));
                if ((result != null) && (result.length() > 0)) {
                    JOptionPane.showMessageDialog(getDemoPanel(),
                                    result + ": " +
                                    getString("OptionPaneDemo.inputresponse"));
                }
	    }
	};
	return createButton(a);
    }

    /**
     * Creates the component dialog button.
     *
     * @return the j button
     */
    public JButton createComponentDialogButton() {
	Action a = new AbstractAction(getString("OptionPaneDemo.componentbutton")) {
	    public void actionPerformed(ActionEvent e) {
		// In a ComponentDialog, you can show as many message components and
		// as many options as you want:

		// Messages
                Object[]      message = new Object[4];
                message[0] = getString("OptionPaneDemo.componentmessage");
                message[1] = new JTextField(getString("OptionPaneDemo.componenttextfield"));

                JComboBox cb = new JComboBox();
                cb.addItem(getString("OptionPaneDemo.component_cb1"));
                cb.addItem(getString("OptionPaneDemo.component_cb2"));
                cb.addItem(getString("OptionPaneDemo.component_cb3"));
                message[2] = cb;

                message[3] = getString("OptionPaneDemo.componentmessage2");

		// Options
                String[] options = {
		    getString("OptionPaneDemo.component_op1"),
		    getString("OptionPaneDemo.component_op2"),
		    getString("OptionPaneDemo.component_op3"),
		    getString("OptionPaneDemo.component_op4"),
		    getString("OptionPaneDemo.component_op5")
		};
                int result = JOptionPane.showOptionDialog(
		    getDemoPanel(),                             // the parent that the dialog blocks
		    message,                                    // the dialog message array
		    getString("OptionPaneDemo.componenttitle"), // the title of the dialog window
		    JOptionPane.DEFAULT_OPTION,                 // option type
		    JOptionPane.INFORMATION_MESSAGE,            // message type
		    null,                                       // optional icon, use null to use the default icon
		    options,                                    // options string array, will be made into buttons
		    options[3]                                  // option that should be made into a default button
		);
		switch(result) {
		   case 0: // yes
		     JOptionPane.showMessageDialog(getDemoPanel(), getString("OptionPaneDemo.component_r1"));
		     break;
		   case 1: // no
		     JOptionPane.showMessageDialog(getDemoPanel(), getString("OptionPaneDemo.component_r2"));
		     break;
		   case 2: // maybe
		     JOptionPane.showMessageDialog(getDemoPanel(), getString("OptionPaneDemo.component_r3"));
		     break;
		   case 3: // probably
		     JOptionPane.showMessageDialog(getDemoPanel(), getString("OptionPaneDemo.component_r4"));
		     break;
		   default:
		     break;
		}

	    }
	};
	return createButton(a);
    }
		
    /**
     * Creates the button.
     *
     * @param a the a
     * @return the j button
     */
    public JButton createButton(Action a) {
	JButton b = new JButton() {
	    public Dimension getMaximumSize() {
		int width = Short.MAX_VALUE;
		int height = super.getMaximumSize().height;
		return new Dimension(width, height);
	    }
	};
	// setting the following client property informs the button to show
	// the action text as it's name. The default is to not show the
	// action text.
	b.putClientProperty("displayActionText", Boolean.TRUE);
	b.setAction(a);
	// b.setAlignmentX(JButton.CENTER_ALIGNMENT);
	return b;
    }

}

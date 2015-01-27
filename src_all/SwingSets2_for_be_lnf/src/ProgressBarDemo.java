/*
 * @(#)ProgressBarDemo.java	1.12 05/11/17
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
 * @(#)ProgressBarDemo.java	1.12 05/11/17
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * JProgressBar Demo.
 *
 * @version 1.12 11/17/05
 * @author Jeff Dinkins
 * # @author Peter Korn (accessibility support)
 */
public class ProgressBarDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	ProgressBarDemo demo = new ProgressBarDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "½ø¶ÈÌõ";
    };

    /**
     * ProgressBarDemo Constructor.
     *
     * @param swingset the swingset
     */
    public ProgressBarDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "ProgressBarDemo"
			, "toolbar/JProgressBar.gif");

		createProgressPanel();
    }

    /** The timer. */
    javax.swing.Timer timer = new javax.swing.Timer(18, createTextLoadAction());
    
    /** The load action. */
    Action loadAction;
    
    /** The stop action. */
    Action stopAction;
    
    /** The progress bar. */
    JProgressBar progressBar;
    
    /** The progress text area. */
    JTextArea progressTextArea;

    /* (non-Javadoc)
     * @see DemoModule#updateDragEnabled(boolean)
     */
    void updateDragEnabled(boolean dragEnabled) {
        progressTextArea.setDragEnabled(dragEnabled);
    }
    
    /**
     * Creates the progress panel.
     */
    public void createProgressPanel() {
	getDemoPanel().setLayout(new BorderLayout());

	JPanel textWrapper = new JPanel(new BorderLayout());
//	textWrapper.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
	textWrapper.setAlignmentX(LEFT_ALIGNMENT);
	progressTextArea = new MyTextArea();
        
	progressTextArea.getAccessibleContext().setAccessibleName(getString("ProgressBarDemo.accessible_text_area_name"));
	progressTextArea.getAccessibleContext().setAccessibleName(getString("ProgressBarDemo.accessible_text_area_description"));
	textWrapper.add(new JScrollPane(progressTextArea), BorderLayout.CENTER);

	getDemoPanel().add(textWrapper, BorderLayout.CENTER);

	JPanel progressPanel = new JPanel();
	getDemoPanel().add(progressPanel, BorderLayout.SOUTH);

	progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, text.length()) {
	    public Dimension getPreferredSize() {
		return new Dimension(270, super.getPreferredSize().height);
//	    	return new Dimension(super.getPreferredSize().width,300 );
	    }
	};
//	progressBar.setForeground(Color.red);
//	progressBar.setBackground(Color.pink);
//	progressBar.setOrientation(JProgressBar.VERTICAL);//* add by Jack Jiang for test
	progressBar.getAccessibleContext().setAccessibleName(getString("ProgressBarDemo.accessible_text_loading_progress"));
	progressBar.setStringPainted(true);//* add by Jack Jiang for test
//	progressBar.setPreferredSize(new Dimension(800,25));
	
//	progressPanel.add(progressBar);
//	progressPanel.add(createLoadButton());
//	progressPanel.add(createStopButton());
	
		JPanel p1 = new JPanel(); 
		p1.add(progressBar);
		p1.add(createLoadButton());
		p1.add(createStopButton());
		
		progressPanel.add(p1);
		
		JProgressBar pbIndeterminate = new JProgressBar()
		{
		    public Dimension getPreferredSize() {
//			    	return new Dimension(15,300 );
		    		return new Dimension(200, 15);
			    }
		}
		;
//		pbIndeterminate.setForeground(Color.black);
		//when the task of (initially) unknown length begins:
		pbIndeterminate.setIndeterminate(true);
//		pbIndeterminate.setOrientation(JProgressBar.VERTICAL);//* add by Jack Jiang for test
		progressPanel.add(pbIndeterminate);

    }

    /**
     * Creates the load button.
     *
     * @return the j button
     */
    public JButton createLoadButton() {
	loadAction = new AbstractAction(getString("ProgressBarDemo.start_button")) {
	    public void actionPerformed(ActionEvent e) {
		loadAction.setEnabled(false);
		stopAction.setEnabled(true);
                if (progressBar.getValue() == progressBar.getMaximum()) {
                    progressBar.setValue(0);
                    textLocation = 0;
                    progressTextArea.setText("");
                }
		timer.start();
	    }
	};
	return createButton(loadAction);
    }

    /**
     * Creates the stop button.
     *
     * @return the j button
     */
    public JButton createStopButton() {
	stopAction = new AbstractAction(getString("ProgressBarDemo.stop_button")) {
	    public void actionPerformed(ActionEvent e) {
		timer.stop();
		loadAction.setEnabled(true);
		stopAction.setEnabled(false);
	    }
	};
	return createButton(stopAction);
    }

    /**
     * Creates the button.
     *
     * @param a the a
     * @return the j button
     */
    public JButton createButton(Action a) {
	JButton b = new JButton();
	// setting the following client property informs the button to show
	// the action text as it's name. The default is to not show the
	// action text.
	b.putClientProperty("displayActionText", Boolean.TRUE);
	b.setAction(a);
	return b;
    }


    /** The text location. */
    int textLocation = 0;

    /** The text. */
    String text = getString("ProgressBarDemo.text");

    /**
     * Creates the text load action.
     *
     * @return the action
     */
    public Action createTextLoadAction() {
	return new AbstractAction("text load action") {
	    public void actionPerformed (ActionEvent e) {
		if(progressBar.getValue() < progressBar.getMaximum()) {
		    progressBar.setValue(progressBar.getValue() + 1);
		    progressTextArea.append(text.substring(textLocation, textLocation+1));
		    textLocation++;
		} else {
			timer.stop();
			loadAction.setEnabled(true);
			stopAction.setEnabled(false);
		}
	    }
	};
    }


    /**
     * The Class MyTextArea.
     */
    class MyTextArea extends JTextArea {
        
        /**
         * Instantiates a new my text area.
         */
        public MyTextArea() {
            super(null, 0, 0);
//	    setEditable(false);
	    setText("");
        }

        /* (non-Javadoc)
         * @see javax.swing.JComponent#getAlignmentX()
         */
        public float getAlignmentX () {
            return LEFT_ALIGNMENT;
        }
 
        /* (non-Javadoc)
         * @see javax.swing.JComponent#getAlignmentY()
         */
        public float getAlignmentY () {
            return TOP_ALIGNMENT;
        }
    }
}



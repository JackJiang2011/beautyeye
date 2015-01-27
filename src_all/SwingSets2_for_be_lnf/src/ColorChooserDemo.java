/*
 * @(#)ColorChooserDemo.java	1.12 05/11/17
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
 * @(#)ColorChooserDemo.java	1.12 05/11/17
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

// TODO: Auto-generated Javadoc
/**
 * JColorChooserDemo.
 *
 * @version 1.1 07/16/99
 * @author Jeff Dinkins
 */
public class ColorChooserDemo extends DemoModule {

    /** The bez anim. */
    BezierAnimationPanel bezAnim;
    
    /** The outer color button. */
    JButton outerColorButton = null;
    
    /** The background color button. */
    JButton backgroundColorButton = null;
    
    /** The gradient a button. */
    JButton gradientAButton = null;
    
    /** The gradient b button. */
    JButton gradientBButton = null;
    
    // to store the color chosen from the JColorChooser
    /** The chosen. */
    private Color chosen;

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	ColorChooserDemo demo = new ColorChooserDemo(null);
	demo.mainImpl();
    }

    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "ÑÕÉ«´°";
    };

    /**
     * ColorChooserDemo Constructor.
     *
     * @param swingset the swingset
     */
    public ColorChooserDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "ColorChooserDemo"
			, "toolbar/JColorChooser.gif");

	// Create the bezier animation panel to put in the center of the panel.
	bezAnim = new BezierAnimationPanel();

	outerColorButton = new JButton(getString("ColorChooserDemo.outer_line"));
	outerColorButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
	outerColorButton.setForeground(Color.white);
	outerColorButton.setIcon(new ColorSwatch("OuterLine", bezAnim));

	backgroundColorButton = new JButton(getString("ColorChooserDemo.background"));
	backgroundColorButton.setIcon(new ColorSwatch("Background", bezAnim));

	gradientAButton = new JButton(getString("ColorChooserDemo.grad_a"));
	gradientAButton.setIcon(new ColorSwatch("GradientA", bezAnim));
	gradientAButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
	gradientAButton.setForeground(Color.white);

	gradientBButton = new JButton(getString("ColorChooserDemo.grad_b"));
	gradientBButton.setIcon(new ColorSwatch("GradientB", bezAnim));
	gradientBButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
	gradientBButton.setForeground(Color.white);

	ActionListener l = new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		Color current = bezAnim.getOuterColor();

		if(e.getSource() == backgroundColorButton) {
		    current = bezAnim.getBackgroundColor();
		} else if(e.getSource() == gradientAButton) {
		    current = bezAnim.getGradientColorA();
		} else if(e.getSource() == gradientBButton) {
		    current = bezAnim.getGradientColorB();
		}

                final JColorChooser chooser = new JColorChooser(current != null ?
                                                                current :
                                                                Color.WHITE);
                if (getSwingSet2() != null && getSwingSet2().isDragEnabled()) {
                    chooser.setDragEnabled(true);
                }

                chosen = null;
                ActionListener okListener = new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        chosen = chooser.getColor();
                    }
                };

                JDialog dialog = JColorChooser.createDialog(getDemoPanel(),
                                                            getString("ColorChooserDemo.chooser_title"),
                                                            true,
                                                            chooser,
                                                            okListener,
                                                            null);

                dialog.show();

		if(e.getSource() == outerColorButton) {
		    bezAnim.setOuterColor(chosen);
		} else if(e.getSource() == backgroundColorButton) {
                    bezAnim.setBackgroundColor(chosen);
		} else if(e.getSource() == gradientAButton) {
                    bezAnim.setGradientColorA(chosen);
		} else {
                    bezAnim.setGradientColorB(chosen);
		}
	    }
	};

	outerColorButton.addActionListener(l);
	backgroundColorButton.addActionListener(l);
	gradientAButton.addActionListener(l);
	gradientBButton.addActionListener(l);

	// Add everything to the panel
	JPanel p = getDemoPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

	// Add control buttons
	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	buttonPanel.add(backgroundColorButton);
	buttonPanel.add(Box.createRigidArea(new Dimension(15, 1)));

	buttonPanel.add(gradientAButton);
	buttonPanel.add(Box.createRigidArea(new Dimension(15, 1)));

	buttonPanel.add(gradientBButton);
	buttonPanel.add(Box.createRigidArea(new Dimension(15, 1)));

	buttonPanel.add(outerColorButton);

	// Add the panel midway down the panel
	p.add(Box.createRigidArea(new Dimension(1, 10)));
	p.add(buttonPanel);
	p.add(Box.createRigidArea(new Dimension(1, 5)));
	p.add(bezAnim);
    }

    /**
     * The Class ColorSwatch.
     */
    class ColorSwatch implements Icon {
	
	/** The gradient. */
	String gradient;
	
	/** The bez. */
	BezierAnimationPanel bez;

	/**
	 * Instantiates a new color swatch.
	 *
	 * @param g the g
	 * @param b the b
	 */
	public ColorSwatch(String g, BezierAnimationPanel b) {
	    bez = b;
	    gradient = g;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#getIconWidth()
	 */
	public int getIconWidth() {
	    return 11;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#getIconHeight()
	 */
	public int getIconHeight() {
	    return 11;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
	    g.setColor(Color.black);
	    g.fillRect(x, y, getIconWidth(), getIconHeight());
	    if(gradient.equals("GradientA")) {
		g.setColor(bez.getGradientColorA());
	    } else if(gradient.equals("GradientB")) {
		g.setColor(bez.getGradientColorB());
	    } else if(gradient.equals("Background")) {
		g.setColor(bez.getBackgroundColor());
	    } else if(gradient.equals("OuterLine")) {
		g.setColor(bez.getOuterColor());
	    }
	    g.fillRect(x+2, y+2, getIconWidth()-4, getIconHeight()-4);
	}
    }

}

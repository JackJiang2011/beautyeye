/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * LayoutControlPanel.java at 2015-2-1 20:25:36, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)LayoutControlPanel.java	1.12 05/11/17
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

// TODO: Auto-generated Javadoc
/*
 * The LayoutControlPanel contains controls for setting an 
 * AbstractButton's horizontal and vertical text position and 
 * horizontal and vertical alignment.
 */

/**
 * The Class LayoutControlPanel.
 */
public class LayoutControlPanel extends JPanel implements SwingConstants {

    /** The absolute positions. */
    private boolean  absolutePositions;
    
    /** The text position. */
    private DirectionPanel textPosition = null;
    
    /** The label alignment. */
    private DirectionPanel labelAlignment = null;
    
    /** The demo. */
    private ButtonDemo demo = null;

    // private ComponentOrientChanger componentOrientChanger = null;

    /**
     * Instantiates a new layout control panel.
     *
     * @param demo the demo
     */
    LayoutControlPanel(ButtonDemo demo) {
        this.demo = demo;

        // this.componentOrientationChanger = componentOrientationChanger;
        
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setAlignmentX(LEFT_ALIGNMENT);
	setAlignmentY(TOP_ALIGNMENT);

        JLabel l;

        // If SwingSet has a ComponentOrientationChanger, then include control
        // for choosing between absolute and relative positioning.  This will
        // only happen when we're running on JDK 1.2 or above.
        //
        // if(componentOrientationChanger != null ) {
        //     l = new JLabel("Positioning:");
        //     add(l);
 	//
        //    ButtonGroup group = new ButtonGroup();
        //    PositioningListener positioningListener = new PositioningListener();
        //    JRadioButton absolutePos = new JRadioButton("Absolute");
        //    absolutePos.setMnemonic('a');
        //    absolutePos.setToolTipText("Text/Content positioning is independant of line direction");
        //    group.add(absolutePos);
        //    absolutePos.addItemListener(positioningListener);
        //    add(absolutePos);
        //
        //    JRadioButton relativePos = new JRadioButton("Relative");
        //    relativePos.setMnemonic('r');
        //    relativePos.setToolTipText("Text/Content positioning depends on line direction.");
        //    group.add(relativePos);
        //    relativePos.addItemListener(positioningListener);
        //    add(relativePos);
        //
        //    add(Box.createRigidArea(demo.VGAP20));
        //
        //    absolutePositions = false;
        //    relativePos.setSelected(true);
        //
        //    componentOrientationChanger.addActionListener( new OrientationChangeListener() );
        //} else {
            absolutePositions = true;
        //}

        textPosition = new DirectionPanel(true, "E", new TextPositionListener());
        labelAlignment = new DirectionPanel(true, "C", new LabelAlignmentListener());

        // Make sure the controls' text position and label alignment match
        // the initial value of the associated direction panel.
        for(int i = 0; i < demo.getCurrentControls().size(); i++) {
            Component c = (Component) demo.getCurrentControls().elementAt(i);
            setPosition(c, RIGHT, CENTER);
            setAlignment(c,CENTER,CENTER);
        }

        //* modified by jb2011：改成一个灰色圆色背景的label
		//l = new JLabel(demo.getString("LayoutControlPanel.textposition_label"));
        l = N9ComponentFactory.createLabel_style4(demo.getString("LayoutControlPanel.textposition_label"));
        add(l);
        add(textPosition);

        //* modified by jb2011：
        add(Box.createRigidArea(new Dimension(1,33)));//demo.VGAP20));

        //* modified by jb2011：改成一个灰色圆色背景的label
        //l = new JLabel(demo.getString("LayoutControlPanel.contentalignment_label"));
        l = N9ComponentFactory.createLabel_style4(demo.getString("LayoutControlPanel.contentalignment_label"));
        add(l);
        add(labelAlignment);

        add(Box.createGlue());
    }


    /**
     * The listener interface for receiving orientationChange events.
     * The class that is interested in processing a orientationChange
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOrientationChangeListener<code> method. When
     * the orientationChange event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OrientationChangeEvent
     */
    class OrientationChangeListener implements ActionListener {
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {
            if( !e.getActionCommand().equals("OrientationChanged") ){
                return;
            }
            if( absolutePositions ){
                return;
            }
            
            String currentTextPosition = textPosition.getSelection();
            if( currentTextPosition.equals("NW") )
                textPosition.setSelection("NE");
            else if( currentTextPosition.equals("NE") )
                textPosition.setSelection("NW");
            else if( currentTextPosition.equals("E") )
                textPosition.setSelection("W");
            else if( currentTextPosition.equals("W") )
                textPosition.setSelection("E");
            else if( currentTextPosition.equals("SE") )
                textPosition.setSelection("SW");
            else if( currentTextPosition.equals("SW") )
                textPosition.setSelection("SE");

            String currentLabelAlignment = labelAlignment.getSelection();
            if( currentLabelAlignment.equals("NW") )
                labelAlignment.setSelection("NE");
            else if( currentLabelAlignment.equals("NE") )
                labelAlignment.setSelection("NW");
            else if( currentLabelAlignment.equals("E") )
                labelAlignment.setSelection("W");
            else if( currentLabelAlignment.equals("W") )
                labelAlignment.setSelection("E");
            else if( currentLabelAlignment.equals("SE") )
                labelAlignment.setSelection("SW");
            else if( currentLabelAlignment.equals("SW") )
                labelAlignment.setSelection("SE");
        }
    }

    /**
     * The listener interface for receiving positioning events.
     * The class that is interested in processing a positioning
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addPositioningListener<code> method. When
     * the positioning event occurs, that object's appropriate
     * method is invoked.
     *
     * @see PositioningEvent
     */
    class PositioningListener implements ItemListener {

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
	    JRadioButton rb = (JRadioButton) e.getSource();
	    if(rb.getText().equals("Absolute") && rb.isSelected()) {
		absolutePositions = true;
	    } else if(rb.getText().equals("Relative") && rb.isSelected()) {
		absolutePositions = false;
	    } 
            
	    for(int i = 0; i < demo.getCurrentControls().size(); i++) {
		Component c = (Component) demo.getCurrentControls().elementAt(i);
                int hPos, vPos, hAlign, vAlign;
                if( c instanceof AbstractButton ) {
                   hPos = ((AbstractButton)c).getHorizontalTextPosition();
                   vPos = ((AbstractButton)c).getVerticalTextPosition();
                   hAlign = ((AbstractButton)c).getHorizontalAlignment();
                   vAlign = ((AbstractButton)c).getVerticalAlignment();
                } else if( c instanceof JLabel ) {
                   hPos = ((JLabel)c).getHorizontalTextPosition();
                   vPos = ((JLabel)c).getVerticalTextPosition();
                   hAlign = ((JLabel)c).getHorizontalAlignment();
                   vAlign = ((JLabel)c).getVerticalAlignment();
                } else {
                    continue;
                }                
                setPosition(c, hPos, vPos);
                setAlignment(c, hAlign, vAlign);
	    }
            
	    demo.invalidate();
	    demo.validate();
	    demo.repaint();            
	}
    };


    // Text Position Listener
    /**
     * The listener interface for receiving textPosition events.
     * The class that is interested in processing a textPosition
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addTextPositionListener<code> method. When
     * the textPosition event occurs, that object's appropriate
     * method is invoked.
     *
     * @see TextPositionEvent
     */
    class TextPositionListener implements ActionListener {
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    JRadioButton rb = (JRadioButton) e.getSource();
	    if(!rb.isSelected()) {
                return;
            }
            String cmd = rb.getActionCommand();
            int hPos, vPos;
            if(cmd.equals("NW")) {
                    hPos = LEFT; vPos = TOP;
            } else if(cmd.equals("N")) {
                    hPos = CENTER; vPos = TOP;
            } else if(cmd.equals("NE")) {
                    hPos = RIGHT; vPos = TOP;
            } else if(cmd.equals("W")) {
                    hPos = LEFT; vPos = CENTER;
            } else if(cmd.equals("C")) {
                    hPos = CENTER; vPos = CENTER;
            } else if(cmd.equals("E")) {
                    hPos = RIGHT; vPos = CENTER;
            } else if(cmd.equals("SW")) {
                    hPos = LEFT; vPos = BOTTOM;
            } else if(cmd.equals("S")) {
                    hPos = CENTER; vPos = BOTTOM;
            } else /*if(cmd.equals("SE"))*/ {
                    hPos = RIGHT; vPos = BOTTOM;
            }
            for(int i = 0; i < demo.getCurrentControls().size(); i++) {
                Component c = (Component) demo.getCurrentControls().elementAt(i);
                setPosition(c, hPos, vPos);
            }
            demo.invalidate();
            demo.validate();
            demo.repaint();
	}
    };


    // Label Alignment Listener
    /**
     * The listener interface for receiving labelAlignment events.
     * The class that is interested in processing a labelAlignment
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addLabelAlignmentListener<code> method. When
     * the labelAlignment event occurs, that object's appropriate
     * method is invoked.
     *
     * @see LabelAlignmentEvent
     */
    class LabelAlignmentListener implements  ActionListener {
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    JRadioButton rb = (JRadioButton) e.getSource();
	    if(!rb.isSelected()) {
                return;
            }
            String cmd = rb.getActionCommand();
            int hPos, vPos;
            if(cmd.equals("NW")) {
                    hPos = LEFT; vPos = TOP;
            } else if(cmd.equals("N")) {
                    hPos = CENTER; vPos = TOP;
            } else if(cmd.equals("NE")) {
                    hPos = RIGHT; vPos = TOP;
            } else if(cmd.equals("W")) {
                    hPos = LEFT; vPos = CENTER;
            } else if(cmd.equals("C")) {
                    hPos = CENTER; vPos = CENTER;
            } else if(cmd.equals("E")) {
                    hPos = RIGHT; vPos = CENTER;
            } else if(cmd.equals("SW")) {
                    hPos = LEFT; vPos = BOTTOM;
            } else if(cmd.equals("S")) {
                    hPos = CENTER; vPos = BOTTOM;
            } else /*if(cmd.equals("SE"))*/ {
                    hPos = RIGHT; vPos = BOTTOM;
            }
            for(int i = 0; i < demo.getCurrentControls().size(); i++) {
                Component c = (Component) demo.getCurrentControls().elementAt(i);
                setAlignment(c,hPos,vPos);
                c.invalidate();
            }
            demo.invalidate();
            demo.validate();
            demo.repaint();
	}
    };

    // Position
    /**
     * Sets the position.
     *
     * @param c the c
     * @param hPos the h pos
     * @param vPos the v pos
     */
    void setPosition(Component c, int hPos, int vPos) {
        boolean ltr = true;
        ltr = c.getComponentOrientation().isLeftToRight();
        if( absolutePositions ) {
            if( hPos == LEADING ) {
                hPos = ltr ? LEFT : RIGHT;
            } else if( hPos == TRAILING ) {
                hPos = ltr ? RIGHT : LEFT;
            }
        } else {
            if( hPos == LEFT ) {
                hPos = ltr ? LEADING : TRAILING;
            } else if( hPos == RIGHT ) {
                hPos = ltr ? TRAILING : LEADING;
            }
        }
        if(c instanceof AbstractButton) {
            AbstractButton x = (AbstractButton) c;
            x.setHorizontalTextPosition(hPos);
            x.setVerticalTextPosition(vPos);
        } else if(c instanceof JLabel) {
            JLabel x = (JLabel) c;
            x.setHorizontalTextPosition(hPos);
            x.setVerticalTextPosition(vPos);
        }
    }

    /**
     * Sets the alignment.
     *
     * @param c the c
     * @param hPos the h pos
     * @param vPos the v pos
     */
    void setAlignment(Component c, int hPos, int vPos) {
        boolean ltr = true;
        ltr = c.getComponentOrientation().isLeftToRight();
        if( absolutePositions ) {
            if( hPos == LEADING ) {
                hPos = ltr ? LEFT : RIGHT;
            } else if( hPos == TRAILING ) {
                hPos = ltr ? RIGHT : LEFT;
            }
        } else {
            if( hPos == LEFT ) {
                hPos = ltr ? LEADING : TRAILING;
            } else if( hPos == RIGHT ) {
                hPos = ltr ? TRAILING : LEADING;
            }
        }
        if(c instanceof AbstractButton) {
            AbstractButton x = (AbstractButton) c;
            x.setHorizontalAlignment(hPos);
            x.setVerticalAlignment(vPos);
        } else if(c instanceof JLabel) {
            JLabel x = (JLabel) c;
            x.setHorizontalAlignment(hPos);
            x.setVerticalAlignment(vPos);
        }
    }
}

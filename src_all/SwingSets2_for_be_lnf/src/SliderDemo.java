/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * SliderDemo.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)SliderDemo.java	1.9 05/11/17
 */


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

// TODO: Auto-generated Javadoc
/**
 * JSlider Demo.
 *
 * @version 1.9 11/17/05
 * @author Dave Kloba
 * @author Jeff Dinkins
 */
public class SliderDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	SliderDemo demo = new SliderDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "Slider";
    };

    /**
     * SliderDemo Constructor.
     *
     * @param swingset the swingset
     */
    public SliderDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "SliderDemo"
			, "toolbar/JSlider.gif");

	createSliderDemo();
    }

    /**
     * Creates the slider demo.
     */
    public void createSliderDemo() {
        JSlider s;
	JPanel hp;
	JPanel vp;
	GridLayout g;
	JPanel tp;
	JLabel tf;
	ChangeListener listener;

	getDemoPanel().setLayout(new BorderLayout());

	tf = new JLabel(getString("SliderDemo.slidervalue"));
	getDemoPanel().add(tf, BorderLayout.SOUTH);
	
	tp = new JPanel();
	g = new GridLayout(1, 2);
	g.setHgap(5);
	g.setVgap(5);
	tp.setLayout(g);
	getDemoPanel().add(tp, BorderLayout.CENTER);
		
	listener = new SliderListener(tf);

	BevelBorder border = new BevelBorder(BevelBorder.LOWERED);

	hp = N9ComponentFactory.createPanel_style1();//modified by jb2011//new JPanel();
	hp.setLayout(new BoxLayout(hp, BoxLayout.Y_AXIS));
//	hp.setBorder(new TitledBorder( 
//			border,
//			getString("SliderDemo.horizontal"),
//			TitledBorder.LEFT,
//			TitledBorder.ABOVE_TOP));
	tp.add(hp);

	vp = N9ComponentFactory.createPanel_style1();//modified by jb2011//new JPanel();
//	vp.setBorder(BorderFactory.createLineBorder(Color.red));
	vp.setLayout(new BoxLayout(vp, BoxLayout.X_AXIS));
//	vp.setBorder(new TitledBorder( 
//			border,
//			getString("SliderDemo.vertical"),
//			TitledBorder.LEFT,
//			TitledBorder.ABOVE_TOP));
	tp.add(vp);

	// Horizontal Slider 1
	JPanel p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.plain")));
	s = new JSlider(-10, 100, 20);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.setAlignmentX(Component.LEFT_ALIGNMENT);//add by jb2011
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.plain"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.a_plain_slider"));
	s.addChangeListener(listener);
	//add by jb2011
	createHorizonalHintBox(p,s,getString("SliderDemo.plain"));
	p.add(Box.createRigidArea(VGAP5));
	p.add(s);
	p.add(Box.createRigidArea(VGAP5));
	hp.add(p);
	hp.add(Box.createRigidArea(VGAP10));

	// Horizontal Slider 2
	p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.majorticks")));
	s = new JSlider(100, 1000, 400);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.setPaintTicks(true);
	s.setMajorTickSpacing(100);
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.majorticks"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.majorticksdescription"));
	s.addChangeListener(listener);

	//add by jb2011
	createHorizonalHintBox(p,s,getString("SliderDemo.majorticks"));
	p.add(Box.createRigidArea(VGAP5));
	p.add(s);
	p.add(Box.createRigidArea(VGAP5));
	hp.add(p);
	hp.add(Box.createRigidArea(VGAP10));

	// Horizontal Slider 3
	p = new JPanel();
	p.setAlignmentX(Component.LEFT_ALIGNMENT);//add by jb2011
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.ticks")));
	s = new JSlider(0, 11, 6);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.putClientProperty("JSlider.isFilled", Boolean.TRUE );

	s.setPaintTicks(true);
	s.setMajorTickSpacing(5);
	s.setMinorTickSpacing(1);

	s.setPaintLabels( true );
	s.setSnapToTicks( true );

	s.getLabelTable().put(new Integer(11), new JLabel(new Integer(11).toString(), JLabel.CENTER));
	s.setLabelTable( s.getLabelTable() );

	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.minorticks"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.minorticksdescription"));

	s.addChangeListener(listener);

	//add by jb2011
	createHorizonalHintBox(p,s,"次记号、刻度");//getString("SliderDemo.ticks"));
	p.add(Box.createRigidArea(VGAP5));
	p.add(s);
	p.add(Box.createRigidArea(VGAP5));
	hp.add(p);
	hp.add(Box.createRigidArea(VGAP10));

	// Horizontal Slider 4
	p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.disabled")));
	BoundedRangeModel brm = new DefaultBoundedRangeModel(80, 0, 0, 100);
	s = new JSlider(brm);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.setPaintTicks(true);
	s.setMajorTickSpacing(20);
	s.setMinorTickSpacing(5);
	s.setEnabled(false);
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.disabled"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.disableddescription"));
	s.addChangeListener(listener);
	
	//add by jb2011
	createHorizonalHintBox(p,s,getString("SliderDemo.disabled"));
	p.add(Box.createRigidArea(VGAP5));
	p.add(s);
	p.add(Box.createRigidArea(VGAP5));
	hp.add(p);
	
        //////////////////////////////////////////////////////////////////////////////

	// Vertical Slider 1
	p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.plain")));
	s = new JSlider(JSlider.VERTICAL, -10, 100, 20);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.plain"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.a_plain_slider"));
	s.addChangeListener(listener);
	p.add(Box.createRigidArea(HGAP10));
//	p.add(s);
	p.add(createVerticalHintBox(s,getString("SliderDemo.plain")));
	p.add(Box.createRigidArea(HGAP10));
	vp.add(p);
	vp.add(Box.createRigidArea(HGAP5));//modified by jb2011, from HGAP10 to HGAP5

	// Vertical Slider 2
	p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.majorticks")));
	s = new JSlider(JSlider.VERTICAL, 100, 1000, 400);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.putClientProperty( "JSlider.isFilled", Boolean.TRUE );
	s.setPaintTicks(true);
	s.setMajorTickSpacing(100);
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.majorticks"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.majorticksdescription"));
	s.addChangeListener(listener);
	p.add(Box.createRigidArea(HGAP25));
//	p.add(s);
	p.add(createVerticalHintBox(s,getString("SliderDemo.majorticks")));
	p.add(Box.createRigidArea(HGAP20));//modified by jb2011, from HGAP25 to HGAP20
	vp.add(p);
	vp.add(Box.createRigidArea(HGAP5));//modified by jb2011, from HGAP10 to HGAP5

	// Vertical Slider 3
	p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.minorticks")));
	s = new JSlider(JSlider.VERTICAL, 0, 100, 60);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.setPaintTicks(true);
	s.setMajorTickSpacing(20);
	s.setMinorTickSpacing(5);
	s.setPaintLabels( true );
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.minorticks"));
	s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.minorticksdescription"));

	s.addChangeListener(listener);
	p.add(Box.createRigidArea(HGAP10));
//	p.add(s);
	p.add(createVerticalHintBox(s,getString("SliderDemo.minorticks")));
	p.add(Box.createRigidArea(HGAP5));//modified by jb2011, from HGAP10 to HGAP5
	vp.add(p);
	vp.add(Box.createRigidArea(HGAP5));

	// Vertical Slider 4
	p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
//	p.setBorder(new TitledBorder(getString("SliderDemo.disabled")));
	s = new JSlider(JSlider.VERTICAL, 0, 100, 80);
	s.setOpaque(false);//add by jb2011：不填充默认背景色（否则放到白色面板板会很难看 ）
	s.setPaintTicks(true);
	s.setMajorTickSpacing(20);
	s.setMinorTickSpacing(5);
	s.setEnabled(false);
	s.getAccessibleContext().setAccessibleName(getString("SliderDemo.disabled"));
        s.getAccessibleContext().setAccessibleDescription(getString("SliderDemo.disableddescription"));
	s.addChangeListener(listener);
	p.add(Box.createRigidArea(HGAP20));
//	p.add(s);
	p.add(createVerticalHintBox(s,getString("SliderDemo.disabled")));
	p.add(Box.createRigidArea(HGAP5));//modified by jb2011, from HGAP10 to HGAP5
	vp.add(p);
    }

    /**
     * The listener interface for receiving slider events.
     * The class that is interested in processing a slider
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addSliderListener<code> method. When
     * the slider event occurs, that object's appropriate
     * method is invoked.
     *
     * @see SliderEvent
     */
    class SliderListener implements ChangeListener {
	
	/** The tf. */
	JLabel tf;
	
	/**
	 * Instantiates a new slider listener.
	 *
	 * @param f the f
	 */
	public SliderListener(JLabel f) {
	    tf = f;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
	    JSlider s1 = (JSlider)e.getSource();
	    tf.setText(getString("SliderDemo.slidervalue") + s1.getValue());
	}
    }
    
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
    	parent.setAlignmentX(Component.LEFT_ALIGNMENT);
    	parent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 5));
    	c.setAlignmentX(Component.LEFT_ALIGNMENT);

    	JLabel l1 = N9ComponentFactory.createLabel_style3(txt);
    	l1.setAlignmentX(Component.LEFT_ALIGNMENT);
    	parent.add(l1);
    }
    
    /**
     * Creates the vertical hint box.
     *
     * @param c the c
     * @param txt the txt
     * @return the j panel
     */
    public static JPanel createVerticalHintBox(JComponent c, String txt)
    {
    	JPanel p = new JPanel();
    	p.setOpaque(false);
    	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    	JLabel l1 = N9ComponentFactory.createLabel_style3(txt);
    	l1.setAlignmentX(Component.CENTER_ALIGNMENT);
    	p.add(l1);
    	c.setAlignmentX(Component.CENTER_ALIGNMENT);
    	p.add(c);
    	
    	p.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
    	return p;
    }//add by jb2011 END
    
}


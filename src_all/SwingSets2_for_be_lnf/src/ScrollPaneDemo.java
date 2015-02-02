/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * ScrollPaneDemo.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)ScrollPaneDemo.java	1.9 05/11/17
 */


import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

// TODO: Auto-generated Javadoc
/**
 * Scroll Pane Demo.
 *
 * @version 1.9 11/17/05
 * @author Jeff Dinkins
 */
public class ScrollPaneDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	ScrollPaneDemo demo = new ScrollPaneDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "滚动面板";
    };

    /**
     * ScrollPaneDemo Constructor.
     *
     * @param swingset the swingset
     */
    public ScrollPaneDemo(SwingSet2 swingset) {
	super(swingset, "ScrollPaneDemo"
			, "toolbar/JScrollPane.gif");

	ImageIcon crayons = createImageIcon("scrollpane/crayons.jpg",  getString("ScrollPaneDemo.crayons"));
	getDemoPanel().add(new ImageScroller(this, crayons), BorderLayout.CENTER);
    }


    /**
     * ScrollPane class that demonstrates how to set the various column and row headers
     * and corners.
     */
    class ImageScroller extends JScrollPane {
	
	/**
	 * Instantiates a new image scroller.
	 *
	 * @param demo the demo
	 * @param icon the icon
	 */
	public ImageScroller(ScrollPaneDemo demo, Icon icon) {
	    super();

	    // Panel to hold the icon image
	    JPanel p = new JPanel(new BorderLayout());
	    p.add(new JLabel(icon), BorderLayout.CENTER);
	    getViewport().add(p);

	    // Create and add a column header to the scrollpane
	    JLabel colHeader = new JLabel(
		demo.createImageIcon("scrollpane/colheader.jpg", getString("ScrollPaneDemo.colheader")));
	    setColumnHeaderView(colHeader);

	    // Create and add a row header to the scrollpane
	    JLabel rowHeader = new JLabel(
		demo.createImageIcon("scrollpane/rowheader.jpg", getString("ScrollPaneDemo.rowheader")));
	    setRowHeaderView(rowHeader);

	    // Create and add the upper left corner
	    JLabel cornerUL = new JLabel(
		demo.createImageIcon("scrollpane/upperleft.jpg", getString("ScrollPaneDemo.upperleft")));
	    setCorner(UPPER_LEFT_CORNER, cornerUL);

	    // Create and add the upper right corner
	    JLabel cornerUR = new JLabel(
		demo.createImageIcon("scrollpane/upperright.jpg", getString("ScrollPaneDemo.upperright")));
	    setCorner(UPPER_RIGHT_CORNER, cornerUR);

	    // Create and add the lower left corner
	    JLabel cornerLL = new JLabel(
		demo.createImageIcon("scrollpane/lowerleft.jpg", getString("ScrollPaneDemo.lowerleft")));
	    setCorner(LOWER_LEFT_CORNER, cornerLL);

	    JScrollBar vsb = getVerticalScrollBar();
	    JScrollBar hsb = getHorizontalScrollBar();

	    vsb.setValue(icon.getIconHeight());
	    hsb.setValue(icon.getIconWidth()/10);
	}
    }

}


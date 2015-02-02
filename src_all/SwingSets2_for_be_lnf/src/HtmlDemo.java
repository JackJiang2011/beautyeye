/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * HtmlDemo.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)HtmlDemo.java	1.12 05/11/17
 */


import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

// TODO: Auto-generated Javadoc
/**
 * Html Demo.
 *
 * @version 1.12 05/11/17
 * @author Jeff Dinkins
 */
public class HtmlDemo extends DemoModule {

    /** The html. */
    JEditorPane html;
    
    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	HtmlDemo demo = new HtmlDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "HTML";
    };
    
    /**
     * HtmlDemo Constructor.
     *
     * @param swingset the swingset
     */
    public HtmlDemo(SwingSet2 swingset) {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        super(swingset, "HtmlDemo"
        		, "toolbar/JEditorPane.gif");
	
        try {
	    URL url = null;
	    // System.getProperty("user.dir") +
	    // System.getProperty("file.separator");
	    String path = null;
	    try {
		path = "/resources/index.html";
		url = getClass().getResource(path);
            } catch (Exception e) {
		System.err.println("Failed to open " + path);
		url = null;
            }
	    
            if(url != null) {
                html = new JEditorPane(url);
//                html.setEditable(false);
                html.addHyperlinkListener(createHyperLinkListener());

		JScrollPane scroller = new JScrollPane();
		JViewport vp = scroller.getViewport();
		vp.add(html);
                getDemoPanel().add(scroller, BorderLayout.CENTER);
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    /**
     * Creates the hyper link listener.
     *
     * @return the hyperlink listener
     */
    public HyperlinkListener createHyperLinkListener() {
	return new HyperlinkListener() {
	    public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		    if (e instanceof HTMLFrameHyperlinkEvent) {
			((HTMLDocument)html.getDocument()).processHTMLFrameHyperlinkEvent(
			    (HTMLFrameHyperlinkEvent)e);
		    } else {
			try {
			    html.setPage(e.getURL());
			} catch (IOException ioe) {
			    System.out.println("IOE: " + ioe);
			}
		    }
		}
	    }
	};
    }
    
    /* (non-Javadoc)
     * @see DemoModule#updateDragEnabled(boolean)
     */
    void updateDragEnabled(boolean dragEnabled) {
        html.setDragEnabled(dragEnabled);
    }
    
}

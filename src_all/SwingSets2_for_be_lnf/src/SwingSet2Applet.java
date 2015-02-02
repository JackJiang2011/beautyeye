/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * SwingSet2Applet.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)SwingSet2Applet.java	1.10 05/11/17
 */


import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JApplet;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class SwingSet2Applet.
 *
 * @version 1.10 11/17/05
 * @author Jeff Dinkins
 */

public class SwingSet2Applet extends JApplet {
    
    /* (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    public void init() {
    	try
		{
    		BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch (Exception e)
		{
			System.err.println("BeautyEyeLNF运行失败，原因是："+e.getMessage());
		}
    	
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new SwingSet2(this), BorderLayout.CENTER);
    }

    /**
     * Gets the uRL.
     *
     * @param filename the filename
     * @return the uRL
     */
    public URL getURL(String filename) {
        URL codeBase = this.getCodeBase();
        URL url = null;
	
        try {
            url = new URL(codeBase, filename);
	    System.out.println(url);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Error: badly specified URL");
            return null;
        }

        return url;
    }


}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEScrollPaneUI.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch4_scroll;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicScrollPaneUI;

// TODO: Auto-generated Javadoc
/**
 * 滚动面板的UI实现类。.
 *
 * @author Jack Jiang(jb2011@163.com
 */
public class BEScrollPaneUI extends BasicScrollPaneUI
{
    
    /**
     * Creates the ui.
     *
     * @param x the x
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent x) 
    {
    	return new BEScrollPaneUI();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicScrollPaneUI#installDefaults(javax.swing.JScrollPane)
     */
    protected void installDefaults(JScrollPane scrollpane) 
    {
    	super.installDefaults(scrollpane);
    	
//    	/* ~~注：ScrollPane.opaque这个属性是jb2011自已加的，目的是控制滚动面板及其Viewport的透明性 */
//    	scrollpane.setOpaque(UIManager.getBoolean("ScrollPane.opaque"));
//    	scrollpane.getViewport().setOpaque(UIManager.getBoolean("ScrollPane.opaque"));
    }
}

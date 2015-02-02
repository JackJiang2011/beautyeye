/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEDesktopIconUI.java at 2015-2-1 20:25:36, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch10_internalframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopIconUI;

/**
 * 内部窗体最小化时的图标ui实现类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类的实现参考了WindowsDesktopIconUI (JDK1.6.0_u18)
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEDesktopIconUI extends BasicDesktopIconUI 
{
    
    /** The width. */
    private int width;

    /**
     * Creates the ui.
     *
     * @param c the c
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent c) 
    {
        return new BEDesktopIconUI();
    }

    //copy from WindowsDesktopIconUI and no modified
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicDesktopIconUI#installDefaults()
     */
    public void installDefaults() 
    {
        super.installDefaults();
        width = UIManager.getInt("DesktopIcon.width");
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicDesktopIconUI#installUI(javax.swing.JComponent)
     */
    public void installUI(JComponent c)   
    {
    	super.installUI(c);

    	//modified by jb2011
//		c.setOpaque(XPStyle.getXP() == null);
    	c.setOpaque(false);
    }

    //copy from WindowsDesktopIconUI and no modified
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicDesktopIconUI#uninstallUI(javax.swing.JComponent)
     */
    public void uninstallUI(JComponent c) 
    {
    	BEInternalFrameTitlePane thePane = (BEInternalFrameTitlePane)iconPane;
        super.uninstallUI(c);
        thePane.uninstallListeners();
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicDesktopIconUI#installComponents()
     */
    protected void installComponents()
    {
        iconPane = new BEInternalFrameTitlePane(frame){
        	//重写父类方法 by jb2011
        	protected void paintTitlePaneImpl(Insets frameInsets,Graphics g
        			, int width,int height, boolean isSelected)
        	{
        		//** Swing BUG补尝：重写父类的目的就是想用Insets(0,0,0,0)来调用paintTitlePaneImpl(否则因
        		//** BasicInternalFrameTitlePane中的布局bug，将会导致填充错位)
        		Insets instes = new Insets(0,0,0,0);
        		super.paintTitlePaneImpl(instes, g, width, height, isSelected);
        	}
        };
        desktopIcon.setLayout(new BorderLayout());
        desktopIcon.add(iconPane, BorderLayout.CENTER);

//		if (XPStyle.getXP() != null) {
//	   	 	desktopIcon.setBorder(null);
//		}
        
        //add by jb2011 2012-06-19
	    desktopIcon.setBorder(UIManager.getBorder("InternalFrame.border"));
    }

    //copy from WindowsDesktopIconUI and no modified
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicDesktopIconUI#getPreferredSize(javax.swing.JComponent)
     */
    public Dimension getPreferredSize(JComponent c)
    {
        // Windows desktop icons can not be resized.  Therefore, we should
        // always return the minimum size of the desktop icon. See
        // getMinimumSize(JComponent c).
        return getMinimumSize(c);
    }

    //copy from WindowsDesktopIconUI and no modified
    /**
     * Windows desktop icons are restricted to a width of 160 pixels by
     * default.  This value is retrieved by the DesktopIcon.width property.
     *
     * @param c the c
     * @return the minimum size
     */
    public Dimension getMinimumSize(JComponent c) 
    {
    	//## Bug FIX：see Issue 60(https://code.google.com/p/beautyeye/issues/detail?id=60&can=1)
    	//## bug起源：此bug源自WindowsDesktopIconUI，BeautyEye因参考了它的实现因而也把该bug带了过来。
    	//## 是否解决：已于2012-10-19日由Jack Jiang解决。
    	//## bug描述：在某些情况下（比如在cpcns的定制be lnf项目中，它的内部窗口先最大化后再最小化时，该DesptopIcon
    	//##          的高度就变的较小（与正常状态下有差异），具体原因待深究。
    	//## 解决方法：参考MetalDesktopIconUI里的实现，用现在的代码即可解决这个问题。
//        Dimension dim = super.getMinimumSize(c);
//        dim.width = width;
//        return dim;
    	return new Dimension(width,
                desktopIcon.getLayout().minimumLayoutSize(desktopIcon).height);
    }
}

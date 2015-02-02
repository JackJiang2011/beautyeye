/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEMenuBarUI.java at 2015-2-1 20:25:37, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch9_menu;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

// TODO: Auto-generated Javadoc
/**
 * JMenuBar的UI实现类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//* 本类的实现参考了WindowsMenuBarUI
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEMenuBarUI extends BasicMenuBarUI//WindowsMenuBarUI
{
    
    /**
     * Creates the ui.
     *
     * @param x the x
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent x)
    {
    	return new BEMenuBarUI();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
     */
    @Override
    public void paint(Graphics g, JComponent c) 
    {
    	int width = c.getWidth();
    	int height = c.getHeight();

    	//背景划一个灰色底线（方便与JMenuBar的顶层菜单项的底色融合）
    	g.setColor(BEMenuUI.MENU_UNSELECTED_UNDERLINE_COLOR);
    	g.fillRect(0,height-BEMenuUI.DECORATED_UNDERLINE_HEIGHT, width, height);
    }
}

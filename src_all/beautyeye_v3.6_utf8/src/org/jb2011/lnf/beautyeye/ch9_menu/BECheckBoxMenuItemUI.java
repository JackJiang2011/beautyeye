/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BECheckBoxMenuItemUI.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch9_menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

// TODO: Auto-generated Javadoc
/**
 * JCheckBoxMenuItem的UI实现类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 */
public class BECheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI 
{
	
	/** 是否强制单项透明(当强制不透明时，在普通状态下该item将不会被绘制背景）. */
	private static boolean enforceTransparent = true;
	
    /**
     * Creates the ui.
     *
     * @param b the b
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent b) 
    {
        return new BECheckBoxMenuItemUI();
    }   
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicMenuItemUI#paintBackground(java.awt.Graphics, javax.swing.JMenuItem, java.awt.Color)
     */
    protected  void paintBackground(Graphics g, JMenuItem menuItem, 
            Color bgColor) 
    {
//        if (WindowsMenuItemUI.isVistaPainting()) {
//            WindowsMenuItemUI.paintBackground(accessor, g, menuItem, bgColor);
//            return;
//        }
//        super.paintBackground(g, menuItem, bgColor);
     // see parent!
		ButtonModel model = menuItem.getModel();
		Color oldColor = g.getColor();
		int menuWidth = menuItem.getWidth();
		int menuHeight = menuItem.getHeight();
		
		Graphics2D g2 = (Graphics2D)g;
		
		if (model.isArmed()
				|| (menuItem instanceof JMenu && model.isSelected()))
		{
			g.setColor(bgColor);
			
			__Icon9Factory__.getInstance().getBgIcon_ItemSelected()
				.draw(g2, 0, 0, menuWidth, menuHeight);
			
//			/****** 选中的背景样式现在是渐变加圆角填充了,impled by js */
//			NLLookAndFeel.setAntiAliasing(g2, true);
//			//矩形填充
//			Paint oldpaint = g2.getPaint();
//			GradientPaint gp = new GradientPaint(0, 0
//					,GraphicHandler.getColor(bgColor, 35, 35, 35)
//					,0, menuHeight,bgColor
//	                );
//			g2.setPaint(gp);
//			g.fillRoundRect(0, 0, menuWidth, menuHeight,5,5);
//			g2.setPaint(oldpaint);
//			NLLookAndFeel.setAntiAliasing(g2, false);
		}
		else
		{
			if(!enforceTransparent)
			{
				g.setColor(menuItem.getBackground());
				g.fillRect(0, 0, menuWidth, menuHeight);
			}
		}
		g.setColor(oldColor);
    }
    
    //copy from com.sun.java.swing.plaf.windows.WindowsIconFactory.CheckBoxMenuItemIcon
    //* 由Jack Jiang修改
    /**
     * The Class CheckBoxMenuItemIcon.
     */
    public static class CheckBoxMenuItemIcon implements Icon, UIResource, Serializable 
    {
    	/** 
    	 * 本常量用于标识是否运行于Vista及更新的windows平台.
    	 * （当然，目前该变量只会在BeautyEyeLookAndFeelWin中被设置，即如果使
    	 * 用BeautyEyeLookAndFeelCross跨平台主类则永远不会被设置，因为跨平台主类及LNF不受此影响哦）.
    	 * @see org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelWin#initForVista()
    	 */
    	private boolean usedForVista = false;
    	
    	/* (non-Javadoc)
	     * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	     */
	    public void paintIcon(Component c, Graphics g, int x, int y) 
    	{
    		AbstractButton b = (AbstractButton) c;
    		ButtonModel model = b.getModel();
    		
    		Image selectedImg = __IconFactory__.getInstance().getCheckboxMenuItemSelectedNormalIcon().getImage();
    		boolean isSelected = model.isSelected();
//    		boolean isArmed = model.isArmed();
    		if (isSelected) 
    		{
//    			if(isArmed)
//    				selectedImg = __IconFactory__.getInstance().getCheckboxMenuItemSelectedRoverIcon().getImage();
    		}
    		else
    			selectedImg = __IconFactory__.getInstance().getCheckboxMenuItemNoneIcon().getImage();

    		g.drawImage(selectedImg
    				, x+(usedForVista?5:-4)//* 注意：当用于windows平台专用主类且处于Vista及更高版win时要做不一样的处理哦
    				, y - 3
    				, null);
    	}
    	
	    /* (non-Javadoc)
	     * @see javax.swing.Icon#getIconWidth()
	     */
	    public int getIconWidth() { return 16; }// default if 9
    	
	    /* (non-Javadoc)
	     * @see javax.swing.Icon#getIconHeight()
	     */
	    public int getIconHeight() { return 16; }// default if 9
    	
		/**
		 * Checks if is used for vista.
		 *
		 * @return true, if is used for vista
		 */
		public boolean isUsedForVista()
		{
			return usedForVista;
		}
		
		/**
		 * Sets the used for vista.
		 *
		 * @param usedForVista the used for vista
		 * @return the check box menu item icon
		 */
		public CheckBoxMenuItemIcon setUsedForVista(boolean usedForVista)
		{
			this.usedForVista = usedForVista;
			return this;
		}
    } // End class CheckBoxMenuItemIcon
}

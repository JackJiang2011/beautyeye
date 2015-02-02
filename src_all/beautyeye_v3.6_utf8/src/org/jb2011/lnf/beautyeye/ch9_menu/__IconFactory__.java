/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __IconFactory__.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch9_menu;

import javax.swing.ImageIcon;

import org.jb2011.lnf.beautyeye.utils.RawCache;

/**
 * 普通图片工厂类.
 * 
 * @author Jack Jiang
 * @version 1.0
 */
public class __IconFactory__ extends RawCache<ImageIcon>
{
	
	/** 相对路径根（默认是相对于本类的相对物理路径）. */
	public final static String IMGS_ROOT="imgs";

	/** The instance. */
	private static __IconFactory__ instance = null;

	/**
	 * Gets the single instance of __IconFactory__.
	 *
	 * @return single instance of __IconFactory__
	 */
	public static __IconFactory__ getInstance()
	{
		if(instance==null)
			instance = new __IconFactory__();
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see org.jb2011.lnf.beautyeye.utils.RawCache#getResource(java.lang.String, java.lang.Class)
	 */
	@Override
	protected ImageIcon getResource(String relativePath, Class baseClass)
	{
		return new ImageIcon(baseClass.getResource(relativePath));
	}
	
	/**
	 * Gets the image.
	 *
	 * @param relativePath the relative path
	 * @return the image
	 */
	public ImageIcon getImage(String relativePath)
	{
		return  getRaw(relativePath,this.getClass());
	}
	
	/**
	 * Gets the radio button menu item check icon.
	 *
	 * @return the radio button menu item check icon
	 */
	public ImageIcon getRadioButtonMenuItemCheckIcon()
	{
		return getImage(IMGS_ROOT+"/RadioButtonMenuItemCheckIcon2.png");
	}
	public ImageIcon getRadioButtonMenuItemNormalIcon()
	{
		return getImage(IMGS_ROOT+"/RadioButtonMenuItemCheckIcon_none.png");
	}
	
	/**
	 * Gets the checkbox menu item selected normal icon.
	 *
	 * @return the checkbox menu item selected normal icon
	 */
	public ImageIcon getCheckboxMenuItemSelectedNormalIcon()
	{
		return getImage(IMGS_ROOT+"/checkbox_menuitem_selected_normal.png");
	}
//	public ImageIcon getCheckboxMenuItemSelectedRoverIcon()
//	{
//		return getImage(IMGS_ROOT+"/checkbox_menuitem_selected_rover.png");
//	}
	/**
 * Gets the checkbox menu item none icon.
 *
 * @return the checkbox menu item none icon
 */
public ImageIcon getCheckboxMenuItemNoneIcon()
	{
		return getImage(IMGS_ROOT+"/checkbox_menuitem_none.png");
	}
	
}

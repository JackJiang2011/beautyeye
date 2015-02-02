/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __IconFactory__.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch13_radio$cb_btn;

import javax.swing.ImageIcon;

import org.jb2011.lnf.beautyeye.utils.RawCache;

// TODO: Auto-generated Javadoc
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
	 * Gets the radio button icon_disable.
	 *
	 * @return the radio button icon_disable
	 */
	public ImageIcon getRadioButtonIcon_disable()
	{
		return getImage(IMGS_ROOT+"/rb_disable.png");
	}
	
	/**
	 * Gets the radio button icon_normal.
	 *
	 * @return the radio button icon_normal
	 */
	public ImageIcon getRadioButtonIcon_normal()
	{
		return getImage(IMGS_ROOT+"/rb_normal.png");
	}
	
	/**
	 * Gets the radio button icon_pressed.
	 *
	 * @return the radio button icon_pressed
	 */
	public ImageIcon getRadioButtonIcon_pressed()
	{
		return getImage(IMGS_ROOT+"/rb_pressed.png");
	}
	
	/**
	 * Gets the radio button icon_unchecked_disable.
	 *
	 * @return the radio button icon_unchecked_disable
	 */
	public ImageIcon getRadioButtonIcon_unchecked_disable()
	{
		return getImage(IMGS_ROOT+"/rb_un_disable.png");
	}
	
	/**
	 * Gets the radio button icon_unchecked_normal.
	 *
	 * @return the radio button icon_unchecked_normal
	 */
	public ImageIcon getRadioButtonIcon_unchecked_normal()
	{
		return getImage(IMGS_ROOT+"/rb_un_normal.png");
	}
	
	/**
	 * Gets the radio button icon_unchecked_pressed.
	 *
	 * @return the radio button icon_unchecked_pressed
	 */
	public ImageIcon getRadioButtonIcon_unchecked_pressed()
	{
		return getImage(IMGS_ROOT+"/rb_un_pressed.png");
	}
	
	/**
	 * Gets the check box button icon_disable.
	 *
	 * @return the check box button icon_disable
	 */
	public ImageIcon getCheckBoxButtonIcon_disable()
	{
		return getImage(IMGS_ROOT+"/cb_disable.png");
	}
	
	/**
	 * Gets the check box button icon_normal.
	 *
	 * @return the check box button icon_normal
	 */
	public ImageIcon getCheckBoxButtonIcon_normal()
	{
		return getImage(IMGS_ROOT+"/cb_normal.png");
	}
	
	/**
	 * Gets the check box button icon_pressed.
	 *
	 * @return the check box button icon_pressed
	 */
	public ImageIcon getCheckBoxButtonIcon_pressed()
	{
		return getImage(IMGS_ROOT+"/cb_pressed.png");
	}
	
	/**
	 * Gets the check box button icon_unchecked_disable.
	 *
	 * @return the check box button icon_unchecked_disable
	 */
	public ImageIcon getCheckBoxButtonIcon_unchecked_disable()
	{
		return getImage(IMGS_ROOT+"/cb_un_disable.png");
	}
	
	/**
	 * Gets the check box button icon_unchecked_normal.
	 *
	 * @return the check box button icon_unchecked_normal
	 */
	public ImageIcon getCheckBoxButtonIcon_unchecked_normal()
	{
		return getImage(IMGS_ROOT+"/cb_un_normal.png");
	}
	
	/**
	 * Gets the check box button icon_unchecked_pressed.
	 *
	 * @return the check box button icon_unchecked_pressed
	 */
	public ImageIcon getCheckBoxButtonIcon_unchecked_pressed()
	{
		return getImage(IMGS_ROOT+"/cb_un_pressed.png");
	}
}

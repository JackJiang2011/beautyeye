/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __IconFactory__.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch15_slider;

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
	 * Gets the slider tick1.
	 *
	 * @return the slider tick1
	 */
	public ImageIcon getSliderTick1()
	{
		return getImage(IMGS_ROOT+"/slider_tick1.png");
	}
	
	/**
	 * Gets the slider tick1_notrangle.
	 *
	 * @return the slider tick1_notrangle
	 */
	public ImageIcon getSliderTick1_notrangle()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_notrangle.png");
	}
	
	/**
	 * Gets the slider tick1_disable.
	 *
	 * @return the slider tick1_disable
	 */
	public ImageIcon getSliderTick1_disable()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_dark.png");
	}
	
	/**
	 * Gets the slider tick1_notrangle_disable.
	 *
	 * @return the slider tick1_notrangle_disable
	 */
	public ImageIcon getSliderTick1_notrangle_disable()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_notrangle_dark.png");
	}
	
	/**
	 * Gets the slider tick1_vertical.
	 *
	 * @return the slider tick1_vertical
	 */
	public ImageIcon getSliderTick1_vertical()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_v.png");
	}
	
	/**
	 * Gets the slider tick1_notrangle_vertical.
	 *
	 * @return the slider tick1_notrangle_vertical
	 */
	public ImageIcon getSliderTick1_notrangle_vertical()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_notrangle_v.png");
	}
	
	
	/**
	 * Gets the slider tick1_ vertica l_disable.
	 *
	 * @return the slider tick1_ vertica l_disable
	 */
	public ImageIcon getSliderTick1_VERTICAL_disable()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_v_dark.png");
	}
	
	/**
	 * Gets the slider tick1_notrangle_ vertica l_disable.
	 *
	 * @return the slider tick1_notrangle_ vertica l_disable
	 */
	public ImageIcon getSliderTick1_notrangle_VERTICAL_disable()
	{
		return getImage(IMGS_ROOT+"/slider_tick1_notrangle_v_dark.png");
	}
}

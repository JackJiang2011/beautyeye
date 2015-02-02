/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __Icon9Factory__.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch3_button;

import org.jb2011.lnf.beautyeye.utils.NinePatchHelper;
import org.jb2011.lnf.beautyeye.utils.RawCache;
import org.jb2011.ninepatch4j.NinePatch;

/**
 * NinePatch图片（*.9.png）工厂类.
 * 
 * @author Jack Jiang
 * @version 1.0
 */
public class __Icon9Factory__ extends RawCache<NinePatch>
{
	
	/** 相对路径根（默认是相对于本类的相对物理路径）. */
	public final static String IMGS_ROOT="imgs/np";

	/** The instance. */
	private static __Icon9Factory__ instance = null;

	/**
	 * Gets the single instance of __Icon9Factory__.
	 *
	 * @return single instance of __Icon9Factory__
	 */
	public static __Icon9Factory__ getInstance()
	{
		if(instance==null)
			instance = new __Icon9Factory__();
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see org.jb2011.lnf.beautyeye.utils.RawCache#getResource(java.lang.String, java.lang.Class)
	 */
	@Override
	protected NinePatch getResource(String relativePath, Class baseClass)
	{
		return NinePatchHelper.createNinePatch(baseClass.getResource(relativePath), false);
	}

	/**
	 * Gets the raw.
	 *
	 * @param relativePath the relative path
	 * @return the raw
	 */
	public NinePatch getRaw(String relativePath)
	{
		return  getRaw(relativePath,this.getClass());
	}

	
	/**
	 * Gets the button icon_ normal green.
	 *
	 * @return the button icon_ normal green
	 */
	public NinePatch getButtonIcon_NormalGreen()
	{
		return getRaw(IMGS_ROOT+"/btn_special_default.9.png");
	}
	
	/**
	 * Gets the button icon_ normal gray.
	 *
	 * @return the button icon_ normal gray
	 */
	public NinePatch getButtonIcon_NormalGray()
	{
		return getRaw(IMGS_ROOT+"/btn_general_default.9.png");
	}
	
	/**
	 * Gets the button icon_ disable gray.
	 *
	 * @return the button icon_ disable gray
	 */
	public NinePatch getButtonIcon_DisableGray()
	{
		return getRaw(IMGS_ROOT+"/btn_special_disabled.9.png");
	}
	
	/**
	 * Gets the button icon_ pressed orange.
	 *
	 * @return the button icon_ pressed orange
	 */
	public NinePatch getButtonIcon_PressedOrange()
	{
		return getRaw(IMGS_ROOT+"/btn_general_pressed.9.png");
	}
	
	/**
	 * Gets the button icon_rover.
	 *
	 * @return the button icon_rover
	 */
	public NinePatch getButtonIcon_rover()
	{
		return getRaw(IMGS_ROOT+"/btn_general_rover.9.png");
	}
	
	/**
	 * Gets the button icon_ normal light blue.
	 *
	 * @return the button icon_ normal light blue
	 */
	public NinePatch getButtonIcon_NormalLightBlue()
	{
		return getRaw(IMGS_ROOT+"/btn_special_lightblue.9.png");
	}
	
	/**
	 * Gets the button icon_ normal red.
	 *
	 * @return the button icon_ normal red
	 */
	public NinePatch getButtonIcon_NormalRed()
	{
		return getRaw(IMGS_ROOT+"/btn_special_red.9.png");
	}
	
	/**
	 * Gets the button icon_ normal blue.
	 *
	 * @return the button icon_ normal blue
	 */
	public NinePatch getButtonIcon_NormalBlue()
	{
		return getRaw(IMGS_ROOT+"/btn_special_blue.9.png");
	}
	
	/**
	 * Gets the toggle button icon_ checked green.
	 *
	 * @return the toggle button icon_ checked green
	 */
	public NinePatch getToggleButtonIcon_CheckedGreen()
	{
		return getRaw(IMGS_ROOT+"/toggle_button_selected.9.png");
	}
	
	/**
	 * Gets the toggle button icon_ rover green.
	 *
	 * @return the toggle button icon_ rover green
	 */
	public NinePatch getToggleButtonIcon_RoverGreen()
	{
		return getRaw(IMGS_ROOT+"/toggle_button_rover.9.png");
	}
	
}
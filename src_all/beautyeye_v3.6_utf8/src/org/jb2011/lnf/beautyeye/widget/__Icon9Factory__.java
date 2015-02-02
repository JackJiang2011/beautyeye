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
package org.jb2011.lnf.beautyeye.widget;

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
	 * Gets the panel bg.
	 *
	 * @return the panel bg
	 */
	public NinePatch getPanelBg()
	{
		return getRaw(IMGS_ROOT+"/query_item_bg_2.9.png");
	}
	
	/**
	 * Gets the hint bg light blue.
	 *
	 * @return the hint bg light blue
	 */
	public NinePatch getHintBgLightBlue()
	{
		return getRaw(IMGS_ROOT+"/hint_bg_lightblue.9.png");
	}
	
	/**
	 * Gets the hint bg light gray.
	 *
	 * @return the hint bg light gray
	 */
	public NinePatch getHintBgLightGray()
	{
		return getRaw(IMGS_ROOT+"/hint_bg_lightblue_gray.9.png");
	}
	
	/**
	 * Gets the tips bg.
	 *
	 * @return the tips bg
	 */
	public NinePatch getTipsBg()
	{
		return getRaw(IMGS_ROOT+"/tips_bg.9.png");
	}
	
	/**
	 * Gets the orange baloon.
	 *
	 * @return the orange baloon
	 */
	public NinePatch getOrangeBaloon()
	{
		return getRaw(IMGS_ROOT+"/orange_baloon1.9.png");
	}
	
	/**
	 * Gets the border icon_ shadow1.
	 *
	 * @return the border icon_ shadow1
	 */
	public NinePatch getBorderIcon_Shadow1()
	{
		return getRaw(IMGS_ROOT+"/shadow1.9.png");
	}
	
	/**
	 * Gets the border icon_ shadow2.
	 *
	 * @return the border icon_ shadow2
	 */
	public NinePatch getBorderIcon_Shadow2()
	{
		return getRaw(IMGS_ROOT+"/shadow2.9.png");
	}
	
	/**
	 * Gets the border icon_plain gray.
	 *
	 * @return the border icon_plain gray
	 */
	public NinePatch getBorderIcon_plainGray()
	{
		return getRaw(IMGS_ROOT+"/plain_gray1.9.png");
	}
	
	/**
	 * Gets the border icon_ shadow3.
	 *
	 * @return the border icon_ shadow3
	 */
	public NinePatch getBorderIcon_Shadow3()
	{
		return getRaw(IMGS_ROOT+"/frame_shadow_border4.9.png");
	}

}
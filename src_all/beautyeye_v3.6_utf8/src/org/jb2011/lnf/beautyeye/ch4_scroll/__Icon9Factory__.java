/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __Icon9Factory__.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch4_scroll;

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
	 * Gets the scroll bar_v.
	 *
	 * @return the scroll bar_v
	 */
	public NinePatch getScrollBar_v()
	{
		return getRaw(IMGS_ROOT+"/scroll_bar_v.9.png");
	}
	
	/**
	 * Gets the scroll bar_rover_v.
	 *
	 * @return the scroll bar_rover_v
	 */
	public NinePatch getScrollBar_rover_v()
	{
		return getRaw(IMGS_ROOT+"/scroll_bar_rover_v.9.png");
	}
	
	/**
	 * Gets the scroll bar_pressed_v.
	 *
	 * @return the scroll bar_pressed_v
	 */
	public NinePatch getScrollBar_pressed_v()
	{
		return getRaw(IMGS_ROOT+"/scroll_bar_pressed_v.9.png");
	}
	
	/**
	 * Gets the scroll bar_h.
	 *
	 * @return the scroll bar_h
	 */
	public NinePatch getScrollBar_h()
	{
		return getRaw(IMGS_ROOT+"/scroll_bar_h.9.png");
	}
	
	/**
	 * Gets the scroll bar_rover_h.
	 *
	 * @return the scroll bar_rover_h
	 */
	public NinePatch getScrollBar_rover_h()
	{
		return getRaw(IMGS_ROOT+"/scroll_bar_rover_h.9.png");
	}
	
	/**
	 * Gets the scroll bar_pressed_h.
	 *
	 * @return the scroll bar_pressed_h
	 */
	public NinePatch getScrollBar_pressed_h()
	{
		return getRaw(IMGS_ROOT+"/scroll_bar_pressed_h.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to bottom.
	 *
	 * @return the scroll bar arrow_to bottom
	 */
	public NinePatch getScrollBarArrow_toBottom()
	{
		return getRaw(IMGS_ROOT+"/arrow_toBottom.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to top.
	 *
	 * @return the scroll bar arrow_to top
	 */
	public NinePatch getScrollBarArrow_toTop()
	{
		return getRaw(IMGS_ROOT+"/arrow_toTop.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to left.
	 *
	 * @return the scroll bar arrow_to left
	 */
	public NinePatch getScrollBarArrow_toLeft()
	{
		return getRaw(IMGS_ROOT+"/arrow_toLeft.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to right.
	 *
	 * @return the scroll bar arrow_to right
	 */
	public NinePatch getScrollBarArrow_toRight()
	{
		return getRaw(IMGS_ROOT+"/arrow_toRight.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to top_rover.
	 *
	 * @return the scroll bar arrow_to top_rover
	 */
	public NinePatch getScrollBarArrow_toTop_rover()
	{
		return getRaw(IMGS_ROOT+"/arrow_toTop_rover.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to bottom_rover.
	 *
	 * @return the scroll bar arrow_to bottom_rover
	 */
	public NinePatch getScrollBarArrow_toBottom_rover()
	{
		return getRaw(IMGS_ROOT+"/arrow_toBottom_rover.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to left_rover.
	 *
	 * @return the scroll bar arrow_to left_rover
	 */
	public NinePatch getScrollBarArrow_toLeft_rover()
	{
		return getRaw(IMGS_ROOT+"/arrow_toLeft_rover.9.png");
	}
	
	/**
	 * Gets the scroll bar arrow_to right_rover.
	 *
	 * @return the scroll bar arrow_to right_rover
	 */
	public NinePatch getScrollBarArrow_toRight_rover()
	{
		return getRaw(IMGS_ROOT+"/arrow_toRight_rover.9.png");
	}
	
	/**
	 * Gets the scroll pane border bg.
	 *
	 * @return the scroll pane border bg
	 */
	public NinePatch getScrollPaneBorderBg()
	{
		return getRaw(IMGS_ROOT+"/scroll_pane_bg1.9.png");
	}
}
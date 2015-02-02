/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __Icon9Factory__.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch5_table;

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
	 * Gets the table scroll border1.
	 *
	 * @return the table scroll border1
	 */
	public NinePatch getTableScrollBorder1()
	{
		return getRaw(IMGS_ROOT+"/table_scrollborder1.9.png");
	}
	
	/**
	 * Gets the table header cell bg1.
	 *
	 * @return the table header cell bg1
	 */
	public NinePatch getTableHeaderCellBg1()
	{
		return getRaw(IMGS_ROOT+"/table_header_bg1.9.png");
	}
	
	/**
	 * Gets the table header cell separator1.
	 *
	 * @return the table header cell separator1
	 */
	public NinePatch getTableHeaderCellSeparator1()
	{
		return getRaw(IMGS_ROOT+"/table_header_separator1.9.png");
	}
}
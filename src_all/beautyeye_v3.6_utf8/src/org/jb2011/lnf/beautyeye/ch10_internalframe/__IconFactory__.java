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
package org.jb2011.lnf.beautyeye.ch10_internalframe;

import javax.swing.ImageIcon;

import org.jb2011.lnf.beautyeye.utils.RawCache;

/**
 * 普通图片工厂类.
 * 
 * @author Jack Jiang
 * @version 1.0, 2015-02-01
 * @since 3.6
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
	 * 窗口关闭图标.
	 *
	 * @return the internal frame close icon
	 */
	public ImageIcon getInternalFrameCloseIcon()
	{
		return getImage(IMGS_ROOT+"/frame_close_over.png");
	}
	
	/**
	 * 窗口最小化图标.
	 *
	 * @return the internal frame min icon
	 */
	public ImageIcon getInternalFrameMinIcon()
	{
		return getImage(IMGS_ROOT+"/frame_windowize_over.png");
	}
	
	/**
	 * 窗口最大化图标.
	 *
	 * @return the internal frame max icon
	 */
	public ImageIcon getInternalFrameMaxIcon()
	{
		return getImage(IMGS_ROOT+"/frame_maximize_over.png");
	}
	
	/**
	 * 窗口恢复图标.
	 *
	 * @return the internal iconfied icon
	 */
	public ImageIcon getInternalIconfiedIcon()
	{
		return getImage(IMGS_ROOT+"/frame_minimize_over.png");
	}
	
	/**
	 * Gets the internel frame icon.
	 *
	 * @return the internel frame icon
	 */
	public ImageIcon getInternalFrameIcon()
	{
		return getImage(IMGS_ROOT+"/ifi1.png");
	}
	
//	/**
//	 * Gets the frame icon_16_16.
//	 *
//	 * @return the frame icon_16_16
//	 */
//	public ImageIcon getFrameIcon_16_16()
//	{
//		return getImage(IMGS_ROOT+"/default_frame_icon.png");
//	}
//	
//	/**
//	 * Gets the frame title head bg_active.
//	 *
//	 * @return the frame title head bg_active
//	 */
//	public ImageIcon getFrameTitleHeadBg_active()
//	{
//		return getImage(IMGS_ROOT+"/head_bg.png");
//	}
	
	/**
	 * Gets the frame title head bg_inactive.
	 *
	 * @return the frame title head bg_inactive
	 */
	public ImageIcon getFrameTitleHeadBg_inactive()
	{
		return getImage(IMGS_ROOT+"/head_inactive.png");
	}
	
//	/**
//	 * Gets the internal frame setup icon.
//	 *
//	 * @return the internal frame setup icon
//	 */
//	public ImageIcon getInternalFrameSetupIcon()
//	{
//		return getImage(IMGS_ROOT+"/frame_setup_normal.png");
//	}
}

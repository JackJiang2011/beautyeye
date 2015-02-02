/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __UI__.java at 2015-2-1 20:25:36, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch12_progress;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class __UI__.
 */
public class __UI__
{
	
	/**
	 * Ui impl.
	 */
	public static void uiImpl()
	{
		UIManager.put("ProgressBar.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("ProgressBar.selectionForeground",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		//* 此属性决定水平进度条的默认最小大小：15是相关于.9.png图片的最小填充
		//* 高度或长度的(小于此高度则NinePatch算法无法解决而很难看)
		UIManager.put("ProgressBar.horizontalSize",new DimensionUIResource(146,15));//默认是146,12
		//* 此属性决定垂直进度条的默认最小大小：15是相关于.9.png图片的最小填充
		//* 高度或长度的(小于此高度则NinePatch算法无法解决而很难看)
		UIManager.put("ProgressBar.verticalSize",new DimensionUIResource(15,146));//默认是12,146
		UIManager.put("ProgressBar.border",new BorderUIResource(BorderFactory.createEmptyBorder(0,0,0,0)));
		UIManager.put("ProgressBarUI",org.jb2011.lnf.beautyeye.ch12_progress.BEProgressBarUI.class.getName());
	}
}

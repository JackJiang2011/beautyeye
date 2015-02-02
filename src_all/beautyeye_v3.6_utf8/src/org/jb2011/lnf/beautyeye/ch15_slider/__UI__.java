/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __UI__.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch15_slider;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

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
		UIManager.put("Slider.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		//JSlider的刻度线绘制颜色
		UIManager.put("Slider.tickColor",new ColorUIResource(new Color(154,154,154)));
		UIManager.put("Slider.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		//获得焦点时的insets
//		UIManager.put("Slider.focusInsets",new InsetsUIResource(2,2,7,7));//父类中默认是2,2,2,2
		//获得焦点时的焦点边框颜色
		UIManager.put("Slider.focus",new ColorUIResource(BeautyEyeLNFHelper.commonFocusedBorderColor));//windows父类中默认是[r=113,g=111,b=100]
		UIManager.put("SliderUI",org.jb2011.lnf.beautyeye.ch15_slider.BESliderUI.class.getName());
	}
}

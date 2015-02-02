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
package org.jb2011.lnf.beautyeye.ch8_toolbar;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

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
		//~* @since 3.4, add by Jack Jiang 2012-11-05
		//~* 【BeautyEye外观的特有定制属性】：true表示BEToolBarUI里，将使用其它典型外观
		//~*  一样的默认纯色填充背景（颜色由ToolBar.background属性指定）, 否则将使用BeautyEye
		//~*  默认的渐变NinePatch图实现背景的填充。另外，还可以使用
		//~*  JToolBar.putClientProperty("ToolBar.isPaintPlainBackground", Boolean.TRUE);来进行
		//~*  独立控制背景的填充方法，ClientProperty相比UIManager中的本方法拥有最高优先级
		UIManager.put("ToolBar.isPaintPlainBackground", Boolean.FALSE);
		//此属性目前用于ToolBar.border中表示触点的颜色
		UIManager.put("ToolBar.shadow", new ColorUIResource(new Color(180,183,187)));
		//此属性目前用于ToolBar.border中表示触点的立体阴影效果颜色
		UIManager.put("ToolBar.highlight", new ColorUIResource(Color.white));
		UIManager.put("ToolBar.dockingBackground",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("ToolBar.floatingBackground",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("ToolBar.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("ToolBar.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		//工具栏的border实现
		UIManager.put("ToolBar.border",new BorderUIResource(
//				com.sun.java.swing.plaf.windows.WindowsBorders.getToolBarBorder()));
				new org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarUI.ToolBarBorder(UIManager.getColor("ToolBar.shadow"),
						UIManager.getColor("ToolBar.highlight"), new Insets(6, 0, 11, 0))));
//				BorderFactory.createEmptyBorder(5, 0, 8, 0)));//5, 5, 8, 5)));
		//分隔条ui实现
		UIManager.put("ToolBarSeparatorUI"
				, org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarSeparatorUI.class.getName());
		UIManager.put("ToolBarUI",org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarUI.class.getName());
	}
}

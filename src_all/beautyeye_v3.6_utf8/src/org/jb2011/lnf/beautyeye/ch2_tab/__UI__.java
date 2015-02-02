/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __UI__.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch2_tab;

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
		UIManager.put("TabbedPane.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("TabbedPane.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
//		UIManager.put("TabbedPane.tabRunOverlay", 2);//本属性无效果
		//false表示tab在边框虑线之上而不是重叠效果
		UIManager.put("TabbedPane.tabsOverlapBorder",true);
		UIManager.put("TabbedPaneUI",org.jb2011.lnf.beautyeye.ch2_tab.BETabbedPaneUI.class.getName());
		//此属性决定了整个JTabbedPane区域的内衬
		UIManager.put("TabbedPane.tabAreaInsets"
				,new javax.swing.plaf.InsetsUIResource(3,20,2,20));//默认是3,2,2,2
		//此属性决定了tab与内容面板间的空白
		UIManager.put("TabbedPane.contentBorderInsets"
				,new javax.swing.plaf.InsetsUIResource(2,0,3,0));//默认是2,2,3,3
		//此参数将决定选中时的tab与左右相邻tab的重合度，正值表示重合、负值表示间隔（空白）
		UIManager.put("TabbedPane.selectedTabPadInsets"//* 注意与NP图的边缘留白配合使用能达到更灵活的效果
				,new javax.swing.plaf.InsetsUIResource(0,1,0,2));//默认是 2,2,2,1
		//此属性决定了JTabbedPane的tab标签的内衬
		UIManager.put("TabbedPane.tabInsets",new javax.swing.plaf.InsetsUIResource(7,15,9,15));//默认是1,4,1,4
		//获得焦点时的虚线框颜色
		UIManager.put("TabbedPane.focus",new ColorUIResource(130,130,130));
		ColorUIResource highlight = new ColorUIResource(BeautyEyeLNFHelper.commonFocusedBorderColor);//192,192,192);
		//在BE LNF中，此颜色将决定TabPlacement=TOP和LEFT二种类型TabbedPane的内容面板那条虚线的颜色
		UIManager.put("TabbedPane.highlight",highlight);//new Color(200,200,200)));
		//在BE LNF中，此颜色将决定TabPlacement=RIGHT和BOTTOM二种类型TabbedPane的内容面板那条虚线的颜色
		UIManager.put("TabbedPane.shadow",highlight);
		//在BE LNF中，因TabPlacement=RIGHT和BOTTOM二种类型时，父类方法会默认再多画一条深色立体线而使得在BE LNF中
		//不好看，此颜色设置的目的就是让此立体阴影线与背景色一致从而看不出它的效果，进而不影响内容面板那条虚线在BE LNF中的视觉效果
		UIManager.put("TabbedPane.darkShadow"
				,new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
	}
}

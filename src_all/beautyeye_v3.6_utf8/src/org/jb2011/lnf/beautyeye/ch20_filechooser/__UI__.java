/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __UI__.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch20_filechooser;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;

/**
 * The Class __UI__.
 */
public class __UI__
{
	//
	/**
	 * Ui impl.
	 */
	public static void uiImpl()
	{
		//文件查看列表的边框实现
		UIManager.put("FileChooser.listViewBorder"
				, new BorderUIResource(new org.jb2011.lnf.beautyeye.ch4_scroll.ScrollPaneBorder()));//com.sun.java.swing.plaf.windows.XPStyle.XPFillBorder);
		//此颜色将决定windows平台下文件选择面板的左边WindowsPlaceBar的背景色
		UIManager.put("ToolBar.shadow",new ColorUIResource(new Color(249,248,243)));
	}
	
	//本方法仅供Windows平台使用
	/**
	 * Ui impl_win.
	 */
	public static void uiImpl_win()
	{
		//JFileChooser的UI实现使用windows平台外观和行为UI，提高用户体验
		UIManager.put("FileChooserUI"
				,org.jb2011.lnf.beautyeye.ch20_filechooser.BEFileChooserUIWin.class.getName());
	}
	
	//本方法仅供通用跨平台主类使用
	/**
	 * Ui impl_cross.
	 */
	public static void uiImpl_cross()
	{
		UIManager.put("FileChooserUI"
				,org.jb2011.lnf.beautyeye.ch20_filechooser.BEFileChooserUICross.class.getName());
	}
}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BeautyEyeLookAndFeelWin.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.InsetsUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import org.jb2011.lnf.beautyeye.winlnfutils.WinUtils;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * <p>
 * BeautyEye Swing外观实现方案 - Windows平台专用外观实现主类.<br>
 * <p>
 * 本主题主类仅供Windows下使用，它将使用Windows操作系统默认的字体等设置.
 * 
 * @author Jack Jiang(jb2011@163.com)
 * @version 1.0
 */
// * 参考源码基于JDK_1.6.0_u18.
public class BeautyEyeLookAndFeelWin extends WindowsLookAndFeel
{
	static{
		initLookAndFeelDecorated();
	}
	
	/**
	 * Instantiates a new beauty eye look and feel win.
	 *
	 * @see BeautyEyeLNFHelper#implLNF()
	 * @see org.jb2011.lnf.beautyeye.ch20_filechooser.__UI__#uiImpl_win()
	 * @see #initForVista()
	 */
	public BeautyEyeLookAndFeelWin()
	{
		super();

		BeautyEyeLNFHelper.implLNF();
		
		//自定义JFileChooser的L&F实现（为了解决windows LNF下文件选择框UI未实现背景填充问题）
		org.jb2011.lnf.beautyeye.ch20_filechooser.__UI__.uiImpl_win();
		
		//针对Vista及更新的windows平台进行特殊设置
		initForVista();
	}
	
	/**
	 * 因Windos LNF会在Vista及更新的操作系统（如win7）上对Windows LNF作附加设置，
	 * 以保证与Vista及更新平台的外观的一致性（如让菜单菜项高度更大等），请参见
	 * WindowsLookAndFeel.initVistaComponentDefaults(..)。
	 * <p>
	 * BeautyEye中因需要保证审美一致性（在所有win平台上）而不需要这些额外的设置，
	 * 但因该方法是private私有方法，无法进行覆盖屏蔽，所以只能在此单列方法，以便针对Vista
	 * 及更新的平台进行补救性重新设置以便与BeautyEye LNF的审核进行适配.
	 * 
	 * @see WindowsLookAndFeel.initVistaComponentDefaults(..)
	 */
	protected void initForVista()
	{
		if(WinUtils.isOnVista())
		{
			UIManager.put("CheckBoxMenuItem.margin",new InsetsUIResource(0,0,0,0));
			UIManager.put("RadioButtonMenuItem.margin",new InsetsUIResource(0,0,0,0));
			UIManager.put("Menu.margin",new InsetsUIResource(0,0,0,0));//windows lnf xp中默认是2，2，2，2
			UIManager.put("MenuItem.margin",new InsetsUIResource(0,0,0,0));//windows lnf中  xp默认是2，2，2，2
			
			UIManager.put("Menu.border",new BorderUIResource(BorderFactory.createEmptyBorder(1,3,2,3)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
			UIManager.put("MenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(1,0,2,0)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
			UIManager.put("CheckBoxMenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,2,4,2)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
			UIManager.put("RadioButtonMenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,0,4,0)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;		
//			UIManager.put("PopupMenu.border",new BorderUIResource(BorderFactory.createEmptyBorder(20,10,20,10)));//	
		
			UIManager.put("CheckBoxMenuItem.checkIcon"
					,new org.jb2011.lnf.beautyeye.ch9_menu.BECheckBoxMenuItemUI.CheckBoxMenuItemIcon().setUsedForVista(true));//javax.swing.plaf.basic.BasicIconFactory.CheckBoxMenuItemIcon);
			UIManager.put("RadioButtonMenuItem.checkIcon"
					,new org.jb2011.lnf.beautyeye.ch9_menu.BERadioButtonMenuItemUI.RadioButtonMenuItemIcon().setUsedForVista(true));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sun.java.swing.plaf.windows.WindowsLookAndFeel#getName()
	 */
	@Override 
	public String getName() 
	{
        return "BeautyEyeWin";
    }

	/* (non-Javadoc)
	 * @see com.sun.java.swing.plaf.windows.WindowsLookAndFeel#getID()
	 */
	@Override 
    public String getID() 
    {
        return "BeautyEyeWin";
    }

	/* (non-Javadoc)
	 * @see com.sun.java.swing.plaf.windows.WindowsLookAndFeel#getDescription()
	 */
	@Override 
    public String getDescription() 
    {
        return "BeautyEye windows-platform L&F developed by Jack Jiang(jb2011@163.com).";
    }
	
	/**
	 * Gets the supports window decorations.
	 *
	 * @return the supports window decorations
	 * {@inheritDoc}
	 */
	@Override 
	public boolean getSupportsWindowDecorations() 
	{
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected void initComponentDefaults(UIDefaults table)
    {
		super.initComponentDefaults(table);
        initOtherResourceBundle(table);
    }
	/**
     * Initialize the defaults table with the name of the other ResourceBundle
     * used for getting localized defaults.
     */
    protected void initOtherResourceBundle(UIDefaults table)
    {
        table.addResourceBundle( "org.jb2011.lnf.beautyeye.resources.beautyeye" );
    }
	
	/**
	 * 据BeautyEyeLNFHelper.frameBorderStyle指明的窗口边框类型来
	 * 决定是否使用操作系统相关的窗口装饰样式.
	 */
	static void initLookAndFeelDecorated()
	{
		if(BeautyEyeLNFHelper.frameBorderStyle == FrameBorderStyle.osLookAndFeelDecorated)
		{
			JFrame.setDefaultLookAndFeelDecorated(false);
			JDialog.setDefaultLookAndFeelDecorated(false);
		}
		else
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		}
		
//		UIManager.put("swing.aatext", Boolean.FALSE);
	}
}

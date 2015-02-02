/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BeautyEyeLookAndFeelCross.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

// TODO: Auto-generated Javadoc
/**
 *<p>
 * BeautyEye Swing外观实现方案 - 跨平台通用外观实现主类.<br>
 * <p>
 * 本主题主类仅供跨平台时使用，它可用于Java支持的所有操作系统.
 * 
 * @author Jack Jiang(jb2011@163.com)
 * @version 1.0
 */
// * 参考源码基于JDK_1.6.0_u18.
//如果要继承BasicLookAndFeel实现跨平台lnf 则还需要做更多的工作，目前
//跨平台时干脆继承MetalLookAndFeel以便站在巨人的肩膀上，节省一些工作量
public class BeautyEyeLookAndFeelCross extends MetalLookAndFeel
{
	static{
		BeautyEyeLookAndFeelWin.initLookAndFeelDecorated();
	}
	
	/**
	 * Instantiates a new beauty eye look and feel cross.
	 */
	public BeautyEyeLookAndFeelCross()
	{
		super();

//		//本属性仅对windows平台有效？！ -> Jack Jiang最终证实没效果！！！！！！！！！！！
//		UIManager.put("Application.useSystemFontSettings", Boolean.TRUE);
		//取消Metal LNF中默认的粗体字
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		//此项如是true，则将会为TabbedPane的内容面板填充天蓝色背景
		UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
		//此项如是true，则将会为TabbedPane的标签填充天蓝色背景
		UIManager.put("TabbedPane.tabsOpaque", Boolean.FALSE);
		BeautyEyeLNFHelper.implLNF();
		
		//自定义JFileChooser的L&F实现（为了解决JFileChooser中的文件查看列表的行高问题）
		org.jb2011.lnf.beautyeye.ch20_filechooser.__UI__.uiImpl_cross();
		
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		JDialog.setDefaultLookAndFeelDecorated(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalLookAndFeel#getName()
	 */
	@Override 
	public String getName() 
	{
        return "BeautyEyeCross";
    }

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalLookAndFeel#getID()
	 */
	@Override 
    public String getID() 
    {
        return "BeautyEyeCross";
    }

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalLookAndFeel#getDescription()
	 */
	@Override 
    public String getDescription() 
    {
        return "BeautyEye cross-platform L&F developed by Jack Jiang(jb2011@163.com).";
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

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalLookAndFeel#isNativeLookAndFeel()
	 */
	@Override
	public boolean isNativeLookAndFeel()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.MetalLookAndFeel#isSupportedLookAndFeel()
	 */
	@Override
	public boolean isSupportedLookAndFeel()
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
}

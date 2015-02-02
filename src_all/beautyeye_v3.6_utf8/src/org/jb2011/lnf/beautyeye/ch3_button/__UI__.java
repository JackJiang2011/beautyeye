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
package org.jb2011.lnf.beautyeye.ch3_button;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

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
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> JButton相关ui属性设定
		UIManager.put("Button.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		//Button.foreground的设定不起效，这可能是LNF里的bug，因NLLookAndFeel
		//是继承自它们所以暂时无能为力，就这么的吧，以后再说
		UIManager.put("Button.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		
		//以下属性将决定按钮获得焦点时的焦点虚线框的绘制偏移量哦
		UIManager.put("Button.dashedRectGapX",3);//windows LNF中默认是3
		UIManager.put("Button.dashedRectGapY",3);//windows LNF中默认是3
		UIManager.put("Button.dashedRectGapWidth",6);//windows LNF中默认是6
		UIManager.put("Button.dashedRectGapHeight",6);//windows LNF中默认是6
		
		UIManager.put("ButtonUI",org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.class.getName());
		UIManager.put("Button.margin",new InsetsUIResource(6, 8, 6, 8));//new InsetsUIResource(6, 8, 6, 8));
		//此border可以与Button.margin连合使用，而者之和即查整个Button的内衬哦
		UIManager.put("Button.border" ,new org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI
					.XPEmptyBorder(new Insets(3,3,3,3)));//default is 3,3,3,3
		//获得焦点时的虚线框颜色
		UIManager.put("Button.focus",new ColorUIResource(130,130,130));

		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> JToggleButton相关ui属性设定
		//注意：本属性不要与ToggleButton.border混用，因为没有它的优先级高，
		//另本参数如用InsetsUIResource则不会有效果，具体原因待查（本属性也将决定toolbar的整体高度和宽度哦）
		UIManager.put("ToggleButton.margin",new Insets(3, 11, 3, 11));//4, 8, 4, 8));////4, 12, 4, 12));
		UIManager.put("ToggleButton.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("ToggleButton.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		//用于ToggleButon被选中时的前景色
		//注：在原WindowsLookAndFeel中，本属性存在（值是Color(0,0,0,)）但在UI里没有用到
		//，此处被jb2011定义为“选中时的前景色”，当然也可以自已定名称，参见 NLWindowsToggleButtonUI2.paintText(..)
		UIManager.put("ToggleButton.focus",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));//new ColorUIResource(Color.white)));//
		UIManager.put("ToggleButtonUI",org.jb2011.lnf.beautyeye.ch3_button.BEToggleButtonUI.class.getName());
		//以下设置对ToggleButton在不加入到JToolBar时是有效果的哦！！！！！！！！！！！
		Border toggleButtonBorder = new BorderUIResource(new MarginBorder());
//		UIManager.put("ToggleButton.margin",new InsetsUIResource(2, 30, 2, 30));
		UIManager.put("ToggleButton.border",toggleButtonBorder);
		/* ~~注：这个属性是Jack Jiang为了更好的ui效果自已加的属性：焦点虚线的颜色 */
		UIManager.put("ToggleButton.focusLine"
				,new ColorUIResource(BeautyEyeLNFHelper.commonFocusedBorderColor.darker()));
		/* ~~注：这个属性是Jack Jiang为了更好的ui效果自已加的属性：焦点虚线的高亮立体阴影颜色 */
		UIManager.put("ToggleButton.focusLineHilight",new ColorUIResource(new Color(240,240,240)));
	}
}

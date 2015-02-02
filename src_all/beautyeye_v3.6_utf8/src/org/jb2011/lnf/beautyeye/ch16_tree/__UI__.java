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
package org.jb2011.lnf.beautyeye.ch16_tree;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
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
		UIManager.put("Tree.background",new ColorUIResource(Color.white));
		UIManager.put("Tree.textBackground",new ColorUIResource(Color.white));
//		UIManager.put("Tree.drawsFocusBorderAroundIcon",new Boolean(false));
		UIManager.put("Tree.selectionForeground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionForegroundColor));
		UIManager.put("Tree.selectionBackground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionBackgroundColor));
		UIManager.put("Tree.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		UIManager.put("Tree.selectionBorderColor",new ColorUIResource(BeautyEyeLNFHelper.commonFocusedBorderColor));//windows父类中默认是0,0,0
		
		UIManager.put("Tree.openIcon",__IconFactory__.getInstance().getTreeDefaultOpenIcon_16_16());
		UIManager.put("Tree.closedIcon",__IconFactory__.getInstance().getTreeDefaultClosedIcon_16_16());
		UIManager.put("Tree.leafIcon",__IconFactory__.getInstance().getTreeDefaultLeafIcon_16_16());
		UIManager.put("Tree.expandedIcon",__IconFactory__.getInstance().getTreeA());
//				,new org.jb2011.lnf.windows2.ch16.BETreeUI.ExpandedIcon());
		UIManager.put("Tree.collapsedIcon",__IconFactory__.getInstance().getTreeB());
//				,new org.jb2011.lnf.windows2.ch16.BETreeUI.CollapsedIcon());
		
		//不绘制层次线
		UIManager.put("Tree.paintLines", false);//default is true
		//行高
		UIManager.put("Tree.rowHeight", 18);//default is 16
		//未选中时单元前景色（备选MacOSX黑 (35,35,35)）
		UIManager.put("Tree.textForeground", new ColorUIResource(70,70,70));
		//处于编辑状态时的文本框边框，因BE LNF中文本框无边框（事实上它是用N9图实现的背景
		//边框视觉效果），所以此处要去掉，但加多点空白，与背景配合起来好看点
		UIManager.put("Tree.editorBorder"
				, new BorderUIResource(BorderFactory.createEmptyBorder(1,5,1,5)));//Windows LNF中默认是LineBorderUIResource
		
		UIManager.put("TreeUI",org.jb2011.lnf.beautyeye.ch16_tree.BETreeUI.class.getName());
	}
}

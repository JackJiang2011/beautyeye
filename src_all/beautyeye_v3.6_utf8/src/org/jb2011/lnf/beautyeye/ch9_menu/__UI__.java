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
package org.jb2011.lnf.beautyeye.ch9_menu;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;

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
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> margin和border设置 START
//		UIManager.put("MenuBar.border",new org.jb2011.lnf.windows2.ch10.EWindowsMenuBarUI.MenuBarBorder(Color.red,Color.BLUE));
		//去掉菜单条下方的border（默认是一个2个像素高的横线，参见WindowsMenuBarUI.MenuBarBorder）
		UIManager.put("MenuBar.border",BorderFactory.createEmptyBorder());
		
		//提示：margin与border并存设置情况容易产生混知，其实官方实现是：当设置margin时，则Border就使用
		//marginBorder，该Border就是用的这个margin来作它的Insets的
		//BueatyEye LNF中推荐实践是：抛弃margin的概念（省的混淆），直接使用border（在其上直接给于insets）即可
		UIManager.put("CheckBoxMenuItem.margin",new InsetsUIResource(0,0,0,0));//iuir);
		UIManager.put("RadioButtonMenuItem.margin",new InsetsUIResource(0,0,0,0));//iuir);
		UIManager.put("Menu.margin",new InsetsUIResource(0,0,0,0));//iuir);//windows lnf中默认是2，2，2，2
		UIManager.put("MenuItem.margin",new InsetsUIResource(0,0,0,0));//iuir);//windows lnf中默认是2，2，2，2//javax.swing.plaf.basic.BasicBorders$MarginBorder@1a1c887
//		UIManager.put("MenuItem.margin",new InsetsUIResource(10,2,10,2));//top=2,left=2,bottom=2,right=2
		
		//请注意：上面的margin已经全设为0了哦
		UIManager.put("Menu.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,3,5,3)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
		UIManager.put("MenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,3,5,3)));//
		UIManager.put("CheckBoxMenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,3,5,3)));//javax.swing.plaf.basic.BasicBorders.MarginBorder;
		UIManager.put("RadioButtonMenuItem.border",new BorderUIResource(BorderFactory.createEmptyBorder(4,3,5,3)));//		
//		UIManager.put("PopupMenu.border",new BorderUIResource(BorderFactory.createEmptyBorder(20,10,20,10)));//	
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> margin和border设置 END
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 颜色设置 START
		UIManager.put("MenuBar.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		UIManager.put("RadioButtonMenuItem.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		UIManager.put("Menu.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		UIManager.put("PopupMenu.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		UIManager.put("CheckBoxMenuItem.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		UIManager.put("MenuItem.foreground",new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
		
		UIManager.put("MenuBar.background",new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
		UIManager.put("Menu.background",new ColorUIResource(new Color(255,255,255,0)));
		UIManager.put("PopupMenu.background",new ColorUIResource(new Color(255,255,255,0)));
		
		UIManager.put("RadioButtonMenuItem.disabledForeground",new ColorUIResource(BeautyEyeLNFHelper.commonDisabledForegroundColor));
		UIManager.put("MenuItem.disabledForeground",new ColorUIResource(BeautyEyeLNFHelper.commonDisabledForegroundColor));
		
		UIManager.put("Menu.selectionForeground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionForegroundColor));
		UIManager.put("MenuItem.selectionForeground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionForegroundColor));
		UIManager.put("CheckBoxMenuItem.selectionForeground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionForegroundColor));
		UIManager.put("RadioButtonMenuItem.selectionForeground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionForegroundColor));
		
		UIManager.put("Menu.selectionBackground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionBackgroundColor));
		UIManager.put("MenuItem.selectionBackground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionBackgroundColor));
		UIManager.put("CheckBoxMenuItem.selectionBackground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionBackgroundColor));
		UIManager.put("RadioButtonMenuItem.selectionBackground",new ColorUIResource(BeautyEyeLNFHelper.commonSelectionBackgroundColor));
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 颜色设置 END

		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 杂项设置 START
		//本值意味着弹出菜单X轴方向上的偏移量，因BE LNF中加了边框，所以要负偏移以便得弹出菜单主体能与菜单项对齐好看一些
		UIManager.put("Menu.menuPopupOffsetX",-3);//win lnf中默认值是0
		//本值意味着弹出菜单Y轴方向上的偏移量，因BE LNF中加了边框，所以要负偏移以便得弹出菜单主体能与菜单项对齐好看一些
		UIManager.put("Menu.menuPopupOffsetY",2);///win lnf默认值是0
		//本值意味着弹出子菜单X轴方向上的偏移量，因BE LNF中加了边框，所以要负偏移以便得弹出菜单主体能与菜单项对齐好看一些
		UIManager.put("Menu.submenuPopupOffsetX", -2);///win lnf默认值是-4
		//本值意味着弹出子菜单Y轴方向上的偏移量，因BE LNF中加了边框，所以要负偏移以便得弹出菜单主体能与菜单项对齐好看一些
		UIManager.put("Menu.submenuPopupOffsetY", -5);///win lnf默认值是-3
		
		//多选按钮式的菜单项选中与否的图标实现设定
		UIManager.put("CheckBoxMenuItem.checkIcon",new org.jb2011.lnf.beautyeye.ch9_menu.BECheckBoxMenuItemUI.CheckBoxMenuItemIcon());//javax.swing.plaf.basic.BasicIconFactory.CheckBoxMenuItemIcon);
		//单选按钮式的菜单项选中与否的图标实现设定
		UIManager.put("RadioButtonMenuItem.checkIcon",new org.jb2011.lnf.beautyeye.ch9_menu.BERadioButtonMenuItemUI.RadioButtonMenuItemIcon());
		
		//加高菜单条，提升视觉体验
		UIManager.put("MenuBar.height",30);//default value is 19
		
		//此属性true时表明禁用的菜单项将不能被rover，否则有rover效果（BE LNF中
		//因禁用状态rover时的字体色影响视觉效果，所以干脆禁用之，逻辑上也很合理）
		UIManager.put("MenuItem.disabledAreNavigable",false);// windows lnf中默认是true
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 杂项设置 END
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> UI实现类设置 START
		UIManager.put("MenuBarUI",org.jb2011.lnf.beautyeye.ch9_menu.BEMenuBarUI.class.getName());
		UIManager.put("MenuUI",org.jb2011.lnf.beautyeye.ch9_menu.BEMenuUI.class.getName());
		UIManager.put("MenuItemUI",org.jb2011.lnf.beautyeye.ch9_menu.BEMenuItemUI.class.getName());
		UIManager.put("RadioButtonMenuItemUI",org.jb2011.lnf.beautyeye.ch9_menu.BERadioButtonMenuItemUI.class.getName());
		UIManager.put("CheckBoxMenuItemUI",org.jb2011.lnf.beautyeye.ch9_menu.BECheckBoxMenuItemUI.class.getName());
		UIManager.put("PopupMenuSeparatorUI",org.jb2011.lnf.beautyeye.ch9_menu.BEPopupMenuSeparatorUI.class.getName());
		UIManager.put("PopupMenuUI",org.jb2011.lnf.beautyeye.ch9_menu.BEPopupMenuUI.class.getName());
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> UI实现类设置 END
	}
}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * WinUtils.java at 2015-2-1 20:25:37, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.winlnfutils;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.utils.MySwingUtilities2;

// TODO: Auto-generated Javadoc
//* 本类中方法参考自 com.sun.java.swing.plaf.windows.WindowsGraphicsUtils
//* 拷贝至此为是使BE LNF在某些实现上与WindowsLNF一致而又要达到跨平台效果而做
/**
 * The Class WinUtils.
 */
public class WinUtils
{
	//* copy from WindowsLookAndFeel START 未做修改
	// Toggle flag for drawing the mnemonic state
	/** The is mnemonic hidden. */
	private static boolean isMnemonicHidden = true;
	/**
	 * Gets the state of the hide mnemonic flag. This only has meaning 
	 * if this feature is supported by the underlying OS.
	 *
	 * @return true if mnemonics are hidden, otherwise, false
	 * @see #setMnemonicHidden
	 * @since 1.4
	 */
	public static boolean isMnemonicHidden() {
		if (UIManager.getBoolean("Button.showMnemonics") == true) {
			// Do not hide mnemonics if the UI defaults do not support this
			isMnemonicHidden = false;
		}
		return isMnemonicHidden;
	}
	//* 注意：该方法在java7中移到了OSInfo非公开类了哦
    /**
	 * Checks if is on vista.
	 *
	 * @return true, if is on vista
	 */
	public static boolean isOnVista() {
        boolean rv = false;
        String osName = System.getProperty("os.name");
        String osVers = System.getProperty("os.version");
        if (osName != null
                && osName.startsWith("Windows")
                && osVers != null
                && osVers.length() > 0) {

            int p = osVers.indexOf('.');
            if (p >= 0) {
                osVers = osVers.substring(0, p);
            }

            try {
                rv = (Integer.parseInt(osVers) >= 6);//win7及更新的windows也在此之列
            } catch (NumberFormatException nfe) {
            }
        }
        return rv;
    }
	//* copy from WindowsLookAndFeel END

	//* copy from WindowsGraphicsUtils START (modified by jack jiang)
	/**
	 * Renders a text String in Windows without the mnemonic.
	 * This is here because the WindowsUI hiearchy doesn't match the Component heirarchy. All
	 * the overriden paintText methods of the ButtonUI delegates will call this static method.
	 * <p>
	 *
	 * @param g Graphics context
	 * @param b Current button to render
	 * @param textRect Bounding rectangle to render the text.
	 * @param text String to render
	 * @param textShiftOffset the text shift offset
	 */
	public static void paintText(Graphics g, AbstractButton b, 
			Rectangle textRect, String text,
			int textShiftOffset) {
//		FontMetrics fm = SwingUtilities2.getFontMetrics(b, g);
		FontMetrics fm = MySwingUtilities2.getFontMetrics(
				b, g);//* modified by Jack Jiang 为了非公开api的兼容性

		int mnemIndex = b.getDisplayedMnemonicIndex();
		// W2K Feature: Check to see if the Underscore should be rendered.
//		if (WindowsLookAndFeel.isMnemonicHidden() == true) {
		if (isMnemonicHidden() == true) {//* modified by jack jiang
			mnemIndex = -1;
		}
//
//		XPStyle xp = XPStyle.getXP();
//		if (xp != null && !(b instanceof JMenuItem)) 
//		{
//			paintXPText(b, g, textRect.x + textShiftOffset, 
//					textRect.y + fm.getAscent() + textShiftOffset,
//					text, mnemIndex);
//		} 
//		else 
		{
			paintClassicText(b, g, textRect.x + textShiftOffset, 
					textRect.y + fm.getAscent() + textShiftOffset,
					text, mnemIndex);
		}
	}
	
	/**
	 * Paint classic text.
	 *
	 * @param b the b
	 * @param g the g
	 * @param x the x
	 * @param y the y
	 * @param text the text
	 * @param mnemIndex the mnem index
	 */
	static void paintClassicText(AbstractButton b, Graphics g, int x, int y,
			String text, int mnemIndex) {
		ButtonModel model = b.getModel();

		/* Draw the Text */
		Color color = b.getForeground();
		if(model.isEnabled()) {
			/*** paint the text normally */
			if(!(b instanceof JMenuItem && model.isArmed()) 
					&& !(b instanceof JMenu && (model.isSelected() || model.isRollover()))) {
				/* We shall not set foreground color for selected menu or
				 * armed menuitem. Foreground must be set in appropriate
				 * Windows* class because these colors passes from
				 * BasicMenuItemUI as protected fields and we can't
				 * reach them from this class */
				g.setColor(b.getForeground());
			}
//			SwingUtilities2.drawStringUnderlineCharAt(b, g,text, mnemIndex, x, y);
			MySwingUtilities2.drawStringUnderlineCharAt(b
					, g,text, mnemIndex, x, y);//* modified by Jack Jiang 为了非公开api的兼容性
		} else {	/*** paint the text disabled ***/
			color        = UIManager.getColor("Button.shadow");
			Color shadow = UIManager.getColor("Button.disabledShadow");
			if(model.isArmed()) {
				color = UIManager.getColor("Button.disabledForeground");
			} else {
				if (shadow == null) {
					shadow = b.getBackground().darker();
				}
				g.setColor(shadow);
//				SwingUtilities2.drawStringUnderlineCharAt(b, g, text, mnemIndex,
//						x + 1, y + 1);
				MySwingUtilities2.drawStringUnderlineCharAt(b, g, text, mnemIndex,
						x + 1, y + 1);//* modified by Jack Jiang 为了非公开api的兼容性
			}
			if (color == null) {
				color = b.getBackground().brighter();
			}
			g.setColor(color);
//			SwingUtilities2.drawStringUnderlineCharAt(b, g, text, mnemIndex, x, y);
			MySwingUtilities2.drawStringUnderlineCharAt(
					b, g, text, mnemIndex, x, y);//* modified by Jack Jiang 为了非公开api的兼容性
			
		}
	}
	//* copy from WindowsGraphicsUtils END (modified by jack jiang)
    
//    /**
//     * Returns true if the specified widget is in a toolbar.
//     */
//    public static boolean isToolBarButton(JComponent c) {
//        return (c.getParent() instanceof JToolBar);
//    }
//    
//	//code all from WindowsLookAndFeel.java
//	public static boolean isOnVista() {
//        boolean rv = false;
//        String osName = System.getProperty("os.name");
//        String osVers = System.getProperty("os.version");
//        if (osName != null
//                && osName.startsWith("Windows")
//                && osVers != null
//                && osVers.length() > 0) {
//
//            int p = osVers.indexOf('.');
//            if (p >= 0) {
//                osVers = osVers.substring(0, p);
//            }
//
//            try {
//                rv = (Integer.parseInt(osVers) >= 6);
//            } catch (NumberFormatException nfe) {
//            }
//        }
//        return rv;
//    }
	
	//* copy from WindowsGraphicsUtils START
	/**
	 * 是否组件的排列方向是从左到右.
	 *
	 * @param c the c
	 * @return true, if is left to right
	 */
	public static boolean isLeftToRight(Component c)
	{
		return c.getComponentOrientation().isLeftToRight();
	}
	//* copy from WindowsGraphicsUtils END
}

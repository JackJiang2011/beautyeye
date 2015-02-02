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
package org.jb2011.lnf.beautyeye.ch7_popup;

import javax.swing.PopupFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class __UI__.
 */
public class __UI__
{
	
	/** The popup factory diy. */
	public static PopupFactory popupFactoryDIY = new TranslucentPopupFactory();
	
	/**
	 * Ui impl.
	 */
	public static void uiImpl()
	{
		PopupFactory.setSharedInstance(popupFactoryDIY);
	}
}

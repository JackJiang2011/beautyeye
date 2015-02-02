/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEToggleButtonUI.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch3_button;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToggleButtonUI;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;
import org.jb2011.lnf.beautyeye.utils.BEUtils;
import org.jb2011.lnf.beautyeye.utils.MySwingUtilities2;

import sun.awt.AppContext;


// TODO: Auto-generated Javadoc
/**
 * JToggleButton的UI实现类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类的实现参考了JDK1.6_u18的WindowsToggleButtonUI代码
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEToggleButtonUI extends BasicToggleButtonUI//WindowsToggleButtonUI//
{
    /** The Constant WINDOWS_TOGGLE_BUTTON_UI_KEY. */
    private static final Object WINDOWS_TOGGLE_BUTTON_UI_KEY = new Object();
    
    /** The nomal color. */
    private NormalColor nomalColor = BEButtonUI.NormalColor.normal;
    
    /**
     * Creates the ui.
     *
     * @param b the b
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent b) {
        AppContext appContext = AppContext.getAppContext();
        BEToggleButtonUI windowsToggleButtonUI = 
                (BEToggleButtonUI) appContext.get(WINDOWS_TOGGLE_BUTTON_UI_KEY);
        if (windowsToggleButtonUI == null) {
            windowsToggleButtonUI = new BEToggleButtonUI();
            appContext.put(WINDOWS_TOGGLE_BUTTON_UI_KEY, windowsToggleButtonUI);
        }
        return windowsToggleButtonUI;
    }
    
    //* 由Jack Jiang于2012-10-12日加入：重写本方法的目的是使得JToggleButton不填充
    //* BasicToggleButtonUI里的白色距形区，否则在JToolBar上时会因该白色距形区的存
    //* 在而使得与BEToolBarUI的渐变背景不协调（丑陋的创可贴效果），实际上
    //* WindowsToolButtonUI里有同名方法里也是它样处理的，详情参见WindowsToolButtonUI
    protected void installDefaults(AbstractButton b) {
    	super.installDefaults(b);
    	LookAndFeel.installProperty(b, "opaque", Boolean.FALSE);
    }

    // ********************************
    //         Paint Methods
    // ********************************
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicToggleButtonUI#paint(java.awt.Graphics, javax.swing.JComponent)
     */
    public void paint(Graphics g, JComponent c) 
    {
////    	if (NLXPStyle.getXP() != null) 
//    	{
    		BEButtonUI.paintXPButtonBackground(nomalColor,g, c);
//    	}
    	super.paint(g, c);
    }
    
    //copy from BasicToggleButtonUI,modified by jb2011 2012-06-15
    //修改的目的是让它在获得焦点（或说点中时）改变前景色，可惜父类中没有实现它，只能自已来解决了
    /**
     * As of Java 2 platform v 1.4 this method should not be used or overriden.
     * Use the paintText method which takes the AbstractButton argument.
     *
     * @param g the g
     * @param c the c
     * @param textRect the text rect
     * @param text the text
     */
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
    	AbstractButton b = (AbstractButton) c;                       
    	ButtonModel model = b.getModel();
    	FontMetrics fm = //SwingUtilities2
    					MySwingUtilities2.getFontMetrics(c, g);
    	int mnemonicIndex = b.getDisplayedMnemonicIndex();

    	/* Draw the Text */
    	if(model.isEnabled()) 
    	{
    		//=================== modified by jb2011 START
    		if(model.isSelected())//选中时使用不同的颜色
    			g.setColor(UIManager.getColor(getPropertyPrefix()+"focus"));
    		else
    			/*** paint the text normally */
    			g.setColor(b.getForeground());
    		//=================== modified by jb2011 END
    		
//    		SwingUtilities2 *不要直接调用该类（因为它是sun未公开api，1.5里与1.6及以后版它放在不同的包里，某天它会消失也说不好）
    		MySwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
    				textRect.x + getTextShiftOffset(),
    				textRect.y + fm.getAscent() + getTextShiftOffset());
    	}
    	else 
    	{
    		/*** paint the text disabled ***/
    		g.setColor(b.getBackground().brighter());
    		//SwingUtilities2 *不要直接调用该类（因为它是sun未公开api，1.5里与1.6及以后版它放在不同的包里，某天它会消失也说不好）
    		MySwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
    				textRect.x, textRect.y + fm.getAscent());
    		g.setColor(b.getBackground().darker());
    		//SwingUtilities2 *不要直接调用该类（因为它是sun未公开api，1.5里与1.6及以后版它放在不同的包里，某天它会消失也说不好）
    		MySwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
    				textRect.x - 1, textRect.y + fm.getAscent() - 1);
    	}
    }
    
    // Method signature defined here overriden in subclasses. 
    // Perhaps this class should be abstract?
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicButtonUI#paintFocus(java.awt.Graphics, javax.swing.AbstractButton, java.awt.Rectangle, java.awt.Rectangle, java.awt.Rectangle)
     */
    protected void paintFocus(Graphics g, AbstractButton b,
    		Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
    {
    	Rectangle bound = b.getVisibleRect();
    	//决定焦点要制的位置（当前实现是往内缩3个像素，与当前按钮背景配合）
    	final int delta = 3;
    	int x = bound.x + delta, y = bound.y + delta
    		, w = bound.width - delta * 2, h = bound.height - delta * 2;
    	
    	//绘制焦点虚线框
    	g.setColor(UIManager.getColor("ToggleButton.focusLine"));//*~ 这是Jack Jiang自定义的属性哦
    	BEUtils.drawDashedRect(g, x, y, w, h, 17, 17, 2, 2);
    	//再绘制焦点虚线框的立体高亮阴影，以便形成立体感
    	g.setColor(UIManager.getColor("ToggleButton.focusLineHilight"));//*~ 这是Jack Jiang自定义的属性哦
    	BEUtils.drawDashedRect(g, x + 1, y + 1, w, h, 17, 17, 2, 2);
    }
}


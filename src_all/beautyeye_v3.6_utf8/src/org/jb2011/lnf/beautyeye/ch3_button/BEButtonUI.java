/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEButtonUI.java at 2015-2-1 20:25:36, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch3_button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.JTextComponent;

import org.jb2011.lnf.beautyeye.utils.BEUtils;

/**
 * JButton的UI实现类.
 * 
 * @author Jack Jiang(jb2011@163.com)
 * @version 1.0
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类的实现参考了JDK1.6_u18中WindowsButtonUI的源码.
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEButtonUI extends BasicButtonUI
{
	
	/** The Constant xWindowsButtonUI. */
	private final static BEButtonUI xWindowsButtonUI = new BEButtonUI();
	
	/** The nomal color. */
	private NormalColor nomalColor = NormalColor.normal;
	
	/**
	 * 按钮颜色方案枚举类型。.
	 */
	public enum NormalColor
	{
		
		/** 普通灰色按钮. */
		normal,
		
		/** 绿色按钮. */
		green,
		
		/** 红色按钮. */
		red,
		
		/** 浅蓝色按钮. */
		lightBlue,
		
		/** 深蓝色按钮. */
		blue
	}
	
	/**
	 * Sets the normal color.
	 *
	 * @param nc the nc
	 * @return the bE button ui
	 */
	public BEButtonUI setNormalColor(NormalColor nc)
	{
		this.nomalColor = nc;
		return this;
	}

	/** The dashed rect gap x. */
	protected int dashedRectGapX;
	
	/** The dashed rect gap y. */
	protected int dashedRectGapY;
	
	/** The dashed rect gap width. */
	protected int dashedRectGapWidth;
	
	/** The dashed rect gap height. */
	protected int dashedRectGapHeight;

	/** The focus color. */
	protected Color focusColor;

	/** The defaults_initialized. */
	private boolean defaults_initialized = false;


	// ********************************
	//          Create PLAF
	// ********************************
	/**
	 * Creates the ui.
	 *
	 * @param c the c
	 * @return the component ui
	 */
	public static ComponentUI createUI(JComponent c){
		return xWindowsButtonUI;
	}


	// ********************************
	//            Defaults
	// ********************************
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicButtonUI#installDefaults(javax.swing.AbstractButton)
	 */
	protected void installDefaults(AbstractButton b) {
		super.installDefaults(b);
		b.setOpaque(false);
		
		if(!defaults_initialized) {
			String pp = getPropertyPrefix();
			dashedRectGapX = UIManager.getInt(pp + "dashedRectGapX");
			dashedRectGapY = UIManager.getInt(pp + "dashedRectGapY");
			dashedRectGapWidth = UIManager.getInt(pp + "dashedRectGapWidth");
			dashedRectGapHeight = UIManager.getInt(pp + "dashedRectGapHeight");
			focusColor = UIManager.getColor(pp + "focus");
			defaults_initialized = true;
		}

//		BEXPStyle xp = BEXPStyle.getXP();
//		if (xp != null) 
		{
			b.setBorder(new XPEmptyBorder(new Insets(3,3,3,3)));//xp.getBorder(b, getXPButtonType(b)));
			LookAndFeel.installProperty(b, "rolloverEnabled", Boolean.TRUE);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicButtonUI#uninstallDefaults(javax.swing.AbstractButton)
	 */
	protected void uninstallDefaults(AbstractButton b) {
		super.uninstallDefaults(b);
		defaults_initialized = false;
	}

	/**
	 * Gets the focus color.
	 *
	 * @return the focus color
	 */
	protected Color getFocusColor() {
		return focusColor;
	}

	// ********************************
	//         Paint Methods
	// ********************************

//	/**
//	 * Overridden method to render the text without the mnemonic
//	 */
//	protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) 
//	{
//		WindowsGraphicsUtils.paintText(g, b, textRect, text, getTextShiftOffset());
//	} 

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicButtonUI#paintFocus(java.awt.Graphics, javax.swing.AbstractButton, java.awt.Rectangle, java.awt.Rectangle, java.awt.Rectangle)
	 */
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect){
		//* 由Jack Jiang于2012-10-16日注释掉以下行：目的是修正当JButton处于JToolBar时不能绘制焦点的问题
//		if (b.getParent() instanceof JToolBar) {
//			// Windows doesn't draw the focus rect for buttons in a toolbar.
//			return;
//		}

		//** 被jb2011注释掉：目的是使得在任何情况下都绘制\获得焦点后的虚线框
//		if (NLXPStyle.getXP() != null) {
//			return;
//		}

		// focus painted same color as text on Basic??
		int width = b.getWidth();
		int height = b.getHeight();
		g.setColor(getFocusColor());
		
		//** modified by jb2011：绘制虚线方法改成可以设置虚线步进的方法，步进设为2则更好看一点
//		BasicGraphicsUtils.drawDashedRect(g, dashedRectGapX, dashedRectGapY,
//				width - dashedRectGapWidth, height - dashedRectGapHeight);
		// 绘制虚线框
		BEUtils.drawDashedRect(g, dashedRectGapX, dashedRectGapY,
				width - dashedRectGapWidth, height - dashedRectGapHeight);
		// 绘制虚线框的半透明白色立体阴影（半透明的用处在于若隐若现的效果比纯白要来的柔和的多）
		g.setColor(new Color(255,255,255,50));
		// 立体阴影就是向右下偏移一个像素实现的
		BEUtils.drawDashedRect(g, dashedRectGapX+1, dashedRectGapY+1,
				width - dashedRectGapWidth, height - dashedRectGapHeight);
	}

//	protected void paintButtonPressed(Graphics g, AbstractButton b)
//	{
//		setTextShiftOffset();
//	}

	// ********************************
	//          Layout Methods
	// ********************************
	/* (non-Javadoc)
 * @see javax.swing.plaf.basic.BasicButtonUI#getPreferredSize(javax.swing.JComponent)
 */
public Dimension getPreferredSize(JComponent c) {
		Dimension d = super.getPreferredSize(c);

		/* Ensure that the width and height of the button is odd,
		 * to allow for the focus line if focus is painted
		 */
		AbstractButton b = (AbstractButton)c;
		if (d != null && b.isFocusPainted()) {
			if(d.width % 2 == 0) { d.width += 1; }
			if(d.height % 2 == 0) { d.height += 1; }
		}
		return d;
	}


	/* These rectangles/insets are allocated once for all 
	 * ButtonUI.paint() calls.  Re-using rectangles rather than 
	 * allocating them in each paint call substantially reduced the time
	 * it took paint to run.  Obviously, this method can't be re-entered.
	 */
//	private static Rectangle viewRect = new Rectangle();

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicButtonUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paint(Graphics g, JComponent c) {
//		if (NLXPStyle.getXP() != null) 
		{
			paintXPButtonBackground(nomalColor,g, c);
		}
		super.paint(g, c);
	}

//	static Part getXPButtonType(AbstractButton b) { 
//		boolean toolbar = (b.getParent() instanceof JToolBar);
//		return toolbar ? Part.TP_BUTTON : Part.BP_PUSHBUTTON; 
//	}

	/**
 * Paint xp button background.
 *
 * @param nomalColor the nomal color
 * @param g the g
 * @param c the c
 */
public static void paintXPButtonBackground(NormalColor nomalColor,Graphics g, JComponent c)
	{
		AbstractButton b = (AbstractButton) c;

//		BEXPStyle xp = BEXPStyle.getXP();

		boolean toolbar = (b.getParent() instanceof JToolBar);
//		Part part = getXPButtonType(b);

		if (b.isContentAreaFilled())// && xp != null)
		{
			ButtonModel model = b.getModel();
//			Skin skin = xp.getSkin(b, part);

			// normal, rollover/activated/focus, pressed, disabled, default
//			State state = State.NORMAL;
			if (toolbar)
			{
//				if (model.isArmed() && model.isPressed())
//				{
//					state = State.PRESSED;
//				}
//				else if (!model.isEnabled())
//				{
//					state = State.DISABLED;
//				}
//				else if (model.isSelected() && model.isRollover())
//				{
//					state = State.HOTCHECKED;
//				}
//				else if (model.isSelected())
//				{
//					state = State.CHECKED;
//				}
//				else if (model.isRollover())
//				{
//					state = State.HOT;
//				}
			}
			else
			{
//				if (model.isArmed() && model.isPressed() || model.isSelected())
//				{
//					state = State.PRESSED;
//				}
//				else if (!model.isEnabled())
//				{
//					state = State.DISABLED;
//				}
//				else if (model.isRollover() || model.isPressed())
//				{
//					state = State.HOT;
//				}
//				else if (b instanceof JButton
//						&& ((JButton) b).isDefaultButton())
//				{
//					state = State.DEFAULTED;
//				}
//				else if (c.hasFocus())
//				{
//					state = State.HOT;
//				}
			}
			Dimension d = c.getSize();
			int dx = 0;
			int dy = 0;
			int dw = d.width;
			int dh = d.height;

			Border border = c.getBorder();
			Insets insets;
			if (border != null)
			{
				// Note: The border may be compound, containing an outer
				// opaque border (supplied by the application), plus an
				// inner transparent margin border. We want to size the
				// background to fill the transparent part, but stay
				// inside the opaque part.
				insets = BEButtonUI.getOpaqueInsets(border, c);
			}
			else
			{
				insets = c.getInsets();
			}
			if (insets != null)
			{
				dx += insets.left;
				dy += insets.top;
				dw -= (insets.left + insets.right);
				dh -= (insets.top + insets.bottom);
			}
			
			/*************************** 以下代码由jb2011改造自WindowsButtonUI START ********************/
			if(toolbar)
			{
				//此状态下JToggleButton和JButton使用各自的背景实现，2012-10-16前无论是不是JToggleButton都是使用该种实是不太合理的
				if(model.isRollover()||model.isPressed())
				{
					if(c instanceof JToggleButton)
						__Icon9Factory__.getInstance().getToggleButtonIcon_RoverGreen().draw((Graphics2D)g, dx, dy, dw, dh);
					else
						__Icon9Factory__.getInstance().getButtonIcon_PressedOrange().draw((Graphics2D)g, dx, dy, dw, dh);
				}
				else if(model.isSelected())//state == State.CHECKED)//||state == State.HOTCHECKED)
				{
					__Icon9Factory__.getInstance().getToggleButtonIcon_CheckedGreen().draw((Graphics2D)g, dx, dy, dw, dh);
				}
				else
				{
					//TODO 其它状态下的按钮背景样式需要完善，要不然看起来太硬！
//					skin.paintSkin(g, dx, dy, dw, dh, state);
				}
			}
			else
			{
				try
				{
					//TODO 其它状态下的按钮背景样式需要完善，要不然看起来太硬！
					
//					if(state == State.PRESSED)
					if(model.isArmed() && model.isPressed() || model.isSelected())
						__Icon9Factory__.getInstance().getButtonIcon_PressedOrange().draw((Graphics2D)g, dx, dy, dw, dh);
//					else if(state == State.DISABLED)
					else if (!model.isEnabled())
						__Icon9Factory__.getInstance().getButtonIcon_DisableGray().draw((Graphics2D)g, dx, dy, dw, dh);
					else if(model.isRollover())
						__Icon9Factory__.getInstance().getButtonIcon_rover().draw((Graphics2D)g, dx, dy, dw, dh);
					else
					{
						if(nomalColor==NormalColor.green)
						{
							__Icon9Factory__.getInstance().getButtonIcon_NormalGreen().draw((Graphics2D)g, dx, dy, dw, dh);
						}
						else if(nomalColor==NormalColor.red)
						{
							__Icon9Factory__.getInstance().getButtonIcon_NormalRed().draw((Graphics2D)g, dx, dy, dw, dh);
						}
						else if(nomalColor==NormalColor.blue)
						{
							__Icon9Factory__.getInstance().getButtonIcon_NormalBlue().draw((Graphics2D)g, dx, dy, dw, dh);
						}
						else if(nomalColor==NormalColor.lightBlue)
						{
							__Icon9Factory__.getInstance().getButtonIcon_NormalLightBlue().draw((Graphics2D)g, dx, dy, dw, dh);
						}
//						else if(nomalColor==NormalColor.red)
//						{
//							//红色按钮禁用状态时为更好地突出禁用状态，用深灰按钮
//							if(state == State.DISABLED)
//								__Icon9Factory__.getInstance().getButtonIcon_NormalGray().draw((Graphics2D)g, dx, dy, dw, dh);
//							else
//								__Icon9Factory__.getInstance().getButtonIcon_NormalRed().draw((Graphics2D)g, dx, dy, dw, dh);
//						}
						else
							__Icon9Factory__.getInstance().getButtonIcon_NormalGray().draw((Graphics2D)g, dx, dy, dw, dh);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			/*************************** 以下代码由JS改造自WindowsButtonUI END ********************/
		}
	}

	/**
	 * returns - b.getBorderInsets(c) if border is opaque
	 * - null if border is completely non-opaque
	 * - somewhere inbetween if border is compound and
	 * outside border is opaque and inside isn't
	 *
	 * @param b the b
	 * @param c the c
	 * @return the opaque insets
	 */
	private static Insets getOpaqueInsets(Border b, Component c) {
		if (b == null) {
			return null;
		}
		if (b.isBorderOpaque()) {
			return b.getBorderInsets(c);
		} else if (b instanceof CompoundBorder) {
			CompoundBorder cb = (CompoundBorder)b;
			Insets iOut = getOpaqueInsets(cb.getOutsideBorder(), c);
			if (iOut != null && iOut.equals(cb.getOutsideBorder().getBorderInsets(c))) {
				// Outside border is opaque, keep looking
				Insets iIn = getOpaqueInsets(cb.getInsideBorder(), c);
				if (iIn == null) {
					// Inside is non-opaque, use outside insets
					return iOut;
				} else {
					// Found non-opaque somewhere in the inside (which is
					// also compound).
					return new Insets(iOut.top + iIn.top, iOut.left + iIn.left,
							iOut.bottom + iIn.bottom, iOut.right + iIn.right);
				}
			} else {
				// Outside is either all non-opaque or has non-opaque
				// border inside another compound border
				return iOut;
			}
		} else {
			return null;
		}
	}
	
	//copy from XPStyle.XPEmptyBorder 代码没有修改
	/**
	 * The Class XPEmptyBorder.
	 */
	public static class XPEmptyBorder extends EmptyBorder implements UIResource 
	{
        
        /**
         * Instantiates a new xP empty border.
         *
         * @param m the m
         */
        public XPEmptyBorder(Insets m) {
            super(m.top+2, m.left+2, m.bottom+2, m.right+2);
        }

        /* (non-Javadoc)
         * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component)
         */
        public Insets getBorderInsets(Component c)       {
            return getBorderInsets(c, getBorderInsets());
        }

        /* (non-Javadoc)
         * @see javax.swing.border.EmptyBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
         */
        public Insets getBorderInsets(Component c, Insets insets)       {
            insets = super.getBorderInsets(c, insets);
                
            Insets margin = null;
            if (c instanceof AbstractButton) {
                Insets m = ((AbstractButton)c).getMargin();
                // if this is a toolbar button then ignore getMargin()
                // and subtract the padding added by the constructor
                if(c.getParent() instanceof JToolBar 
                   && ! (c instanceof JRadioButton)
                   && ! (c instanceof JCheckBox)
                   && m instanceof InsetsUIResource) {
                    insets.top -= 2;
                    insets.left -= 2;
                    insets.bottom -= 2;
                    insets.right -= 2;
                } else {
                    margin = m;
                }
            } else if (c instanceof JToolBar) {
                margin = ((JToolBar)c).getMargin();
            } else if (c instanceof JTextComponent) {
                margin = ((JTextComponent)c).getMargin();
            }
            if (margin != null) {
                insets.top    = margin.top + 2;
                insets.left   = margin.left + 2;
                insets.bottom = margin.bottom + 2;
                insets.right  = margin.right + 2;
            }
            return insets;
        }
    }
}


/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEScrollBarUI.java at 2015-2-1 20:25:36, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch4_scroll;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import org.jb2011.lnf.beautyeye.utils.BEUtils;
import org.jb2011.ninepatch4j.NinePatch;

/**
 * 本类是滚动条的UI实现.
 * 
 * @author Jack Jiang(jb2011@163.com), 2009-09-01
 * @version 1.0 
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类的实现参考了Windows LNF中的WindowsScrollBarUI.
//注：滚动条的两端箭头按钮参考自xp主题的实现，未作修改，因而这部分逻辑代码与WindowsScrollBarUI中是完全一样的.
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEScrollBarUI extends BasicScrollBarUI 
{
	/**
	 * Creates a UI for a JScrollBar.
	 *
	 * @param c the text field
	 * @return the UI
	 */
	public static ComponentUI createUI(JComponent c) 
	{
		return new BEScrollBarUI();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#createDecreaseButton(int)
	 */
	protected JButton createDecreaseButton(int orientation) 
	{
		return new WindowsArrowButton(orientation,
				UIManager.getColor("ScrollBar.thumb"),
				UIManager.getColor("ScrollBar.thumbShadow"),
				UIManager.getColor("ScrollBar.thumbDarkShadow"),
				UIManager.getColor("ScrollBar.thumbHighlight"));
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#createIncreaseButton(int)
	 */
	protected JButton createIncreaseButton(int orientation)  
	{
		return new WindowsArrowButton(orientation,
				UIManager.getColor("ScrollBar.thumb"),
				UIManager.getColor("ScrollBar.thumbShadow"),
				UIManager.getColor("ScrollBar.thumbDarkShadow"),
				UIManager.getColor("ScrollBar.thumbHighlight"));
	}

	/**
	 * WindowsArrowButton is used for the buttons to position the
	 * document up/down. It differs from BasicArrowButton in that the
	 * preferred size is always a square.
	 */
	protected class WindowsArrowButton extends BasicArrowButton
	{

		/**
		 * Instantiates a new windows arrow button.
		 *
		 * @param direction the direction
		 * @param background the background
		 * @param shadow the shadow
		 * @param darkShadow the dark shadow
		 * @param highlight the highlight
		 */
		public WindowsArrowButton(int direction, Color background, Color shadow,
				Color darkShadow, Color highlight)
		{
			super(direction, background, shadow, darkShadow, highlight);
		}

		/**
		 * Instantiates a new windows arrow button.
		 *
		 * @param direction the direction
		 */
		public WindowsArrowButton(int direction) {
			super(direction);
		}

		/* (non-Javadoc)
		 * @see javax.swing.plaf.basic.BasicArrowButton#paint(java.awt.Graphics)
		 */
		public void paint(Graphics g) 
		{
//			NLXPStyle xp = NLXPStyle.getXP();
//			if (xp != null) 
			{
				ButtonModel model = getModel();
//				Skin skin = xp.getSkin(scrollbar, Part.SBP_ARROWBTN);
//				State state = null;

//				// normal, rollover, pressed, disabled
//				if (model.isArmed() && model.isPressed()) 
//				{
//					switch (direction) 
//					{
//						case NORTH: state = State.UPPRESSED;    break;
//						case SOUTH: state = State.DOWNPRESSED;  break;
//						case WEST:  state = State.LEFTPRESSED;  break;
//						case EAST:  state = State.RIGHTPRESSED; break;
//					}
//				} 
//				else if (!model.isEnabled()) 
//				{
//					switch (direction) {
//						case NORTH: state = State.UPDISABLED;    break;
//						case SOUTH: state = State.DOWNDISABLED;  break;
//						case WEST:  state = State.LEFTDISABLED;  break;
//						case EAST:  state = State.RIGHTDISABLED; break;
//					}
//				} 
//				else if (model.isRollover() || model.isPressed())
//				{
//					switch (direction) {
//						case NORTH: state = State.UPHOT;    break;
//						case SOUTH: state = State.DOWNHOT;  break;
//						case WEST:  state = State.LEFTHOT;  break;
//						case EAST:  state = State.RIGHTHOT; break;
//					}
//				} 
//				else 
//				{
//					switch (direction)
//					{
//						case NORTH: state = State.UPNORMAL;    break;
//						case SOUTH: state = State.DOWNNORMAL;  break;
//						case WEST:  state = State.LEFTNORMAL;  break;
//						case EAST:  state = State.RIGHTNORMAL; break;
//					}
//				}

				//原实现（windows样式）
//				skin.paintSkin(g, 0, 0, getWidth(), getHeight(), state);
				//2012-01-10 by js实现自定义样式
				Graphics2D g2 = (Graphics2D)g;
				switch(direction)
				{
					case NORTH: 
						if(model.isRollover())
							__Icon9Factory__.getInstance().getScrollBarArrow_toTop_rover().draw(g2, 0, 0,getWidth(), getHeight());
						else
							__Icon9Factory__.getInstance().getScrollBarArrow_toTop().draw(g2, 0, 0,getWidth(), getHeight());
						break;
					case SOUTH:
						if(model.isRollover())
							__Icon9Factory__.getInstance().getScrollBarArrow_toBottom_rover().draw(g2, 0, 0,getWidth(), getHeight());
						else
							__Icon9Factory__.getInstance().getScrollBarArrow_toBottom().draw(g2, 0, 0,getWidth(), getHeight());
						break;
					case WEST:
						if(model.isRollover())
							__Icon9Factory__.getInstance().getScrollBarArrow_toLeft_rover().draw(g2, 0, 0,getWidth(), getHeight());
						else
							__Icon9Factory__.getInstance().getScrollBarArrow_toLeft().draw(g2, 0, 0,getWidth(), getHeight());
						break;
					case EAST: 
						if(model.isRollover())
							__Icon9Factory__.getInstance().getScrollBarArrow_toRight_rover().draw(g2, 0, 0,getWidth(), getHeight());
						else
							__Icon9Factory__.getInstance().getScrollBarArrow_toRight().draw(g2, 0, 0,getWidth(), getHeight());
						break;
				}
			} 
//			else 
//			{
//				super.paint(g);
//			}
		}

		/* (non-Javadoc)
		 * @see javax.swing.plaf.basic.BasicArrowButton#getPreferredSize()
		 */
		public Dimension getPreferredSize() 
		{
			int size = 16;
			if (scrollbar != null) 
			{
				switch (scrollbar.getOrientation()) 
				{
					case JScrollBar.VERTICAL:
						size = scrollbar.getWidth();
						break;
					case JScrollBar.HORIZONTAL:
						size = scrollbar.getHeight();
						break;
				}
				size = Math.max(size, 5);
			}
			return new Dimension(size, size);
		}
	}
	//----------------------------------------------------------------------------------- END
	
	//----------------------------------------------------------------------------------- 本次改造的主体部分
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#paintTrack(java.awt.Graphics, javax.swing.JComponent, java.awt.Rectangle)
	 */
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)  
	{
    	if(c==null||g==null)	
			return;
		Graphics2D g2 = (Graphics2D)g;
		
		Paint oldp= g2.getPaint();
		int w = trackBounds.width;
		int h = trackBounds.height;	
		int x = trackBounds.x;
		int y = trackBounds.y;

		if(this.scrollbar.getOrientation()==JScrollBar.VERTICAL)
		{
//			//1/2处渐变
//			GradientPaint gp = new GradientPaint(x, y
//					, GraphicHandler.getColor(trackColor,-15,-15,-15), w/2, y,trackColor);
//			g2.setPaint(gp);
//			g2.fillRect(x, y, w/2, h);
//
//			g2.setPaint(oldp);
//			g2.setColor(trackColor);
//			g2.fillRect(w/2, y, w/2, h);
			
			//** 简洁版轨迹实现
			int hhhWidth = 5;
			int px = (w-hhhWidth)/2;
			int delta = 50;
			//第1条
			g2.setColor(new Color(150+delta,151+delta,146+delta));
			g2.drawLine(px+0,y+10,px+0,y+h-10);
			//第2条
			g2.setColor(new Color(160+delta,160+delta,162+delta));
			g2.drawLine(px+1,y+10,px+1,y+h-10);
			//第3条
			g2.setColor(new Color(163+delta,162+delta,167+delta));
			g2.drawLine(px+2,y+10,px+2,y+h-10);
			//第4条
			g2.setColor(new Color(162+delta,162+delta,162+delta));
			g2.drawLine(px+3,y+10,px+3,y+h-10);
			//第5条
			g2.setColor(new Color(150+delta,150+delta,150+delta));
			g2.drawLine(px+4,y+10,px+4,y+h-10);
		}
		else
		{
			//1/2处渐变
//			GradientPaint gp = new GradientPaint(x, y
//					, GraphicHandler.getColor(trackColor,-15,-15,-15), x, h/2,trackColor);
//			g2.setPaint(gp);
//			g2.fillRect(x, y, w, h/2);
//
//			g2.setPaint(oldp);
//			g2.setColor(trackColor);
//			g2.fillRect(x, h/2, w, h);
			
			//** 简洁版轨迹实现
			int hhhWidth = 5;
			int py = (h-hhhWidth)/2;
			int delta = 50;
			//第1条
			g2.setColor(new Color(150+delta,151+delta,146+delta));
			g2.drawLine(x+10,py+0,x+w-10,py+0);
			//第2条
			g2.setColor(new Color(160+delta,160+delta,162+delta));
			g2.drawLine(x+10,py+1,x+w-10,py+1);
			//第3条
			g2.setColor(new Color(163+delta,162+delta,167+delta));
			g2.drawLine(x+10,py+2,x+w-10,py+2);
			//第4条
			g2.setColor(new Color(162+delta,162+delta,162+delta));
			g2.drawLine(x+10,py+3,x+w-10,py+3);
			//第5条
			g2.setColor(new Color(150+delta,150+delta,150+delta));
			g2.drawLine(x+10,py+4,x+w-10,py+4); 
		}
	}
	
	/**
	 * 滚动条绘制.
	 *
	 * @param g the g
	 * @param c the c
	 * @param thumbBounds the thumb bounds
	 */
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)  
	{
		if(thumbBounds.isEmpty() || !scrollbar.isEnabled())	
		{
			return;
		}
		Graphics2D g2 = (Graphics2D)g;
		int w = thumbBounds.width-4;
		int h = thumbBounds.height-4;		
		g2.translate(thumbBounds.x+2, thumbBounds.y+2);
		
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BEUtils.setAntiAliasing(g2, true);
		
		if(this.scrollbar.getOrientation()==JScrollBar.VERTICAL)
		{
			//防止thunmb的最小高度比图片的最小高度还要小，这样在绘制时就会出问题
			//起实，目前没还没有办法很好解决，因为即使在这里作处理，但是thumb本身
			//还是那么小，所以绘图还是会有问题，但起码在不拖动时看起来是正常的，以后再解决吧！
			NinePatch np = null;
			if(isDragging)
				np = __Icon9Factory__.getInstance().getScrollBar_pressed_v();
			else if(this.isThumbRollover())
				np = __Icon9Factory__.getInstance().getScrollBar_rover_v();
			else
				np = __Icon9Factory__.getInstance().getScrollBar_v();
			
			//如果滚动行高度小于NP图的最小高度时则交给此方法绘制（否则NP图的填充将出现虚绘，而影响滚动条的体验哦）
			if(h<np.getHeight())
				paintThumbIfSoSmall(g2, 0, 0, w, h);
			else
				np.draw(g2, 0, 0, w, h);
		}
		else
		{
			//防止thunmb的最小高度比图片的最小高度还要小，这样在绘制时就会出问题
			//起实，目前没还没有办法很好解决，因为即使在这里作处理，但是thumb本身
			//还是那么小，所以绘图还是会有问题，但起码在不拖动时看起来是正常的，以后再解决吧！
			NinePatch np = null;
			if(isDragging)
				np = __Icon9Factory__.getInstance().getScrollBar_pressed_h();
			else if(this.isThumbRollover())
				np = __Icon9Factory__.getInstance().getScrollBar_rover_h();
			else
				np = __Icon9Factory__.getInstance().getScrollBar_h();
			
			//如果滚动行宽度小于NP图的最小宽度时则交给此方法绘制（否则NP图的填充将出现虚绘，而影响滚动条的体验哦）
			if(w<np.getWidth())
				paintThumbIfSoSmall(g2, 0, 0, w, h);
			else
				np.draw(g2, 0, 0, w, h);
		}
		
		g2.translate(-thumbBounds.x, -thumbBounds.y);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		BEUtils.setAntiAliasing(g2, false);
	}
	//----------------------------------------------------------------------------------- END
	
	//* 参考自BasicScrollBarUI，由Jack Jiang于2012-09-17修改
	/**
	 * 如果滚动条非常小（小到小于NP图的最小大小）时调用此方法实现滚动条的精确绘制.
	 *
	 * @param g2 the g2
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 */
	protected void paintThumbIfSoSmall(Graphics2D g2, int x, int y, int w, int h)
	{
		final int NORMAL_ARC = 6;//定义圆角直径
		//如果w或h太小时，则就不绘制圆角了(直角即可)，要不然就没法绘全圆角而很难看
		int arc = ((w <= NORMAL_ARC || h <= NORMAL_ARC)?0:NORMAL_ARC);
		g2.setColor(thumbDarkShadowColor);
		g2.drawRoundRect(x, y, w-1, h-1,arc,arc);//画滚动条的外层
		g2.setColor(thumbColor);
		g2.fillRoundRect(x+1, y+1, w-2, h-2,arc,arc);//填充滚动条的内层
	}
}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BESliderUI.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch15_slider;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;

import org.jb2011.lnf.beautyeye.utils.BEUtils;

// TODO: Auto-generated Javadoc
/**
 * JSlider的ui实现类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 * @version 1.0
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类的实现参考了WindowsComboBoxUI
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BESliderUI extends BasicSliderUI
{
	
	/** 水平Slider的Thumb高度. */
	protected final int THUMB_HEIGHT_HORIZONAL = 7;// TODO 此属性可提取为Ui属性，方便以后配置（大小应是NP图的最小高度，最大值得看JSlider的高度了）
	
	/** 垂直Slider的Thumb宽度. */
	protected final int THUMB_WIDTH_VERTICAL = 7;// TODO 此属性可提取为Ui属性，方便以后配置（大小应是NP图的最小高度，最大值得看JSlider的高度了）
	
	/**
	 * Instantiates a new bE slider ui.
	 *
	 * @param b the b
	 */
	public BESliderUI(JSlider b){
		super(b);
	}

	/**
	 * Creates the ui.
	 *
	 * @param b the b
	 * @return the component ui
	 */
	public static ComponentUI createUI(JComponent b) {
		return new BESliderUI((JSlider)b);
	}
	
	//copy from BasicSliderUI and modified by jb2011
    /* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSliderUI#paintTrack(java.awt.Graphics)
	 */
	public void paintTrack(Graphics g)  {        
        Rectangle trackBounds = trackRect;
        if ( slider.getOrientation() == JSlider.HORIZONTAL ) 
        {
            int cy = (trackBounds.height / 2) - 2;
            int cw = trackBounds.width;

            g.translate(trackBounds.x, trackBounds.y + cy);
//            g.setColor(getShadowColor());
//            g.drawLine(0, 0, cw - 1, 0);
//            g.drawLine(0, 1, 0, 2);
//            g.setColor(getHighlightColor());
//            g.drawLine(0, 3, cw, 3);
//            g.drawLine(cw, 0, cw, 3);
//            g.setColor(Color.black);
//            g.drawLine(1, 1, cw-2, 1);
            
            if(slider.isEnabled())
            {
            	//轨道背景
            	__Icon9Factory__.getInstance().getSliderTrack()
            		.draw((Graphics2D)g, 0,0, cw, THUMB_HEIGHT_HORIZONAL);
            	//轨道（填充到当前刻度值处）
            	__Icon9Factory__.getInstance().getSliderTrack_forground()
        			.draw((Graphics2D)g, 0,0, thumbRect.x, THUMB_HEIGHT_HORIZONAL);
            }
            else
            {
            	//轨道背景
            	__Icon9Factory__.getInstance().getSliderTrack_disable()
        			.draw((Graphics2D)g, 0,0, cw, THUMB_HEIGHT_HORIZONAL);
            	//轨道（填充到当前刻度值处）
            	__Icon9Factory__.getInstance().getSliderTrack_forground_disable()
    				.draw((Graphics2D)g, 0,0, thumbRect.x, THUMB_HEIGHT_HORIZONAL);
            }

            g.translate(-trackBounds.x, -(trackBounds.y + cy));
        }
        else 
        {
            int cx = (trackBounds.width / 2) - 2;
            int ch = trackBounds.height;

            g.translate(trackBounds.x + cx, trackBounds.y);

//            g.setColor(getShadowColor());
//            g.drawLine(0, 0, 0, ch - 1);
//            g.drawLine(1, 0, 2, 0);
//            g.setColor(getHighlightColor());
//            g.drawLine(3, 0, 3, ch);
//            g.drawLine(0, ch, 3, ch);
//            g.setColor(Color.black);
//            g.drawLine(1, 1, 1, ch-2);
            
            if(slider.isEnabled())
            {
            	//轨道背景
            	__Icon9Factory__.getInstance().getSliderTrack_VERITICAL()
        			.draw((Graphics2D)g, 0,0, THUMB_WIDTH_VERTICAL, ch);
            	//轨道（填充到当前刻度值处）
            	// TODO BUG：当前有个bug，即在SwingSets2演示中，当thumb高度较小时，轨道半圆形被画出，这可能父类中thumbRect中计算有部碮同，有时间再研究吧，官方以后版本或许能解决哦
            	__Icon9Factory__.getInstance().getSliderTrack_VERTICAL_forground()
    				.draw((Graphics2D)g, 0,thumbRect.y, THUMB_WIDTH_VERTICAL, ch - thumbRect.y);
            }
            else
            {
            	//轨道背景
            	__Icon9Factory__.getInstance().getSliderTrack_VERITICAL_disable()
    			.draw((Graphics2D)g, 0,0, THUMB_WIDTH_VERTICAL, ch);
            	//轨道（填充到当前刻度值处）
            	__Icon9Factory__.getInstance().getSliderTrack_VERTICAL_forground_disable()
    				.draw((Graphics2D)g, 0,thumbRect.y, THUMB_WIDTH_VERTICAL, ch - thumbRect.y);
            }
            
            g.translate(-(trackBounds.x + cx), -trackBounds.y);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicSliderUI#paintFocus(java.awt.Graphics)
     */
    public void paintFocus(Graphics g)  
    {        
    	g.setColor( getFocusColor() );

//    	BasicGraphicsUtils.drawDashedRect( g, focusRect.x, focusRect.y,
//    					   focusRect.width, focusRect.height );
    	BEUtils.drawDashedRect(g, focusRect.x, focusRect.y,
    					   focusRect.width, focusRect.height);
    }

    //copy from BasicSliderUI and modified by jb2011
	/* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicSliderUI#paintThumb(java.awt.Graphics)
     */
    public void paintThumb(Graphics g)
	{
		Rectangle knobBounds = thumbRect;
		int w = knobBounds.width;
		int h = knobBounds.height;

		g.translate(knobBounds.x, knobBounds.y);
		if (slider.isEnabled())
		{
			g.setColor(slider.getBackground());
		}
		else
		{
			g.setColor(slider.getBackground().darker());
		}

		//* 该4行代码被Jack Jiang提炼成了方法 isPaintArrowThumb()
//		Boolean paintThumbArrowShape = (Boolean) slider
//				.getClientProperty("Slider.paintThumbArrowShape");
//		if ((!slider.getPaintTicks() && paintThumbArrowShape == null)
//				|| paintThumbArrowShape == Boolean.FALSE)
		if(isPaintNoTrangleThumb())
		{
			if(slider.getOrientation() == JSlider.HORIZONTAL)
				g.drawImage(
						slider.isEnabled()?__IconFactory__.getInstance().getSliderTick1_notrangle().getImage()
								:__IconFactory__.getInstance().getSliderTick1_notrangle_disable().getImage()
					, 0, 0, null);
			else
				g.drawImage(slider.isEnabled()?__IconFactory__.getInstance().getSliderTick1_notrangle_vertical().getImage()
						:__IconFactory__.getInstance().getSliderTick1_notrangle_VERTICAL_disable().getImage()
						, 0, 0, null);
		}
		else if (slider.getOrientation() == JSlider.HORIZONTAL)
		{
			g.drawImage(slider.isEnabled()?__IconFactory__.getInstance().getSliderTick1().getImage()
							:__IconFactory__.getInstance().getSliderTick1_disable().getImage()
					, 0, 0, null);
		}
		else
		{ // vertical
			g.drawImage(slider.isEnabled()?__IconFactory__.getInstance().getSliderTick1_vertical().getImage()
							:__IconFactory__.getInstance().getSliderTick1_VERTICAL_disable().getImage()
					, 0, 0, null);
		}

		g.translate(-knobBounds.x, -knobBounds.y);
	}
	
	//* 本方法由jb2011提炼
	//该thumb是否是无3角箭头的样式，true表示无3解箭头（即圆形thumb），false表示有3角箭头样式
	/**
	 * Checks if is paint no trangle thumb.
	 *
	 * @return true, if is paint no trangle thumb
	 */
	protected boolean isPaintNoTrangleThumb()
	{
		Boolean paintThumbArrowShape = (Boolean) slider
				.getClientProperty("Slider.paintThumbArrowShape");

		//不绘制有箭头标识的thumb样式(即普通圆形thumb)
		return (!slider.getPaintTicks() && paintThumbArrowShape == null)
				|| paintThumbArrowShape == Boolean.FALSE;
	}

	//copy from BasicSliderUI and modified by jb2011
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSliderUI#getThumbSize()
	 */
	protected Dimension getThumbSize()
	{
		boolean isPaintNoTrangle = isPaintNoTrangleThumb();
		
		Dimension size = new Dimension();
		if (slider.getOrientation() == JSlider.VERTICAL)
		{
			size.width = 17;//20;
			size.height = isPaintNoTrangle?16:12;//14;
		}
		else
		{
			size.width = isPaintNoTrangle?16:12;//14;
			size.height = 17;//20;
		}
		return size;
	}
}


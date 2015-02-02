/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BESpinnerUI.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch18_spinner;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

import org.jb2011.lnf.beautyeye.ch18_spinner.BESpinnerUI.GlyphButton.Type;
import org.jb2011.lnf.beautyeye.utils.BEUtils;

// TODO: Auto-generated Javadoc
/**
 * JSPinner的UI实现类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 * @version 1.0
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//参考自WindowsSpinnerUI 由Jack Jiang修改
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BESpinnerUI extends BasicSpinnerUI
{
	
	/**
	 * Creates the ui.
	 *
	 * @param c the c
	 * @return the component ui
	 */
	public static ComponentUI createUI(JComponent c) 
	{
		return new BESpinnerUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSpinnerUI#createEditor()
	 */
	protected JComponent createEditor()
	{
		JComponent e = super.createEditor();
		e.setOpaque(false);

		//参见JSpinner.NumberEditor，super.createEditor()返回值就是它的父类
		//（是一个JPanel实例），它是由一个FormatttedTextField及其父JPanel组成
		//的，所以设置完 e.setOpaque(false)，还要把它的子FormatttedTextField
		//设置成透明，其实它的子只有1个，它里为了适用未来的扩展假设它有很多子，
		Component[] childs = e.getComponents();
		BEUtils.componentsOpaque(childs, false);

		return e;
	}

	/**
	 * Paint.
	 *
	 * @param g the g
	 * @param c the c
	 * {@inheritDoc}
	 * @since 1.6
	 */
	public void paint(Graphics g, JComponent c) {
//		if (NLXPStyle.getXP() != null) 
		{
			paintXPBackground(g, c);
		}
		super.paint(g,c);
	}

//	private State getXPState(JComponent c) {
//		State state = State.NORMAL;
//		if (!c.isEnabled()) {
//			state = State.DISABLED;
//		}
//		return state;
//	}

	/**
 * Paint xp background.
 *
 * @param g the g
 * @param c the c
 */
private void paintXPBackground(Graphics g, JComponent c) {
//		NLXPStyle xp = NLXPStyle.getXP();
//		Skin skin = xp.getSkin(c, Part.EP_EDIT);
//		State state = getXPState(c);
//		skin.paintSkin(g, 0, 0, c.getWidth(), c.getHeight(), state);
		
//		g.setColor(Color.blue);
//		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		
		if(spinner != null && !spinner.isEnabled())
			__Icon9Factory__.getInstance().getSpinnerDisableBg().
				draw((Graphics2D)g, 0, 0, c.getWidth(), c.getHeight());
		else
			__Icon9Factory__.getInstance().getSpinnerBg().
				draw((Graphics2D)g, 0, 0, c.getWidth(), c.getHeight());
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSpinnerUI#createPreviousButton()
	 */
	protected Component createPreviousButton() {
//		if (NLXPStyle.getXP() != null) 
		{
			JButton xpButton = new GlyphButton(spinner, Type.down);
			Dimension size = UIManager.getDimension("Spinner.arrowButtonSize");
			xpButton.setPreferredSize(size);
			xpButton.setRequestFocusEnabled(false);
			installPreviousButtonListeners(xpButton);
			return xpButton;
		}
//		return super.createPreviousButton();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicSpinnerUI#createNextButton()
	 */
	protected Component createNextButton() {
//		if (NLXPStyle.getXP() != null) 
		{
			JButton xpButton = new GlyphButton(spinner, Type.up);
			Dimension size = UIManager.getDimension("Spinner.arrowButtonSize");
			xpButton.setPreferredSize(size);
			xpButton.setRequestFocusEnabled(false);
			installNextButtonListeners(xpButton);
			return xpButton;
		}
//		return super.createNextButton();
	}

	//copy from com.sun.java.swing.plaf.windows.XPStyle.GlyphButton
	/**
	 * The Class GlyphButton.
	 */
	static class GlyphButton extends JButton {
//		private Skin skin;
		/** The type. */
		private Type type = null;
		
		/**
		 * The Enum Type.
		 */
		public enum Type
		{
			
			/** The down. */
			down,
			
			/** The up. */
			up
		}

		/**
		 * Instantiates a new glyph button.
		 *
		 * @param parent the parent
		 * @param type the type
		 */
		public GlyphButton(Component parent, Type type) {
//			XPStyle xp = getXP();
//			skin = xp.getSkin(parent, part);
			this.type = type;
			setBorder(null);
			setContentAreaFilled(false);
			setMinimumSize(new Dimension(5, 5));
			setPreferredSize(new Dimension(16, 16));
			setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		}   

		/* (non-Javadoc)
		 * @see java.awt.Component#isFocusTraversable()
		 */
		public boolean isFocusTraversable() {
			return false;
		}

//		protected State getState() {
//			State state = State.NORMAL;
//			if (!isEnabled()) {
//				state = State.DISABLED;
//			} else if (getModel().isPressed()) {
//				state = State.PRESSED;
//			} else if (getModel().isRollover()) {
//				state = State.HOT;
//			}
//			return state;
//		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		public void paintComponent(Graphics g) {
			Dimension d = getSize();
//			skin.paintSkin(g, 0, 0, d.width, d.height, getState());
//			g.setColor(Color.red);
//			g.drawRect(0, 0, d.width, d.height);
			
			if(type == Type.up)
			{
				if(!isEnabled() || getModel().isPressed()) 
					__Icon9Factory__.getInstance().getUpButtonBg_pressed().
						draw((Graphics2D)g, 0, 0, d.width, d.height);
				else
					__Icon9Factory__.getInstance().getUpButtonBg().
						draw((Graphics2D)g, 0, 0, d.width, d.height);
			}
			else if(type == Type.down)
			{
				if(!isEnabled() || getModel().isPressed()) 
					__Icon9Factory__.getInstance().getDownButtonBg_pressed().
						draw((Graphics2D)g, 0, 0, d.width, d.height);
				else
					__Icon9Factory__.getInstance().getDownButtonBg().
						draw((Graphics2D)g, 0, 0, d.width, d.height);
			}
			
		}

//		public void setPart(Component parent, Part part) {
////			XPStyle xp = getXP();
////			skin = xp.getSkin(parent, part);
//			revalidate();
//			repaint();
//		}

		/* (non-Javadoc)
		 * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
		 */
		protected void paintBorder(Graphics g) {    
		}
	}
}


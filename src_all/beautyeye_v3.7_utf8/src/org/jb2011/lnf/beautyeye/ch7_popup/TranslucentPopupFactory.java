/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * TranslucentPopupFactory.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch7_popup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.JWindow;
import javax.swing.MenuElement;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboPopup;

import org.jb2011.lnf.beautyeye.utils.WindowTranslucencyHelper;
import org.jb2011.lnf.beautyeye.widget.ImageBgPanel;

// TODO: Auto-generated Javadoc
/**
 * 自定义透明背景的弹出工厂实现类.
 * 本类其实与Swing的UI机制关联不大，完全可以独立于L&F使用。
 * 
 * @author Jack Jiang(jb2011@163.com), 2012-05-18
 * @version 1.0
 */
public class TranslucentPopupFactory extends PopupFactory
{
	
	/* (non-Javadoc)
	 * @see javax.swing.PopupFactory#getPopup(java.awt.Component, java.awt.Component, int, int)
	 */
	@Override
	public Popup getPopup(Component owner, Component contents, int x, int y)
			throws IllegalArgumentException
	{
		// A more complete implementation would cache and reuse popups
		return new TranslucentPopup(owner, contents, x, y);
	}

	/**
	 * 透明背景的Popup实现类.
	 * <p>
	 * 本类的大部分代码都是原封不动地参考自Popup父类(JDK版本是1.6_u18版的源码)，因为Popup父
	 * 类中的多数方法都是使用的包内可见性，所以没法直接继承，像这种不允许第3方自行扩展的情
	 * 况在Swing标准包里比较少见，不知原作者是出于何种考虑，无疑这样的类实现是相当的欠缺远
	 * 见性，但这种情况在LNF里很常见。
	 */
	protected class TranslucentPopup extends Popup
	{
		//copy all from parent class
		/**
		 * The Component representing the Popup.
		 */
		private Component component;

		//copy all from parent class
		/**
		 * Creates a <code>Popup</code> for the Component <code>owner</code>
		 * containing the Component <code>contents</code>. <code>owner</code>
		 * is used to determine which <code>Window</code> the new
		 * <code>Popup</code> will parent the <code>Component</code> the
		 * <code>Popup</code> creates to.
		 * A null <code>owner</code> implies there is no valid parent.
		 * <code>x</code> and
		 * <code>y</code> specify the preferred initial location to place
		 * the <code>Popup</code> at. Based on screen size, or other paramaters,
		 * the <code>Popup</code> may not display at <code>x</code> and
		 * <code>y</code>.
		 *
		 * @param owner    Component mouse coordinates are relative to, may be null
		 * @param contents Contents of the Popup
		 * @param x        Initial x screen coordinate
		 * @param y        Initial y screen coordinate
		 */
		public TranslucentPopup(Component owner, Component contents, int x,
				int y)
		{
			this();
			if (contents == null)
			{
				throw new IllegalArgumentException("Contents must be non-null");
			}
			
			reset(owner, contents, x, y);
		}

		//copy all from parent class
		/**
		 * Creates a <code>Popup</code>. This is provided for subclasses.
		 */
		public TranslucentPopup()
		{
		}

		/**
		 * <p>
		 * Makes the <code>Popup</code> visible. If the <code>Popup</code> is
		 * currently visible, this has no effect.<br>
		 * 
		 * 本方法的结构完全copy自父类方法，但添加了component.repaint();的调用.
		 * </p>
		 */
		public void show()
		{
			Component component = getComponent();
			
			if (component != null)
			{
				component.setVisible(true);
				
				//此行代码必须要有，否则可能是因为没有关闭双缓存（getRootPane().
				//setUseTrueDoubleBuffering(false)，这个方法是包内可见，所以本类里没法调用
				//），而导致界面没有重绘：要不就是UI没有绘出来，要不就是出现空白的popup（没有内容组件）
				component.repaint();
			}
		}

		//copy all from parent class
		/**
		 * Hides and disposes of the <code>Popup</code>. Once a
		 * <code>Popup</code> has been disposed you should no longer invoke
		 * methods on it. A <code>dispose</code>d <code>Popup</code> may be
		 * reclaimed and later used based on the <code>PopupFactory</code>. As
		 * such, if you invoke methods on a <code>disposed</code>
		 * <code>Popup</code>, indeterminate behavior will result.
		 */
		public void hide()
		{
			Component component = getComponent();

			if (component instanceof JWindow)
			{
				component.hide();
				((JWindow) component).getContentPane().removeAll();
			}
			dispose();
		}

		//copy all from parent class
		/**
		 * Frees any resources the <code>Popup</code> may be holding onto.
		 */
		protected void dispose()
		{
			Component component = getComponent();
			Window window = SwingUtilities.getWindowAncestor(component);

			if (component instanceof JWindow)
			{
				((Window) component).dispose();
				component = null;
			}
			// If our parent is a DefaultFrame, we need to dispose it, too.
			if (window instanceof DefaultFrame)
			{
				window.dispose();
			}
		}

		/**
		 * <p>
		 * Resets the <code>Popup</code> to an initial state.<br>
		 * 
		 * 本方法的结构完全copy自父类方法，但进行中包括：窗口透明、用图片实现背景填充等在内的修改.
		 * </p>
		 *
		 * @param owner the owner
		 * @param contents the contents
		 * @param ownerX the owner x
		 * @param ownerY the owner y
		 */
		protected void reset(Component owner, Component contents, int ownerX, int ownerY)
		{
			if (getComponent() == null)
			{
				component = createComponent(owner);
			}

			Component c = getComponent();
			if (c instanceof JWindow)
			{
				JWindow component = (JWindow) getComponent();
				component.setLocation(ownerX, ownerY);
				
				boolean isTooltip = ((JComponent)contents instanceof JToolTip);
				//如果contents是BasicComboPopup或其子类那当前就应该是用于下拉框的弹出列表罗
				boolean isComboBoxPopup = (contents instanceof BasicComboPopup);
				
				//每像素透明
//				com.sun.awt.AWTUtilities.setWindowOpaque(component, false);
				WindowTranslucencyHelper.setWindowOpaque(component, false);
				//内容组件半透明
//				com.sun.awt.AWTUtilities.setWindowOpacity(component,
//						isTooltip ? 1.0f : 0.95f);//0.85f : 0.95f);//0.8f : 0.95f);
				WindowTranslucencyHelper.setOpacity(component,
						isTooltip ? 1.0f : isComboBoxPopup?0.95f : 0.95f);//0.85f : 0.95f);//0.8f : 0.95f);
				
//				component.getContentPane().add(contents, BorderLayout.CENTER);
//				contents.invalidate();
//				if (component.isVisible())
//				{
//					// Do not call pack() if window is not visible to
//					// avoid early native peer creation
//					pack();
//				}
				
				//图片填充背景的内容面板
				ImageBgPanel imageContentPane = new ImageBgPanel().setN9(
						isTooltip?
								__Icon9Factory__.getInstance().getTooltipBg()
								:isComboBoxPopup?//是下拉框列表则使一个跟JScrollPane一样的背景图，好看！
										org.jb2011.lnf.beautyeye.ch4_scroll.__Icon9Factory__.getInstance().getScrollPaneBorderBg()
										:__Icon9Factory__.getInstance().getPopupBg()
				);
				imageContentPane.setLayout(new BorderLayout());
				imageContentPane.add(contents, BorderLayout.CENTER);
				
				//为每一个要显示的真正的内容组件加一个空border，目的
				//是使得父面板的背景图片显示的效果更好
				if(contents instanceof JComponent)
				{
					((JComponent)contents).setOpaque(false);
					//* ######################################### Bug FIX START ############################################
					//* 关于Java Bug 6683775,地址：http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6683775，解决于java1.6.0_14
					//* 
					//* 【Bug说明】：
					//* 在java1.6.0_14之前(当然是>=u10版)，在设置窗口透明时，即调用方法
					//* AWTUtilities.setWindowOpaque(component, false)使得窗口透明后，再设置位于
					//* Windows上的第1层JPanel也为透明时(即JPanel.setOpaque(false))，则会出现类似
					//* 于很久以前给JPanel设置一个半透明色（即alpha小于255）时出现的不正确paint情况，
					//* 直接导致下拉框的弹出列表ui刷新出现混乱（根本就不能即时刷新，ui一塌糊涂）的情况.
					//* 
					//* 【java1.6.0_14版前如何解决】：
					//* 该Bug 6683775在被官方解决前，怎么才能避免出现ui刷新
					//* 混乱呢？Bug 6683775上的官方回复给出了解决方案：JPanel.setDoubleBuffered(false)（官方认为
					//* 光设置JPanel.setOpaque(false)是不够的，还得取消双缓冲才行），不过好在官方在u14及以后的版本中
					//* 彻底解决了这个问题.
					//* 
					//* 【目前BeautyEye L&F中如何解决？】：
					//* 为了能在该Bug被解决前（即java小于java1.6.0_14版）BE LNF也能正常运行，只能稍微牺牲一点ui性能，
					//* 按官方解决方法——“JPanel.setDoubleBuffered(false)”来解决吧.
					((JComponent)contents).setDoubleBuffered(false);
					//* ######################################### Bug FIX END ##############################################
					
					((JComponent)contents).setBorder(
							isTooltip?
									BorderFactory.createEmptyBorder(6,8,12,12)//6,8,12,8)
									:isComboBoxPopup?BorderFactory.createEmptyBorder(6,4,6,4)
											:BorderFactory.createEmptyBorder(5,3,6,3));//10,2,10,4));//5,5,20,5));
				}
				
				//如果BasicComboPopup即表明它应该是ComboBox里的弹出菜单，为了使N9
				//背景能被完整展现出来则要把combox弹出列表所在的JScrollPane进行透明设置
				//，因为如果它不被透明设置则本pop的NP图背景就只能显示边缘，而且底边有一点被
				//挡住，不好看（另注：本JScrollPane在BEComboBoxUI中被设置了一个EmptyBorder，所以
				//没有在ScrollPaneUI中定认的那个ScrollBorder哦，没有那个border的目的就是为了在些使用
				//pop的背景撒）
				if(isComboBoxPopup)//contents instanceof BasicComboPopup)//* add by jb2011 2012-08-31
	        	{
					Component[] cs = ((BasicComboPopup)contents).getComponents();
					//该JScrollPane就是contents的第一层子组件里
					if(cs != null && cs.length>0)
					{
						//遍历并找出它
						for(Component com : cs)
						{
							if(com instanceof JScrollPane)
								((JScrollPane)com).setOpaque(false);
						}
					}
//					BEUtils.componentsOpaque(((BasicComboPopup)contents).getComponents(), false);
	        	}
				//如果是菜单父宿主则所有JMenuItem也设置透明,jb2011 2009-08-29
				else if(contents instanceof JPopupMenu)
	        	{
	        		MenuElement[]  mes=((JPopupMenu)contents).getSubElements();
	        		for(int i=0;i<mes.length;i++)
	        		{
	        			if(mes[i] instanceof JMenuItem)
	        				((JMenuItem)mes[i]).setOpaque(false);
	        		}
	        	}
				
				// add the contents to the popup
	        	component.setContentPane(imageContentPane);
//				popupWindow.getContentPane().add(p, BorderLayout.CENTER);
//				contents.invalidate();
				if (component.isVisible())
				{
					// Do not call pack() if window is not visible to
					// avoid early native peer creation
					pack();
				}
			}
		}

		//copy all from parent class
		/**
		 * Causes the <code>Popup</code> to be sized to fit the preferred size
		 * of the <code>Component</code> it contains.
		 */
		protected void pack()
		{
			Component component = getComponent();

			if (component instanceof Window)
			{
				((Window) component).pack();
			}
		}

		//copy all from parent class
		/**
		 * Returns the <code>Window</code> to use as the parent of the
		 * <code>Window</code> created for the <code>Popup</code>. This creates
		 * a new <code>DefaultFrame</code>, if necessary.
		 *
		 * @param owner the owner
		 * @return the parent window
		 */
		protected Window getParentWindow(Component owner)
		{
			Window window = null;

			if (owner instanceof Window)
			{
				window = (Window) owner;
			}
			else if (owner != null)
			{
				window = SwingUtilities.getWindowAncestor(owner);
			}
			if (window == null)
			{
				window = new DefaultFrame();
			}
			return window;
		}

		//copy all from parent class
		/**
		 * Creates the Component to use as the parent of the <code>Popup</code>.
		 * The default implementation creates a <code>Window</code>, subclasses
		 * should override.
		 *
		 * @param owner the owner
		 * @return the component
		 */
		protected Component createComponent(Component owner)
		{
			if (GraphicsEnvironment.isHeadless())
			{
				// Generally not useful, bail.
				return null;
			}

			return new HeavyWeightWindow(getParentWindow(owner));
		}

		//copy all from parent class
		/**
		 * Returns the <code>Component</code> returned from
		 * <code>createComponent</code> that will hold the <code>Popup</code>.
		 *
		 * @return the component
		 */
		protected Component getComponent()
		{
			return component;
		}
		
		//copy all from parent class
		/**
		 * Used if no valid Window ancestor of the supplied owner is found.
		 * <p>
		 * PopupFactory uses this as a way to know when the Popup shouldn't be
		 * cached based on the Window.
		 */
		protected class DefaultFrame extends Frame
		{}
		
		//copy all from parent class
		/**
		 * Component used to house window.
		 */
		protected class HeavyWeightWindow extends JWindow// implements ModalExclude
		{
			
			/**
			 * Instantiates a new heavy weight window.
			 *
			 * @param parent the parent
			 */
			public HeavyWeightWindow(Window parent)
			{
				super(parent);
				// FIXME: 一个外国朋友用了后，在此项被设置成false的情况下，Popup里的JTextField不能编辑了！
				setFocusableWindowState(true);
				setName("###overrideRedirect###");
				// Popups are typically transient and most likely won't benefit
				// from true double buffering. Turn it off here.
//				getRootPane().setUseTrueDoubleBuffering(false);//本方法不能在包外被调用
				// Try to set "always-on-top" for the popup window.
				// Applets usually don't have sufficient permissions to do it.
				// In this case simply ignore the exception.
				try
				{
					setAlwaysOnTop(true);
				}
				catch (SecurityException se)
				{
					// setAlwaysOnTop is restricted,
					// the exception is ignored
				}
			}

			/* (non-Javadoc)
			 * @see javax.swing.JWindow#update(java.awt.Graphics)
			 */
			public void update(Graphics g)
			{
				paint(g);
			}

			/* (non-Javadoc)
			 * @see java.awt.Window#show()
			 */
			public void show()
			{
				this.pack();
				super.show();
			}
		}
	}
}
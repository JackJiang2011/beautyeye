/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BETableHeaderUI.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch5_table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;

import org.jb2011.lnf.beautyeye.utils.ReflectHelper;

import sun.swing.table.DefaultTableCellHeaderRenderer;

//import sun.swing.table.DefaultTableCellHeaderRenderer;

// TODO: Auto-generated Javadoc
/**
 * 表格头UI实现类。
 * <p>
 * 本类代码只能用于JDK1.6及以上版本，因JDK1.5及以下版本没法兼容本类中的很大一部分关键代码。
 * 
 * @author Jack Jiang(jb2011@163.com), 2011-03-28
 * @see com.sun.java.swing.plaf.windows.WindowsTableHeaderUI
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类实现参考了JDK1.6_u18中的WindowsTableHeaderUI源码，本类中除了兼容性修改，其它源码基本不变！
//另：本代码来自JDK自带src中，似乎代码不是最新的，存在一处不太明显的bug！
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BETableHeaderUI extends BasicTableHeaderUI 
{
	
	/** The original header renderer. */
	private TableCellRenderer originalHeaderRenderer;

	/**
	 * Creates the ui.
	 *
	 * @param h the h
	 * @return the component ui
	 */
	public static ComponentUI createUI(JComponent h) 
	{
		return new BETableHeaderUI();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicTableHeaderUI#installUI(javax.swing.JComponent)
	 */
	public void installUI(JComponent c) 
	{
		super.installUI(c);
//		if (BEXPStyle.getXP() != null) 
		{
			originalHeaderRenderer = header.getDefaultRenderer();
			if (originalHeaderRenderer instanceof UIResource) 
			{
				header.setDefaultRenderer(new XPDefaultRenderer());
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicTableHeaderUI#uninstallUI(javax.swing.JComponent)
	 */
	public void uninstallUI(JComponent c) 
	{
		if (header.getDefaultRenderer() instanceof XPDefaultRenderer) 
		{
			header.setDefaultRenderer(originalHeaderRenderer);
		}
		super.uninstallUI(c);
	}

//	@Override
	/* (non-Javadoc)
 * @see javax.swing.plaf.basic.BasicTableHeaderUI#rolloverColumnUpdated(int, int)
 */
protected void rolloverColumnUpdated(int oldColumn, int newColumn) 
	{
//		if (BEXPStyle.getXP() != null) 
		{
			header.repaint(header.getHeaderRect(oldColumn)); 
			header.repaint(header.getHeaderRect(newColumn));
		}
	}
	
	//绘制头Ui内容，本方法提取出来是为了在ELineNumTable里也可以用到（它是进行了自定义Ui头实现）
	/**
	 * Paint head cell.
	 *
	 * @param g the g
	 * @param headCellSize the head cell size
	 */
	public static void paintHeadCell(Graphics g, Dimension headCellSize)
	{
		int w = headCellSize.width,h = headCellSize.height-1;
		
//		Graphics2D g2 = (Graphics2D)g;
//		//渐变背景
//		Paint oldp= g2.getPaint();
//		GradientPaint gp = new GradientPaint(0, 0
//				, new Color(250,250,250)
//				, 0, h,new Color(241,241,241));
//		g2.setPaint(gp);
//		g.fillRect(0, 0, w, h);
//		//矩形区下1/2处非渐变填充（为了产生立体效果）
//		g2.setPaint(oldp);
//		
//		//上下线条，突出立体感
//		g.setColor(new Color(215,215,215));
//		g.drawLine(0, 0, w, 0);
//		g.setColor(new Color(249,250,249));
//		g.drawLine(0, h-1, w, h-1);
//		g.setColor(new Color(209,209,209));
//		g.drawLine(0, h, w, h);
		
		__Icon9Factory__.getInstance().getTableHeaderCellBg1()
			.draw((Graphics2D)g, 0, 0, w, h);//表头背景
		__Icon9Factory__.getInstance().getTableHeaderCellSeparator1()
			.draw((Graphics2D)g, w - 2, 0, 4, h-1);//表头右边的分隔线,h-1是为了让分隔线往上移一个像素，好看一点
	}

	/**
	 * The Class XPDefaultRenderer.
	 */
	private class XPDefaultRenderer extends DefaultTableCellHeaderRenderer 
	{
//		Skin skin;
//		boolean isSelected, hasFocus, hasRollover;
//		int column;

		/**
		 * Instantiates a new xP default renderer.
		 */
		XPDefaultRenderer() 
		{
			setHorizontalAlignment(LEADING);
			setVerticalAlignment(CENTER);
		}

		/* (non-Javadoc)
		 * @see sun.swing.table.DefaultTableCellHeaderRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus,int row, int column)
		{
			
			//** 本方法里可以有默认设置render字体和颜色的设置，所以即使在上面构造方法里设置自已的字体等都不起效
			//，在此方法调用之后调置是没有问题的
			super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
			
//			this.isSelected = isSelected;
//			this.hasFocus = hasFocus;
//			this.column = column;
//			this.hasRollover = (column == getRolloverColumn());
//			if (skin == null) 
//			{
//				skin = BEXPStyle.getXP().getSkin(header, Part.HP_HEADERITEM); 
//			}
			Insets margins = UIManager.getInsets("TableHeader.cellMargin");//skin.getContentMargin();
			Border border = null;
			int contentTop = 0;
			int contentLeft = 0;
			int contentBottom = 0;
			int contentRight = 0;
			if (margins != null) 
			{
				contentTop = margins.top;
				contentLeft = margins.left;
				contentBottom = margins.bottom;
				contentRight = margins.right;
			}
			/* idk:
			 * Both on Vista and XP there is some offset to the
			 * HP_HEADERITEM content. It does not seem to come from 
			 * Prop.CONTENTMARGINS. Do not know where it is defined.
			 * using some hardcoded values.
			 */
			contentLeft += 5;
			contentBottom += 4;
			contentRight += 5;
			
			/* On Vista sortIcon is painted above the header's text.
			 * We use border to paint it.
			 */
			Icon sortIcon;
			//原UI代码
//			if (ZCWindowsLookAndFeel.isOnVista()&& 
//					((sortIcon = getIcon()) instanceof javax.swing.plaf.UIResource
//							|| sortIcon == null)) 
			//现代码：上面原Java源代码里有WindowsLookAndFeel.isOnVista()==true时才会进入下面的代码
			//逻辑是不对的，也就是只要不是vista，就不可能绘制排序图标，这估计是Jdk自带的Java源代码很可能不是最新的！
			if (
					//去掉vista判断
//					ZCWindowsLookAndFeel.isOnVista() 
//					&& 
					((sortIcon = getIcon()) instanceof javax.swing.plaf.UIResource|| sortIcon == null)) 
				
			{
				//原UI代码：原代码里为了仿vista样式，标头高度很高，现注释掉！
//				contentTop += 1;
				
				setIcon(null);
				sortIcon = null;
				
				//** BUG Fixed：2012-09-13 by Jsck Jiang
				//** 该BUG产生原因是早期的1.6版不存在getColumnSortOrder方法，为了兼容，目前用反射来解决，
				//** 即如果当前运行的jre存在该方法则调用之，否则忽略之，最大限度保证BeautyEye的运行
//				SortOrder sortOrder = getColumnSortOrder(table, column);				
				SortOrder sortOrder = (SortOrder)ReflectHelper.invokeMethod(DefaultTableCellHeaderRenderer.class
							, this, "getColumnSortOrder"
							, new Class[]{JTable.class, int.class}, new Object[]{table, column} );
				if (sortOrder != null) 
				{
					switch (sortOrder) 
					{
						case ASCENDING: 
							sortIcon =
								UIManager.getIcon("Table.ascendingSortIcon");
							break;
						case DESCENDING:
							sortIcon =
								UIManager.getIcon("Table.descendingSortIcon");
							break;
					}
				}
				
				if (sortIcon != null) 
				{
					//原UI代码：原代码里为了仿vista样式，标头高度很高，现注释掉！
//					contentBottom = sortIcon.getIconHeight();
					border = new IconBorder(sortIcon, contentTop, contentLeft, 
							contentBottom, contentRight);
				} 
				else 
				{
					sortIcon = UIManager.getIcon("Table.ascendingSortIcon");
					int sortIconHeight = (sortIcon != null) ? sortIcon.getIconHeight() : 0;
					if (sortIconHeight != 0)
					{
						//原UI代码：原代码里为了仿vista样式，标头高度很高，现注释掉！
//						contentBottom = sortIconHeight;
					}
					border = new EmptyBorder(
						//原UI代码：原代码里为了仿vista样式，标头高度很高，现注释掉！
//						sortIconHeight + contentTop, contentLeft, contentBottom, contentRight);
//						/现代码：border的top不要那么（原代码是为了仿vista的）
						contentTop, contentLeft, contentBottom, contentRight);
				}
			} 
			else 
			{
				contentTop += 3;
				border = new EmptyBorder(contentTop, contentLeft, contentBottom, contentRight);
			}
			setBorder(border);
			
			
			//** jb2011设置字体颜色和加粗
//			this.setForeground(ColorHelper.DARK_GRAY1_LIKE_APPLE);
//			this.setFont(this.getFont().deriveFont(Font.BOLD));
			
			return this;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		public void paint(Graphics g) 
		{
			Dimension size = getSize();
//			State state = State.NORMAL;
//			TableColumn draggedColumn = header.getDraggedColumn();
//			if (draggedColumn != null && 
//					column == SwingUtilities2.convertColumnIndexToView(
//							header.getColumnModel(), 
//							draggedColumn.getModelIndex())) 
//			{
//				state = State.PRESSED;
//			} 
//			else if (isSelected || hasFocus || hasRollover) 
//			{
//				state = State.HOT;
//			} 
			/* on Vista there are more states for sorted columns */
//			if (WinUtils.isOnVista()) 
//			{
//				SortOrder sortOrder = getColumnSortOrder(header.getTable(), column);
//				if (sortOrder != null) 
//				{
//					switch(sortOrder) 
//					{
//						case ASCENDING:
//							/* falls through */
//						case DESCENDING:
//							switch (state)
//							{
//								case NORMAL:
//									state = State.SORTEDNORMAL;
//									break;
//								case PRESSED:
//									state = State.SORTEDPRESSED;
//									break;
//								case HOT:
//									state = State.SORTEDHOT;
//									break;
//								default:
//									/* do nothing */
//							}
//						default : 
//							/* do nothing */
//					}
//				}
//			}
			
			paintHeadCell(g, size);
//			skin.paintSkin(g, 0, 0, size.width-1, size.height-1, state);
			
			super.paint(g);
		}
	}
	
	//* 由jb2011 修改自WindowsTableHeaderUI里的同名类.
	/**
	 * A border with an Icon at the middle of the top side.
	 * Outer insets can be provided for this border.
	 */
	private static class IconBorder implements Border, UIResource
	{
		
		/** The icon. */
		private final Icon icon;
		
		/** The top. */
		private final int top;
		
		/** The left. */
		private final int left;
		
		/** The bottom. */
		private final int bottom;
		
		/** The right. */
		private final int right;
		
		/**
		 * Creates this border;.
		 *
		 * @param icon - icon to paint for this border
		 * @param top the top
		 * @param left the left
		 * @param bottom the bottom
		 * @param right the right
		 */
		public IconBorder(Icon icon, int top, int left, int bottom, int right) 
		{
			this.icon = icon;
			this.top = top;
			this.left = left;
			this.bottom = bottom;
			this.right = right;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
		 */
		public Insets getBorderInsets(Component c) 
		{
			//原UI代码：原代码里为了仿vista样式，标头高度很高，现注释掉！
//			return new Insets(icon.getIconHeight() + top, left, bottom, right);
			//现代码
			return new Insets(top, left, bottom, right);
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.border.Border#isBorderOpaque()
		 */
		public boolean isBorderOpaque() 
		{
			return false;
		}
		
		/* (non-Javadoc)
		 * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
		 */
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
		{
			icon.paintIcon(c, g, 
					
			//原UI代码：原代码里为了仿vista样式，标头高度很高且图标的位置是放在top中央，现注释掉！
//			x + left + (width - left - right - icon.getIconWidth()) / 2, y + top);
					
			//现代码：图标放在右边利往左来2个像素的位置（好看一点）
			x + left + width - right - icon.getIconWidth()-2, y );
		}
	}
}


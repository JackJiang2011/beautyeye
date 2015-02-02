/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEComboBoxUI.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch14_combox;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

// TODO: Auto-generated Javadoc
/**
 * JComboBox的UI实现类。.
 *
 * @author Jack Jiang(jb2011@163.com), 2012-06-30
 * @version 1.0
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//本类的实现参考了WindowsComboBoxUI
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEComboBoxUI extends BasicComboBoxUI 
	implements org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.__UseParentPaintSurported
{
    /**
     * Creates the ui.
     *
     * @param c the c
     * @return the component ui
     */
    public static ComponentUI createUI(JComponent c) 
    {
//    	//监听焦点改变
//    	if(NLLookAndFeel.paintFocusedBorder&&NLLookAndFeel.comboxFocusedThikness>=1)
//    		c.addFocusListener(NLLookAndFeel.FocusListenerImpl.getInstance()
//    				.setFocusedThikness(NLLookAndFeel.comboxFocusedThikness));
        return new BEComboBoxUI();
    }  
    
    //* 本方法由Jack Jiang于2012-09-07日加入
    /**
     * 是否使用父类的绘制实现方法，true表示是.
     * <p>
     * 因为在BE LNF中，边框和背景等都是使用N9图，没法通过设置背景色和前景
     * 色来控制JComboBox的颜色和边框，本方法的目的就是当用户设置了进度条的border或背景色
     * 时告之本实现类不使用BE LNF中默认的N9图填充绘制而改用父类中的方法（父类中的方法
     * 就可以支持颜色的设置罗，只是丑点，但总归是能适应用户的需求场景要求，其实用户完全可以
     * 通过JComboBox.setUI(..)方式来自定义UI哦）.
     *
     * @return true, if is use parent paint
     */
    public boolean isUseParentPaint()
    {
    	return comboBox != null 
    		&& ( !(comboBox.getBorder() instanceof UIResource)
    				||!(comboBox.getBackground() instanceof UIResource));
    }
    
    //copy from parent and modified by Jack Jiang
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicComboBoxUI#installUI(javax.swing.JComponent)
     */
    @Override
    public void installUI( JComponent c )
    {
    	super.installUI(c);
    	
    	//2012-08-30*******************************************************【重要说明】 START 对应BEListUI中的【重要说明】
    	//* 【重要说明】因BEListUI中为了使列表行单元高变的更高（在MyDefaultListCellRenderer.java中
    	//* 像COmboxRender一样通过增到border不起效果，它可能是BasicListUI的设计缺陷，它要么取FixedCellHeight
    	//* 固定值，要么取getPreferSize()即自动计算高度——它似乎是不计入border的，所以render设置border不起效）
    	//* 所以只能为列表单元设置因定值：list.setFixedCellHeight(30)，但它将影响Combox里的行高（也会变成30高）
    	//* 所以此处要把列表UI中强制设定的30高针对Combox还原成自动计算（API中规定FixedCellHeight==-1即表示自动计算）
    	popup.getList().setFixedCellHeight(-1);
    	//**************************************************************** 【重要说明】 END
    	
    	//* 以下代码由jb2011加入
//    	comboBox.setMaximumRowCount(8);//这个最大行可以起效，但似乎它的行高指的是一个固定值而不是计算值，像本LNF里因cell本身行高就很高
    								   //即使设置了最大显示行，但是显示的并不是指定值，有待进一步研究
    	//为下拉框的弹出弹加border，这样上下空白外一点好看一些
    	// install the scrollpane border
        Container parent = popup.getList().getParent();  // should be viewport
        if (parent != null) 
        {
            parent = parent.getParent();  // should be the scrollpane
            if (parent != null && parent instanceof JScrollPane) 
                LookAndFeel.installBorder((JScrollPane)parent, "ComboBox.scrollPaneBorder");//*~ 注：这个属性是Jack Jiang仿照JTabel里的实现自已加的属性
        }
    }
    
    /**
     * Gets the combox.
     *
     * @return the combox
     */
    public JComboBox getCombox()
    {
    	return this.comboBox;
    }
    
    //copy from BasicComboBoxUI and modified by jb2011
    //自定义下接框箭头按钮实现类
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createArrowButton()
     */
    @Override 
    protected JButton createArrowButton() 
    {
    	JButton button = new JButton()
    	{
    		//不允许设置border
    		public void setBorder(Border b){
    		}
    		//背景填充
    		public void paint(Graphics g){
    			boolean isEnabled = isEnabled();
    			boolean isPressed = getModel().isPressed();
    			if(isEnabled)
    			{
    				if(isPressed)
    					__Icon9Factory__.getInstance().getButtonArrow_pressed()
						.draw((Graphics2D)g, 0, 0, this.getWidth(), this.getHeight());
    				else
    					__Icon9Factory__.getInstance().getButtonArrow_normal()
    						.draw((Graphics2D)g, 0, 0, this.getWidth(), this.getHeight());
    			}
    			else
    			{
    				__Icon9Factory__.getInstance().getButtonArrow_disable()
						.draw((Graphics2D)g, 0, 0, this.getWidth(), this.getHeight());
    			}
    		}
    		// Don't want the button to participate in focus traversable.
    		public boolean isFocusTraversable(){
    			return false;
    		}
    		/**
    		 * Returns the preferred size of the {@code BasicArrowButton}.
    		 *
    		 * @return the preferred size
    		 */
    		public Dimension getPreferredSize() {
    			return new Dimension(20, 20);
    		}
    		/**
    		 * Returns the minimum size of the {@code BasicArrowButton}.
    		 *
    		 * @return the minimum size
    		 */
    		public Dimension getMinimumSize() {
    			return new Dimension(5, 5);
    		}
    		/**
    		 * Returns the maximum size of the {@code BasicArrowButton}.
    		 *
    		 * @return the maximum size
    		 */
    		public Dimension getMaximumSize() {
    			return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    		}
    	};
    	button.setName("ComboBox.arrowButton");
    	return button;
    }
    
    //* copy from BasicComboBoxUI and modified by jb2011
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicComboBoxUI#paint(java.awt.Graphics, javax.swing.JComponent)
     */
    @Override
    public void paint( Graphics g, JComponent c ) 
    {
        hasFocus = comboBox.hasFocus();
//        if ( !comboBox.isEditable() ) 
        {
            Rectangle r = rectangleForCurrentValue();
            paintCurrentValueBackground(g,r,hasFocus);
            if(!comboBox.isEditable())
            	paintCurrentValue(g,r,hasFocus);
        }
    }
    
    //* copy from BasicComboBoxUI and modified by jb2011
    /**
     * Paints the background of the currently selected item.
     *
     * @param g the g
     * @param bounds the bounds
     * @param hasFocus the has focus
     */
    public void paintCurrentValueBackground(Graphics g,Rectangle bounds,boolean hasFocus) 
    {
    	//* 如果用户作了自定义颜色设置则使用父类方法来实现绘制，否则BE LNF中没法支持这些设置哦
    	if(!isUseParentPaint())
    	{
    		if ( comboBox.isEnabled() )
    			org.jb2011.lnf.beautyeye.ch6_textcoms.__Icon9Factory__.getInstance().getTextFieldBgNormal()
    			.draw((Graphics2D)g, 0,0,comboBox.getWidth(),comboBox.getHeight());
    		else
    			org.jb2011.lnf.beautyeye.ch6_textcoms.__Icon9Factory__.getInstance().getTextFieldBgDisabled()
    			.draw((Graphics2D)g, 0,0,comboBox.getWidth(),comboBox.getHeight());
    	}
    	else
    	{
    		super.paintCurrentValueBackground(g, bounds, hasFocus);
    	}
    }
    
    //* copy from BasicComboBoxUI and modified by jb2011
    /**
     * Creates the default renderer that will be used in a non-editiable combo 
     * box. A default renderer will used only if a renderer has not been 
     * explicitly set with <code>setRenderer</code>.
     * 
     * @return a <code>ListCellRender</code> used for the combo box
     * @see javax.swing.JComboBox#setRenderer
     */
    protected ListCellRenderer createRenderer() 
    {
        return new BEComboBoxRenderer.UIResource(this);
    }
    
    //* 由jb2011 Override本方法的目的是改变父类方法可见性，方便第3方调用
    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicComboBoxUI#getInsets()
     */
    public Insets getInsets()
    {
    	return super.getInsets();
    }
    
    //---------------------------------------------------------
//    static boolean isMenuShortcutKeyDown(InputEvent event) {
//        return (event.getModifiers() & 
//                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
//    }
//    /**
//     * Creates the JList used in the popup to display 
//     * the items in the combo box model. This method is called when the UI class
//     * is created.
//     *
//     * @return a <code>JList</code> used to display the combo box items
//     */
//    protected JList createList() {
//	return new JList( comboBox.getModel() ) {
//            public void processMouseEvent(MouseEvent e)  {
//                if (
////                		BasicGraphicsUtils.
//                		isMenuShortcutKeyDown(e))  {
//                    // Fix for 4234053. Filter out the Control Key from the list. 
//                    // ie., don't allow CTRL key deselection.
//                    Toolkit toolkit = Toolkit.getDefaultToolkit();
//                    e = new MouseEvent((Component)e.getSource(), e.getID(), e.getWhen(), 
//                                       e.getModifiers() ^ toolkit.getMenuShortcutKeyMask(),
//                                       e.getX()-10, e.getY(),
//                                       e.getXOnScreen()-10, e.getYOnScreen(),
//                                       e.getClickCount(),
//                                       e.isPopupTrigger(),
//                                       MouseEvent.NOBUTTON);
//                }
//                super.processMouseEvent(e);
//            }
//        };
//    }
    
    //* 以下代码拷自父类，目的是修正弹出popup窗口的x、y坐标，不像菜单UI里有
    //* Menu.menuPopupOffsetX等4个属性可设置以备对坐标进行调整，BeautyEye LNF中由Jack Jiang
    //* 依照Menu中的实现自定义了2个属性，以便以后配置。参考自jdk1.6.0_u18源码.
    /**
     * Creates the popup portion of the combo box.
     *
     * @return an instance of <code>ComboPopup</code>
     * @see ComboPopup
     */
    protected ComboPopup createPopup() {
    	return new BasicComboPopup( comboBox ){
    		/** popupOffsetX是jb2011自定的属性，用于修正下拉框的弹出窗的X坐标 */
    		private int popupOffsetX = UIManager.getInt("ComboBox.popupOffsetX");
    		/** popupOffsetY是jb2011自定的属性，用于修正下拉框的弹出窗的Y坐标 */
    		private int popupOffsetY = UIManager.getInt("ComboBox.popupOffsetY");
    		
    		//* copy from parent and modified by Jack Jiang
    		/**
    		 * Implementation of ComboPopup.show().
    		 */
    		public void show() {
    			setListSelection(comboBox.getSelectedIndex());
    			Point location = getPopupLocation();
    			show( comboBox
    					//以下x、y坐标修正代码由Jack Jiang增加
    					, location.x + popupOffsetX //*~ popupOffsetX是自定属性，用于修改弹出窗的X坐标
    					, location.y + popupOffsetY //*~ popupOffsetY是自定属性，用于修改弹出窗的Y坐标
    			);
    		}

    		//* copy from parent and no modified
    		/**
    		 * Sets the list selection index to the selectedIndex. This 
    		 * method is used to synchronize the list selection with the 
    		 * combo box selection.
    		 * 
    		 * @param selectedIndex the index to set the list
    		 */
    		private void setListSelection(int selectedIndex) {
    			if ( selectedIndex == -1 ) {
    				list.clearSelection();
    			}
    			else {
    				list.setSelectedIndex( selectedIndex );
    				list.ensureIndexIsVisible( selectedIndex );
    			}
    		}

    		//* copy from parent and no modified
    		/**
    		 * Calculates the upper left location of the Popup.
    		 */
    		private Point getPopupLocation() {
    			Dimension popupSize = comboBox.getSize();
    			Insets insets = getInsets();

    			// reduce the width of the scrollpane by the insets so that the popup
    			// is the same width as the combo box.
    			popupSize.setSize(popupSize.width - (insets.right + insets.left), 
    					getPopupHeightForRowCount( comboBox.getMaximumRowCount()));
    			Rectangle popupBounds = computePopupBounds( 0, comboBox.getBounds().height,
    					popupSize.width, popupSize.height);
    			Dimension scrollSize = popupBounds.getSize();
    			Point popupLocation = popupBounds.getLocation();

    			scroller.setMaximumSize( scrollSize );
    			scroller.setPreferredSize( scrollSize );
    			scroller.setMinimumSize( scrollSize );

    			list.revalidate();

    			return popupLocation;
    		}
    	};
    }
    
    //## Bug FIX：Issue 49(https://code.google.com/p/beautyeye/issues/detail?id=49) 
    //* 此方法由Jack Jiang于2012-10-12加入：重写父类方法的目的是使得默认的
    //* Editor透明（即不填充默认背景），因为BE LNF中JTextField的LNF是用NP图
    //* 实现的，此处不透明的话就会遮住NP背景图，从而使得外观难看。
    /**
     * Creates the default editor that will be used in editable combo boxes.  
     * A default editor will be used only if an editor has not been 
     * explicitly set with <code>setEditor</code>.
     *
     * @return a <code>ComboBoxEditor</code> used for the combo box
     * @see javax.swing.JComboBox#setEditor
     */
    protected ComboBoxEditor createEditor() 
    {
    	BasicComboBoxEditor.UIResource bcbe = new BasicComboBoxEditor.UIResource();
    	if(bcbe != null)
    	{
    		Component c = bcbe.getEditorComponent();
    		if(c != null)
    		{
    			//把默认的Editor设置成透明(editor不透明的话就会遮住NP背景图，从而使得外观难看)
    			((JComponent)c).setOpaque(false);
    			
    			//* 以下这段是为了给默认Editor加上border而加（没有它个border将使
    			//* 得与不可编辑comboBox的内容组件看起来有差异哦），
    			//* 在WindowsComboBoxUI中，这段代码是放在WindowsComboBoxEditor
    			//* 中的方法createEditorComponent中实现，由于该 方法是1.6里才有的，
    			//* BE LNF因要兼容java1.5，所以不作类似实现就在本方法中实现也没有问题。
    			//* 类似实现请参考WindowsComboBoxUI.WindowsComboBoxEditor类
//    			JTextField editor = (JTextField)c;
                Border border = (Border)UIManager.get("ComboBox.editorBorder");
                if (border != null) 
                {
                	((JComponent)c).setBorder(border);
                }
    		}
    	}
        return bcbe;
    }
}

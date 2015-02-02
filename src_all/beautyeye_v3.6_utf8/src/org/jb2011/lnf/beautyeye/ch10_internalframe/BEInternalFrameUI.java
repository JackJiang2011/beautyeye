/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BEInternalFrameUI.java at 2015-2-1 20:25:37, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.ch10_internalframe;

import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicInternalFrameUI;

// TODO: Auto-generated Javadoc
/**
 * 内部窗体UI实现类.
 * 
 * @author Jack Jiang
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//BeautyEye外观实现中取消了isPalette的所有特殊处理，isPalette及相关属性在
//该外观中将失去意义，请注意。
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class BEInternalFrameUI extends BasicInternalFrameUI
{
	
	/** The title pane. */
	private BEInternalFrameTitlePane titlePane;

	/** The Constant metalPropertyChangeListener. */
	private static final PropertyChangeListener metalPropertyChangeListener = new XZCMetalPropertyChangeHandler();

	/** The Constant handyEmptyBorder. */
	private static final Border handyEmptyBorder = new EmptyBorder(0, 0, 0, 0);

//	protected static String IS_PALETTE = "JInternalFrame.isPalette";

	/** The FRAM e_ type. */
private static String FRAME_TYPE = "JInternalFrame.frameType";
	
	/** The NORMA l_ frame. */
	private static String NORMAL_FRAME = "normal";
//	private static String PALETTE_FRAME = "palette";
	/** The OPTIO n_ dialog. */
private static String OPTION_DIALOG = "optionDialog";

	/**
	 * Instantiates a new bE internal frame ui.
	 *
	 * @param b the b
	 */
	public BEInternalFrameUI(JInternalFrame b)
	{
		super(b);
	}

	/**
	 * Creates the ui.
	 *
	 * @param c the c
	 * @return the component ui
	 */
	public static ComponentUI createUI(JComponent c)
	{
		return new BEInternalFrameUI((JInternalFrame) c);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#installUI(javax.swing.JComponent)
	 */
	public void installUI(JComponent c)
	{
		super.installUI(c);

//		Object paletteProp = c.getClientProperty(IS_PALETTE);
//		if (paletteProp != null)
//		{
//			setPalette(((Boolean) paletteProp).booleanValue());
//		}

		Container content = frame.getContentPane();
		stripContentBorder(content);
		//c.setOpaque(false);
		
		frame.setOpaque(false);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#uninstallUI(javax.swing.JComponent)
	 */
	public void uninstallUI(JComponent c)
	{
		frame = (JInternalFrame) c;

		Container cont = ((JInternalFrame) (c)).getContentPane();
		if (cont instanceof JComponent)
		{
			JComponent content = (JComponent) cont;
			if (content.getBorder() == handyEmptyBorder)
			{
				content.setBorder(null);
			}
		}
		super.uninstallUI(c);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#installListeners()
	 */
	protected void installListeners()
	{
		super.installListeners();
		frame.addPropertyChangeListener(metalPropertyChangeListener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#uninstallListeners()
	 */
	protected void uninstallListeners()
	{
		frame.removePropertyChangeListener(metalPropertyChangeListener);
		super.uninstallListeners();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#installKeyboardActions()
	 */
	protected void installKeyboardActions()
	{
		super.installKeyboardActions();
		ActionMap map = SwingUtilities.getUIActionMap(frame);
		if (map != null)
		{
			// BasicInternalFrameUI creates an action with the same name, we override
			// it as Metal frames do not have system menus.
			map.remove("showSystemMenu");
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#uninstallKeyboardActions()
	 */
	protected void uninstallKeyboardActions()
	{
		super.uninstallKeyboardActions();
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#uninstallComponents()
	 */
	protected void uninstallComponents()
	{
		titlePane = null;
		super.uninstallComponents();
	}

	/**
	 * Strip content border.
	 *
	 * @param c the c
	 */
	private void stripContentBorder(Object c)
	{
		if (c instanceof JComponent)
		{
			JComponent contentComp = (JComponent) c;
			Border contentBorder = contentComp.getBorder();
			if (contentBorder == null || contentBorder instanceof UIResource)
			{
				contentComp.setBorder(handyEmptyBorder);
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicInternalFrameUI#createNorthPane(javax.swing.JInternalFrame)
	 */
	protected JComponent createNorthPane(JInternalFrame w)
	{
		titlePane = new BEInternalFrameTitlePane(w);
		return titlePane;
	}

	/**
	 * Sets the frame type.
	 *
	 * @param frameType the new frame type
	 */
	private void setFrameType(String frameType)
	{
		if (frameType.equals(OPTION_DIALOG))
		{
			LookAndFeel.installBorder(frame, "InternalFrame.optionDialogBorder");
//			titlePane.setPalette(false);
		}
//		else if (frameType.equals(PALETTE_FRAME))
//		{
//			LookAndFeel.installBorder(frame, "InternalFrame.paletteBorder");
////			titlePane.setPalette(true);
//		}
		else
		{
			LookAndFeel.installBorder(frame, "InternalFrame.border");
//			titlePane.setPalette(false);
		}
	}

//	// this should be deprecated - jcs
//	public void setPalette(boolean isPalette)
//	{
////		if (isPalette)
////		{
////			LookAndFeel.installBorder(frame, "InternalFrame.paletteBorder");
////		}
////		else
//		{
//			LookAndFeel.installBorder(frame, "InternalFrame.border");
//		}
////		titlePane.setPalette(isPalette);
//	}

	/**
 * The Class XZCMetalPropertyChangeHandler.
 */
private static class XZCMetalPropertyChangeHandler implements
			PropertyChangeListener
	{
		
		/* (non-Javadoc)
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent e)
		{
			String name = e.getPropertyName();
			JInternalFrame jif = (JInternalFrame) e.getSource();

			if (!(jif.getUI() instanceof BEInternalFrameUI))
			{
				return;
			}

			BEInternalFrameUI ui = (BEInternalFrameUI) jif.getUI();
			if (name.equals(FRAME_TYPE))
			{
				if (e.getNewValue() instanceof String)
				{
					ui.setFrameType((String) e.getNewValue());
				}
			}
//			else if (name.equals(IS_PALETTE))
//			{
//				if (e.getNewValue() != null)
//				{
//					ui.setPalette(((Boolean) e.getNewValue()).booleanValue());
//				}
//				else
//				{
//					ui.setPalette(false);
//				}
//			}
			else if (name.equals(JInternalFrame.CONTENT_PANE_PROPERTY))
			{
				ui.stripContentBorder(e.getNewValue());
			}
		}
	} // end class MetalPropertyChangeHandler
}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * T.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;


// TODO: Auto-generated Javadoc
/**
 * The Class T.
 */
public class T
{
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args)throws Exception
	{
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		JDialog.setDefaultLookAndFeelDecorated(true);
		
//		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;
		BeautyEyeLNFHelper.launchBeautyEyeLNF();
		
//		UIManager.setLookAndFeel(new MetalLookAndFeel());
		
		JFrame f = new JFrame();
//		f.setDefaultLookAndFeelDecorated(false);
		
//		com.sun.awt.AWTUtilities.setWindowOpacity(f, 0.5f);
//		com.sun.awt.AWTUtilities.setWindowOpaque(f, true);
//		f.pack();
		f.setBounds(100,100,300,300);
		
		
		JToolBar tb = new JToolBar();
		tb.add(new JTextField("111111111111111111"));
		
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(tb, BorderLayout.NORTH);
		f.getContentPane().add(new JButton("1111111111111111111111"), BorderLayout.CENTER);
		
		
//		((JComponent)f.getContentPane())
//			.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//		f.setUndecorated(false);
		f.setVisible(true);
		
//		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

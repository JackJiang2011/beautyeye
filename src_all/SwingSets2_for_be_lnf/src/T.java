/*
 * 
 */
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;


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
		
		f.getContentPane().add(new JButton("1111111111111111111111"));
		
//		((JComponent)f.getContentPane())
//			.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//		f.setUndecorated(false);
		f.setVisible(true);
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * MySwingUtilities2.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

package org.jb2011.lnf.beautyeye.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

// TODO: Auto-generated Javadoc
/**
 * 本类中的方法一一对应于SUN未公开的类SwingUtilities2中的方法。
 * <p>
 * 本类中的各方法都是自动据JVM版本不同对SwingUtilities2进行反射调用
 * 实现的，以便解决兼容性问题。.
 *
 * @author Jack Jiang(jb2011@163.com)
 */
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 Start
//此类中方法一一对应于官方非公开的SwingUtilities2类中的方法,
//只因该类是非公开类，在不同版本里会被移动，甚至未来有被取消的可能哦，
//所以这了最大化兼容1.5版jdk，所以做个类通过反射来调用该jre里的SwingUtilities2实现
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 一些说明 END
public class MySwingUtilities2
{
    
    /**
     * Returns the FontMetrics for the current Font of the passed
     * in Graphics.  This method is used when a Graphics
     * is available, typically when painting.  If a Graphics is not
     * available the JComponent method of the same name should be used.
     * <p>
     * Callers should pass in a non-null JComponent, the exception
     * to this is if a JComponent is not readily available at the time of
     * painting.
     * <p>
     * This does not necessarily return the FontMetrics from the
     * Graphics.
     *
     * @param c JComponent requesting FontMetrics, may be null
     * @param g Graphics Graphics
     * @return the font metrics
     */
    public static FontMetrics getFontMetrics(JComponent c, Graphics g) {
//        return getFontMetrics(c, g, g.getFont());
    	return (FontMetrics)invokeSwingUtilities2StaticMethod("getFontMetrics"
    			   , new Class[]{JComponent.class, Graphics.class}
    			   , new Object[]{c, g});
    }


    /**
     * Returns the FontMetrics for the specified Font.
     * This method is used when a Graphics is available, typically when
     * painting.  If a Graphics is not available the JComponent method of
     * the same name should be used.
     * <p>
     * Callers should pass in a non-null JComonent, the exception
     * to this is if a JComponent is not readily available at the time of
     * painting.
     * <p>
     * This does not necessarily return the FontMetrics from the
     * Graphics.
     *
     * @param c Graphics Graphics
     * @param g the g
     * @param font Font to get FontMetrics for
     * @return the font metrics
     */
    public static FontMetrics getFontMetrics(JComponent c, Graphics g,
                                             Font font) {
//        if (c != null) {
//            // Note: We assume that we're using the FontMetrics
//            // from the widget to layout out text, otherwise we can get
//            // mismatches when printing.
//            return c.getFontMetrics(font);
//        }
//        return Toolkit.getDefaultToolkit().getFontMetrics(font);
    	return (FontMetrics)invokeSwingUtilities2StaticMethod("getFontMetrics"
   			   , new Class[]{JComponent.class, Graphics.class, Font.class}
   			   , new Object[]{c, g, font});
    }

    /**
     * Returns the width of the passed in String.
     *
     * @param c JComponent that will display the string, may be null
     * @param fm FontMetrics used to measure the String width
     * @param string String to get the width of
     * @return the int
     */
    public static int stringWidth(JComponent c, FontMetrics fm, String string){
//        return fm.stringWidth(string);
    	return (Integer)invokeSwingUtilities2StaticMethod("stringWidth"
  			   , new Class[]{JComponent.class, FontMetrics.class, String.class}
  			   , new Object[]{c, fm, string});
    }

    /**
     * Draws the string at the specified location.
     *
     * @param c JComponent that will display the string, may be null
     * @param g Graphics to draw the text to
     * @param text String to display
     * @param x X coordinate to draw the text at
     * @param y Y coordinate to draw the text at
     */
    public static void drawString(JComponent c, Graphics g, String text,
                                  int x, int y) {
    	invokeSwingUtilities2StaticMethod("drawString"
 			   , new Class[]{JComponent.class, Graphics.class, String.class, int.class, int.class}
 			   , new Object[]{c, g, text, x, y});
    	
//        // c may be null
//
//        // All non-editable widgets that draw strings call into this
//        // methods.  By non-editable that means widgets like JLabel, JButton
//        // but NOT JTextComponents.
//        if ( text == null || text.length() <= 0 ) { //no need to paint empty strings
//            return;
//        }
//        if (isPrinting(g)) {
//            Graphics2D g2d = getGraphics2D(g);
//            if (g2d != null) {
//                TextLayout layout = new TextLayout(text, g2d.getFont(),
//                                                   DEFAULT_FRC);
//
//                /* Use alternate print color if specified */
//                Color col = g2d.getColor();
//                if (col instanceof PrintColorUIResource) {
//                    g2d.setColor(((PrintColorUIResource)col).getPrintColor());
//                }
//
//                layout.draw(g2d, x, y);
//                
//                g2d.setColor(col);
//
//                return;
//            }
//        } 
//
//        // If we get here we're not printing
//        if (drawTextAntialiased(c) && (g instanceof Graphics2D)) {
//            Graphics2D g2 = (Graphics2D)g;
//            Object oldAAValue = g2.getRenderingHint(
//                                       RenderingHints.KEY_TEXT_ANTIALIASING);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//            g.drawString(text, x, y);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                                    oldAAValue);
//        }
//        else {
//            g.drawString(text, x, y);
//        }
    }
    
    /*
     * returns true if the Graphics is print Graphics
     * false otherwise
     */
    /**
     * Checks if is printing.
     *
     * @param g the g
     * @return true, if is printing
     */
    static boolean isPrinting(Graphics g) 
    {
//        return (g instanceof PrinterGraphics || g instanceof PrintGraphics);
    	return (Boolean)invokeSwingUtilities2StaticMethod("isPrinting"
    			   , new Class[]{Graphics.class}
    			   , new Object[]{g});
    }
    
    /**
     * Returns whether or not text should be drawn antialiased.
     *
     * @param c JComponent to test.
     * @return Whether or not text should be drawn antialiased for the
     *         specified component.
     */
    private static boolean drawTextAntialiased(JComponent c) {
//        if (!AA_TEXT_DEFINED) {
//            if (c != null) {
//                // Check if the component wants aa text
////                return ((Boolean)c.getClientProperty(
////                                  AA_TEXT_PROPERTY_KEY)).booleanValue();
//            	return false;
//            }
//            // No component, assume aa is off
//            return false;
//        }
//        // 'swing.aatext' was defined, use its value.
//        return AA_TEXT;
    	return (Boolean)invokeSwingUtilities2StaticMethod("drawTextAntialiased"
   			   , new Class[]{JComponent.class}
   			   , new Object[]{c});
    }

    /**
     * Returns whether or not text should be drawn antialiased.
     *
     * @param aaText Whether or not aa text has been turned on for the
     *        component.
     * @return Whether or not text should be drawn antialiased.
     */
    public static boolean drawTextAntialiased(boolean aaText) 
    {
//        if (!AA_TEXT_DEFINED) {
//            // 'swing.aatext' wasn't defined, use the components aa text value.
//            return aaText;
//        }
//        // 'swing.aatext' was defined, use its value.
//        return AA_TEXT;
    	return (Boolean)invokeSwingUtilities2StaticMethod("drawTextAntialiased"
  			   , new Class[]{boolean.class}
  			   , new Object[]{aaText});
    }
    
    /* 
     * Tries it best to get Graphics2D out of the given Graphics
     * returns null if can not derive it.
     */
    /**
     * Gets the graphics2 d.
     *
     * @param g the g
     * @return the graphics2 d
     */
    public static Graphics2D getGraphics2D(Graphics g) 
    {
//        if (g instanceof Graphics2D) {
//            return (Graphics2D) g;
//        } else if (g instanceof ProxyPrintGraphics) {
//            return (Graphics2D)(((ProxyPrintGraphics)g).getGraphics());
//        } else {
//            return null;
//        }
    	return (Graphics2D)invokeSwingUtilities2StaticMethod("getGraphics2D"
 			   , new Class[]{Graphics.class}
 			   , new Object[]{g});
    }
    
    /**
     * Draws the string at the specified location underlining the specified
     * character.
     *
     * @param c JComponent that will display the string, may be null
     * @param g Graphics to draw the text to
     * @param text String to display
     * @param underlinedIndex Index of a character in the string to underline
     * @param x X coordinate to draw the text at
     * @param y Y coordinate to draw the text at
     */
    public static void drawStringUnderlineCharAt(JComponent c,Graphics g,
                           String text, int underlinedIndex, int x,int y) {
//        drawString(c, g, text, x, y);
//        if (underlinedIndex >= 0 && underlinedIndex < text.length() ) {
//            // PENDING: this needs to change.
//            FontMetrics fm = g.getFontMetrics();
//            int underlineRectX = x + stringWidth(c,
//                                      fm, text.substring(0,underlinedIndex));
//            int underlineRectY = y;
//            int underlineRectWidth = fm.charWidth(text.
//                                                  charAt(underlinedIndex));
//            int underlineRectHeight = 1;
//            g.fillRect(underlineRectX, underlineRectY + 1,
//                       underlineRectWidth, underlineRectHeight);
//        }
    	invokeSwingUtilities2StaticMethod("drawStringUnderlineCharAt"
 			   , new Class[]{JComponent.class, Graphics.class, String.class, int.class, int.class, int.class}
 			   , new Object[]{c, g, text, underlinedIndex, x, y});
    }

   /**
    * Clips the passed in String to the space provided.
    *
    * @param c JComponent that will display the string, may be null
    * @param fm FontMetrics used to measure the String width
    * @param string String to display
    * @param availTextWidth Amount of space that the string can be drawn in
    * @return Clipped string that can fit in the provided space.
    */
   public static String clipStringIfNecessary(JComponent c, FontMetrics fm,
                                              String string,
                                              int availTextWidth) {
//       if ((string == null) || (string.equals("")))  {
//           return "";
//       }
//       int textWidth = stringWidth(c, fm, string);
//       if (textWidth > availTextWidth) {
//           return clipString(c, fm, string, availTextWidth);
//       }
	   string = (String)invokeSwingUtilities2StaticMethod("clipStringIfNecessary"
			   , new Class[]{JComponent.class, FontMetrics.class, String.class, int.class}
			   , new Object[]{c, fm, string, availTextWidth});
       return string;
   }
   
   /**
    * Clips the passed in String to the space provided.  NOTE: this assumes
    * the string does not fit in the available space.
    *
    * @param c JComponent that will display the string, may be null
    * @param fm FontMetrics used to measure the String width
    * @param string String to display
    * @param availTextWidth Amount of space that the string can be drawn in
    * @return Clipped string that can fit in the provided space.
    */
   public static String clipString(JComponent c, FontMetrics fm,
                                   String string, int availTextWidth) {
	   string = (String)invokeSwingUtilities2StaticMethod("clipString"
			   , new Class[]{JComponent.class, FontMetrics.class, String.class, int.class}
			   , new Object[]{c, fm, string, availTextWidth});
	   
       return string;
   }
   
   /**
    * Invoke swing utilities2 static method.
    *
    * @param methodName the method name
    * @param paramsType the params type
    * @param paramsValue the params value
    * @return the object
    */
   public static Object invokeSwingUtilities2StaticMethod(String methodName
		   , Class[] paramsType, Object[] paramsValue)
   {
	   return ReflectHelper.invokeStaticMethod(ReflectHelper.getClass(getSwingUtilities2ClassName())
			   , methodName, paramsType, paramsValue);
   }
   //* SwingUtilities2是SUN的非公开api，它在不同的版本里位于不同的包内，甚至某天有消失的可能哦
   //* 但是不用它又不行，好几个地方都用到了它，用反射的好处是不需要把代码拷过来，它将自动适应各
   //* 版本里的实现，比如在新版本里这些方法里可能已经优化了优化等等
   /**
    * Gets the swing utilities2 class name.
    *
    * @return the swing utilities2 class name
    */
   public static String getSwingUtilities2ClassName()
   {
	   //java1.6及以后更新版本位于sun.swing包内，目前截至JDK1.7.0_u6是在这个包内，以后更新的版本会放哪只那以后再说罗
	   if(JVM.current().isOrLater(JVM.JDK1_6))
		   return "sun.swing.SwingUtilities2";
	   //1.6以下的老版本
	   else
		   return "com.sun.java.swing.SwingUtilities2";
   }
}

	
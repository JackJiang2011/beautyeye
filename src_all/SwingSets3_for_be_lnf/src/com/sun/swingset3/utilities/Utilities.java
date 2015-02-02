/*
 * Copyright 2007-2008 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.swingset3.utilities;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.jnlp.ServiceManager;
import javax.swing.SwingConstants;

/**
 *
 * @author aim
 */
public class Utilities {
    
    private Utilities() {
        // never instantiate
    }
    
    public static boolean runningFromWebStart() {
        return ServiceManager.getServiceNames() != null;        
    }

    public static String getURLFileName(URL url) {
        String path = url.getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }
    
    private static BufferedImage createCompatibleImage(int width, int height) {
        
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);

    }
    
    public static BufferedImage createTranslucentImage(int width, int height) {
        
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
               
    }
    
    public static BufferedImage createGradientImage(int width, int height, Color gradient1, Color gradient2) {
                   
            BufferedImage gradientImage = createCompatibleImage(width, height);
            GradientPaint gradient = new GradientPaint(0, 0, gradient1, 0, height, gradient2, false);
            Graphics2D g2 = (Graphics2D)gradientImage.getGraphics();
            g2.setPaint(gradient);
            g2.fillRect(0, 0, width, height);
            g2.dispose();
            
            return gradientImage;
    }


    public static BufferedImage createGradientMask(int width, int height, int orientation) {
        // algorithm derived from Romain Guy's blog
        BufferedImage gradient = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gradient.createGraphics();
        GradientPaint paint = new GradientPaint(0.0f, 0.0f,
                new Color(1.0f, 1.0f, 1.0f, 1.0f),
                orientation == SwingConstants.HORIZONTAL? width : 0.0f, 
                orientation == SwingConstants.VERTICAL? height : 0.0f,
                new Color(1.0f, 1.0f, 1.0f, 0.0f));
        g.setPaint(paint);
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        g.dispose();
        gradient.flush();

        return gradient;
    }

    public static Color deriveColorAlpha(Color base, int alpha) {
        return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
    }
    
    /**
     * Derives a color by adding the specified offsets to the base color's 
     * hue, saturation, and brightness values.   The resulting hue, saturation,
     * and brightness values will be contrained to be between 0 and 1.
     * @param base the color to which the HSV offsets will be added
     * @param dH the offset for hue
     * @param dS the offset for saturation
     * @param dB the offset for brightness
     * @return Color with modified HSV values
     */
    public static Color deriveColorHSB(Color base, float dH, float dS, float dB) {
        float hsb[] = Color.RGBtoHSB(
                base.getRed(), base.getGreen(), base.getBlue(), null);

        hsb[0] += dH;
        hsb[1] += dS;
        hsb[2] += dB;
        return Color.getHSBColor(
                hsb[0] < 0? 0 : (hsb[0] > 1? 1 : hsb[0]),
                hsb[1] < 0? 0 : (hsb[1] > 1? 1 : hsb[1]),
                hsb[2] < 0? 0 : (hsb[2] > 1? 1 : hsb[2]));
                                               
    }
    
    public static String getHTMLColorString(Color color) {
        String red = Integer.toHexString(color.getRed());
        String green = Integer.toHexString(color.getGreen());
        String blue = Integer.toHexString(color.getBlue());

        return "#" + 
                (red.length() == 1? "0" + red : red) +
                (green.length() == 1? "0" + green : green) +
                (blue.length() == 1? "0" + blue : blue);        
    }

   public static void printColor(String key, Color color) {
       float hsb[] = Color.RGBtoHSB(
                color.getRed(), color.getGreen(),
                color.getBlue(), null);
       System.out.println(key+": RGB=" + 
               color.getRed() + ","+ color.getGreen() + ","+ color.getBlue() + "  " +
                "HSB=" + String.format("%.0f%n",hsb[0]*360) + "," + 
                            String.format("%.3f%n",hsb[1]) + "," + 
                            String.format("%.3f%n", hsb[2]));
   }
}

/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
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
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 *
 * @author Administrator
 */
public class ArrowIcon implements Icon, SwingConstants {
    private static final float DB = -.06f;
    private int direction;
    private int size;
    private Color color;
    private BufferedImage arrowImage;
    
    public ArrowIcon(int direction) {
        this(direction, 10, null);
    }
    
    public ArrowIcon(int direction, Color color) {
        this(direction, 10, color);
    }
    
    public ArrowIcon(int direction, int size, Color color) {
        this.size = size;
        this.direction = direction;
        this.color = color;
    }

    public int getIconHeight() {        
        return size;
    }

    public int getIconWidth() {
        return size;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(getArrowImage(), x, y, c);
    }
    
    protected Image getArrowImage() {
        if (arrowImage == null) {
            arrowImage = Utilities.createTranslucentImage(size, size);
            AffineTransform atx = direction != SOUTH? new AffineTransform() : null;
            switch(direction ) {
                case NORTH:
                    atx.setToRotation(Math.PI, size/2, size/2);
                    break;
                case EAST:
                    atx.setToRotation(-(Math.PI/2), size/2, size/2);
                    break;
                case WEST:
                    atx.setToRotation(Math.PI/2, size/2, size/2);
                case SOUTH:
                default:{ /* no xform*/ }                   
            }       
            Graphics2D ig = (Graphics2D)arrowImage.getGraphics();
            if (atx != null) {
                ig.setTransform(atx);
            }
            int width = size;
            int height = size/2 + 1;
            int xx = (size - width)/2;
            int yy = (size - height + 1)/2;

            Color base = color != null? color : UIManager.getColor("controlDkShadow").darker(); 

            paintArrow(ig, base, xx, yy);
            paintArrowBevel(ig, base, xx, yy);
            paintArrowBevel(ig, Utilities.deriveColorHSB(base, 0f, 0f, .20f), xx, yy + 1);
        }
        return arrowImage;
    }
    
    protected void paintArrow(Graphics2D g, Color base, int x, int y) {
        g.setColor(base);
        /*
        Path2D.Float arrowShape = new Path2D.Float();
        arrowShape.moveTo(x, y-1);
        System.out.println("moveTo "+(x)+","+(y-1));
        arrowShape.lineTo(size-1, y-1);
        System.out.println("lineTo "+(size-1)+","+(y-1));
        arrowShape.lineTo(size/2, y+(size/2));
        System.out.println("lineTo "+(size/2)+","+(y+(size/2)));
        arrowShape.lineTo(size/2 - 1, y+(size/2));
        System.out.println("lineTo "+ (size/2 - 1)+","+(y+(size/2)));
        arrowShape.lineTo(x, y-1);
        System.out.println("lineTo "+(x)+","+(y-1));
        g.fill(arrowShape);
*/       
        int len = size - 2;
        int xx = x;
        int yy = y-1;
        while (len >= 2) {
            xx++;
            yy++;
            g.fillRect(xx, yy, len, 1);
            len -= 2;
        }
    }

    protected void paintArrowBevel(Graphics g, Color base, int x, int y) {
        int len = size;
        int xx = x;
        int yy = y;
        Color c2 = Utilities.deriveColorHSB(base, 0f, 0f, (-DB)*(size/2));
        while (len >= 2) {
            c2 = Utilities.deriveColorHSB(c2, 0f, 0f, DB);
            g.setColor(c2);
            g.fillRect(xx, yy, 1, 1);
            g.fillRect(xx + len - 1, yy, 1, 1);
            len -= 2;
            xx++;
            yy++;
        }

    }
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.add(panel);
        
        panel.add(new JLabel("north", new ArrowIcon(ArrowIcon.NORTH), JLabel.CENTER));
        panel.add(new JLabel("west", new ArrowIcon(ArrowIcon.WEST), JLabel.CENTER));
        panel.add(new JLabel("south", new ArrowIcon(ArrowIcon.SOUTH), JLabel.CENTER));
        panel.add(new JLabel("east", new ArrowIcon(ArrowIcon.EAST), JLabel.CENTER));
        panel.add(new JLabel("east-20", new ArrowIcon(ArrowIcon.EAST, 20, Color.blue), JLabel.CENTER));
        
        frame.pack();
        frame.setVisible(true);
    }

}

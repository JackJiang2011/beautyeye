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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Simple panel subclass which renders a 2-color vertical linear gradient
 * as the background.
 * @author Aim
 */
public class GradientPanel extends JPanel {
    private final Color[] colors = new Color[2];
    private Image image; 
    
    public GradientPanel(Color color1, Color color2) {
        super();
        setOpaque(false);  // unfortunately required to disable automatic bg painting
        setBackground(color1); // in case colors are derived from background
        colors[0] = color1;
        colors[1] = color2;
    }
    
    public void setGradientColor1(Color color) {
        changeGradientColor(0, color);
    }
    
    public void setGradientColor2(Color color) {
        changeGradientColor(1, color);
    }
    
    protected void changeGradientColor(int colorIndex, Color newColor) {
        Color oldColor = colors[colorIndex];
        colors[colorIndex] = newColor;
        if (!oldColor.equals(newColor)) {
            image = null;
            firePropertyChange("gradientColor"+colorIndex, oldColor, newColor);
        }
    }
    
    protected Image getGradientImage() {
        Dimension size = getSize();
        if (image == null ||
                image.getWidth(null) != size.width ||
                image.getHeight(null) != size.height) {

            image = Utilities.createGradientImage(size.width, size.height, 
                    colors[0], colors[1]);
        
        }
        return image;
    }
   
    @Override
    protected void paintComponent(Graphics g) {
        Image gradientImage = getGradientImage();
        g.drawImage(gradientImage, 0, 0, null);
        super.paintComponent(g);
    }

}

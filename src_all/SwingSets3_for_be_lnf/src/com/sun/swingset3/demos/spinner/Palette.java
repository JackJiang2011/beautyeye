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
package com.sun.swingset3.demos.spinner;

/**
 * @author Mikhail Lapshin
 */
import java.awt.*;

public class Palette {
    private final int minColor;
    private final int colorRange;
    private Color[] colors;
    private int[] rgbColors;
    private final int rSteps;
    private final int gSteps;
    private final int bSteps;
    private int totalRange;
    private int rRange;
    private int gRange;
    private int bRange;
    private final double rStart;
    private final double gStart;
    private final double bStart;

    public Palette(int totalRange, int minColor, int maxColor, double rStart,
                         double gStart, double bStart, int rSteps, int gSteps, int bSteps) {
        this.minColor = minColor;
        this.colorRange = maxColor - minColor;
        this.rStart = rStart;
        this.gStart = gStart;
        this.bStart = bStart;
        this.rSteps = rSteps;
        this.gSteps = gSteps;
        this.bSteps = bSteps;
        setSize(totalRange);
    }

    public void setSize(int newSize) {
        totalRange = newSize;
        rRange = totalRange / rSteps;
        gRange = totalRange / gSteps;
        bRange = totalRange / bSteps;
        fillColorTable();
    }

    private void fillColorTable() {
        colors = new Color[totalRange];
        rgbColors = new int[totalRange];
        for (int i = 0; i < totalRange; i++) {
            double cosR = Math.cos(i * 2 * Math.PI / rRange + rStart);
            double cosG = Math.cos(i * 2 * Math.PI / gRange + gStart);
            double cosB = Math.cos(i * 2 * Math.PI / bRange + bStart);
            Color color = new Color(
                    (int) ((cosR * colorRange) + colorRange) / 2 + minColor,
                    (int) ((cosG * colorRange) + colorRange) / 2 + minColor,
                    (int) ((cosB * colorRange) + colorRange) / 2 + minColor);
            colors[i] = color;
            rgbColors[i] = color.getRGB();
        }
    }

    public Color getColor(int index) {
        return colors[index];
    }

    public int getRgbColor(int index) {
        return rgbColors[index];
    }

    public int getSize() {
        return totalRange;
    }
}

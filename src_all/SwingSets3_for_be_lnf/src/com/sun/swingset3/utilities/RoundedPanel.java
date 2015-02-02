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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Administrator
 */
public class RoundedPanel extends JXPanel {
    private final int cornerRadius;
    private boolean contentAreaFilled;
    
    private transient RoundRectangle2D.Float roundBounds;
    
    public RoundedPanel() {
        this(10);
    }
    
    public RoundedPanel(LayoutManager layout) {
        this(layout, 10);
    }
    
    public RoundedPanel(int cornerRadius) {
        this(new FlowLayout(), cornerRadius);
    }
    
    public RoundedPanel(LayoutManager layout, int cornerRadius) {
        super(layout);
        this.cornerRadius = cornerRadius;
        this.roundBounds = new RoundRectangle2D.Float(0,0,0,0,
                cornerRadius, cornerRadius);
        this.contentAreaFilled = true;
        setOpaque(false);
    }
    
    public void setContentAreaFilled(boolean contentFilled) {
        this.contentAreaFilled = contentFilled;
    }
    
    public boolean isContentAreaFilled() {
        return contentAreaFilled;
    }
       
    @Override
    protected void paintComponent(Graphics g) {
        if (isContentAreaFilled()) {
            Graphics2D g2 = (Graphics2D) g;
            Dimension size = getSize();
            roundBounds.width = size.width;
            roundBounds.height = size.height;
            g2.setColor(getBackground());
            g2.fill(roundBounds);
        }
        super.paintComponent(g);
    }
        
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        RoundedPanel p = new RoundedPanel(new BorderLayout(), 16);
        JPanel p2 = new JPanel();
        p2.setBackground(Color.blue);
        p.add(p2);
        frame.add(p);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}

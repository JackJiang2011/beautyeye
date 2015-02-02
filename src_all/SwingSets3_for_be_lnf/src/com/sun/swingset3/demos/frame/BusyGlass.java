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

package com.sun.swingset3.demos.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * GlassPane component which can be set on toplevel
 * containers to makes those containers "busy" be disabling input.
 *
 * Example usage:
 * <pre><code>
 *    // Install glasspane
 *    frame.setGlassPane(new BusyGlass());
 *
 *    // Make frame busy
 *    frame.getGlassPane().setVisible(true);
 * </code></pre>
 *
 * Caution: A well-written client should rarely need to make
 * a window "busy" because the app should be as responsive as possible;
 * long-winded operations should be off-loaded to non-GUI threads
 * whenever possible.
 *
 * @author aim
 */
//<snip>Make toplevel "busy"
public class BusyGlass extends JPanel {

    /**
     * Create GlassPane component to block input on toplevel
     */
    public BusyGlass() {
        setLayout(new BorderLayout());
        setVisible(false); //initially invisible
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    protected void paintComponent(Graphics g) {
        // Render partially opaque to 'veil' the frame's contents so
        // that the user has visual feedback that the components
        // arn't responsive.
        Color bgColor = getBackground();
        g.setColor(new Color(bgColor.getRed(),
                bgColor.getGreen(),
                bgColor.getBlue(), 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
//</snip>
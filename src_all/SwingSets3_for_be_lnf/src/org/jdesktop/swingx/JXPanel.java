/*
 * $Id: JXPanel.java,v 1.27 2007/11/25 15:52:56 kschaefe Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jdesktop.swingx;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Scrollable;
import org.jdesktop.swingx.RepaintManagerX;
import org.jdesktop.swingx.TranslucentRepaintManager;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

/**
 * A simple JPanel extension that adds translucency support.
 * This component and all of its content will be displayed with the specified
 * &quot;alpha&quot; transluscency property value. It also supports the
 * Painters using the backgroundPainter property.  For example, to change the background of the
 * panel to a checkeboard do something like this:
 *
 *
 * <PRE> <CODE>JXPanel panel = new JXPanel();
 * panel.setBackgroundPainter(new CheckerboardPainter());</CODE></PRE>
 * @author rbair
 * 
 * Note: This has been imported directly into the SwingSet3 source in order
 * to make a critical bugfix which was needed for SwingSet3.
 */
public class JXPanel extends JPanel implements Scrollable {
    private boolean scrollableTracksViewportHeight;
    private boolean scrollableTracksViewportWidth;

    /**
     * The alpha level for this component.
     */
    private float alpha = 1.0f;
    /**
     * If the old alpha value was 1.0, I keep track of the opaque setting because
     * a translucent component is not opaque, but I want to be able to restore
     * opacity to its default setting if the alpha is 1.0. Honestly, I don't know
     * if this is necessary or not, but it sounded good on paper :)
     * <p>TODO: Check whether this variable is necessary or not</p>
     */
    private boolean oldOpaque;

    /**
     * Creates a new instance of JXPanel
     */
    public JXPanel() {
    }

    /**
     * @param isDoubleBuffered
     */
    public JXPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * @param layout
     */
    public JXPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * @param layout
     * @param isDoubleBuffered
     */
    public JXPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    /**
     * Set the alpha transparency level for this component. This automatically
     * causes a repaint of the component.
     *
     * @param alpha must be a value between 0 and 1 inclusive.
     */
    public void setAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be between 0 and 1 inclusive");
        }

        if (this.alpha != alpha) {
            float oldAlpha = this.alpha;
            this.alpha = alpha;
            if (alpha > 0f && alpha < 1f) {
                if (oldAlpha == 1) {
                    //it used to be 1, but now is not. Save the oldOpaque
                    oldOpaque = isOpaque();
                    setOpaque(false);
                }

                //TODO this was quite the controversial choice, in automatically
                //replacing the repaint manager. In retrospect, I'd probably
                //opt for making this a manual choice. There really isn't a clear
                //win, no matter the approach.
                RepaintManager manager = RepaintManager.currentManager(this);
                if (!manager.getClass().isAnnotationPresent(TranslucentRepaintManager.class)) {
                    RepaintManager.setCurrentManager(new RepaintManagerX());
                }
            } else if (alpha == 1) {
                //restore the oldOpaque if it was true (since opaque is false now)
                if (oldOpaque) {
                    setOpaque(true);
                }
            }
            firePropertyChange("alpha", oldAlpha, alpha);
            repaint();
        }
    }

    /**
     * @return the alpha translucency level for this component. This will be
     * a value between 0 and 1, inclusive.
     */
    public float getAlpha() {
        return alpha;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
     */
    public boolean getScrollableTracksViewportHeight() {
        return scrollableTracksViewportHeight;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
     */
    public boolean getScrollableTracksViewportWidth() {
        return scrollableTracksViewportWidth;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
     */
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }
    /**
     * @param scrollableTracksViewportHeight The scrollableTracksViewportHeight to set.
     */
    public void setScrollableTracksViewportHeight(boolean scrollableTracksViewportHeight) {
        this.scrollableTracksViewportHeight = scrollableTracksViewportHeight;
    }
    /**
     * @param scrollableTracksViewportWidth The scrollableTracksViewportWidth to set.
     */
    public void setScrollableTracksViewportWidth(boolean scrollableTracksViewportWidth) {
        this.scrollableTracksViewportWidth = scrollableTracksViewportWidth;
    }

    /**
     * Overriden paint method to take into account the alpha setting
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        float a = getAlpha();
        if (a == 1) {
            super.paint(g);
        } else {
            //the component is translucent, so we need to render to
            //an intermediate image before painting
            BufferedImage img = GraphicsUtilities.createCompatibleTranslucentImage(getWidth(), getHeight());
            Graphics2D gfx = img.createGraphics();
            super.paint(gfx);
            gfx.dispose();
            Graphics2D g2d = (Graphics2D)g;
            Composite oldComp = g2d.getComposite();
            Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a);
            g2d.setComposite(alphaComp);
            g2d.drawImage(img, null, 0, 0);
            g2d.setComposite(oldComp);
        }
    }
}


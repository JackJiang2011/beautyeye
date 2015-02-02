/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * BezierAnimationPanel.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)BezierAnimationPanel.java	1.16 05/11/17
 */


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * BezierAnimationPanel.
 *
 * @version 1.16 11/17/05
 * @author Jim Graham
 * @author Jeff Dinkins (removed dynamic setting changes, made swing friendly)
 */
class BezierAnimationPanel extends JPanel implements Runnable {
    
    /** The background color. */
    Color backgroundColor =  new Color(0,     0, 153);
    
    /** The outer color. */
    Color outerColor      =  new Color(255, 255, 255);
    
    /** The gradient color a. */
    Color gradientColorA  =  new Color(255,   0, 101);
    
    /** The gradient color b. */
    Color gradientColorB  =  new Color(255, 255,   0);

    /** The bg changed. */
    boolean bgChanged = false;

    /** The gradient. */
    GradientPaint gradient = null;
    
    /** The NUMPTS. */
    public final int NUMPTS = 6;

    /** The animpts. */
    float animpts[] = new float[NUMPTS * 2];

    /** The deltas. */
    float deltas[] = new float[NUMPTS * 2];

    /** The staticpts. */
    float staticpts[] = {
	 50.0f,   0.0f,
	150.0f,   0.0f,
	200.0f,  75.0f,
	150.0f, 150.0f,
	 50.0f, 150.0f,
	  0.0f,  75.0f,
    };

    /** The movepts. */
    float movepts[] = new float[staticpts.length];

    /** The img. */
    BufferedImage img;

    /** The bounds. */
    Rectangle bounds = null;

    /** The anim. */
    Thread anim;
    
    /** The lock. */
    private final Object lock = new Object();

    /**
     * BezierAnimationPanel Constructor.
     */
    public BezierAnimationPanel() {
        addHierarchyListener(
	    new HierarchyListener() {
	       public void hierarchyChanged(HierarchyEvent e) {
		   if(isShowing()) {
		       start();
		   } else {
		       stop();
		   }
	       }
	   }
	);
	setBackground(getBackgroundColor());
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#isOpaque()
     */
    public boolean isOpaque() {
        return true;
    }

    /**
     * Gets the gradient color a.
     *
     * @return the gradient color a
     */
    public Color getGradientColorA() {
	return gradientColorA;
    }

    /**
     * Sets the gradient color a.
     *
     * @param c the new gradient color a
     */
    public void setGradientColorA(Color c) {
	if(c != null) {
	    gradientColorA = c;
	}
    }

    /**
     * Gets the gradient color b.
     *
     * @return the gradient color b
     */
    public Color getGradientColorB() {
	return gradientColorB;
    }

    /**
     * Sets the gradient color b.
     *
     * @param c the new gradient color b
     */
    public void setGradientColorB(Color c) {
	if(c != null) {
	    gradientColorB = c;
	}
    }

    /**
     * Gets the outer color.
     *
     * @return the outer color
     */
    public Color getOuterColor() {
	return outerColor;
    }

    /**
     * Sets the outer color.
     *
     * @param c the new outer color
     */
    public void setOuterColor(Color c) {
	if(c != null) {
	    outerColor = c;
	}
    }

    /**
     * Gets the background color.
     *
     * @return the background color
     */
    public Color getBackgroundColor() {
	return backgroundColor;
    }

    /**
     * Sets the background color.
     *
     * @param c the new background color
     */
    public void setBackgroundColor(Color c) {
	if(c != null) {
	    backgroundColor = c;
	    setBackground(c);
	    bgChanged = true;
	}
    }

    /**
     * Start.
     */
    public void start() {
	Dimension size = getSize();
	for (int i = 0; i < animpts.length; i += 2) {
	    animpts[i + 0] = (float) (Math.random() * size.width);
	    animpts[i + 1] = (float) (Math.random() * size.height);
	    deltas[i + 0] = (float) (Math.random() * 4.0 + 2.0);
	    deltas[i + 1] = (float) (Math.random() * 4.0 + 2.0);
	    if (animpts[i + 0] > size.width / 6.0f) {
		deltas[i + 0] = -deltas[i + 0];
	    }
	    if (animpts[i + 1] > size.height / 6.0f) {
		deltas[i + 1] = -deltas[i + 1];
	    }
	}
	anim = new Thread(this);
	anim.setPriority(Thread.MIN_PRIORITY);
	anim.start();
    }

    /**
     * Stop.
     */
    public synchronized void stop() {
	anim = null;
	notify();
    }

    /**
     * Animate.
     *
     * @param pts the pts
     * @param deltas the deltas
     * @param index the index
     * @param limit the limit
     */
    public void animate(float[] pts, float[] deltas, int index, int limit) {
	float newpt = pts[index] + deltas[index];
	if (newpt <= 0) {
	    newpt = -newpt;
	    deltas[index] = (float) (Math.random() * 3.0 + 2.0);
	} else if (newpt >= (float) limit) {
	    newpt = 2.0f * limit - newpt;
	    deltas[index] = - (float) (Math.random() * 3.0 + 2.0);
	}
	pts[index] = newpt;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
	Thread me = Thread.currentThread();
	while (getSize().width <= 0) {
	    try {
		anim.sleep(500);
	    } catch (InterruptedException e) {
		return;
	    }
        }
         
	Graphics2D g2d = null;
	Graphics2D BufferG2D = null;
	Graphics2D ScreenG2D = null;
	BasicStroke solid = new BasicStroke(9.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 9.0f);
	GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO);
	int rule = AlphaComposite.SRC_OVER;
	AlphaComposite opaque = AlphaComposite.SrcOver;
	AlphaComposite blend = AlphaComposite.getInstance(rule, 0.9f);
	AlphaComposite set = AlphaComposite.Src;
	int frame = 0;
	int frametmp = 0;
	Dimension oldSize = getSize();
	Shape clippath = null;
	while (anim == me) {
	    Dimension size = getSize();
	    if (size.width != oldSize.width || size.height != oldSize.height) {
		img = null;
		clippath = null;
		if (BufferG2D != null) {
		    BufferG2D.dispose();
		    BufferG2D = null;
		}
		if (ScreenG2D != null) {
		    ScreenG2D.dispose();
		    ScreenG2D = null;
		}
	    }
	    oldSize = size;

	    if (img == null) {
		img = (BufferedImage) createImage(size.width, size.height);
	    }

        if (BufferG2D == null) {
		BufferG2D = img.createGraphics();
		BufferG2D.setRenderingHint(RenderingHints.KEY_RENDERING,
					   RenderingHints.VALUE_RENDER_DEFAULT);
		BufferG2D.setClip(clippath);
	    }
	    g2d = BufferG2D;

	    float[] ctrlpts;
	    for (int i = 0; i < animpts.length; i += 2) {
		animate(animpts, deltas, i + 0, size.width);
		animate(animpts, deltas, i + 1, size.height);
	    }
	    ctrlpts = animpts;
	    int len = ctrlpts.length;
	    gp.reset();
	    int dir = 0;
	    float prevx = ctrlpts[len - 2];
	    float prevy = ctrlpts[len - 1];
	    float curx = ctrlpts[0];
	    float cury = ctrlpts[1];
	    float midx = (curx + prevx) / 2.0f;
	    float midy = (cury + prevy) / 2.0f;
	    gp.moveTo(midx, midy);
	    for (int i = 2; i <= ctrlpts.length; i += 2) {
		float x1 = (midx + curx) / 2.0f;
		float y1 = (midy + cury) / 2.0f;
		prevx = curx;
		prevy = cury;
		if (i < ctrlpts.length) {
		    curx = ctrlpts[i + 0];
		    cury = ctrlpts[i + 1];
		} else {
		    curx = ctrlpts[0];
		    cury = ctrlpts[1];
		}
		midx = (curx + prevx) / 2.0f;
		midy = (cury + prevy) / 2.0f;
		float x2 = (prevx + midx) / 2.0f;
		float y2 = (prevy + midy) / 2.0f;
		gp.curveTo(x1, y1, x2, y2, midx, midy);
	    }
	    gp.closePath();

	    synchronized(lock) {
        g2d.setComposite(set);
	    g2d.setBackground(backgroundColor);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_OFF);

	    if(bgChanged || bounds == null) {
		bounds = new Rectangle(0, 0, getWidth(), getHeight());
		bgChanged = false;
	    }
	    
        // g2d.clearRect(bounds.x-5, bounds.y-5, bounds.x + bounds.width + 5, bounds.y + bounds.height + 5);
	    g2d.clearRect(0, 0, getWidth(), getHeight());

	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setColor(outerColor);
	    g2d.setComposite(opaque);
	    g2d.setStroke(solid);
	    g2d.draw(gp);
	    g2d.setPaint(gradient);

	    if(!bgChanged) {
		bounds = gp.getBounds();
	    } else {
		bounds = new Rectangle(0, 0, getWidth(), getHeight());
		bgChanged = false;
	    }
	    gradient = new GradientPaint(bounds.x, bounds.y, gradientColorA,
					 bounds.x + bounds.width, bounds.y + bounds.height,
					 gradientColorB, true);
	    g2d.setComposite(blend);
	    g2d.fill(gp);
        }
	    if (g2d == BufferG2D) {
		repaint();
	    }
	    ++frame;
	    Thread.yield();
	}
	if (g2d != null) {	
	    g2d.dispose();
	}
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
	synchronized (lock) {
	   Graphics2D g2d = (Graphics2D) g;
	   if (img != null) {
	       int imgw = img.getWidth();
	       int imgh = img.getHeight();
	       g2d.setComposite(AlphaComposite.Src);
	       g2d.drawImage(img, null, 0, 0);
	   }
        }
    }
}

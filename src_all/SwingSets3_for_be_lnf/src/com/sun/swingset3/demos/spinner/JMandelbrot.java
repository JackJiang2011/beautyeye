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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

import com.sun.swingset3.demos.ResourceManager;

/**
 * @author Mikhail Lapshin
 */
public class JMandelbrot extends JComponent {
    private static final double EPSILON = 1E-16;
    private static final int MIN_WIDTH = 50;
    private static final int MIN_HEIGHT = 50;
    private static final double ZOOM_RATE = 3;
    private static final int NUM_OF_THREADS = 4;

    private Point2D center;
    public static final String CENTER_PROPERTY_NAME = "center";

    private int maxIteration = 300;
    public static final String MAX_ITERATION_PROPERTY_NAME = "maxIteration";

    private Palette palette;
    public static final String PALETTE_PROPERTY_NAME = "palette";

    private BufferedImage buffer;
    private final MandelbrotCalculator[] calculators =
            new MandelbrotCalculator[NUM_OF_THREADS];

    private double xLowLimit = -2;
    private double xHighLimit = 2;
    private double yLowLimit = -2;
    private double yHighLimit = 2;
    private double xScale = 100;
    private double yScale = 100;
    private int oldComponentWidth = (int) (xScale * (xHighLimit - xLowLimit));
    private int oldComponentHeight = (int) (yScale * (yHighLimit - yLowLimit));

    public JMandelbrot(int width, int height, Palette palette,
                       ResourceManager resourceManager) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        calcConstants(width, height);
        setPalette(palette);
        setToolTipText(resourceManager.getString("SpinnerDemo.toolTip"));
        installListeners();
    }

    private void calcConstants() {
        calcConstants(getWidth(), getHeight());
    }

    private void calcConstants(int width, int height) {
        if ((width >= MIN_WIDTH) && (height >= MIN_HEIGHT)) {
            double oldIntervalWidth = xHighLimit - xLowLimit;
            double oldIntervalHeight = yHighLimit - yLowLimit;
            double newIntervalWidth =
                    width * oldIntervalWidth / oldComponentWidth;
            double newIntervalHeight =
                    height * oldIntervalHeight / oldComponentHeight;
            double xDiff = newIntervalWidth - oldIntervalWidth;
            double yDiff = newIntervalHeight - oldIntervalHeight;
            xLowLimit -= xDiff / 2;
            xHighLimit += xDiff / 2;
            yLowLimit -= yDiff / 2;
            yHighLimit += yDiff / 2;
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            oldComponentWidth = width;
            oldComponentHeight = height;
            setCenter(calcCenter());
        }
    }

    private void installListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int xCoord = e.getX();
                int yCoord = e.getY();
                double intervalWidth = xHighLimit - xLowLimit;
                double intervalHeight = yHighLimit - yLowLimit;
                double x = intervalWidth * xCoord / getWidth() + xLowLimit;
                double y = intervalHeight * yCoord / getHeight() + yLowLimit;

                double newIntervalWidth;
                double newIntervalHeight;
                if (e.getButton() == MouseEvent.BUTTON1) {
                    boolean limitReached = false;
                    newIntervalWidth = intervalWidth / ZOOM_RATE;
                    if ((newIntervalWidth / getWidth()) < EPSILON) {
                        newIntervalWidth = intervalWidth;
                        limitReached = true;
                    }
                    newIntervalHeight = intervalHeight / ZOOM_RATE;
                    if ((newIntervalHeight / getHeight()) < EPSILON) {
                        newIntervalHeight = intervalHeight;
                        limitReached = true;
                    }
                    if (!limitReached) {
                        xLowLimit = x - (x - xLowLimit) / ZOOM_RATE;
                        yLowLimit = y - (y - yLowLimit) / ZOOM_RATE;
                    }
                } else {
                    newIntervalWidth = intervalWidth * ZOOM_RATE;
                    newIntervalHeight = intervalHeight * ZOOM_RATE;
                    xLowLimit = x - (x - xLowLimit) * ZOOM_RATE;
                    yLowLimit = y - (y - yLowLimit) * ZOOM_RATE;
                }

                xHighLimit = xLowLimit + newIntervalWidth;
                yHighLimit = yLowLimit + newIntervalHeight;

                setCenter(calcCenter());

                xScale = getWidth() / newIntervalWidth;
                yScale = getHeight() / newIntervalHeight;

                calculatePicture();
            }
        });

        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                calcConstants();
                calculatePicture();
                repaint();
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }

            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    //<snip>Use SwingWorker to asynchronously calculate parts of the picture     
    public void calculatePicture() {
        int yStep = getHeight() / NUM_OF_THREADS;
        int yStart = 0;
        for (int i = 0; i < calculators.length; i++) {
            if ((calculators[i] != null) && !calculators[i].isDone()) {
                calculators[i].cancel(true);
            }
            int yEnd = i == calculators.length - 1 ? getHeight() : yStart + yStep;
            calculators[i] = new MandelbrotCalculator(yStart, yEnd);
            calculators[i].execute();
            yStart = yEnd;
        }
    }
    //</snip>

    private Point2D calcCenter() {
        return new Point2D.Double(xLowLimit + (xHighLimit - xLowLimit) / 2,
                yLowLimit + (yHighLimit - yLowLimit) / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    //<snip>Use SwingWorker to asynchronously calculate parts of the picture
    private class MandelbrotCalculator extends SwingWorker<Object, Object> {
        private final int yStart;
        private final int yEnd;

        public MandelbrotCalculator(int yStart, int yEnd) {
            this.yStart = yStart;
            this.yEnd = yEnd;
        }

        protected Object doInBackground() throws Exception {
            int[] data = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();

            double xArr[] = new double[buffer.getWidth()];

            for (int i = 0; i < xArr.length; i++) {
                xArr[i] = i / xScale + xLowLimit;
            }

            double yArr[] = new double[yEnd - yStart];

            for (int i = 0; i < yArr.length; i++) {
                yArr[i] = (yStart + i) / yScale + yLowLimit;
            }

            int i = yStart * buffer.getWidth();

            for (double y : yArr) {
                for (double x : xArr) {
                    int value = calcValue(x, y);

                    data[i] = value == maxIteration ? 0 : palette.getRgbColor(value);

                    i++;
                }
                if (Thread.currentThread().isInterrupted()) {
                    return null;
                }
                publish();
            }
            return null;
        }

        private int calcValue(double x, double y) {
            double x0 = x;
            double y0 = y;

            for (int i = 0; i < maxIteration; i++) {
                double x2 = x * x;
                double y2 = y * y;

                if (x2 + y2 > 4) {
                    return i;
                }

                y = 2 * x * y + y0;
                x = x2 - y2 + x0;
            }

            return maxIteration;
        }

        @Override
        protected void process(List<Object> chunks) {
            repaint();
        }
    }
    //</snip>     

    // Getters and Setters

    public int getMaxIteration() {
        return maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
        int oldValue = this.maxIteration;
        this.maxIteration = maxIteration;
        firePropertyChange(MAX_ITERATION_PROPERTY_NAME, oldValue, maxIteration);
        palette.setSize(maxIteration);
    }

    public double getXHighLimit() {
        return xHighLimit;
    }

    public double getXLowLimit() {
        return xLowLimit;
    }

    public double getYLowLimit() {
        return yLowLimit;
    }

    public double getYHighLimit() {
        return yHighLimit;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D coords) {
        Point2D oldValue = this.center;
        this.center = coords;

        double width = xHighLimit - xLowLimit;
        double height = yHighLimit - yLowLimit;

        xLowLimit = coords.getX() - width / 2;
        xHighLimit = xLowLimit + width;
        yLowLimit = coords.getY() - height / 2;
        yHighLimit = yLowLimit + height;

        firePropertyChange(CENTER_PROPERTY_NAME, oldValue, coords);
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        Palette oldValue = this.palette;
        palette.setSize(maxIteration);
        this.palette = palette;
        firePropertyChange(PALETTE_PROPERTY_NAME, oldValue, palette);
    }
}

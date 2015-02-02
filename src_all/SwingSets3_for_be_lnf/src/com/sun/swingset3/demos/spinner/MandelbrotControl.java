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
import java.awt.geom.Point2D;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import com.sun.swingset3.demos.ResourceManager;

/**
 * @author Mikhail Lapshin
 */
public class MandelbrotControl extends JPanel {
    private final JMandelbrot mandelbrot;
    private JSpinner iterSpinner;
    private CoordSpinner xSpinner;
    private CoordSpinner ySpinner;
    private static final double COORD_SPINNER_STEP = 0.1d; // part of width or height
    private final ResourceManager resourceManager;

    public MandelbrotControl(JMandelbrot mandelbrot,
                             ResourceManager resourceManager) {
        this.mandelbrot = mandelbrot;
        this.resourceManager = resourceManager;
        createUI();
        installListeners();
    }

    private void createUI() {
        setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));
        setBorder(BorderFactory.createTitledBorder(
                resourceManager.getString("SpinnerDemo.fractalControls")));
        JSpinnerPanel spinnerPanel = new JSpinnerPanel();

        //<snip>Create spinner
        iterSpinner = new JSpinner(new SpinnerNumberModel(
                mandelbrot.getMaxIteration(), 10, 100000, 50));
        //</snip>
        //<snip>Add change listener using anonymus inner class 
        iterSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mandelbrot.setMaxIteration((Integer) iterSpinner.getValue());
                mandelbrot.calculatePicture();
            }
        });
        //</snip>
        spinnerPanel.addSpinner(
                resourceManager.getString("SpinnerDemo.iterations"), iterSpinner);

        //<snip>Create spinner
        final double xValue = mandelbrot.getCenter().getX();
        double width = mandelbrot.getXHighLimit() - mandelbrot.getXLowLimit();
        xSpinner = new CoordSpinner(xValue, width * COORD_SPINNER_STEP);
        //</snip>
        //<snip>Add change listener using anonymus inner class
        xSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Double newX = (Double) xSpinner.getValue();
                mandelbrot.setCenter(new Point2D.Double(
                        newX, mandelbrot.getCenter().getY()));
                mandelbrot.calculatePicture();
            }
        });
        //</snip>
        spinnerPanel.addSpinner(
                resourceManager.getString("SpinnerDemo.x"), xSpinner);

        //<snip>Create spinner
        final double yValue = mandelbrot.getCenter().getY();
        double height = mandelbrot.getYHighLimit() - mandelbrot.getYLowLimit();
        ySpinner = new CoordSpinner(yValue, height * COORD_SPINNER_STEP);
        //</snip>
        //<snip>Add change listener using anonymus inner class
        ySpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Double newY = (Double) ySpinner.getValue();
                mandelbrot.setCenter(new Point2D.Double(
                        mandelbrot.getCenter().getX(), newY));
                mandelbrot.calculatePicture();
            }
        });
        //</snip>
        spinnerPanel.addSpinner(
                resourceManager.getString("SpinnerDemo.y"), ySpinner);

        add(spinnerPanel);
    }

    private void installListeners() {
        mandelbrot.addPropertyChangeListener(
                JMandelbrot.CENTER_PROPERTY_NAME,
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        double width = mandelbrot.getXHighLimit()
                                - mandelbrot.getXLowLimit();
                        double newX = mandelbrot.getCenter().getX();
                        xSpinner.updateModel(newX, width * COORD_SPINNER_STEP);
                        double height = mandelbrot.getYHighLimit()
                                - mandelbrot.getYLowLimit();
                        double newY = mandelbrot.getCenter().getY();
                        ySpinner.updateModel(newY, height * COORD_SPINNER_STEP);
                    }
                }
        );
    }

    //<snip>Customized spinner class
    // It uses special format for NumberEditor and has constant preferred width
    private static class CoordSpinner extends JSpinner {
        @Override
        protected JComponent createEditor(SpinnerModel model) {
            return new NumberEditor(this, "#.####################");
        }

        public CoordSpinner(double value, double stepSize) {
            super(new SpinnerNumberModel(value, -100, 100, stepSize));
        }

        //A useful shortcut method
        public void updateModel(double value, double stepSize) {
            SpinnerNumberModel model = (SpinnerNumberModel) getModel();
            model.setValue(value);
            model.setStepSize(stepSize);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension prefSize = super.getPreferredSize();
            prefSize.setSize(180, prefSize.getHeight());
            return prefSize;
        }
    }
    //</snip>
}



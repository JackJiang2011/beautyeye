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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.ResourceManager;

/**
 * Demonstrates JSpinner and SwingWorker
 *
 * @author Mikhail Lapshin
 */
@DemoProperties(
        value = "Spinner Demo",
        category = "Controls",
        description = "Demonstrates JSpinner and SwingWorker",
        sourceFiles = {
                "com/sun/swingset3/demos/spinner/SpinnerDemo.java",
                "com/sun/swingset3/demos/spinner/JMandelbrot.java",
                "com/sun/swingset3/demos/spinner/JPaletteShower.java",
                "com/sun/swingset3/demos/spinner/JSpinnerPanel.java",
                "com/sun/swingset3/demos/spinner/MandelbrotControl.java",
                "com/sun/swingset3/demos/spinner/Palette.java",
                "com/sun/swingset3/demos/spinner/PaletteChooser.java",
                "com/sun/swingset3/demos/ResourceManager.java",
                "com/sun/swingset3/demos/spinner/resources/SpinnerDemo.properties",
                "com/sun/swingset3/demos/spinner/resources/images/SpinnerDemo.gif"
                }
)
public class SpinnerDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(getClass());

    public static void main(String[] args) {
        JFrame frame = new JFrame(
                SpinnerDemo.class.getAnnotation(DemoProperties.class).value());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SpinnerDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public SpinnerDemo() {
        setLayout(new BorderLayout());

        // Create main components
        PaletteChooser chooser =
                new PaletteChooser(resourceManager);
        final JMandelbrot mandelbrot =
                new JMandelbrot(400, 400, chooser.getPalette(), resourceManager);
        MandelbrotControl mandelbrotControl =
                new MandelbrotControl(mandelbrot, resourceManager);

        // Connect palette chooser and mandelbrot component
        chooser.addPropertyChangeListener(
                PaletteChooser.PALETTE_PROPERTY_NAME,
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        mandelbrot.setPalette((Palette) evt.getNewValue());
                        mandelbrot.calculatePicture();
                    }
                }
        );

        // Layout components
        add(mandelbrot);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(chooser, BorderLayout.NORTH);

        JPanel mandelbrotControlPanel = new JPanel();
        mandelbrotControlPanel.setLayout(new BorderLayout());
        mandelbrotControlPanel.add(mandelbrotControl, BorderLayout.NORTH);
        controlPanel.add(mandelbrotControlPanel, BorderLayout.CENTER);

        add(controlPanel, BorderLayout.EAST);
    }
}

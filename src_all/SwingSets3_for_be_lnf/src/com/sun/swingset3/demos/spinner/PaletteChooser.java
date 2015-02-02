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
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import com.sun.swingset3.demos.ResourceManager;

/**
 * @author Mikhail Lapshin
 */
public class PaletteChooser extends JPanel {
    private static final int MIN_COLOR = 50;
    private static final int MAX_COLOR = 255;
    private static final int R_STEPS = 5;
    private static final int G_STEPS = 5;
    private static final int B_STEPS = 5;
    private static final int R_ANGLE = 270;
    private static final int G_ANGLE = 90;
    private static final int B_ANGLE = 0;

    public static final String PALETTE_PROPERTY_NAME = "palette";

    private final ResourceManager resourceManager;
    private Palette palette;
    private final JPaletteShower shower;
    private final ChangeListener changeListener;

    private JSpinner rsSpinner;
    private JSpinner gsSpinner;
    private JSpinner bsSpinner;
    private JSpinner raSpinner;
    private JSpinner gaSpinner;
    private JSpinner baSpinner;

    public PaletteChooser(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        palette = new Palette(MAX_COLOR - MIN_COLOR, MIN_COLOR, MAX_COLOR,
                Math.toRadians(R_ANGLE), Math.toRadians(G_ANGLE),
                Math.toRadians(B_ANGLE), R_STEPS, G_STEPS, B_STEPS);
        shower = new JPaletteShower(palette, 250, 25);

        //<snip>Use single change listener for several spinners
        changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setPalette(createPalette());
                shower.setPalette(palette);
                repaint();
            }
        };
        //</snip>

        setBorder(BorderFactory.createTitledBorder(
                resourceManager.getString("SpinnerDemo.colorPalette")));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(shower);
        add(createControlPanel());
    }

    private double toRadians(JSpinner spinner) {
        return Math.toRadians(getIntValue(spinner));
    }

    private Palette createPalette() {
        return new Palette(getWidth(), MIN_COLOR, MAX_COLOR,
                toRadians(raSpinner), toRadians(gaSpinner),
                toRadians(baSpinner), getIntValue(rsSpinner),
                getIntValue(gsSpinner), getIntValue(bsSpinner));
    }

    private static int getIntValue(JSpinner spinner) {
        return (Integer) spinner.getValue();
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));
        controlPanel.add(createStepPanel());
        controlPanel.add(createStartAnglePanel());
        return controlPanel;
    }

    private JPanel createStartAnglePanel() {
        JSpinnerPanel startAnglePanel = new JSpinnerPanel();
        startAnglePanel.setBorder(BorderFactory.createTitledBorder(
                resourceManager.getString("SpinnerDemo.startAngles")));

        raSpinner = createAngleSpinner(R_ANGLE, "SpinnerDemo.r", startAnglePanel);
        gaSpinner = createAngleSpinner(G_ANGLE, "SpinnerDemo.g", startAnglePanel);
        baSpinner = createAngleSpinner(B_ANGLE, "SpinnerDemo.b", startAnglePanel);

        return startAnglePanel;
    }

    private JPanel createStepPanel() {
        JSpinnerPanel stepPanel = new JSpinnerPanel();
        stepPanel.setBorder(BorderFactory.createTitledBorder(
                resourceManager.getString("SpinnerDemo.steps")));

        rsSpinner = createStepSpinner(R_STEPS, "SpinnerDemo.r", stepPanel);
        gsSpinner = createStepSpinner(G_STEPS, "SpinnerDemo.g", stepPanel);
        bsSpinner = createStepSpinner(B_STEPS, "SpinnerDemo.b", stepPanel);

        return stepPanel;
    }

    private JSpinner createAngleSpinner(int startAngle, String resourceName,
                                        JSpinnerPanel parent) {
        SpinnerModel model = new SpinnerNumberModel(startAngle, 0, 360, 10);
        return createSpinner(model, resourceName, parent);
    }

    private JSpinner createStepSpinner(int startSteps, String resourceName,
                                       JSpinnerPanel parent) {
        SpinnerModel model = new SpinnerNumberModel(startSteps, 1, 1000, 1);
        return createSpinner(model, resourceName, parent);
    }

    private JSpinner createSpinner(SpinnerModel model, String resourceName,
                                   JSpinnerPanel parent) {

        //<snip>Create spinner
        JSpinner spinner = new JSpinner(model);
        //</snip>
        //<snip>Use single change listener for several spinners
        spinner.addChangeListener(changeListener);
        //</snip>
        parent.addSpinner(resourceManager.getString(resourceName), spinner);
        return spinner;
    }

    public Palette getPalette() {
        return palette;
    }

    private void setPalette(Palette palette) {
        Palette oldValue = this.palette;
        this.palette = palette;
        firePropertyChange(PALETTE_PROPERTY_NAME, oldValue, palette);
    }
}

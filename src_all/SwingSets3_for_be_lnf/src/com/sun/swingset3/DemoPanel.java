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

package com.sun.swingset3;

import com.sun.swingset3.utilities.HTMLPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.animation.timing.triggers.TimingTrigger;
import org.jdesktop.animation.timing.triggers.TimingTriggerEvent;
import org.jdesktop.swingx.JXPanel;
import com.sun.swingset3.utilities.RoundedBorder;
import com.sun.swingset3.utilities.RoundedPanel;
import com.sun.swingset3.utilities.RoundedTitleBorder;
import com.sun.swingset3.utilities.Utilities;

/**
 *
 * @author aim
 */
public class DemoPanel extends JXPanel {
    private static final Border roundedBorder = new RoundedBorder(10);
    private static final Insets margin = new Insets(8, 10, 8, 8);

    private Demo demo;

    public DemoPanel(Demo demo) {
        this.demo = demo;
        setLayout(new BorderLayout());
        // remind(aim): how to access resourceMap?
        //resourceMap = getContext().getResourceMap();

        LoadAnimationPanel loadAnimationPanel = new LoadAnimationPanel();

        add(loadAnimationPanel);
        loadAnimationPanel.setAnimating(true);

        LoadedDemoPanel demoPanel = new LoadedDemoPanel(demo);

        try {
            loadAnimationPanel.setAnimating(false);
            Animator fadeOutAnimator = new Animator(400,
                    new FadeOut(DemoPanel.this,
                            loadAnimationPanel, demoPanel));
            fadeOutAnimator.setAcceleration(.2f);
            fadeOutAnimator.setDeceleration(.3f);
            Animator fadeInAnimator = new Animator(400,
                    new PropertySetter(DemoPanel.this, "alpha", 0.3f, 1.0f));
            TimingTrigger.addTrigger(fadeOutAnimator, fadeInAnimator, TimingTriggerEvent.STOP);
            fadeOutAnimator.start();
        } catch (Exception ignore) {
            System.err.println(ignore);
            ignore.printStackTrace();
        }
    }

    public Demo getDemo() {
        return demo;
    }

    private static class FadeOut extends PropertySetter {
        private JXPanel parent;
        private JXPanel out;
        private JComponent in;

        public FadeOut(JXPanel parent, JXPanel out, JComponent in) {
            super(out, "alpha", 1.0f, 0.3f);
            this.parent = parent;
            this.out = out;
            this.in = in;
        }

        public void end() {
            parent.setAlpha(0.3f);
            parent.remove(out);
            parent.add(in);
            parent.revalidate();
        }
    } // Fader

    private static class LoadAnimationPanel extends RoundedPanel {
        private String message;
        private int triState = 0;
        private boolean animating = false;
        private Animator animator;

        public LoadAnimationPanel() {
            super(10);
            setBorder(roundedBorder);
            setBackground(Utilities.deriveColorHSB(
                    UIManager.getColor("Panel.background"), 0, 0, -.06f));

            // remind(aim): get from resource map
            message = "demo loading";

            PropertySetter rotator = new PropertySetter(this, "triState", 0, 3);
            animator = new Animator(500, Animator.INFINITE,
                    Animator.RepeatBehavior.LOOP, rotator);
            // Don't animate gears if loading is quick
            animator.setStartDelay(200);
        }

        public void setAnimating(boolean animating) {
            this.animating = animating;
            if (animating) {
                animator.start();
            } else {
                animator.stop();
            }
        }

        public boolean isAnimating() {
            return animating;
        }

        public void setTriState(int triState) {
            this.triState = triState;
            repaint();
        }

        public int getTriState() {
            return triState;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            Dimension size = getSize();

            Color textColor = Utilities.deriveColorHSB(getBackground(), 0, 0, -.3f);
            Color dotColor = Utilities.deriveColorHSB(textColor, 0, .2f, -.08f);
            g2.setColor(textColor);
            g2.setFont(UIManager.getFont("Label.font").deriveFont(32f));
            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D rect = metrics.getStringBounds(message, g2);
            Rectangle2D dotRect = metrics.getStringBounds(".", g2);
            float x = (float) (size.width - (rect.getWidth() + 3 * dotRect.getWidth())) / 2;
            float y = (float) (size.height - rect.getHeight()) / 2;
            g2.drawString(message, x, y);
            int tri = getTriState();
            float dx = 0;
            for (int i = 0; i < 3; i++) {
                g2.setColor(animator.isRunning() && i == tri ?
                        dotColor :
                        textColor);
                g2.drawString(".", x + (float) (rect.getWidth() + dx), y);
                dx += dotRect.getWidth();
            }
        }
    } // LoadAnimationPanel

    private static class LoadedDemoPanel extends RoundedPanel {
        private String demoName;
        private JComponent descriptionArea;
        private JComponent demoPanel;

        public LoadedDemoPanel(Demo demo) {
            super(10);
            setLayout(null);
            demoName = demo.getName();

            URL description = demo.getHTMLDescription();
            if (description != null) {
                descriptionArea = createDescriptionArea(description);
                add(descriptionArea);

                demoPanel = new RoundedPanel(new BorderLayout());
                demoPanel.setBorder(roundedBorder);

            } else {
                // no description             
                demoPanel = new JXPanel(new BorderLayout());
            }
            demoPanel.add(demo.createDemoComponent());
            add(demoPanel);

            applyDefaults();
        }

        private static JComponent createDescriptionArea(URL descriptionURL) {
            JEditorPane descriptionPane = new HTMLPanel();
            descriptionPane.setEditable(false);
            descriptionPane.setMargin(margin);
            descriptionPane.setOpaque(true);
            try {
                descriptionPane.setPage(descriptionURL);
            } catch (IOException e) {
                System.err.println("couldn't load description from URL:" + descriptionURL);
            }
            return descriptionPane;
        }

        @Override
        public void doLayout() {
            if (demoPanel != null) {
                Dimension size = getSize();
                Insets insets = getInsets();

                if (descriptionArea == null) {
                    // Make demo fill entire area within border
                    demoPanel.setBounds(insets.left, insets.top,
                            size.width - insets.left - insets.right,
                            size.height - insets.top - insets.bottom);
                } else {
                    // Split space between HTML description and running demo
                    Dimension demoSize = demoPanel.getPreferredSize();
                    int margin = insets.top / 2;
                    Rectangle bounds = new Rectangle();
                    bounds.width = Math.max(demoSize.width, (int) (size.width * .50));
                    bounds.height = Math.max(demoSize.height, size.height -
                            2 * margin);
                    bounds.x = size.width - bounds.width - margin;
                    bounds.y = margin;
                    demoPanel.setBounds(bounds);
                    descriptionArea.setBounds(insets.left, insets.top,
                            size.width - margin - insets.right - bounds.width,
                            size.height - insets.top - insets.bottom);
                }
            }
        }

        @Override
        public void updateUI() {
            super.updateUI();
            applyDefaults();
        }

        private void applyDefaults() {
            setBorder(new RoundedTitleBorder(demoName,
                    UIManager.getColor(SwingSet3.TITLE_GRADIENT_COLOR1_KEY),
                    UIManager.getColor(SwingSet3.TITLE_GRADIENT_COLOR2_KEY)));

            setFont(UIManager.getFont(SwingSet3.TITLE_FONT_KEY));
            Color bg = Utilities.deriveColorHSB(
                    UIManager.getColor("Panel.background"), 0, 0, -.06f);
            setBackground(bg);
            setForeground(UIManager.getColor(SwingSet3.TITLE_FOREGROUND_KEY));
            if (demoPanel != null) {
                demoPanel.setBackground(Utilities.deriveColorHSB(bg, 0, 0, .04f));
            }
            if (descriptionArea != null) {
                descriptionArea.setBackground(bg);
            }
        }
    }
}

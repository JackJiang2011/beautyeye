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

package com.sun.swingset3.demos;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;

/**
 * Container class which extends JLayeredPane to ensure its sizing
 * tracks its "master" child component and supported fading a message
 * component in/out of the top layer
 * @author Amy Fowler
 */
public class Stacker extends JLayeredPane {
    private Component master; // dictates sizing, scrolling
    private JPanel messageLayer;
    private JXPanel messageAlpha;

    public Stacker(Component master) {
        this.master = master;
        setLayout(null);
        add(master, JLayeredPane.DEFAULT_LAYER);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return master.getPreferredSize();
    }
    
    @Override
    public void doLayout() {
        // ensure all layers are sized the same
        Dimension size = getSize();
        Component layers[] = getComponents();
        for (Component layer : layers) {
            layer.setBounds(0, 0, size.width, size.height);
        }
    }
   
    /**
     * Fades in the specified message component in the top layer of this
     * layered pane.
     * @param message the component to be displayed in the message layer
     * @param finalAlpha the alpha value of the component when fade in is complete
     */
    public void showMessageLayer(JComponent message, final float finalAlpha) {
        messageLayer = new JPanel();
        messageLayer.setOpaque(false);
        GridBagLayout gridbag = new GridBagLayout();
        messageLayer.setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;

        messageAlpha = new JXPanel();
        messageAlpha.setOpaque(false);
        messageAlpha.setAlpha(0.0f);
        gridbag.addLayoutComponent(messageAlpha, c);
        messageLayer.add(messageAlpha);
        messageAlpha.add(message);

        add(messageLayer, JLayeredPane.POPUP_LAYER);
        revalidate();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Animator animator = new Animator(2000,
                        new PropertySetter(messageAlpha, "alpha", 0.0f, finalAlpha));
                animator.setStartDelay(200);
                animator.setAcceleration(.2f);
                animator.setDeceleration(.5f);
                animator.start();
            }
        });
    }

    /**
     * Fades out and removes the current message component
     */
    public void hideMessageLayer() {
        if (messageLayer != null && messageLayer.isShowing()) {
            Animator animator = new Animator(500,
                    new PropertySetter(messageAlpha, "alpha", messageAlpha.getAlpha(), 0.0f) {
                        public void end() {
                            remove(messageLayer);
                            revalidate();
                        }
                    });
            animator.setStartDelay(300);
            animator.setAcceleration(.2f);
            animator.setDeceleration(.5f);
            animator.start();
        }
    }

}

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

package com.sun.swingset3.utilities;

import com.sun.swingset3.utilities.ArrowIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 * 
 * @author aim
 */
public class CollapsiblePanel extends JPanel {
    public enum Orientation { HORIZONTAL, VERTICAL }
      
    private JPanel panel;
    private JComponent child;
    
    private JCheckBox expandCheckBox; // may be null, if no title was supplied
    private Orientation orientation = Orientation.VERTICAL;
    private Dimension childPrefSize;    
    private boolean expanded = true;
    
    public CollapsiblePanel(JComponent child) {
        this(child, Orientation.VERTICAL);
    }
    
    public CollapsiblePanel(JComponent child, Orientation orientation) {
        this.orientation = orientation;
        this.child = child;
        setLayout(new BorderLayout()); 
        panel = new JPanel(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        panel.add(child, BorderLayout.CENTER);
    }
    
    /** Creates a new instance of CollapsiblePanel */
    public CollapsiblePanel(JComponent child, String title, String tooltip) {
        this(child, Orientation.VERTICAL, title, tooltip);
    }
    
    public CollapsiblePanel(JComponent child, String title) {
        this(child, Orientation.VERTICAL, title, null);
    }
    
    public CollapsiblePanel(JComponent child, Orientation orientation,
            String title, String tooltip) {
        this(child, orientation);
        add(createCollapseControl(title, tooltip), 
                orientation == Orientation.HORIZONTAL? 
                    BorderLayout.WEST : BorderLayout.NORTH);
        
    }
        
    protected Component createCollapseControl(String title, String tooltip) {
        // Add upper panel to hold collapse control
        Box box = Box.createHorizontalBox();
        
        expandCheckBox = new JCheckBox(title);
        expandCheckBox.setBorder(new EmptyBorder(0,4,0,0));
        expandCheckBox.setToolTipText(tooltip);
        expandCheckBox.setHorizontalTextPosition(JCheckBox.RIGHT);
        expandCheckBox.setSelectedIcon(new ArrowIcon(ArrowIcon.SOUTH));
        expandCheckBox.setIcon(new ArrowIcon(ArrowIcon.EAST));
        expandCheckBox.setSelected(isExpanded());
        
        expandCheckBox.addChangeListener(new CollapseListener());
        box.add(expandCheckBox);
        
        return box;                       
    }
    
    public void setExpanded(boolean expanded) {
        boolean oldExpanded = this.expanded;
        if (oldExpanded != expanded) {
            if (expandCheckBox != null) {
                expandCheckBox.setSelected(expanded);
            }
            childPrefSize = child.getPreferredSize();
            this.expanded = expanded;
            
            if (isShowing()) {
                // only animate if currently showing
                Animator animator = null;
                if (orientation == Orientation.VERTICAL) {
                    animator = new Animator(600, new PropertySetter(this, "collapseHeight",
                            expanded ? 0 : childPrefSize.height, expanded ? childPrefSize.height : 0));

                }
                if (orientation == Orientation.HORIZONTAL) {
                    animator = new Animator(600, new PropertySetter(this, "collapseWidth",
                            expanded ? 0 : childPrefSize.width, expanded ? childPrefSize.width : 0));
                }
                animator.setStartDelay(10);
                animator.setAcceleration(.2f);
                animator.setDeceleration(.3f);
                animator.start();
            } else {
                if (orientation == Orientation.VERTICAL) {
                    setCollapseHeight(expanded? childPrefSize.height : 0);

                } else if (orientation == Orientation.HORIZONTAL) {
                    setCollapseWidth(expanded? childPrefSize.width : 0);
                }               
            }           
            firePropertyChange("expanded", oldExpanded, expanded);

        }
    }
    
    // intended only for animator, but must be public
    public void setCollapseHeight(int height) {
        panel.setPreferredSize(new Dimension(childPrefSize.width, height));
        child.revalidate();
        repaint();
    }
    
    // intended only for animator, but must be public
    public void setCollapseWidth(int width) {
        panel.setPreferredSize(new Dimension(width, childPrefSize.height));
        child.revalidate();
        repaint();
    }

    public boolean isExpanded() {
        return expanded;
    }
    
    public void setExpandedIcon(Icon expandedIcon) {
        if (expandCheckBox != null) {
            expandCheckBox.setSelectedIcon(expandedIcon);
        }
    }
    
    public void setCollapsedIcon(Icon collapsedIcon) {
        if (expandCheckBox != null) {
            expandCheckBox.setIcon(collapsedIcon);
        }
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (expandCheckBox != null) {
            expandCheckBox.setFont(font);
        }
    }
    
    @Override
    public void setForeground(Color foreground) {
        super.setForeground(foreground);
        if (expandCheckBox != null) {
            expandCheckBox.setForeground(foreground);
        }
    }
    
    @Override
    public void updateUI() {
        super.updateUI();
        //configureDefaults();
    }
    
    protected void configureDefaults() {
        if (expandCheckBox != null) {
            if (UIManager.getLookAndFeel().getName().equals("Nimbus")) {
                expandCheckBox.setBorder(new EmptyBorder(0,4,0,0));
            } else {
                expandCheckBox.setBorder(new EmptyBorder(0,0,0,0));
            }
        }
    }
    
    // only used if checkbox is present
    private class CollapseListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            setExpanded(expandCheckBox.isSelected());
        }
    }    
}

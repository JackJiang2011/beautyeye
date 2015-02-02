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

package com.sun.swingset3.demos.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.sun.swingset3.DemoProperties;
import com.sun.swingset3.demos.DemoUtilities;

/**
 *
 * @author aim
 */
@DemoProperties(
        value = "JDialog Demo",
        category = "Toplevel Containers",
        description = "Demonstrates JDialog, Swing's top-level dialog container.",
        sourceFiles = {
                "com/sun/swingset3/demos/dialog/DialogDemo.java",
                "com/sun/swingset3/demos/DemoUtilities.java",
                "com/sun/swingset3/demos/dialog/resources/DialogDemo.html",
                "com/sun/swingset3/demos/dialog/resources/images/DialogDemo.gif"
                }
)
public class DialogDemo extends JPanel { 
    
    private JDialog dialog;
    
    private JComponent dialogSpaceholder;    
        
    public DialogDemo() {        
        initComponents();
    }
    
    protected void initComponents() {
        dialog = createDialog();

        setLayout(new BorderLayout()); 
        
        add(createControlPanel(), BorderLayout.WEST);
        dialogSpaceholder = createDialogSpaceholder(dialog);
        add(dialogSpaceholder, BorderLayout.CENTER);
    }
                
    private static JComponent createDialogSpaceholder(JDialog dialog) {
        // Create placeholder panel to provide space in which to
        // display the toplevel dialog so that the control panel is not
        // obscured by it.
        JPanel placeholder = new JPanel();
        Dimension prefSize = dialog.getPreferredSize();
        prefSize.width += 12;
        prefSize.height += 12;
        placeholder.setPreferredSize(prefSize);
        return placeholder;
    }
    
    protected JComponent createControlPanel() {
        // Create control panel on Left
        Box panel = Box.createVerticalBox();
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));
        
        // Create button to control visibility of frame
        JButton showButton = new JButton("Show JDialog...");
        showButton.addActionListener(new ShowActionListener());
        panel.add(showButton); 
        
        return panel;
    }
    
    private static JDialog createDialog() {
 
        //<snip>Create dialog
        JDialog dialog = new JDialog(new JFrame(), "Demo JDialog", false);
        //</snip>
        
        //<snip>Add dialog's content
        JLabel label = new JLabel("I'm content.");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(200,140));
        dialog.add(label);
        //</snip>
        
        //<snip>Initialize dialog's size
        // which will shrink-to-fit its contents
        dialog.pack(); 
        //</snip>
        
        return dialog;
    }
    
    public void start() {
        DemoUtilities.setToplevelLocation(dialog, dialogSpaceholder, SwingConstants.CENTER);
        showDialog();
    }
    
    public void stop() {
        //<snip>Hide dialog
        dialog.setVisible(false);
        //</snip>
    }
    
    public void showDialog() {
        //<snip>Show dialog
        // if dialog already visible, then bring to the front
        if (dialog.isShowing()) {
            dialog.toFront();
        } else {
            dialog.setVisible(true);
        }
        //</snip>
    }
    
    private class ShowActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            showDialog();
        }
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                DialogDemo demo = new DialogDemo();
                frame.add(demo);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                demo.start();
            }
        });
    }
}

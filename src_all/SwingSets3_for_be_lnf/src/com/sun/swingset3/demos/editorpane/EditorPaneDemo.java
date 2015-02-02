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

package com.sun.swingset3.demos.editorpane;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import com.sun.swingset3.DemoProperties;

/**
 * EditorPane Demo (was HTMLDemo in SwingSet2)
 *
 * @version 1.12 05/11/17
 * @author Jeff Dinkins
 */
@DemoProperties(
        value = "JEditorPane Demo",
        category = "Text",
        description = "Demonstrates JEditorPane, a text component which supports display and editing of rich text formats (such as HTML)",
        sourceFiles = {
                "com/sun/swingset3/demos/editorpane/EditorPaneDemo.java",
                "com/sun/swingset3/demos/editorpane/book/ant.html",
                "com/sun/swingset3/demos/editorpane/book/bug.html",
                "com/sun/swingset3/demos/editorpane/book/index.html",
                "com/sun/swingset3/demos/editorpane/book/king.html",
                "com/sun/swingset3/demos/editorpane/book/preface.html",
                "com/sun/swingset3/demos/editorpane/book/seaweed.html",
                "com/sun/swingset3/demos/editorpane/book/title.html",
                "com/sun/swingset3/demos/editorpane/book/editorpane/back.jpg",
                "com/sun/swingset3/demos/editorpane/book/editorpane/forward.jpg",
                "com/sun/swingset3/demos/editorpane/book/editorpane/header.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/ant.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/book.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/bug.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/bug2.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/COPYRIGHT",
                "com/sun/swingset3/demos/editorpane/book/Octavo/crest.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/king.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/micro.jpg",
                "com/sun/swingset3/demos/editorpane/book/Octavo/seaweed.jpg",
                "com/sun/swingset3/demos/editorpane/resources/EditorPaneDemo.properties",
                "com/sun/swingset3/demos/editorpane/resources/images/EditorPaneDemo.gif"
                }
)
public class EditorPaneDemo extends JPanel {

    private JEditorPane html;

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(EditorPaneDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new EditorPaneDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * EditorPaneDemo Constructor
     */
    public EditorPaneDemo() {
        setLayout(new BorderLayout());

        try {
            URL url;
            // System.getProperty("user.dir") +
            // System.getProperty("file.separator");
            String path = null;
            try {
                path = "book/index.html";
                url = getClass().getResource(path);
            } catch (Exception e) {
                System.err.println("Failed to open " + path);
                url = null;
            }

            if (url != null) {
                html = new JEditorPane(url);
                html.setEditable(false);
                html.addHyperlinkListener(createHyperLinkListener());

                JScrollPane scroller = new JScrollPane();
                JViewport vp = scroller.getViewport();
                vp.add(html);
                add(scroller, BorderLayout.CENTER);
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    private HyperlinkListener createHyperLinkListener() {
        return new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (e instanceof HTMLFrameHyperlinkEvent) {
                        ((HTMLDocument) html.getDocument()).processHTMLFrameHyperlinkEvent(
                                (HTMLFrameHyperlinkEvent) e);
                    } else {
                        try {
                            html.setPage(e.getURL());
                        } catch (IOException ioe) {
                            System.out.println("IOE: " + ioe);
                        }
                    }
                }
            }
        };
    }
}

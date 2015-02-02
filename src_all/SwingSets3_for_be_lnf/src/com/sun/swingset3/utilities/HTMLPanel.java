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

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.util.EventListener;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ObjectView;

import com.sun.swingset3.demos.DemoUtilities;

/**
 *
 * Extends JEditorPane for more convenient display of an HTML page. 
 * <p>
 * Swing's HTML support allows Swing components to be embedded in the HTML using
 * the <object> tag,
 * e.g.<br>
 * <pre><code>    
 *	<object classid="swingset3.demos.controls.JButtonDemo">
 * </code></pre>
 * These embedded components will be instantiated deep inside the text package and
 * it is difficult to get a handle on the resulting instantiated component instance.
 * Thus, this class supports adding a ComponentCreationListener to be notified just
 * after the component is created.</p>
 * <p>
 * This class also automatically installs a hyperlink listener to ensure that
 * clicking on hyperlinks bring up the associated url in the browser.
 * 
 * @author aim
 */
public class HTMLPanel extends JEditorPane {
    /**
     * Callback interface for getting notification when components specified in the
     * <object> tag are created in the editor pane.
     */
    public static interface ComponentCreationListener extends EventListener {
        public void componentCreated(HTMLPanel htmlPanel, Component component);
    }

    private static HyperlinkHandler hyperlinkHandler;

    /**
     * Creates a new instance of HTMLPanel with a default content type of &quot;text/html&quot;
     */
    public HTMLPanel() {
        setContentType("text/html");
        setEditorKit(new ComponentEditorKit()); // brute force
        setEditable(false); // VERY IMPORTANT!
        if (hyperlinkHandler == null) {
            hyperlinkHandler = new HyperlinkHandler();
        }
        addHyperlinkListener(hyperlinkHandler);
    }

    public void addComponentCreationListener(HTMLPanel.ComponentCreationListener l) {
        listenerList.add(ComponentCreationListener.class, l);
    }

    public void removeComponentCreationListener(HTMLPanel.ComponentCreationListener l) {
        listenerList.remove(HTMLPanel.ComponentCreationListener.class, l);
    }

    private class ComponentEditorKit extends HTMLEditorKit {
        @Override
        public ViewFactory getViewFactory() {
            return new ComponentFactory();
        }
    }

    protected class ComponentFactory extends HTMLEditorKit.HTMLFactory {
        public ComponentFactory() {
            super();
        }

        @Override
        public View create(Element element) {
            AttributeSet attrs = element.getAttributes();
            Object elementName =
                    attrs.getAttribute(AbstractDocument.ElementNameAttribute);
            Object o = (elementName != null) ?
                    null : attrs.getAttribute(StyleConstants.NameAttribute);
            if (o instanceof HTML.Tag) {
                if (o == HTML.Tag.OBJECT) {
                    return new ComponentView(element);
                }
            }
            return super.create(element);
        }
    }

    protected class ComponentView extends ObjectView {
        public ComponentView(Element element) {
            super(element);
        }

        @Override
        protected Component createComponent() {
            final Component component = super.createComponent();

            Runnable notifier = new Runnable() {
                public void run() {
                    final ComponentCreationListener listeners[] =
                            HTMLPanel.this.listenerList.getListeners(ComponentCreationListener.class);
                    for (ComponentCreationListener l : listeners) {
                        l.componentCreated(HTMLPanel.this, component);
                    }
                }
            };
            // just in case document is being loaded in separate thread. (?)
            if (EventQueue.isDispatchThread()) {
                notifier.run();
            } else {
                EventQueue.invokeLater(notifier);
            }
            return component;
        }
    }

    // single instance of handler is shared for ALL DemoPanel instances
    public static class HyperlinkHandler implements HyperlinkListener {
        Cursor defaultCursor;

        public void hyperlinkUpdate(HyperlinkEvent event) {
            JEditorPane descriptionPane = (JEditorPane) event.getSource();
            HyperlinkEvent.EventType type = event.getEventType();
            if (type == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    DemoUtilities.browse(event.getURL().toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e);
                }

            } else if (type == HyperlinkEvent.EventType.ENTERED) {
                defaultCursor = descriptionPane.getCursor();
                descriptionPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            } else if (type == HyperlinkEvent.EventType.EXITED) {
                descriptionPane.setCursor(defaultCursor);
            }
        }
    }
}

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
package com.sun.swingset3.demos.textfield;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * JHistoryTextField
 *
 * @author Pavel Porvatov
 */
public class JHistoryTextField extends JTextField {
    private static final int MAX_VISIBLE_ROWS = 8;

    private final List<String> history = new ArrayList<String>();

    private final JPopupMenu popup = new JPopupMenu() {
        public Dimension getPreferredSize() {
            Dimension dimension = super.getPreferredSize();

            dimension.width = JHistoryTextField.this.getWidth();

            return dimension;
        }
    };

    private final JList list = new JList(new DefaultListModel());

    private String userText;

    private boolean notificationDenied;

    public JHistoryTextField() {
        JScrollPane scrollPane = new JScrollPane(list,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBar(null);
        scrollPane.setBorder(null);

        list.setFocusable(false);

        popup.add(scrollPane);
        popup.setFocusable(false);
        popup.setBorder(new LineBorder(Color.BLACK, 1));

        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }
        });

        list.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());

                if (index >= 0 && list.getSelectedIndex() != index) {
                    list.setSelectedIndex(index);
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    setTextWithoutNotification((String) list.getSelectedValue());

                    popup.setVisible(false);
                }
            }
        });

        addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                popup.setVisible(false);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (popup.isShowing()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP: {
                            changeListSelectedIndex(-1);

                            break;
                        }

                        case KeyEvent.VK_PAGE_UP: {
                            changeListSelectedIndex(-list.getVisibleRowCount());

                            break;
                        }

                        case KeyEvent.VK_DOWN: {
                            changeListSelectedIndex(1);

                            break;
                        }

                        case KeyEvent.VK_PAGE_DOWN: {
                            changeListSelectedIndex(list.getVisibleRowCount());

                            break;
                        }

                        case KeyEvent.VK_ESCAPE: {
                            popup.setVisible(false);

                            setTextWithoutNotification(userText);

                            break;
                        }

                        case KeyEvent.VK_ENTER:
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_RIGHT: {
                            popup.setVisible(false);

                            break;
                        }
                    }
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN ||
                            e.getKeyCode() == KeyEvent.VK_UP ||
                            e.getKeyCode() == KeyEvent.VK_PAGE_UP ||
                            e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                        userText = getText();

                        showFilteredHistory();
                    }
                }
            }
        });
    }

    private void changeListSelectedIndex(int delta) {
        int size = list.getModel().getSize();
        int index = list.getSelectedIndex();

        int newIndex;

        if (index < 0) {
            newIndex = delta > 0 ? 0 : size - 1;
        } else {
            newIndex = index + delta;
        }

        if (newIndex >= size || newIndex < 0) {
            newIndex = newIndex < 0 ? 0 : size - 1;

            if (index == newIndex) {
                newIndex = -1;
            }
        }

        if (newIndex < 0) {
            list.getSelectionModel().clearSelection();
            list.ensureIndexIsVisible(0);

            setTextWithoutNotification(userText);
        } else {
            list.setSelectedIndex(newIndex);
            list.ensureIndexIsVisible(newIndex);

            setTextWithoutNotification((String) list.getSelectedValue());
        }
    }

    private void setTextWithoutNotification(String text) {
        notificationDenied = true;

        try {
            setText(text);
        } finally {
            notificationDenied = false;
        }
    }

    private void onTextChanged() {
        if (!notificationDenied) {
            userText = getText();

            showFilteredHistory();
        }
    }

    private void showFilteredHistory() {
        list.getSelectionModel().clearSelection();

        DefaultListModel model = (DefaultListModel) list.getModel();

        model.clear();

        for (String s : history) {
            if (s.contains(userText)) {
                model.addElement(s);
            }
        }

        int size = model.size();

        if (size == 0) {
            popup.setVisible(false);
        } else {
            list.setVisibleRowCount(size < MAX_VISIBLE_ROWS ? size : MAX_VISIBLE_ROWS);

            popup.pack();

            if (!popup.isShowing()) {
                popup.show(JHistoryTextField.this, 0, getHeight());
            }
        }
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void setHistory(List<? extends String> history) {
        this.history.clear();
        this.history.addAll(history);
    }
}

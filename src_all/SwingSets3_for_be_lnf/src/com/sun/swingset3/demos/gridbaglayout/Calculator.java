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

package com.sun.swingset3.demos.gridbaglayout;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Calculator
 *
 * @author Pavel Porvatov
 */
public class Calculator extends JComponent {
    private static final String ZERO = "0";

    private static final char DECIMAL_SEPARATOR = ',';

    private final JTextField tfScreen = new JTextField();

    private enum State {
        INPUT_X,
        INPUT_X_FINISHED,
        INPUT_Y,
        INPUT_Y_FINISHED
    }

    private enum Operator {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        SQRT,
        INVERSE,
        EQUALS
    }

    private final Map<Character, Operator> keyMapping = new HashMap<Character, Operator>();

    private String operand_x;

    private Operator operator;

    private State state;

    public Calculator() {
        keyMapping.put('/', Operator.DIVISION);
        keyMapping.put('*', Operator.MULTIPLICATION);
        keyMapping.put('+', Operator.ADDITION);
        keyMapping.put('-', Operator.SUBTRACTION);
        keyMapping.put('\n', Operator.EQUALS);

        initUI();

        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (Character.isDigit(c)) {
                    doProcessChar(c);
                } else if (c == '.' || c == ',') {
                    doProcessChar(DECIMAL_SEPARATOR);
                } else {
                    Operator operator = keyMapping.get(c);

                    if (operator != null) {
                        doProcessOperator(operator);
                    }
                }
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_BACK_SPACE:
                        doProcessBackspace();

                        break;

                    case KeyEvent.VK_DELETE:
                        doReset();
                }
            }
        });

        doReset();
    }

    private void initUI() {
        tfScreen.setHorizontalAlignment(JTextField.RIGHT);

        JButton btnBackspace = new JButton("Backspace");

        btnBackspace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doProcessBackspace();
            }
        });

        JButton btnReset = new JButton("C");

        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doReset();
            }
        });

        JPanel pnGridPanel = new JPanel(new GridLayout(1, 2, 8, 8));

        pnGridPanel.add(btnBackspace);
        pnGridPanel.add(btnReset);

        setLayout(new GridBagLayout());

        JButton btnSwapSign = new SquareButton("+/-");

        btnSwapSign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doSwapSign();
            }
        });

        addComp(tfScreen, 0, 0, 5, 1);
        addComp(pnGridPanel, 0, 1, 5, 1);

        addComp(new CharButton('7'), 0, 2, 1, 1);
        addComp(new CharButton('8'), 1, 2, 1, 1);
        addComp(new CharButton('9'), 2, 2, 1, 1);
        addComp(new OperatorButton(Operator.DIVISION, "/"), 3, 2, 1, 1);
        addComp(new OperatorButton(Operator.INVERSE, "1/x"), 4, 2, 1, 1);

        addComp(new CharButton('4'), 0, 3, 1, 1);
        addComp(new CharButton('5'), 1, 3, 1, 1);
        addComp(new CharButton('6'), 2, 3, 1, 1);
        addComp(new OperatorButton(Operator.MULTIPLICATION, "*"), 3, 3, 1, 1);
        addComp(new OperatorButton(Operator.SQRT, "sqrt"), 4, 3, 1, 1);

        addComp(new CharButton('1'), 0, 4, 1, 1);
        addComp(new CharButton('2'), 1, 4, 1, 1);
        addComp(new CharButton('3'), 2, 4, 1, 1);
        addComp(new OperatorButton(Operator.SUBTRACTION, "-"), 3, 4, 1, 1);

        addComp(new CharButton('0'), 0, 5, 1, 1);
        addComp(btnSwapSign, 1, 5, 1, 1);
        addComp(new CharButton(DECIMAL_SEPARATOR), 2, 5, 1, 1);
        addComp(new OperatorButton(Operator.ADDITION, "+"), 3, 5, 1, 1);
        addComp(new OperatorButton(Operator.EQUALS, "="), 4, 5, 1, 1);

        // Set focusable false
        resetFocusable(this);

        setFocusable(true);
    }

    private static void resetFocusable(Component component) {
        component.setFocusable(false);

        if (component instanceof Container) {
            for (Component c : ((Container) component).getComponents()) {
                resetFocusable(c);
            }
        }
    }

    private void doReset() {
        operand_x = null;
        operator = null;
        state = State.INPUT_X;

        tfScreen.setText(ZERO);
    }

    private void doProcessChar(char c) {
        String text = tfScreen.getText();

        String newValue;

        if (state == State.INPUT_X || state == State.INPUT_Y) {
            newValue = attachChar(text, c);

            if (stringToValue(newValue) == null) {
                return;
            }
        } else {
            newValue = attachChar("0", c);

            if (stringToValue(newValue) == null) {
                return;
            }

            if (operator == null) {
                operand_x = null;

                state = State.INPUT_X;
            } else {
                operand_x = text;

                state = State.INPUT_Y;
            }
        }

        tfScreen.setText(newValue);
    }

    private static String attachChar(String s, char c) {
        if (Character.isDigit(c)) {
            if (s.equals(ZERO)) {
                return Character.toString(c);
            }

            if (s.equals("-" + ZERO)) {
                return "-" + Character.toString(c);
            }

            return s + Character.toString(c);
        } else {
            return s + Character.toString(c);
        }
    }

    private void doSwapSign() {
        String text = tfScreen.getText();

        tfScreen.setText(text.startsWith("-") ? text.substring(1) : "-" + text);
    }

    private void doProcessBackspace() {
        String text = tfScreen.getText();

        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }

        if (text.length() == 0 || text.equals("-")) {
            text = ZERO;
        }

        if (stringToValue(text) != null) {
            tfScreen.setText(text);
        }
    }

    private void doProcessOperator(Operator operator) {
        double y = stringToValue(tfScreen.getText());

        // Process unary operators
        boolean isUnary;

        switch (operator) {
            case SQRT:
                tfScreen.setText(valueToString(Math.sqrt(y)));

                isUnary = true;

                break;

            case INVERSE:
                tfScreen.setText(valueToString(1 / y));

                isUnary = true;

                break;

            default:
                isUnary = false;
        }

        if (isUnary) {
            if (state == State.INPUT_X) {
                state = State.INPUT_X_FINISHED;
            }

            if (state == State.INPUT_Y) {
                state = State.INPUT_Y_FINISHED;
            }

            return;
        }

        // Process binary operators
        if (state == State.INPUT_Y || state == State.INPUT_Y_FINISHED) {
            double x = stringToValue(operand_x);
            double result;

            switch (this.operator) {
                case ADDITION:
                    result = x + y;

                    break;

                case SUBTRACTION:
                    result = x - y;

                    break;

                case MULTIPLICATION:
                    result = x * y;

                    break;

                case DIVISION:
                    result = x / y;

                    break;

                default:
                    throw new IllegalStateException("Unsupported operation " + operator);
            }

            tfScreen.setText(valueToString(result));
        }

        this.operator = operator == Operator.EQUALS ? null : operator;
        operand_x = null;

        state = State.INPUT_X_FINISHED;
    }

    private static Double stringToValue(String value) {
        try {
            return new Double(value.replace(DECIMAL_SEPARATOR, '.'));
        } catch (NumberFormatException e) {
            // Continue convertion
        }

        if (value.endsWith(String.valueOf(DECIMAL_SEPARATOR))) {
            try {
                // Try convert uncompleted value
                return new Double(value.substring(0, value.length() - 1));
            } catch (NumberFormatException e) {
                // Continue convertion
            }
        }

        return null;
    }

    private static String valueToString(Double value) {
        if (value == null) {
            return ZERO;
        } else {
            String result = value.toString();

            if (result.endsWith(".0")) {
                result = result.substring(0, result.length() - 2);
            }

            if (result.equals("-0")) {
                result = ZERO;
            }

            return result;
        }
    }

    private void addComp(Component comp, int gridx, int gridy,
            int gridwidth, int gridheight) {
        add(comp, new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(4, 4, 4, 4), 0, 0));
    }

    private static class SquareButton extends JButton {
        private SquareButton(String text) {
            super(text);

            setMargin(new Insets(2, 0, 2, 0));
        }

        public Dimension getMinimumSize() {
            Dimension result = super.getMinimumSize();

            if (result.width < result.height) {
                result.width = result.height;
            }

            return result;
        }

        public Dimension getPreferredSize() {
            return getMinimumSize();
        }
    }

    private class CharButton extends SquareButton {
        private CharButton(final char c) {
            super(String.valueOf(c));

            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doProcessChar(c);
                }
            });
        }
    }

    private class OperatorButton extends SquareButton {
        private OperatorButton(final Operator operator, String text) {
            super(text);

            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doProcessOperator(operator);
                }
            });
        }
    }
}

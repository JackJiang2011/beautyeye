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

package com.sun.swingset3.demos.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author aim
 */
public class OscarCellRenderers {

    //<snip>Render table rows with alternating colors
    public static class RowRenderer extends DefaultTableCellRenderer {
        private Color rowColors[];

        public RowRenderer() {
            // initialize default colors from look-and-feel
            rowColors = new Color[1];
            rowColors[0] = UIManager.getColor("Table.background");
        }

        public RowRenderer(Color colors[]) {
            super();
            setRowColors(colors);
        }

        public void setRowColors(Color colors[]) {
            rowColors = colors;
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setText(value != null ? value.toString() : "unknown");
            if (!isSelected) {
                setBackground(rowColors[row % rowColors.length]);
            }
            return this;
        }

        public boolean isOpaque() {
            return true;
        }
    }
    //<snip>

    //<snip>Render "year" table column with font representing style of decade
    // currently only used on OS X because fonts are Mac centric.

    public static class YearRenderer extends RowRenderer {
        private HashMap<String, Font> eraFonts;

        public YearRenderer() {
            setHorizontalAlignment(JLabel.CENTER);

            if (System.getProperty("os.name").equals("Mac OS X")) {
                eraFonts = new HashMap<String, Font>();
                eraFonts.put("192"/*1920's*/, new Font("Jazz LET", Font.PLAIN, 12));
                eraFonts.put("193"/*1930's*/, new Font("Mona Lisa Solid ITC TT", Font.BOLD, 18));
                eraFonts.put("194"/*1940's*/, new Font("American Typewriter", Font.BOLD, 12));
                eraFonts.put("195"/*1950's*/, new Font("Britannic Bold", Font.PLAIN, 12));
                eraFonts.put("196"/*1960's*/, new Font("Cooper Black", Font.PLAIN, 14));
                eraFonts.put("197"/*1970's*/, new Font("Syncro LET", Font.PLAIN, 14));
                eraFonts.put("198"/*1980's*/, new Font("Mistral", Font.PLAIN, 18));
                eraFonts.put("199"/*1990's*/, new Font("Papyrus", Font.BOLD, 14));
                eraFonts.put("200"/*2000's*/, new Font("Calisto MT", Font.PLAIN, 14));
            }
        }

        public YearRenderer(Color colors[]) {
            this();
            setRowColors(colors);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String year = table.getValueAt(row,
                    table.convertColumnIndexToView(OscarTableModel.YEAR_COLUMN)).toString();
            if (eraFonts != null && year != null && year.length() == 4) {
                String era = year.substring(0, 3);
                Font eraFont = eraFonts.get(era);
                setFont(eraFont);
            }
            return this;
        }
    }
    //</snip>

    //<snip>Render "nominee" table column with special icon for winners

    public static class NomineeRenderer extends RowRenderer {
        private final ImageIcon winnerIcon;
        private final ImageIcon nomineeIcon; // nice way of saying "loser" :)

        public NomineeRenderer() {
            winnerIcon = new ImageIcon(
                    getClass().getResource("resources/images/goldstar.png"));
            nomineeIcon = new ImageIcon(
                    getClass().getResource("resources/images/nominee.png"));
            setHorizontalTextPosition(JLabel.TRAILING);
        }

        public NomineeRenderer(Color colors[]) {
            this();
            setRowColors(colors);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            TableModel model = table.getModel();
            boolean winner = ((Boolean) model.getValueAt(table.convertRowIndexToModel(row),
                    OscarTableModel.WINNER_COLUMN)).booleanValue();

            List<String> persons = (List<String>) value;
            String text = persons != null && !persons.isEmpty() ? persons.get(0) : "name unknown";
            int personCount = persons.size();
            if (personCount > 1) {
                setText(text + " + more...");
                StringBuffer winners = new StringBuffer("");
                for (int i = 0; i < personCount; i++) {
                    String person = persons.get(i);
                    winners.append(" " + person + (i < personCount - 1 ? ", " : ""));
                }
                setToolTipText((winner ? "Winners:" : "Nominees:") + winners);
            } else {
                setText(text);
                setToolTipText(winner ? "Winner!" : "Nominee");
            }

            setIcon(winner ? winnerIcon : nomineeIcon);

            return this;
        }
    }
    //</snip>

    public static class MovieRenderer extends HyperlinkCellRenderer {
        public MovieRenderer(Action action, boolean underlineOnRollover, Color rowColors[]) {
            super(action, underlineOnRollover);
            setRowColors(rowColors);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
            if (value != null) {
                setToolTipText("http://www.imdb.com/" + "\"" + value + "\"");
            }
            return this;
        }
    }

}

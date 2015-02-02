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

package com.sun.swingset3.utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 * Simple application which displays the contents of the UIDefaults hash map
 * for the current look and feel.
 *
 * @author aim
 */
public class DefaultsDisplay extends JPanel {
    private static final int rowHeight = 32;
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {            
            public void run() {
                JFrame frame = new JFrame("UIDefaults Display");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new DefaultsDisplay());
                frame.pack();
                frame.setVisible(true);
            }
        });        
    }

    
    private String OSXLookAndFeelName; // hack for look-and-feel name mismatch on OS X
    
    private Map<String,String> lookAndFeelsMap; 
    private final Map<String,JComponent> defaultsTablesMap;
    
    private JComboBox lookAndFeelComboBox;
    private JCheckBox onlyVisualsCheckBox;
    private JTabbedPane tabPane;
    
    private RowFilter<UIDefaultsTableModel,Integer> visualsFilter;
    
    
    /** Creates a new instance of DefaultsDisplayer */
    public DefaultsDisplay() {
        defaultsTablesMap = new HashMap<String,JComponent>();    
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if (System.getProperty("os.name").equals("Mac OS X")) {
                OSXLookAndFeelName = UIManager.getLookAndFeel().getName();
            }
        } catch (Exception ex) {
            // better work :-)
        }
        
        setLayout(new BorderLayout());
                
        JPanel controls = new JPanel();        
        controls.add(createLookAndFeelControl());
        controls.add(createFilterControl());
        add(controls, BorderLayout.NORTH);
                
        tabPane = new JTabbedPane();
        add(tabPane, BorderLayout.CENTER);
        
        addDefaultsTab();
        
    }
    
    protected JComponent createLookAndFeelControl() {
        JPanel panel = new JPanel();
        
        JLabel label = new JLabel("Current Look and Feel");
        lookAndFeelComboBox = new JComboBox();
        label.setLabelFor(lookAndFeelComboBox);
        panel.add(label);
        panel.add(lookAndFeelComboBox);
        
        // Look for toolkit look and feels first
        UIManager.LookAndFeelInfo lookAndFeelInfos[] = UIManager.getInstalledLookAndFeels();
        lookAndFeelsMap = new HashMap<String,String>();
        for(UIManager.LookAndFeelInfo info : lookAndFeelInfos) {
            String name = info.getName();
            // workaround for problem where Info and name property don't match on OS X
            if (name.equals("Mac OS X")) {
                name = OSXLookAndFeelName;
            }
            // workaround for bug where Nimbus classname is incorrect
            lookAndFeelsMap.put(name, name.equals("Nimbus")? 
                "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" :
                          info.getClassName());
            lookAndFeelComboBox.addItem(name);
        }
        lookAndFeelComboBox.setSelectedItem(UIManager.getLookAndFeel().getName());
        lookAndFeelComboBox.addActionListener(new ChangeLookAndFeelAction());
        
        return panel;
    }
    
    protected JComponent createFilterControl() {
              
        onlyVisualsCheckBox = new JCheckBox("Show Only Visual Defaults");
        onlyVisualsCheckBox.setSelected(true);
        onlyVisualsCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boolean showOnlyVisuals = onlyVisualsCheckBox.isSelected();
                for(int i = 0; i < tabPane.getTabCount() ; i++) {
                    JScrollPane scrollpane = (JScrollPane)tabPane.getComponentAt(i);
                    JTable table = (JTable)scrollpane.getViewport().getView();
                    TableRowSorter sorter = (TableRowSorter)table.getRowSorter();
                    sorter.setRowFilter(showOnlyVisuals? visualsFilter : null);                    
                }
            }
        });
        return onlyVisualsCheckBox;
    }
    
    protected boolean hasDefaultsTab(String lookAndFeelName) {
        return defaultsTablesMap.get(lookAndFeelName) != null;
    }
    
    protected void addDefaultsTab() {
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        JScrollPane scrollPane = new JScrollPane(createDefaultsTable());
        tabPane.addTab(lookAndFeel.getName() + " Defaults", scrollPane);
        defaultsTablesMap.put(lookAndFeel.getName(), scrollPane);
    }
    
    protected JTable createDefaultsTable() {
        JTable table = new JTable(new UIDefaultsTableModel());
        table.setRowHeight(rowHeight);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initFilters(table);
        
        DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
        
        Color rowColors[] = new Color[2];
        rowColors[0] = UIManager.getColor("Table.background");
        rowColors[1] = new Color((int)(rowColors[0].getRed() * .90),
                                 (int)(rowColors[0].getGreen() * .95), 
                                 (int)(rowColors[0].getBlue() * .95));
        
        int width = 0;
        
        TableColumn column = new TableColumn();
        column.setCellRenderer(new KeyRenderer(rowColors));
        column.setModelIndex(UIDefaultsTableModel.KEY_COLUMN);
        column.setHeaderValue("Key");
        column.setPreferredWidth(250);
        columnModel.addColumn(column);
        width += column.getPreferredWidth();
        
        column = new TableColumn();
        column.setCellRenderer(new RowRenderer(rowColors));
        column.setModelIndex(UIDefaultsTableModel.TYPE_COLUMN);
        column.setHeaderValue("Type");
        column.setPreferredWidth(250);
        columnModel.addColumn(column);
        width += column.getPreferredWidth();
        
        column = new TableColumn();
        column.setCellRenderer(new ValueRenderer(rowColors));
        column.setModelIndex(UIDefaultsTableModel.VALUE_COLUMN);
        column.setHeaderValue("Value");
        column.setPreferredWidth(300);
        columnModel.addColumn(column);
        width += column.getPreferredWidth();
        
        table.setColumnModel(columnModel);
        
        table.setPreferredScrollableViewportSize(new Dimension(width, 12 * rowHeight));
        
        return table;
        
    }
    
    protected void initFilters(JTable table) {
        TableRowSorter sorter = new TableRowSorter(table.getModel());
        table.setRowSorter(sorter);
        
        if (visualsFilter == null) {
            visualsFilter = new RowFilter<UIDefaultsTableModel,Integer>() {
                public boolean include(Entry<? extends UIDefaultsTableModel, ? extends Integer> entry) {
                    UIDefaultsTableModel model = entry.getModel();
                    Object defaultsValue = model.getValueAt(entry.getIdentifier().intValue(),
                            UIDefaultsTableModel.VALUE_COLUMN);

                    return defaultsValue instanceof Color ||
                            defaultsValue instanceof Font ||
                            defaultsValue instanceof Icon;
                }
            };
        }
        
        if (onlyVisualsCheckBox.isSelected()) {
            sorter.setRowFilter(visualsFilter);
        }
    }
    
    private class ChangeLookAndFeelAction extends AbstractAction {
        
        public ChangeLookAndFeelAction() {
            super("Change LookAndFeel");
        }
        
        public void actionPerformed(ActionEvent event) {
            String lookAndFeelName = (String)lookAndFeelComboBox.getSelectedItem();
            try {
                UIManager.setLookAndFeel(lookAndFeelsMap.get(lookAndFeelName));
                SwingUtilities.updateComponentTreeUI(DefaultsDisplay.this.getTopLevelAncestor());
                if (!hasDefaultsTab(lookAndFeelName)) {
                    addDefaultsTab();
                }
                tabPane.setSelectedComponent(defaultsTablesMap.get(lookAndFeelName));
            } catch (Exception ex) {
                System.err.println("could not change look and feel to " + lookAndFeelName);
                System.err.println(ex);
            }
        }
    }
    
    private static class UIDefaultsTableModel extends AbstractTableModel {
        private static final int KEY_COLUMN = 0;
        private static final int TYPE_COLUMN = 1;
        private static final int VALUE_COLUMN = 2;
        
        private UIDefaults defaults;
        private List<Object> keys;
        
        public UIDefaultsTableModel() {
            // make a local copy of the defaults table in case the look and feel changes
            defaults = new UIDefaults();
            keys = new ArrayList<Object>();
            UIDefaults realDefaults = UIManager.getDefaults();
            Enumeration keysEnum = realDefaults.keys();
            while (keysEnum.hasMoreElements()) {
                Object key = keysEnum.nextElement();
                if (!defaults.containsKey(key)) {
                    keys.add(key);
                    defaults.put(key, realDefaults.get(key));
                } else {
                    System.out.println("found duplicate key:"+key);
                }
            }
        }
        
        public int getColumnCount() {
            return 3;
        }
        
        public int getRowCount() {
            return defaults.size();
        }
        
        public Class getColumnClass(int column) {
            Class klass = null;
            switch(column) {
                case KEY_COLUMN: {
                    klass = String.class;
                    break;
                }
                case TYPE_COLUMN: {
                    klass = String.class;
                    break;
                }
                case VALUE_COLUMN: {
                    klass = Object.class;
                }
            }
            return klass;
        }
        
        public Object getValueAt(int row, int column) {
            Object value = null;
            switch(column) {
                case KEY_COLUMN: {
                    value = keys.get(row);
                    break;
                }
                case TYPE_COLUMN: {
                    Object key = keys.get(row);
                    Object defaultsValue = defaults.get(key);
                    value = defaultsValue != null? defaultsValue.getClass().getName() : "";
                    break;
                }
                case VALUE_COLUMN: {
                    value = defaults.get(keys.get(row));
                }
            }
            return value;
        }
        
    }
    
    private static class RowRenderer extends JLabel implements TableCellRenderer {
        private final Color[] rowColors;
        private final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1); 
        
        public RowRenderer(Color colors[]) {
            rowColors = colors;
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            setText(value != null? value.toString() : "unknown");
            setIcon(null);
            setColors(table, row, isSelected);
            setBorder(this, hasFocus, isSelected);
            return this;
        }
        
        protected void setColors(JTable table, int row, boolean isSelected) {
            if (!isSelected) {
                setBackground(rowColors[row % rowColors.length]);
                setForeground(table.getForeground());
            } else {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
        }
        
        protected void setBorder(JComponent renderer, boolean hasFocus, boolean isSelected) {
        
            if (hasFocus) {
                Border border = null;
                if (isSelected) {
                    border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
                }
                if (border == null) {
                    border = UIManager.getBorder("Table.focusCellHighlightBorder");
                }
                renderer.setBorder(border);
                                
            } else {
                renderer.setBorder(noFocusBorder);
            }
        }
    }
    
    private static class KeyRenderer extends RowRenderer {
        
        public KeyRenderer(Color colors[]) {
            super(colors);
            setFont(getFont().deriveFont(Font.BOLD));
        }
    } 
    
    private static class ValueRenderer extends RowRenderer {
        private final JButton buttonIconRenderer;
        private final JRadioButton radioIconRenderer;
        private final JMenuItem menuItemIconRenderer;
        private final JCheckBox checkboxIconRenderer;
        
        public ValueRenderer(Color colors[]) {
            super(colors);
            buttonIconRenderer = new JButton();
            buttonIconRenderer.setBorderPainted(false);
            buttonIconRenderer.setContentAreaFilled(false);
            buttonIconRenderer.setText("(for AbstractButtons only)");
            radioIconRenderer = new JRadioButton();
            radioIconRenderer.setBorderPainted(false);
            radioIconRenderer.setText("for JRadioButtons only)");
            checkboxIconRenderer = new JCheckBox();
            checkboxIconRenderer.setBorderPainted(false);
            checkboxIconRenderer.setText("for JCheckBoxes only)");
            menuItemIconRenderer = new JMenuItem();
            menuItemIconRenderer.setBorderPainted(false);
            menuItemIconRenderer.setText("(for JMenuItems only)");
            
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JComponent renderer = null;
            
            if (value instanceof Icon) {
               Icon icon = (Icon)value;
               // Hack to fix problem where some plaf Icons cast the component to
               // various component classes. yikes!  not very re-usable icons :(
               String className = (String)table.getValueAt(row, UIDefaultsTableModel.TYPE_COLUMN);
               if (className.contains("BasicIconFactory$RadioButtonMenuItemIcon") ||
                   className.contains("BasicIconFactory$CheckBoxMenuItemIcon") ||
                   className.contains("OceanTheme$IFIcon") ||
                   className.contains("MotifIconFactory$RadioButtonIcon") ||
                   className.contains("MotifIconFactory$CheckBoxIcon") ||
                   className.contains("MotifIconFactory$MenuArrowIcon") ||
                   className.contains("WindowsIconFactory$FrameButtonIcon") ||
                   className.contains("WindowsIconFactory$RadioButtonIcon")) {
                   buttonIconRenderer.setIcon(icon);
                   buttonIconRenderer.setSelected(true);
                   renderer = buttonIconRenderer;
                   
               } else if (className.contains("MetalIconFactory$RadioButtonIcon")) {
                   radioIconRenderer.setIcon(icon);
                   renderer = radioIconRenderer;
                   
               } else if (className.contains("MetalIconFactory$RadioButtonMenuItemIcon") ||
                          className.contains("MetalIconFactory$CheckBoxMenuItemIcon") ||
                          className.contains("MetalIconFactory$MenuArrowIcon") ||
                          className.contains("MetalIconFactory$MenuItemArrowIcon")) {
                   menuItemIconRenderer.setIcon(icon);
                   menuItemIconRenderer.setSelected(true);
                   renderer = menuItemIconRenderer;
                   
               } else if (className.contains("MetalIconFactory$CheckBoxIcon") ||
                       className.contains("WindowsIconFactory$CheckBoxIcon")) {
                   checkboxIconRenderer.setIcon(icon);
                   checkboxIconRenderer.setSelected(true);
                   renderer = checkboxIconRenderer;
               } 
            }
            
            if (renderer != null) {
                // special hack renderer for icons needs to be colorized because
                // it doesn't extend RowRenderer
                setColors(table, row, isSelected);
                setBorder(renderer, hasFocus, isSelected);
            
            } else {
                // renderer == this
                renderer = (JComponent)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Color) {
                    Color color = (Color)value;
                    float[] hsb = Color.RGBtoHSB(color.getRed(), 
                            color.getGreen(), color.getBlue(), null);
                    
                    setIcon(ColorIcon.getIcon(color));
                    setText("RGB=" + color.getRed() + ","+ color.getGreen() + ","+ color.getBlue() + "  " +
                            "HSB=" + String.format("%.0f%n",hsb[0]*360) + "," + 
                            String.format("%.3f%n",hsb[1]) + "," + 
                            String.format("%.3f%n", hsb[2]));
                    
                } else if (value instanceof Font) {
                    Font font = (Font)value;
                    setFont(font);
                    setText(font.getFontName() + " size="+ font.getSize2D());
                    setIcon(null);
                    
                } else if (value instanceof Icon) {
                    setIcon((Icon)value);
                    setText("");
                }
            }
           return renderer;
        }
            
        @Override
        protected void paintComponent(Graphics g) {
            try {
                super.paintComponent(g);
            } catch (ClassCastException ex) {
                System.out.println("cast exception:" + ex.getMessage());
            }
        }
    }
        
    private static class ColorIcon implements Icon {
        private static HashMap<Color,ColorIcon> icons;
    
        private Color color;
        private int size = DefaultsDisplay.rowHeight - 3;
        
        public static ColorIcon getIcon(Color color) {
            if (icons == null) {
                icons = new HashMap<Color,ColorIcon>();
            }
            ColorIcon icon = icons.get(color);
            if (icon == null) {
                icon = new ColorIcon(color);
                icons.put(color, icon);
            }
            return icon;
        }
    
        protected ColorIcon(Color color) {
            this.color = color;
        }
    
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, size, size);
            g.setColor(Color.black);
            g.drawRect(x, y, size, size);
        }        
        
        public int getIconWidth() {
            return size;
        }
        
        public int getIconHeight() {
            return size;
        }        
    }    
}

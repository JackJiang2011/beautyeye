package org.jb2011.lnf.beautyeye.winlnfutils.d;
///*
// * @(#)XPStyle.java	1.28 07/01/09
// *
// * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
// * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// */
//
///*
// * <p>These classes are designed to be used while the
// * corresponding <code>LookAndFeel</code> class has been installed
// * (<code>UIManager.setLookAndFeel(new <i>XXX</i>LookAndFeel())</code>).
// * Using them while a different <code>LookAndFeel</code> is installed
// * may produce unexpected results, including exceptions.
// * Additionally, changing the <code>LookAndFeel</code>
// * maintained by the <code>UIManager</code> without updating the
// * corresponding <code>ComponentUI</code> of any
// * <code>JComponent</code>s may also produce unexpected results,
// * such as the wrong colors showing up, and is generally not
// * encouraged.
// * 
// */
//
//package org.jb2011.lnf.beautyeye.winlnfutils;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.GraphicsConfiguration;
//import java.awt.Image;
//import java.awt.Insets;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.awt.image.DataBufferInt;
//import java.awt.image.WritableRaster;
//import java.security.AccessController;
//import java.util.HashMap;
//
//import javax.swing.AbstractButton;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JRadioButton;
//import javax.swing.JToolBar;
//import javax.swing.UIManager;
//import javax.swing.border.AbstractBorder;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import javax.swing.plaf.ColorUIResource;
//import javax.swing.plaf.InsetsUIResource;
//import javax.swing.plaf.UIResource;
//import javax.swing.text.JTextComponent;
//
//import org.jb2011.lnf.beautyeye.winlnfutils.BETMSchema.Part;
//import org.jb2011.lnf.beautyeye.winlnfutils.BETMSchema.Prop;
//import org.jb2011.lnf.beautyeye.winlnfutils.BETMSchema.State;
//import org.jb2011.lnf.beautyeye.winlnfutils.BETMSchema.TypeEnum;
//
//import sun.awt.windows.ThemeReader;
//import sun.security.action.GetPropertyAction;
//import sun.swing.CachedPainter;
//
//import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
//
///*
// * 本类实际就是XP主题包中的类，未作任何改变.
// * 代码参考java源码，仅作兼容性修改
// * Add by js 2009-09-01.
// */
///**
// * Implements Windows XP Styles for the Windows Look and Feel.
// *
// * @version 1.28 01/09/07
// * @author Leif Samuelsson
// */
//public class BEXPStyle {
//    // Singleton instance of this class
//    private static BEXPStyle xp;
//
//    // Singleton instance of SkinPainter
//    private static SkinPainter skinPainter = new SkinPainter();
//
//    private static Boolean themeActive = null;
//
//    private HashMap<String, Border> borderMap;
//    private HashMap<String, Color>  colorMap;
//
//    private boolean flatMenus;
//
//    static {
//        invalidateStyle();
//    }
//
//    /** Static method for clearing the hashmap and loading the
//     * current XP style and theme
//     */
//    static synchronized void invalidateStyle() {
//        xp = null;
//        themeActive = null;
//    }
//
//    /** Get the singleton instance of this class
//     *
//     * @return the singleton instance of this class or null if XP styles
//     * are not active or if this is not Windows XP
//     */
//    public static synchronized BEXPStyle getXP() {
//        if (themeActive == null) {
//            Toolkit toolkit = Toolkit.getDefaultToolkit();
//            themeActive =
//                (Boolean)toolkit.getDesktopProperty("win.xpstyle.themeActive");
//            if (themeActive == null) {
//                themeActive = Boolean.FALSE;
//            }
//            if (themeActive.booleanValue()) {
//                GetPropertyAction propertyAction =
//                    new GetPropertyAction("swing.noxp");
//                if (AccessController.doPrivileged(propertyAction) == null &&
//                    ThemeReader.isThemed() &&
//                    !(UIManager.getLookAndFeel()
//                      instanceof WindowsClassicLookAndFeel)) {
//
//                    xp = new BEXPStyle();
//                }
//            }
//        }
//        return xp;
//    }
//
//
//
//    /** Get a named <code>String</code> value from the current style
//     *
//     * @param part a <code>Part</code>
//     * @param state a <code>String</code>
//     * @param attributeKey a <code>String</code>
//     * @return a <code>String</code> or null if key is not found
//     *    in the current style
//     *
//     * This is currently only used by WindowsInternalFrameTitlePane for painting
//     * title foregound and can be removed when no longer needed
//     */
//    String getString(Component c, Part part, State state, Prop prop) {
//        return getTypeEnumName(c, part, state, prop);
//    }
//
//    private static String getTypeEnumName(Component c, Part part, State state, Prop prop) {
//        int enumValue = ThemeReader.getEnum(part.getControlName(c), part.getValue(),
//                                            State.getValue(part, state),
//                                            prop.getValue());
//        if (enumValue == -1) {
//            return null;
//        }
//        return TypeEnum.getTypeEnum(prop, enumValue).getName();
//    }
//
//
//
//
//    /** Get a named <code>int</code> value from the current style
//     *
//     * @param part a <code>Part</code>
//     * @return an <code>int</code> or null if key is not found
//     *    in the current style
//     */
//    int getInt(Component c, Part part, State state, Prop prop, int fallback) {
//        return ThemeReader.getInt(part.getControlName(c), part.getValue(),
//                                  State.getValue(part, state),
//                                  prop.getValue());
//    }
//
//    /** Get a named <code>Dimension</code> value from the current style
//     *
//     * @param key a <code>String</code>
//     * @return a <code>Dimension</code> or null if key is not found
//     *    in the current style
//     *
//     * This is currently only used by WindowsProgressBarUI and the value
//     * should probably be cached there instead of here.
//     */
//    Dimension getDimension(Component c, Part part, State state, Prop prop) {
//        return ThemeReader.getPosition(part.getControlName(c), part.getValue(),
//                                       State.getValue(part, state),
//                                       prop.getValue());
//    }
//
//    /** Get a named <code>Point</code> (e.g. a location or an offset) value
//     *  from the current style
//     *
//     * @param key a <code>String</code>
//     * @return a <code>Point</code> or null if key is not found
//     *    in the current style
//     *
//     * This is currently only used by WindowsInternalFrameTitlePane for painting
//     * title foregound and can be removed when no longer needed
//     */
//    Point getPoint(Component c, Part part, State state, Prop prop) {
//        Dimension d = ThemeReader.getPosition(part.getControlName(c), part.getValue(),
//                                              State.getValue(part, state),
//                                              prop.getValue());
//        if (d != null) {
//            return new Point(d.width, d.height);
//        } else {
//            return null;
//        }
//    }
//
//    /** Get a named <code>Insets</code> value from the current style
//     *
//     * @param key a <code>String</code>
//     * @return an <code>Insets</code> object or null if key is not found
//     *    in the current style
//     *
//     * This is currently only used to create borders and by
//     * WindowsInternalFrameTitlePane for painting title foregound.
//     * The return value is already cached in those places.
//     */
//    Insets getMargin(Component c, Part part, State state, Prop prop) {
//        return ThemeReader.getThemeMargins(part.getControlName(c), part.getValue(),
//                                           State.getValue(part, state),
//                                           prop.getValue());
//    }
//
//
//    /** Get a named <code>Color</code> value from the current style
//     *
//     * @param part a <code>Part</code>
//     * @return a <code>Color</code> or null if key is not found
//     *    in the current style
//     */
//    synchronized Color getColor(Skin skin, Prop prop, Color fallback) {
//        String key = skin.toString() + "." + prop.name();
//        Part part = skin.part;
//        Color color = colorMap.get(key);
//        if (color == null) {
//            color = ThemeReader.getColor(part.getControlName(null), part.getValue(),
//                                         State.getValue(part, skin.state),
//                                         prop.getValue());
//            if (color != null) {
//                color = new ColorUIResource(color);
//                colorMap.put(key, color);
//            }
//        }
//        return (color != null) ? color : fallback;
//    }
//
//    public Color getColor(Component c, Part part, State state, Prop prop, Color fallback) {
//        return getColor(new Skin(c, part, state), prop, fallback);
//    }
//
//
//
//    /** Get a named <code>Border</code> value from the current style
//     *
//     * @param part a <code>Part</code>
//     * @return a <code>Border</code> or null if key is not found
//     *    in the current style or if the style for the particular
//     *    part is not defined as "borderfill".
//     */
//    public synchronized Border getBorder(Component c, Part part) {
//        if (part == Part.MENU) {
//            // Special case because XP has no skin for menus
//            if (flatMenus) {
//                // TODO: The classic border uses this color, but we should
//                // create a new UI property called "PopupMenu.borderColor"
//                // instead.
//                return new XPFillBorder(UIManager.getColor("InternalFrame.borderShadow"),
//                                        1);
//            } else {
//                return null;    // Will cause L&F to use classic border
//            }
//        }
//        Skin skin = new Skin(c, part, null);
//        Border border = borderMap.get(skin.string);
//        if (border == null) {
//            String bgType = getTypeEnumName(c, part, null, Prop.BGTYPE);
//            if ("borderfill".equalsIgnoreCase(bgType)) {
//                int thickness = getInt(c, part, null, Prop.BORDERSIZE, 1);
//                Color color = getColor(skin, Prop.BORDERCOLOR, Color.black);
//                border = new XPFillBorder(color, thickness);
//            } else if ("imagefile".equalsIgnoreCase(bgType)) {
//                Insets m = getMargin(c, part, null, Prop.SIZINGMARGINS);
//                if (m != null) {
//                    if (getBoolean(c, part, null, Prop.BORDERONLY)) {
//                        border = new XPImageBorder(c, part);
//                    } else {
//                        if(part == Part.TP_BUTTON) {
//                            border = new XPEmptyBorder(new Insets(3,3,3,3));
//                        } else {
//                            border = new XPEmptyBorder(m);
//                        }
//                    }
//                }
//            }
//            if (border != null) {
//                borderMap.put(skin.string, border);
//            }
//        }
//        return border;
//    }
//
//    private class XPFillBorder extends LineBorder implements UIResource {
//        XPFillBorder(Color color, int thickness) {
//            super(color, thickness);
//        }
//
//        public Insets getBorderInsets(Component c)       {
//            return getBorderInsets(c, new Insets(0,0,0,0));
//        }
//
//        public Insets getBorderInsets(Component c, Insets insets)       {
//            Insets margin = null;
//            //
//            // Ideally we'd have an interface defined for classes which
//            // support margins (to avoid this hackery), but we've
//            // decided against it for simplicity
//            //
//           if (c instanceof AbstractButton) {
//               margin = ((AbstractButton)c).getMargin();
//           } else if (c instanceof JToolBar) {
//               margin = ((JToolBar)c).getMargin();
//           } else if (c instanceof JTextComponent) {
//               margin = ((JTextComponent)c).getMargin();
//           }
//           insets.top    = (margin != null? margin.top : 0)    + thickness;
//           insets.left   = (margin != null? margin.left : 0)   + thickness;
//           insets.bottom = (margin != null? margin.bottom : 0) + thickness;
//           insets.right =  (margin != null? margin.right : 0)  + thickness;
//               
//           return insets;
//        }
//    }
//
//    private class XPImageBorder extends AbstractBorder implements UIResource {
//        Skin skin;
//
//        XPImageBorder(Component c, Part part) {
//            this.skin = getSkin(c, part);
//        }
//
//        public void paintBorder(Component c, Graphics g,
//                                int x, int y, int width, int height) {
//            skin.paintSkin(g, x, y, width, height, null);
//        }
//
//        public Insets getBorderInsets(Component c)       {
//            return getBorderInsets(c, new Insets(0,0,0,0));
//        }
//
//        public Insets getBorderInsets(Component c, Insets insets)       {
//            Insets margin = null;
//            Insets borderInsets = skin.getContentMargin();
//            //
//            // Ideally we'd have an interface defined for classes which
//            // support margins (to avoid this hackery), but we've
//            // decided against it for simplicity
//            //
//           if (c instanceof AbstractButton) {
//               margin = ((AbstractButton)c).getMargin();
//           } else if (c instanceof JToolBar) {
//               margin = ((JToolBar)c).getMargin();
//           } else if (c instanceof JTextComponent) {
//               margin = ((JTextComponent)c).getMargin();
//           }
//           insets.top    = (margin != null? margin.top : 0)    + borderInsets.top;
//           insets.left   = (margin != null? margin.left : 0)   + borderInsets.left;
//           insets.bottom = (margin != null? margin.bottom : 0) + borderInsets.bottom;
//           insets.right  = (margin != null? margin.right : 0)  + borderInsets.right;
//               
//           return insets;
//        }
//    }
//
//    private class XPEmptyBorder extends EmptyBorder implements UIResource {
//        XPEmptyBorder(Insets m) {
//            super(m.top+2, m.left+2, m.bottom+2, m.right+2);
//        }
//
//        public Insets getBorderInsets(Component c)       {
//            return getBorderInsets(c, getBorderInsets());
//        }
//
//        public Insets getBorderInsets(Component c, Insets insets)       {
//            insets = super.getBorderInsets(c, insets);
//                
//            Insets margin = null;
//            if (c instanceof AbstractButton) {
//                Insets m = ((AbstractButton)c).getMargin();
//                // if this is a toolbar button then ignore getMargin()
//                // and subtract the padding added by the constructor
//                if(c.getParent() instanceof JToolBar 
//                   && ! (c instanceof JRadioButton)
//                   && ! (c instanceof JCheckBox)
//                   && m instanceof InsetsUIResource) {
//                    insets.top -= 2;
//                    insets.left -= 2;
//                    insets.bottom -= 2;
//                    insets.right -= 2;
//                } else {
//                    margin = m;
//                }
//            } else if (c instanceof JToolBar) {
//                margin = ((JToolBar)c).getMargin();
//            } else if (c instanceof JTextComponent) {
//                margin = ((JTextComponent)c).getMargin();
//            }
//            if (margin != null) {
//                insets.top    = margin.top + 2;
//                insets.left   = margin.left + 2;
//                insets.bottom = margin.bottom + 2;
//                insets.right  = margin.right + 2;
//            }
//            return insets;
//        }
//    }
//
//    public boolean isSkinDefined(Component c, Part part) {
//        return (part.getValue() == 0) 
//            || ThemeReader.isThemePartDefined(
//                   part.getControlName(c), part.getValue(), 0);
//    }
//
//    /** Get a <code>Skin</code> object from the current style
//     * for a named part (component type)
//     *
//     * @param part a <code>Part</code>
//     * @return a <code>Skin</code> object
//     */
//    public synchronized Skin getSkin(Component c, Part part) {
//        assert isSkinDefined(c, part) : "part " + part + " is not defined"; 
//        return new Skin(c, part, null);
//    }
//
//
//
//
//    /** A class which encapsulates attributes for a given part
//     * (component type) and which provides methods for painting backgrounds
//     * and glyphs
//     */
//    public static class Skin {
//        final Component component;
//        final Part part;
//        final State state;
//
//        private final String string;
//        private Dimension size = null;
//
//        Skin(Component component, Part part) {
//            this(component, part, null);
//        }
//
//        Skin(Part part, State state) {
//            this(null, part, state);
//        }
//
//        Skin(Component component, Part part, State state) {
//            this.component = component;
//            this.part  = part;
//            this.state = state;
//
//            String str = part.getControlName(component) +"." + part.name();
//            if (state != null) {
//                str += "("+state.name()+")";
//            }
//            string = str;
//        }
//
//        public Insets getContentMargin() {
//            // This is only called by WindowsTableHeaderUI so far.
//            return ThemeReader.getThemeMargins(part.getControlName(null), part.getValue(),
//                                               0, Prop.SIZINGMARGINS.getValue());
//        }
//
//        private int getWidth(State state) {
//            if (size == null) {
//                size = getPartSize(part, state);
//            }
//            return size.width;
//        }
//
//        public int getWidth() {
//            return getWidth((state != null) ? state : State.NORMAL);
//        }
//
//        private int getHeight(State state) {
//            if (size == null) {
//                size = getPartSize(part, state);
//            }
//            return size.height;
//        }
//
//        public int getHeight() {
//            return getHeight((state != null) ? state : State.NORMAL);
//        }
//
//        public String toString() {
//            return string;
//        }
//
//        public boolean equals(Object obj) {
//            return (obj instanceof Skin && ((Skin)obj).string.equals(string));
//        }
//
//        public int hashCode() {
//            return string.hashCode();
//        }
//
//        /** Paint a skin at x, y.
//         *
//         * @param g   the graphics context to use for painting
//         * @param dx  the destination <i>x</i> coordinate.
//         * @param dy  the destination <i>y</i> coordinate.
//         * @param state which state to paint
//         */
//        public void paintSkin(Graphics g, int dx, int dy, State state) {
//            if (state == null) {
//                state = this.state;
//            }
//            paintSkin(g, dx, dy, getWidth(state), getHeight(state), state);
//        }
//
//        /** Paint a skin in an area defined by a rectangle.
//         *
//         * @param g the graphics context to use for painting
//         * @param r     a <code>Rectangle</code> defining the area to fill,
//         *                     may cause the image to be stretched or tiled
//         * @param state which state to paint
//         */
//        void paintSkin(Graphics g, Rectangle r, State state) {
//            paintSkin(g, r.x, r.y, r.width, r.height, state);
//        }
//
//        /** Paint a skin at a defined position and size
//         *
//         * @param g   the graphics context to use for painting
//         * @param dx  the destination <i>x</i> coordinate.
//         * @param dy  the destination <i>y</i> coordinate.
//         * @param dw  the width of the area to fill, may cause
//         *                  the image to be stretched or tiled
//         * @param dh  the height of the area to fill, may cause
//         *                  the image to be stretched or tiled
//         * @param state which state to paint
//         */
//        public void paintSkin(Graphics g, int dx, int dy, int dw, int dh, State state) {
//            skinPainter.paint(null, g, dx, dy, dw, dh, this, state);
//        }
//        /** 
//         * Paint a skin at a defined position and size
//         *
//         * @param g   the graphics context to use for painting
//         * @param dx  the destination <i>x</i> coordinate
//         * @param dy  the destination <i>y</i> coordinate
//         * @param dw  the width of the area to fill, may cause
//         *                  the image to be stretched or tiled
//         * @param dh  the height of the area to fill, may cause
//         *                  the image to be stretched or tiled
//         * @param state which state to paint
//         * @param borderFill should test if the component uses a border fill
//         *                 and skip painting if it is
//         */
//        void paintSkin(Graphics g, int dx, int dy, int dw, int dh, State state, 
//                boolean borderFill) {
//            if(borderFill && "borderfill".equals(getTypeEnumName(component, part, 
//                    state, Prop.BGTYPE))) {
//                return;
//            }
//            skinPainter.paint(null, g, dx, dy, dw, dh, this, state);
//        }
//    }
//
//    private static class SkinPainter extends CachedPainter {
//        SkinPainter() {
//            super(30);
//            flush();
//        }
//
//        protected void paintToImage(Component c, Image image, Graphics g,
//                                    int w, int h, Object[] args) {
//            Skin skin = (Skin)args[0];
//            Part part = skin.part;
//            State state = (State)args[1];
//            if (state == null) {
//                state = skin.state;
//            }
//            if (c == null) {
//                c = skin.component;
//            }
//            WritableRaster raster = ((BufferedImage)image).getRaster();
//            DataBufferInt buffer = (DataBufferInt)raster.getDataBuffer();
//            ThemeReader.paintBackground(buffer.getData(),
//                                        part.getControlName(c), part.getValue(),
//                                        State.getValue(part, state),
//                                        0, 0, w, h, w);
//        }
//
//        protected Image createImage(Component c, int w, int h,
//                                    GraphicsConfiguration config, Object[] args) {
//            return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        }
//    }
//
//    static class GlyphButton extends JButton {
//        private Skin skin;
//
//        public GlyphButton(Component parent, Part part) {
//            BEXPStyle xp = getXP();
//            skin = xp.getSkin(parent, part);
//            setBorder(null);
//            setContentAreaFilled(false);
//        }   
//
//        public boolean isFocusTraversable() {
//            return false;
//        }
//
//        protected State getState() { 
//            State state = State.NORMAL; 
//            if (!isEnabled()) {
//                state = State.DISABLED;
//            } else if (getModel().isPressed()) {
//                state = State.PRESSED;
//            } else if (getModel().isRollover()) {
//                state = State.HOT;
//            }
//            return state;
//        }
//
//        public void paintComponent(Graphics g) {
//            Dimension d = getSize();
//            skin.paintSkin(g, 0, 0, d.width, d.height, getState());
//        }
//
//        public void setPart(Component parent, Part part) { 
//            BEXPStyle xp = getXP(); 
//            skin = xp.getSkin(parent, part); 
//            revalidate(); 
//            repaint(); 
//        }
//
//        protected void paintBorder(Graphics g) {    
//        }
//
//        public Dimension getPreferredSize() {
//            return new Dimension(16, 16);
//        }
//
//        public Dimension getMinimumSize() {
//            return new Dimension(5, 5);
//        }
//
//        public Dimension getMaximumSize() {
//            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
//        }
//    }
//
//    // Private constructor
//    private BEXPStyle() {
//        flatMenus = getSysBoolean(Prop.FLATMENUS);
//
//        colorMap  = new HashMap<String, Color>();
//        borderMap = new HashMap<String, Border>();
//        // Note: All further access to the maps must be synchronized
//    }
//
//
//    private boolean getBoolean(Component c, Part part, State state, Prop prop) {
//        return ThemeReader.getBoolean(part.getControlName(c), part.getValue(),
//                                      State.getValue(part, state),
//                                      prop.getValue());
//    }
//
//    private static Dimension getPartSize(Part part, State state) {
//        return ThemeReader.getPartSize(part.getControlName(null), part.getValue(),
//                                       State.getValue(part, state));
//    }
//
//    private static boolean getSysBoolean(Prop prop) {
//         // We can use any widget name here, I guess.
//        return ThemeReader.getSysBoolean("window", prop.getValue());
//    }
//}

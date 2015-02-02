/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * ContrastTheme.java at 2015-2-1 20:25:41, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)ContrastTheme.java	1.11 05/11/17
 */

 
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.DefaultMetalTheme;

// TODO: Auto-generated Javadoc
/**
 * This class describes a higher-contrast Metal Theme.
 *
 * @version 1.11 11/17/05
 * @author Michael C. Albers
 */

public class ContrastTheme extends DefaultMetalTheme {

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getName()
     */
    public String getName() { return "Contrast"; }

    /** The primary1. */
    private final ColorUIResource primary1 = new ColorUIResource(0, 0, 0);
    
    /** The primary2. */
    private final ColorUIResource primary2 = new ColorUIResource(204, 204, 204);
    
    /** The primary3. */
    private final ColorUIResource primary3 = new ColorUIResource(255, 255, 255);
    
    /** The primary highlight. */
    private final ColorUIResource primaryHighlight = new ColorUIResource(102,102,102);

    /** The secondary2. */
    private final ColorUIResource secondary2 = new ColorUIResource(204, 204, 204);
    
    /** The secondary3. */
    private final ColorUIResource secondary3 = new ColorUIResource(255, 255, 255);
    
    /** The control highlight. */
    private final ColorUIResource controlHighlight = new ColorUIResource(102,102,102);

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getPrimary1()
     */
    protected ColorUIResource getPrimary1() { return primary1; } 
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getPrimary2()
     */
    protected ColorUIResource getPrimary2() { return primary2; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getPrimary3()
     */
    protected ColorUIResource getPrimary3() { return primary3; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getPrimaryControlHighlight()
     */
    public ColorUIResource getPrimaryControlHighlight() { return primaryHighlight;}

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getSecondary2()
     */
    protected ColorUIResource getSecondary2() { return secondary2; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getSecondary3()
     */
    protected ColorUIResource getSecondary3() { return secondary3; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getControlHighlight()
     */
    public ColorUIResource getControlHighlight() { return super.getSecondary3(); }

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getFocusColor()
     */
    public ColorUIResource getFocusColor() { return getBlack(); }

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getTextHighlightColor()
     */
    public ColorUIResource getTextHighlightColor() { return getBlack(); }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getHighlightedTextColor()
     */
    public ColorUIResource getHighlightedTextColor() { return getWhite(); }
  
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getMenuSelectedBackground()
     */
    public ColorUIResource getMenuSelectedBackground() { return getBlack(); }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getMenuSelectedForeground()
     */
    public ColorUIResource getMenuSelectedForeground() { return getWhite(); }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getAcceleratorForeground()
     */
    public ColorUIResource getAcceleratorForeground() { return getBlack(); }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getAcceleratorSelectedForeground()
     */
    public ColorUIResource getAcceleratorSelectedForeground() { return getWhite(); }


    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#addCustomEntriesToTable(javax.swing.UIDefaults)
     */
    public void addCustomEntriesToTable(UIDefaults table) {

        Border blackLineBorder = new BorderUIResource(new LineBorder( getBlack() ));

	Object textBorder = new BorderUIResource( new CompoundBorder(
						       blackLineBorder,
					               new BasicBorders.MarginBorder()));

        table.put( "ToolTip.border", blackLineBorder);
	table.put( "TitledBorder.border", blackLineBorder);

        table.put( "TextField.border", textBorder);
        table.put( "PasswordField.border", textBorder);
        table.put( "TextArea.border", textBorder);
        table.put( "TextPane.border", textBorder);
        table.put( "EditorPane.border", textBorder);


    }

}

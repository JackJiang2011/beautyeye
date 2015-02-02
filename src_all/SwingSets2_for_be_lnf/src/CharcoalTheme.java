/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * CharcoalTheme.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)CharcoalTheme.java	1.10 05/11/17
 */


import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

// TODO: Auto-generated Javadoc
/**
 * This class describes a theme using gray colors.
 *
 * 1.10 11/17/05
 * @author Steve Wilson
 */
public class CharcoalTheme extends DefaultMetalTheme {

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getName()
     */
    public String getName() { return "Charcoal"; }

    /** The primary1. */
    private final ColorUIResource primary1 = new ColorUIResource(66, 33, 66);
    
    /** The primary2. */
    private final ColorUIResource primary2 = new ColorUIResource(90, 86, 99);
    
    /** The primary3. */
    private final ColorUIResource primary3 = new ColorUIResource(99, 99, 99);

    /** The secondary1. */
    private final ColorUIResource secondary1 = new ColorUIResource(0, 0, 0);
    
    /** The secondary2. */
    private final ColorUIResource secondary2 = new ColorUIResource(51, 51, 51);
    
    /** The secondary3. */
    private final ColorUIResource secondary3 = new ColorUIResource(102, 102, 102);

    /** The black. */
    private final ColorUIResource black = new ColorUIResource(222, 222, 222);
    
    /** The white. */
    private final ColorUIResource white = new ColorUIResource(0, 0, 0);

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
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getSecondary1()
     */
    protected ColorUIResource getSecondary1() { return secondary1; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getSecondary2()
     */
    protected ColorUIResource getSecondary2() { return secondary2; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.DefaultMetalTheme#getSecondary3()
     */
    protected ColorUIResource getSecondary3() { return secondary3; }

    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getBlack()
     */
    protected ColorUIResource getBlack() { return black; }
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getWhite()
     */
    protected ColorUIResource getWhite() { return white; }

}

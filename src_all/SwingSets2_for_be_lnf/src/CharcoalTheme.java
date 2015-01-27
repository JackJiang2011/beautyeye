/*
 * @(#)CharcoalTheme.java	1.10 05/11/17
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
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

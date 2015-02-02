/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * DemoModule.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)DemoModule.java	1.23 05/11/17
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

// TODO: Auto-generated Javadoc
/**
 * A generic SwingSet2 demo module.
 *
 * @version 1.23 11/17/05
 * @author Jeff Dinkins
 */
public class DemoModule extends JApplet {

    // The preferred size of the demo
    /** The PREFERRE d_ width. */
    private int PREFERRED_WIDTH = 680;
    
    /** The PREFERRE d_ height. */
    private int PREFERRED_HEIGHT = 600;

    /** The lowered border. */
    Border loweredBorder = //new CompoundBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED), 
					      new EmptyBorder(15,10,5,10);//);

    // Premade convenience dimensions, for use wherever you need 'em.
    /** The HGA p2. */
    public static Dimension HGAP2 = new Dimension(2,1);
    
    /** The VGA p2. */
    public static Dimension VGAP2 = new Dimension(1,2);

    /** The HGA p5. */
    public static Dimension HGAP5 = new Dimension(5,1);
    
    /** The VGA p5. */
    public static Dimension VGAP5 = new Dimension(1,5);
    
    /** The HGA p10. */
    public static Dimension HGAP10 = new Dimension(10,1);
    
    /** The VGA p10. */
    public static Dimension VGAP10 = new Dimension(1,10);

    /** The HGA p15. */
    public static Dimension HGAP15 = new Dimension(15,1);
    
    /** The VGA p15. */
    public static Dimension VGAP15 = new Dimension(1,15);
    
    /** The HGA p20. */
    public static Dimension HGAP20 = new Dimension(20,1);
    
    /** The VGA p20. */
    public static Dimension VGAP20 = new Dimension(1,20);

    /** The HGA p25. */
    public static Dimension HGAP25 = new Dimension(25,1);
    
    /** The VGA p25. */
    public static Dimension VGAP25 = new Dimension(1,25);

    /** The HGA p30. */
    public static Dimension HGAP30 = new Dimension(30,1);
    
    /** The VGA p30. */
    public static Dimension VGAP30 = new Dimension(1,30);
	
    /** The swingset. */
    private SwingSet2 swingset = null;
    
    /** The panel. */
    private JPanel panel = null;
    
    /** The resource name. */
    private String resourceName = null;
    
    /** The icon path. */
    private String iconPath = null;
    
    /** The source code. */
    private String sourceCode = null;

    // Resource bundle for internationalized and accessible text
    /** The bundle. */
    private ResourceBundle bundle = null;

    /**
     * Instantiates a new demo module.
     *
     * @param swingset the swingset
     */
    public DemoModule(SwingSet2 swingset) {
	this(swingset, null, null);
    }

    /**
     * Instantiates a new demo module.
     *
     * @param swingset the swingset
     * @param resourceName the resource name
     * @param iconPath the icon path
     */
    public DemoModule(SwingSet2 swingset, String resourceName, String iconPath) {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
	panel = new JPanel();
	
	panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
	panel.setLayout(new BorderLayout());

	this.resourceName = resourceName;
	this.iconPath = iconPath;
	this.swingset = swingset;

	loadSourceCode();
    }

    /**
     * Gets the resource name.
     *
     * @return the resource name
     */
    public String getResourceName() {
	return resourceName;
    }

    /**
     * Gets the demo panel.
     *
     * @return the demo panel
     */
    public JPanel getDemoPanel() {
	return panel;
    }

    /**
     * Gets the swing set2.
     *
     * @return the swing set2
     */
    public SwingSet2 getSwingSet2() {
	return swingset;
    }


    /**
     * Gets the string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
	String value = "nada";
	if(bundle == null) {
	    if(getSwingSet2() != null) {
		bundle = getSwingSet2().getResourceBundle();
	    } else {
		bundle = ResourceBundle.getBundle("resources.swingset");
	    }
	}
	try {
	    value = bundle.getString(key);
	} catch (MissingResourceException e) {
	    System.out.println("java.util.MissingResourceException: Couldn't find value for: " + key);
	}
	return value;
    }

    /**
     * Gets the mnemonic.
     *
     * @param key the key
     * @return the mnemonic
     */
    public char getMnemonic(String key) {
	return (getString(key)).charAt(0);
    }

    /**
     * Creates the image icon.
     *
     * @param filename the filename
     * @param description the description
     * @return the image icon
     */
    public ImageIcon createImageIcon(String filename, String description) {
	if(getSwingSet2() != null) {
	    return getSwingSet2().createImageIcon(filename, description);
	} else {
	    String path = "/resources/images/" + filename;
	    return new ImageIcon(getClass().getResource(path), description); 
	}
    }
    

    /**
     * Gets the source code.
     *
     * @return the source code
     */
    public String getSourceCode() {
	return sourceCode;
    }

    /**
     * Load source code.
     */
    public void loadSourceCode() {
	if(getResourceName() != null) {
	    String filename = getResourceName() + ".java";
	    sourceCode = new String("<html><body bgcolor=\"#ffffff\"><pre>");
	    InputStream is;
	    InputStreamReader isr;
	    CodeViewer cv = new CodeViewer();
	    URL url;
	    
	    try {
		url = getClass().getResource(filename); 
		is = url.openStream();
		isr = new InputStreamReader(is, "UTF-8");
		BufferedReader reader = new BufferedReader(isr);
		
		// Read one line at a time, htmlize using super-spiffy
		// html java code formating utility from www.CoolServlets.com
		String line = reader.readLine();
		while(line != null) {
		    sourceCode += cv.syntaxHighlight(line) + " \n ";
		    line = reader.readLine();
		}
		sourceCode += new String("</pre></body></html>");
            } catch (Exception ex) {
                sourceCode = "Could not load file: " + filename;
            }
	}
    }

    /* (non-Javadoc)
     * @see java.awt.Component#getName()
     */
    public String getName() {
	return getString(getResourceName() + ".name");
    };

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    public Icon getIcon() {
	return createImageIcon(iconPath, getResourceName() + ".name");
    };

    /**
     * Gets the tool tip.
     *
     * @return the tool tip
     */
    public String getToolTip() {
	return getString(getResourceName() + ".tooltip");
    };

    /**
     * Main impl.
     */
    public void mainImpl() {
	JFrame frame = new JFrame(getName());
        frame.getContentPane().setLayout(new BorderLayout());
	frame.getContentPane().add(getDemoPanel(), BorderLayout.CENTER);
	getDemoPanel().setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
	frame.pack();
	frame.show();
    }

    /**
     * Creates the horizontal panel.
     *
     * @param threeD the three d
     * @return the j panel
     */
    public JPanel createHorizontalPanel(boolean threeD) {
        JPanel p = N9ComponentFactory.createPanel_style1(null).setDrawBg(threeD);//modified by jb2011
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setAlignmentY(TOP_ALIGNMENT);
        p.setAlignmentX(LEFT_ALIGNMENT);
        if(threeD) {
            p.setBorder(loweredBorder);
        }
        //因背景是白色N9图，这里设置它不填充默认背景好看一点，要不然灰色背景出来就不好看了
        p.setOpaque(false);//add by jb2011 2012-08-24
        return p;
    }
    
    /**
     * Creates the vertical panel.
     *
     * @param threeD the three d
     * @return the j panel
     */
    public JPanel createVerticalPanel(boolean threeD) {
    	JPanel p = N9ComponentFactory.createPanel_style1(null).setDrawBg(threeD);//modified by jb2011
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentY(TOP_ALIGNMENT);
        p.setAlignmentX(LEFT_ALIGNMENT);
        if(threeD) {
            p.setBorder(loweredBorder);
        }
        return p;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	DemoModule demo = new DemoModule(null);
	demo.mainImpl();
    }

    /* (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    public void init() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getDemoPanel(), BorderLayout.CENTER);
    }
    
    /**
     * Update drag enabled.
     *
     * @param dragEnabled the drag enabled
     */
    void updateDragEnabled(boolean dragEnabled) {}
    
    
}


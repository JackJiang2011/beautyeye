/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * TreeDemo.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)TreeDemo.java	1.13 05/11/17
 */


import java.awt.BorderLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

// TODO: Auto-generated Javadoc
/**
 * JTree Demo.
 *
 * @version 1.13 11/17/05
 * @author Jeff Dinkins
 */
public class TreeDemo extends DemoModule {

    /** The tree. */
    JTree tree;

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	TreeDemo demo = new TreeDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "树";
    };

    /**
     * TreeDemo Constructor.
     *
     * @param swingset the swingset
     */
    public TreeDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "TreeDemo", "toolbar/JTree.gif");

	getDemoPanel().add(createTree(), BorderLayout.CENTER);
    }
 
    /**
     * Creates the tree.
     *
     * @return the j component
     */
    public JComponent createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(getString("TreeDemo.music"));
        DefaultMutableTreeNode catagory = null ;
	DefaultMutableTreeNode artist = null;
	DefaultMutableTreeNode record = null;

	// open tree data 
	URL url = getClass().getResource("/resources/tree.txt");

	try {
	    // convert url to buffered string
	    InputStream is = url.openStream();
	    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	    BufferedReader reader = new BufferedReader(isr);

	    // read one line at a time, put into tree
	    String line = reader.readLine();
	    while(line != null) {
		// System.out.println("reading in: ->" + line + "<-");
		char linetype = line.charAt(0);
		switch(linetype) {
		   case 'C':
		     catagory = new DefaultMutableTreeNode(line.substring(2));
		     top.add(catagory);
		     break;
		   case 'A':
		     if(catagory != null) {
		         catagory.add(artist = new DefaultMutableTreeNode(line.substring(2)));
		     }
		     break;
		   case 'R':
		     if(artist != null) {
		         artist.add(record = new DefaultMutableTreeNode(line.substring(2)));
		     }
		     break;
		   case 'S':
		     if(record != null) {
		         record.add(new DefaultMutableTreeNode(line.substring(2)));
		     }
		     break;
		   default:
		     break;
		}
		line = reader.readLine();
	    }
	} catch (IOException e) {
	}

	tree = new JTree(top) {
	    public Insets getInsets() {
		return new Insets(5,5,5,5);
	    }
	};
//	tree.setBackground(new Color(250,250,250));
        
        tree.setEditable(true);
            
	return new JScrollPane(tree);
//        return tree;
    }
    
    /* (non-Javadoc)
     * @see DemoModule#updateDragEnabled(boolean)
     */
    void updateDragEnabled(boolean dragEnabled) {
        tree.setDragEnabled(dragEnabled);
    }

}

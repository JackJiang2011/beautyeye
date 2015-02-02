/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * ExampleFileView.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)ExampleFileView.java	1.11 05/11/30
 */

import java.io.File;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.filechooser.FileView;

// TODO: Auto-generated Javadoc
/**
 * A convenience implementation of the FileView interface that
 * manages name, icon, traversable, and file type information.
 *
 * This this implemention will work well with file systems that use
 * "dot" extensions to indicate file type. For example: "picture.gif"
 * as a gif image.
 *
 * If the java.io.File ever contains some of this information, such as
 * file type, icon, and hidden file inforation, this implementation may
 * become obsolete. At minimum, it should be rewritten at that time to
 * use any new type information provided by java.io.File
 *
 * Example:
 *    JFileChooser chooser = new JFileChooser();
 *    fileView = new ExampleFileView();
 *    fileView.putIcon("jpg", new ImageIcon("images/jpgIcon.jpg"));
 *    fileView.putIcon("gif", new ImageIcon("images/gifIcon.gif"));
 *    chooser.setFileView(fileView);
 *
 * @version 1.11 11/30/05
 * @author Jeff Dinkins
 */
public class ExampleFileView extends FileView {
    
    /** The icons. */
    private Hashtable icons = new Hashtable(5);
    
    /** The file descriptions. */
    private Hashtable fileDescriptions = new Hashtable(5);
    
    /** The type descriptions. */
    private Hashtable typeDescriptions = new Hashtable(5);

    /**
     * The name of the file.  Do nothing special here. Let
     * the system file view handle this.
     *
     * @param f the f
     * @return the name
     * @see FileView#getName
     */
    public String getName(File f) {
	return null;
    }

    /**
     * Adds a human readable description of the file.
     *
     * @param f the f
     * @param fileDescription the file description
     */
    public void putDescription(File f, String fileDescription) {
	fileDescriptions.put(f, fileDescription);
    }

    /**
     * A human readable description of the file.
     *
     * @param f the f
     * @return the description
     * @see FileView#getDescription
     */
    public String getDescription(File f) {
	return (String) fileDescriptions.get(f);
    };

    /**
     * Adds a human readable type description for files. Based on "dot"
     * extension strings, e.g: ".gif". Case is ignored.
     *
     * @param extension the extension
     * @param typeDescription the type description
     */
    public void putTypeDescription(String extension, String typeDescription) {
	typeDescriptions.put(extension, typeDescription);
    }

    /**
     * Adds a human readable type description for files of the type of
     * the passed in file. Based on "dot" extension strings, e.g: ".gif".
     * Case is ignored.
     *
     * @param f the f
     * @param typeDescription the type description
     */
    public void putTypeDescription(File f, String typeDescription) {
	putTypeDescription(getExtension(f), typeDescription);
    }

    /**
     * A human readable description of the type of the file.
     *
     * @param f the f
     * @return the type description
     * @see FileView#getTypeDescription
     */
    public String getTypeDescription(File f) {
	return (String) typeDescriptions.get(getExtension(f));
    }

    /**
     * Convenience method that returns the "dot" extension for the
     * given file.
     *
     * @param f the f
     * @return the extension
     */
    public String getExtension(File f) {
	String name = f.getName();
	if(name != null) {
	    int extensionIndex = name.lastIndexOf('.');
	    if(extensionIndex < 0) {
		return null;
	    }
	    return name.substring(extensionIndex+1).toLowerCase();
	}
	return null;
    }

    /**
     * Adds an icon based on the file type "dot" extension
     * string, e.g: ".gif". Case is ignored.
     *
     * @param extension the extension
     * @param icon the icon
     */
    public void putIcon(String extension, Icon icon) {
	icons.put(extension, icon);
    }

    /**
     * Icon that reperesents this file. Default implementation returns
     * null. You might want to override this to return something more
     * interesting.
     *
     * @param f the f
     * @return the icon
     * @see FileView#getIcon
     */
    public Icon getIcon(File f) {
	Icon icon = null;
	String extension = getExtension(f);
	if(extension != null) {
	    icon = (Icon) icons.get(extension);
	}
	return icon;
    }

    /**
     * Whether the directory is traversable or not. Generic implementation
     * returns true for all directories and special folders.
     * 
     * You might want to subtype ExampleFileView to do somethimg more interesting,
     * such as recognize compound documents directories; in such a case you might
     * return a special icon for the directory that makes it look like a regular
     * document, and return false for isTraversable to not allow users to
     * descend into the directory.
     *
     * @param f the f
     * @return the boolean
     * @see FileView#isTraversable
     */
    public Boolean isTraversable(File f) {
	// if (some_reason) {
	//    return Boolean.FALSE;
	// }
	return null;	// Use default from FileSystemView
    };

}

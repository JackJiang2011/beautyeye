/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * FileChooserDemo.java at 2015-2-1 20:25:36, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)FileChooserDemo.java	1.18 06/02/03
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

// TODO: Auto-generated Javadoc
/**
 * JFileChooserDemo.
 *
 * @version 1.18 02/03/06
 * @author Jeff Dinkins
 */
public class FileChooserDemo extends DemoModule {
    
    /** The image. */
    JLabel theImage;
    
    /** The jpg icon. */
    Icon jpgIcon; 
    
    /** The gif icon. */
    Icon gifIcon;

    /**
     * main method allows us to run as a standalone demo.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
	FileChooserDemo demo = new FileChooserDemo(null);
	demo.mainImpl();
    }
    
    /* (non-Javadoc)
     * @see DemoModule#getName()
     */
    @Override public String getName() {
    	return "文件窗";
    };

    /**
     * FileChooserDemo Constructor.
     *
     * @param swingset the swingset
     */
    public FileChooserDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "FileChooserDemo"
			, "toolbar/JFileChooser.gif");
	createFileChooserDemo();
    }

    /**
     * Creates the file chooser demo.
     */
    public void createFileChooserDemo() {
	theImage = new JLabel("");
	jpgIcon = createImageIcon("filechooser/jpgIcon.jpg", "jpg image");
	gifIcon = createImageIcon("filechooser/gifIcon.gif", "gif image");

	JPanel demoPanel = getDemoPanel();
	demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));

	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

	demoPanel.add(Box.createRigidArea(VGAP10));
	demoPanel.add(innerPanel);
	demoPanel.add(Box.createRigidArea(VGAP10));

	innerPanel.add(Box.createRigidArea(HGAP20));

	// Create a panel to hold buttons
	JPanel buttonPanel = new JPanel() {
	    public Dimension getMaximumSize() {
		return new Dimension(getPreferredSize().width, super.getMaximumSize().height);
	    }
	};
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

	buttonPanel.add(Box.createRigidArea(VGAP15));
	buttonPanel.add(createPlainFileChooserButton());
	buttonPanel.add(Box.createRigidArea(VGAP15));
	JButton btn = createPreviewFileChooserButton();
	btn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
	btn.setForeground(Color.white);
	buttonPanel.add(btn);
	buttonPanel.add(Box.createRigidArea(VGAP15));
	JButton btn2 = createCustomFileChooserButton();
	btn2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
	btn2.setForeground(Color.white);
	buttonPanel.add(btn2);
	buttonPanel.add(Box.createVerticalGlue());

	// Create a panel to hold the image
	JPanel imagePanel = new JPanel();//* comment by jb2011
//	JPanel imagePanel = N9ComponentFactory.createPanel_style1(new Insets(18,10,10,21));//* add by jb2011
	imagePanel.setLayout(new BorderLayout());
//	imagePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));//* comment by jb2011
	JScrollPane scroller = new JScrollPane(theImage);
        scroller.getVerticalScrollBar().setUnitIncrement(10);
        scroller.getHorizontalScrollBar().setUnitIncrement(10);
//        scroller.setBorder(null);
//        scroller.setBackground(Color.red);
	imagePanel.add(scroller, BorderLayout.CENTER);

	// add buttons and image panels to inner panel
	innerPanel.add(buttonPanel);
	innerPanel.add(Box.createRigidArea(HGAP30));
	innerPanel.add(imagePanel);
	innerPanel.add(Box.createRigidArea(HGAP20));
    }

    /**
     * Creates the file chooser.
     *
     * @return the j file chooser
     */
    public JFileChooser createFileChooser() {
	// create a filechooser
	JFileChooser fc = new JFileChooser();
        if (getSwingSet2() != null && getSwingSet2().isDragEnabled()) {
            fc.setDragEnabled(true);
        }
	
	// set the current directory to be the images directory
	File swingFile = new File("resources/images/About.jpg");
	if(swingFile.exists()) {
	    fc.setCurrentDirectory(swingFile);
	    fc.setSelectedFile(swingFile);
	}
	
	return fc;
    }


    /**
     * Creates the plain file chooser button.
     *
     * @return the j button
     */
    public JButton createPlainFileChooserButton() {
	Action a = new AbstractAction(getString("FileChooserDemo.plainbutton")) {
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = createFileChooser();

		// show the filechooser
		int result = fc.showOpenDialog(getDemoPanel());
		
		// if we selected an image, load the image
		if(result == JFileChooser.APPROVE_OPTION) {
		    loadImage(fc.getSelectedFile().getPath());
		}
	    }
	};
	return createButton(a);
    }

    /**
     * Creates the preview file chooser button.
     *
     * @return the j button
     */
    public JButton createPreviewFileChooserButton() {
	Action a = new AbstractAction(getString("FileChooserDemo.previewbutton")) {
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = createFileChooser();

		// Add filefilter & fileview
                javax.swing.filechooser.FileFilter filter = createFileFilter(
                    getString("FileChooserDemo.filterdescription"),
                    "jpg", "gif");
		ExampleFileView fileView = new ExampleFileView();
		fileView.putIcon("jpg", jpgIcon);
		fileView.putIcon("gif", gifIcon);
		fc.setFileView(fileView);
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		
		// add preview accessory
		fc.setAccessory(new FilePreviewer(fc));

		// show the filechooser
		int result = fc.showOpenDialog(getDemoPanel());
		
		// if we selected an image, load the image
		if(result == JFileChooser.APPROVE_OPTION) {
		    loadImage(fc.getSelectedFile().getPath());
		}
	    }
	};
	return createButton(a);
    }

    /** The dialog. */
    JDialog dialog;
    
    /** The fc. */
    JFileChooser fc;

    /**
     * Creates the file filter.
     *
     * @param description the description
     * @param extensions the extensions
     * @return the javax.swing.filechooser. file filter
     */
    private javax.swing.filechooser.FileFilter createFileFilter(
            String description, String...extensions) {
        description = createFileNameFilterDescriptionFromExtensions(
                    description, extensions);
//        return new FileNameExtensionFilter(description, extensions);
        return null;//* 由jb2011 20120829修改：为了能兼容在jdk1.5上动行，1.6里才有的FileNameExtensionFilter没法用，这里就去掉吧，反正本程序也仅用于演示
    }

    /**
     * Creates the file name filter description from extensions.
     *
     * @param description the description
     * @param extensions the extensions
     * @return the string
     */
    private String createFileNameFilterDescriptionFromExtensions(
            String description, String[] extensions) {
        String fullDescription = (description == null) ?
                "(" : description + " (";
        // build the description from the extension list
        fullDescription += "." + extensions[0];
        for (int i = 1; i < extensions.length; i++) {
            fullDescription += ", .";
            fullDescription += extensions[i];
        }
        fullDescription += ")";
        return fullDescription;
    }

    /**
     * Creates the custom file chooser button.
     *
     * @return the j button
     */
    public JButton createCustomFileChooserButton() {
	Action a = new AbstractAction(getString("FileChooserDemo.custombutton")) {
	    public void actionPerformed(ActionEvent e) {
		fc = createFileChooser();

		// Add filefilter & fileview
                javax.swing.filechooser.FileFilter filter = createFileFilter(
                    getString("FileChooserDemo.filterdescription"),
                    "jpg", "gif");
		ExampleFileView fileView = new ExampleFileView();
		fileView.putIcon("jpg", jpgIcon);
		fileView.putIcon("gif", gifIcon);
		fc.setFileView(fileView);
		fc.addChoosableFileFilter(filter);

		// add preview accessory
		fc.setAccessory(new FilePreviewer(fc));

		// remove the approve/cancel buttons
		fc.setControlButtonsAreShown(false);

		// make custom controls
		//wokka
		JPanel custom = new JPanel();
		custom.setLayout(new BoxLayout(custom, BoxLayout.Y_AXIS));
		custom.add(Box.createRigidArea(VGAP10));
		JLabel description = new JLabel(getString("FileChooserDemo.description"));
		description.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		custom.add(description);
		custom.add(Box.createRigidArea(VGAP10));
		custom.add(fc);

		Action okAction = createOKAction();
		fc.addActionListener(okAction);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createImageButton(createFindAction()));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createButton(createAboutAction()));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createButton(okAction));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createButton(createCancelAction()));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createImageButton(createHelpAction()));
		buttons.add(Box.createRigidArea(HGAP10));

		custom.add(buttons);
		custom.add(Box.createRigidArea(VGAP10));
		
		// show the filechooser
		Frame parent = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, getDemoPanel());
		dialog = new JDialog(parent, getString("FileChooserDemo.dialogtitle"), true);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.getContentPane().add(custom, BorderLayout.CENTER);
		dialog.pack();
		dialog.setLocationRelativeTo(getDemoPanel());
		dialog.show();
	    }
	};
	return createButton(a);
    }

    /**
     * Creates the about action.
     *
     * @return the action
     */
    public Action createAboutAction() {
	return new AbstractAction(getString("FileChooserDemo.about")) {
	    public void actionPerformed(ActionEvent e) {
		File file = fc.getSelectedFile();
		String text;
		if(file == null) {
		    text = getString("FileChooserDemo.nofileselected");
		} else {
		    text = "<html>" + getString("FileChooserDemo.thefile");
		    text += "<br><font color=green>" + file.getName() + "</font><br>";
		    text += getString("FileChooserDemo.isprobably") + "</html>";
		}
		JOptionPane.showMessageDialog(getDemoPanel(), text);
	    }
	};
    }

    /**
     * Creates the ok action.
     *
     * @return the action
     */
    public Action createOKAction() {
	return new AbstractAction(getString("FileChooserDemo.ok")) {
	    public void actionPerformed(ActionEvent e) {
		dialog.dispose();
		if (!e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)
		    && fc.getSelectedFile() != null) {

		    loadImage(fc.getSelectedFile().getPath());
		}
	    }
	};
    }

    /**
     * Creates the cancel action.
     *
     * @return the action
     */
    public Action createCancelAction() {
	return new AbstractAction(getString("FileChooserDemo.cancel")) {
	    public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	    }
	};
    }

    /**
     * Creates the find action.
     *
     * @return the action
     */
    public Action createFindAction() {
	Icon icon = createImageIcon("filechooser/find.gif", getString("FileChooserDemo.find"));
	return new AbstractAction("", icon) {
	    public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(getDemoPanel(), getString("FileChooserDemo.findquestion"));
		if (result != null) {
		    JOptionPane.showMessageDialog(getDemoPanel(), getString("FileChooserDemo.findresponse"));
		}
	    }
	};
    }

    /**
     * Creates the help action.
     *
     * @return the action
     */
    public Action createHelpAction() {
	Icon icon = createImageIcon("filechooser/help.gif", getString("FileChooserDemo.help"));
	return new AbstractAction("", icon) {
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(getDemoPanel(), getString("FileChooserDemo.helptext"));
	    }
	};
    }
    
    /**
     * The Class MyImageIcon.
     */
    class MyImageIcon extends ImageIcon {
	
	/**
	 * Instantiates a new my image icon.
	 *
	 * @param filename the filename
	 */
	public MyImageIcon(String filename) {
	    super(filename);
	};
	
	/* (non-Javadoc)
	 * @see javax.swing.ImageIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
	    g.setColor(Color.white);
	    g.fillRect(0,0, c.getWidth(), c.getHeight());
	    if(getImageObserver() == null) {
		g.drawImage(
		    getImage(),
		    c.getWidth()/2 - getIconWidth()/2,
		    c.getHeight()/2 - getIconHeight()/2,
		    c
		);
	    } else {
		g.drawImage(
		    getImage(),
		    c.getWidth()/2 - getIconWidth()/2,
		    c.getHeight()/2 - getIconHeight()/2,
		    getImageObserver()
		);
	    }
	}
    }

    /**
     * Load image.
     *
     * @param filename the filename
     */
    public void loadImage(String filename) {
	theImage.setIcon(new MyImageIcon(filename));
    }

    /**
     * Creates the button.
     *
     * @param a the a
     * @return the j button
     */
    public JButton createButton(Action a) {
	JButton b = new JButton(a) {
	    public Dimension getMaximumSize() {
		int width = Short.MAX_VALUE;
		int height = super.getMaximumSize().height;
		return new Dimension(width, height);
	    }
	};
	return b;
    }

    /**
     * Creates the image button.
     *
     * @param a the a
     * @return the j button
     */
    public JButton createImageButton(Action a) {
	JButton b = new JButton(a);
	b.setMargin(new Insets(0,0,0,0));
	return b;
    }
}

class FilePreviewer extends JComponent implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    
    public FilePreviewer(JFileChooser fc) {
	setPreferredSize(new Dimension(100, 50));
	fc.addPropertyChangeListener(this);
	setBorder(new BevelBorder(BevelBorder.LOWERED));
    }
    
    public void loadImage(File f) {
        if (f == null) {
            thumbnail = null;
        } else {
	    ImageIcon tmpIcon = new ImageIcon(f.getPath());
	    if(tmpIcon.getIconWidth() > 90) {
		thumbnail = new ImageIcon(
		    tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
	    } else {
		thumbnail = tmpIcon;
	    }
	}
    }
    
    public void propertyChange(PropertyChangeEvent e) {
	String prop = e.getPropertyName();
	if(prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
	    if(isShowing()) {
                loadImage((File) e.getNewValue());
		repaint();
	    }
	}
    }
    
    public void paint(Graphics g) {
	super.paint(g);
	if(thumbnail != null) {
	    int x = getWidth()/2 - thumbnail.getIconWidth()/2;
	    int y = getHeight()/2 - thumbnail.getIconHeight()/2;
	    if(y < 0) {
		y = 0;
	    }
	    
	    if(x < 5) {
		x = 5;
	    }
	    thumbnail.paintIcon(this, g, x, y);
	}
    }
}


/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * SwingSet2.java at 2015-2-1 20:25:37, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)SwingSet2.java	1.54 06/05/31
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SingleSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * A demo that shows all of the Swing components.
 *
 * @version 1.54 05/31/06
 * @author Jeff Dinkins
 */
public class SwingSet2 extends JPanel {
	
	/** The demos. */
	String[] demos = {
			"ButtonDemo",
			"ColorChooserDemo",
			"ComboBoxDemo",
			"FileChooserDemo",
			"HtmlDemo",
			"ListDemo",
			"OptionPaneDemo",
			"ProgressBarDemo",
			"ScrollPaneDemo",
			"SliderDemo",
			"SplitPaneDemo",
			"TabbedPaneDemo",
			"TableDemo",
			"ToolTipDemo",
			"TreeDemo"
	};

	/**
	 * Load demos.
	 */
	void loadDemos() {
		for(int i = 0; i < demos.length;) {
			if(isApplet() && demos[i].equals("FileChooserDemo")) {
				// don't load the file chooser demo if we are
				// an applet
			} else {
				loadDemo(demos[i]);
			}
			i++;
		}
	}

	// Possible Look & Feels
	//    private static final String mac      =
	//            "com.sun.java.swing.plaf.mac.MacLookAndFeel";
	/** The Constant metal. */
	private static final String metal    =
		"javax.swing.plaf.metal.MetalLookAndFeel";
	//    private static final String motif    =
	//            "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	/** The Constant windows. */
	private static final String windows  =
		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	/** The Constant gtk. */
	private static final String gtk  =
		"org.jb2011.lnf.windows2.Windows2LookAndFeel";

	// The current Look & Feel
	/** The current look and feel. */
	private static String currentLookAndFeel = metal;

	// List of demos
	/** The demos list. */
	private ArrayList<DemoModule> demosList = new ArrayList<DemoModule>();

	// The preferred size of the demo
	/** The Constant PREFERRED_WIDTH. */
	private static final int PREFERRED_WIDTH = 720;
	
	/** The Constant PREFERRED_HEIGHT. */
	private static final int PREFERRED_HEIGHT = 640;

	// Box spacers
	/** The HGAP. */
	private Dimension HGAP = new Dimension(1,5);
	
	/** The VGAP. */
	private Dimension VGAP = new Dimension(5,1);

	// Resource bundle for internationalized and accessible text
	/** The bundle. */
	private ResourceBundle bundle = null;

	// A place to hold on to the visible demo
	/** The current demo. */
	private DemoModule currentDemo = null;
	
	/** The demo panel. */
	private JPanel demoPanel = null;

	// About Box
	/** The about box. */
	private JDialog aboutBox = null;

	// Status Bar
	/** The status field. */
	private JLabel statusField = null;

	// Tool Bar
	/** The toolbar. */
	private ToggleButtonToolBar toolbar = null;
	
	/** The toolbar group. */
	private ButtonGroup toolbarGroup = new ButtonGroup();

	// Menus
	/** The menu bar. */
	private JMenuBar menuBar = null;
	
	/** The laf menu. */
	private JMenu lafMenu = null;
	
	/** The themes menu. */
	private JMenu themesMenu = null;
	
	/** The audio menu. */
	private JMenu audioMenu = null;
	
	/** The options menu. */
	private JMenu optionsMenu = null;
	
	/** The laf menu group. */
	private ButtonGroup lafMenuGroup = new ButtonGroup();
	
	/** The themes menu group. */
	private ButtonGroup themesMenuGroup = new ButtonGroup();
	
	/** The audio menu group. */
	private ButtonGroup audioMenuGroup = new ButtonGroup();

	// Popup menu
	/** The popup menu. */
	private JPopupMenu popupMenu = null;
	
	/** The popup menu group. */
	private ButtonGroup popupMenuGroup = new ButtonGroup();

	// Used only if swingset is an application 
	/** The frame. */
	private JFrame frame = null;

	// Used only if swingset is an applet 
	/** The applet. */
	private SwingSet2Applet applet = null;

	// To debug or not to debug, that is the question
	/** The DEBUG. */
	private boolean DEBUG = true;
	
	/** The debug counter. */
	private int debugCounter = 0;

	// The tab pane that holds the demo
	/** The tabbed pane. */
	private JTabbedPane tabbedPane = null;

	/** The demo src pane. */
	private JEditorPane demoSrcPane = null;


	// contentPane cache, saved from the applet or application frame
	/** The content pane. */
	Container contentPane = null;


	// number of swingsets - for multiscreen
	// keep track of the number of SwingSets created - we only want to exit
	// the program when the last one has been closed.
	/** The num s ss. */
	private static int numSSs = 0;
	
	/** The swing sets. */
	private static Vector<SwingSet2> swingSets = new Vector<SwingSet2>();

	/** The drag enabled. */
	private boolean dragEnabled = false;

	/**
	 * Instantiates a new swing set2.
	 *
	 * @param applet the applet
	 */
	public SwingSet2(SwingSet2Applet applet) {
		this(applet, null);
	}

	/**
	 * SwingSet2 Constructor.
	 *
	 * @param applet the applet
	 * @param gc the gc
	 */
	public SwingSet2(SwingSet2Applet applet, GraphicsConfiguration gc) {
		// Note that applet may be null if this is started as an application
		this.applet = applet;

		if (!isApplet()) {
			frame = createFrame(gc);
		}

		// set the layout
		setLayout(new BorderLayout());

		// set the preferred size of the demo
		setPreferredSize(new Dimension(PREFERRED_WIDTH,PREFERRED_HEIGHT));

		initializeDemo();
		preloadFirstDemo();

		// Show the demo. Must do this on the GUI thread using invokeLater.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				showSwingSet2();
			}
		});

		// Start loading the rest of the demo in the background
		DemoLoadThread demoLoader = new DemoLoadThread(this);
		demoLoader.start();
	}


	/**
	 * SwingSet2 Main. Called only if we're an application, not an applet.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		// Create SwingSet on the default monitor
//		BeautyEyeLNFHelper.frameBorderStyle = 
//			BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
////		UIManager.setLookAndFeel(BeautyEyeLNFHelper.getBeautyEyeLNFCrossPlatform());//new WindowsLookAndFeel());
//		UIManager.put("RootPane.setupButtonVisible", false);
		BeautyEyeLNFHelper.debug = true;
//		BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
		BeautyEyeLNFHelper.launchBeautyEyeLNF();
//		UIManager.put("ToolBar.border",new BorderUIResource(
//				new org.jb2011.lnf.beautyeye.ch8_toolbar.BEToolBarUI.ToolBarBorder(UIManager.getColor("ToolBar.shadow"),
//						UIManager.getColor("ToolBar.highlight"), new Insets(6, 0, 0, 0))));
		
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		JDialog.setDefaultLookAndFeelDecorated(true);
//		UIManager.setLookAndFeel(new MetalLookAndFeel());
//		UIManager.setLookAndFeel(new WindowsLookAndFeel());
		
		SwingSet2 swingset = new SwingSet2(null, GraphicsEnvironment.
				getLocalGraphicsEnvironment().
				getDefaultScreenDevice().
				getDefaultConfiguration());
	}

	// *******************************************************
	// *************** Demo Loading Methods ******************
	// *******************************************************



	/**
	 * Initialize demo.
	 */
	public void initializeDemo() {
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		
		//* 由jb2011修改
		this.setBorder(BorderFactory.createEmptyBorder(2,0,4,0));//2,2,4,2));
		
		add(top, BorderLayout.NORTH);

		menuBar = createMenus();

		if (isApplet()) {
			applet.setJMenuBar(menuBar);
		} else {
			frame.setJMenuBar(menuBar);
		}

//		 creates popup menu accessible via keyboard
		popupMenu = createPopupMenu();

		ToolBarPanel toolbarPanel = new ToolBarPanel();
		toolbarPanel.setLayout(new BorderLayout());
		toolbar = new ToggleButtonToolBar();
		toolbarPanel.add(toolbar, BorderLayout.CENTER);
		top.add(toolbarPanel, BorderLayout.SOUTH);
		toolbarPanel.addContainerListener(toolbarPanel);

		tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.getModel().addChangeListener(new TabListener());

		statusField = new JLabel("");
//		statusField.setEditable(false);
		JPanel hinePanel = new JPanel(new BorderLayout());//add by jb2011
		JLabel hintLabel = N9ComponentFactory.createLabel_style1("友情提示");
		hinePanel.add(hintLabel,BorderLayout.WEST);
		statusField.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		hinePanel.add(statusField,BorderLayout.CENTER);
		hinePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
		add(hinePanel, BorderLayout.SOUTH);

		demoPanel = new JPanel();
		demoPanel.setLayout(new BorderLayout());
		//modified by jb2011
//		demoPanel.setBorder(new EtchedBorder());
		demoPanel.setBorder(new DemoPanelBorder());
		tabbedPane.addTab("Hi There!", demoPanel);

		// Add html src code viewer 
		demoSrcPane = new JEditorPane("text/html", getString("SourceCode.loading"));
		demoSrcPane.setEditable(false);

		JScrollPane scroller = new JScrollPane();
		scroller.getViewport().add(demoSrcPane);

		tabbedPane.addTab(
//				getString("TabbedPane.src_label"),
				"Java源代码",
				null,
				scroller,
				getString("TabbedPane.src_tooltip")
		);
	}

	/** The current tab demo. */
	DemoModule currentTabDemo = null;
	
	/**
	 * The listener interface for receiving tab events.
	 * The class that is interested in processing a tab
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addTabListener<code> method. When
	 * the tab event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see TabEvent
	 */
	class TabListener implements ChangeListener {
		
		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(ChangeEvent e) {
			SingleSelectionModel model = (SingleSelectionModel) e.getSource();
			boolean srcSelected = model.getSelectedIndex() == 1;
			if(currentTabDemo != currentDemo && demoSrcPane != null && srcSelected) {
				demoSrcPane.setText(getString("SourceCode.loading"));
				repaint();
			}
			if(currentTabDemo != currentDemo && srcSelected) {
				currentTabDemo = currentDemo;
				setSourceCode(currentDemo);
			} 
		}
	}


	/**
	 * Create menus.
	 *
	 * @return the j menu bar
	 */
	public JMenuBar createMenus() {
		JMenuItem mi;
		// ***** create the menubar ****
		JMenuBar menuBar = new JMenuBar();
		menuBar.getAccessibleContext().setAccessibleName(
				getString("MenuBar.accessible_description"));

		// ***** create File menu 
		JMenu fileMenu = (JMenu) menuBar.add(new JMenu(getString("FileMenu.file_label")));
		
		//TODO 以下代码用来测试项级Menu上设置图标
//		fileMenu.setIcon(
//				org.jb2011.lnf.beautyeye.ch9_menu
//				.__IconFactory__.getInstance().getRadioButtonMenuItemCheckIcon());
		
		fileMenu.setMnemonic(getMnemonic("FileMenu.file_mnemonic"));
		fileMenu.getAccessibleContext().setAccessibleDescription(getString("FileMenu.accessible_description"));

		createMenuItem(fileMenu, "FileMenu.about_label", "FileMenu.about_mnemonic",
				"FileMenu.about_accessible_description", new AboutAction(this));

		fileMenu.addSeparator();

		createMenuItem(fileMenu, "FileMenu.open_label", "FileMenu.open_mnemonic",
				"FileMenu.open_accessible_description", null);

		createMenuItem(fileMenu, "FileMenu.save_label", "FileMenu.save_mnemonic",
				"FileMenu.save_accessible_description", null);

		createMenuItem(fileMenu, "FileMenu.save_as_label", "FileMenu.save_as_mnemonic",
				"FileMenu.save_as_accessible_description", null);


		if(!isApplet()) {
			fileMenu.addSeparator();

			createMenuItem(fileMenu, "FileMenu.exit_label", "FileMenu.exit_mnemonic",
					"FileMenu.exit_accessible_description", new ExitAction(this)
			);
		}

		// Create these menu items for the first SwingSet only.
		if (numSSs == 0) {
			// ***** create laf switcher menu 
			lafMenu = (JMenu) menuBar.add(new JMenu(getString("LafMenu.laf_label")));
			lafMenu.setMnemonic(getMnemonic("LafMenu.laf_mnemonic"));
			lafMenu.getAccessibleContext().setAccessibleDescription(
					getString("LafMenu.laf_accessible_description"));

			mi = createLafMenuItem(lafMenu, "LafMenu.java_label", "LafMenu.java_mnemonic",
					"LafMenu.java_accessible_description", metal);
			mi.setSelected(true); // this is the default l&f

			createLafMenuItem(lafMenu, "LafMenu.windows_label", "LafMenu.windows_mnemonic",
					"LafMenu.windows_accessible_description", windows);
			createLafMenuItem(lafMenu, "LafMenu.gtk_label", "LafMenu.gtk_mnemonic", 
					"LafMenu.gtk_accessible_description", gtk);

			// ***** create themes menu 
			themesMenu = (JMenu) menuBar.add(new JMenu(getString("ThemesMenu.themes_label")));
			themesMenu.setMnemonic(getMnemonic("ThemesMenu.themes_mnemonic"));
			themesMenu.getAccessibleContext().setAccessibleDescription(
					getString("ThemesMenu.themes_accessible_description"));

			// ***** create the audio submenu under the theme menu
			audioMenu = (JMenu) themesMenu.add(new JMenu(getString("AudioMenu.audio_label")));
			audioMenu.setMnemonic(getMnemonic("AudioMenu.audio_mnemonic"));
			audioMenu.getAccessibleContext().setAccessibleDescription(
					getString("AudioMenu.audio_accessible_description"));

			createAudioMenuItem(audioMenu, "AudioMenu.on_label",
					"AudioMenu.on_mnemonic", 
					"AudioMenu.on_accessible_description",
					new OnAudioAction(this));

			mi = createAudioMenuItem(audioMenu, "AudioMenu.default_label",
					"AudioMenu.default_mnemonic", 
					"AudioMenu.default_accessible_description",
					new DefaultAudioAction(this));
			mi.setSelected(true); // This is the default feedback setting

			createAudioMenuItem(audioMenu, "AudioMenu.off_label",
					"AudioMenu.off_mnemonic", 
					"AudioMenu.off_accessible_description",
					new OffAudioAction(this));


			// ***** create the font submenu under the theme menu
			JMenu fontMenu = (JMenu) themesMenu.add(new JMenu(getString("FontMenu.fonts_label")));
			fontMenu.setMnemonic(getMnemonic("FontMenu.fonts_mnemonic"));
			fontMenu.getAccessibleContext().setAccessibleDescription(
					getString("FontMenu.fonts_accessible_description"));
			ButtonGroup fontButtonGroup = new ButtonGroup();
			mi = createButtonGroupMenuItem(fontMenu, "FontMenu.plain_label",
					"FontMenu.plain_mnemonic",
					"FontMenu.plain_accessible_description",
					new ChangeFontAction(this, true), fontButtonGroup);
			mi.setSelected(true);
			mi = createButtonGroupMenuItem(fontMenu, "FontMenu.bold_label",
					"FontMenu.bold_mnemonic",
					"FontMenu.bold_accessible_description",
					new ChangeFontAction(this, false), fontButtonGroup);



			// *** now back to adding color/font themes to the theme menu
			mi = createThemesMenuItem(themesMenu, "ThemesMenu.ocean_label",
					"ThemesMenu.ocean_mnemonic",
					"ThemesMenu.ocean_accessible_description",
					new OceanTheme());
			mi.setSelected(true); // This is the default theme

			createThemesMenuItem(themesMenu, "ThemesMenu.steel_label",
					"ThemesMenu.steel_mnemonic",
					"ThemesMenu.steel_accessible_description",
					new DefaultMetalTheme());

			createThemesMenuItem(themesMenu, "ThemesMenu.aqua_label", "ThemesMenu.aqua_mnemonic",
					"ThemesMenu.aqua_accessible_description", new AquaTheme());

			createThemesMenuItem(themesMenu, "ThemesMenu.charcoal_label", "ThemesMenu.charcoal_mnemonic",
					"ThemesMenu.charcoal_accessible_description", new CharcoalTheme());

			createThemesMenuItem(themesMenu, "ThemesMenu.contrast_label", "ThemesMenu.contrast_mnemonic",
					"ThemesMenu.contrast_accessible_description", new ContrastTheme());

			createThemesMenuItem(themesMenu, "ThemesMenu.emerald_label", "ThemesMenu.emerald_mnemonic",
					"ThemesMenu.emerald_accessible_description", new EmeraldTheme());

			createThemesMenuItem(themesMenu, "ThemesMenu.ruby_label", "ThemesMenu.ruby_mnemonic",
					"ThemesMenu.ruby_accessible_description", new RubyTheme());

			// ***** create the options menu
			optionsMenu = (JMenu)menuBar.add(
					new JMenu(getString("OptionsMenu.options_label")));
			optionsMenu.setMnemonic(getMnemonic("OptionsMenu.options_mnemonic"));
			optionsMenu.getAccessibleContext().setAccessibleDescription(
					getString("OptionsMenu.options_accessible_description"));

			// ***** create tool tip submenu item.
			mi = createCheckBoxMenuItem(optionsMenu, "OptionsMenu.tooltip_label",
					"OptionsMenu.tooltip_mnemonic",
					"OptionsMenu.tooltip_accessible_description",
					new ToolTipAction());
			mi.setSelected(true);

			// ***** create drag support submenu item.
			createCheckBoxMenuItem(optionsMenu, "OptionsMenu.dragEnabled_label",
					"OptionsMenu.dragEnabled_mnemonic",
					"OptionsMenu.dragEnabled_accessible_description",
					new DragSupportAction());

		}


		// ***** create the multiscreen menu, if we have multiple screens
		if (!isApplet()) {
			GraphicsDevice[] screens = GraphicsEnvironment.
			getLocalGraphicsEnvironment().
			getScreenDevices();
			if (screens.length > 1) {

				JMenu multiScreenMenu = (JMenu) menuBar.add(new JMenu(
						getString("MultiMenu.multi_label")));

				multiScreenMenu.setMnemonic(getMnemonic("MultiMenu.multi_mnemonic"));    
				multiScreenMenu.getAccessibleContext().setAccessibleDescription(
						getString("MultiMenu.multi_accessible_description"));

				createMultiscreenMenuItem(multiScreenMenu, MultiScreenAction.ALL_SCREENS);
				for (int i = 0; i < screens.length; i++) {
					createMultiscreenMenuItem(multiScreenMenu, i);
				}
			}
		}

		return menuBar;
	}

	/**
	 * Create a checkbox menu menu item.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param action the action
	 * @return the j menu item
	 */
	private JMenuItem createCheckBoxMenuItem(JMenu menu, String label,
			String mnemonic,
			String accessibleDescription,
			Action action) {
		JCheckBoxMenuItem mi = (JCheckBoxMenuItem)menu.add(
				new JCheckBoxMenuItem(getString(label)));
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(
				accessibleDescription));
		mi.addActionListener(action);
		return mi;
	}

	/**
	 * Create a radio button menu menu item for items that are part of a
	 * button group.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param action the action
	 * @param buttonGroup the button group
	 * @return the j menu item
	 */
	private JMenuItem createButtonGroupMenuItem(JMenu menu, String label,
			String mnemonic,
			String accessibleDescription,
			Action action,
			ButtonGroup buttonGroup) {
		JRadioButtonMenuItem mi = (JRadioButtonMenuItem)menu.add(
				new JRadioButtonMenuItem(getString(label)));
		buttonGroup.add(mi);
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(
				accessibleDescription));
		mi.addActionListener(action);
		return mi;
	}

	/**
	 * Create the theme's audio submenu.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param action the action
	 * @return the j menu item
	 */
	public JMenuItem createAudioMenuItem(JMenu menu, String label,
			String mnemonic,
			String accessibleDescription,
			Action action) {
		JRadioButtonMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(getString(label)));
		audioMenuGroup.add(mi);
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(accessibleDescription));
		mi.addActionListener(action);

		return mi;
	}

	/**
	 * Creates a generic menu item.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param action the action
	 * @return the j menu item
	 */
	public JMenuItem createMenuItem(JMenu menu, String label, String mnemonic,
			String accessibleDescription, Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(getString(label)));
		
//		mi.setBorder(BorderFactory.createEmptyBorder());
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(accessibleDescription));
		mi.addActionListener(action);
		if(action == null) {
			mi.setEnabled(false);
		}
		return mi;
	}

	/**
	 * Creates a JRadioButtonMenuItem for the Themes menu.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param theme the theme
	 * @return the j menu item
	 */
	public JMenuItem createThemesMenuItem(JMenu menu, String label, String mnemonic,
			String accessibleDescription, MetalTheme theme) {
		JRadioButtonMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(getString(label)));
		themesMenuGroup.add(mi);
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(accessibleDescription));
//		mi.addActionListener(new ChangeThemeAction(this, theme));

		return mi;
	}

	/**
	 * Creates a JRadioButtonMenuItem for the Look and Feel menu.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param laf the laf
	 * @return the j menu item
	 */
	public JMenuItem createLafMenuItem(JMenu menu, String label, String mnemonic,
			String accessibleDescription, String laf) {
		JMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(getString(label)));
		lafMenuGroup.add(mi);
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(accessibleDescription));
//		mi.addActionListener(new ChangeLookAndFeelAction(this, laf));

//		mi.setEnabled(isAvailableLookAndFeel(laf));

		return mi;
	}

	/**
	 * Creates a multi-screen menu item.
	 *
	 * @param menu the menu
	 * @param screen the screen
	 * @return the j menu item
	 */
	public JMenuItem createMultiscreenMenuItem(JMenu menu, int screen) {
		JMenuItem mi = null;
		if (screen == MultiScreenAction.ALL_SCREENS) {
			mi = (JMenuItem) menu.add(new JMenuItem(getString("MultiMenu.all_label")));
			mi.setMnemonic(getMnemonic("MultiMenu.all_mnemonic"));
			mi.getAccessibleContext().setAccessibleDescription(getString(
			"MultiMenu.all_accessible_description"));
		}
		else {
			mi = (JMenuItem) menu.add(new JMenuItem(getString("MultiMenu.single_label") + " " +
					screen));
			mi.setMnemonic(KeyEvent.VK_0 + screen);
			mi.getAccessibleContext().setAccessibleDescription(getString(
					"MultiMenu.single_accessible_description") + " " + screen);

		}
		mi.addActionListener(new MultiScreenAction(this, screen));
		return mi;
	}

	/**
	 * Creates the popup menu.
	 *
	 * @return the j popup menu
	 */
	public JPopupMenu createPopupMenu() {
		JPopupMenu popup = new JPopupMenu("JPopupMenu demo");

		createPopupMenuItem(popup, "LafMenu.java_label", "LafMenu.java_mnemonic",
				"LafMenu.java_accessible_description", metal);

		createPopupMenuItem(popup, "LafMenu.windows_label", "LafMenu.windows_mnemonic",
				"LafMenu.windows_accessible_description", windows);

		createPopupMenuItem(popup, "LafMenu.gtk_label", "LafMenu.gtk_mnemonic",
				"LafMenu.gtk_accessible_description", gtk);

		// register key binding to activate popup menu
		InputMap map = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.SHIFT_MASK),
		"postMenuAction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU, 0), "postMenuAction");
		getActionMap().put("postMenuAction", new ActivatePopupMenuAction(this, popup));

		return popup;
	}

	/**
	 * Creates a JMenuItem for the Look and Feel popup menu.
	 *
	 * @param menu the menu
	 * @param label the label
	 * @param mnemonic the mnemonic
	 * @param accessibleDescription the accessible description
	 * @param laf the laf
	 * @return the j menu item
	 */
	public JMenuItem createPopupMenuItem(JPopupMenu menu, String label, String mnemonic,
			String accessibleDescription, String laf) {
		JMenuItem mi = menu.add(new JMenuItem(getString(label)));
		popupMenuGroup.add(mi);
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(getString(accessibleDescription));
//		mi.addActionListener(new ChangeLookAndFeelAction(this, laf));
//		mi.setEnabled(isAvailableLookAndFeel(laf));

		return mi;
	}


	/**
	 * Load the first demo. This is done separately from the remaining demos
	 * so that we can get SwingSet2 up and available to the user quickly.
	 */
	public void preloadFirstDemo() {
		DemoModule demo = addDemo(new InternalFrameDemo(this));
		setDemo(demo);
	}


	/**
	 * Add a demo to the toolbar.
	 *
	 * @param demo the demo
	 * @return the demo module
	 */
	public DemoModule addDemo(DemoModule demo) {
		demosList.add(demo);
		if (dragEnabled) {
			demo.updateDragEnabled(true);
		}
		// do the following on the gui thread
		SwingUtilities.invokeLater(new SwingSetRunnable(this, demo) {
			public void run() {
				SwitchToDemoAction action = new SwitchToDemoAction(swingset, (DemoModule) obj);
				JToggleButton tb = swingset.getToolBar().addToggleButton(action);
//				swingset.getToolBar().add(new JSeparator(JSeparator.VERTICAL));
				swingset.getToolBarGroup().add(tb);
				if(swingset.getToolBarGroup().getSelection() == null) {
					tb.setSelected(true);
				}
//				tb.setText(null);
				tb.setIcon(null);
				tb.setToolTipText(((DemoModule)obj).getToolTip());

				if(demos[demos.length-1].equals(obj.getClass().getName())) {
					setStatus(getString("Status.popupMenuAccessible"));
				} 
			}
		});
		return demo;
	}


	/**
	 * Sets the current demo.
	 *
	 * @param demo the new demo
	 */
	public void setDemo(DemoModule demo) {
		currentDemo = demo;

		// Ensure panel's UI is current before making visible
		JComponent currentDemoPanel = demo.getDemoPanel();
		//** 以下代码被注释掉了，它将会导致针对某一组件在运行时设置的UI将在下
		//** 次重新loadDemo时被置为当前正在使用的L&F的默认组件UI:比如，原本在程序初始化时把一按钮设置成绿色的自定义
		//** UI，但它所有在Demo被利于新load时因调用此方法而使该按钮重新恢复到当前L&F的默认UI（再也不是绿色了
		//** ，当然该段代码不能算作bug，只是SwingSet2为了确保演示L&F时能即时刷新UI，但恰好跟jb2011需要的目标不同而已）！
//		SwingUtilities.updateComponentTreeUI(currentDemoPanel);

		demoPanel.removeAll();
		demoPanel.add(currentDemoPanel, BorderLayout.CENTER);

		tabbedPane.setSelectedIndex(0);
		tabbedPane.setTitleAt(0, demo.getName());
		tabbedPane.setToolTipTextAt(0, demo.getToolTip());
	}


	/**
	 * Bring up the SwingSet2 demo by showing the frame (only
	 * applicable if coming up as an application, not an applet);.
	 */
	public void showSwingSet2() {
		if(!isApplet() && getFrame() != null) {
			// put swingset in a frame and show it
			JFrame f = getFrame();
			f.setTitle(getString("Frame.title")+" - BeautyEye L&F v3.6 (欢迎加入Java Swing QQ群:259448663)");
			f.getContentPane().add(this, BorderLayout.CENTER);
//			f.pack();
			f.setSize(1024, 750);

			Rectangle screenRect = f.getGraphicsConfiguration().getBounds();
			Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
					f.getGraphicsConfiguration());

			// Make sure we don't place the demo off the screen.
			int centerWidth = screenRect.width < f.getSize().width ?
					screenRect.x :
						screenRect.x + screenRect.width/2 - f.getSize().width/2;
			int centerHeight = screenRect.height < f.getSize().height ?
					screenRect.y :
						screenRect.y + screenRect.height/2 - f.getSize().height/2;

			centerHeight = centerHeight < screenInsets.top ?
					screenInsets.top : centerHeight;

			f.setLocation(centerWidth, centerHeight);
			f.show();
			numSSs++;
			swingSets.add(this);
		} 
	}

	// *******************************************************
	// ****************** Utility Methods ********************
	// *******************************************************

	/**
	 * Loads a demo from a classname.
	 *
	 * @param classname the classname
	 */
	void loadDemo(String classname) {
		setStatus(getString("Status.loading") + getString(classname + ".name"));
		DemoModule demo = null;
		try {
			Class demoClass = Class.forName(classname);
			Constructor demoConstructor = demoClass.getConstructor(new Class[]{SwingSet2.class});
			demo = (DemoModule) demoConstructor.newInstance(new Object[]{this});
			addDemo(demo);
		} catch (Exception e) {
			System.out.println("Error occurred loading demo: " + classname);
		}
	}

	/**
	 * A utility function that layers on top of the LookAndFeel's
	 * isSupportedLookAndFeel() method. Returns true if the LookAndFeel
	 * is supported. Returns false if the LookAndFeel is not supported
	 * and/or if there is any kind of error checking if the LookAndFeel
	 * is supported.
	 * 
	 * The L&F menu will use this method to detemine whether the various
	 * L&F options should be active or inactive.
	 *
	 * @param laf the laf
	 * @return true, if is available look and feel
	 */
	protected boolean isAvailableLookAndFeel(String laf) {
		try { 
			Class lnfClass = Class.forName(laf);
			LookAndFeel newLAF = (LookAndFeel)(lnfClass.newInstance());
			return newLAF.isSupportedLookAndFeel();
		} catch(Exception e) { // If ANYTHING weird happens, return false
			return false;
		}
	}


	/**
	 * Determines if this is an applet or application.
	 *
	 * @return true, if is applet
	 */
	public boolean isApplet() {
		return (applet != null);
	}

	/**
	 * Returns the applet instance.
	 *
	 * @return the applet
	 */
	public SwingSet2Applet getApplet() {
		return applet;
	}


	/**
	 * Returns the frame instance.
	 *
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Returns the menubar.
	 *
	 * @return the menu bar
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Returns the toolbar.
	 *
	 * @return the tool bar
	 */
	public ToggleButtonToolBar getToolBar() {
		return toolbar;
	}

	/**
	 * Returns the toolbar button group.
	 *
	 * @return the tool bar group
	 */
	public ButtonGroup getToolBarGroup() {
		return toolbarGroup;
	}

	/**
	 * Returns the content pane wether we're in an applet
	 * or application.
	 *
	 * @return the content pane
	 */
	public Container getContentPane() {
		if(contentPane == null) {
			if(getFrame() != null) {
				contentPane = getFrame().getContentPane();
			} else if (getApplet() != null) {
				contentPane = getApplet().getContentPane();
			}
		}
		return contentPane;
	}

	/**
	 * Create a frame for SwingSet2 to reside in if brought up
	 * as an application.
	 *
	 * @param gc the gc
	 * @return the j frame
	 */
	public static JFrame createFrame(GraphicsConfiguration gc) {
		JFrame frame = new JFrame(gc);
		if (numSSs == 0) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			WindowListener l = new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					numSSs--;
					swingSets.remove(this);
				}
			};
			frame.addWindowListener(l);
		}
		
		//由Jack Jiang于2012-09-22加上，防止因JToolBar的大小而影响JFrame的prefereSize使得没法再缩小
		frame.setMinimumSize(new Dimension(100,100));
		return frame;
	}


	/**
	 * Set the status.
	 *
	 * @param s the new status
	 */
	public void setStatus(String s) {
		// do the following on the gui thread
		SwingUtilities.invokeLater(new SwingSetRunnable(this, s) {
			public void run() {
				swingset.statusField.setText((String) obj);
			}
		});
	}


	/**
	 * This method returns a string from the demo's resource bundle.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String getString(String key) {
		String value = null;
		try {
			value = getResourceBundle().getString(key);
		} catch (MissingResourceException e) {
			System.out.println("java.util.MissingResourceException: Couldn't find value for: " + key);
		}
		if(value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
	}

	/**
	 * Sets the drag enabled.
	 *
	 * @param dragEnabled the new drag enabled
	 */
	void setDragEnabled(boolean dragEnabled) {
		if (dragEnabled == this.dragEnabled) {
			return;
		}

		this.dragEnabled = dragEnabled;

		for (DemoModule dm : demosList) {
			dm.updateDragEnabled(dragEnabled);
		}

		demoSrcPane.setDragEnabled(dragEnabled);
	}

	/**
	 * Checks if is drag enabled.
	 *
	 * @return true, if is drag enabled
	 */
	boolean isDragEnabled() {
		return dragEnabled;
	}

	/**
	 * Returns the resource bundle associated with this demo. Used
	 * to get accessable and internationalized strings.
	 *
	 * @return the resource bundle
	 */
	public ResourceBundle getResourceBundle() {
		if(bundle == null) {
			bundle = ResourceBundle.getBundle("resources.swingset");
		}
		return bundle;
	}

	/**
	 * Returns a mnemonic from the resource bundle. Typically used as
	 * keyboard shortcuts in menu items.
	 *
	 * @param key the key
	 * @return the mnemonic
	 */
	public char getMnemonic(String key) {
		return (getString(key)).charAt(0);
	}

	/**
	 * Creates an icon from an image contained in the "images" directory.
	 *
	 * @param filename the filename
	 * @param description the description
	 * @return the image icon
	 */
	public ImageIcon createImageIcon(String filename, String description) {
		String path = "/resources/images/" + filename;
		return new ImageIcon(getClass().getResource(path)); 
	}

	/**
	 * If DEBUG is defined, prints debug information out to std ouput.
	 *
	 * @param s the s
	 */
	public void debug(String s) {
		if(DEBUG) {
			System.out.println((debugCounter++) + ": " + s);
		}
	}

	/**
	 * Stores the current L&F, and calls updateLookAndFeel, below.
	 *
	 * @param laf the new look and feel
	 */
	public void setLookAndFeel(String laf) {
		if(currentLookAndFeel != laf) {
			currentLookAndFeel = laf;
			/* The recommended way of synchronizing state between multiple
			 * controls that represent the same command is to use Actions.
			 * The code below is a workaround and will be replaced in future
			 * version of SwingSet2 demo.
			 */
			String lafName = null;
			if(laf == metal) lafName = getString("LafMenu.java_label");
			if(laf == gtk) lafName = getString("LafMenu.gtk_label");
			if(laf == windows) lafName = getString("LafMenu.windows_label");
			
			themesMenu.setEnabled(laf == metal);
			updateLookAndFeel();
			for(int i=0;i<lafMenu.getItemCount();i++) {
				JMenuItem item = lafMenu.getItem(i);
				if(item.getText() == lafName) {
					item.setSelected(true);
				} else {
					item.setSelected(false);
				}
			}
		}
	}

	/**
	 * Update this swing set.
	 */
	private void updateThisSwingSet() {
		if (isApplet()) {
			SwingUtilities.updateComponentTreeUI(getApplet());
		} else {
			JFrame frame = getFrame();
			if (frame == null) {
				SwingUtilities.updateComponentTreeUI(this);
			} else {
				SwingUtilities.updateComponentTreeUI(frame);
			}
		}

		SwingUtilities.updateComponentTreeUI(popupMenu);
		if (aboutBox != null) {
			SwingUtilities.updateComponentTreeUI(aboutBox);
		}
	}

	/**
	 * Sets the current L&F on each demo module.
	 */
	public void updateLookAndFeel() {
		try {
			System.out.println("!!currentLookAndFeel="+currentLookAndFeel);
			UIManager.setLookAndFeel(currentLookAndFeel);
			if (isApplet()) {
				updateThisSwingSet();
			} else {
				for (SwingSet2 ss : swingSets) {
					ss.updateThisSwingSet();
				}
			}
		} catch (Exception ex) {
			System.out.println("Failed loading L&F: " + currentLookAndFeel);
			System.out.println(ex);
		}
	}

	/**
	 * Loads and puts the source code text into JEditorPane in the "Source Code" tab.
	 *
	 * @param demo the new source code
	 */
	public void setSourceCode(DemoModule demo) {
		// do the following on the gui thread
		SwingUtilities.invokeLater(new SwingSetRunnable(this, demo) {
			public void run() {
				swingset.demoSrcPane.setText(((DemoModule)obj).getSourceCode());
				swingset.demoSrcPane.setCaretPosition(0);

			}
		});
	}

	// *******************************************************
	// **************   ToggleButtonToolbar  *****************
	// *******************************************************
	/** The zero insets. */
	static Insets zeroInsets = new Insets(3,2,3,2);
	
	/**
	 * The Class ToggleButtonToolBar.
	 */
	protected class ToggleButtonToolBar extends JToolBar {
		
		/**
		 * Instantiates a new toggle button tool bar.
		 */
		public ToggleButtonToolBar() {
			super();
			this.setFloatable(true);
//			this.putClientProperty("ToolBar.isPaintPlainBackground", Boolean.TRUE);
		}

		/**
		 * Adds the toggle button.
		 *
		 * @param a the a
		 * @return the j toggle button
		 */
		JToggleButton addToggleButton(Action a) {
			JToggleButton tb = new JToggleButton(
					(String)a.getValue(Action.NAME),null
//					(Icon)a.getValue(Action.SMALL_ICON)
			);
//			tb.setMargin(zeroInsets);
//			tb.setText(null);
			tb.setEnabled(a.isEnabled());
			tb.setToolTipText((String)a.getValue(Action.SHORT_DESCRIPTION));
			tb.setAction(a);
			add(tb);
			return tb;
		}
	}

	// *******************************************************
	// *********  ToolBar Panel / Docking Listener ***********
	// *******************************************************
	/**
	 * The Class ToolBarPanel.
	 */
	class ToolBarPanel extends JPanel implements ContainerListener {

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#contains(int, int)
		 */
		public boolean contains(int x, int y) {
			Component c = getParent();
			if (c != null) {
				Rectangle r = c.getBounds();
				return (x >= 0) && (x < r.width) && (y >= 0) && (y < r.height);
			}
			else {
				return super.contains(x,y);
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ContainerListener#componentAdded(java.awt.event.ContainerEvent)
		 */
		public void componentAdded(ContainerEvent e) {
			Container c = e.getContainer().getParent();
			if (c != null) {
				c.getParent().validate();
				c.getParent().repaint();	    
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ContainerListener#componentRemoved(java.awt.event.ContainerEvent)
		 */
		public void componentRemoved(ContainerEvent e) {
			Container c = e.getContainer().getParent();
			if (c != null) {
				c.getParent().validate();
				c.getParent().repaint();
			}
		}
	}

	// *******************************************************
	// ******************   Runnables  ***********************
	// *******************************************************

	/**
	 * Generic SwingSet2 runnable. This is intended to run on the
	 * AWT gui event thread so as not to muck things up by doing
	 * gui work off the gui thread. Accepts a SwingSet2 and an Object
	 * as arguments, which gives subtypes of this class the two
	 * "must haves" needed in most runnables for this demo.
	 */
	class SwingSetRunnable implements Runnable {
		
		/** The swingset. */
		protected SwingSet2 swingset;
		
		/** The obj. */
		protected Object obj;

		/**
		 * Instantiates a new swing set runnable.
		 *
		 * @param swingset the swingset
		 * @param obj the obj
		 */
		public SwingSetRunnable(SwingSet2 swingset, Object obj) {
			this.swingset = swingset;
			this.obj = obj;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
		}
	}


	// *******************************************************
	// ********************   Actions  ***********************
	// *******************************************************

	/**
	 * The Class SwitchToDemoAction.
	 */
	public class SwitchToDemoAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/** The demo. */
		DemoModule demo;

		/**
		 * Instantiates a new switch to demo action.
		 *
		 * @param swingset the swingset
		 * @param demo the demo
		 */
		public SwitchToDemoAction(SwingSet2 swingset, DemoModule demo) {
			super(demo.getName(), demo.getIcon());
			this.swingset = swingset;
			this.demo = demo;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			swingset.setDemo(demo);
		}
	}

	/**
	 * The Class OkAction.
	 */
	class OkAction extends AbstractAction {
		
		/** The about box. */
		JDialog aboutBox;

		/**
		 * Instantiates a new ok action.
		 *
		 * @param aboutBox the about box
		 */
		protected OkAction(JDialog aboutBox) {
			super("OkAction");
			this.aboutBox = aboutBox;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			aboutBox.setVisible(false);
		}
	}

	/**
	 * The Class ChangeLookAndFeelAction.
	 */
	class ChangeLookAndFeelAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/** The laf. */
		String laf;
		
		/**
		 * Instantiates a new change look and feel action.
		 *
		 * @param swingset the swingset
		 * @param laf the laf
		 */
		protected ChangeLookAndFeelAction(SwingSet2 swingset, String laf) {
			super("ChangeTheme");
			this.swingset = swingset;
			this.laf = laf;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			swingset.setLookAndFeel(laf);
		}
	}

	/**
	 * The Class ActivatePopupMenuAction.
	 */
	class ActivatePopupMenuAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/** The popup. */
		JPopupMenu popup;
		
		/**
		 * Instantiates a new activate popup menu action.
		 *
		 * @param swingset the swingset
		 * @param popup the popup
		 */
		protected ActivatePopupMenuAction(SwingSet2 swingset, JPopupMenu popup) {
			super("ActivatePopupMenu");
			this.swingset = swingset;
			this.popup = popup;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			Dimension invokerSize = getSize();
			Dimension popupSize = popup.getPreferredSize();
			popup.show(swingset, (invokerSize.width - popupSize.width) / 2,
					(invokerSize.height - popupSize.height) / 2);
		}
	}

	// Turns on all possible auditory feedback
	/**
	 * The Class OnAudioAction.
	 */
	class OnAudioAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/**
		 * Instantiates a new on audio action.
		 *
		 * @param swingset the swingset
		 */
		protected OnAudioAction(SwingSet2 swingset) {
			super("Audio On");
			this.swingset = swingset;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			//* Jack Jiang 于2012-09-11日注释掉
//			UIManager.put("AuditoryCues.playList",
//					UIManager.get("AuditoryCues.allAuditoryCues"));
//			swingset.updateLookAndFeel();
		}
	}

	// Turns on the default amount of auditory feedback
	/**
	 * The Class DefaultAudioAction.
	 */
	class DefaultAudioAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/**
		 * Instantiates a new default audio action.
		 *
		 * @param swingset the swingset
		 */
		protected DefaultAudioAction(SwingSet2 swingset) {
			super("Audio Default");
			this.swingset = swingset;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			//* Jack Jiang 于2012-09-11日注释掉
//			UIManager.put("AuditoryCues.playList",
//					UIManager.get("AuditoryCues.defaultCueList"));
//			swingset.updateLookAndFeel();
		}
	}

	// Turns off all possible auditory feedback
	/**
	 * The Class OffAudioAction.
	 */
	class OffAudioAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/**
		 * Instantiates a new off audio action.
		 *
		 * @param swingset the swingset
		 */
		protected OffAudioAction(SwingSet2 swingset) {
			super("Audio Off");
			this.swingset = swingset;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			//* Jack Jiang 于2012-09-11日注释掉
//			UIManager.put("AuditoryCues.playList",
//					UIManager.get("AuditoryCues.noAuditoryCues"));
//			swingset.updateLookAndFeel();
		}
	}

	// Turns on or off the tool tips for the demo.
	/**
	 * The Class ToolTipAction.
	 */
	class ToolTipAction extends AbstractAction {
		
		/**
		 * Instantiates a new tool tip action.
		 */
		protected ToolTipAction() {
			super("ToolTip Control");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			boolean status = ((JCheckBoxMenuItem)e.getSource()).isSelected();
			ToolTipManager.sharedInstance().setEnabled(status);
		}
	}

	/**
	 * The Class DragSupportAction.
	 */
	class DragSupportAction extends AbstractAction {
		
		/**
		 * Instantiates a new drag support action.
		 */
		protected DragSupportAction() {
			super("DragSupport Control");
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			boolean dragEnabled = ((JCheckBoxMenuItem)e.getSource()).isSelected();
			if (isApplet()) {
				setDragEnabled(dragEnabled);
			} else {
				for (SwingSet2 ss : swingSets) {
					ss.setDragEnabled(dragEnabled);
				}
			}
		}
	}

	/**
	 * The Class ChangeThemeAction.
	 */
	class ChangeThemeAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/** The theme. */
		MetalTheme theme;
		
		/**
		 * Instantiates a new change theme action.
		 *
		 * @param swingset the swingset
		 * @param theme the theme
		 */
		protected ChangeThemeAction(SwingSet2 swingset, MetalTheme theme) {
			super("ChangeTheme");
			this.swingset = swingset;
			this.theme = theme;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			MetalLookAndFeel.setCurrentTheme(theme);
			swingset.updateLookAndFeel();
		}
	}

	/**
	 * The Class ExitAction.
	 */
	class ExitAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/**
		 * Instantiates a new exit action.
		 *
		 * @param swingset the swingset
		 */
		protected ExitAction(SwingSet2 swingset) {
			super("ExitAction");
			this.swingset = swingset;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * The Class AboutAction.
	 */
	class AboutAction extends AbstractAction {
		
		/** The swingset. */
		SwingSet2 swingset;
		
		/**
		 * Instantiates a new about action.
		 *
		 * @param swingset the swingset
		 */
		protected AboutAction(SwingSet2 swingset) {
			super("AboutAction");
			this.swingset = swingset;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if(aboutBox == null) {
				// JPanel panel = new JPanel(new BorderLayout());
				JPanel panel = new AboutPanel(swingset);
				panel.setLayout(new BorderLayout());

				aboutBox = new JDialog(swingset.getFrame(), getString("AboutBox.title"), false);
				aboutBox.setResizable(false);
				aboutBox.getContentPane().add(panel, BorderLayout.CENTER);

				// JButton button = new JButton(getString("AboutBox.ok_button_text"));
				JPanel buttonpanel = new JPanel();
				buttonpanel.setBorder(new javax.swing.border.EmptyBorder(0, 0, 3, 0));
				buttonpanel.setOpaque(false);
				JButton button = (JButton) buttonpanel.add(
						new JButton(getString("AboutBox.ok_button_text"))
				);
				panel.add(buttonpanel, BorderLayout.SOUTH);

				button.addActionListener(new OkAction(aboutBox));
			}
			aboutBox.pack();
			if (isApplet()) {
				aboutBox.setLocationRelativeTo(getApplet());
			} else {
				aboutBox.setLocationRelativeTo(getFrame());
			}
			aboutBox.show();
		}
	}

	/**
	 * The Class MultiScreenAction.
	 */
	class MultiScreenAction extends AbstractAction {
		
		/** The Constant ALL_SCREENS. */
		static final int ALL_SCREENS = -1;
		
		/** The screen. */
		int screen;
		
		/**
		 * Instantiates a new multi screen action.
		 *
		 * @param swingset the swingset
		 * @param screen the screen
		 */
		protected MultiScreenAction(SwingSet2 swingset, int screen) {
			super("MultiScreenAction");
			this.screen = screen;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			GraphicsDevice[] gds = GraphicsEnvironment.
			getLocalGraphicsEnvironment().
			getScreenDevices();
			if (screen == ALL_SCREENS) {
				for (int i = 0; i < gds.length; i++) {
					SwingSet2 swingset = new SwingSet2(null,
							gds[i].getDefaultConfiguration());
					swingset.setDragEnabled(dragEnabled);
				}
			}
			else {
				SwingSet2 swingset = new SwingSet2(null,
						gds[screen].getDefaultConfiguration());
				swingset.setDragEnabled(dragEnabled);
			}
		}
	}

	// *******************************************************
	// **********************  Misc  *************************
	// *******************************************************

	/**
	 * The Class DemoLoadThread.
	 */
	class DemoLoadThread extends Thread {
		
		/** The swingset. */
		SwingSet2 swingset;

		/**
		 * Instantiates a new demo load thread.
		 *
		 * @param swingset the swingset
		 */
		public DemoLoadThread(SwingSet2 swingset) {
			this.swingset = swingset;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			swingset.loadDemos();
		}
	}

	/**
	 * The Class AboutPanel.
	 */
	class AboutPanel extends JPanel {
		
		/** The aboutimage. */
		ImageIcon aboutimage = null;
		
		/** The swingset. */
		SwingSet2 swingset = null;

		/**
		 * Instantiates a new about panel.
		 *
		 * @param swingset the swingset
		 */
		public AboutPanel(SwingSet2 swingset) {
			this.swingset = swingset;
			aboutimage = swingset.createImageIcon("About.jpg", "AboutBox.accessible_description");
			setOpaque(false);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		public void paint(Graphics g) {
			aboutimage.paintIcon(this, g, 0, 0);
			super.paint(g);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#getPreferredSize()
		 */
		public Dimension getPreferredSize() {
			return new Dimension(aboutimage.getIconWidth(),
					aboutimage.getIconHeight());
		}
	}


	/**
	 * The Class ChangeFontAction.
	 */
	private class ChangeFontAction extends AbstractAction {
		
		/** The swingset. */
		private SwingSet2 swingset;
		
		/** The plain. */
		private boolean plain;

		/**
		 * Instantiates a new change font action.
		 *
		 * @param swingset the swingset
		 * @param plain the plain
		 */
		ChangeFontAction(SwingSet2 swingset, boolean plain) {
			super("FontMenu");
			this.swingset = swingset;
			this.plain = plain;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			//* Jack Jiang 于2012-09-11日注释掉
//			if (plain) {
//				UIManager.put("swing.boldMetal", Boolean.FALSE);
//			}
//			else {
//				UIManager.put("swing.boldMetal", Boolean.TRUE);
//			}
//			// Change the look and feel to force the settings to take effect.
//			updateLookAndFeel();
		}
	}
	
	//------------------------------------------------------------- 由jb2011于2012-06-20实现
	//用于DemoPanel的边框实现，视觉目标是：简洁。
	//原EtchedBorder边框太土，但是没边框将导致整体效果稍显单调，所以做此边框
	/**
	 * The Class DemoPanelBorder.
	 */
	private class DemoPanelBorder extends AbstractBorder
	{
		
		/**
		 * Instantiates a new demo panel border.
		 */
		public DemoPanelBorder() {
		}

		/* (non-Javadoc)
		 * @see javax.swing.border.AbstractBorder#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
		 */
		public void paintBorder(Component c, Graphics g, int x, int y, 
				int width, int height) 
		{
//			g.drawLine(x,y, widthheight); // draw top
//			g.drawLine(x,y, x,height-1); // draw left
//			g.drawLine(width-1,y, width-1,height-1); // draw right
			
			//** 绘制border的底线
			//虚线样式
			Stroke oldStroke = ((Graphics2D)g).getStroke();
			Stroke sroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 0, new float[]{1, 2}, 0);//实线，空白
			((Graphics2D)g).setStroke(sroke);
			//底边上（浅灰色）
			g.setColor(new Color(200,200,200));
			g.drawLine(x,height-2, width-1,height-2); // draw bottom1
			//底边下（白色）：绘制一条白色虚线的目的是与上面的灰线产生较强对比度从而形成立体效果
			//，本L&F实现中因与Panel的底色对比度不够强烈而立体感不明显（颜色越深的底色最终效果越明显）
			g.setColor(Color.white);
			g.drawLine(x,height-1, width-1,height-1);//draw bottom2
			
			((Graphics2D)g).setStroke(oldStroke);
		}

		//border只有底边，且高度为2像素
		/* (non-Javadoc)
		 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component)
		 */
		public Insets getBorderInsets(Component c) {
			return new Insets(0,0,2,0);
		}

		/* (non-Javadoc)
		 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
		 */
		public Insets getBorderInsets(Component c, Insets insets) {
//			insets.top = insets.left = insets.bottom = insets.right = 1;
			return getBorderInsets(c);//insets;
		}
	}
}


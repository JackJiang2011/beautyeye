/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Platform.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.utils;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

// TODO: Auto-generated Javadoc
/**
 * 一个用于判断操作系统类型的实用工具类。.
 *
 * @author Jack Jiang(jb2011@163.com)
 */
public class Platform
{
	/** Operating system is Windows NT. */
    public static final int OS_WINNT = 1 << 0;

    /** Operating system is Windows 95. */
    public static final int OS_WIN95 = OS_WINNT << 1;

    /** Operating system is Windows 98. */
    public static final int OS_WIN98 = OS_WIN95 << 1;

    /** Operating system is Solaris. */
    public static final int OS_SOLARIS = OS_WIN98 << 1;

    /** Operating system is Linux. */
    public static final int OS_LINUX = OS_SOLARIS << 1;

    /** Operating system is HP-UX. */
    public static final int OS_HP = OS_LINUX << 1;

    /** Operating system is IBM AIX. */
    public static final int OS_AIX = OS_HP << 1;

    /** Operating system is SGI IRIX. */
    public static final int OS_IRIX = OS_AIX << 1;

    /** Operating system is Sun OS. */
    public static final int OS_SUNOS = OS_IRIX << 1;

    /** Operating system is Compaq TRU64 Unix. */
    public static final int OS_TRU64 = OS_SUNOS << 1;

    /** Operating system is OS/2. */
    public static final int OS_OS2 = OS_TRU64 << 2;

    /** Operating system is Mac. */
    public static final int OS_MAC = OS_OS2 << 1;

    /** Operating system is Windows 2000. */
    public static final int OS_WIN2000 = OS_MAC << 1;

    /** Operating system is Compaq OpenVMS. */
    public static final int OS_VMS = OS_WIN2000 << 1;

    /** Operating system is one of the Windows variants but we don't know which one it is. */
    public static final int OS_WIN_OTHER = OS_VMS << 1;

    /** Operating system is unknown. */
    public static final int OS_OTHER = OS_WIN_OTHER << 1;

    /** Operating system is FreeBSD. @since 4.50 */
    public static final int OS_FREEBSD = OS_OTHER << 1;

    /** A mask for Windows platforms. */
    public static final int OS_WINDOWS_MASK = OS_WINNT | OS_WIN95 | OS_WIN98 | OS_WIN2000 | OS_WIN_OTHER;

    /** A mask for Unix platforms. */
    public static final int OS_UNIX_MASK = OS_SOLARIS | OS_LINUX | OS_HP | OS_AIX | OS_IRIX | OS_SUNOS | OS_TRU64 |
        OS_MAC | OS_FREEBSD;

    /** The operating system. */
    private static int operatingSystem = -1;
    
    /** Get the operating system on which NetBeans is running.
    * @return one of the <code>OS_*</code> constants (such as {@link #OS_WINNT})
    */
    public static int getOperatingSystem() {
        if (operatingSystem == -1) {
            String osName = System.getProperty("os.name");

            if ("Windows NT".equals(osName)) { // NOI18N
                operatingSystem = OS_WINNT;
            } else if ("Windows 95".equals(osName)) { // NOI18N
                operatingSystem = OS_WIN95;
            } else if ("Windows 98".equals(osName)) { // NOI18N
                operatingSystem = OS_WIN98;
            } else if ("Windows 2000".equals(osName)) { // NOI18N
                operatingSystem = OS_WIN2000;
            } else if (osName.startsWith("Windows ")) { // NOI18N
                operatingSystem = OS_WIN_OTHER;
            } else if ("Solaris".equals(osName)) { // NOI18N
                operatingSystem = OS_SOLARIS;
            } else if (osName.startsWith("SunOS")) { // NOI18N
                operatingSystem = OS_SOLARIS;
            }
            // JDK 1.4 b2 defines os.name for me as "Redhat Linux" -jglick
            else if (osName.endsWith("Linux")) { // NOI18N
                operatingSystem = OS_LINUX;
            } else if ("HP-UX".equals(osName)) { // NOI18N
                operatingSystem = OS_HP;
            } else if ("AIX".equals(osName)) { // NOI18N
                operatingSystem = OS_AIX;
            } else if ("Irix".equals(osName)) { // NOI18N
                operatingSystem = OS_IRIX;
            } else if ("SunOS".equals(osName)) { // NOI18N
                operatingSystem = OS_SUNOS;
            } else if ("Digital UNIX".equals(osName)) { // NOI18N
                operatingSystem = OS_TRU64;
            } else if ("OS/2".equals(osName)) { // NOI18N
                operatingSystem = OS_OS2;
            } else if ("OpenVMS".equals(osName)) { // NOI18N
                operatingSystem = OS_VMS;
            } else if (osName.equals("Mac OS X")) { // NOI18N
                operatingSystem = OS_MAC;
            } else if (osName.startsWith("Darwin")) { // NOI18N
                operatingSystem = OS_MAC;
            } else if (osName.toLowerCase(Locale.US).startsWith("freebsd")) { // NOI18N 
                operatingSystem = OS_FREEBSD;
            } else {
                operatingSystem = OS_OTHER;
            }
        }
        return operatingSystem;
    }
    
    /** Test whether NetBeans is running on some variant of Windows.
    * @return <code>true</code> if Windows, <code>false</code> if some other manner of operating system
    */
    public static boolean isWindows() {
        return (getOperatingSystem() & OS_WINDOWS_MASK) != 0;
    }

    /** Test whether NetBeans is running on some variant of Unix.
    * Linux is included as well as the commercial vendors, and Mac OS X.
    * @return <code>true</code> some sort of Unix, <code>false</code> if some other manner of operating system
    */
    public static boolean isUnix() {
        return (getOperatingSystem() & OS_UNIX_MASK) != 0;
    }
    
    /** Test whether the operating system supports icons on frames (windows).
    * @return <code>true</code> if it does <em>not</em>
    *
    */
    public static boolean isLargeFrameIcons() {
        return (getOperatingSystem() == OS_SOLARIS) || (getOperatingSystem() == OS_HP);
    }

    /**
     * Finds out the monitor where the user currently has the input focus.
     * This method is usually used to help the client code to figure out on
     * which monitor it should place newly created windows/frames/dialogs.
     *
     * @return the GraphicsConfiguration of the monitor which currently has the
     * input focus
     */
    private static GraphicsConfiguration getCurrentGraphicsConfiguration() {
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focusOwner != null) {
            Window w = SwingUtilities.getWindowAncestor(focusOwner);
            if (w != null) {
                return w.getGraphicsConfiguration();
            }
        }

        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Returns the usable area of the screen where applications can place its
     * windows.  The method subtracts from the screen the area of taskbars,
     * system menus and the like.  The screen this method applies to is the one
     * which is considered current, ussually the one where the current input
     * focus is.
     *
     * @return the rectangle of the screen where one can place windows
     *
     * @since 2.5
     */
    public static Rectangle getUsableScreenBounds() {
        return getUsableScreenBounds(getCurrentGraphicsConfiguration());
    }

    /**
     * Returns the usable area of the screen where applications can place its
     * windows.  The method subtracts from the screen the area of taskbars,
     * system menus and the like.
     *
     * @param gconf the GraphicsConfiguration of the monitor
     * @return the rectangle of the screen where one can place windows
     *
     * @since 2.5
     */
    public static Rectangle getUsableScreenBounds(GraphicsConfiguration gconf) {
        if (gconf == null) {
            gconf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        }

        Rectangle bounds = new Rectangle(gconf.getBounds());

        String str;

        str = System.getProperty("netbeans.screen.insets"); // NOI18N

        if (str != null) {
            StringTokenizer st = new StringTokenizer(str, ", "); // NOI18N

            if (st.countTokens() == 4) {
                try {
                    bounds.y = Integer.parseInt(st.nextToken());
                    bounds.x = Integer.parseInt(st.nextToken());
                    bounds.height -= (bounds.y + Integer.parseInt(st.nextToken()));
                    bounds.width -= (bounds.x + Integer.parseInt(st.nextToken()));
                } catch (NumberFormatException ex) {
                    Logger.getAnonymousLogger().log(Level.WARNING, null, ex);
                }
            }

            return bounds;
        }

        str = System.getProperty("netbeans.taskbar.height"); // NOI18N

        if (str != null) {
            bounds.height -= Integer.getInteger(str, 0).intValue();

            return bounds;
        }

        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Insets insets = toolkit.getScreenInsets(gconf);
            bounds.y += insets.top;
            bounds.x += insets.left;
            bounds.height -= (insets.top + insets.bottom);
            bounds.width -= (insets.left + insets.right);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, null, ex);
        }

        return bounds;
    }
}

/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * JVM.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.utils;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

// TODO: Auto-generated Javadoc
/**
 * Deals with the different version of the Java Virtual Machine. <br>
 */
public class JVM {

	/** The Constant JDK1_0. */
	public final static int JDK1_0 = 10;
	
	/** The Constant JDK1_1. */
	public final static int JDK1_1 = 11;
	
	/** The Constant JDK1_2. */
	public final static int JDK1_2 = 12;
	
	/** The Constant JDK1_3. */
	public final static int JDK1_3 = 13;
	
	/** The Constant JDK1_4. */
	public final static int JDK1_4 = 14;
	
	/** The Constant JDK1_5. */
	public final static int JDK1_5 = 15;
	
	/** The Constant JDK1_6. */
	public final static int JDK1_6 = 16;
	
	/** The Constant JDK1_6_U10_AND_AFTER. */
	public final static int JDK1_6_U10_AND_AFTER = 17;
	
	/** The is jd k1_6_ u10. */
	public static boolean isJDK1_6_U10 = false;
	
	/** The is jd k1_6_ u11. */
	public static boolean isJDK1_6_U11 = false;
//	public static boolean isJDK1_6_U12 = false;
	/** The Constant JDK1_7. */
public final static int JDK1_7 = 30;
	
	/** The Constant JDK1_8. */
	public final static int JDK1_8 = 31;
	
	/** The Constant JDK1_9. */
	public final static int JDK1_9 = 32;

	/** The current. */
	private static JVM current;
	static {
		current = new JVM();
	}

	/**
	 * Current.
	 *
	 * @return the current JVM object
	 */
	public static JVM current() {
		return current;
	}

	/** The jdk version. */
	private int jdkVersion;

	/**
	 * Creates a new JVM data from the <code>java.version</code>
	 * System property
	 *  
	 */
	public JVM() {
		this(System.getProperty("java.version"));
	}

	/**
	 * Constructor for the OS object.
	 *
	 * @param p_JavaVersion the p_ java version
	 */
	public JVM(String p_JavaVersion) {
		if (p_JavaVersion.startsWith("1.9.")) {
			jdkVersion = JDK1_9;
		} 
		else if (p_JavaVersion.startsWith("1.8.")) {
			jdkVersion = JDK1_8;
		} 
		else if (p_JavaVersion.startsWith("1.7.")) {
			jdkVersion = JDK1_7;
		} 
		else if (p_JavaVersion.startsWith("1.6.")) 
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel".equals(info.getClassName())
						||"javax.swing.plaf.nimbus.NimbusLookAndFeel".equals(info.getClassName())) //java1.7及以后版本该类被移至此
				{
					jdkVersion = JDK1_6_U10_AND_AFTER;//意味java版本是大于或等于java1.6.0_10
					break;
				}
			}

			//如果是1.6.0_10及以上版本，再来看看具体是哪个版本
			if(jdkVersion == JDK1_6_U10_AND_AFTER)
			{
				if(p_JavaVersion.startsWith("1.6.0_10"))//java 1.6.0 update 10
					isJDK1_6_U10 = true;
				if(p_JavaVersion.startsWith("1.6.0_11"))//java 1.6.0 update 11
					isJDK1_6_U11 = true;
//				else if(p_JavaVersion.startsWith("1.6.0_12"))//java 1.6.0 update 12
//					isJDK1_6_U12 = true;
			}

			jdkVersion = jdkVersion == 0 ? JDK1_6 : jdkVersion;
		} else if (p_JavaVersion.startsWith("1.5.")) {
			jdkVersion = JDK1_5;
		} else if (p_JavaVersion.startsWith("1.4.")) {
			jdkVersion = JDK1_4;
		} else if (p_JavaVersion.startsWith("1.3.")) {
			jdkVersion = JDK1_3;
		} else if (p_JavaVersion.startsWith("1.2.")) {
			jdkVersion = JDK1_2;
		} else if (p_JavaVersion.startsWith("1.1.")) {
			jdkVersion = JDK1_1;
		} else if (p_JavaVersion.startsWith("1.0.")) {
			jdkVersion = JDK1_0;
		} else {
			// unknown version, assume 1.3
			jdkVersion = JDK1_3;
		}
	}

	/**
	 * Checks if is or later.
	 *
	 * @param p_Version the p_ version
	 * @return true, if is or later
	 */
	public boolean isOrLater(int p_Version) {
		return jdkVersion >= p_Version;
	}

	/**
	 * Checks if is one dot one.
	 *
	 * @return true, if is one dot one
	 */
	public boolean isOneDotOne() {
		return jdkVersion == JDK1_1;
	}

	/**
	 * Checks if is one dot two.
	 *
	 * @return true, if is one dot two
	 */
	public boolean isOneDotTwo() {
		return jdkVersion == JDK1_2;
	}

	/**
	 * Checks if is one dot three.
	 *
	 * @return true, if is one dot three
	 */
	public boolean isOneDotThree() {
		return jdkVersion == JDK1_3;
	}

	/**
	 * Checks if is one dot four.
	 *
	 * @return true, if is one dot four
	 */
	public boolean isOneDotFour() {
		return jdkVersion == JDK1_4;
	}

	/**
	 * Checks if is one dot five.
	 *
	 * @return true, if is one dot five
	 */
	public boolean isOneDotFive() {
		return jdkVersion == JDK1_5;
	}

	/**
	 * Checks if is one dot six.
	 *
	 * @return true, if is one dot six
	 */
	public boolean isOneDotSix() {
		return jdkVersion == JDK1_6
		||jdkVersion==JDK1_6_U10_AND_AFTER;//如果JDK是1.6_u10及以上那当然也是JDK1.6大版本了
	}

//	/**
//	 * Determines if the version of JDK1_6 has Nimbus Look and Feel installed.
//	 * 是否是JDK1.6_u10及以上版本（这个版本很重要，包括Nimbus新主题及透明窗口等新特性）.
//	 * <p>
//	 * 另一个需要注意地方：窗口透明在此版本上存在一个BUG：6750920，所以此版本在BE LNF将不作为支持窗口透明特性
//	 * 
//	 * @return {@code true} if Nimbus is available and the version is 1.6;
//	 *         {@code false} otherwise
//	 */
//	public boolean isOneDotSixUpdate10() {
//		return jdkVersion == JDK1_6_U10_AND_AFTER;
//	}
	
	/**
	 * Determines if the version of JDK1_6 has Nimbus Look and Feel installed.
	 * 是否是JDK1.6_u10及以上版本（这个版本很重要，包括Nimbus新主题及透明窗口等新特性）.
	 * <p>
	 * 另一个需要注意地方：窗口透明在u10版本上存在一个BUG：6750920，所以u10版本在BE LNF将不作为支持窗口透明特性
	 * 
	 * @return {@code true} if Nimbus is available and the version is 1.6;
	 *         {@code false} otherwise
	 */
	public boolean isOneDotSixUpdate12OrAfter() {
//		return jdkVersion == JDK1_6_U12;
//		if(isJDK1_6_U12)
//			return true;
//		else
			//如何时判断是12以上版呢？大版本是update10及以上 且 不是u10也不是u11，那必然就是u12或以上版本了
			return (jdkVersion == JDK1_6_U10_AND_AFTER
						&& !isJDK1_6_U10 && !isJDK1_6_U11)
					|| jdkVersion >= JDK1_7;
	}

	/**
	 * Checks if is one dot seven.
	 *
	 * @return true, if is one dot seven
	 */
	public boolean isOneDotSeven() {
		return jdkVersion == JDK1_7;
	}
	
	/**
	 * Checks if is one dot eight.
	 *
	 * @return true, if is one dot eight
	 */
	public boolean isOneDotEight() {
		return jdkVersion == JDK1_8;
	}
	
	/**
	 * Checks if is one dot nine.
	 *
	 * @return true, if is one dot nine
	 */
	public boolean isOneDotNine() {
		return jdkVersion == JDK1_9;
	}
}
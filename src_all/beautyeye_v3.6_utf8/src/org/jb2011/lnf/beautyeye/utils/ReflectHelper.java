/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * ReflectHelper.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package org.jb2011.lnf.beautyeye.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * 提供了反射方便方法的辅助类.
 * 
 * @author Jack Jiang(jb2011@163.com), 2012-08-29
 * @version 1.0
 */
public class ReflectHelper
{
	/**
	 * 通过反射调用指定类名的指定静态方法.
	 * 
	 * @param theClassFullName 类全名，形如：javax.swing.JTable
	 * @param methodName 要调用的方法名
	 * @param paramsType 要调用的方法参数类型，无参数则传null
	 * @param paramsValue 要调用的方法参数值，无参数值则传null
	 * @return 如果该方法有返回值且正常调用则返回该值，否则返回null
	 */
	public static Object invokeStaticMethod(String theClassFullName, String methodName
			, Class[] paramsType, Object[] paramsValue)
	{
		return invokeStaticMethod(getClass(theClassFullName), methodName, paramsType, paramsValue);
	}
	/**
	 * 通过反射调用指定Class对象的指定静态方法.
	 * 
	 * @param theClass 类
	 * @param methodName 要调用的方法名
	 * @param paramsType 要调用的方法参数类型，无参数则传null
	 * @param paramsValue 要调用的方法参数值，无参数值则传null
	 * @return 如果该方法有返回值且正常调用则返回该值，否则返回null
	 */
	public static Object invokeStaticMethod(Class theClass, String methodName
			, Class[] paramsType, Object[] paramsValue)
	{
		return invokeMethod(theClass, theClass, methodName, paramsType, paramsValue);
	}
	
	/**
	 * 通过反射调用指定Class对象的指定方法（包括静态方法和实例方法）.
	 * 
	 * @param theClassFullName 类全名，形如：javax.swing.JTable
	 * @param theObject 要调用方法所对应的类对象，如果要调用的是静态方法则本参数与theClassFullName是同一个Class对象哦
	 * @param methodName 要调用的方法名
	 * @param paramsType 要调用的方法参数类型，无参数则传null
	 * @param paramsValue 要调用的方法参数值，无参数值则传null
	 * @return 如果该方法有返回值且正常调用则返回该值，否则返回null
	 */
	public static Object invokeMethod(String theClassFullName, Object theObject, String methodName
			, Class[] paramsType, Object[] paramsValue)
	{
		Class theClass = getClass(theClassFullName);
		//静态方法的被调用对象就是这个类的Class对象本身哦
		return invokeMethod(theClass, theClass, methodName, paramsType, paramsValue);
	}
	/**
	 * 通过反射调用指定Class对象的指定方法（包括静态方法和实例方法）.
	 * 
	 * @param theClass 类
	 * @param theObject 要调用方法所对应的类对象，如果要调用的是静态方法则本参数与theClass是同一个Class对象哦
	 * @param methodName 要调用的方法名
	 * @param paramsType 要调用的方法参数类型，无参数则传null
	 * @param paramsValue 要调用的方法参数值，无参数值则传null
	 * @return 如果该方法有返回值且正常调用则返回该值，否则返回null
	 */
	public static Object invokeMethod(Class theClass, Object theObject, String methodName
			, Class[] paramsType, Object[] paramsValue)
	{
		Object ret = null;
		if(theClass != null)
		{
			try
			{
				Method m = theClass.getMethod(methodName, paramsType);
				ret = m.invoke(theObject, paramsValue);
//				 System.out.println("@通过反射调用方法"+theClass.getName()+"."+methodName
//										   +"("+Arrays.toString(paramsType)+")成功.");
			}
			catch (Exception e)
			{
				if(BeautyEyeLNFHelper.debug)
					e.printStackTrace();
				
				LogHelper.error("通过反射调用方法"+theClass.getName()+"."+methodName
						+"("+Arrays.toString(paramsType)+")失败，您的JRE版本可能需要更新，"+e.getMessage());
			}
		}
		return ret;
	}

	/**
	 * 通过反射获得指定全类名的Class对象.
	 * 
	 * @param className 全类名，形如：javax.swing.JTable
	 * @return 如果成功则返回该类的Class对象，否则返回null
	 */
	public static Class getClass(String className)
	{
		try
		{
			return Class.forName(className);
		}
		catch (Exception e)
		{
			LogHelper.error("通过反射获得"+className+"的Class对象失败，您的JRE版本可能需要更新，"+e.getMessage());
			return null;
		}
	}
}

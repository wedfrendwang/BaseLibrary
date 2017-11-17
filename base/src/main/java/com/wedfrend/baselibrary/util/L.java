package com.wedfrend.baselibrary.util;

import android.util.Log;

import com.wedfrend.baselibrary.BaseLibrary;

/**
 * Log统一管理类
 * 
 * @author wedfrend
 * 
 */
public class L
{

	private L()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	private static final String TAG = "way";

	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.d(TAG, msg);
	}

	public static void e(String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg)
	{
		if (BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication)
			Log.i(tag, msg);
	}
}
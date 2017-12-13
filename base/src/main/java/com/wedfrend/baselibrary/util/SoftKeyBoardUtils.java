package com.wedfrend.baselibrary.util;

import android.content.Context;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软件盘管理类
 * 
 * 
 */
public class SoftKeyBoardUtils
{
	private static final String TAG = "SoftKeyBoardUtils";
	/**
	 * 打开软件盘
	 * @param mEditText
	 * @param mContext
	 */
	public static void openKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软件盘
	 * @param mEditText
	 * @param mContext
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}


	/**
	 * 关闭软件盘：这个跟具体控件无关
	 * @param mContext
	 * @param window
     */
	public static void closeKeybord( Context mContext, Window window)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if( imm.isActive()){
		imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);}
	}
	
	
	
	/**
	 * 软键盘是否显示
	 * @param mContext
	 * @return
	 */
	public static boolean isOpenKeyBord(Context mContext){
		
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		L.i(TAG,"软键盘是否弹起" + imm.isActive());
		return imm.isActive();
		
	}
	
	
	
	
	
}


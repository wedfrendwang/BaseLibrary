package com.wedfrend.baselibrary.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.wedfrend.baselibrary.BaseLibrary;

/**
 * Toast统一管理类
 * 
 */
public class T
{

	private T()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 1  长时间
	 * 0  短时间
	 * @param message
	 * @param i
     */
	public static void show(String message, int i){
		Toast.makeText(BaseLibrary.newInstance().getContext(), message, i).show();
	}

	public static void show(int ResId,int i){
		Toast.makeText(BaseLibrary.newInstance().getContext(), ResId, i).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}


	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 *  @param context
	  * @param message
	 */
	static Toast toast;
	public static void showCenter(int message){
		if(toast == null){
			toast= new Toast(BaseLibrary.newInstance().getContext());
			toast.setGravity(Gravity.CENTER,0,0);
		}
		toast.setText(message);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}



	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
			Toast.makeText(context, message, duration).show();
	}





}
package com.wedfrend.baselibrary.util;


import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *
 * SnackBar的提示语句类
 *
 * 这里面只提供 @resId的方法，因为国际化的考虑，所有字段都将从string.xml中读取
 *
 * Created by welive on 2017/6/13.
 */

public class S  {
    private S() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }



    /**
     * 短时间显示
     *
     * @param message
     */
    public static void showShort(View v, int message)
    {
            Snackbar.make(v,message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param message
     */
    public static void showLong(View v, int message)
    {
            Snackbar.make(v,message, Snackbar.LENGTH_LONG).show();
    }

    /**
     *
     * @param v
     * @param resId    提示名称
     * @param resIdAction   可点击事件的名称
     * @param listener      监听方法
     */
    public static void show(View v, int resId, int resIdAction, View.OnClickListener listener)
    {
            Snackbar.make(v,resId, Snackbar.LENGTH_LONG).setAction(resIdAction,listener).show();
    }

}

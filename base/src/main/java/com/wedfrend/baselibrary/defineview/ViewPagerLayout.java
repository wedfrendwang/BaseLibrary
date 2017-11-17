package com.wedfrend.baselibrary.defineview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/8/24 15:57
* desc: 设置是否可以滑动的ViewPager,在这里设置自定义属性事件
*/

public class ViewPagerLayout extends ViewPager {

    public ViewPagerLayout(Context context) {
        this(context,null);
    }

    public ViewPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //true表示可以滚动
    private boolean isScroll = true;

    /**
     * 设置viewPager是不是可以改动
     * @param noScroll
     */
    public void setNoScroll(boolean noScroll) {
        this.isScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /** return false;//super.onTouchEvent(arg0); */
        if (isScroll)
            return super.onTouchEvent(arg0);
        else
            return false;
    }

    //关键方法，在ViewPager中如果设置不可滚动，将该返回值返回 为false即可
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isScroll)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }






}

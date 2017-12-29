package com.wedfrend.baselibrary.defineview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/12/27 9:37
* desc: Width == Height LinearLayout
*/
public class EqualsLinearLayout extends LinearLayout {
    public EqualsLinearLayout(Context context) {
        super(context);
    }

    public EqualsLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqualsLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}

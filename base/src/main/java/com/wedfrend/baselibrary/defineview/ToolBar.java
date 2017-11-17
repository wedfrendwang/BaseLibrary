package com.wedfrend.baselibrary.defineview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/13 16:12
* desc: 自定义toolbar,以符合android开发的效果
*/

public class ToolBar extends Toolbar {
    private static final String TAG = "ToolBar";

    public ToolBar(Context context) {
        this(context,null);
    }

    public ToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        }

    private Context context;
    String text;
    public ToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

//    private View toolbar_back;
//    private TextView toolbar_title;
    /**
     * 界面绘制完成之后都会执行的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取第一个子布局
//        View viewParent = getChildAt(0);
//        //返回按钮
//        toolbar_back = viewParent.findViewById(R.id.toolbar_back);
//        //title按钮
//        toolbar_title = (TextView) viewParent.findViewById(R.id.toolbar_title);
    }

    private int textId;

    public int getTextId() {
        return textId;
    }

    /**
     * 设置相应的text文字
     * @param textId
     */
    public void setTextId(@StringRes int textId) {
        this.textId = textId;
//        toolbar_title.setText(textId);
    }
}

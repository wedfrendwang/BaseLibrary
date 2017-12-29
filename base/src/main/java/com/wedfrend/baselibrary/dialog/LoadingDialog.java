package com.wedfrend.baselibrary.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.wedfrend.baselibrary.R;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/21 16:11
* desc:
*/

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        this(context,R.style.DefineDialog);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        InitLoadingDialog();
    }

    private ImageView iv_loading;
    private ObjectAnimator objectAnimator;

    protected void InitLoadingDialog() {
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        iv_loading = ((ImageView) findViewById(R.id.iv_loading));
        objectAnimator = ObjectAnimator.ofFloat(iv_loading,"rotation",0.0f,360f);
    }

    @Override
    public void show() {
        super.show();
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    @Override
    public void cancel() {
        super.cancel();
        if(objectAnimator != null && objectAnimator.isStarted()){
            objectAnimator.end();//取消掉动画
        }
    }

    @Override
    public void onBackPressed() {

        //取消物理按钮作用
    }
}

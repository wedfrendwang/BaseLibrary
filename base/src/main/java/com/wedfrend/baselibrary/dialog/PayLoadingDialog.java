package com.wedfrend.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedfrend.baselibrary.R;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/22 15:27
* desc: 支付加载对话框
*/
public class PayLoadingDialog extends Dialog {


    public PayLoadingDialog(Context context) {
        this(context, R.style.DefineDialog);
    }

    public PayLoadingDialog(Context context, int themeResId) {
        super(context,themeResId);
        InitPayLoadingDialog();
    }



    private ImageView iv_image,iv_load;
    private TextView tv_pay;
    private String payHint = "订单确认中...";
    private int DrawableId = 0;
    private AnimationDrawable animationDrawable;

    protected void InitPayLoadingDialog() {
        setContentView(R.layout.dialog_pay_loading);
        iv_image = ((ImageView) findViewById(R.id.iv_image));
        tv_pay = ((TextView) findViewById(R.id.tv_pay));
        iv_load = ((ImageView) findViewById(R.id.iv_load));
        iv_image.setImageResource(R.drawable.pay_animal);
        animationDrawable = (AnimationDrawable) iv_image.getDrawable();
        setCanceledOnTouchOutside(false);

    }

    /**
     *
     */
    public void setParams(String payHint,int DrawableId){
        if(payHint != null){
            this.payHint = payHint;
        }
        if(DrawableId != 0){
            this.DrawableId = DrawableId;
        }

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void show() {
        tv_pay.setText(payHint);
        if(DrawableId != 0){
            iv_image.setImageResource(DrawableId);
        }
        animationDrawable.start();
        super.show();

    }

    @Override
    public void cancel() {
        super.cancel();
        animationDrawable.stop();
    }
}

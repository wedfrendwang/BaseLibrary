package com.wedfrend.baselibrary.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wedfrend.baselibrary.R;
import com.wedfrend.baselibrary.defineview.NavFrameLayout;
import com.wedfrend.baselibrary.dialog.LoadingDialog;
import com.wedfrend.baselibrary.util.L;


/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/9 17:56
* desc:  activity 父类
*/

public abstract class BaseAppCompatActivity extends AppCompatActivity implements View.OnClickListener,NavFrameLayout.LoadingListener {

    protected String TAG;
    /**
     * 初始化控件的抽象方法
     */
    public abstract void InitView();

    /**界面初始化数据或者请求网络数据实现该方法*/
    protected abstract void InitDate();
    /**
     * 在android程序中，由于手机系统原因，会有物理返回按钮
     * 而在目前市场上的APP的UI模式向IOS看起，所以在界面中也是有BACK按键，那么为了达到两者的功能的统一，抽象方法实现
     */
    public abstract void onBackPress();

    protected Dialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个值为activity除去包名的值
        TAG = getClass().getSimpleName();
        L.i(TAG,"方法开始执行------------------------------------------->");
        CollectorStack();
        /*锁死竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        InitView();
        loadingDialog = new LoadingDialog(this);
    }

    private boolean notBack;//默认为false
    /**
    設置toolbar的标题栏，并且设置是否物理返回键的功能和界面中的一致
     */
    private TextView tv;
    protected void setToolBarTitle(@StringRes int StrRes){
        tv = (TextView)findViewById(R.id.toolbar_title);
        tv.setText(StrRes);
        //将物理返回按钮设置跟界面toolbar返回按钮一致
        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPress();

            }
        });
        setNotBack(true);
    }

    /*
    界面重新加载
     */
    @Override
    protected void onResume() {
        super.onResume();
        InitDate();
    }



    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    /**
     * 对于Activity栈的管理
     * 由于android的设计模式，在activity的注册中有这几种可选
     * android:launchMode="singleInstance"singleTop,singleTask
     * 为了保证一致，需要遍历循环，好在移动端的栈不会堆积很多，在性能方面还是可以的。
     *
     * ps：如果在整个项目中感觉对整个类的影响并不大的情况，是可以不需要这个的
     */
    protected void CollectorStack(){
        //
        if(AppCompatActivityCollector.newInstance().searchActivity(getClass()))
            AppCompatActivityCollector.newInstance().removeAssignActivity(getClass());
        AppCompatActivityCollector.newInstance().addActivity(this);
        //打印在栈中的活动类。原理和Activity的压栈一致，查看目前栈中的类
        L.e(TAG,AppCompatActivityCollector.newInstance().checkActivity());
    }

    @Override
    protected void onDestroy() {//只要activity调用finish()方法，destroy()一定调用
        super.onDestroy();
        AppCompatActivityCollector.newInstance().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        //一般情况下，手机本身的back按键就是出栈的实现而已
        if(isNotBack())
            onBackPress();
        else
            super.onBackPressed();

    }


    /**
     * 界面跳转，不传递任何值
     * @param cls
     */
    public void startActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(BaseAppCompatActivity.this,cls);
        startActivity(intent);
    }
    /**
     * 向下一个Activity需要传递的参数字段
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<?> cls , Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(BaseAppCompatActivity.this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 网络异常界面所牵扯到的相应处理方法
     */
    @Override
    public void onLoading() {
        InitDate();
    }

    /**
    activity加载完成之后
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public boolean isNotBack() {
        return notBack;
    }

    public void setNotBack(boolean notBack) {
        this.notBack = notBack;
    }
}

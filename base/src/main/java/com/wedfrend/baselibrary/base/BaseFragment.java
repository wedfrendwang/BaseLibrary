package com.wedfrend.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wedfrend.baselibrary.defineview.NavFrameLayout;
import com.wedfrend.baselibrary.util.L;


/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/8/25 14:57
* desc: BaseFragment与Activity的使用
 *
 *      关于Fragment的相应一些方法
 *      一：onHiddenChanged()方法：当整个Fragment第一次加载，且与Activity连用的时候使用hide()与show()方法
 *          当 Fragment在被隐藏的时候，该方法会执行，隐藏的时候该值为true,显示的时候为false
 *
*/

public abstract class BaseFragment extends Fragment implements NavFrameLayout.LoadingListener,View.OnClickListener{

    protected String TAG;
    protected View mContentView;
    protected BaseAppCompatActivity mActivity;
    protected String UID,XQID;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();
        L.i(TAG,"fragment方法开始执行");
        mActivity = (BaseAppCompatActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected LayoutInflater inflater;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        if (mContentView == null) {
            initView(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    /**
     * 初始化View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected void setContentView(@LayoutRes int layoutResID) {
        mContentView = inflater.inflate(layoutResID, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }

    @Override
    public void onResume() {
        super.onResume();
//        UID =(String) SPUtils.get(SPUtilsParams.UID,"");
//        XQID =(String) SPUtils.get(SPUtilsParams.XQID,"");
        if(!isHidden()){//
            /*
            当Fragment 第一次实例化，该值返回为false
             */
            onLazyLoadOnce();
        }


    }

    /**
     * 进行数据加载或者访问网络数据的时候
     */
    public abstract void onLazyLoadOnce();

    @Override
    public void onLoading() {
        //点击网络异常刷新界面执行加载界面的方法
        onLazyLoadOnce();
    }

    /**
     * 正常使用Fragment的切换使用，被隐藏时为true，显示为false
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){//为false的时候才回执行，为真的话真能证明此时的Fragment不在当前显示
            onLazyLoadOnce();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

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
* desc: Fragment与ViewPager的使用
 *
 *      关于Fragment的相应一些方法
 *      setUserVisibleHint()方法，很有趣，而且网上所有的懒加载方法的使用都与该方法相关
 *          前提：Fragment与ViewPager连用的时候该方法才会被主动的调用
 *          1:ViewPager适配器：首先会将所有的Fragment的setUserVisibleHint的值统一设置为false
 *          2:然后执行整个Fragment的运行流程中
 *             onAttach---onCreate---setUserVisibleHint---onCreateView-----
 *             这里又一次调用setUserVisibleHint，所以懒加载需要注意的是对OnResume的整个操作
 *
*/

public abstract class BaseViewPagerFragment extends Fragment implements NavFrameLayout.LoadingListener{

    private boolean injected = false;

    protected String TAG;
    protected View mContentView;
    protected BaseAppCompatActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();
        mActivity = (BaseAppCompatActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Fragment与ViewPager连用的时候，会触发该方法
     * 显示为true
     * @param isVisibleToUser
     */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.i(TAG,"设置当前的相应显示判断参数");
    }

    /**
     * 请求网络数据或者数据的初始化
     */
    protected abstract void onLazyLoadOnce();

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

    /**
    设置界面
     */

    protected void setContentView(@LayoutRes int layoutResID) {
        mContentView = inflater.inflate(layoutResID, null);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        //当ViewPager开始实例化的时候，会先将所有的设置为false
        if(getUserVisibleHint()){//判断只有当该Fragment在界面显示的时候进行数据的加载以及显示
            onLazyLoadOnce();
        }
    }

    /**页面加载异常*/
    @Override
    public void onLoading() {
        onLazyLoadOnce();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public abstract void clearCache();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放相应资源，清除相应缓存
        clearCache();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

}

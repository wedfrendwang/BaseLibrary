package com.wedfrend.baselibrary.defineview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wedfrend.baselibrary.Enum.EnumContent;
import com.wedfrend.baselibrary.R;
import com.wedfrend.baselibrary.util.T;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/8/29 11:11
* desc: 在手机端加载过程中，会出现很多的状况
 *
 *      1：加载中
 *      2：数据出错
 *      3：网络异常
 *      4：重新加载
 *
 *      为了方便的统一管理，所以自定义View来进行相应的加载状态方法
 *
*/

public class NavFrameLayout extends FrameLayout {

    private FrameLayout wholeFrameLayout;
    //布局中的子页面
    private View mContentView;
    private LayoutInflater layoutInflater;
    //不同的状态
    private View statusView;

    private EnumContent.statusPage statusPage = EnumContent.statusPage.LOADING;

    public EnumContent.statusPage getStatusPage() {
        return statusPage;
    }

    public void setStatusPage(EnumContent.statusPage statusPage) {
        this.statusPage = statusPage;
    }

    private  int loadingLayoutId = R.layout.z_loading_layout;
    private  int dateErrorLayoutId = R.layout.z_daterror_layout;
    private  int netErrorLayoutId = R.layout.z_neterror_layout;

    public NavFrameLayout(Context context) {
        this(context,null);
    }

    public NavFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NavFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitDefault(context, attrs, defStyleAttr);
    }
    //初始化
    public void InitDefault(Context context, AttributeSet attrs, int defStyleAttr){
        wholeFrameLayout = new FrameLayout(context);
        layoutInflater = LayoutInflater.from(context);
        wholeFrameLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(wholeFrameLayout);
        //加载界面的显示
        setViewLoading(loadingLayoutId);
    }

    /**
    界面绘制完成之后会主动掉用，但是该类中只能有一个子文件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() != 2){
            //抛出运行时异常
            throw new RuntimeException(NavFrameLayout.class.getSimpleName() + "必须有且只有一个子控件");
        }
        mContentView = getChildAt(1);
        mContentView.setVisibility(View.GONE);
    }

    /**
     * 正在加载
     * @param layoutResID
     */
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ImageView iv_loading;
    ObjectAnimator objectAnimator;

    /**
     * 需要重新定义自己的加载界面，重写这个方法
     * @param layoutResID
     */
    public void setViewLoading(@LayoutRes int layoutResID){
        statusPage = EnumContent.statusPage.LOADING;
        if (statusView != null && statusView.getParent() != null) {
            ViewGroup parent = (ViewGroup) statusView.getParent();
            parent.removeView(statusView);
        }
        statusView = layoutInflater.inflate(layoutResID,null);
        if (statusView != null) {
            statusView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            wholeFrameLayout.addView(statusView);
        }
        statusView.setVisibility(View.VISIBLE);

        /*
        适合微住项目的界面设置
         */
        iv_loading = ((ImageView) statusView.findViewById(R.id.iv_loading));
        objectAnimator = ObjectAnimator.ofFloat(iv_loading,"rotation",0.0f,360f);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
//        swipeRefreshLayout = ((SwipeRefreshLayout) statusView.findViewById(R.id.refresh_loading));
//        swipeRefreshLayout.setProgressViewOffset(true, 50, 100);
//        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
//        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
//        //下拉刷新调用的一个接口
//        swipeRefreshLayout.setColorSchemeResources(R.color.color_bbb, R.color.color_bbb, R.color.color_bbb);
//        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
    }

    /**
     * 设置界面正常加载的情况下
     */
    public void setNormal(){

        if(statusView.getVisibility() == View.VISIBLE){
            //异常界面隐藏
            statusView.setVisibility(View.GONE);
            //正常界面显示
            mContentView.setVisibility(View.VISIBLE);
        }
        //正在加载中
        if(statusPage.equals(EnumContent.statusPage.LOADING)){
            //取消加载界面的显示
            if(objectAnimator != null&&objectAnimator.isStarted()){
                objectAnimator.cancel();
            }
        }
        //数据出现异常
        else if(statusPage.equals(EnumContent.statusPage.DATEERROR)){
        }
        //网络出现异常
        else if(statusPage.equals(EnumContent.statusPage.NETERROR)){
        }
        setStatusPage(EnumContent.statusPage.NONE);
    }

    /**
     *
     * @param status 异常状态 LOADING,DATEERROR,NETERROR
     * @param imgId  图片ID
     * @param StringId 提示字段ID
     */
    public void setException(EnumContent.statusPage status, @Nullable @DrawableRes int imgId, Object StringId){
        if(statusPage.equals(EnumContent.statusPage.NONE)){
            //表示已经正常显示过一次了，所以之后的所有异常只是进行一次提示
            if(status.equals(EnumContent.statusPage.DATEERROR)){
                if(StringId instanceof String){
                    T.show((String) StringId,0);
                }else if(StringId instanceof Integer){
                    T.show((Integer) StringId,0);
                }
            }else if(status.equals(EnumContent.statusPage.NETERROR)){
                T.show(R.string.net_error,0);
            }
            //结束异常显示的过程
            return;
        }
        //一般情况这个不会发生的，如果statusView一旦被隐藏，那么就是至少有一次界面属于正常成功的情况
        if(statusView.getVisibility() == View.GONE){
            //正常界面隐藏
            mContentView.setVisibility(View.GONE);
            //异常界面显示
            statusView.setVisibility(View.VISIBLE);
        }
        /**
         * 判断如果进入异常显示，如果上一步属于界面加载，那么需要取消动画显示
         */
        if(statusPage.equals(EnumContent.statusPage.LOADING)){
            if(objectAnimator != null && objectAnimator.isStarted()){
                objectAnimator.end();//取消掉动画
            }
        }
        //将你的值赋值给全局变量
        statusPage = status;
        //判断是否是加载界面
        if(status.equals(EnumContent.statusPage.LOADING)){
            //加载界面显示
            setViewLoading(loadingLayoutId);
            return;
        }
        //数据出现异常
        if(status.equals(EnumContent.statusPage.DATEERROR)){
            setViewDateError(dateErrorLayoutId,imgId,(StringId instanceof Integer)?(Integer) StringId:R.string.not_date);
            return;
        }
        //网络出现异常
        if(status.equals(EnumContent.statusPage.NETERROR)){
            setViewNetError(netErrorLayoutId);
            return;
        }

    }


    /**
     * 数据异常
     * @param layoutResID 数据异常自定义的界面
     */
    ImageView imageView;
    TextView textView;
    public void setViewDateError( @LayoutRes int layoutResID,int ImageId,int strID){

        if (statusView != null && statusView.getParent() != null) {
            ViewGroup parent = (ViewGroup) statusView.getParent();
            parent.removeView(statusView);
        }
        statusView = layoutInflater.inflate(layoutResID,null);
        if (statusView != null) {
            statusView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            wholeFrameLayout.addView(statusView);
        }
        //下面要做些事情
        imageView = ((ImageView) statusView.findViewById(R.id.iv_errorDate));
        textView = ((TextView) statusView.findViewById(R.id.tv_dateHint));
        imageView.setImageResource(ImageId);
        textView.setText(strID);
    }

    /**
     * 网络异常
     * @param layoutResID 自定义网络异常的Layout界面
     */
    public void setViewNetError(@LayoutRes int layoutResID){

        if (statusView != null && statusView.getParent() != null) {
            ViewGroup parent = (ViewGroup) statusView.getParent();
            parent.removeView(statusView);
        }
        //设置默认的网络异常界面
        statusView = layoutInflater.inflate(layoutResID,null);
        //开始界面显示
        if (statusView != null) {
            statusView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            wholeFrameLayout.addView(statusView);
        }
        statusView.findViewById(R.id.tv_errorNet).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setException(EnumContent.statusPage.LOADING,0,0);
                //从新加载数据内容
                loadingListener.onLoading();
            }
        });
    }

    //这里还需要一个接口的触发时间，来证实界面的可行性
    public interface LoadingListener{
        void onLoading();
    }

    private LoadingListener loadingListener;

    public LoadingListener getLoadingListener() {
        return loadingListener;
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public void setLoadingLayoutId(int loadingLayoutId) {
        this.loadingLayoutId = loadingLayoutId;
    }

    public void setDateErrorLayoutId(int dateErrorLayoutId) {
        this.dateErrorLayoutId = dateErrorLayoutId;
    }

    public void setNetErrorLayoutId(int netErrorLayoutId) {
        this.netErrorLayoutId = netErrorLayoutId;
    }
}

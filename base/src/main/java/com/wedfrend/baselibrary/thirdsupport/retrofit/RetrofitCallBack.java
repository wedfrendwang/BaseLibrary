package com.wedfrend.baselibrary.thirdsupport.retrofit;



import com.wedfrend.baselibrary.util.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 自己封装的一个关于Retrofit的调用问题，以便在开发时候的测试以及输出
 * Created by welive on 2017/6/13.
 */

public class RetrofitCallBack<T> implements Callback<T> {

    private BaseCallBack<T> baseCallBack;
    private String TAG = "base";

    /**
     *
     * @param Tag  在输出的过程中，我希望请求在我调试过程可以清楚的看到，并且输出相应的内容
     * @param baseCallBack
     */
    public RetrofitCallBack(String Tag, BaseCallBack<T> baseCallBack) {

        this.baseCallBack = baseCallBack;
        this.TAG = Tag;
    }

    public RetrofitCallBack(BaseCallBack<T> baseCallBack) {

        this.baseCallBack = baseCallBack;
    }
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        L.i(TAG,"界面请求加载的数据");
        if(!response.isSuccessful()){
            baseCallBack.OnError(call,null);
            return;
        }
        if(response.body() == null)
            baseCallBack.OnError(call,null);
        else
            baseCallBack.onSuccess(call,response);

        L.i(TAG,response.body().toString());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        L.i(TAG,t.toString());
        //这里就只能提示网络异常，对吧，但是在我们的设计中网络异常是由特别的界面执行，这个还真是伤脑子
        baseCallBack.OnError(call,t);
    }

    public interface BaseCallBack<T>{

        void onSuccess(Call<T> call, Response<T> response);

        void OnError(Call<T> call, Throwable t);
    }


}

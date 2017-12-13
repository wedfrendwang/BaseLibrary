package com.wedfrend.baselibrary.thirdsupport.retrofit;



import com.wedfrend.baselibrary.BaseLibrary;
import com.wedfrend.baselibrary.util.L;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/10 10:43
* desc: 关于Retrofit2.0 + OkHttpClient 请求网址添加相应共同参数
*/
public class CommonParamsInterceptor implements okhttp3.Interceptor {
    private static final String TAG = "CommonParamsInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        if(request.method().toUpperCase().equals("GET")){
            HttpUrl.Builder builder = request.url().newBuilder();
            for (Map.Entry<String,String> entryItem: BaseLibrary.newInstance().getMapParams().entrySet()
                 ) {
                builder.addQueryParameter(entryItem.getKey(),entryItem.getValue());
            }
            HttpUrl httpUrl = builder.build();

            request = request.newBuilder().url(httpUrl).build();
            if(BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication) {
                L.e(TAG, "intercept: " + request.headers().toString());
                L.e(TAG, "intercept: " + request.method());
                L.e(TAG, "intercept: " + request.toString());
            }

        }else if(request.method().toUpperCase().equals("POST")){
            //post请求追加参数
            FormBody.Builder newBody = new FormBody.Builder();
            for (Map.Entry<String,String> entryItem: BaseLibrary.newInstance().getMapParams().entrySet()
                    ) {
                newBody.add(entryItem.getKey(),entryItem.getValue());
            }
            newBody.build();
            //拦截请求中用户传入的数据，把参数遍历放入新的body里面，然后进行一块提交
            FormBody oldBody = (FormBody) request.body();
            for (int i = 0; i < oldBody.size(); i++)
            {
                newBody.add(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
            //输出参数看看
            if(BaseLibrary.newInstance().getTypeApplication() != BaseLibrary.newInstance().ProduceApplication) {
                L.e(TAG, "intercept: " + request.headers().toString());
                L.e(TAG, "intercept: " + request.method());
                L.e(TAG, "intercept: " + request.toString());
                for (int i = 0; i < newBody.build().size(); i++)
                {
                    L.i( TAG, newBody.build().encodedName(i)+"---------->"+ newBody.build().encodedValue(i));
                }
            }
        }
        return chain.proceed(request);
    }
}
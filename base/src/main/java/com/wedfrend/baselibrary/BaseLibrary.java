package com.wedfrend.baselibrary;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wedfrend.baselibrary.thirdsupport.retrofit.CommonParamsInterceptor;
import com.wedfrend.baselibrary.util.BaseAppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/9 17:58
* desc:
 *     1：该BaseLibrary采用网络框架为  Retrofit2.0 + Retrofit Gson 进Json数据进行解析
 *     2：对于Retrofit如果对于使用方式不清楚，可以自己google查阅
 *     3：Retrofit内置相应的OkHttp网络框架
 *     4：本类对相应的功能进行封装,本类以单例模式进行必要开发
 *
 *        setContext()  -----传递上下文参数，建议在自己App项目中的Application中进行配置
 *
 *        设置整个App中请求访问的Api为测试环境还是生产环境
 *
 *        参数：TestApplication，ProduceApplication，ProduceTestApplication
 *
 *        setDomainUrl() -----设置生产环境的 Url
 *
 *        setTestDomainUrl() ----设置测试环境的 Url
 *
 *        setRetrofit() -----设置Retrofit.Builder基础配置
 *
 *        setOkHttpClient() ----设置OkHttp相应的参数
 *
 *        该类在使用前均可以使用
 *
*/

public class BaseLibrary {

    private static BaseLibrary baselibrary;

    public static BaseLibrary newInstance() {

        if(baselibrary == null){
             baselibrary = new BaseLibrary();
        }
        return baselibrary;
    }


    private Context context;

    /**
     * 传递上下文参数，建议在自己App项目中的Application中进行配置
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }
    public Context getContext() {
        return context;
    }

    public final Integer TestApplication = 1;/*纯粹的测试环境---》测试服网址并可以Debug*/
    public final Integer ProduceApplication = 2;/*纯粹生产环境---》生产服网址并不可以Debug*/
    public final Integer ProduceTestApplication = 3;/*生产测试环境---》生产服网址并可以Debug*/

    /*判断服务器为测试服还是正式服，并且在整个项目运行中是否debug输出*/
    private Integer TypeApplication = TestApplication;
    public Integer getTypeApplication() {
        return TypeApplication;
    }
    public void setTypeApplication(Integer typeApplication) {
        TypeApplication = typeApplication;
    }

    /*测试域名 例如：http:www.test.xxx/ 一定要添加 "/"   */
    private String TestDomainUrl;
    public String getTestDomainUrl() {
        return TestDomainUrl;
    }

    /**
     * 设置测试环境下的 Url
     * @param testDomainUrl
     */
    public void setTestDomainUrl(String testDomainUrl) {
        TestDomainUrl = testDomainUrl;
    }
    /*正式域名  例如：http:www.test.xxx/ 一定要添加 "/"   */
    private String DomainUrl;
    public String getDomainUrl() {
        return DomainUrl;
    }

    /**
     * 设置生产环境的 Url
     * @param domainUrl
     */
    public void setDomainUrl(String domainUrl) {
        DomainUrl = domainUrl;
    }

    /*关于使用Retrofit2的网络框架来进习行网络请求*/
    private Retrofit.Builder retrofitBuilder;

    /**
     * 第一自己的RetrofitBuilder
     * @param retrofitBuilder
     */
    public void setRetrofitBuilder(Retrofit.Builder retrofitBuilder) {
        this.retrofitBuilder = retrofitBuilder;
    }

    /**
     * 获取Retrofit.Builder
     * @return
     */
    public Retrofit getRetrofit(){
        if(retrofitBuilder != null){
            return retrofitBuilder.build();
        }else{
            //默认下的相关配置
            retrofitBuilder = new Retrofit.Builder();
            //判断测试服务器，或者正式服务器
            switch (TypeApplication){
                case 1:
                    retrofitBuilder.baseUrl(getTestDomainUrl());
                    break;
                //目前为2 和 3 都是正式服，只是Debug不同而已
                default:
                    retrofitBuilder.baseUrl(getDomainUrl());
                    break;
            }
            retrofitBuilder.client(getOkHttpClient());
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create(getGson()));
            return retrofitBuilder.build();
        }
    }

    /**
     * 默认配置Gson的一些请求处理
     */
    private Gson gson;

    public Gson getGson() {
        if(gson == null){
            gson = new GsonBuilder()
                    //序列化null
                    .serializeNulls().create();
        }
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

//                 new  GsonBuilder()
//                //序列化null
//                .serializeNulls().create();
//                // 设置日期时间格式，另有2个重载方法
//                // 在序列化和反序化时均生效
////                .setDateFormat("yyyy-MM-dd")
//                // 禁此序列化内部类
////                .disableInnerClassSerialization()
//                //生成不可执行的Json（多了 )]}' 这4个字符）
////                .generateNonExecutableJson()
//                //禁止转义html标签
////                .disableHtmlEscaping()
//                //格式化输出
////                .setPrettyPrinting()


    /* Retrofit2.0 + OKHttpClient 添加附带请求参数*/
    private Map<String,String> mapParams;
    public Map<String, String> getMapParams() {
        if(mapParams != null){
            return mapParams;
        }else{
            mapParams = new HashMap<>();
            mapParams.put("ver", BaseAppUtils.getVersionName(context));
            mapParams.put("device","android");
            return mapParams;
        }

    }

    public void setMapParams(Map<String, String> mapParams) {
        if(!mapParams.isEmpty())
            mapParams.clear();
        this.mapParams = mapParams;
    }

    /*OkHttpClient 相关初始化配置*/
    private OkHttpClient okHttpClient;

    public OkHttpClient getOkHttpClient() {
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder().addInterceptor(new CommonParamsInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).writeTimeout(20,TimeUnit.SECONDS).build();
        }
        return okHttpClient;

    }

    /*设置OkHttpClient的拦截器*/
    public void setOkHttpClient(okhttp3.Interceptor interceptor) {
        this.okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).writeTimeout(20,TimeUnit.SECONDS).build();
    }
    /*直接设置OkHttpClient*/
    public void setOkHttpClient(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }
}

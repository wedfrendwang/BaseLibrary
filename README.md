书写android项目基础类库整理

1：baseLibrary作为个人整理的类库进行代码的快速开发。
封装现阶段流行的网络框架Retrofit2.0，OkHttp的封装方法，测试正式debug等相关操作

使用方法：

对于任何引用该Library的开发者，其中重要的初始化内容在于 BaseLibrary.java类中

封装一

BaseLibrary 设置关于如何使用该基础类库的相关方法，目前并不是非常完善，但是足以解决应用型开发的需求

使用方法
        //设置上下文参数
        BaseLibrary.newInstance().setContext(context);
        //设置测试服务还是正式服务
        BaseLibrary.newInstance().setTypeApplication(typeApplication);
        //设置测试服务器地址
        BaseLibrary.newInstance().setTestDomainUrl(testDomainUrl);
        //设置正式服地址
        BaseLibrary.newInstance().setDomainUrl(domainUrl);
        //获取到Retrofit，并设置自己项目中的请求接口Engine
        BaseLibrary.newInstance().getRetrofit();


封装二  关于移动端界面显示过程中，出现  加载中，正在加载，加载错误，网络异常等状态进行相应封装
       创建自己开发控件NavFrameLayout.

       NavFrameLayout extends FrameLayout,并 设置相关方法，来进行界面的正常 显示与异常操作

       使用方法：结合实际开发逻辑需求，结合相应的网络操作

            //请求后台数据内容,使用Retrofit
            BusStopInitParams.newInstance().getEngine().RequestCarPort(BusStopInitParams.newInstance().getUid()).
                    enqueue(new RetrofitCallBack<CarPot>(TAG,new RetrofitCallBack.BaseCallBack<CarPot>() {
                @Override
                public void onSuccess(Call<CarPot> call, Response<CarPot> response) {
                    //数据封装，数据填充
                    if(response.body().getError().equals("0")){//成功的情况下
                       if(response.body().getDetail().getPark_attr()!=null){
                           //正常成功的情况下
                           navFrameLayout.setNormal();
                           toolbar_TvMenu.setText(R.string.paymentRecord);
                           toolbar_TvMenu.setVisibility(View.VISIBLE);
                           setDateToView(response.body());
                       }else{
                           navFrameLayout.setException(EnumContent.statusPage.DATEERROR,R.mipmap.nodate_busstop,R.string.noPortHint);
                       }

                    }else{//不为0，默认情况下视为异常
                        navFrameLayout.setException(EnumContent.statusPage.DATEERROR,R.mipmap.nodate_busstop,response.body().getErrorinfo());
                    }
                }

                @Override
                public void OnError(Call<CarPot> call, Throwable throwable) {
                    //数据解析异常
                    if(throwable == null){
                        navFrameLayout.setException(EnumContent.statusPage.DATEERROR,R.mipmap.nodate_busstop,R.string.noPortHint);
                    }else{//网络加载异常
                        navFrameLayout.setException(EnumContent.statusPage.NETERROR,R.mipmap.nowifi_bg,R.string.app_name);
                    }
                }
            }));

       NavFrameLayout 提供方法：

       枚举类型

        /**
         * 页面加载状态
         */
        public enum statusPage{
            NONE,/*界面处于正常情况下*/
            LOADING,/*界面处于加载情况下*/
            DATEERROR,/*界面处于数据出现异常情况下*/
            NETERROR/*界面处于网络异常情况下*/
        }

       /*
       设置当前界面处于的状态
       */
       public void setStatusPage(EnumContent.statusPage statusPage) {
               this.statusPage = statusPage;
           }


       /*设置相应的异常数据情况

       说明：在目前的项目设计中，所设计整理的类库为我所从事公司的项目服务，所有类库中的相关方法没有达到万能，只能以目前项目需求进行定制
            但是基类公共源码放在Github开源中，有兴趣的朋友可以下载下来随意改动，定制自己的类库


       @params status：异常情况
       @params imgId： 异常情况需要显示的图片
       @params StringId: 提示字段
       */
       public void setException(EnumContent.statusPage status, @Nullable @DrawableRes int imgId, Object StringId)


     /*数据一切正常情况下*/
     public void setNormal()

封装三：下拉刷新，上拉加载的相关封装

     总体思路与自定义NavFrameLayout 思想一致，自定义控件RefreshLayout extends LinearLayout

     1：创建刷新布局，在显示的时候设置距离跟布局顶端为 刷新控件的  高度
     2：创建加载布局，在正常情况下隐藏
     3：最为重要的是处理相关控件的滑动监听，不同时候的处理

     这个自定义控件的封装是参考一位大神的代码，BGARefreshLayout.该项目的刷新更加完全多样，我只是拿出一部分来创建属于自己项目的相关操作
     具体网址：https://github.com/bingoogolapple/BGARefreshLayout-Android


封装四：toolbar

    在之前的android开发中，我自己处理手机头部的内容，都是使用相应的一些布局来进行创建，但是自从
    Material Design 的设计中，无法与其他相关控件进行连用，所以自己定义ToolBar

重写一：重写RecyclerView Item的点击操作

       类 OnItemClickListener extends  RecyclerView.SimpleOnItemTouchListener

       使用方法：recycler_carport.addOnItemTouchListener(new OnItemClickListener(recycler_carport, new OnItemClickListener.onItemClickListener() {
                        @Override
                        public void onItemClick(View view, int i) {
                            //点击进行更新处理事件
                            if(ListDetailBean.get(i).getCurrent_status().equals("1")){
                                finish();
                                return;
                            }else{
                                //加载框显示出来
                                loadingDialog.show();
                                RequestWebService(CarportStatus.UPDATE,uid,ListDetailBean.get(i).getId(),"1");
                            }
                        }
                        @Override
                        public void onItemLongClick(View view, int i) {
                        }
                    }));

重写二：重写OkHttp中对于网络请求中的相关操作进行Interceptor监听，添加相应的公共请求参数，输出请求内容
















package com.wedfrend.baselibrary.Enum;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/8/28 16:22
* desc:
*/

public class EnumContent {

    /*
    关于网络的状态,这个可以使用广播进行数据的监听，比较方便
     */
    public enum NetWork{
        WIFI,MOBILE,NONE
    }

    /**
     * 页面加载状态
     */
    public enum statusPage{
        NONE,LOADING,DATEERROR,NETERROR
    }


    /**
     * 上下拉刷新的状态
     */

    public enum RefreshPage{
        NORMAL,REFRESH,LOADMOR
    }

}

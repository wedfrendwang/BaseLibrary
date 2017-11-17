package com.wedfrend.baselibrary.base;

import android.app.Activity;
import android.util.Log;

import com.wedfrend.baselibrary.util.L;

import java.util.Iterator;
import java.util.Stack;

/**
* author:wedfrend
* email:wedfrend@yeah.net
* create:2017/11/9 17:35
* desc: 关于Activity栈管理
*/

public class AppCompatActivityCollector {

    private static final String TAG = "Collector";

    private AppCompatActivityCollector() {
    }

    private static AppCompatActivityCollector activityCollector = new AppCompatActivityCollector();

    public static AppCompatActivityCollector newInstance() {
        return activityCollector;
    }

    private Stack<Activity> stackActivity;

    /**
     * 将活动添加入栈
     *
     * @param activity
     */

    public void addActivity(Activity activity) {
        if (stackActivity == null || stackActivity.isEmpty()) {
            stackActivity = new Stack<>();
        }
        L.i(TAG, "addActivity: " + activity.getClass());
        stackActivity.add(activity);
    }


    /**
     * 将活动移除出栈
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (!stackActivity.isEmpty()) {
            stackActivity.remove(activity);
        }
    }


    /**
     * 将制定的Activity移除出栈
     *
     * @param cls
     */
    public String removeAssignActivity(Class<?> cls) {

        if (!stackActivity.isEmpty()) {
            Iterator<Activity> iterator = stackActivity.iterator();
            while (iterator.hasNext()) {//执行遍历循环
                Activity activity = iterator.next();
                if (activity.getClass().equals(cls)) {//查看是否一致
                    //一致便将该activity移除
                    iterator.remove();
                    activity.finish();
                }
            }
        }
        return checkActivity();
    }

    /**
     * 查找当前栈中的Activity,这个只是防止在推送中要求的跳转功能。
     */
    public boolean searchActivity(Class<?> cls) {
        if(stackActivity == null || stackActivity.isEmpty())
            return false;
        if (!stackActivity.isEmpty()) {
            Iterator<Activity> iterator = stackActivity.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity.getClass().equals(cls)) {//查看是否一致
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查询当前栈中的Activity
     */
    public String checkActivity() {

        StringBuffer stringBuffer = new StringBuffer();
        if (!stackActivity.isEmpty()) {
            Iterator<Activity> iterator = stackActivity.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                L.d(TAG, "checkActivity: " + activity.getClass());
                stringBuffer.append(activity.getClass()+"\n");
            }
        }
        return stringBuffer.toString();
    }

    public void finishAllActivity(String activityParentName){

        if (!stackActivity.isEmpty()) {
            Iterator<Activity> iterator = stackActivity.iterator();
            while (iterator.hasNext()) {//执行遍历循环
                Activity activity = iterator.next();
                if(activity.getClass().getName().contains(activityParentName)){
                    iterator.remove();
                    activity.finish();
                }
            }
        }
    }


    /*
    该方法如果不是最终App使用类就不要进行调用
     */
    public void finishApplication(){

        if (!stackActivity.isEmpty()) {
            Iterator<Activity> iterator = stackActivity.iterator();
            while (iterator.hasNext()) {//执行遍历循环
                Activity activity = iterator.next();
                iterator.remove();
                activity.finish();
            }
            //杀掉进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}








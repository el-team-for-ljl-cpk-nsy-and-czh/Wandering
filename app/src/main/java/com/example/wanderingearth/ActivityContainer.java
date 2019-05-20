package com.example.wanderingearth;

import android.app.Activity;

import java.util.ArrayList;

/*
使用ActivityContainer,在要结束应用程序时遍历每一个activity使其全部停止；
 */

public class ActivityContainer {
    private static ActivityContainer instance = new ActivityContainer();
    private static ArrayList<Activity> activityStack = new ArrayList<>();
    public static ActivityContainer getInstance(){
        return instance;
    }
    public void addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new ArrayList<Activity>();
        }
        activityStack.add(activity);
    }
    public void removeActivity(Activity activity){
        if(activity != null){
            activityStack.remove(activity);
        }
    }
    public void finishAllActivity(){
        for(int i = 0;i<activityStack.size();i++){
            if(activityStack.get(i)!=null){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
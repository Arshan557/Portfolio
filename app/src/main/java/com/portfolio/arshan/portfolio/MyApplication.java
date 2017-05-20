package com.portfolio.arshan.portfolio;

import android.app.Application;
import android.content.Context;

/**
 * Created by Arshan on 20-Sep-2016.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }
    public static MyApplication getInstance(){
        return  mInstance;
    }
    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}

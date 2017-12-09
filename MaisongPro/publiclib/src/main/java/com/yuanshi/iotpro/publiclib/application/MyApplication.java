package com.yuanshi.iotpro.publiclib.application;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/6/28.
 */

public class MyApplication extends Application {
    public static final ThreadPoolExecutor THREAD_EXCUTER  = new ThreadPoolExecutor(100,200,15, TimeUnit.SECONDS,new ArrayBlockingQueue(200));
    public static Calendar calendar;
    public static Timer timer;
    public static boolean isLogin = false;
    @Override
    public void onCreate() {
        super.onCreate();
        calendar = Calendar.getInstance();
        timer = new Timer();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

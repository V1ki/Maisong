package com.yuanshi.iotpro.publiclib.utils;

import android.util.Log;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public class YLog {
    public static final String TAG = "DBC";
    public static boolean logSwitch = true;
    public static void e(String msg){
        if(logSwitch){
            Log.e(TAG,msg);
        }
    }
    public static void w(String msg){
        if(logSwitch){
            Log.w(TAG,msg);
        }
    }
    public static void d(String msg){
        if(logSwitch){
            Log.d(TAG,msg);
        }
    }
    public static void i(String msg){
        if(logSwitch){
            Log.i(TAG,msg);
        }
    }
    public static void v(String msg){
        if(logSwitch){
            Log.v(TAG,msg);
        }
    }
}

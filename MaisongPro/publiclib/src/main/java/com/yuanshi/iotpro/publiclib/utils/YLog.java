package com.yuanshi.iotpro.publiclib.utils;

import android.util.Log;

import com.tencent.bugly.crashreport.BuglyLog;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public class YLog {
    public static final String TAG = "DBC";
    public static boolean logSwitch = true;
    public static void e(String msg){
        if(logSwitch){
            
          BuglyLog.e(TAG,msg);
        }
    }
    public static void w(String msg){
        if(logSwitch){
            BuglyLog.w(TAG,msg);
        }
    }
    public static void d(String msg){
        if(logSwitch){
            BuglyLog.d(TAG,msg);
        }
    }
    public static void i(String msg){
        if(logSwitch){
            BuglyLog.i(TAG,msg);
        }
    }
    public static void v(String msg){
        if(logSwitch){
            BuglyLog.v(TAG,msg);
        }
    }
}

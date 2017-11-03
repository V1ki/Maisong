package com.yuanshi.iotpro.publiclib.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yuanshi.iotpro.publiclib.utils.NativeReadBroadcast;
import com.yuanshi.iotpro.publiclib.utils.SystemBarTintManager;
import com.yuanshi.iotpro.publiclib.utils.YLog;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    public static ArrayList<Activity> activities = new ArrayList<>();
    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitle();
        setContentView(getContentViewId());
        setTranslucentStatus(this);
        ButterKnife.bind(this);
        mContext = this;
        activities.add(this);
        init(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setOnWiFiStateChangeListener();
    }

    /**
     * 设置wifi状态监听listener
     */
    public void setOnWiFiStateChangeListener(){
        NativeReadBroadcast.setOnWifiStateChangeListener(new NativeReadBroadcast.OnWifiStateChange() {
            @Override
            public void onMobileNetWork() {
                onMobileNetwork();
            }
            @Override
            public void onWiFiConnected(String SSID) {
                wiFiConnected(SSID);
            }

            @Override
            public void onWiFiDisConnected() {
                wiFiDisconnected();
            }

            @Override
            public void onWiFiNetWork() {
                netWorkWiFi();
            }

            @Override
            public void onNetWorkNone() {
                netWorkNone();
            }

            @Override
            public void onWiFiOpened() {
                wifiOpened();
            }

            @Override
            public void onWiFiClosed() {
                wifiClosed();
            }
        });
    }
    protected void wiFiConnected(String ssid){
        YLog.e("已连接到网络："+ssid);
    }
    protected void onMobileNetwork(){
        YLog.e("切换到手机网络");
    }
    protected void wiFiDisconnected(){
        YLog.e("网络连接断开！");
    }
    protected void netWorkWiFi(){
        YLog.e("切换到WiFi网络！");
    }
    protected void netWorkNone(){
        YLog.e("切换到无网络连接！");
    }
    protected void wifiClosed(){
        YLog.e("WiFi开关关闭");
    }
    protected void wifiOpened(){
        YLog.e("WiFi开关打开");
    }
    protected void hideTitle(){
        getSupportActionBar().hide();
    }

    protected void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }

    protected void hideSoftInput() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * rabbtMq收到推送
     *
     * @param msg
     */
    protected void rabbitMqGetPushMessage(String msg) {
    }
    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {

    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {

    }

    @Override
    public void onGetPushMessage(String msg) {
        rabbitMqGetPushMessage(msg);
    }

    /**
     * 设置状态栏背景状态
     */
    @SuppressLint("InlinedApi")
    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);// 状态栏无背景
    }

    public static void finishAll(){
        for(Activity activity: activities){
//            if(activity != null && !activity.getLocalClassName().equals("activity.HomePageActivity")){
                activity.finish();
//            }
        }
    }
}

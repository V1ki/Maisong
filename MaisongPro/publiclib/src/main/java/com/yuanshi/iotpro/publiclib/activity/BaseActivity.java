package com.yuanshi.iotpro.publiclib.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanshi.iotpro.publiclib.R;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenter;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenterIml;
import com.yuanshi.iotpro.publiclib.utils.NativeReadBroadcast;
import com.yuanshi.iotpro.publiclib.utils.YLog;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    public static ArrayList<Activity> activities = new ArrayList<>();
    protected abstract int getContentViewId();
    protected IHttpPresenter iHttpPresenter;
    protected abstract void init(Bundle savedInstanceState);
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜
        mContext = this;
        iHttpPresenter = new IHttpPresenterIml(this,mContext);
        ButterKnife.bind(this);
        activities.add(this);
        init(savedInstanceState);
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setOnWiFiStateChangeListener();
        setOnReceiveMsgListener();
    }

    public void setOnReceiveMsgListener(){
        NativeReadBroadcast.setOnReviceMsgListener(new NativeReadBroadcast.OnReviceMsgListener() {
            @Override
            public void onReceiveFrdApplyInfo(String phone, String reason) {
                onReceiveApplyInfo(phone,reason);
            }

            @Override
            public void onFriendAddSuccess(String phone) {
                onFrdAddSuccess(phone);
            }

            @Override
            public void onReveiceMessage(int msgCount) {
                onReceiveConvertionMsg(msgCount);
            }
        });
    }

    protected void onReceiveApplyInfo(String phone,String reason){

    }

    protected void onFrdAddSuccess(String phone){

    }

    protected void onReceiveConvertionMsg(int msgCount){
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
        Toast.makeText(this.getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPushMessage(String msg) {
        rabbitMqGetPushMessage(msg);
    }

    @Override
    public void onError(String msgType, String msg, Object obj) {

    }

    /**
     * 设置状态栏背景状态
     */
//    @SuppressLint("ResourceAsColor")
//    public  void setTranslucentStatus(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            YLog.e("Android version--->"+Build.VERSION.SDK_INT);
//            Window window = activity.getWindow();
//            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
////            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            //设置状态栏颜色
//            window.setStatusBarColor(Color.parseColor("#00000000"));
////            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
////            View mChildView = mContentView.getChildAt(0);
////            if (mChildView != null) {
////                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
////                ViewCompat.setFitsSystemWindows(mChildView, true);
////            }
//        }else{
//            Window window = activity.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
//            int statusBarHeight = getStatusBarHeight(activity);
//            View mTopView = mContentView.getChildAt(0);
//            if (mTopView != null && mTopView.getLayoutParams() != null && mTopView.getLayoutParams().height == statusBarHeight) {
//                //避免重复添加 View
//                mTopView.setBackgroundColor(Color.parseColor("#00000000"));
//                return;
//            }
////            //使 ChildView 预留空间
////            if (mTopView != null) {
////                ViewCompat.setFitsSystemWindows(mTopView, true);
////            }
////            //添加假 View
////            mTopView = new View(activity);
////            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
////            mTopView.setBackgroundColor(R.color.colorPrimary);
////            mContentView.addView(mTopView, 0, lp);
//        }
//    }

    public static void finishAll(){
        for(Activity activity: activities){
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void BackToLoginActivity(){
        for(Activity activity: activities){
            if(activity != null && !activity.getLocalClassName().equals("activity.LoginActivity")){
                activity.finish();
            }
        }
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onDownloadComplete(View view,String fileName) {
    }

    @Override
    public void onDownloadError(View view, Throwable e,String fileName) {

    }

    @Override
    public void onDownloadProgress(View view, long progress, long total) {

    }
}

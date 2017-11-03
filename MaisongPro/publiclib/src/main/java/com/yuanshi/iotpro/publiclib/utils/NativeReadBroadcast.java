package com.yuanshi.iotpro.publiclib.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/**
 * Created by admin on 2017/7/1.
 */

public class NativeReadBroadcast extends BroadcastReceiver {

    private static OnWifiStateChange onWifiStateChangeListener;
    public static void setOnWifiStateChangeListener(OnWifiStateChange wifiStateChangeListener){
        onWifiStateChangeListener = wifiStateChangeListener;
    }

    public interface OnWifiStateChange{
        void onMobileNetWork();
        void onWiFiConnected(String SSID);
        void onWiFiDisConnected();
        void onWiFiNetWork();
        void onNetWorkNone();
        void onWiFiOpened();
        void onWiFiClosed();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                if(onWifiStateChangeListener != null){
                    onWifiStateChangeListener.onWiFiClosed();
                }
            }

            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                if(onWifiStateChangeListener != null){
                    onWifiStateChangeListener.onWiFiOpened();
                }
            }
        }   else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){//wifi连接上与否
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if(info.getState().equals(NetworkInfo.State.DISCONNECTED)){
               if(onWifiStateChangeListener != null){
                   onWifiStateChangeListener.onWiFiDisConnected();
               }
            }
            else if(info.getState().equals(NetworkInfo.State.CONNECTED)){
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String SSID = wifiInfo.getSSID();
                if(onWifiStateChangeListener != null){
                    onWifiStateChangeListener.onWiFiConnected(SSID);
                }
            }
        }
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {//网络状态发生改变
            int netWorkState = NetWorkUtil.getNetWorkState(context);
            switch (netWorkState){
                case NetWorkUtil.NETWORK_MOBILE:
                    if(onWifiStateChangeListener != null){
                        onWifiStateChangeListener.onMobileNetWork();
                    }
                    break;
                case NetWorkUtil.NETWORK_WIFI:
                    if(onWifiStateChangeListener != null){
                        onWifiStateChangeListener.onWiFiNetWork();
                    }
                    break;
                case NetWorkUtil.NETWORK_NONE:
                    if(onWifiStateChangeListener != null){
                        onWifiStateChangeListener.onNetWorkNone();
                    }
                    break;
            }
        }

    }
}

package com.yuanshi.iotpro.publiclib.activity;

import android.view.View;

/**
 * Created by Dengbocheng on 2017/4/22.
 */

public interface IBaseView<T> {
    void onGetPushMessage(String msg);//收到推送消息调用
    void onHttpSuccess(String msgType, String msg, Object obj);
    void onHttpFaild(String msgType, String msg, Object obj);
    void onError(String msgType, String msg, Object obj);
    void onDownloadProgress(View view,long progress, long total);
    void onDownloadError(View view,Throwable e,String fileName);
    void onDownloadComplete(View view,String fileName);


}

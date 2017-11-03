package com.yuanshi.maisong.application;


import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.yuanshi.iotpro.publiclib.application.MyApplication;


/**
 * Created by Dengbocheng on 2017/10/24.
 */

public class MSongAppliacation extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        EaseUI.getInstance().init(this,null);
        EMClient.getInstance().setDebugMode(true);
    }
}

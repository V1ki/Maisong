package com.yuanshi.maisong.application;


import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.yuanshi.iotpro.publiclib.application.MyApplication;

import java.util.List;


/**
 * Created by Dengbocheng on 2017/10/24.
 */

public class MSongAppliacation extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this,options);
        EMClient.getInstance().setDebugMode(true);
        CrashHandler crashHandler = CrashHandler.getInstance();//记录崩溃日志、崩溃重启
        crashHandler.init(this);

    }
}

package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.maisong.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/23.
 */

public class WelcomActivity extends BaseActivity {
    @BindView(R.id.welcom_bg)
    ImageView welcomBg;
    private Timer timer = new Timer();
    private boolean hasLoginInfo  = false;
    private LoginInfoBean loginInfoBean;

    @Override
    protected int getContentViewId() {
        return R.layout.welcom_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        fullScreen();
        checkLoginInfo();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(CHANGE_SECONDS);
            }
        }, 1000, 1000);
    }

    private int wait_seconds = 3;
    private static final int CHANGE_SECONDS = 0x0010;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_SECONDS:
                    wait_seconds--;
                    if (wait_seconds == 0) {
                        startNextActivity();
                    }
                    break;
            }
        }
    };


    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "login2":
                Gson gson = new Gson();
                LoginInfoBean responseBean = gson.fromJson(gson.toJson(obj),LoginInfoBean.class);
                iLoginInfoDBPresenter.updateLastLoginTime(responseBean.getLast_login_time(),responseBean.getPhone());
                getSharedPreferences(Constant.MAIN_SH_NAME, MODE_PRIVATE).edit().putString(Constant.USER_PHONE_KEY,responseBean.getPhone()).commit();
                Intent intent = new Intent();
                if(getSharedPreferences(Constant.MAIN_SH_NAME, MODE_PRIVATE).getBoolean(Constant.HAS_PUT_USER_INFO_KEY,false)){
                    intent.setClass(WelcomActivity.this,MainActivity.class);
                }else{
                    intent.setClass( WelcomActivity.this,PerfectDataActivity.class);
                }
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {
       switch (msgType){
           case "login2":
               Intent intent = new Intent(WelcomActivity.this,LoginAcitvity.class);
               startActivity(intent);
               finish();
               break;
       }
    }

    @Override
    public void onError(String msgType, String msg, Object obj) {
        switch (msgType){
            case "login2":
                Intent intent = new Intent(WelcomActivity.this,LoginAcitvity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void startNextActivity() {
        timer.cancel();
        Intent intent = new Intent();
        if(hasLoginInfo){
            login();
        }else{
            intent.setClass(WelcomActivity.this,LoginAcitvity.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(){
        if(loginInfoBean != null && !TextUtils.isEmpty(loginInfoBean.getUid())){
            iHttpPresenter.login2(loginInfoBean.getUid());
        }else{
            Intent intent = new Intent();
            intent.setClass(WelcomActivity.this,LoginAcitvity.class);
            startActivity(intent);
            finish();
        }
    }


    /**
     * 查询数据库登录信息
     */
    public void checkLoginInfo(){
        List<LoginInfoBean> logininfos= iLoginInfoDBPresenter.selectAllLoginInfo();
        if(logininfos!= null && logininfos.size()>0){
            hasLoginInfo = true;
            LoginComparator loginComparator = new LoginComparator();
            loginInfoBean  = Collections.max(logininfos,loginComparator);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 比较登录时间最近的账号
     */
    class LoginComparator implements Comparator<LoginInfoBean> {
        @Override
        public int compare(LoginInfoBean o1, LoginInfoBean o2) {
            return (Long.parseLong(o1.getLast_login_time()) < Long.parseLong(o2.getLast_login_time()) ? -1 : (Long.parseLong(o1.getLast_login_time()) == Long.parseLong(o2.getLast_login_time()) ? 0 : 1));
        }
    }
}

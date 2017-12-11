package com.yuanshi.maisong.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/23.
 */

public class LoginAcitvity extends BaseActivity {
    @BindView(R.id.login_logo_img)
    ImageView loginLogoImg;
    @BindView(R.id.get_check_code_btn)
    TextView getCheckCodeBtn;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.ed_userName)
    EditText edUserName;
    @BindView(R.id.ed_password)
    EditText edPassword;
    private String phoneRegex = "[1][3,4,5,7,8][0-9]{9}";
    private LoginBeanDaoUtil loginBeanDaoUtil ;

    @Override
    protected int getContentViewId() {
        return R.layout.login_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        fullScreen();
        loginBeanDaoUtil = new LoginBeanDaoUtil(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get_check_code_btn, R.id.login_btn})
    public void onViewClicked(View view) {
        String phone = edUserName.getText().toString().trim();
        String checkCode = edPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.get_check_code_btn:
                if(checkPhone(phone)){
                    startTimer();
                    iHttpPresenter.getverify(phone);
                }
                break;
            case R.id.login_btn:
                if(checkPhone(phone) && checkCode(checkCode)){
                    iHttpPresenter.login(phone,checkCode);
                }
                singup(phone,phone);
                break;
        }
    }
    public void createCount(final String userName,final String pwd){
        new Thread(new Runnable(){
            @Override
            public void run() {
                            try {
                                EMClient.getInstance().createAccount(userName, pwd);
                                YLog.e("createAccount success!!");
                                singup(userName,pwd);
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                YLog.e("createAccount faild!!-->"+e.getErrorCode());
                                return;
                            }
            }
        }).start();
    }
    public void singup(final String userName,final String pwd){
        new Thread(new Runnable(){
            @Override
            public void run() {
                EMClient.getInstance().login(userName,pwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        YLog.e("EMClient login success!!");
                        startNextActivity();
                    }
                    @Override
                    public void onError(int i, String s) {
                        YLog.e(" EMClient login error!!--"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        YLog.e("EMClient logining!!--"+s);
                    }
                });
            }
        }).start();
    }

    private static final int WAIT_RESEND_CHECK_CODE = 0x0010;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("ResourceType")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WAIT_RESEND_CHECK_CODE:
                    if(waitSecond == 0){
                        if(timer != null){
                            timer.cancel();
                        }
                        getCheckCodeBtn.setTextColor(getResources().getColorStateList(R.drawable.text_click_selector1));
                        getCheckCodeBtn.setText(R.string.get_check_code);
                        getCheckCodeBtn.setEnabled(true);
                    }else{
                        getCheckCodeBtn.setTextColor(getResources().getColor(R.color.tab_unselected));
                        getCheckCodeBtn.setText(String.format(getString(R.string.seconds_later),waitSecond));
                        getCheckCodeBtn.setEnabled(false);
                    }
                    break;
            }
        }
    };

    private void startNextActivity(){
        Intent intent = new Intent(LoginAcitvity.this, PerfectDataActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "login":
                YLog.e("login onHttpSuccess~~~~~"+msg);
                Gson gson = new Gson();
                LoginInfoBean loginInfoBean =  gson.fromJson(gson.toJson(obj),LoginInfoBean.class);
                getSharedPreferences(Constant.MAIN_SH_NAME, MODE_PRIVATE).edit().putString(Constant.USER_PHONE_KEY,loginInfoBean.getPhone()).commit();
                YLog.e("loginBean~~"+gson.toJson(loginInfoBean));
                createCount(loginInfoBean.getPhone(),loginInfoBean.getPhone());
                saveLoginInfo(loginInfoBean);
                startNextActivity();
                break;
            case "getverify":
                YLog.e("getverify onHttpSuccess~~~~~"+msg);
                break;
        }
    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {
        super.onHttpFaild(msgType,msg,obj);
        switch (msgType){
            case "login":
                YLog.e("login onHttpFaild~~~~~"+msg);
                if(!TextUtils.isEmpty(msg)){
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }
                break;
            case "getverify":
                if(!TextUtils.isEmpty(msg)){
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onError(String msgType, String msg, Object obj) {
        super.onError(msgType, msg, obj);
        YLog.e("onError~~~~"+msg);
    }

    /**
     * 保存登录信息
     */
    public void saveLoginInfo(LoginInfoBean loginInfoBean){
        YLog.e("保存用户信息到数据库");
        LoginInfoBean localBean = loginBeanDaoUtil.qeuryUserInfo(Long.parseLong(loginInfoBean.getPhone()));
        if(localBean != null){
            YLog.e("本地有该用户数据");
            loginInfoBean.set_id(localBean.get_id());
            loginBeanDaoUtil.updateUserInfo(loginInfoBean);
        }else{
            YLog.e("本地没有该用户数据");
            loginInfoBean.set_id(Long.parseLong(loginInfoBean.getPhone()));
            loginBeanDaoUtil.insertLoginInfo(loginInfoBean);
        }
        getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).edit().putString(Constant.USER_PHONE_KEY,loginInfoBean.getPhone()).commit();
    }

    private int waitSecond = 60;
    private Timer timer = new Timer();
    public void startTimer(){
        if(timer!= null){
            timer.cancel();
            waitSecond = 60;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                waitSecond--;
                handler.sendEmptyMessage(WAIT_RESEND_CHECK_CODE);
            }
        },0,1000);
    }

    public boolean checkPhone(String phoneNum){
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNum);
       if(matcher.matches()){
           return true;
       }else{
           Toast.makeText(getApplicationContext(), "未输入手机号码或输入手机号不合规范！", Toast.LENGTH_SHORT).show();
           return false;
       }
    }
    public boolean checkCode(String code){
        return !TextUtils.isEmpty(code);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!= null){
            timer.cancel();
        }
    }
}

package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;

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

    @Override
    protected int getContentViewId() {
        return R.layout.login_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        fullScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get_check_code_btn, R.id.login_btn,R.id.login_logo_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_check_code_btn:
                break;
            case R.id.login_btn:
//              singup();
                startNextActivity();
                break;
            case R.id.login_logo_img:
                singout();
                break;
        }
    }
    public void createCount(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                            try {
                                EMClient.getInstance().createAccount(edUserName.getText().toString().trim(), edPassword.getText().toString().trim());
                                YLog.e("createAccount success!!");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                YLog.e("createAccount faild!!-->"+e.getErrorCode());
                                return;
                            }
            }
        }).start();
    }
    public void singup(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                EMClient.getInstance().login(edUserName.getText().toString().trim(), edPassword.getText().toString().trim(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        YLog.e("login success!!");
                        startNextActivity();
                    }

                    @Override
                    public void onError(int i, String s) {
                        YLog.e("login error!!--"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        YLog.e("logining!!--"+s);
                    }
                });
            }
        }).start();
    }
    public void singout(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                EMClient.getInstance().logout(true,new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        YLog.e("loginout success!!");
//                        startNextActivity();
                    }

                    @Override
                    public void onError(int i, String s) {
                        YLog.e("loginout error!!--"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        YLog.e("loginouting!!--"+s);
                    }
                });
            }
        }).start();
    }
    private void startNextActivity(){
        Intent intent = new Intent(LoginAcitvity.this, PerfectDataActivity.class);
        startActivity(intent);
        finish();
    }
}

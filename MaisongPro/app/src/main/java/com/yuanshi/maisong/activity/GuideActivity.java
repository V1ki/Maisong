package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/23.
 */

public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.skip_btn)
    TextView skipBtn;
    private Timer timer = new Timer();
    @Override
    protected int getContentViewId() {
        return R.layout.guide_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        skipBtn.setText(String.format(getString(R.string.skip_wait),wait_seconds).toString());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(CHANGE_SECONDS);
            }
        },1000,1000);
    }

    private int wait_seconds = 5;
    private static final int CHANGE_SECONDS = 0x0010;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGE_SECONDS:
                    wait_seconds --;
                    skipBtn.setText(String.format(getString(R.string.skip_wait),wait_seconds).toString());
                    if(wait_seconds == 0){
                        startNextActivity();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.skip_btn)
    public void onViewClicked() {
        startNextActivity();
    }
    private void startNextActivity(){
        timer.cancel();
        Intent intent = new Intent(GuideActivity.this, LoginAcitvity.class);
        startActivity(intent);
        finish();
    }
}

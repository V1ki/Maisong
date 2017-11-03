package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

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

public class WelcomActivity extends BaseActivity {
    @BindView(R.id.welcom_bg)
    ImageView welcomBg;
    private Timer timer = new Timer();

    @Override
    protected int getContentViewId() {
        return R.layout.welcom_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        fullScreen();
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



    private void startNextActivity() {
        timer.cancel();
        Intent intent = new Intent(WelcomActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.welcom_bg)
    public void onViewClicked() {
        startNextActivity();
    }
}

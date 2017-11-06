package com.yuanshi.maisong.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/11/3.
 * 好友详情页面
 */

public class FriendDetailActivity extends BaseActivity {

    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.add_friend_btn)
    TextView addFriendBtn;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.height_value)
    TextView heightValue;
    @BindView(R.id.weight_value)
    TextView weightValue;
    @BindView(R.id.sex_value)
    ImageView sexValue;
    @BindView(R.id.base_info_layout)
    LinearLayout baseInfoLayout;
    @BindView(R.id.information)
    TextView information;
    @BindView(R.id.information_value)
    TextView informationValue;
    @BindView(R.id.department)
    TextView department;
    @BindView(R.id.department_value)
    TextView departmentValue;
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.position_value)
    TextView positionValue;
    @BindView(R.id.wechat)
    TextView wechat;
    @BindView(R.id.wechat_value)
    TextView wechatValue;
    @BindView(R.id.wechat_layout)
    RelativeLayout wechatLayout;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.email_value)
    TextView emailValue;
    @BindView(R.id.email_layout)
    RelativeLayout emailLayout;
    @BindView(R.id.send_msg_btn)
    TextView sendMsgBtn;
    private int openType = 0;//打开类型 0：好友详情；1：联系人资料

    @Override
    protected int getContentViewId() {
        return R.layout.friend_detail_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        openType = getIntent().getIntExtra("openType", 0);
        switch (openType) {
            case 0:
                titleText.setText(R.string.friend_detail);
                addFriendBtn.setVisibility(View.GONE);
                baseInfoLayout.setVisibility(View.GONE);
                emailLayout.setVisibility(View.VISIBLE);
                wechatLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                titleText.setText(R.string.his_info);
                addFriendBtn.setVisibility(View.VISIBLE);
                baseInfoLayout.setVisibility(View.VISIBLE);
                emailLayout.setVisibility(View.GONE);
                wechatLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.add_friend_btn, R.id.send_msg_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.add_friend_btn:
                break;
            case R.id.send_msg_btn:
                break;
        }
    }
}

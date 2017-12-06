package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;
import com.yuanshi.maisong.view.CircleImageView;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

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
    @BindView(R.id.headIcon)
    CircleImageView headIcon;
    @BindView(R.id.sexTv)
    TextView sexTv;
    @BindView(R.id.sex_icon)
    ImageView sexIcon;
    private String phone,departmentText, positionText;//用户手机号
    private UserInfoBean userInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.friend_detail_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        departmentText = getIntent().getStringExtra("department");
        positionText = getIntent().getStringExtra("position");
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), R.string.user_phone_null, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (isFriend(phone)) {
            addFriendBtn.setVisibility(View.GONE);
        } else {
            addFriendBtn.setVisibility(View.VISIBLE);
        }
        departmentValue.setText(departmentText);
        positionValue.setText(positionText);
        iHttpPresenter.phonegetuser(phone, "");
    }

    private void initData() {
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getNickname())) {
                nickName.setText(userInfo.getNickname());
            }
            if (!TextUtils.isEmpty(userInfo.getAvatar())) {
                Glide.with(this).load(userInfo.getAvatar()).error(R.mipmap.ic_launcher).into(headIcon);
            }
            if (!TextUtils.isEmpty(userInfo.getPhone())) {
                account.setText(userInfo.getPhone());
                informationValue.setText(userInfo.getPhone());
            }
            switch (userInfo.getSex()) {
                case 0:
                    sexIcon.setImageResource(R.mipmap.girl_checked);
                    break;
                case 1:
                    sexIcon.setImageResource(R.mipmap.boy_checked);
                    break;
            }
            if(!TextUtils.isEmpty(userInfo.getWeixin())){
                wechatValue.setText(userInfo.getWeixin());
            }
            if(!TextUtils.isEmpty(userInfo.getEmail())){
                emailValue.setText(userInfo.getEmail());
            }
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case "phonegetuser":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                userInfo = gson.fromJson(json, UserInfoBean.class);
                initData();
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
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, userInfo.getPhone());
                intent.putExtra("title",userInfo.getNickname());
                startActivity(intent);
                finish();
                break;
        }
    }

    public boolean isFriend(final String phone) {
        try {
            return MyApplication.THREAD_EXCUTER.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    YLog.e("my friends size-->"+usernames.size());
                    for(String str : usernames){
                        YLog.e("my friends-->"+str);
                    }
                    return usernames.contains(phone);
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}

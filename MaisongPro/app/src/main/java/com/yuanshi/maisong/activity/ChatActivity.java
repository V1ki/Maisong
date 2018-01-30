package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.maisong.R;

/**
 * Created by Dengbocheng on 2017/10/24.
 */

public class ChatActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {

        return R.layout.chat_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container,chatFragment).commit();
        chatFragment.setChatFragmentHelper(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {
                String myName = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
                if(!TextUtils.isEmpty(username)){
                    if(myName.equals(username)){
                        Intent intent = new Intent(ChatActivity.this, AccountSettingActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(ChatActivity.this, FriendDetailActivity.class);
                        intent.putExtra("phone", username);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
    }
}

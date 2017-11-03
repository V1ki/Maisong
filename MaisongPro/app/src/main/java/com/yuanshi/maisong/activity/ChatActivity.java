package com.yuanshi.maisong.activity;

import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
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
    }
}

package com.yuanshi.maisong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanshi.maisong.R;
import com.yuanshi.maisong.activity.AccountSettingActivity;
import com.yuanshi.maisong.activity.SettingsActivity;
import com.yuanshi.maisong.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2016/9/12.
 */
public class MoreFragment extends Fragment {
    @BindView(R.id.headIcon)
    CircleImageView headIcon;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userTel)
    TextView userTel;
    @BindView(R.id.myinfo_icon)
    ImageView myinfoIcon;
    @BindView(R.id.forword_icon)
    ImageView forwordIcon;
    @BindView(R.id.account_setting_layout)
    RelativeLayout accountSettingLayout;
    @BindView(R.id.myfriends_icon)
    ImageView myfriendsIcon;
    @BindView(R.id.my_friends_layout)
    RelativeLayout myFriendsLayout;
    @BindView(R.id.help_icon)
    ImageView helpIcon;
    @BindView(R.id.help_layout)
    RelativeLayout helpLayout;
    @BindView(R.id.settings_icon)
    ImageView settingsIcon;
    @BindView(R.id.settings_layout)
    RelativeLayout settingsLayout;
    Unbinder unbinder;
    private View m_View;
    public static MoreFragment moreFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (m_View == null) {
            m_View = inflater.inflate(R.layout.self_detail_layout, null);
        }
        ViewGroup parent = (ViewGroup) m_View.getParent();
        if (parent != null) {
            parent.removeView(m_View);
        }
        initView();
        moreFragment = this;
        unbinder = ButterKnife.bind(this, m_View);
        return m_View;
    }

    public static MoreFragment getInstance() {
        if (moreFragment == null) {
            moreFragment = new MoreFragment();
        }
        return moreFragment;
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.account_setting_layout, R.id.my_friends_layout, R.id.help_layout, R.id.settings_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.account_setting_layout:
                intent.setClass(getActivity(),AccountSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_friends_layout:
                break;
            case R.id.help_layout:
                break;
            case R.id.settings_layout:
                intent.setClass(getActivity(),SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

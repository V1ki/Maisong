package com.yuanshi.maisong.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.activity.AccountSettingActivity;
import com.yuanshi.maisong.activity.ESContactsActivity;
import com.yuanshi.maisong.activity.MainActivity;
import com.yuanshi.maisong.activity.SettingsActivity;
import com.yuanshi.maisong.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2016/9/12.
 */
public class MoreFragment extends BaseFragment{
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
    private String userPhone;
    private LoginBeanDaoUtil loginBeanDaoUtil;
    @Override
    protected View getMainView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (m_View == null) {
            m_View = inflater.inflate(R.layout.self_detail_layout, null);
        }
        ViewGroup parent = (ViewGroup) m_View.getParent();
        if (parent != null) {
            parent.removeView(m_View);
        }
        loginBeanDaoUtil = new LoginBeanDaoUtil(getActivity());
        moreFragment = this;
        unbinder = ButterKnife.bind(this, m_View);
        initView();
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
    public void onResume() {
        super.onResume();
        setUerInfo();
    }

    public void setUerInfo(){
        userPhone = getActivity().getSharedPreferences(Constant.MAIN_SH_NAME, Context.MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
        LoginInfoBean loginInfoBean = getLoginInfoBean(userPhone);
        YLog.e("setUserInfo--->"+loginInfoBean);
        if(loginInfoBean!= null && !TextUtils.isEmpty(loginInfoBean.getAvatar())){
            YLog.e("setUserInfo--->"+loginInfoBean.getAvatar());
            Glide.with(getActivity()).load(loginInfoBean.getAvatar()).error(R.mipmap.ease_default_avatar).into(headIcon);
            userName.setText(loginInfoBean.getNickname());
        }
    }
    public LoginInfoBean getLoginInfoBean(String userPhone){
        return  loginBeanDaoUtil.qeuryUserInfo(Long.parseLong(userPhone));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.account_setting_layout, R.id.my_friends_layout, R.id.help_layout, R.id.settings_layout,R.id.headIcon})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.account_setting_layout:
                intent.setClass(getActivity(),AccountSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_friends_layout:
                intent.setClass(getActivity(),ESContactsActivity.class);
                intent.putExtra("openType", EaseContactListFragment.OPEN_TYPE_MOREFRAGMENT);
                startActivity(intent);
                break;
            case R.id.help_layout:
                break;
            case R.id.settings_layout:
                intent.setClass(getActivity(),SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.headIcon:
                intent.setClass(getActivity(),AccountSettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}

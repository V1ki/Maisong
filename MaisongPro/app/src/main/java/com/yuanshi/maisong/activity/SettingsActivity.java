package com.yuanshi.maisong.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/11/2.
 */
public class SettingsActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.version_code_layout)
    RelativeLayout versionCodeLayout;
    @BindView(R.id.clear_cache_layout)
    RelativeLayout clearCacheLayout;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;
    @BindView(R.id.cache_size_tv)
    TextView cacheSizeTv;

    private LoginBeanDaoUtil loginBeanDaoUtil;
    @Override
    protected int getContentViewId() {
        return R.layout.settings_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loginBeanDaoUtil = new LoginBeanDaoUtil(this);
        getCacheSize();
        checkNewVersion();

    }

    /**
     * 获取缓存大小
     */
    public void getCacheSize() {
        String cacheSize = "";
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheSizeTv.setText(cacheSize);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.version_code_layout, R.id.clear_cache_layout, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.version_code_layout:
                checkNewVersion();
                break;
            case R.id.clear_cache_layout:
                showClearCacheDialog();
                break;
            case R.id.logout_btn:
                showLogoutDialog();
                break;
        }
    }

    /**
     * 弹出退出登录确认框
     */
    public void showLogoutDialog(){
        final Dialog mCameraDialog = new Dialog(this, R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.logout_dialog_layout, null);
        TextView contentTv = root.findViewById(R.id.dialog_content);
        contentTv.setText("确定要退出当前用户？");
        root.findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager)this
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width-80; // 宽度
        root.measure(0, 0);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.alpha = 2f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    /**
     * 弹出退出登录确认框
     */
    public void showClearCacheDialog(){
        final Dialog mCameraDialog = new Dialog(this, R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.logout_dialog_layout, null);
        TextView contentTv = root.findViewById(R.id.dialog_content);
        contentTv.setText("确定要清除缓存？");
        root.findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCache();
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager)this
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width-80; // 宽度
        root.measure(0, 0);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.alpha = 2f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }


    /**
     * 检查最新版本
     */
    public void checkNewVersion() {

    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        DataCleanManager.clearAllCache(this);
        getCacheSize();
    }

    public void logout() {
        iHttpPresenter.logout();
    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {
        super.onHttpFaild(msgType,msg,obj);
        switch (msgType){
            case "logout":
                YLog.e("logout~~~~onHttpFaild"+msg);

                break;
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "logout":
                YLog.e("logout~~~~onHttpSuccess"+msg);
                String loginPhone = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
                if(!TextUtils.isEmpty(loginPhone)){
                    LoginInfoBean localBean = loginBeanDaoUtil.qeuryUserInfo(Long.parseLong(loginPhone));
                    loginBeanDaoUtil.deleteUserInfo(localBean);
                }
                //个人资料完善改为false
                getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).edit().putBoolean(Constant.HAS_PUT_USER_INFO_KEY,false).commit();
                singout();//环信退出登录
                Intent intnet = new Intent(this,LoginAcitvity.class);
                startActivity(intnet);
                finish();
                break;
        }
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

    @Override
    public void onError(String msgType, String msg, Object obj) {
        switch (msgType){
            case "logout":
                YLog.e("logout~~~~onError"+msg);
                break;
        }
    }
}

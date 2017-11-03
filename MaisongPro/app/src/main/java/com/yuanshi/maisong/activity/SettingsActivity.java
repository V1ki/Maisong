package com.yuanshi.maisong.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
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

    @Override
    protected int getContentViewId() {
        return R.layout.settings_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
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
                clearCache();
                break;
            case R.id.logout_btn:
                logout();
                break;
        }
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

    }
}

package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.fragment.CrewFragment;
import com.yuanshi.maisong.fragment.MoreFragment;
import com.yuanshi.maisong.fragment.ServiceFlatformFragment;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.view.BottomTabItem;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_viewPager)
    ViewPager mainViewPager;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    private Fragment[] fragments = {
            null,//代码中使用环信sdk的会话列表fragment
            CrewFragment.getInstance(),
            ServiceFlatformFragment.getInstance(),
            MoreFragment.getInstance()};
    int[] tabDrawableIds_checked = {R.mipmap.msg_tab_sel,
            R.mipmap.crew_tab_sel,
            R.mipmap.server_platform_tab_sel,
            R.mipmap.more_tab_sel};
    int[] tabDrawableIds_nomal = {R.mipmap.msg_tab,
            R.mipmap.crew_tab,
            R.mipmap.server_platform_tab,
            R.mipmap.more_tab};
    int[] tabNames = {R.string.messages,
            R.string.crews,
            R.string.service_platform,
            R.string.self_detail};

    public static final int SHOW_HOMEPAGE = 0;
    public static MainActivity instance;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initBottomLayout();
        initFragmentPager();
        ((BottomTabItem) bottomLayout.getChildAt(SHOW_HOMEPAGE))
                .setItemChecked(true);// 设置默认选中第一个Tab
        instance = this;
    }

    public void initFragmentPager(){
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setOnAddBtnClickListener(new EaseConversationListFragment.OnAddBtnClickLister() {
            @Override
            public void onAddbtnClick(View view) {
                startActivity(new Intent(MainActivity.this,ESContactsActivity.class));
            }
        });
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        fragments[0] = conversationListFragment;
        mainViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mainViewPager.setCurrentItem(0);
        mainViewPager.setOffscreenPageLimit(fragments.length);
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < fragments.length; i++) {
                    if (i != position % fragments.length) {
                        ((BottomTabItem) (MainActivity.instance.bottomLayout
                                .getChildAt(i))).setItemChecked(false);
                    } else {
                        ((BottomTabItem) (MainActivity.instance.bottomLayout
                                .getChildAt(i))).setItemChecked(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 加载底部tab布局
     */
    private void initBottomLayout() {
        for (int i = 0; i < tabNames.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0F);
            BottomTabItem item = new BottomTabItem(this,
                    tabDrawableIds_nomal[i], tabDrawableIds_checked[i],
                    tabNames[i], i);
//            params.setMargins(0, 0, 0, 0);
//            if (i == tabNames.length / 2 - 1) {
//                //中间偏左的往左移40
//                params.setMargins(0, 0, 40, 0);
//            }
//            if (i == tabNames.length / 2) {
//                //中间偏右的往右移40
//                params.setMargins(40, 0, 0, 0);
//            }
            bottomLayout.addView(item, params);
        }
        // 设置默认选中第一个Tab
        ((BottomTabItem) bottomLayout.getChildAt(0)).setItemChecked(true);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        FragmentManager fm;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * 改变底部tab的选中状态
     *
     * @param tabId 底部TabID
     */
    public void chageBottonTab(int tabId) {
        if(instance.mainViewPager.getCurrentItem()!= tabId){
            MainActivity.instance.mainViewPager.setCurrentItem(tabId % fragments.length);
            ((BottomTabItem) (MainActivity.instance.bottomLayout.getChildAt(tabId)))
                    .setItemChecked(true);
            if (MainActivity.instance != null
                    && MainActivity.instance.bottomLayout != null) {
                for (int i = 0; i < MainActivity.instance.bottomLayout
                        .getChildCount(); i++) {
                    if (i != tabId) {
                        ((BottomTabItem) (MainActivity.instance.bottomLayout
                                .getChildAt(i))).setItemChecked(false);
                    }
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }


    private long exitTime = 0;

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 800) {
            Toast.makeText(getApplicationContext(),
                    R.string.press_back_again_to_exit, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAll();
        }
    }

}

package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.google.gson.Gson;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.yuanshi.iotpro.daoutils.FriendApplyBeanDaoUtil;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.daoutils.UserBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.bean.FriendsApplyBean;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.NativeReadBroadcast;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.CrewHttpBean;
import com.yuanshi.maisong.bean.GroupBean;
import com.yuanshi.maisong.fragment.CrewFragment;
import com.yuanshi.maisong.fragment.EMconversationFragment;
import com.yuanshi.maisong.fragment.MoreFragment;
import com.yuanshi.maisong.fragment.ServiceFlatformFragment;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.BottomTabItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_viewPager)
    ViewPager mainViewPager;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    public List<CrewHttpBean> crewList = new ArrayList<>();//我的剧组列表
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
    public FriendApplyBeanDaoUtil friendApplyBeanDaoUtil;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        friendApplyBeanDaoUtil = new FriendApplyBeanDaoUtil(this);
        setOnContactFriendListener();
        initBottomLayout();
        initFragmentPager();
        ((BottomTabItem) bottomLayout.getChildAt(SHOW_HOMEPAGE))
                .setItemChecked(true);// 设置默认选中第一个Tab
        instance = this;
        LoginBeanDaoUtil util = new LoginBeanDaoUtil(this);
        List<LoginInfoBean> list = util.queryAllUserInfo();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    public void setOnContactFriendListener(){
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
                YLog.e("好友添加成功---》"+username+":"+reason);
                FriendsApplyBean bean = new FriendsApplyBean();
                bean.setReason(reason);
                bean.setState(Constant.FRD_APPLY_STATE_NORMAL);
                bean.setPhone(username);
                bean.set_id(Long.parseLong(bean.getPhone()));
                 FriendsApplyBean localBean = friendApplyBeanDaoUtil.qeuryFrdApplyInfo(bean.get_id());
                 if(localBean == null){
                     friendApplyBeanDaoUtil.insertFrdApplyInfo(bean);
                 }else{
                     localBean.setReason(bean.getReason());
                     localBean.setState(Constant.FRD_APPLY_STATE_NORMAL);
                     friendApplyBeanDaoUtil.updateFrdApplyInfo(localBean);
                 }
                Intent intent = new Intent(MainActivity.this, NativeReadBroadcast.class);
                intent.setAction(Constant.RECEIVED_FRDAPPLY_INFO);
                intent.putExtra("username",username);
                intent.putExtra("reason",reason);
                sendBroadcast(intent);
            }
            @Override
            public void onFriendRequestAccepted(String s) {
                //好友请求被同意
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                //好友请求被拒绝
            }
            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
            }
            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                YLog.e("好友添加成功---》"+username);
                Intent intent = new Intent(MainActivity.this, NativeReadBroadcast.class);
                intent.setAction(Constant.ADDED_FRIEBND_SUCCESS);
                intent.putExtra("username",username);
                sendBroadcast(intent);
            }
        });
    }



    public void initFragmentPager(){
        final EMconversationFragment conversationListFragment = new EMconversationFragment();
        conversationListFragment.setOnAddBtnClickListener(new EaseConversationListFragment.OnAddBtnClickLister() {
            @Override
            public void onAddbtnClick(View view) {
                Intent intent = new Intent(MainActivity.this,ESContactsActivity.class);
                intent.putExtra("openType", EaseContactListFragment.OPEN_TYPE_CONTACTLIST);
                startActivity(intent);
            }
        });
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                String conversationName = conversation.conversationId();
                if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                    EMGroup group = EMClient.getInstance().groupManager().getGroup(conversation.conversationId());
                    conversationName= group != null ? group.getGroupName() : conversation.conversationId();
                } else if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
                    EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(conversation.conversationId());
                    conversationName = (room != null && !TextUtils.isEmpty(room.getName()) ? room.getName() : conversation.conversationId());
                }else if(conversation.getType() == EMConversation.EMConversationType.Chat){
                    UserBeanDaoUtil userBeanDaoUtil = new UserBeanDaoUtil(MainActivity.this);
                    UserInfoBean userInfoBean = userBeanDaoUtil.qeuryUserInfo(Long.parseLong(conversation.conversationId()));
                    if(!TextUtils.isEmpty(userInfoBean.getNickname())){
                        conversationName = userInfoBean.getNickname();
                    }
                }
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                intent.putExtra("title",conversationName);
                startActivity(intent);
            }
        });
        //删除和某个user会话，如果需要保留聊天记录，传false
        conversationListFragment.setConversationListItemLongClickListener(new EaseConversationListFragment.EaseConversationListItemLongClickListener() {
            @Override
            public void onListItemLongClicked(final EMConversation conversation) {
                    boolean flag = EMClient.getInstance().chatManager().deleteConversation(conversation.conversationId(), true);
                    YLog.e("长按--》"+conversation.conversationId()+":"+flag);
                conversationListFragment.reloadCrewListData();
            }
        });
//
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
        iHttpPresenter.index();
    }


    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "index":
                String json = new Gson().toJson(obj);
                crewList = Utils.jsonToList(json, CrewHttpBean[].class);
                reloadCrewListData();
                break;
            }
    }

    /**
     * 刷新剧组列表相关数据
     */
    public void reloadCrewListData(){
        List<String> groupidList = new ArrayList<>();
        ((EMconversationFragment)fragments[0]).reloadCrewListData();
        ((CrewFragment)fragments[1]).reloadCrewListData();
    }

    @Override
    protected void onReceiveConvertionMsg(int msgCount) {
        super.onReceiveConvertionMsg(msgCount);
        ((EMconversationFragment)fragments[0]).reloadCrewListData();
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
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
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


    /**
     * 环信信息接收监听器
     */
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            Intent intent = new Intent(MainActivity.this, NativeReadBroadcast.class);
            intent.setAction(Constant.RECEIVED_MESSAGE);
            intent.putExtra("messageCount",messages.size());
            sendBroadcast(intent);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };


}

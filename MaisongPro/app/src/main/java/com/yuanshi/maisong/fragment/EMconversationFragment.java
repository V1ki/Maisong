package com.yuanshi.maisong.fragment;

import android.util.Pair;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yuanshi.iotpro.daoutils.UserBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.IBaseView;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenter;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenterIml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Dengbocheng on 2017/11/27.
 */

public class EMconversationFragment extends EaseConversationListFragment implements IBaseView{
    /**
     * load conversation list
     *
     * @return
    +    */
    protected List<EMConversation> loadConversationList(){
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        IHttpPresenter iHttpPresenter = new IHttpPresenterIml(this,getActivity());
        for(EMConversation emConversation:list){
            if(emConversation.isGroup()){
                iHttpPresenter.phonegetuser("",emConversation.conversationId());
            }else{
                iHttpPresenter.phonegetuser(emConversation.conversationId(),"");
            }
        }
        return list;
    }



    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    public void reloadCrewListData(){

        refresh();
    }

    @Override
    public void onGetPushMessage(String msg) {

    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "phonegetuser":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                UserInfoBean userInfoBean = gson.fromJson(json, UserInfoBean.class);
                userInfoBean.set_id(Long.parseLong(userInfoBean.getPhone()));
                //存入用户信息
                UserBeanDaoUtil userBeanDaoUtil = new UserBeanDaoUtil(getActivity());
                UserInfoBean localUserinfo = userBeanDaoUtil.qeuryUserInfo(Long.parseLong(userInfoBean.getPhone()));
                if(localUserinfo != null){
                    userBeanDaoUtil.updateUserInfo(userInfoBean);
                }else{
                    userBeanDaoUtil.insertUserInfo(userInfoBean);
                }
                break;
        }
    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {

    }

    @Override
    public void onError(String msgType, String msg, Object obj) {

    }
}

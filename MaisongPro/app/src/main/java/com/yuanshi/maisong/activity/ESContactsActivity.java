package com.yuanshi.maisong.activity;

import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.maisong.R;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Dengbocheng on 2017/10/25.
 */
public class ESContactsActivity extends BaseActivity{

    @Override
    protected int getContentViewId() {
        return R.layout.es_contacts_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EaseContactListFragment contactListFragment= new EaseContactListFragment();
        //需要设置联系人列表才能启动fragment
        contactListFragment.setContactsMap(getContacts());
        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                //这里应该执行显示好友详情
//                startActivity(new Intent(ESContactsActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,contactListFragment).commit();
    }

    public HashMap<String,EaseUser> getContacts(){
        try {
            return MyApplication.THREAD_EXCUTER.submit(new Callable<HashMap<String,EaseUser>>() {
                @Override
                public HashMap<String,EaseUser> call(){
                    HashMap<String, EaseUser> contacts = new HashMap<String, EaseUser>();
                    List<String> usernames = null;
                    try {
                        usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                        if(usernames.size() > 0){
                            for(String userName: usernames){
                                EaseUser user = new EaseUser(userName);
                                contacts.put(userName,user);
                            }
                        }
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    return contacts;
                }
            }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new HashMap<String, EaseUser>();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new HashMap<String, EaseUser>();
        }
    }
}
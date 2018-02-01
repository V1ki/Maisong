package com.yuanshi.maisong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuanshi.iotpro.daoutils.FriendApplyBeanDaoUtil;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.bean.FriendsApplyBean;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/8.
 */

public class FriendApplyDetailActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.listView)
    ListView listView;
    private FriendApplyBeanDaoUtil friendApplyBeanDaoUtil;
    private MyFrdApplyInfoAdapter adapter;

    private List<FriendsApplyBean> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.frd_apply_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        friendApplyBeanDaoUtil = new FriendApplyBeanDaoUtil(this);
        adapter = new MyFrdApplyInfoAdapter(this);
        initData();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FriendApplyDetailActivity.this, FriendDetailActivity.class);
                intent.putExtra("phone",list.get(i).getPhone());
                startActivity(intent);
            }
        });
    }

    public void initData(){
        List<FriendsApplyBean> localList = friendApplyBeanDaoUtil.queryAllFrdApplyInfo();
        for(FriendsApplyBean friendsApplyBean: localList){
            if(TextUtils.isEmpty(friendsApplyBean.getAvatar())){
                iHttpPresenter.phonegetuser(friendsApplyBean.getPhone(),"");
            }else{
                list.add(friendsApplyBean);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_icon)
    public void onViewClicked() {
        finish();
    }

    class MyFrdApplyInfoAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context context;

        public MyFrdApplyInfoAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = inflater.inflate(R.layout.frd_apply_item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            final FriendsApplyBean fBean = list.get(i);
            viewHolder.userResonTv.setText(fBean.getReason());
            viewHolder.nickNameTv.setText(fBean.getNickname());
            Glide.with(context).load(fBean.getAvatar()).error(R.drawable.ease_default_avatar).into(viewHolder.headIcon);
            if(fBean.getState() == Constant.FRD_APPLY_STATE_NORMAL){
                viewHolder.doneTv.setVisibility(View.GONE);
                viewHolder.decliendLayout.setVisibility(View.VISIBLE);
                viewHolder.acceptLayout.setVisibility(View.VISIBLE);
            }else if(fBean.getState() == Constant.FRD_APPLY_STATE_ACCEPT){
                viewHolder.doneTv.setVisibility(View.VISIBLE);
                viewHolder.doneTv.setText(R.string.accepted);
                viewHolder.decliendLayout.setVisibility(View.GONE);
                viewHolder.acceptLayout.setVisibility(View.GONE);
            }else if(fBean.getState() == Constant.FRD_APPLY_STATE_DECLINED){
                viewHolder.doneTv.setVisibility(View.VISIBLE);
                viewHolder.doneTv.setText(R.string.declineded);
                viewHolder.decliendLayout.setVisibility(View.GONE);
                viewHolder.acceptLayout.setVisibility(View.GONE);
            }
            viewHolder.acceptTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(fBean.getPhone());
                    } catch (HyphenateException e) {
                        CrashReport.postCatchedException(e);
                    }
                }
            });
            viewHolder.decliendTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(fBean.getPhone());
                    } catch (HyphenateException e) {
                        CrashReport.postCatchedException(e);
                    }
                    fBean.setState(Constant.FRD_APPLY_STATE_DECLINED);
                    friendApplyBeanDaoUtil.updateFrdApplyInfo(fBean);
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.head_icon)
        ImageView headIcon;
        @BindView(R.id.nickNameTv)
        TextView nickNameTv;
        @BindView(R.id.userResonTv)
        TextView userResonTv;
        @BindView(R.id.accept_tv)
        TextView acceptTv;
        @BindView(R.id.accept_layout)
        RelativeLayout acceptLayout;
        @BindView(R.id.decliend_tv)
        TextView decliendTv;
        @BindView(R.id.decliend_layout)
        RelativeLayout decliendLayout;
        @BindView(R.id.done_tv)
        TextView doneTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "phonegetuser":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                UserInfoBean userInfoBean = gson.fromJson(json, UserInfoBean.class);
                FriendsApplyBean fbean = friendApplyBeanDaoUtil.qeuryFrdApplyInfo(Long.parseLong(userInfoBean.getPhone()));
                if(fbean != null){
                    fbean.setPhone(userInfoBean.getPhone());
                    fbean.setNickname(userInfoBean.getNickname());
                    fbean.setAvatar(userInfoBean.getAvatar());
                }
                friendApplyBeanDaoUtil.updateFrdApplyInfo(fbean);
                list.add(fbean);
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onReceiveApplyInfo(String phone, String reason) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onFrdAddSuccess(String phone) {
        try {
            FriendsApplyBean bean = friendApplyBeanDaoUtil.qeuryFrdApplyInfo(Long.parseLong(phone));
            bean.setState(Constant.FRD_APPLY_STATE_ACCEPT);
            friendApplyBeanDaoUtil.updateFrdApplyInfo(bean);
            LoginBeanDaoUtil util = new LoginBeanDaoUtil(this);
            String myphone = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
            LoginInfoBean loginInfoBean = util.qeuryUserInfo(Long.parseLong(myphone));
            if(loginInfoBean!= null){
                Utils.sendHelloMessage(loginInfoBean,phone,getString(R.string.we_ware_friend));
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){
          CrashReport.postCatchedException(e);
        }

    }
}

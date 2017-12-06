package com.yuanshi.maisong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.DailyCallBean;
import com.yuanshi.maisong.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/30.
 * 剧组通知列表页面
 */

public class CrewNotifycationActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.crew_notifycation_listView)
    ListView crewNotifyListView;
    @BindView(R.id.edit_notify)
    ImageButton editNotify;
    private List<DailyCallBean> dailyCallList = new ArrayList<>();
    private MyCallsAdapter adapter;
    private String crewId;

    @Override
    protected int getContentViewId() {
        return R.layout.crew_notifycation_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("crewId");
        if(TextUtils.isEmpty(crewId)){
            Toast.makeText(getApplicationContext(), R.string.crew_id_null, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        iHttpPresenter.index(Constant.HTTP_REQUEST_REMIND,crewId);
        adapter = new MyCallsAdapter(this);
        crewNotifyListView.setAdapter(adapter);
    }

    public void initData(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        dailyCallList = (List<DailyCallBean>) Utils.jsonToList(json, DailyCallBean[].class);
        YLog.e("通告单个数--》"+dailyCallList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case Constant.HTTP_REQUEST_REMIND+":index":
                initData(obj);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.edit_notify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.edit_notify:
                Intent intent = new Intent(this,EditNotifyActivity.class);
                intent.putExtra("editType",Constant.HTTP_REQUEST_REMIND);
                intent.putExtra("crewId", crewId);
                startActivity(intent);
                break;
        }
    }


    private class MyCallsAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyCallsAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dailyCallList.size();
        }

        @Override
        public Object getItem(int i) {
            return dailyCallList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.crew_notify_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final DailyCallBean dailyCallBean = dailyCallList.get(i);
            if (dailyCallBean.getStatus() == DailyCallActivity.CALL_STATE_WITH_DRAW) {//如果已撤回
                holder.titleLayout.setVisibility(View.GONE);
                holder.releaselayout.setVisibility(View.GONE);
                holder.btnLayout.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.GONE);
                holder.withdrawMsgLayout.setVisibility(View.VISIBLE);
                holder.withdrawText.setText(R.string.withdraw);
            } else {
                holder.titleLayout.setVisibility(View.VISIBLE);
                holder.releaselayout.setVisibility(View.VISIBLE);
                holder.btnLayout.setVisibility(View.VISIBLE);
                holder.withdrawMsgLayout.setVisibility(View.GONE);
                holder.withdrawText.setText(R.string.withdraw);
                holder.imageView.setVisibility(View.VISIBLE);
            }

            if (dailyCallBean.isHasRead()) {//阅读状态
                holder.readState.setBackgroundResource(R.drawable.half_corner_grey);
                holder.readState.setText(R.string.has_readed);
            }else{
                holder.readState.setBackgroundResource(R.drawable.half_corner_org);
                holder.readState.setText(R.string.has_no_readed);
            }
            holder.title.setText(dailyCallBean.getTitle());
            String releaseDateTxt = String.format(getString(R.string.calls_release_date), dailyCallBean.getAddtime());
            holder.releaseDate.setText(releaseDateTxt);
            String noReadCountTxt = String.format(getString(R.string.no_read_count), dailyCallBean.getNoreadcount());
            holder.noReadCount.setText(noReadCountTxt);
            Glide.with(CrewNotifycationActivity.this).
                    load(dailyCallBean.getPics()[0]).
                    placeholder(R.mipmap.ic_launcher).
                    error(R.mipmap.delete_icon).
                    thumbnail(0.1f).centerCrop().
                    into(holder.imageView);
            holder.checkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "选择查看第" + i + "条通知", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CrewNotifycationActivity.this, ShowTextImageActivity.class);
                    intent.putExtra("title",getString(R.string.notifycation_of_crew));
                    intent.putExtra("id",dailyCallBean.getId());
                    startActivity(intent);

                }
            });

            holder.withdrawLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "选择撤回第" + i + "条通知", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.readState)
        TextView readState;
        @BindView(R.id.title_layout)
        RelativeLayout titleLayout;
        @BindView(R.id.release_date)
        TextView releaseDate;
        @BindView(R.id.noReadCount)
        TextView noReadCount;
        @BindView(R.id.releaselayout)
        RelativeLayout releaselayout;
        @BindView(R.id.checkLayout)
        LinearLayout checkLayout;
        @BindView(R.id.withdrawLayout)
        LinearLayout withdrawLayout;
        @BindView(R.id.btn_layout)
        LinearLayout btnLayout;
        @BindView(R.id.withdrawText)
        TextView withdrawText;
        @BindView(R.id.withdrawMsgLayout)
        LinearLayout withdrawMsgLayout;
        @BindView(R.id.imageView)
        ImageView imageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

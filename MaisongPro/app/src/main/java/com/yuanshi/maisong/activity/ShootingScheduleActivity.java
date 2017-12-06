package com.yuanshi.maisong.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * 拍摄大计划列表界面
 */

public class ShootingScheduleActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.shooting_schedule_listView)
    ListView shootingScheduleListView;
    private List<DailyCallBean> dailyCallList = new ArrayList<>();
    private MyCallsAdapter adapter;
    private String crewId;
    @Override
    protected int getContentViewId() {
        return R.layout.shooting_schedule_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("crewId");
        if(TextUtils.isEmpty(crewId)){
            Toast.makeText(getApplicationContext(),R.string.crew_id_null, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        iHttpPresenter.index(Constant.HTTP_REQUEST_BIGPLAN,crewId);
        adapter = new MyCallsAdapter(this);
        shootingScheduleListView.setAdapter(adapter);
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
            case Constant.HTTP_REQUEST_BIGPLAN+":index":
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

    @OnClick(R.id.back_icon)
    public void onViewClicked() {
        finish();
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
                view = inflater.inflate(R.layout.daily_call_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            DailyCallBean dailyCallBean = dailyCallList.get(i);
            if (dailyCallBean.getStatus() == DailyCallActivity.CALL_STATE_WITH_DRAW) {//如果已撤回
                holder.titleLayout.setVisibility(View.GONE);
                holder.releaselayout.setVisibility(View.GONE);
                holder.btnLayout.setVisibility(View.GONE);
                holder.withdrawMsgLayout.setVisibility(View.VISIBLE);
                holder.withdrawText.setText(R.string.withdraw);
            } else {
                holder.titleLayout.setVisibility(View.VISIBLE);
                holder.releaselayout.setVisibility(View.VISIBLE);
                holder.btnLayout.setVisibility(View.VISIBLE);
                holder.withdrawMsgLayout.setVisibility(View.GONE);
                holder.withdrawText.setText(R.string.withdraw);
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
            holder.checkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "选择查看第" + i + "条通知", Toast.LENGTH_SHORT).show();
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

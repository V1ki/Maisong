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
 * 剧本扉页列表页面
 */

public class ScriptUpdateActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.edit_script_update)
    ImageButton editScriptUpdate;
    @BindView(R.id.script_update_listView)
    ListView scriptUpdateListView;
    private List<DailyCallBean> dailyCallList = new ArrayList<>();
    private MyCallsAdapter adapter;
    private String crewId;

    @Override
    protected int getContentViewId() {
        return R.layout.script_update_layout;
    }

    @OnClick({R.id.back_icon, R.id.edit_script_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.edit_script_update:
                Intent intent = new Intent(this, EditNotifyActivity.class);
                intent.putExtra("crewId", crewId);
                intent.putExtra("editType",Constant.HTTP_REQUEST_SCRIPTPAGE);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("crewId");
        if(TextUtils.isEmpty(crewId)){
            Toast.makeText(getApplicationContext(), R.string.crew_id_null, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        adapter = new MyCallsAdapter(this);
        scriptUpdateListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iHttpPresenter.index(Constant.HTTP_REQUEST_SCRIPTPAGE,crewId);
    }

    public void initData(Object obj) {
        Gson gson = new Gson();
        if(obj != null){
            String json = gson.toJson(obj);
            dailyCallList = Utils.jsonToList2(json, DailyCallBean.class);
        }else{
            dailyCallList = new ArrayList<>();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case Constant.HTTP_REQUEST_SCRIPTPAGE+":index":
                initData(obj);
                break;
            case Constant.HTTP_REQUEST_SCRIPTPAGE+":doAdd":
                iHttpPresenter.index(Constant.HTTP_REQUEST_SCRIPTPAGE,crewId);
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
            final DailyCallBean dailyCallBean = dailyCallList.get(i);
            if (dailyCallBean.getStatus() == DailyCallActivity.CALL_STATE_WITH_DRAW) {//如果已撤回
                holder.titleLayout.setVisibility(View.GONE);
                holder.releaselayout.setVisibility(View.GONE);
                holder.btnLayout.setVisibility(View.GONE);
                holder.withdrawMsgLayout.setVisibility(View.VISIBLE);
                holder.withdrawText.setText(R.string.withdraw);
            }else{
                holder.titleLayout.setVisibility(View.VISIBLE);
                holder.releaselayout.setVisibility(View.VISIBLE);
                holder.btnLayout.setVisibility(View.VISIBLE);
                holder.withdrawMsgLayout.setVisibility(View.GONE);
                holder.withdrawText.setText(R.string.withdraw);
            }

            if (dailyCallBean.getReaded() == Constant.NOTIFY_READED){//阅读状态
                holder.readState.setBackgroundResource(R.drawable.half_corner_grey);
                holder.readState.setText(R.string.has_readed);
            }else{
                holder.readState.setBackgroundResource(R.drawable.half_corner_org);
                holder.readState.setText(R.string.has_no_readed);
            }
            holder.title.setText(dailyCallBean.getTitle());
            String releaseDateTxt = String.format(getString(R.string.calls_release_date),dailyCallBean.getAddtime());
            holder.releaseDate.setText(releaseDateTxt);
            String noReadCountTxt = String.format(getString(R.string.no_read_count),dailyCallBean.getNoreadcount());
            holder.noReadCount.setText(noReadCountTxt);
            holder.checkLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScriptUpdateActivity.this, ShowTextImageActivity.class);
                    intent.putExtra("title",getString(R.string.script_update));
                    intent.putExtra("id",dailyCallBean.getId());
                    intent.putExtra("requestType",Constant.HTTP_REQUEST_SCRIPTPAGE);
                    startActivity(intent);

                }
            });

            holder.withdrawLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showWithdrawDialog(dailyCallBean);
                }
            });
            return view;
        }
    }

    /**
     * 弹出撤回确认框
     */
    public void showWithdrawDialog(final DailyCallBean dailyCallBean){
        final Dialog mCameraDialog = new Dialog(this, R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.logout_dialog_layout, null);
        TextView contentTv = root.findViewById(R.id.dialog_content);
        contentTv.setText("确定撤回该信息？");
        root.findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.withdrawNotice(dailyCallBean.getCid(),dailyCallBean.getId(),iHttpPresenter,Constant.HTTP_REQUEST_SCRIPTPAGE);
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

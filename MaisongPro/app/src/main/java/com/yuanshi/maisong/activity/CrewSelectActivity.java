package com.yuanshi.maisong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.CrewHttpBean;
import com.yuanshi.maisong.bean.CrewInfoBean;
import com.yuanshi.maisong.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/30.
 */

public class CrewSelectActivity extends BaseActivity {
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.delete_icon)
    ImageView deleteIcon;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.no_search_content_tv)
    TextView noSearchContentTv;
    @BindView(R.id.searchResultListView)
    ListView searchResultListView;

    private List<CrewHttpBean> crewInfoList = new ArrayList<>();
    private MyCrewListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.crew_slect_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter = new MyCrewListAdapter(this);
        searchResultListView.setAdapter(adapter);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(getApplicationContext(), "点击了搜索", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    iHttpPresenter.searchcrew(edSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    public void initData() {
//        CrewInfoBean crewInfoBean = new CrewInfoBean();
//        crewInfoBean.setCrewName("《唐伯虎点蚊香》");
//        crewInfoBean.setDirector("万建坤");
//        crewInfoBean.setRecipeName("圆石影业");
//        crewInfoBean.setProduct_name("唐石山工作室");
//        crewInfoBean.setStartDate("2017-10-10");
//        crewInfoBean.setEndDate("2019-2-28");
//
//        CrewInfoBean crewInfoBean1 = new CrewInfoBean();
//        crewInfoBean1.setCrewName("《鲁迅漂流记》");
//        crewInfoBean1.setDirector("万建坤");
////        crewInfoBean1.setRecipeName("圆石影业");
//        crewInfoBean1.setProduct_name("唐石山工作室");
//        crewInfoBean1.setStartDate("2017-10-10");
//        crewInfoBean1.setEndDate("2019-2-28");
//
//        CrewInfoBean crewInfoBean2 = new CrewInfoBean();
//        crewInfoBean2.setCrewName("《钢铁侠是怎样炼成的》");
//        crewInfoBean2.setDirector("万建坤");
////        crewInfoBean2.setRecipeName("圆石影业");
////        crewInfoBean2.setProduct_name("唐石山工作室");
//        crewInfoBean2.setStartDate("2017-10-10");
//        crewInfoBean2.setEndDate("2019-2-28");
//
//        crewInfoList.add(crewInfoBean);
//        crewInfoList.add(crewInfoBean1);
//        crewInfoList.add(crewInfoBean2);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "searchcrew":
                String json = new Gson().toJson(obj);
                crewInfoList = Utils.jsonToList(json,CrewHttpBean[].class);
                initData();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.delete_icon, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_icon:
                edSearch.setText("");
                edSearch.requestFocus();
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    private class MyCrewListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyCrewListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return crewInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return crewInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view == null){
                view = inflater.inflate(R.layout.crew_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            CrewHttpBean crewInfoBean = crewInfoList.get(i);
            if(TextUtils.isEmpty(crewInfoBean.getProduced())){
                holder.productLayout.setVisibility(View.GONE);
            }else{
                holder.productorName.setVisibility(View.VISIBLE);
                holder.productorName.setText(crewInfoBean.getProduced());
            }
            if(TextUtils.isEmpty(crewInfoBean.getMakinger())){
                holder.recipeLayout.setVisibility(View.GONE);
            }else{
                holder.recipeLayout.setVisibility(View.VISIBLE);
                holder.recipeName.setText(crewInfoBean.getMakinger());
            }
            holder.crewName.setText(crewInfoBean.getTitle());
            holder.directorName.setText(crewInfoBean.getDirector());
            holder.madeDate.setText(crewInfoBean.getStime()+"-"+crewInfoBean.getEtime());

            holder.applyJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"选择加入"+crewInfoList.get(i).getTitle()+"剧组",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CrewSelectActivity.this, JoinCrewActivity.class);
                    intent.putExtra("crewId",crewInfoList.get(i).getId());
                    intent.putExtra("crewName",crewInfoList.get(i).getTitle());
                    startActivity(intent);

                }
            });

            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.crewName)
        TextView crewName;
        @BindView(R.id.applyJoin)
        TextView applyJoin;
        @BindView(R.id.directorName)
        TextView directorName;
        @BindView(R.id.directorLayout)
        LinearLayout directorLayout;
        @BindView(R.id.productorName)
        TextView productorName;
        @BindView(R.id.productLayout)
        LinearLayout productLayout;
        @BindView(R.id.recipeName)
        TextView recipeName;
        @BindView(R.id.recipeLayout)
        LinearLayout recipeLayout;
        @BindView(R.id.madeDate)
        TextView madeDate;
        @BindView(R.id.dataLayout)
        LinearLayout dataLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

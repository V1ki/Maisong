package com.yuanshi.maisong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/11/2.
 * 备忘录搜索页面
 */
public class SearchMemoireActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageView backIcon;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.delete_icon)
    ImageView deleteIcon;
    @BindView(R.id.edit_btn)
    ImageView editBtn;
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.searchResultListView)
    ListView searchResultListView;
    private MyMemoireListAdapter adapter;
    private String crewId;
    private ArrayList<DailyCallBean> memorandumList = new ArrayList<>();
    @Override
    protected int getContentViewId() {
        return R.layout.search_memoire_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("crewId");
        if(TextUtils.isEmpty(crewId)){
            Toast.makeText(getApplicationContext(),R.string.crew_id_null,Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        iHttpPresenter.index(Constant.HTTP_REQUEST_MEMORANDUM,crewId);
        adapter = new MyMemoireListAdapter(this);
        searchResultListView.setAdapter(adapter);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    memorandumList.clear();
                    adapter.notifyDataSetChanged();
                    iHttpPresenter.searchMemorandum(crewId,edSearch.getText().toString());
                }
                return false;
            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){

                }else{
                    iHttpPresenter.index(Constant.HTTP_REQUEST_MEMORANDUM,crewId);
                }
            }
        });

        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchMemoireActivity.this, ShowTextImageActivity.class);
                intent.putExtra("id",memorandumList.get(i).getId());
                intent.putExtra("title",getString(R.string.memoire));
                intent.putExtra("requestType",Constant.HTTP_REQUEST_MEMORANDUM);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(edSearch.getText().toString().trim())){

        }else{
            iHttpPresenter.index(Constant.HTTP_REQUEST_MEMORANDUM,crewId);
        }
    }

    public void initData(Object obj) {
        YLog.e("备忘列表获取成功");
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        memorandumList = (ArrayList<DailyCallBean>) Utils.jsonToList2(json, DailyCallBean.class);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.delete_icon, R.id.edit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.delete_icon:
                edSearch.setText("");
//                iHttpPresenter.index(Constant.HTTP_REQUEST_MEMORANDUM,crewId);
                break;
            case R.id.edit_btn:
                Intent intent = new Intent(this, EditNotifyActivity.class);
                intent.putExtra("editType", Constant.HTTP_REQUEST_MEMORANDUM);
                intent.putExtra("crewId", crewId);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case Constant.HTTP_REQUEST_MEMORANDUM+":index":
                initData(obj);
                break;
            case "searchMemorandum":
                initData(obj);
                break;
        }
    }
    private class MyMemoireListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        public MyMemoireListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return memorandumList.size();
        }

        @Override
        public Object getItem(int i) {
            return memorandumList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.memoire_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            DailyCallBean memoireInfoBean = memorandumList.get(i);
            holder.titleText.setText(memoireInfoBean.getTitle());
            holder.createDate.setText(memoireInfoBean.getAddtime());
            holder.synopsis.setText(memoireInfoBean.getContent());
            return view;
        }
    }
    static class ViewHolder {
        @BindView(R.id.title_text)
        TextView titleText;
        @BindView(R.id.create_date)
        TextView createDate;
        @BindView(R.id.synopsis)
        TextView synopsis;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

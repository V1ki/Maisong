package com.yuanshi.maisong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.MemoireInfoBean;

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

    private ArrayList<MemoireInfoBean> infoList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.search_memoire_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter = new MyMemoireListAdapter(this);
        searchResultListView.setAdapter(adapter);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(getApplicationContext(), "点击了搜索", Toast.LENGTH_SHORT).show();
                    infoList.clear();
                    adapter.notifyDataSetChanged();
                    initData();
                }
                return false;
            }
        });

        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchMemoireActivity.this, ShowTextImageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initData() {
        MemoireInfoBean memoireInfoBean = new MemoireInfoBean();
        memoireInfoBean.setTitle("这是一个神奇的标题，多打几个字就更神奇了！");
        memoireInfoBean.setCreateDate("2017-11-11");
        memoireInfoBean.setSynopsis("这里是内容概要，也没有什么好提要的");
        infoList.add(memoireInfoBean);
        infoList.add(memoireInfoBean);
        infoList.add(memoireInfoBean);
        infoList.add(memoireInfoBean);
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
                break;
            case R.id.edit_btn:
                Intent intent = new Intent(this, EditNotifyActivity.class);
                startActivity(intent);
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
            return infoList.size();
        }

        @Override
        public Object getItem(int i) {
            return infoList.get(i);
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
            MemoireInfoBean memoireInfoBean = infoList.get(i);
            holder.titleText.setText(memoireInfoBean.getTitle());
            holder.createDate.setText(memoireInfoBean.getCreateDate());
            holder.synopsis.setText(memoireInfoBean.getSynopsis());
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
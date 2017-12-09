package com.yuanshi.maisong.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.MsgBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/12.
 */
public class MessagesFragment extends Fragment {
    @BindView(R.id.add_btn)
    ImageView addBtn;
    Unbinder unbinder;
    private View m_View;
    public static MessagesFragment messagesFragment;
    public ArrayList<MsgBean> msgList = new ArrayList<>();
    private MyMsgListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (m_View == null) {
            m_View = inflater.inflate(R.layout.messages_layout, null);
        }
        ViewGroup parent = (ViewGroup) m_View.getParent();
        if (parent != null) {
            parent.removeView(m_View);
        }
        messagesFragment = this;
        unbinder = ButterKnife.bind(this, m_View);
        initView();
        return m_View;
    }

    public static MessagesFragment getInstance() {
        if (messagesFragment == null) {
            messagesFragment = new MessagesFragment();
        }
        return messagesFragment;
    }

    private void initView() {
//        MsgBean msgBean = new MsgBean();
//        msgList.add(msgBean);
//        msgList.add(msgBean);
//        msgList.add(msgBean);
//        msgList.add(msgBean);
//        adapter = new MyMsgListViewAdapter(getActivity());
//        msgListview.setAdapter(adapter);
//        msgListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), ChatActivity.class);
//                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
//                intent.putExtra(EaseConstant.EXTRA_USER_ID, "dbctest1");
//                getActivity().startActivity(intent);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_btn)
    public void onViewClicked() {
        Toast.makeText(getActivity().getApplicationContext(), "添加会话", Toast.LENGTH_SHORT).show();
    }

    class MyMsgListViewAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public MyMsgListViewAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return msgList.size();
        }

        @Override
        public Object getItem(int i) {
            return msgList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder ;
            if(view == null){
                view = (LinearLayout) layoutInflater.inflate(R.layout.msg_list_item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.msgTitle.setText("这是一个神奇的消息标题");
            return view;
        }
    }
    static class ViewHolder {
        @BindView(R.id.headIcon)
        ImageView headIcon;
        @BindView(R.id.msgTitle)
        TextView msgTitle;
        @BindView(R.id.msg_time)
        TextView msgTime;
        @BindView(R.id.msgDetail)
        TextView msgDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

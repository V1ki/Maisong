package com.yuanshi.maisong.utils.recycleviewutils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.yuanshi.maisong.R;

/**
 * Created by Dengbocheng on 2017/12/1.
 */

public class EditUtilsRecycleAdapter extends RecyclerView.Adapter<EditUtilsRecycleAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;
    private int[] imgres;
    private Context context;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public EditUtilsRecycleAdapter(int[] imgres , Context context) {
        this.context = context;
        this.imgres = imgres;
    }

    public void updateData(int[] imgres) {
        this.imgres = imgres;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.action_btn.setImageResource(imgres[position]);
        holder.itemView.setTag(imgres[position]);
    }
    @Override
    public int getItemCount() {
        return imgres == null ? 0 : imgres.length;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取图片资源Id
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取图片资源Id
            mOnItemLongClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return true;
    }


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton action_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            action_btn = (ImageButton) itemView.findViewById(R.id.action_btn);
        }
    }
}

package com.yuanshi.maisong.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.ContactMember;
import com.yuanshi.maisong.view.CircleImageView;

import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<ContactMember> list = null;
	private Context mContext;
	
	public SortAdapter(Context mContext, List<ContactMember> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	public void updateListView(List<ContactMember> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final ContactMember mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.contact_list_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.departmentTv = view.findViewById(R.id.departmentTv);
			viewHolder.positionTv = view.findViewById(R.id.positionTv);
			viewHolder.headIcon = view.findViewById(R.id.headIcon);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		int section = getSectionForPosition(position);
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setText(this.list.get(position).getUsername());
		viewHolder.departmentTv.setText(this.list.get(position).getDepartment());
		viewHolder.positionTv.setText(this.list.get(position).getPosition());
		if(!TextUtils.isEmpty(this.list.get(position).getAvatar())){
			Glide.with(mContext).load(this.list.get(position).getAvatar()).error(R.mipmap.ease_default_avatar).into(viewHolder.headIcon);
		}
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView departmentTv;
		TextView positionTv;
		CircleImageView headIcon;
	}


	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
package com.yuanshi.maisong.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanshi.maisong.R;
import com.yuanshi.maisong.activity.MainActivity;


public class BottomTabItem extends RelativeLayout {
	private int drawableID_normal,drawableID_checked;
	private String tabText;
	private ImageView icon;
	private TextView tabTextView;
	public boolean isCheck;
	private Context context;
	public int id;
	public BottomTabItem(Context context,int drawableID_normal,int drawableID_checked,int tabText,int id) {
		super(context);
		this.context = context;
		this.id = id;
		this.drawableID_normal = drawableID_normal;
		this.drawableID_checked = drawableID_checked;
		LayoutInflater inflater = LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.bottom_tab_item, null);
		layout.setOnClickListener(listener);
		icon = (ImageView) layout.findViewById(R.id.tab_icon);
		icon.setImageResource(drawableID_normal);
		tabTextView = (TextView) layout.findViewById(R.id.tab_text);
		tabTextView.setText(getResources().getString(tabText));
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);//addRule参数对应RelativeLayout XML布局的属性
		addView(layout, params);
	}
	public void setItemChecked(boolean isChecked){
		if(isChecked){
			icon.setImageResource(drawableID_checked);
			tabTextView.setTextColor(getResources().getColor(R.color.tab_select));
		}else{
			icon.setImageResource(drawableID_normal);
			tabTextView.setTextColor(getResources().getColor(R.color.tab_unselected));
		}
	}
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (MainActivity.instance != null) {
//				if (MainActivity.instance.mainViewPager.getCurrentItem() != id) {
//					MainActivity.instance.mainViewPager.setCurrentItem(id % 3);
					MainActivity.instance.chageBottonTab(id);
//				}
			}
		}
	};
	
	
}

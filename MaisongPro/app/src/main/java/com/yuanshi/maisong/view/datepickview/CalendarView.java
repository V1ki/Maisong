package com.yuanshi.maisong.view.datepickview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.DateBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarView extends LinearLayout {
	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_date;
	private TextView tv_week;
	private TextView tv_today;
	private MonthDateView monthDateView;
	private DateViewClick dateViewClick;
	private TextView checkTv;
	private TextView addTxteTv;
	private TextView dataText2;
	private RelativeLayout add_btn_layout;
	private RelativeLayout check_layout;
	private OnCreateMemoClick onCreateMemoClick;
	private OnCheckMemoClick onCheckMemoClick;
	private OnItemClickListener onItemClickListener;
	private List<DayAndPrice> listDayAndPrice = new ArrayList<DayAndPrice>();
	private List<WorkOrRelax> listWorkOrRelax = new ArrayList<WorkOrRelax>();
	private Calendar showingCarlender;
	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.activity_date, this);
		iv_left = (ImageView) view.findViewById(R.id.iv_left);
		iv_right = (ImageView) view.findViewById(R.id.iv_right);
		checkTv = view.findViewById(R.id.check_tv);
		addTxteTv = view.findViewById(R.id.addTxteTv);
		dataText2 = view.findViewById(R.id.date_text_2);
		monthDateView = (MonthDateView) view.findViewById(R.id.monthDateView);
		tv_date = (TextView) view.findViewById(R.id.date_text);
		tv_week  =(TextView) view.findViewById(R.id.selectDate_text);
		tv_today = (TextView) view.findViewById(R.id.tv_today);
		add_btn_layout = view.findViewById(R.id.add_btn_layout);
		check_layout = findViewById(R.id.check_layout);
		showingCarlender = Calendar.getInstance();
		showLouchDate(showingCarlender);
		monthDateView.setTextView(tv_date,tv_week);
		monthDateView.setDayAndPriceList(listDayAndPrice);
		monthDateView.setDateClick(new MonthDateView.DateClick() {
			@Override
			public void onClickOnDate(DayAndPrice dayAndPrice) {
				if(dateViewClick != null){
					if(monthDateView.getSelectDate()== null)  return;
					if(monthDateView.getSelectDate().getYear() == dayAndPrice.year &&
							monthDateView.getSelectDate().getMonth() == dayAndPrice.month-1 &&
							monthDateView.getSelectDate().getDay() == dayAndPrice.day){//选中的日期被点击
						dateViewClick.dateClick(dayAndPrice);
					}
				}
				if(onItemClickListener != null){
					onItemClickListener.onItemClick(dayAndPrice);
				}
			}
		});
		monthDateView.setOnDateSelectListener(new MonthDateView.OnDateSelectListener() {
			@Override
			public void onSelect(DayAndPrice dayAndPrice) {
				showingCarlender = monthDateView.getShowCalender();
				showLouchDate(showingCarlender);
				dayAndPrice.month = dayAndPrice.month+1;
			}
		});
		setOnlistener();
	}

	public void showLouchDate(Calendar calendar){
		LunarCalendar lunarCalendar = new LunarCalendar(calendar);
		Date date = lunarCalendar.getDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
//		CalendarUtil calendarUtil = new CalendarUtil(calendar);
		dataText2.setText(lunarCalendar.toString());
	}
	/**
	 * 设置监听
	 */
	private void setOnlistener(){
		iv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
			}
		});
		
		iv_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
			}
		});
		
		tv_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.setTodayToView();
			}
		});

		checkTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
					if(onCheckMemoClick != null){
						onCheckMemoClick.checkMemoClick(view);
					}
			}
		});

		addTxteTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(onCreateMemoClick != null){
					if(monthDateView.getSelectDate()!= null &&
							monthDateView.getSelectDate().getYear() != 0 &&
							monthDateView.getSelectDate().getDay() != 0){
						onCreateMemoClick.createMemoClick(view);
					}else{
						Toast.makeText(getContext(),R.string.did_not_select_date,Toast.LENGTH_SHORT).show();
					}

				}
			}
		});
	}

	public void hideMemerLayout(){
		add_btn_layout.setVisibility(INVISIBLE);
		check_layout.setVisibility(INVISIBLE);
	}
	
	/**
	 * 设置有事务的号码
	 * @param listDayAndPrice
	 */
	public void setListDayAndPrice(List<DayAndPrice> listDayAndPrice) {
		this.listDayAndPrice = listDayAndPrice;
		monthDateView.setDayAndPriceList(listDayAndPrice);
	}
	/**
	 * 获取所选择的年份
	 * @return
	 */
	public int getSelectYear(){
		return monthDateView.getmSelYear();
	}
	
	/**
	 * 获取所选择的月份
	 * @return
	 */
	public int getSelectMonth(){
		return monthDateView.getmSelMonth();
	}

	public boolean isdayAndPriceList(){
		return monthDateView.isdayAndPriceList();
	}
	/**
	 * 获取所选择的日期
	 * @return
	 */
	public int getSelectDay(){
		return monthDateView.getmSelDay();
	}
	
	/**
	 * 设置日期的click事件
	 * @param dateViewClick
	 */
	public void setDateViewClick(DateViewClick dateViewClick) {
		this.dateViewClick = dateViewClick;
	}
	public interface DateViewClick{
		public void dateClick(DayAndPrice dayAndPrice);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
	public interface OnItemClickListener{
		 void onItemClick(DayAndPrice dayAndPrice);
	}

	/**
	 * 添加备忘点击回调接口
	 */
	public interface OnCreateMemoClick{
		 void createMemoClick(View view);
	}
	public void setOnCreateMemoClick(OnCreateMemoClick onCreateMemoClick){
		this.onCreateMemoClick = onCreateMemoClick;
	}


	/**
	 * 查看备忘点击回调接口
	 */
	public interface OnCheckMemoClick{
		void checkMemoClick(View view);
	}
	public void setOnCheckMemoClick(OnCheckMemoClick onCheckMemoClick){
		this.onCheckMemoClick = onCheckMemoClick;
	}
	public void setSelectYearMonthDay(int year, int month, int day){
		monthDateView.setSelectYearMonthDay(year, month, day);
	}

	public boolean hasThings(DayAndPrice dayAndPrice){
		return monthDateView.hasThings(dayAndPrice);
	}

	public DateBean getSelectDate(){
		return monthDateView.getSelectDate();
	}
}

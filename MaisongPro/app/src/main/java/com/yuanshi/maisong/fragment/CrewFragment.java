package com.yuanshi.maisong.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desmond.citypicker.bean.BaseCity;
import com.desmond.citypicker.callback.IOnCityPickerCheckedCallBack;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenter;
import com.yuanshi.iotpro.publiclib.presenter.IHttpPresenterIml;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.ImageViewUtil;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.activity.CreateCrewActivity;
import com.yuanshi.maisong.activity.CrewCotactListActivity;
import com.yuanshi.maisong.activity.CrewNotifycationActivity;
import com.yuanshi.maisong.activity.CrewSelectActivity;
import com.yuanshi.maisong.activity.DailyCallActivity;
import com.yuanshi.maisong.activity.EditNotifyActivity;
import com.yuanshi.maisong.activity.MainActivity;
import com.yuanshi.maisong.activity.ProfileActivity;
import com.yuanshi.maisong.activity.ScriptUpdateActivity;
import com.yuanshi.maisong.activity.SearchMemoireActivity;
import com.yuanshi.maisong.activity.ShootingScheduleActivity;
import com.yuanshi.maisong.adapter.WeatherListAdapter;
import com.yuanshi.maisong.bean.CrewHttpBean;
import com.yuanshi.maisong.bean.DailyCallBean;
import com.yuanshi.maisong.bean.DateBean;
import com.yuanshi.maisong.bean.WeatherBean;
import com.yuanshi.maisong.utils.Constact;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.utils.recycleviewutils.DividerItemDecoration;
import com.yuanshi.maisong.utils.recycleviewutils.PagingScrollHelper;
import com.yuanshi.maisong.view.WheelView2;
import com.yuanshi.maisong.view.datepickview.CalendarView;
import com.yuanshi.maisong.view.datepickview.DayAndPrice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CrewFragment extends BaseFragment implements PagingScrollHelper.onPageChangeListener {
    @BindView(R.id.join_crew_btn)
    TextView joinCrewBtn;
    @BindView(R.id.create_crew_btn)
    TextView createCrewBtn;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.crew_name)
    TextView crewName;
    @BindView(R.id.memorandum)
    TextView memorandum;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    Unbinder unbinder;
    @BindView(R.id.call_sheet)
    TextView callSheet;
    @BindView(R.id.notifycation_of_crew)
    TextView notifycationOfCrew;
    @BindView(R.id.script_update)
    TextView scriptUpdate;
    @BindView(R.id.shooting_schedule)
    TextView shootingSchedule;
    @BindView(R.id.crew_contacts)
    TextView crewContacts;
    @BindView(R.id.profile)
    TextView profile;
    @BindView(R.id.weather_list_layout)
    RecyclerView weatherListLayout;
    @BindView(R.id.textView5)
    TextView textView5;
    Unbinder unbinder1;
    @BindView(R.id.memorandum_title)
    TextView memorandumTitle;
    @BindView(R.id.memorandum_layout)
    LinearLayout memorandumLayout;
    @BindView(R.id.crew_name_img)
    ImageView crewNameImg;
    @BindView(R.id.call_sheet_img)
    ImageView callSheetImg;
    @BindView(R.id.notifycation_img)
    ImageView notifycationImg;
    @BindView(R.id.script_update_img)
    ImageView scriptUpdateImg;
    @BindView(R.id.shooting_schedule_img)
    ImageView shootingScheduleImg;
    @BindView(R.id.contact_list_img)
    ImageView contactListImg;
    @BindView(R.id.profile_img)
    ImageView profileImg;
    @BindView(R.id.end_img)
    ImageView endImg;
    private View m_View;
    public static CrewFragment crewFragment;
    private IHttpPresenter iHttpPresenter;
    private CrewHttpBean currentCrew;
    private WeatherListAdapter adapter;
    private List<WeatherBean> weatherList = new ArrayList<>();
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private ArrayList<DayAndPrice> memorandumDateList = new ArrayList<>();
    private ArrayList<DailyCallBean> memorandumList = new ArrayList<>();
    private SimpleDateFormat spt = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected View getMainView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        iHttpPresenter = new IHttpPresenterIml(this, getActivity());
        if (m_View == null) {
            m_View = inflater.inflate(R.layout.crew_layout, null);
        }
        ViewGroup parent = (ViewGroup) m_View.getParent();
        if (parent != null) {
            parent.removeView(m_View);
        }
        crewFragment = this;
        unbinder = ButterKnife.bind(this, m_View);
        scaleImageView();
        initView();
        return m_View;
    }

    /**
     * 等比例拉伸IamgeView图片
     */
    public void scaleImageView() {
        ImageViewUtil.matchAll(this.getActivity(), crewNameImg);
        ImageViewUtil.matchAll(this.getActivity(), callSheetImg);
    }

    public static CrewFragment getInstance() {
        if (crewFragment == null) {
            crewFragment = new CrewFragment();
        }
        return crewFragment;
    }

    private void initView() {
        memorandum.setText(Utils.getStringDateFromMillis(System.currentTimeMillis(), "yyyy年MM月dd日") + ">");
        scrollHelper.setUpRecycleView(weatherListLayout);
        scrollHelper.setOnPageChangeListener(this);
        weatherList = new ArrayList<>();
        WeatherBean defaultBean = new WeatherBean();//显示默认的无天气信息
        defaultBean.setTemperatureMax(0.0);
        defaultBean.setTemperatureMin(0.0);
        defaultBean.setSunriseTime(System.currentTimeMillis()/1000/3600*3600);
        defaultBean.setSunsetTime(System.currentTimeMillis()/1000/3600*3600);
        defaultBean.setSummary("暂无");
        defaultBean.setIcon("unknown");
        localCity = "北京市";
        weatherList.add(defaultBean);
        adapter = new WeatherListAdapter((ArrayList<WeatherBean>) weatherList, getActivity(), localCity, new IOnCityPickerCheckedCallBack() {
            @Override
            public void onCityPickerChecked(BaseCity baseCity) {
                if(baseCity!= null && !TextUtils.isEmpty(baseCity.getCityName())){
                    YLog.e(baseCity.getCityName()+":"+baseCity.getId());
                    Address address = Utils.getLocationFromCityName(CrewFragment.this.getContext(),baseCity.getCityName(),handler);
                    getWeatherInfo(address.getLatitude(),address.getLongitude());
                }
            }
        });
        weatherListLayout.setAdapter(adapter);
        loadWeather(weatherList);
        Location location = Utils.getLocation(this.getContext());
        if(location != null){
            getWeatherInfo(location.getLatitude(),location.getLongitude());//获取天气信息；
        }else{
            getWeatherInfo(39.9046900000,116.4071700000);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private final int GET_WEATHERINFO_SUCCESS = 0x0010;//获取天气信息成功
    public static final int GET_WERTHERINFO_FAILD = 0x0020;//获取天气信息失败
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_WEATHERINFO_SUCCESS:
                    List<WeatherBean> weatherBeanlist = (List<WeatherBean>) msg.obj;
                    loadWeather(weatherBeanlist);
                    adapter.setLocalCity(localCity);
                    adapter.setmData(weatherBeanlist);
                    adapter.notifyDataSetChanged();
                    break;
                case GET_WERTHERINFO_FAILD:
                    Toast.makeText(getActivity().getApplicationContext(), R.string.weather_got_faild, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void reloadCrewListData() {
        if (((MainActivity) getActivity()).crewList != null && ((MainActivity) getActivity()).crewList.size() > 0) {
            if (!TextUtils.isEmpty(getCurrentCrewName()) && findCrewByName(getCurrentCrewName()) != null) {
                currentCrew = findCrewByName(getCurrentCrewName());
                crewName.setText(currentCrew.getTitle());
            } else {
                crewName.setText(((MainActivity) getActivity()).crewList.get(0).getTitle());
                currentCrew = ((MainActivity) getActivity()).crewList.get(0);
            }
            saveToSharedPreference(currentCrew.getTitle());
        }
    }

    /**
     * 加载天气信息
     */
    public void loadWeather(List<WeatherBean> weatherBeans) {
        YLog.e("开始加载天气--->" + weatherBeans.size());
        weatherList = weatherBeans;
        LinearLayoutManager hLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration hDividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
        weatherListLayout.setLayoutManager(hLinearLayoutManager);
        weatherListLayout.addItemDecoration(hDividerItemDecoration);
        scrollHelper.updateLayoutManger();
        adapter.updateData(weatherList);
    }

    private String localCity;//当前所在城市

    public void getWeatherInfo(final double lat,final double longit) {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ForecastIO fio = new ForecastIO(Constact.DARK_SKY_KEY); //instantiate the class with the API key.
                fio.setUnits(ForecastIO.UNITS_SI);
                //sets the units as SI - optional
                fio.setLang("zh");
                fio.setExcludeURL("hourly,minutely");
                YLog.e("经纬度--》" + String.valueOf(lat) + "-----" + String.valueOf(longit));
                fio.getForecast(String.valueOf(lat), String.valueOf(longit));   //sets the latitude and longitude - not optional
                //it will fail to get forecast if it is not set
                //this method should be called after the options were set
                localCity = Utils.getLocalCity(CrewFragment.this.getActivity(), lat, longit,handler);
                JsonObject jsonObject = fio.getDaily();
                JsonArray jsonArray = (JsonArray) jsonObject.get("data");
                if (jsonArray != null && jsonArray.size() > 0) {
                    YLog.e("天气信息获取成功" + jsonArray.toString());
                    final List<WeatherBean> weatherBeanList = Utils.jsonToList(jsonArray.toString(), WeatherBean[].class);
                    Message msg = new Message();
                    msg.what = GET_WEATHERINFO_SUCCESS;
                    msg.obj = weatherBeanList;
                    handler.sendMessage(msg);
                } else {
                    YLog.e("天气信息获取失败");
                    Message msg = new Message();
                    msg.what = GET_WERTHERINFO_FAILD;
                    handler.sendMessage(msg);
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.call_sheet, R.id.notifycation_of_crew, R.id.script_update, R.id.shooting_schedule,
            R.id.crew_contacts, R.id.profile, R.id.join_crew_btn, R.id.create_crew_btn, R.id.crew_name,
            R.id.memorandum_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.join_crew_btn://加入剧组,跳转到剧组搜索页面
                intent.setClass(getActivity(), CrewSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.create_crew_btn://创建剧组，跳转创建剧组页面
                intent.setClass(getActivity(), CreateCrewActivity.class);
                startActivity(intent);
                break;
            case R.id.crew_name://点击剧组名称，切换已添加剧组
                showSelectCrewDialog();
                break;
            case R.id.memorandum_layout://点击备忘录，弹出日历选项
                showDatePickerLayout();
                break;
            case R.id.call_sheet://点击每日通告单，查看每日通告
                intent.setClass(getActivity(), DailyCallActivity.class);
                intent.putExtra("crewId", currentCrew.getId());
                startActivity(intent);
                break;
            case R.id.notifycation_of_crew://点击剧组通知，查看剧组通知
                intent.setClass(getActivity(), CrewNotifycationActivity.class);
                intent.putExtra("crewId", currentCrew.getId());
                startActivity(intent);
                break;
            case R.id.script_update://点击剧本扉页
                intent.setClass(getActivity(), ScriptUpdateActivity.class);
                intent.putExtra("crewId", currentCrew.getId());
                startActivity(intent);
                break;
            case R.id.shooting_schedule://点击拍摄大计划
                intent.setClass(getActivity(), ShootingScheduleActivity.class);
                intent.putExtra("crewId", currentCrew.getId());
                startActivity(intent);
                break;
            case R.id.crew_contacts://点击剧组通讯录
                intent.setClass(getActivity(), CrewCotactListActivity.class);
                intent.putExtra("crewId", currentCrew.getId());
                startActivity(intent);
                break;
            case R.id.profile://点击我在剧组
                intent.setClass(getActivity(), ProfileActivity.class);
                if (currentCrew != null && !TextUtils.isEmpty(currentCrew.getId())) {
                    intent.putExtra("groupId", currentCrew.getId());
                    intent.putExtra("crewName", currentCrew.getTitle());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.no_check_crew, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public CrewHttpBean findCrewByName(String gourpTitle) {
        for (CrewHttpBean crewhttpBean : ((MainActivity) getActivity()).crewList) {
            if (crewhttpBean.getTitle().equals(gourpTitle)) {
                return crewhttpBean;
            }
        }
        return null;
    }


    private void showSelectCrewDialog() {
        final Dialog mCameraDialog = new Dialog(getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.bottom_dialog_layout, null);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        final WheelView2 wheelView = (WheelView2) root.findViewById(R.id.wheelView);
        ArrayList nameList = new ArrayList();
        for (CrewHttpBean bean : ((MainActivity) getActivity()).crewList) {
            nameList.add(bean.getTitle());
        }
        wheelView.setItems(nameList);
        wheelView.setSeletion(nameList.indexOf(crewName.getText().toString()));
        root.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCameraDialog != null && mCameraDialog.isShowing()) {
                    mCameraDialog.dismiss();
                }
            }
        });
        root.findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//此处处理点击了已选剧组后续操作
                if (mCameraDialog != null && mCameraDialog.isShowing()) {
                    crewName.setText(wheelView.getSeletedItem());
                    currentCrew = findCrewByName(wheelView.getSeletedItem());
                    saveToSharedPreference(wheelView.getSeletedItem());
                    mCameraDialog.dismiss();
                }
            }
        });
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width; // 宽度
        root.measure(0, 0);
        lp.height = height * 2 / 5;

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }


    /**
     * 将选中的剧组保存到sp
     *
     * @param crewTitle
     */
    public void saveToSharedPreference(String crewTitle) {
        getActivity().getSharedPreferences(Constant.MAIN_SH_NAME, Context.MODE_PRIVATE).edit().putString(Constant.CURRENT_CREW_NAME_KEY, crewTitle).commit();
        getMemorandum();
    }

    public void getMemorandum() {
        memorandumTitle.setText(R.string.no_memoire_today);
        memorandumDateList.clear();
        iHttpPresenter.index(Constant.HTTP_REQUEST_MEMORANDUM, currentCrew.getId());
    }

    /**
     * 将选中的剧组保存到sp
     */
    public String getCurrentCrewName() {
        return getActivity().getSharedPreferences(Constant.MAIN_SH_NAME, Context.MODE_PRIVATE).getString(Constant.CURRENT_CREW_NAME_KEY, "");
    }

    private void showDatePickerLayout() {
        final Dialog mCameraDialog = new Dialog(getActivity(), R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.date_picker_layout, null);
        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        calendarView.setListDayAndPrice(memorandumDateList);
        Calendar calendar = Calendar.getInstance();
        YLog.e("设置日期");
        calendarView.setSelectYearMonthDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendarView.setDateViewClick(new CalendarView.DateViewClick() {
            @Override
            public void dateClick(DayAndPrice dayAndPrice) {//回调返回月份从0开始计数
                YLog.e("选中日期被点击");
//                if (calendarView.hasThings(dayAndPrice)) {
//                    Intent intent = new Intent(getActivity(), ShowTextImageActivity.class);
//                    intent.putExtra("id","");
//                    intent.putExtra("title",getString(R.string.memoire));
//                    intent.putExtra("requestType",Constant.HTTP_REQUEST_MEMORANDUM);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), "本日无备忘！", Toast.LENGTH_SHORT).show();
//                }
//                mCameraDialog.dismiss();
            }
        });

        calendarView.setOnCreateMemoClick(new CalendarView.OnCreateMemoClick() {
            @Override
            public void createMemoClick(View view) {//回调返回月份从0开始计数
                DateBean dateBean = calendarView.getSelectDate();
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateBean.getYear(), dateBean.getMonth(), dateBean.getDay());
                YLog.e("选中日期毫秒---》" + calendar.getTimeInMillis() + ":" + System.currentTimeMillis());
                String addtime = Utils.getStringDateFromMillis(calendar.getTimeInMillis(), "yyyy-MM-dd");
                YLog.e("选择了添加时间---》" + addtime);
                Intent intent = new Intent(getActivity(), EditNotifyActivity.class);
                intent.putExtra("editType", Constant.HTTP_REQUEST_MEMORANDUM);
                intent.putExtra("crewId", currentCrew.getId());
                intent.putExtra("addtime", addtime);
                startActivity(intent);
                mCameraDialog.dismiss();
            }
        });
        calendarView.setOnCheckMemoClick(new CalendarView.OnCheckMemoClick() {
            @Override
            public void checkMemoClick(View view) {//回调返回月份从0开始计数
//                if(calendarView.isdayAndPriceList()){
                Intent intent = new Intent(getActivity(), SearchMemoireActivity.class);
                intent.putExtra("crewId", currentCrew.getId());
                startActivity(intent);
                mCameraDialog.dismiss();
//                }else{
//                    Toast.makeText(getActivity().getApplicationContext(), "本日无备忘！", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width - 50; // 宽度
        root.measure(0, 0);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case Constant.HTTP_REQUEST_MEMORANDUM + ":index":
                if (obj != null) {
                    initData(obj);
                }
                break;
        }
    }

    public void initData(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        if (!TextUtils.isEmpty(json)) {
            memorandumList = (ArrayList<DailyCallBean>) Utils.jsonToList2(json, DailyCallBean.class);
            setMorandunDate(memorandumList);
        }
    }

    public void setMorandunDate(ArrayList<DailyCallBean> list) {
        memorandumDateList.clear();
        for (DailyCallBean dailyCallBean : list) {
            String dateTime = dailyCallBean.getAddtime();

            try {
                String date = dateTime.split(" ")[0];
                String[] dateStrs = date.split("-");
                DayAndPrice dayAndPrice = new DayAndPrice("", Integer.parseInt(dateStrs[0]), Integer.parseInt(dateStrs[1]), Integer.parseInt(dateStrs[2]));
                String today = Utils.getStringDateFromMillis(System.currentTimeMillis(), "yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(dateStrs[0]), (Integer.parseInt(dateStrs[1])) - 1, Integer.parseInt(dateStrs[2]));
                if (today.equals(Utils.getStringDateFromMillis(calendar.getTimeInMillis(), "yyyy-MM-dd"))) {
                    memorandumTitle.setText(dailyCallBean.getTitle());
                }
                memorandumDateList.add(dayAndPrice);
            } catch (Exception e) {
                CrashReport.postCatchedException(e);
            }
        }
    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {
        super.onHttpFaild(msgType, msg, obj);
        switch (msgType) {
            case "index":
                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onPageChange(int index) {

    }
}

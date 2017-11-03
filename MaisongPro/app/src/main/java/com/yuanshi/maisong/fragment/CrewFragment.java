package com.yuanshi.maisong.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import com.google.gson.Gson;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.activity.CreateCrewActivity;
import com.yuanshi.maisong.activity.CrewCotactListActivity;
import com.yuanshi.maisong.activity.CrewNotifycationActivity;
import com.yuanshi.maisong.activity.CrewSelectActivity;
import com.yuanshi.maisong.activity.DailyCallActivity;
import com.yuanshi.maisong.activity.EditNotifyActivity;
import com.yuanshi.maisong.activity.ProfileActivity;
import com.yuanshi.maisong.activity.ScriptUpdateActivity;
import com.yuanshi.maisong.activity.SearchMemoireActivity;
import com.yuanshi.maisong.activity.ShootingScheduleActivity;
import com.yuanshi.maisong.activity.ShowTextImageActivity;
import com.yuanshi.maisong.bean.DateBean;
import com.yuanshi.maisong.bean.WeatherBean;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.utils.Constact;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.WheelView;
import com.yuanshi.maisong.view.datepickview.CalendarView;
import com.yuanshi.maisong.view.datepickview.DayAndPrice;

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
public class CrewFragment extends Fragment {
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
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.weather_text)
    TextView weatherText;
    @BindView(R.id.temperature_text)
    TextView temperatureText;
    @BindView(R.id.sunriseTime)
    TextView sunriseTime;
    @BindView(R.id.sunsetTime)
    TextView sunsetTime;
    @BindView(R.id.weather_layout)
    LinearLayout weatherLayout;
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
    private View m_View;
    public static CrewFragment crewFragment;
    public ArrayList<String> crewList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (m_View == null) {
            m_View = inflater.inflate(R.layout.crew_layout, null);
        }
        ViewGroup parent = (ViewGroup) m_View.getParent();
        if (parent != null) {
            parent.removeView(m_View);
        }
        crewFragment = this;
        unbinder = ButterKnife.bind(this, m_View);
        initView();
        return m_View;
    }

    public static CrewFragment getInstance() {
        if (crewFragment == null) {
            crewFragment = new CrewFragment();
        }
        return crewFragment;
    }

    private void initView() {
        crewList.add("《唐伯虎点蚊香》");
        crewList.add("《鲁迅漂流记》");
        crewList.add("《剪刀手爱刘德华》");
        crewList.add("《这个手刹不太灵》");
        crewList.add("《钢铁侠是怎样炼成的》");
        getWeatherInfo();//获取天气信息；
    }

    private final int GET_WEATHERINFO_SUCCESS = 0x0010;//获取天气信息成功
    private final int GET_WERTHERINFO_FAILD = 0x0020;//获取天气信息失败
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_WEATHERINFO_SUCCESS:
                    WeatherBean weatherBean = (WeatherBean) msg.obj;
                    loadWeather(weatherBean);

                    break;
                case GET_WERTHERINFO_FAILD:

                    break;
            }
        }
    };

    /**
     * 加载天气信息
     * @param weatherBean
     */
    public void loadWeather(WeatherBean weatherBean) {
        temperatureText.setText(String.format(getString(R.string.tempature_format), weatherBean.getTemperatureMax(), weatherBean.getTemperatureMin()).toString());
        sunriseTime.setText(Utils.getDateTimeFromMillisecond(weatherBean.getSunriseTime()));
        sunsetTime.setText(Utils.getDateTimeFromMillisecond(weatherBean.getSunsetTime()));
        YLog.e(new Gson().toJson(weatherBean));
        weatherText.setText(weatherBean.getSummary());
        switch (weatherBean.getIcon()) {
            case "clear-day"://晴天
                weatherIcon.setImageResource(R.mipmap.weather_sunny);
                break;
            case "clear-night"://晴夜
                weatherIcon.setImageResource(R.mipmap.weather_sunny);
                break;
            case "rain"://雨
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "snow"://雪
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "sleet"://雨夹雪
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "wind"://风
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "fog"://雾
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "cloudy"://多云
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "partly-cloudy-day"://晴转多云日
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "partly-cloudy-night":
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "hail"://冰雹
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "thunderstorm":
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            case "tornado":
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
            default:
                weatherIcon.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }

    public void getWeatherInfo() {
        MyApplication.THREAD_EXCUTER.execute(new Runnable() {
            @Override
            public void run() {
                ForecastIO fio = new ForecastIO(Constact.DARK_SKY_KEY); //instantiate the class with the API key.
                fio.setUnits(ForecastIO.UNITS_SI);
                //sets the units as SI - optional
                fio.setLang("zh");
                fio.setExcludeURL("hourly,minutely");
                //excluded the minutely and hourly reports from the reply
                Location location = Utils.getLocation(getActivity());
                if (location != null) {
                    YLog.e(String.valueOf(location.getLatitude()) + "-----" + String.valueOf(location.getLongitude()));
                    fio.getForecast(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));   //sets the latitude and longitude - not optional
                    //it will fail to get forecast if it is not set
                    //this method should be called after the options were set
                    YLog.e(fio.getDaily().toString());
                    JsonObject jsonObject = fio.getDaily();
                    JsonArray jsonArray = (JsonArray) jsonObject.get("data");
                    if (jsonArray != null && jsonArray.size() > 0) {
                        WeatherBean weatherBean = new Gson().fromJson(jsonArray.get(0).toString(), WeatherBean.class);
                        Message msg = new Message();
                        msg.what = GET_WEATHERINFO_SUCCESS;
                        msg.obj = weatherBean;
                        handler.sendMessage(msg);
                    } else {
                        YLog.e("天气信息获取失败");
                        Message msg = new Message();
                        msg.what = GET_WERTHERINFO_FAILD;
                        handler.sendMessage(msg);
                    }
                } else {
                    YLog.e("GPS定位失败");
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
            R.id.crew_contacts, R.id.profile,R.id.join_crew_btn, R.id.create_crew_btn, R.id.crew_name,
            R.id.memorandum, R.id.weather_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.join_crew_btn://加入剧组,跳转到剧组搜索页面
                intent.setClass(getActivity(),CrewSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.create_crew_btn://创建剧组，跳转创建剧组页面
                intent.setClass(getActivity(),CreateCrewActivity.class);
                startActivity(intent);
                break;
            case R.id.crew_name://点击剧组名称，切换已添加剧组
                showSelectCrewDialog();
                break;
            case R.id.memorandum://点击备忘录，弹出日历选项
                showDatePickerLayout();
                break;
            case R.id.weather_layout://点击天气模块，重新获取天气信息
                getWeatherInfo();
                break;
            case R.id.call_sheet://点击每日通告单，查看每日通告
                intent.setClass(getActivity(),DailyCallActivity.class);
                startActivity(intent);
                break;
            case R.id.notifycation_of_crew://点击剧组通知，查看剧组通知
                intent.setClass(getActivity(),CrewNotifycationActivity.class);
                startActivity(intent);
                break;
            case R.id.script_update://点击剧本扉页
                intent.setClass(getActivity(),ScriptUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.shooting_schedule://点击拍摄大计划
                intent.setClass(getActivity(),ShootingScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.crew_contacts://点击剧组通讯录
                intent.setClass(getActivity(),CrewCotactListActivity.class);
                startActivity(intent);
                break;
            case R.id.profile://点击我在剧组
                intent.setClass(getActivity(),ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void showSelectCrewDialog() {
        final Dialog mCameraDialog = new Dialog(getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.bottom_dialog_layout, null);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        final WheelView wheelView = (WheelView) root.findViewById(R.id.wheelView);
        wheelView.setItems(crewList);
        wheelView.setSeletion(crewList.indexOf(crewName.getText().toString()));
        root.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCameraDialog != null && mCameraDialog.isShowing()){
                    mCameraDialog.dismiss();
                }
            }
        });
        root.findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//此处处理点击了已选剧组后续操作
                YLog.e("选中了列表的第"+wheelView.getSeletedIndex()+"项-->"+wheelView.getSeletedItem());
                if(mCameraDialog != null && mCameraDialog.isShowing()){
                    crewName.setText(wheelView.getSeletedItem());
                    mCameraDialog.dismiss();
                }
            }
        });
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        WindowManager wm = (WindowManager)getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width; // 宽度
        root.measure(0, 0);
        lp.height = height*2/5;

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    private void showDatePickerLayout() {
        Dialog mCameraDialog = new Dialog(getActivity(), R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.date_picker_layout, null);
        List<DayAndPrice> list = new ArrayList<DayAndPrice>();
        final DayAndPrice dAndPrice = new DayAndPrice("", 2017,10,20);
        DayAndPrice dAndPrice1 = new DayAndPrice("", 2017,10,25);
        DayAndPrice dAndPrice2 = new DayAndPrice("", 2017,2,18);
        DayAndPrice dAndPrice3 = new DayAndPrice("", 2017,2,25);
        DayAndPrice dAndPrice4 = new DayAndPrice("", 2017,3,5);
        DayAndPrice dAndPrice5 = new DayAndPrice("", 2017,3,11);
        DayAndPrice dAndPrice6 = new DayAndPrice("", 2017,3,15);
        DayAndPrice dAndPrice7 = new DayAndPrice("", 2017,4,25);
        DayAndPrice dAndPrice8 = new DayAndPrice("", 2017,4,1);
        DayAndPrice dAndPrice9 = new DayAndPrice("", 2017,4,13);
        DayAndPrice dAndPrice10 = new DayAndPrice("", 2017,5,16);
        DayAndPrice dAndPrice11 = new DayAndPrice("", 2017,5,2);
        DayAndPrice dAndPrice12 = new DayAndPrice("", 2017,5,4);
        DayAndPrice dAndPrice13 = new DayAndPrice("", 2017,5,25);
        list.add(dAndPrice);list.add(dAndPrice1);list.add(dAndPrice2);list.add(dAndPrice3);
        list.add(dAndPrice4);list.add(dAndPrice5);list.add(dAndPrice6);list.add(dAndPrice7);
        list.add(dAndPrice8);list.add(dAndPrice9);list.add(dAndPrice10);list.add(dAndPrice11);
        list.add(dAndPrice12);list.add(dAndPrice13);
        final CalendarView calendarView =  root.findViewById(R.id.calendarView);
        calendarView.setListDayAndPrice(list);
        Calendar calendar = Calendar.getInstance();
        YLog.e("设置日期");
        calendarView.setSelectYearMonthDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        calendarView.setDateViewClick(new CalendarView.DateViewClick() {
            @Override
            public void dateClick(DayAndPrice dayAndPrice) {//回调返回月份从0开始计数
                YLog.e("选中日期被点击");
                if(calendarView.hasThings(dayAndPrice)){
                    Intent intent = new Intent(getActivity(), ShowTextImageActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "本日无备忘！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        calendarView.setOnCreateMemoClick(new CalendarView.OnCreateMemoClick() {
            @Override
            public void createMemoClick(View view) {//回调返回月份从0开始计数
                DateBean dateBean = calendarView.getSelectDate();
                Intent intent = new Intent(getActivity(), EditNotifyActivity.class);
                intent.putExtra("editType",EditNotifyActivity.EDIT_TYPE_MEMOIRE);
                startActivity(intent);
            }
        });
        calendarView.setOnCheckMemoClick(new CalendarView.OnCheckMemoClick() {
            @Override
            public void checkMemoClick(View view) {//回调返回月份从0开始计数
                Toast.makeText(getActivity().getApplicationContext(),"进入搜索备忘录页面",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SearchMemoireActivity.class);
                startActivity(intent);
            }
        });

        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager)getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width-50; // 宽度
        root.measure(0, 0);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}

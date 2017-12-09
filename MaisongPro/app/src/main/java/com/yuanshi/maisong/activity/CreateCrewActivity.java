package com.yuanshi.maisong.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMGroup;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.datepickview.CalendarView;
import com.yuanshi.maisong.view.datepickview.DayAndPrice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/30.
 */

public class CreateCrewActivity extends BaseActivity {
    @BindView(R.id.ed_crewName)
    EditText edCrewName;
    @BindView(R.id.ed_productName)
    EditText edProductName;
    @BindView(R.id.ed_recipeName)
    EditText edRecipeName;
    @BindView(R.id.ed_directorName)
    EditText edDirectorName;
    @BindView(R.id.ed_startDate)
    TextView edStartDate;
    @BindView(R.id.ed_endDate)
    TextView edEndDate;
    @BindView(R.id.ed_crewPwd)
    EditText edCrewPwd;
    @BindView(R.id.create_btn)
    TextView createBtn;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;
    private SimpleDateFormat spt = new SimpleDateFormat("yyyy-MM-dd");
    private long startDateMil;
    private long endDateMil;
    @Override
    protected int getContentViewId() {
        return R.layout.create_crew_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public void createCrew() {
        if (checkInputData()) {
            StringBuilder groupName = new StringBuilder();
            groupName.append(getString(R.string.makinger)).append("《").append(edCrewName.getText().toString().trim()).append("》");
            LoginBeanDaoUtil util = new LoginBeanDaoUtil(this);
            String phone = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
            LoginInfoBean loginInfoBean = util.qeuryUserInfo(Long.parseLong(phone));
            EMGroup group = Utils.createChatRoom(loginInfoBean,groupName.toString(), edProductName.getText().toString().trim(), new String[]{}, edDirectorName.getText().toString().trim());
            YLog.e("roomOwner-->" + group.getOwner());
            YLog.e("roomName-->" + group.getGroupName());
            YLog.e("roomId-->" + group.getGroupId());
            iHttpPresenter.doAdd("0",
                    edCrewName.getText().toString().trim(),
                    edDirectorName.getText().toString().trim(),
                    edProductName.getText().toString().trim(),
                    edRecipeName.getText().toString().trim(),
                    edStartDate.getText().toString().trim(),
                    edEndDate.getText().toString().trim(),
                    edCrewPwd.getText().toString().trim(),
                    group.getGroupId());
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case "doAdd":
                Toast.makeText(getApplicationContext(), "创建剧组成功！", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public boolean checkInputData() {
        if(startDateMil >= endDateMil){
            Toast.makeText(getApplicationContext(), R.string.startdate_before_enddate, Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(edCrewName.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), R.string.crew_name_not_null, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edCrewPwd.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), R.string.crew_pwd_not_null, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edDirectorName.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), R.string.crew_director_not_null, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edStartDate.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), R.string.crew_start_time_not_null, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edEndDate.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), R.string.crew_end_time_not_null, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void showDatePickerLayout(final int checkType) {
        final Dialog mCameraDialog = new Dialog(this, R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.date_picker_layout, null);
        List<DayAndPrice> list = new ArrayList<DayAndPrice>();
        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        calendarView.setListDayAndPrice(list);
        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectYearMonthDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendarView.hideMemerLayout();
        calendarView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(DayAndPrice dayAndPrice) {
                switch (checkType){
                    case START_DATE:
                        setStartDate(dayAndPrice);
                        break;
                    case END_DATE:
                        setEndDate(dayAndPrice);
                        break;
                }
                mCameraDialog.dismiss();
            }
        });

        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width - 50; // 宽度
        root.measure(0, 0);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    private void setEndDate(DayAndPrice dayAndPrice){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(dayAndPrice.year,dayAndPrice.month-1,dayAndPrice.day,0,0,0);
        endDateMil = calendar.getTimeInMillis();
        edEndDate.setText(spt.format(new Date(endDateMil)));
    }

    private void setStartDate(DayAndPrice dayAndPrice){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(dayAndPrice.year,dayAndPrice.month-1,dayAndPrice.day,0,0,0);
        startDateMil = calendar.getTimeInMillis();
        edStartDate.setText(spt.format(new Date(startDateMil)));
    }
    @OnClick({R.id.ed_startDate, R.id.ed_endDate,R.id.create_btn,R.id.back_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.ed_startDate:
                showDatePickerLayout(START_DATE);
                break;
            case R.id.ed_endDate:
                showDatePickerLayout(END_DATE);
                break;
            case R.id.create_btn:
                createCrew();
                break;
        }
    }
}

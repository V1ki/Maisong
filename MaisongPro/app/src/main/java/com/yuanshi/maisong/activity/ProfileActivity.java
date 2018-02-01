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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.AuthBean;
import com.yuanshi.maisong.bean.HttpDepartmentBean;
import com.yuanshi.maisong.bean.MyProfileInfo;
import com.yuanshi.maisong.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/30.
 * 我在剧组界面
 */

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.ed_crewName)
    EditText edCrewName;
    @BindView(R.id.modify_crew_name)
    TextView modifyCrewName;
    @BindView(R.id.tel_no_check_icon)
    ImageView telNoCheckIcon;
    @BindView(R.id.tel_no_value)
    TextView telNoValue;
    @BindView(R.id.not_show_check_icon)
    ImageView notShowCheckIcon;
    @BindView(R.id.not_show_tel)
    TextView notShowTel;
    @BindView(R.id.my_department)
    TextView myDepartment;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.myName)
    EditText myName;
    @BindView(R.id.ed_position)
    EditText edPosition;
    @BindView(R.id.quick_crew)
    TextView quickCrew;
    @BindView(R.id.save_intent)
    TextView saveIntent;
    private String crewId;//剧组id
    private String crewName;//剧组名称
    private boolean canModifyCrewName = false;//是否有更改剧组名的权限
    private int telShowType = 0;//组内号码显示状态 0，显示；1，不显示
    private static final int SHOW_TEL_NO = 1;
    private static final int NO_SHOW_TEL_NO = 0;
    private MyProfileInfo myProfileInfo;
    private HttpDepartmentBean currentDepartment;
    private AuthBean currentPostion;
    private List<HttpDepartmentBean> departmentList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.profile_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("groupId");
        crewName = getIntent().getStringExtra("crewName");
        if (TextUtils.isEmpty(crewId)) {
            Toast.makeText(getApplicationContext(), R.string.group_id_is_null, Toast.LENGTH_SHORT).show();
            finish();
        }
        edCrewName.setText(crewName);
        iHttpPresenter.thecrewinfo(crewId);

    }


    /**
     * 检查是否有修改剧组名权限
     */
    public void checkeModifyPremission() {
        if (canModifyCrewName) {
            edCrewName.setEnabled(true);
            modifyCrewName.setText(R.string.modify);
            modifyCrewName.setTextColor(getResources().getColor(R.color.btn_bg));
            modifyCrewName.setClickable(true);
        } else {
            edCrewName.setEnabled(false);
            modifyCrewName.setText(R.string.only_manager_change);
            modifyCrewName.setTextColor(getResources().getColor(R.color.hint_text));
            modifyCrewName.setClickable(false);
        }
    }

    public void refreshUI() {
        try {
            if(myProfileInfo != null){
                telNoValue.setText(myProfileInfo.getPhone());
                myDepartment.setText(currentDepartment.getTitle());
                myName.setText(myProfileInfo.getUsername());
                telShowType = Integer.parseInt(myProfileInfo.getPhoneshow());
                edPosition.setText(currentPostion.getTitle());
            }
        }catch (NullPointerException e){
            CrashReport.postCatchedException(e);
        }

        checkTelShowType();

    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case "thecrewinfo":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                myProfileInfo = gson.fromJson(json, MyProfileInfo.class);
                iHttpPresenter.department(crewId);
                break;
            case "editusercrew":
                Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case "department":
                String departmentJson = (String) obj;
                departmentList = Utils.jsonToList2(departmentJson, HttpDepartmentBean.class);
                currentDepartment = findDepartMentById(departmentList);
                currentPostion = findPositionById();
                refreshUI();
                break;
            case "outs":
                Toast.makeText(getApplicationContext(), R.string.out_crew_success, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }


    /**
     * 弹出清除缓存确认框
     */
    public void showOutCrewDialog(){
        final Dialog mCameraDialog = new Dialog(this, R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.logout_dialog_layout, null);
        TextView contentTv = root.findViewById(R.id.dialog_content);
        contentTv.setText("确定要退出当前剧组？");
        root.findViewById(R.id.commit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iHttpPresenter.outs(crewId);
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager)this
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width-80; // 宽度
        root.measure(0, 0);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.alpha = 2f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    public HttpDepartmentBean findDepartMentById(List<HttpDepartmentBean> list){
        for(HttpDepartmentBean httpDepartmentBean : list){
            if(httpDepartmentBean.getId().equals(myProfileInfo.getDepartment())){
                return httpDepartmentBean;
            }
        }
        return null;
    }

    public AuthBean findPositionById(){
        for(AuthBean authBean : currentDepartment.getAuth()){
            if(authBean.getId().equals(myProfileInfo.getPosition())){
                return authBean;
            }
        }
        return null;
    }
    /**
     * 检查剧组内电话展示状态
     */
    public void checkTelShowType() {
        switch (telShowType) {
            case SHOW_TEL_NO:
                telNoCheckIcon.setImageResource(R.mipmap.checked_icon);
                telNoValue.setTextColor(getResources().getColor(R.color.btn_bg));
                notShowCheckIcon.setImageResource(R.mipmap.unchecked_icon);
                notShowTel.setTextColor(getResources().getColor(R.color.main_text));
                break;
            case NO_SHOW_TEL_NO:
                telNoCheckIcon.setImageResource(R.mipmap.unchecked_icon);
                telNoValue.setTextColor(getResources().getColor(R.color.main_text));
                notShowCheckIcon.setImageResource(R.mipmap.checked_icon);
                notShowTel.setTextColor(getResources().getColor(R.color.btn_bg));
                break;
        }
        if(myProfileInfo != null){
            myProfileInfo.setPhoneshow(String.valueOf(telShowType));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.modify_crew_name, R.id.tel_no_check_icon, R.id.not_show_check_icon, R.id.quick_crew,R.id.save_intent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.modify_crew_name:
                //修改剧组名称请求
                break;
            case R.id.tel_no_check_icon:
                telShowType = SHOW_TEL_NO;
                checkTelShowType();
                break;
            case R.id.not_show_check_icon:
                telShowType = NO_SHOW_TEL_NO;
                checkTelShowType();
                break;
            case R.id.quick_crew:
                //退出剧组请求
                showOutCrewDialog();
                break;
            case R.id.save_intent:
                //保存修改
                myProfileInfo.setUsername(myName.getText().toString().trim());
                myProfileInfo.setDepartment(myDepartment.getText().toString().trim());
                Map<String, Object> map = myProfileInfo.getParamsMap();
                map.put("id",crewId);
                map.put("department",currentDepartment.getId());
                map.put("position",currentPostion.getId());
                iHttpPresenter.editusercrew(map);
                break;
        }
    }
}

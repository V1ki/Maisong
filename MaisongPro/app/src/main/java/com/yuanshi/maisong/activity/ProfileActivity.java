package com.yuanshi.maisong.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.MyProfileInfo;

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
    private String groupId;//剧组id
    private String crewName;//剧组名称
    private boolean canModifyCrewName = false;//是否有更改剧组名的权限
    private int telShowType = 0;//组内号码显示状态 0，显示；1，不显示
    private static final int SHOW_TEL_NO = 1;
    private static final int NO_SHOW_TEL_NO = 0;
    private MyProfileInfo myProfileInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.profile_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        groupId = getIntent().getStringExtra("groupId");
        crewName = getIntent().getStringExtra("crewName");
        if (TextUtils.isEmpty(groupId)) {
            Toast.makeText(getApplicationContext(), R.string.group_id_is_null, Toast.LENGTH_SHORT).show();
            finish();
        }
        edCrewName.setText(crewName);
        iHttpPresenter.thecrewinfo(groupId);

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
                myDepartment.setText(myProfileInfo.getDepartment());
                myName.setText(myProfileInfo.getUsername());
                telShowType = Integer.parseInt(myProfileInfo.getPhoneshow());
                edPosition.setText(myProfileInfo.getDepartment());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
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
                refreshUI();
                break;
            case "editusercrew":
                Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
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
                break;
            case R.id.save_intent:
                //保存修改
                myProfileInfo.setUsername(myName.getText().toString().trim());
                myProfileInfo.setDepartment(myDepartment.getText().toString().trim());
                Map<String, Object> map = myProfileInfo.getParamsMap();
                map.put("id",groupId);
                iHttpPresenter.editusercrew(map);
                break;
        }
    }
}

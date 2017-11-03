package com.yuanshi.maisong.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;

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
    TextView myName;
    @BindView(R.id.ed_position)
    EditText edPosition;
    @BindView(R.id.quick_crew)
    TextView quickCrew;

    private boolean canModifyCrewName = false;//是否有更改剧组名的权限
    private int telShowType = 0;//组内号码显示状态 0，显示；1，不显示
    private static final int SHOW_TEL_NO = 0;
    private static final int NO_SHOW_TEL_NO = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.profile_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        checkeModifyPremission();
        checkTelShowType();
        showMyProfileInfo();
    }

    /**
     * 显示我在剧组的个人信息（电话号码、部门等）
     */
    public void showMyProfileInfo(){

    }

    /**
     * 检查是否有修改剧组名权限
     */
    public void checkeModifyPremission(){
        if(canModifyCrewName){
            edCrewName.setEnabled(true);
            modifyCrewName.setText(R.string.modify);
            modifyCrewName.setTextColor(getResources().getColor(R.color.btn_bg));
            modifyCrewName.setClickable(true);
        }else{
            edCrewName.setEnabled(false);
            modifyCrewName.setText(R.string.only_manager_change);
            modifyCrewName.setTextColor(getResources().getColor(R.color.hint_text));
            modifyCrewName.setClickable(false);
        }
    }

    /**
     * 检查剧组内电话展示状态
     */
    public void checkTelShowType(){
        switch (telShowType){
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.modify_crew_name, R.id.tel_no_check_icon, R.id.not_show_check_icon, R.id.quick_crew})
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
        }
    }
}

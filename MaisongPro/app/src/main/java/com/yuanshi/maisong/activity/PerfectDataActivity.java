package com.yuanshi.maisong.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.maisong.R;
import com.yuanshi.iotpro.publiclib.bean.UserInfoBean;
import com.yuanshi.maisong.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/10/30.
 * 完善个人资料Activity
 */

public class PerfectDataActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.ed_nikeName)
    EditText edNikeName;
    @BindView(R.id.boy_radio)
    ImageView boyRadio;
    @BindView(R.id.girl_radio)
    ImageView girlRadio;
    @BindView(R.id.submint_btn)
    TextView submintBtn;

    private int sexCheck = 0;//性别选项 0：男； 1,：女
    @Override
    protected int getContentViewId() {
        return R.layout.perfect_data_layout;
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

    @OnClick({R.id.back_icon, R.id.boy_radio, R.id.girl_radio, R.id.submint_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.boy_radio:
                sexCheck = 1;
                boyRadio.setImageResource(R.mipmap.boy_checked);
                girlRadio.setImageResource(R.mipmap.girl_unchecked);
                break;
            case R.id.girl_radio:
                sexCheck = 0;
                boyRadio.setImageResource(R.mipmap.boy_unchecked);
                girlRadio.setImageResource(R.mipmap.girl_checked);
                break;
            case R.id.submint_btn:
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setName(edNikeName.getText().toString().trim());
                userInfoBean.setSex(sexCheck);
//                userInfoBean.setPhone(getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,""));
                iHttpPresenter.edituser(Utils.getEditInfoMap(userInfoBean));
                break;
        }
    }

    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {
        super.onHttpFaild(msgType,msg,obj);
        switch (msgType){
            case "edituser":
                if(!TextUtils.isEmpty(msg)){
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onError(String msgType, String msg, Object obj) {
        switch (msgType){
            case "edituser":
                if(!TextUtils.isEmpty(msg)){
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "edituser":
                if(!TextUtils.isEmpty(msg)){
                    saveUserInfo();
                    Intent intent= new Intent(PerfectDataActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
    public void saveUserInfo(){
        SharedPreferences sp = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE);
        sp.edit().putBoolean(Constant.HAS_PUT_USER_INFO_KEY,true).commit();
    }

}

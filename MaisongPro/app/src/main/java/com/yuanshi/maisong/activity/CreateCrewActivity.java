package com.yuanshi.maisong.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.utils.Utils;

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
    EditText edStartDate;
    @BindView(R.id.ed_endDate)
    EditText edEndDate;
    @BindView(R.id.ed_crewPwd)
    EditText edCrewPwd;
    @BindView(R.id.create_btn)
    TextView createBtn;


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

    @OnClick(R.id.create_btn)
    public void onViewClicked() {
        createCrew();

    }
    public void createCrew(){
        if(checkInputData()){
            StringBuilder groupName = new StringBuilder();
            groupName.append(getString(R.string.makinger)).append("《").append(edCrewName.getText().toString().trim()).append("》");
            EMGroup group = Utils.createChatRoom(groupName.toString(),edProductName.getText().toString().trim(),new String[]{},edDirectorName.getText().toString().trim());
            YLog.e("roomOwner-->"+group.getOwner());
            YLog.e("roomName-->"+group.getGroupName());
            YLog.e("roomId-->"+group.getGroupId());
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
        switch (msgType){
            case "doAdd":
                Toast.makeText(getApplicationContext(),"创建剧组成功！",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public boolean checkInputData(){
        if(TextUtils.isEmpty(edCrewName.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),R.string.crew_name_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(edCrewPwd.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),R.string.crew_pwd_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(edDirectorName.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),R.string.crew_director_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(edStartDate.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),R.string.crew_start_time_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(edEndDate.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),R.string.crew_end_time_not_null,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

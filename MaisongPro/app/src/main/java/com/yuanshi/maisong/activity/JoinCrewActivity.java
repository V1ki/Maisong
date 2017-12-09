package com.yuanshi.maisong.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.application.MyApplication;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.AuthBean;
import com.yuanshi.maisong.bean.HttpDepartmentBean;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/11/28.
 */

public class JoinCrewActivity extends BaseActivity {
    @BindView(R.id.ed_crew_pwd)
    EditText edCrewPwd;
    @BindView(R.id.ed_department)
    TextView edDepartment;
    @BindView(R.id.ed_position)
    TextView edPosition;
    private ArrayList<HttpDepartmentBean> departmentList = new ArrayList<>();
    private ArrayList<AuthBean> positionList = new ArrayList<>();
    private HttpDepartmentBean selectDepartment;
    private static final int SHOW_DEPARTMENT = 0;//展示部门
    private static final int SHOW_POSITION = 1;//展示职位
    private AuthBean selectAuthBean;

    private String crewId;
    private String crewName;

    @Override
    protected int getContentViewId() {
        return R.layout.join_crew_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        crewId = getIntent().getStringExtra("crewId");
        crewName = getIntent().getStringExtra("crewName");
        iHttpPresenter.department(crewId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case "department":
                String json = (String) obj;
                List<HttpDepartmentBean> list = Utils.jsonToList2(json, HttpDepartmentBean.class);
                departmentList.addAll(list);
                selectDepartment = departmentList.get(0);
                edDepartment.setText(selectDepartment.getTitle());
                selectAuthBean = selectDepartment.getAuth().get(0);
                edPosition.setText(selectAuthBean.getTitle());
                break;
            case "joins":
                YLog.e("msg");
                MyApplication.THREAD_EXCUTER.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(TextUtils.isEmpty(selectDepartment.getGroupid()) || selectDepartment.getGroupid().equals("0.0")){
                                createChatGroup();
                            }else{
                                EMClient.getInstance().groupManager().joinGroup(selectDepartment.getGroupid());//需异步处理
                                LoginBeanDaoUtil util = new LoginBeanDaoUtil(JoinCrewActivity.this);
                                String myphone = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
                                LoginInfoBean loginInfoBean = util.qeuryUserInfo(Long.parseLong(myphone));
                                Utils.sendHelloMessage(loginInfoBean,selectDepartment.getGroupid(),"大家好，我是"+loginInfoBean.getNickname()+",很高兴认识各位");
                            }
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Toast.makeText(getApplicationContext(),"剧组添加成功！",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public void createChatGroup(){
        StringBuilder groupName = new StringBuilder();
        groupName.append(selectDepartment.getTitle()).append("《").append(crewName).append("》");
        LoginBeanDaoUtil util = new LoginBeanDaoUtil(this);
        String phone = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
        LoginInfoBean loginInfoBean = util.qeuryUserInfo(Long.parseLong(phone));
        EMGroup group = Utils.createChatRoom(loginInfoBean,groupName.toString(), crewName+":"+selectDepartment.getTitle(), new String[]{}, "");
    }

    @OnClick({R.id.apply_btn, R.id.ed_crew_pwd, R.id.ed_department, R.id.ed_position,R.id.back_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.apply_btn:
                joinCrew();
                break;
            case R.id.ed_crew_pwd:
                break;
            case R.id.ed_department:
                showSelectCrewDialog(SHOW_DEPARTMENT);
                break;
            case R.id.ed_position:
                showSelectCrewDialog(SHOW_POSITION);
                break;
        }
    }

    /**
     * 加入剧组，加入剧组主群
     */
    public void joinCrew(){
        if(checkSelectData()){
            Map<String, Object> map = new HashMap<>();
            map.put("id",crewId);
            map.put("pw",edCrewPwd.getText().toString().trim());
            map.put("department",selectDepartment.getId());
            map.put("position",selectAuthBean.getId());
            map.put("groupid",selectDepartment.getGroupid());
            iHttpPresenter.joins(map);
        }
    }

    public boolean checkSelectData(){
        if(selectDepartment == null){
            Toast.makeText(getApplicationContext(), "您没有选择任何部门", Toast.LENGTH_SHORT).show();
            return false;
        }else if(selectAuthBean == null){
            Toast.makeText(getApplicationContext(), "您没有选择任何职位", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(selectDepartment.getId())){
            Toast.makeText(getApplicationContext(), "没有找到该部门ID", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(selectAuthBean.getId())){
            Toast.makeText(getApplicationContext(), "没有找到该职位ID", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(selectDepartment.getGroupid())){
            Toast.makeText(getApplicationContext(), "没有找到剧组的群组ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showSelectCrewDialog(final int showType) {
        final Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog_layout, null);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        final WheelView wheelView = (WheelView) root.findViewById(R.id.wheelView);
        ArrayList nameList = new ArrayList();
        switch (showType) {
            case SHOW_DEPARTMENT:
                for (HttpDepartmentBean department : departmentList) {
                    nameList.add(department.getTitle());
                }
                break;
            case SHOW_POSITION:
                for (AuthBean authBean : selectDepartment.getAuth()) {
                    nameList.add(authBean.getTitle());
                }
                break;
        }

        wheelView.setItems(nameList);
        wheelView.setSeletion(0);
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
                YLog.e("选中了列表的第" + wheelView.getSeletedIndex() + "项-->" + wheelView.getSeletedItem());
                if (mCameraDialog != null && mCameraDialog.isShowing()) {
                    switch (showType) {
                        case SHOW_DEPARTMENT:
                            selectDepartment = findDepartMentByTitle( wheelView.getSeletedItem());
                            YLog.e("部门groupId"+selectDepartment.getGroupid());
                            edDepartment.setText(wheelView.getSeletedItem());
                            edPosition.setText(selectDepartment.getAuth().get(0).getTitle());
                            mCameraDialog.dismiss();
                            break;
                        case SHOW_POSITION:
                            edPosition.setText(wheelView.getSeletedItem());
                            selectAuthBean = findAuthByTitle(wheelView.getSeletedItem());
                            mCameraDialog.dismiss();
                            break;
                    }
                }
            }
        });
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        lp.width = width; // 宽度
        root.measure(0, 0);
        lp.height = height * 2 / 5;

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    public HttpDepartmentBean findDepartMentByTitle(String title){
        for(HttpDepartmentBean httpDepartMent: departmentList){
            if(httpDepartMent.getTitle().equals(title)){
                return httpDepartMent;
            }
        }
        return null;
    }

    public AuthBean findAuthByTitle(String title){
        for(AuthBean authBean: selectDepartment.getAuth()){
            if(authBean.getTitle().equals(title)){
                return authBean;
            }
        }
        return null;
    }
}

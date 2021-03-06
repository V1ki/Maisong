package com.yuanshi.maisong.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yuanshi.iotpro.daoutils.LoginBeanDaoUtil;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.bean.LoginInfoBean;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.HttpImgUrlBean;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.CircleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Dengbocheng on 2017/11/3.
 */
public class AccountSettingActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.save_btn)
    TextView saveBtn;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.headIcon)
    CircleImageView headIcon;
    @BindView(R.id.ed_nikeName)
    EditText edNikeName;
    @BindView(R.id.select_sex_tv)
    TextView selectSexTv;
    @BindView(R.id.select_sex_icon)
    ImageView selectSexIcon;
    @BindView(R.id.ed_wechat)
    EditText edWechat;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_tel_no)
    EditText edTelNo;
    private ArrayList<AlbumFile> myAlbumFiles  = new ArrayList<>();
//    public String headIconPath;//待提交的头像路径
    private String path ;
    private Bitmap head;
    private LoginInfoBean loginInfoBean;
    private String userPhone;
    private LoginBeanDaoUtil loginBeanDaoUtil;

    @Override
    protected int getContentViewId() {
        return R.layout.account_setting_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loginBeanDaoUtil = new LoginBeanDaoUtil(this);
        path = Utils.getHeadIconPath();
        userPhone = getSharedPreferences(Constant.MAIN_SH_NAME,MODE_PRIVATE).getString(Constant.USER_PHONE_KEY,"");
        loginInfoBean = getLoginInfoBean(userPhone);
        if(loginInfoBean != null){
            edNikeName.setText(loginInfoBean.getNickname());
            edEmail.setText(loginInfoBean.getEmail());
            edTelNo.setText(loginInfoBean.getPhone());
            edWechat.setText(loginInfoBean.getWeixin());
            if(loginInfoBean.getSex().equals("0")){
                selectSexTv.setText(R.string.sex_girl);
            }else{
                selectSexTv.setText(R.string.sex_boy);
            }
            Glide.with(this).load(loginInfoBean.getAvatar()).error(R.mipmap.ease_default_avatar).into(headIcon);
        }
    }

    public LoginInfoBean getLoginInfoBean(String userPhone){
       return loginBeanDaoUtil.qeuryUserInfo(Long.parseLong(userPhone));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_icon, R.id.save_btn, R.id.headIcon, R.id.select_sex_tv, R.id.select_sex_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.save_btn:
                //保存个人信息
                uploadHeadIcon();
//                iHttpPresenter.edituser();
                break;
            case R.id.headIcon:
                selectHeadIcon();
                break;
            case R.id.select_sex_tv:
            case R.id.select_sex_icon:
                showSexDialog();
                break;
        }
    }

    private static final int REQUEST_CODE_FOR_PIC = 0x0010;
    private static final int REQUEST_CODE_CROP_PIC = 0x0010;
    private static final int TO_CROP_PIC_ACTIVITY = 0x0020;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TO_CROP_PIC_ACTIVITY:
                    String filePath = (String) msg.obj;
                    File temp = new File(filePath);
                    cropPhoto(Uri.fromFile(temp));
                    break;
            }
        }
    };

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CODE_CROP_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CROP_PIC){//图片裁剪完成，返回裁剪后的图片地址
            Bundle extras = data.getExtras();
            head = extras.getParcelable("data");
            if(head != null){
                setPicToView(head);
                Drawable drawable =new BitmapDrawable(head);
                headIcon.setImageDrawable(drawable);
            }
        }
    }

    public void editUserInfo(){
        loginInfoBean.setNickname(edNikeName.getText().toString().trim());
        loginInfoBean.setEmail(edEmail.getText().toString().trim());
        loginInfoBean.setWeixin(edWechat.getText().toString().trim());

        Map<String, Object> map = new HashMap<>();
        map.put("name",loginInfoBean.getNickname());
        map.put("sex",loginInfoBean.getSex());
        map.put("weixin",loginInfoBean.getWeixin());
        map.put("email",loginInfoBean.getEmail());
        map.put("avatar",loginInfoBean.getAvatar());
        iHttpPresenter.edituser(map);
    }
    public void uploadHeadIcon(){
        if (head != null) {
//            setPicToView(head);
            File file = new File(headIconPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            iHttpPresenter.appupload(body,file.getPath());
        }else{
            editUserInfo();
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "edituser":
                saveLoginInfo();
                finish();
                break;
            case "appupload":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                HttpImgUrlBean httpImgUrlBean = gson.fromJson(json, HttpImgUrlBean.class);
                if(httpImgUrlBean != null){
                    String httpIconUrl = httpImgUrlBean.getImgUrl();
                    loginInfoBean.setAvatar(httpIconUrl);
                }
                editUserInfo();
                break;
        }
    }

    /**
     * 保存修改后的用户信息
     */
    public void saveLoginInfo(){
        LoginInfoBean localBean = loginBeanDaoUtil.qeuryUserInfo(Long.parseLong(loginInfoBean.getPhone()));
        loginInfoBean.set_id(localBean.get_id());
        loginBeanDaoUtil.updateUserInfo(loginInfoBean);
    }

    public String headIconPath;
    /**
     * 保存图片到sd卡
     * @param mBitmap
     */
    private void setPicToView(Bitmap mBitmap) {
        headIconPath = path+"/"+System.currentTimeMillis()+"head.jpg";
        File file = new File(headIconPath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                CrashReport.postCatchedException(e);
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            headIconPath = file.getPath();
//            resetHeadIcon(headIconPath);
        } catch (FileNotFoundException e) {
            CrashReport.postCatchedException(e);
        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            CrashReport.postCatchedException(e);
        }
    }

    /**
     * 选择头像
     */
    public void selectHeadIcon(){
        Album.image(this) // Image selection.
                .multipleChoice()
                .requestCode(REQUEST_CODE_FOR_PIC)
                .camera(true)
                .columnCount(3)
                .selectCount(1)
                .checkedList(myAlbumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        myAlbumFiles = result;
                        Message msg = new Message();
                        msg.what = TO_CROP_PIC_ACTIVITY;
                        msg.obj = myAlbumFiles.get(0).getPath();
                        handler.sendMessage(msg);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        Toast.makeText(getApplicationContext(), R.string.user_cancel, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    /**
     * 弹出选择性别popupWindow
     */
    public void showSexDialog(){
            final Dialog mCameraDialog = new Dialog(this, R.style.datePickerStyle);
            LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                    R.layout.sex_select_layout, null);
            root.findViewById(R.id.select_boy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(loginInfoBean!= null){
                        loginInfoBean.setSex("1");
                    }
                    selectSexTv.setText(R.string.sex_boy);
                    mCameraDialog.dismiss();
                }
            });
        root.findViewById(R.id.select_girl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginInfoBean!= null){
                    loginInfoBean.setSex("0");
                }
                selectSexTv.setText(R.string.sex_girl);
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
            lp.width = width/2; // 宽度
            root.measure(0, 0);
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.alpha = 2f; // 透明度
            dialogWindow.setAttributes(lp);
            mCameraDialog.show();
    }
}

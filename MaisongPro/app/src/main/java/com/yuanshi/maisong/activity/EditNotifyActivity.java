package com.yuanshi.maisong.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.HttpImgUrlBean;
import com.yuanshi.maisong.utils.FileUploadObserver;
import com.yuanshi.maisong.utils.UploadFileRequestBody;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.ProgressImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Dengbocheng on 2017/10/31.
 * 编辑剧组通知和剧组扉页界面
 */

public class EditNotifyActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.release_btn)
    TextView releaseBtn;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.ed_title)
    EditText edTitle;
    @BindView(R.id.input_count)
    TextView inputCount;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.add_img_btn)
    ImageView addImgBtn;
    @BindView(R.id.my_gallary)
    LinearLayout myGallary;
    @BindView(R.id.hor_scrollView)
    HorizontalScrollView horScrollView;
    @BindView(R.id.title_text)
    TextView titleText;
    private String crewId;
    private String editType ;//编辑内容类型；
    public ConcurrentHashMap<String, String> picPath = new ConcurrentHashMap();//图片是否已上传


    private ArrayList<AlbumFile> myAlbumFiles = new ArrayList<>();//所有的图片列表
    private ArrayList<AlbumFile> newAlbumFiles = new ArrayList<>();//新增的图片列表
    private LayoutInflater inflater;

    @Override
    protected int getContentViewId() {
        return R.layout.edit_notify_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        inflater = LayoutInflater.from(this);
        editType = getIntent().getStringExtra("editType");
        crewId = getIntent().getStringExtra("crewId");
        switch (editType) {
            case Constant.HTTP_REQUEST_REMIND:
                titleText.setText(R.string.notifycation_of_crew);
                break;
            case Constant.HTTP_REQUEST_SCRIPTPAGE:
                titleText.setText(R.string.script_update);
                break;
            case Constant.HTTP_REQUEST_MEMORANDUM:
                titleText.setText(R.string.memoire);
                break;
        }

        edTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputCount.setText(""+charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private static final int REFRESH_GALLARY = 0x0010;
    private static final int SCROLL_RIGHT = 0x0020;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_GALLARY:
                    for (AlbumFile albumFile : newAlbumFiles) {
                        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.imageview_item, null);
                        relativeLayout.setTag(albumFile.getPath());
                        LinearLayout.LayoutParams paParams = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        paParams.setMargins(5,5,5,5);
                        ProgressImageView imageView = relativeLayout.findViewById(R.id.image_res);
                        ImageView delete_icon = relativeLayout.findViewById(R.id.delete_icon);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(220, 220);
//                        params.setMargins(5, 5, 5, 5);
                        imageView.setLayoutParams(params);
                        myGallary.addView(relativeLayout);
                        delete_icon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myGallary.removeView((View) view.getParent());
                                removeAlbumInList((String) ((View) view.getParent()).getTag());
                            }
                        });
                        Glide.with(EditNotifyActivity.this).
                                load(albumFile.getPath()).
                                placeholder(R.mipmap.ic_launcher).
                                error(R.mipmap.delete_icon).
                                centerCrop().
                                into(imageView);
                        YLog.e("图片大小--》"+albumFile.getSize());
                        if(albumFile.getSize() > 5*1024*1024){//图片大于5M,压缩处理
                            Bitmap bitmap = Utils.compressPixel(albumFile.getPath());
                            String path  = Utils.savePics(bitmap,albumFile.getName());
                            uploadPic(path,imageView);
                        }else{
                            uploadPic(albumFile.getPath(),imageView);
                        }
                    }
                    scrollRight();
                    break;
                case SCROLL_RIGHT:
                    horScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    break;
            }
        }
    };


    public void removeAlbumInList(String filePath) {
        for (int i = 0; i < myAlbumFiles.size(); i++) {
            if (myAlbumFiles.get(i).getPath().equals(filePath)) {
                myAlbumFiles.remove(i);
            }
        }
        if(picPath.containsKey(filePath)){
            picPath.remove(filePath);
        }
    }

    public void scrollRight() {
        handler.sendEmptyMessageDelayed(SCROLL_RIGHT, 300);
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType){
            case "appupload":
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                HttpImgUrlBean httpImgUrlBean = gson.fromJson(json, HttpImgUrlBean.class);
                String filePath = msg;
                if(httpImgUrlBean != null){
                    String httpIconUrl = httpImgUrlBean.getImgUrl();
                    picPath.put(msg,httpIconUrl );
                    YLog.e("图片上传成功"+msg);
                }
                break;
            case "notice:doAdd":
                Toast.makeText(getApplicationContext(), R.string.apply_notice_success,Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @OnClick({R.id.back_icon, R.id.release_btn, R.id.add_img_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.release_btn:

                //发布编辑内容
                uploadEditContent();
                break;
            case R.id.add_img_btn:
                selectPic();
                break;
        }
    }

    /**
     * 发布编辑内容
     */
    public void uploadEditContent(){
        if(checkData()){
            Map<String,Object> map = new HashMap<>();
            map.put("title",edTitle.getText().toString().trim());
            map.put("content",edContent.getText().toString().trim());
            map.put("cid",crewId);
            map.put("pics",getPicString());
            iHttpPresenter.doAdd(editType,"",map);
        }
    }



    //检验发布内容是否合法
    public boolean checkData(){

        return true;
    }

    public String getPicString(){
        StringBuilder sb = new StringBuilder();
        for(String path: picPath.values()){
            sb.append(path).append(",");
        }
        return sb.toString();
    }
    private static final int REQUEST_CODE_FOR_PIC = 0x0010;

    public void selectPic() {
        Album.image(this) // Image selection.
                .multipleChoice()
                .requestCode(REQUEST_CODE_FOR_PIC)
                .camera(true)
                .columnCount(3)
                .selectCount(Integer.MAX_VALUE)
                .checkedList(myAlbumFiles)
//                .filterSize() // Filter the file size.
//                .filterMimeType() // Filter file format.
//                .afterFilterVisibility() // Show the filtered files, but they are not available.
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
//                        myAlbumFiles = result;
                        newAlbumFiles.clear();
                        for(AlbumFile albumFile: result){
                            if(!myAlbumFiles.contains(albumFile)){
                                newAlbumFiles.add(albumFile);
                            }
                        }
//                        myGallary.removeAllViews();
                        myAlbumFiles.addAll(newAlbumFiles);
                        handler.sendEmptyMessage(REFRESH_GALLARY);
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

    public void uploadPic(final String filePath,final ProgressImageView imageView){
        if(!picPath.containsKey(filePath)){
            File file = new File(filePath);
            UploadFileRequestBody requestFile = new UploadFileRequestBody(file, new FileUploadObserver<ResponseBody>() {
                @Override
                public void onUpLoadSuccess(ResponseBody responseBody) {

                }

                @Override
                public void onUpLoadFail(Throwable e) {

                }
                @Override
                public void onProgress(int progress) {
                    YLog.e(filePath+"进度--》"+progress);
                    imageView.setProgress(progress);
                }
            });
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            YLog.e("开始上传图片--》"+filePath);
            imageView.setProgress(0);
            picPath.put(filePath,"");
            iHttpPresenter.appupload(body,filePath);
        }
    }
}

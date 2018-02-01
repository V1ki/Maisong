package com.yuanshi.maisong.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yuanshi.maisong.utils.SoftHideKeyBoardUtil;
import com.yuanshi.maisong.utils.UploadFileRequestBody;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.utils.recycleviewutils.EditUtilsRecycleAdapter;
import com.yuanshi.maisong.view.RoundProgressBar;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.MultipartBody;
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
    @BindView(R.id.add_img_btn)
    ImageView addImgBtn;
    @BindView(R.id.my_gallary)
    LinearLayout myGallary;
    @BindView(R.id.hor_scrollView)
    HorizontalScrollView horScrollView;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.editor)
    RichEditor editor;
    @BindView(R.id.edit_scroll)
    RecyclerView editScroll;
    private String crewId;
    private String editType;//编辑内容类型；
    public ConcurrentHashMap<String, String> picPath = new ConcurrentHashMap();//图片是否已上传
    private String addtime;//添加备忘的时间


    private ArrayList<AlbumFile> myAlbumFiles = new ArrayList<>();//所有的图片列表
    private ArrayList<AlbumFile> newAlbumFiles = new ArrayList<>();//新增的图片列表
    private LayoutInflater inflater;
    private EditUtilsRecycleAdapter adapter;
    private int[] scrollUtilImgs = new int[]{R.mipmap.undo, R.mipmap.redo,R.mipmap.bold,R.mipmap.italic, R.mipmap.subscript,R.mipmap.superscript,
            R.mipmap.strikethrough, R.mipmap.underline,R.mipmap.h1,R.mipmap.h2,R.mipmap.h3,R.mipmap.h4,R.mipmap.h5,
            R.mipmap.h6,R.mipmap.txt_color,R.mipmap.bg_color, R.mipmap.indent,R.mipmap.outdent,R.mipmap.justify_left,
            R.mipmap.justify_center,R.mipmap.justify_right,R.mipmap.bullets,R.mipmap.numbers,R.mipmap.insert_image};

    @Override
    protected int getContentViewId() {
        return R.layout.edit_notify_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecycleView();
        SoftHideKeyBoardUtil.assistActivity(this);
        editor.setPaddingRelative(5, 5, 5, 5);
        inflater = LayoutInflater.from(this);
        editType = getIntent().getStringExtra("editType");
        crewId = getIntent().getStringExtra("crewId");
        addtime = getIntent().getStringExtra("addtime");
        YLog.e("Edit   addtime" +addtime);
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
                inputCount.setText("" + charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void initRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        editScroll.setLayoutManager(linearLayoutManager);
        adapter = new EditUtilsRecycleAdapter(scrollUtilImgs,this);
        editScroll.setAdapter(adapter);
        adapter.setOnItemClickListener(new EditUtilsRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int imageId) {
                switch (imageId){
                    case R.mipmap.undo:
                        editor.undo();
                        break;
                    case R.mipmap.redo:
                        editor.redo();
                        break;
                    case R.mipmap.bold:
                        editor.setBold();
                        break;
                    case R.mipmap.italic:
                        editor.setItalic();
                        break;
                    case R.mipmap.subscript:
                        editor.setSubscript();
                        break;
                    case R.mipmap.superscript:
                        editor.setSuperscript();
                        break;
                    case R.mipmap.strikethrough:
                        editor.setStrikeThrough();
                        break;
                    case R.mipmap.underline:
                        editor.setUnderline();
                        break;
                    case R.mipmap.h1:
                        editor.setHeading(1);
                        break;
                    case R.mipmap.h2:
                        editor.setHeading(2);
                        break;
                    case R.mipmap.h3:
                        editor.setHeading(3);
                        break;
                    case R.mipmap.h4:
                        editor.setHeading(4);
                        break;
                    case R.mipmap.h5:
                        editor.setHeading(5);
                        break;
                    case R.mipmap.h6:
                        editor.setHeading(6);
                        break;
                    case R.mipmap.txt_color:
                        showColorPicker(R.color.main_text,new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int color) {
                                editor.setTextColor(color);
                            }
                        });
                        break;
                    case R.mipmap.bg_color:
                        showColorPicker(R.color.white,new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int color) {
                                editor.setTextBackgroundColor(color);
                            }
                        });
                        break;
                    case R.mipmap.indent:
                        editor.setIndent();
                        break;
                    case R.mipmap.outdent:
                        editor.setOutdent();
                        break;
                    case R.mipmap.justify_left:
                        editor.setAlignLeft();
                        break;
                    case R.mipmap.justify_center:
                        editor.setAlignCenter();
                        break;
                    case R.mipmap.justify_right:
                        editor.setAlignRight();
                        break;
                    case R.mipmap.bullets:
                        editor.setBullets();
                        break;
                    case R.mipmap.numbers:
                        editor.setNumbers();
                        break;
//                    case R.mipmap.blockquote:
//                        editor.setBlockquote();
//                        break;
                    case R.mipmap.insert_image:
                        selectPic();
//                        String str = Utils.getHeadIconPath() + "/head.jpg";
//                        editor.insertImage("file:/storage/emulated/0/MySong/HeadIcon", "dachshund");
                        break;
                }
            }
        });
    }

    public void showColorPicker(int defaultColorRes,ColorPickerDialog.OnColorChangedListener onColorChangedListener){
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, getResources().getColor(defaultColorRes));
        colorPickerDialog.setOnColorChangedListener(onColorChangedListener);
        colorPickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

//    private static final int REFRESH_GALLARY = 0x0010;
//    private static final int SCROLL_RIGHT = 0x0020;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case REFRESH_GALLARY:
//                    for (AlbumFile albumFile : newAlbumFiles) {
//                        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.imageview_item, null);
//                        relativeLayout.setTag(albumFile.getPath());
//                        LinearLayout.LayoutParams paParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        paParams.setMargins(5, 5, 5, 5);
//                        ProgressImageView imageView = relativeLayout.findViewById(R.id.image_res);
//                        ImageView delete_icon = relativeLayout.findViewById(R.id.delete_icon);
//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(220, 220);
////                        params.setMargins(5, 5, 5, 5);
//                        imageView.setLayoutParams(params);
//                        myGallary.addView(relativeLayout);
//                        delete_icon.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                myGallary.removeView((View) view.getParent());
//                                removeAlbumInList((String) ((View) view.getParent()).getTag());
//                            }
//                        });
//                        Glide.with(EditNotifyActivity.this).
//                                load(albumFile.getPath()).
//                                placeholder(R.mipmap.ic_launcher).
//                                error(R.mipmap.delete_icon).
//                                centerCrop().
//                                into(imageView);
//                        YLog.e("图片大小--》" + albumFile.getSize());
//                        if (albumFile.getSize() > 5 * 1024 * 1024) {//图片大于5M,压缩处理
//                            YLog.e("压缩前的图片大小" + albumFile.getSize());
//                            Bitmap bitmap = Utils.compressPixel(albumFile.getPath());
//                            String path = Utils.savePics(bitmap, albumFile.getName());
//                            uploadPic(path, imageView);
//                        } else {
//                            uploadPic(albumFile.getPath(), imageView);
//                        }
//                    }
//                    scrollRight();
//                    break;
//                case SCROLL_RIGHT:
//                    horScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
//                    break;
//            }
//        }
//    };


//    public void removeAlbumInList(String filePath) {
//        for (int i = 0; i < myAlbumFiles.size(); i++) {
//            if (myAlbumFiles.get(i).getPath().equals(filePath)) {
//                myAlbumFiles.remove(i);
//            }
//        }
//        if (picPath.containsKey(filePath)) {
//            picPath.remove(filePath);
//        }
//    }

//    public void scrollRight() {
//        handler.sendEmptyMessageDelayed(SCROLL_RIGHT, 300);
//    }


    @Override
    public void onHttpFaild(String msgType, String msg, Object obj) {
        super.onHttpFaild(msgType, msg, obj);
        switch (msgType) {
            case "appupload":
                if(uploadingDialog != null && uploadingDialog.isShowing()){
                    uploadingDialog.dismiss();
                }
                isUpLoading = false;
                break;
        }
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case "appupload":
                if(uploadingDialog != null && uploadingDialog.isShowing()){
                    uploadingDialog.dismiss();
                }
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                HttpImgUrlBean httpImgUrlBean = gson.fromJson(json, HttpImgUrlBean.class);
                String filePath = msg;
                if (httpImgUrlBean != null) {
                    String httpIconUrl = httpImgUrlBean.getImgUrl();
//                    picPath.put(msg, httpIconUrl);
                    YLog.e("图片上传成功" + httpIconUrl);
                    YLog.e("insertImage" + httpIconUrl);
                    editor.insertImage(httpIconUrl,"dachshund");
                    isUpLoading = false;

                }
                break;
            case Constant.HTTP_REQUEST_BIGPLAN + ":doAdd":
            case Constant.HTTP_REQUEST_NOTICE + ":doAdd":
            case Constant.HTTP_REQUEST_MEMORANDUM + ":doAdd":
            case Constant.HTTP_REQUEST_REMIND + ":doAdd":
            case Constant.HTTP_REQUEST_SCRIPTPAGE + ":doAdd":
                Toast.makeText(getApplicationContext(), R.string.apply_notice_success, Toast.LENGTH_SHORT).show();
                if(MainActivity.instance != null){
                    MainActivity.instance.reloadCrewListData();//重新加载主页面内容
                }
                finish();
                break;
        }
    }


    /**
     * 发布编辑内容
     */
    public void uploadEditContent() {
        if (checkData()) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", edTitle.getText().toString().trim());
            map.put("content", editor.getHtml());
            map.put("cid", crewId);
            map.put("pics", getPicString());
            if(editType == Constant.HTTP_REQUEST_MEMORANDUM){
                if(!TextUtils.isEmpty(addtime)){
                }else{
                    addtime  = Utils.getStringDateFromMillis(System.currentTimeMillis(),"yyyy-MM-dd");
                }
            }
            YLog.e("发布备忘录---》"+addtime);
            iHttpPresenter.doAdd(editType, "", map,addtime);
        }
    }


    //检验发布内容是否合法
    public boolean checkData() {

        return true;
    }

    public String getPicString() {
        StringBuilder sb = new StringBuilder();
        for (String path : picPath.values()) {
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
                .selectCount(1)
                .checkedList(null)
//                .filterSize() // Filter the file size.
//                .filterMimeType() // Filter file format.
//                .afterFilterVisibility() // Show the filtered files, but they are not available.
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
//                        myAlbumFiles = result;
//                        newAlbumFiles.clear();
//                        for (AlbumFile albumFile : result) {
//                            if (!myAlbumFiles.contains(albumFile)) {
//                                newAlbumFiles.add(albumFile);
//                            }
//                        }
//                        myGallary.removeAllViews();
//                        myAlbumFiles.addAll(newAlbumFiles);
                        for(AlbumFile albumFile: result){
                            Bitmap bitmap = Utils.compressPixel(albumFile.getPath());
                            String path = Utils.savePics(bitmap, albumFile.getName());
                            showUploadingDialog(path);
                        }
//                        handler.sendEmptyMessage(REFRESH_GALLARY);
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

    private  Dialog uploadingDialog;
    private boolean isUpLoading = false;
    /**
     * 弹出图片上传进度dialog
     */
    public void showUploadingDialog(String filePath){
        uploadingDialog = new Dialog(this, R.style.datePickerStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.uploading_layout, null);
        uploadingDialog.setContentView(root);
        RoundProgressBar progressBar = root.findViewById(R.id.progress_bar);
        uploadingDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = uploadingDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标

        WindowManager wm = (WindowManager)this
                .getSystemService(Context.WINDOW_SERVICE);

        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;// 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        root.measure(0, 0);
//        lp.alpha = 2f; // 透明度
        dialogWindow.setAttributes(lp);
        isUpLoading = true;
        uploadPic(filePath,progressBar);
        uploadingDialog.show();
    }


    public void uploadPic(final String filePath, final RoundProgressBar progressBar) {
        if (!picPath.containsKey(filePath)) {
            File file = new File(filePath);
            UploadFileRequestBody requestFile = new UploadFileRequestBody(file, new FileUploadObserver<ResponseBody>() {
                @Override
                public void onUpLoadSuccess(ResponseBody responseBody) {
                    isUpLoading = false;
                }

                @Override
                public void onUpLoadFail(Throwable e) {
                    isUpLoading = false;
                    Toast.makeText(getApplicationContext(), R.string.upload_faild, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(int progress) {
                    YLog.e(filePath + "进度--》" + progress);
                    progressBar.setProgress(progress);
                }
            });
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            YLog.e("开始上传图片--》" + filePath);
//            imageView.setProgress(0);
            picPath.put(filePath, "");
            iHttpPresenter.appupload(body, filePath);
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
//            case R.id.add_img_btn:
//                selectPic();
//                break;
        }
    }
}

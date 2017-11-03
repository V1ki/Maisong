package com.yuanshi.maisong.activity;

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
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private int editType = 0;//编辑内容类型；
    public static final int EDIT_TYPE_CREW_NOTITY = 0;//编辑剧组通知
    public static final int EDIT_TYPE_SCRIPT_UPDATE = 1;//编辑剧本扉页
    public static final int EDIT_TYPE_MEMOIRE = 2;//编辑备忘录


    private ArrayList<AlbumFile> myAlbumFiles = new ArrayList<>();
    private LayoutInflater inflater;

    @Override
    protected int getContentViewId() {
        return R.layout.edit_notify_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        inflater = LayoutInflater.from(this);
        editType = getIntent().getIntExtra("editType", EDIT_TYPE_CREW_NOTITY);
        switch (editType) {
            case EDIT_TYPE_CREW_NOTITY:
                titleText.setText(R.string.notifycation_of_crew);
                break;
            case EDIT_TYPE_SCRIPT_UPDATE:
                titleText.setText(R.string.script_update);
                break;
            case EDIT_TYPE_MEMOIRE:
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
                    for (AlbumFile albumFile : myAlbumFiles) {
                        YLog.e("添加图片");
                        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.imageview_item, null);
                        relativeLayout.setTag(albumFile.getPath());
                        ImageView imageView = relativeLayout.findViewById(R.id.image_res);
                        ImageView delete_icon = relativeLayout.findViewById(R.id.delete_icon);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(220, 220);
                        params.setMargins(5, 5, 5, 5);
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
                    }
                    scrollRight();
                    break;
                case SCROLL_RIGHT:
                    horScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    break;
            }
        }
    };


    public void removeAlbumInList(String picPath) {
        for (int i = 0; i < myAlbumFiles.size(); i++) {
            if (myAlbumFiles.get(i).getPath().equals(picPath)) {
                myAlbumFiles.remove(i);
            }
        }
    }

    public void scrollRight() {
        handler.sendEmptyMessageDelayed(SCROLL_RIGHT, 300);
    }

    @OnClick({R.id.back_icon, R.id.release_btn, R.id.add_img_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.release_btn:
                //发布编辑内容
                break;
            case R.id.add_img_btn:
                selectPic();
                break;
        }
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
                        myAlbumFiles = result;
                        myGallary.removeAllViews();
                        handler.sendEmptyMessage(REFRESH_GALLARY);
                        for (AlbumFile file : result) {
                            YLog.e(file.getPath());
                        }
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
}

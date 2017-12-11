package com.yuanshi.maisong.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.utils.txtread_utils.FileHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/9.
 */

public class ShowFileActivity extends BaseActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.txt_tv)
    TextView txtTv;
    @BindView(R.id.txt_layout)
    ScrollView txtLayout;
    private String fileName;

    @Override
    protected int getContentViewId() {
        return R.layout.show_file_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        fileName = getIntent().getStringExtra("fileName");
        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(getApplicationContext(), R.string.file_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        titleText.setText(fileName);
        File file = new File(Utils.getFileDownloadRealPath() + "/" + fileName);
        if (file.exists()) {
            if (fileName.endsWith("pdf")) {
                txtLayout.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                displayFromFile(file);
            } else if (fileName.endsWith("txt")) {
                txtLayout.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                showTxtFile(fileName);
            } else {
                Toast.makeText(getApplicationContext(), R.string.not_support_file_format, Toast.LENGTH_SHORT).show();
                finish();
            }
        }else {
            Toast.makeText(getApplicationContext(), R.string.file_not_found, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void displayFromAssets(String assetFileName) {
        pdfView.fromAsset(assetFileName)   //设置pdf文件地址
                .defaultPage(0)         //设置默认显示第1页
                .onPageChange(this)     //设置翻页监听
                .onLoad(this)           //设置加载监听
                .onDraw(this)            //绘图监听
                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
                .swipeVertical(false)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻页
//                 .pages()  //把 5 过滤掉
                .load();
    }

    private void displayFromFile(File file) {
        pdfView.fromFile(file)   //设置pdf文件地址
                .defaultPage(0)         //设置默认显示第1页
                .onPageChange(this)     //设置翻页监听
                .onLoad(this)           //设置加载监听
                .onDraw(this)            //绘图监听
                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
                .swipeVertical(false)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻
                // .pages( 2 ，5  )  //把2  5 过滤掉
                .load();
    }

    public void showTxtFile(String fileName) {
        FileHelper helper = new FileHelper(this);
        String text = helper.readSDFile(fileName);
        txtTv.setText(text);
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_icon)
    public void onViewClicked() {
        finish();
    }
}

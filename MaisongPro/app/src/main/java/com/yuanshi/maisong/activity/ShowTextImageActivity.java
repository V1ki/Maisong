package com.yuanshi.maisong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuanshi.iotpro.publiclib.activity.BaseActivity;
import com.yuanshi.iotpro.publiclib.utils.Constant;
import com.yuanshi.iotpro.publiclib.utils.YLog;
import com.yuanshi.maisong.R;
import com.yuanshi.maisong.bean.NoticeDetailBean;
import com.yuanshi.maisong.utils.Utils;
import com.yuanshi.maisong.view.MixedTextImageLayout;
import com.yuanshi.maisong.view.RoundProgressBar;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengbocheng on 2017/11/2.
 */
public class ShowTextImageActivity extends BaseActivity {
    @BindView(R.id.back_icon)
    ImageButton backIcon;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.mixed_text_image_layout)
    MixedTextImageLayout mixedTextImageLayout;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.autherName)
    TextView autherName;
    @BindView(R.id.create_date)
    TextView createDate;
    @BindView(R.id.subline)
    TextView subline;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.fileLisetView)
    ListView fileLisetView;

    private String id;
    private String title;
    private String requestType;
    private ArrayList<String> fileList = new ArrayList<>();
    private FileListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.show_text_image_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        requestType = getIntent().getStringExtra("requestType");
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(getApplicationContext(), R.string.crew_id_null, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        adapter = new FileListAdapter(this);
        fileLisetView.setAdapter(adapter);
        titleText.setText(title);
        iHttpPresenter.details(requestType, id);
        fileLisetView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewHolder holder = (ViewHolder) view.getTag();
                String url = fileList.get(i);
                String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
                if( holder.progressLayout.getVisibility() == View.VISIBLE){//正在下载中，点击不处理
                    return ;
                }else{
                    if(!Utils.isFileExist(fileName)){//未下载，点击开始下载
                        holder.progressLayout.setVisibility(View.VISIBLE);
                        iHttpPresenter.download(fileList.get(i), Utils.getFileDownloadTempPath(), fileName, view);
                    }else{//已下载，点击打开文件
                        Intent intent = new Intent(ShowTextImageActivity.this, ShowFileActivity.class);
                        intent.putExtra("fileName", fileName);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onDownloadProgress(View view, long progress, long total) {
        super.onDownloadProgress(view, progress, total);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.progressBar.setProgress((int) (100 * progress / total));
    }

    @Override
    public void onDownloadComplete(View view,String fileName) {
        super.onDownloadComplete(view,fileName);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.progressLayout.setVisibility(View.GONE);
        Utils.moveFile(fileName);//下载完成，将文件移动到正式目录
        viewHolder.downloadStateTv.setText(R.string.has_downloaded);
        viewHolder.downloadStateTv.setTextColor(getResources().getColor(R.color.btn_green_noraml));
    }

    @Override
    public void onDownloadError(View view, Throwable e,String fileName) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.progressLayout.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(),R.string.download_faild,Toast.LENGTH_SHORT).show();
        Utils.deleteFile(new File(Utils.getFileDownloadTempPath()+"/"+fileName));//下载失败，删除文件
        viewHolder.downloadStateTv.setText(R.string.not_downloaded);
        viewHolder.downloadStateTv.setTextColor(getResources().getColor(R.color.gray_normal));
    }

    private void initData(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        NoticeDetailBean notice = gson.fromJson(json, NoticeDetailBean.class);
        fileList = notice.getFile();
        adapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(notice.getTitle())) {
            textTitle.setText(notice.getTitle());
        }
        if (!TextUtils.isEmpty(notice.getAuthor())) {
            autherName.setText("@" + notice.getAuthor());
        }
        if (!TextUtils.isEmpty(notice.getAddtime())) {
            createDate.setText(notice.getAddtime());
        }
        buildContent(notice);
    }

    private void buildContent(NoticeDetailBean notice) {
        StringBuilder content = new StringBuilder();

        if (!TextUtils.isEmpty(notice.getContent()) && notice.getPics() != null && notice.getPics().length > 0) {
            String[] contents = notice.getContent().split("\n");
            if (contents.length <= notice.getPics().length) {
                for (int i = 0; i < contents.length; i++) {
                    content.append("<img>").append(notice.getPics()[i]).append("</img>");
                    content.append(contents[i]);
                }
                for (int i = contents.length; i < notice.getPics().length; i++) {
                    content.append(contents[i]);
                }
            } else {
                for (int i = 0; i < notice.getPics().length; i++) {
                    content.append("<img>").append(notice.getPics()[i]).append("</img>");
                    content.append(contents[i]);
                }
                for (int i = notice.getPics().length; i < contents.length; i++) {
                    content.append(contents[i]);
                }
            }
        } else if (notice.getPics() == null || notice.getPics().length == 0) {
            content.append(notice.getContent());
        }

        mixedTextImageLayout.setContent(content.toString());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 200);
    }

    @Override
    public void onHttpSuccess(String msgType, String msg, Object obj) {
        switch (msgType) {
            case Constant.HTTP_REQUEST_REMIND + ":details":
            case Constant.HTTP_REQUEST_SCRIPTPAGE + ":details":
            case Constant.HTTP_REQUEST_MEMORANDUM + ":details":
            case Constant.HTTP_REQUEST_BIGPLAN + ":details":
            case Constant.HTTP_REQUEST_NOTICE + ":details":
                initData(obj);
                break;
        }
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

    private class FileListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public FileListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return fileList.size();
        }

        @Override
        public Object getItem(int i) {
            return fileList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.file_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            String url = fileList.get(i);
            if (url.endsWith("pdf")) {
                holder.fileIcon.setImageResource(R.mipmap.pdf_icon);
            } else if (url.endsWith("txt")) {
                holder.fileIcon.setImageResource(R.mipmap.txt_icon);
            }
            YLog.e("PDF地址" + url);
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
            if(Utils.isFileExist(fileName)){
                holder.downloadStateTv.setTextColor(getResources().getColor(R.color.btn_green_noraml));
                holder.downloadStateTv.setText(R.string.has_downloaded);
            }else{
                holder.downloadStateTv.setTextColor(getResources().getColor(R.color.gray_normal));
                holder.downloadStateTv.setText(R.string.not_downloaded);
            }
            holder.fileName.setText(fileName);
            holder.progressLayout.setVisibility(View.GONE);
            return view;
        }
    }
    static class ViewHolder {
        @BindView(R.id.progress_bar)
        RoundProgressBar progressBar;
        @BindView(R.id.progress_layout)
        RelativeLayout progressLayout;
        @BindView(R.id.fileIcon)
        ImageView fileIcon;
        @BindView(R.id.fileName)
        TextView fileName;
        @BindView(R.id.downloadStateTv)
        TextView downloadStateTv;
        @BindView(R.id.title_layout)
        RelativeLayout titleLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

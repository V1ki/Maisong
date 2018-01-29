package com.yuanshi.maisong.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yuanshi.maisong.R;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/12.
 */
public class ServiceFlatformFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    private View m_View;
    public static ServiceFlatformFragment serviceFlatformFragment;
    public String loadUrl = "http://47.104.13.45/index.php?m=Mobile";//加载的链接地址

    public String localFile = "";//本地备用html文件

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (m_View == null) {
            m_View = inflater.inflate(R.layout.serveice_platform_layout, null);
        }
        ViewGroup parent = (ViewGroup) m_View.getParent();
        if (parent != null) {
            parent.removeView(m_View);
        }
        initView();
        initWebView();
        serviceFlatformFragment = this;
        unbinder = ButterKnife.bind(this, m_View);
        return m_View;
    }

    public static ServiceFlatformFragment getInstance() {
        if (serviceFlatformFragment == null) {
            serviceFlatformFragment = new ServiceFlatformFragment();
        }
        return serviceFlatformFragment;
    }

    private void initView() {
        webView = (WebView) m_View.findViewById(R.id.webView);
    }

    private void initWebView() {
        webView.loadUrl(loadUrl);
        webViewSettings();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * webView加载错误
             * @param view
             * @param errorCode
             * @param description
             * @param failingUrl
             */
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                switch (errorCode) {
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        view.loadUrl("file:///android_assets/error_handle.html");
                        break;
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * 获取加载进度
             * @param view
             * @param newProgress
             * @return
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
            }
        });
    }

    public void webViewSettings() {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持插件
        //        webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    public void sendEmail(String address) {
        Intent i = new Intent(Intent.ACTION_SEND);
        // i.setType("text/plain"); //模拟器请使用这行
        i.setType("message/rfc822"); // 真机上使用这行
        i.putExtra(Intent.EXTRA_EMAIL,
                new String[] { address });
        i.putExtra(Intent.EXTRA_SUBJECT, "主题");
        i.putExtra(Intent.EXTRA_TEXT, "正文");
        startActivity(Intent.createChooser(i,
                getString(R.string.select_email_app)));
    }
}

package com.yuanshi.maisong.fragment;

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
    public String loadUrl = "http://m.jd.com/";//加载的链接地址
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
        WebSettings settings = webView.getSettings();
        webView.setVerticalScrollbarOverlay(true); //指定的垂直滚动条有叠加样式
        settings.setUseWideViewPort(true);//设定支持viewport

        settings.setLoadWithOverviewMode(true);

        settings.setBuiltInZoomControls(true);

        settings.setSupportZoom(true);//设定支持缩放
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

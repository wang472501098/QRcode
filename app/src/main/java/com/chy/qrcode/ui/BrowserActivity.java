package com.chy.qrcode.ui;

import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.chy.qrcode.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 47250 on 2017/6/21.
 */
public class BrowserActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.wv_Browser)
    WebView wvBrowser;
    private String linkUrl;
    @Bind(R.id.webViewLoading)
    ProgressBar webViewLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linkUrl = getIntent().getStringExtra("linkUrl");
        initView();
    }

    private void initView() {

        wvBrowser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvBrowser.getSettings().setAppCacheEnabled(true);
        wvBrowser.getSettings().setDefaultTextEncodingName("utf-8");

        wvBrowser.setBackgroundColor(Color.argb(0, 0, 0, 0));
        wvBrowser.getSettings().setAllowContentAccess(true);
        wvBrowser.getSettings().setJavaScriptEnabled(true);

        wvBrowser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvBrowser.getSettings().setLoadsImagesAutomatically(true);
        wvBrowser.getSettings().setDomStorageEnabled(true);
        wvBrowser.getSettings().setLoadWithOverviewMode(true);
        wvBrowser.getSettings().setDisplayZoomControls(false);
        //显示进度
        wvBrowser.getSettings().setSupportZoom(true);
        wvBrowser.getSettings().setBuiltInZoomControls(false);
        wvBrowser.setInitialScale(5);

        wvBrowser.getSettings().setUseWideViewPort(true);
        wvBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wvBrowser.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                wvBrowser.setVisibility(View.GONE);
            }
        });
        wvBrowser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (null != webViewLoading) {
                    if (newProgress == 100) {
                        webViewLoading.setVisibility(View.GONE);
                    } else {
                        webViewLoading.setVisibility(View.VISIBLE);
                        webViewLoading.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        if (!TextUtils.isEmpty(linkUrl)) {
            wvBrowser.loadUrl(linkUrl);
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    public void onBackPressed() {
        if (wvBrowser.canGoBack()) {
            wvBrowser.goBack();
        } else {
            finish();
        }
    }
}

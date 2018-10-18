package net.eanfang.worker.ui.activity.worksapce.notice;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfficialDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.wb_view)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_detail);
        ButterKnife.bind(this);

        setTitle(getIntent().getStringExtra("title"));
        setLeftBack();
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setSupportZoom(false);//不支持缩放
        setting.setBuiltInZoomControls(false);//不出现放大和缩小的按钮
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);//不设置网络缓存

        mWebView.setWebViewClient(new WebViewClient() {
        });//IE内核

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });//谷歌内核

        mWebView.loadUrl(getIntent().getStringExtra("url"));
    }
}

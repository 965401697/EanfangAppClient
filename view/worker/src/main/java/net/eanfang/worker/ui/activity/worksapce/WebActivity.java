package net.eanfang.worker.ui.activity.worksapce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.util.ConnectivityChangeUtil;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  11:18
 * @email houzhongzhou@yeah.net
 * @desc 加载webview使用
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.web_content)
    FrameLayout mRootLayout;
    WebView mWebView;
    @BindView(R.id.ll_check_net)
    LinearLayout llCheckNet;
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;
    @BindView(R.id.ll_error_view)
    LinearLayout llErrorView;
    private boolean mLastLoadFailed = false;
    private String urls, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        urls = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        //添加webView到布局中
        addWebViewToLayout();
        setWebView();
        setWebClient();
        setWebViewChromeClient();
        loadUrl(urls);
        initCheck();

    }

    private void initCheck() {
        setLeftBack(true);
        setTitle(title);
        if (ConnectivityChangeUtil.isNetConnected(WebActivity.this) == false) {
            llErrorView.setVisibility(View.VISIBLE);
            loadUrl(urls);
        } else {
            llErrorView.setVisibility(View.GONE);
            loadUrl(urls);
        }
        llCheckNet.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_SETTINGS)));

        llRefresh.setOnClickListener((v) -> {
            if (ConnectivityChangeUtil.isNetConnected(this) == true) {
                llErrorView.setVisibility(View.GONE);
                loadUrl(urls);
            } else {
                showToast("亲，没有网络！！！");
            }
        });
    }


    void addWebViewToLayout() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mRootLayout.addView(mWebView);

    }

    /**
     * 配置webView
     */
    void setWebView() {
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        //支持Javascript交互
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式q


    }

    /**
     * 设置webView的Client，如页面加载开始，错误，拦截请求，接受Error等
     */
    void setWebClient() {
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);
            }

            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                startLoading();
            }

            //页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissLoading();

            }

            //页面加载每一个资源，如图片
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }


            //监听WebView发出的请求并做相应的处理
            //浏览器的渲染以及资源加载都是在一个线程中，如果在shouldInterceptRequest处理时间过长，WebView界面就会阻塞
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            //监听WebView发出的请求并做相应的处理
            //浏览器的渲染以及资源加载都是在一个线程中，如果在shouldInterceptRequest处理时间过长，WebView界面就会阻塞
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            //页面加载出现错误,23以下的
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLastLoadFailed = true;
                dismissLoading();

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLastLoadFailed = true;
                dismissLoading();
            }

            //https错误
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                mLastLoadFailed = true;
                dismissLoading();
            }
        });
    }


    /**
     * 设置webView的辅助功能，如页面加载进度，title，图标，js弹框等
     */
    void setWebViewChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient() {

            //页面加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            //获取标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            //获取图标
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            //是否支持页面中的js警告弹出框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Toast.makeText(WebActivity.this, message, Toast.LENGTH_SHORT).show();

                return super.onJsAlert(view, url, message, result);
            }

            //是否支持页面中的js确定弹出框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            //是否支持页面中的js输入弹出框
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);

            }
        });
    }

    /**
     * 加载url
     */
    void loadUrl(String url) {
//        mWebView.loadUrl(url, extraHeaders);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    @SuppressLint("NewApi")
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

}

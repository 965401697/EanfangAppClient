package net.eanfang.client.ui.activity.worksapce;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
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

import com.eanfang.util.ConnectivityChangeReceiver;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

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
    private static final String APP_CACAHE_DIRNAME = "webview.db";
    @BindView(R.id.web_content)
    FrameLayout mRootLayout;
    WebView mWebView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.ll_check_net)
    LinearLayout llCheckNet;
    @BindView(R.id.ll_refresh)
    LinearLayout llRefresh;
    @BindView(R.id.ll_error_view)
    LinearLayout llErrorView;
    private boolean mLastLoadFailed = false;
    private String urls, title;
    Map extraHeaders = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        urls = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        extraHeaders.put("userToken", EanfangApplication.get().getUser().getToken());
        //添加webView到布局中
        addWebViewToLayout();

        //set webView Setting
        setWebView();

        //set webView Client
        setWebClient();

        //set webView chrome
        setWebViewChromeClient();
        //load web
        loadUrl(urls);
        initCheck();

    }

    private void initCheck() {
        setLeftBack();
        setTitle(title);
        if (ConnectivityChangeReceiver.isNetConnected(WebActivity.this) == false) {
            llErrorView.setVisibility(View.VISIBLE);
            loadUrl(urls);
        } else {
            llErrorView.setVisibility(View.GONE);
            loadUrl(urls);
        }
        llCheckNet.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_SETTINGS)));

        llRefresh.setOnClickListener((v) -> {
            if (ConnectivityChangeReceiver.isNetConnected(this) == true) {
                llErrorView.setVisibility(View.GONE);
                loadUrl(urls);
            } else {
                showToast("亲，没有网络！！！");
            }
        });
    }


    /**
     * 1、不在xml中定义Webview，而是在需要的时候在Activity中创建
     * 使用getApplicationgContext()，避免内存泄漏。
     * <p>
     * 2、当然，你也可以配置webView所在Activity，
     * 在AndroidManifest中的进程为：android:process=":remote"
     * 避免泄漏影响主进程
     **/
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

        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
//        if (ConnectivityChangeReceiver.isNetConnected(getApplicationContext())) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式q

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //网页中触发下载动作
            }
        });

    }

    /**
     * 设置webView的Client，如页面加载开始，错误，拦截请求，接受Error等
     */
    void setWebClient() {
        mWebView.setWebViewClient(new WebViewClient() {

            //拦截页面中的url加载,21以下的
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (resolveShouldLoadLogic(url)) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            //拦截页面中的url加载,21以上的
            @TargetApi(21)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (resolveShouldLoadLogic(request.getUrl().toString())) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mLastLoadFailed) {
                    llLoading.setVisibility(View.GONE);
                }

            }

            //页面加载每一个资源，如图片
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }


            //监听WebView发出的请求并做相应的处理
            //浏览器的渲染以及资源加载都是在一个线程中，如果在shouldInterceptRequest处理时间过长，WebView界面就会阻塞
            //21以下的
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            //监听WebView发出的请求并做相应的处理
            //浏览器的渲染以及资源加载都是在一个线程中，如果在shouldInterceptRequest处理时间过长，WebView界面就会阻塞
            //21以上的
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            //页面加载出现错误,23以下的
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLastLoadFailed = true;
                llLoading.setVisibility(View.GONE);

            }

            //页面加载出现错误,23以上的
            @TargetApi(23)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLastLoadFailed = true;
                llLoading.setVisibility(View.GONE);
            }

            //https错误
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                mLastLoadFailed = true;
                llLoading.setVisibility(View.GONE);
            }
        });
    }

    /**
     * js与web交互2
     * 通过 shouldOverrideUrlLoading 与 js交互
     */
    private boolean resolveShouldLoadLogic(String url) {
        Uri uri = Uri.parse(url);
        //解析协议
        if (uri.getScheme().equals("js")) {
            if (uri.getAuthority().equals("Authority")) {
                //你还可以继续接续参数
                //Set<String> collection = uri.getQueryParameterNames();
                Toast.makeText(WebActivity.this, "JS 2方法回调到web了", Toast.LENGTH_SHORT).show();

            }
            return true;
        }
        return false;
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
                /**
                 * 有时候，为了安全考虑，js的参数回调，会通过这类地方回调回来，然后不弹出框。
                 */
                if (resolveJSPrompt(message)) {
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);

            }
        });
    }

    /**
     * js与web交互3
     * 通过 onJsPrompt 与 js交互
     */
    private boolean resolveJSPrompt(String message) {
        Uri uri = Uri.parse(message);
        if (uri.getScheme().equals("js")) {
            if (uri.getAuthority().equals("Authority")) {

                //Set<String> collection = uri.getQueryParameterNames();
                //参数result:代表消息框的返回值(输入值)
                //result.confirm("JS 3方法回调到web");
                Toast.makeText(WebActivity.this, "JS 3方法回调到web了", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }

    /**
     * 加载url
     */
    void loadUrl(String url) {
        mWebView.loadUrl(url, extraHeaders);

/**  格式规定为:file:///android_asset/文件名.html
 *   mWebView.loadUrl("file:///android_asset/localHtml.html");
 方式1. 加载远程网页：
 方式2：加载asset的html页面
 mWebView.loadUrl("file:///android_asset/localHtml.html");
 方式3：加载手机SD的html页面
 mWebView.loadUrl("file:///mnt/sdcard/database/taobao.html");*/
    }

    /**
     * 是否可以后退
     * Webview.canGoBack()
     * 后退网页
     * Webview.goBack()
     * 是否可以前进
     * Webview.canGoForward()
     * 前进网页
     * Webview.goForward()
     * 以当前的index为起始点前进或者后退到历史记录中指定的steps
     * 如果steps为负数则为后退，正数则为前进
     * Webview.goBackOrForward(intsteps)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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

}

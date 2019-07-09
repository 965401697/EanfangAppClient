package net.eanfang.client.ui.activity.worksapce.notice;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfficialDetailActivity extends BaseClientActivity {

    @BindView(R.id.wb_view)
    WebView mWebView;
    @BindView(R.id.tv_gone)
    TextView tvGone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_official_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        setTitle(getIntent().getStringExtra("title"));
        setLeftBack();

        if (TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
            mWebView.setVisibility(View.GONE);
            tvGone.setVisibility(View.VISIBLE);
            return;
        }

        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setSupportZoom(false);//不支持缩放
        setting.setBuiltInZoomControls(false);//不出现放大和缩小的按钮
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);//不设置网络缓存

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//页面加载完成
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
                loadingDialog.show();
            }
        });//IE内核

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });//谷歌内核

        mWebView.loadUrl(getIntent().getStringExtra("url"));
        long id = getIntent().getLongExtra("id", 0);
        if (id > 0) {
            readStatus(id);
        }

    }

    private void readStatus(long id) {
        EanfangHttp.post(NewApiService.GET_OFFICIAL_CHANGE_STATUS)
                .params("id", id)
                .execute(new EanfangCallback<JSONObject>(this, false, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject object) {

                    }
                });
    }
}

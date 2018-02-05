package com.eanfang.application;

import com.camera.CameraApplication;
import com.eanfang.BuildConfig;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.FrecsoImagePipelineUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMOptions;
import com.im.model.Model;
import com.okgo.OkGo;
import com.okgo.cache.CacheEntity;
import com.okgo.cache.CacheMode;
import com.okgo.cookie.CookieJarImpl;
import com.okgo.cookie.store.DBCookieStore;
import com.okgo.https.HttpsUtils;
import com.okgo.interceptor.HttpLoggingInterceptor;
import com.okgo.model.HttpHeaders;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import easeui.EaseUI;
import okhttp3.OkHttpClient;

/**
 * @author Mr.hou
 *         Created at 2017/3/2
 * @desc 做SDK初始化工作
 */
public class EanfangApplication extends CustomeApplication {

    public static final String TAG = EanfangApplication.class.getSimpleName();
    private static EanfangApplication mEanfangApplication;
    private OkGo http;

    public static EanfangApplication getApplication() {
        return mEanfangApplication;
    }

    public static EanfangApplication get() {
        return mEanfangApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mEanfangApplication = this;
        initConfig();
        initOkGo();
//        initXinGe();
        CameraApplication.init(this, true);

        initEase();
    }


    /**
     * 环信初始化
     */
    private void initEase() {
        EMOptions options = new EMOptions();
        //设置需要同意后才接受邀请
        options.setAcceptInvitationAlways(false);
        //设置需要同意后才能进行群邀请
        options.setAutoAcceptGroupInvitation(false);

        EaseUI.getInstance().init(this, options);

        //初始化数据模型层类
        Model.getInstance().init(this);


    }

    @Override
    public void initConfig() {
        /**fresco加载图片*/
        Fresco.initialize(this, FrecsoImagePipelineUtil.getImagePipelineConfig(getApplicationContext()));
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(this));
        SharePreferenceUtil.get().init(mEanfangApplication);
        //bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_CLIENT, false);
    }

    public void initOkGo() {

        //创建OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //全局的读取超时时间
        builder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);

        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        HttpHeaders headers = new HttpHeaders();

        if (EanfangApplication.get().getUser() != null) {
            headers.put("YAF-Token", EanfangApplication.get().getUser().getToken());
        }
        headers.put("Request-From", "CLIENT");

        //必须调用初始化
        http = OkGo.getInstance().init(this)
                //建议设置OkHttpClient，不设置将使用默认的
                .setOkHttpClient(builder.build())
                //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheMode(CacheMode.NO_CACHE)
                //全局统一缓存时间，默认永不过期，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .setRetryCount(3)
                //全局公共头
                .addCommonHeaders(headers);
        //全局公共参数
        EanfangHttp.setHttp(http);
    }


}

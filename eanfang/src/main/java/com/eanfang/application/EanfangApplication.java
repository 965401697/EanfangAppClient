package com.eanfang.application;

import android.util.Log;

import com.camera.CameraApplication;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.eanfang.util.SharePreferenceUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.okgo.OkGo;
import com.okgo.cache.CacheEntity;
import com.okgo.cache.CacheMode;
import com.okgo.cookie.CookieJarImpl;
import com.okgo.cookie.store.DBCookieStore;
import com.okgo.https.HttpsUtils;
import com.okgo.interceptor.HttpLoggingInterceptor;
import com.okgo.model.HttpHeaders;
import com.photopicker.com.imageloader.BGAGlideImageLoader;
import com.photopicker.com.imageloader.BGAImage;
import com.tencent.smtt.sdk.QbSdk;
import com.yaf.sys.entity.BaseDataEntity;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

import static com.eanfang.config.EanfangConst.XUNFEI_APPID;

/**
 * @author Mr.hou
 *         Created at 2017/3/2
 * @desc 做SDK初始化工作
 */
public class EanfangApplication extends CustomeApplication {

    public static final String TAG = EanfangApplication.class.getSimpleName();
    private static EanfangApplication mEanfangApplication;
    /**
     * 是否自动更新过
     */
    public static boolean isUpdated = false;
    /**
     * 存储地域
     */
    public static BaseDataEntity sSaveArea;
    /**
     * app类型
     */
    public static String AppType;

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

//        BaseUtil.init(this);
        //数据库初始化 ziwu
//        mManager = DaoManager.getInstance();
//        mManager.init(this);
        CameraApplication.init(this, true);

        //初始换tbs 不需要 callback 的可以传入 null
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("zzw", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.e("zzw", " onCoreInitFinished");
            }

        });
        // 初始化讯飞
        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        RecognitionManager.getSingleton().init(EanfangApplication.getApplication().getApplicationContext(), XUNFEI_APPID);
        // 初始化BGA 图片选择
        BGAImage.setImageLoader(new BGAGlideImageLoader());
    }


    @Override
    public void initConfig() {
        /**fresco加载图片*/
        OkHttpClient mOkHttpClient = new OkHttpClient();
        ImagePipelineConfig frescoConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, mOkHttpClient).build();
        Fresco.initialize(this, frescoConfig);

//        Fresco.initialize(this, FrecsoImagePipelineUtil.getImagePipelineConfig(getApplicationContext()));
        SimpleDraweeView.initialize(new PipelineDraweeControllerBuilderSupplier(this));
        SharePreferenceUtil.get().init(mEanfangApplication);

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

        if (EanfangApplication.get().getUser() != null) {
            EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
        }

        try {
            String appType = (String) SharePreferenceUtil.get().get("app", "");
            if (appType.equals("client")) {
                EanfangHttp.setClient();
            } else if (appType.equals("worker")) {
                EanfangHttp.setWorker();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (EanfangApplication.AppType != null && EanfangApplication.AppType.equals("client")) {
//            EanfangHttp.setClient();
//        } else if (EanfangApplication.AppType != null && EanfangApplication.AppType.equals("worker")) {
//            EanfangHttp.setWorker();
//        }
    }


}

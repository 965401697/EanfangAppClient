package com.eanfang.base;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.eanfang.BuildConfig;
import com.eanfang.base.kit.V;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.network.Leaves;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.voice.RecognitionManager;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.bingoogolapple.photopicker.imageloader.BGAGlideImageLoader;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import okhttp3.OkHttpClient;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class BaseApplication extends MultiDexApplication {
    /*
     * 初始化TAG
     * */
    private static String TAG = BaseApplication.class.getName();

    private static BaseApplication appContext;

    /*Activity堆*/
    private List<Activity> activityStack = new ArrayList<>();
//    /**
//     * 存储地域
//     */
//    public BaseDataEntity sSaveArea;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        Config.Cache.init();

        Leaves.INSTANCE.init(this);
        initFresco();
        initOkGo();
        initConfig();
    }


    protected void initConfig() {
        //debug开启  严格模式
        if (BuildConfig.DEBUG_MOD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //相机助手
//        CameraApplication.init(this, BuildConfig.DEBUG_MOD);
        //初始换tbs  debug模式 回调 正式模式不回调
        QbSdk.initX5Environment(this, !BuildConfig.DEBUG_MOD ? null : new QbSdk.PreInitCallback() {
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
        RecognitionManager.getSingleton().init(BaseApplication.get().getApplicationContext(), BuildConfig.IFLYTEK_APP_ID);
        // 初始化BGA 图片选择
        BGAImage.setImageLoader(new BGAGlideImageLoader());
    }

    /**
     * 初始化 OkGo
     * OkGo的网络方法已过期 请尽快移步 rds
     */
    @Deprecated
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
        OkGo http = OkGo.getInstance().init(this)
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

    //--------------------------------------------------初始化部分----------------------------------------------------

    /**
     * 初始化 Fresco
     * Fresco 即将被废弃 请尽快替换 ImageChoseManager
     */
    @Deprecated
    private void initFresco() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        ImagePipelineConfig frescoConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, mOkHttpClient).build();
//        Fresco.initialize(this, frescoConfig);
    }

    public static BaseApplication get() {
        return appContext;
    }

    //---------------------------------------------------activity处理-------------------------------------------------

    /**
     * 添加activity
     *
     * @param activity activity
     */
    public void addActivity(final Activity activity) {
        if (null == activityStack) {
            activityStack = new ArrayList<>();
        }
        activityStack.add(activity);
    }

    /**
     * 关闭 activity
     *
     * @param activity activity
     */
    public void closeActivity(final Activity activity) {
        if (null == activityStack) {
            activityStack = new ArrayList<>();
        }
        if (!activity.isFinishing()) {
            activity.finish();
        }
        activityStack.remove(activity);
    }

    /**
     * 通过类关闭activity
     *
     * @param activity activity
     */
    public void closeActivity(final Class<? extends Activity> activity) {
        if (null == activityStack) {
            activityStack = new ArrayList<>();
        }
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().getName().equals(activity.getName())) {
                closeActivity(activityStack.get(i));
                i--;
            }
        }
    }

    /**
     * 关闭所有Activity
     */
    public void closeAllActivity() {
        for (int i = 0; i < activityStack.size(); ) {
            closeActivity(activityStack.get(i));
        }
    }

    /**
     * 获取最后一个Activity
     */

    public Activity currentActivity() {
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 返回寨内Activity的总数
     */
    public int howManyActivities() {
        return activityStack.size();
    }


    //---------------------------------------------------缓存部分---------------------------------------------

    /**
     * 缓存放值
     *
     * @param key   key
     * @param value value
     */
    @Deprecated
    public void set(String key, Object value) {
        CacheKit.get().put(key, value);
    }

    /**
     * 缓存取值  String
     *
     * @param key          key
     * @param defaultValue defaultValue
     * @return String
     */
    @Deprecated
    public String get(String key, Object defaultValue) {
        String s = CacheKit.get().getStr(key);
        return s != null ? s : defaultValue.toString();
    }

    /**
     * 缓存取值 T
     *
     * @param key   key
     * @param clazz clazz
     * @param <T>   T
     * @return Object
     */
    @Deprecated
    public <T> T get(String key, Class<T> clazz) {
        return CacheKit.get().get(key, clazz);
    }

    /**
     * 缓存删除值
     *
     * @param key key
     */
    @Deprecated
    public void remove(String key) {
        CacheKit.get().remove(key);
    }

    @Deprecated
    public void clear() {
        CacheKit.get().clear();
    }

    /**
     * 取缓存 LoginBean
     *
     * @return LoginBean
     */
    public LoginBean getLoginBean() {
        return CacheKit.get().get(LoginBean.class.getName(), LoginBean.class);
    }

    /**
     * 取缓存 getDefaultUser
     *
     * @return getDefaultUser
     */
    public UserEntity getUser() {
        return V.v(() -> getAccount().getDefaultUser());
    }

    /**
     * 取缓存 getUserId
     *
     * @return getUserId
     */
    public Long getUserId() {
        return V.v(() -> getUser().getUserId());
    }

    /**
     * 取缓存 getCompanyId
     *
     * @return getCompanyId
     */
    public Long getCompanyId() {
        return V.v(() -> getUser().getCompanyId());
    }

    /**
     * 取缓存 getTopCompanyId
     *
     * @return getTopCompanyId
     */
    public Long getTopCompanyId() {
        return V.v(() -> getUser().getTopCompanyId());
    }

    /**
     * 取缓存 getOrgCode
     *
     * @return getOrgCode
     */
    public String getOrgCode() {
        return V.v(() -> getUser().getDepartmentEntity().getOrgCode());
    }

    /**
     * 取缓存 getCompanyEntity
     *
     * @return getCompanyEntity
     */
    public OrgEntity getCompanyEntity() {
        return V.v(() -> getAccount().getDefaultUser().getCompanyEntity());
    }

    /**
     * 取缓存 getTopCompanyEntity
     *
     * @return getTopCompanyEntity
     */
    public OrgEntity getTopCompanyEntity() {
        return V.v(() -> getAccount().getDefaultUser().getTopCompanyEntity());
    }

    /**
     * 取缓存 getDepartmentEntity
     *
     * @return getDepartmentEntity
     */
    public OrgEntity getDepartmentEntity() {
        return V.v(() -> getAccount().getDefaultUser().getDepartmentEntity());
    }

    /**
     * 取缓存 getAccId
     *
     * @return getAccId
     */
    public Long getAccId() {
        return V.v(() -> getAccount().getAccId());
    }

    /**
     * 取缓存 account
     *
     * @return AccountEntity
     */
    public AccountEntity getAccount() {
        return V.v(() -> getLoginBean().getAccount());
    }
}
package net.eanfang.client.base;

import android.app.Activity;
import android.util.Log;

import com.eanfang.base.BaseApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangHttp;
import com.eanfang.kit.cache.CacheKit;
import com.eanfang.kit.loading.LoadKit;
import com.eanfang.network.config.HttpConfig;
import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.ui.activity.im.CustomizeMessage;
import net.eanfang.client.ui.activity.im.CustomizeMessageItemProvider;
import net.eanfang.client.ui.activity.im.CustomizeVideoMessage;
import net.eanfang.client.ui.activity.im.CustomizeVideoMessageItemProvider;
import net.eanfang.client.ui.activity.im.MyConversationClickListener;
import net.eanfang.client.ui.activity.im.SampleExtensionModule;

import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import lombok.Getter;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * 客户端的 Application 初始化一些配置
 * <p>
 * 使用时需要替换 AndroidManifest
 */
public class ClientApplication extends BaseApplication {
    @Getter
    private static IWXAPI wxApi;

    @Override
    public void onCreate() {
        super.onCreate();

        initRongIM();
        initWxPay();
        initHttp();
    }


    @Override
    protected void initConfig() {
        super.initConfig();
        LoadKit.initLoadSir();
        CacheKit.init(this).put("APP_TYPE", BuildConfig.APP_TYPE);
        MobSDK.init(this, BuildConfig.MOB_CLIENT_APPID, BuildConfig.MOB_CLIENT_APPKEY);
    }

    /**
     * 初始化网络配置
     */
    private void initHttp() {
        HttpConfig.init(com.eanfang.BuildConfig.API_HOST,
                BuildConfig.APP_TYPE,
                com.eanfang.BuildConfig.OSS_ENDPOINT,
                com.eanfang.BuildConfig.OSS_BUCKET,
                CacheKit.getDiskCacheDir(this).getPath(),
                BuildConfig.DEBUG,
                BuildConfig.VERSION_CODE
        );

        EanfangHttp.setClient();
        if (BaseApplication.get().getUser() != null) {
            EanfangHttp.setToken(BaseApplication.get().getLoginBean().getToken());
            HttpConfig.get().setToken(BaseApplication.get().getLoginBean().getToken());
        }
    }

    /**
     * 初始化微信支付
     */
    @Deprecated
    private void initWxPay() {
        //初始化微信支付
        wxApi = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);
        wxApi.registerApp(EanfangConst.WX_APPID_CLIENT);
    }

    /**
     * 初始化融云
     */
    private void initRongIM() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
            RongExtensionManager.getInstance().registerExtensionModule(new SampleExtensionModule());
            RongIM.setConversationClickListener(new MyConversationClickListener());

            RongIM.registerMessageType(CustomizeMessage.class);
            RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());

            RongIM.registerMessageType(CustomizeVideoMessage.class);
            RongIM.registerMessageTemplate(new CustomizeVideoMessageItemProvider());
        }
    }

    /**
     * 链接融云 获取token
     *
     * @param token token
     */
    public static void connect(String token, Activity activity) {
//        Log.e("zzw2", "connect = " + token);
        RongIM.connect(token, !BuildConfig.DEBUG ? null : new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("zzw", "融云登录onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("zzw", "融云登录onSuccess" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("zzw", "--融云登录onError" + errorCode);
            }
        });

    }

}
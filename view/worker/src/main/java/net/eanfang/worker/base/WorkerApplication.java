package net.eanfang.worker.base;

import android.util.Log;

import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.http.EanfangHttp;
import com.mob.MobSDK;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.ui.activity.im.CustomizeMessage;
import net.eanfang.worker.ui.activity.im.CustomizeMessageItemProvider;
import net.eanfang.worker.ui.activity.im.CustomizeVideoMessage;
import net.eanfang.worker.ui.activity.im.CustomizeVideoMessageItemProvider;
import net.eanfang.worker.ui.activity.im.MyConversationClickListener;
import net.eanfang.worker.ui.activity.im.SampleExtensionModule;

import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class WorkerApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initRongIM();
        initHttp();
        initBugly();
    }

    private void initBugly() {
        SDKManager.getBugly().init(this, BuildConfig.BUGLY_WORKER, HttpConfig.get().isDebug());
    }

    @Override
    protected void initConfig() {
        super.initConfig();
        LoadKit.initLoadSir();
        CacheKit.init(this).put("APP_TYPE", BuildConfig.APP_TYPE);
        MobSDK.init(this, BuildConfig.MOB_WORKER_APPID, BuildConfig.MOB_WORKER_APPKEY);
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
                BuildConfig.DEBUG_MOD,
                BuildConfig.VERSION_CODE
        );

        EanfangHttp.setWorker();
        if (BaseApplication.get().getUser() != null) {
            EanfangHttp.setToken(BaseApplication.get().getLoginBean().getToken());
            HttpConfig.get().setToken(BaseApplication.get().getLoginBean().getToken());
        }
    }

    /**
     * 初始化融云
     */
    private void initRongIM() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
//            PushConfig config = new PushConfig.Builder()
//                    .enableMiPush(XIAOMI_APPID_WORKER, XIAOMI_APPKEY_WORKER)
//                    .enableMeiZuPush(MEIZU_APPID_WORKER, MEIZU_APPKEY_WORKER)
//                    .build();
//            RongPushClient.setPushConfig(config);
            RongIM.init(this);
            RongExtensionManager.getInstance().registerExtensionModule(new SampleExtensionModule());
            RongIM.setConversationClickListener(new MyConversationClickListener());

            RongIM.registerMessageType(CustomizeMessage.class);
            RongIM.registerMessageType(CustomizeVideoMessage.class);

            RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());
            RongIM.registerMessageTemplate(new CustomizeVideoMessageItemProvider());

            Conversation.ConversationType[] types = new Conversation.ConversationType[]{
                    Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP,
                    Conversation.ConversationType.DISCUSSION
            };
            RongIM.getInstance().setReadReceiptConversationTypeList(types);

        }
    }

    /**
     * 获取融云token
     *
     * @param token
     */
    public static void connect(String token) {

        RongIM.connect(token, !BuildConfig.DEBUG_MOD ? null : new RongIMClient.ConnectCallback() {

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

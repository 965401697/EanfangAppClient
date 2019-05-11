package net.eanfang.client.ui.base;

import android.app.Activity;
import android.util.Log;

import com.eanfang.application.EanfangApplication;
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

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by O u r on 2018/5/7.
 */

public class ClientApplication extends EanfangApplication {
    private static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);

//            RongIM.registerMessageType(SightMessage.class);
//            RongIM.registerMessageTemplate(new SightMessageItemProvider());

            RongExtensionManager.getInstance().registerExtensionModule(new SampleExtensionModule());
            RongIM.setConversationClickListener(new MyConversationClickListener());

            RongIM.registerMessageType(CustomizeMessage.class);
            RongIM.registerMessageTemplate(new CustomizeMessageItemProvider());

            RongIM.registerMessageType(CustomizeVideoMessage.class);
            RongIM.registerMessageTemplate(new CustomizeVideoMessageItemProvider());

            //初始化微信支付
            api = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);

            api.registerApp(EanfangConst.WX_APPID_CLIENT);

            HttpConfig.init(com.eanfang.BuildConfig.API_HOST, BuildConfig.APP_TYPE, com.eanfang.BuildConfig.OSS_ENDPOINT, com.eanfang.BuildConfig.OSS_BUCKET);
            LoadKit.initLoadSir();
            CacheKit.init(this).put("APP_TYPE", BuildConfig.APP_TYPE);
            EanfangHttp.setClient();
            if (EanfangApplication.get().getUser() != null) {
                EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
                HttpConfig.get().setToken(EanfangApplication.get().getUser().getToken());
            }

            MobSDK.init(this, "22bb8de378eab", "f93cc21381c6f51702be823efde3e402");
        }


    }

    public static IWXAPI getWxApi() {
        return api;
    }

    /**
     * 融云的token
     *
     * @param token
     */
    public static void connect(String token, Activity activity) {

        Log.e("zzw2", "connect = " + token);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("zzw", "融云登录onTokenIncorrect");

//                EanfangHttp.post(UserApi.POST_RONGY_TOKEN)
//                        .params("userId", EanfangApplication.get().getAccId())
//                        .execute(new EanfangCallback<String>(activity, false, String.class, (str) -> {
//                            if (!TextUtils.isEmpty(str)) {
//                                JSONObject json = JSONObject.parseObject(str);
//                                String t = json.getString("token");
//                                EanfangApplication.get().set(EanfangConst.RONG_YUN_TOKEN, t);
//                                ClientApplication.connect(t, activity);
//                            }
//                        }));
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

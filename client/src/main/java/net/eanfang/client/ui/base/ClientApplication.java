package net.eanfang.client.ui.base;

import android.util.Log;

import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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

            RongExtensionManager.getInstance().registerExtensionModule(new SampleExtensionModule());
            RongIM.getInstance().setConversationClickListener(new MyConversationClickListener());

            //初始化微信支付
            api = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);}

    }

    public static IWXAPI getWxApi() {
        return api;
    }
    /**
     * 融云的token
     *
     * @param token
     */
    public static void connect(String token) {


        RongIM.connect(token, new RongIMClient.ConnectCallback() {

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

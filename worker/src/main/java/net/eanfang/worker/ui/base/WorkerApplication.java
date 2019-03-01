package net.eanfang.worker.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.eanfang.application.EanfangApplication;
import com.eanfang.util.SharePreferenceUtil;
import com.mob.MobSDK;

import net.eanfang.worker.BuildConfig;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.activity.im.CustomizeMessage;
import net.eanfang.worker.ui.activity.im.CustomizeMessageItemProvider;
import net.eanfang.worker.ui.activity.im.CustomizeVideoMessage;
import net.eanfang.worker.ui.activity.im.CustomizeVideoMessageItemProvider;
import net.eanfang.worker.ui.activity.im.MyConversationClickListener;
import net.eanfang.worker.ui.activity.im.SampleExtensionModule;

import java.io.IOException;

import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by O u r on 2018/4/15.
 */

public class WorkerApplication extends EanfangApplication {


    public int count = 0;
    private ForwardListener mForwardListener;

    public void setmForwardListener(ForwardListener mForwardListener) {
        this.mForwardListener = mForwardListener;
    }

    private static WorkerApplication mWorkerApplication;

    public static WorkerApplication getApplication() {
        return mWorkerApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkerApplication = this;
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);


            RongExtensionManager.getInstance().registerExtensionModule(new SampleExtensionModule());
            RongIM.getInstance().setConversationClickListener(new MyConversationClickListener());

            RongIM.registerMessageType(CustomizeMessage.class);
            RongIM.registerMessageType(CustomizeVideoMessage.class);


            RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider());
            RongIM.getInstance().registerMessageTemplate(new CustomizeVideoMessageItemProvider());

            Conversation.ConversationType[] types = new Conversation.ConversationType[]{
                    Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP,
                    Conversation.ConversationType.DISCUSSION
            };
            RongIM.getInstance().setReadReceiptConversationTypeList(types);

            try {
                SharePreferenceUtil.get().set(BuildConfig.TYPE_APP, BuildConfig.TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MobSDK.init(this,"299cfb8d27500","91afc15795a7f3dc04b5cab818c097c9");
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                count++;
//                Log.e("zzw", "加=" + count);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (count == 1 && activity instanceof MainActivity) {
//                    Log.e("zzw", "MainActivity");
                    mForwardListener.onForwardListener();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {


            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                count--;
//                Log.e("zzw", "减=" + count);

            }
        });


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

    public interface ForwardListener {
        void onForwardListener();
    }

}

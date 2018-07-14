package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.util.Log;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/13$  18:18$
 */
public class MeiZuReceiver extends MzPushMessageReceiver {
    @Override
    public void onRegister(Context context, String s) {
        Log.e("meizu", "魅族注册onRegister" + s);
    }

    @Override
    public void onMessage(Context context, String s) {
        Log.e("meizu", "魅族注册onMessage" + s);
    }

    @Override
    public void onUnRegister(Context context, boolean b) {
        Log.e("meizu", "魅族注册onUnRegister" + b);
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        Log.e("meizu", "魅族注册onPushStatus" + pushSwitchStatus.getMessage().toString());
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {

    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {

    }
}

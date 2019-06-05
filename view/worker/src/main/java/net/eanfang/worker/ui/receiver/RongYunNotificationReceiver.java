package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.eanfang.worker.ui.activity.MainActivity;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by O u r on 2018/4/15.
 */

public class RongYunNotificationReceiver extends PushMessageReceiver {


    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
        Log.e("GG", "onNotificationMessageArrived" + pushNotificationMessage.getPushContent().toString());
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        Log.e("GG", "onNotificationMessageClicked=" + pushNotificationMessage.getPushContent().toString());
        if (MainActivity.hashMap.get(pushNotificationMessage.getTargetId()) != null) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.hashMap.remove(pushNotificationMessage.getTargetId());
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 第三方push状态回调
     *
     * @param pushType   push类型
     * @param action     当前的操作，连接或者获取token
     * @param resultCode 返回的错误码
     */
    @Override
    public void onThirdPartyPushState(PushType pushType, String action, long resultCode) {
        super.onThirdPartyPushState(pushType, action, resultCode);
        Log.e("GG", "融云 onThirdPartyPushState resultCode:" + resultCode + "pushType:" + pushType.getName());
        /**
         * 处理华为push连接错误，由终端用户操作解决.
         * 当错误码为以下错误码才能通过终端用户操作解决:
         * {@link com.huawei.hms.api.ConnectionResult#SERVICE_MISSING}
         * {@link com.huawei.hms.api.ConnectionResult#SERVICE_VERSION_UPDATE_REQUIRED}
         * {@link com.huawei.hms.api.ConnectionResult#BINDFAIL_RESOLUTION_REQUIRED}
         *
         * @param activity  当前界面的activity， 不能传空
         * @param errorCode 错误码
         *
         * public static void resolveHWPushError(Activity activity, long errorCode) throws RongException {}
         */

    }
}
package net.eanfang.client.ui.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.eanfang.client.ui.activity.MainActivity;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by O u r on 2018/4/15.
 */

public class RongYunNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        Log.e("zzw", "onNotificationMessageArrived" + message.getPushContent().toString());
        return false;// 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        if (message.getPushContent().equals("好友邀请") || message.getPushContent().equals("被删除通知") || message.getPushContent().equals("被移除群组通知")) {

            context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        return false;// 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }
}
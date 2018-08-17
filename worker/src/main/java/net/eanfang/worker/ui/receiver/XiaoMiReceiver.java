package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.util.Log;

import com.eanfang.ui.base.voice.SynthesizerPresenter;
import com.eanfang.util.StringUtils;
import com.eanfang.util.Var;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/13$  15:21$
 */
public class XiaoMiReceiver extends PushMessageReceiver {


    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
        Log.e("xiaomi", "onReceivePassThroughMessage" + miPushMessage.getContent());
    }


    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
        Log.e("xiaomi", "onNotificationMessageClicked" + miPushMessage.getContent());
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        Log.e("xiaomi", "onNotificationMessageArrived" + miPushMessage.getContent());

        Var.get("MainActivity.initMessageCount").setVar(Var.get("MainActivity.initMessageCount").getVar() + 1);
        Var.get("ContactListFragment.messageCount").setVar(Var.get("ContactListFragment.messageCount").getVar() + 1);
        System.err.println("---------------------extra:" + miPushMessage.getExtra().get("audio"));
        if (!StringUtils.isEmpty(miPushMessage.getExtra().get("audio"))) {
            SynthesizerPresenter.getInstance().start(miPushMessage.getExtra().get("audio"));
        }

    }

    @Override
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceiveMessage(context, miPushMessage);
        Log.e("xiaomi", "onReceiveMessage" + miPushMessage.getContent());
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
        Log.e("xiaomi", "onReceiveRegisterResult" + miPushCommandMessage.getCommand());
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
        Log.e("xiaomi", "onCommandResult" + miPushCommandMessage.getCommand());
    }

}

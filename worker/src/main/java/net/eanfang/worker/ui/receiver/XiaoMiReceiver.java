package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.ui.base.voice.SynthesizerPresenter;
import com.eanfang.util.StringUtils;
import com.xiaomi.mipush.sdk.MessageHandleService;
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

        System.err.println("---------------------extra:" + miPushMessage.getExtra().get("audio"));
        if (!StringUtils.isEmpty(miPushMessage.getExtra().get("audio"))) {
            SynthesizerPresenter.getInstance().initTts(miPushMessage.getExtra().get("audio"));
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

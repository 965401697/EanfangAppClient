package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camera.util.LogUtil;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.ui.base.voice.SynthesizerPresenter;
import com.eanfang.util.StringUtils;
import com.eanfang.util.Var;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by MrHou
 *
 * @on 2017/11/7  14:37
 * @email houzhongzhou@yeah.net
 * @desc 信鸽推送定义Receiver
 */

public class MessageReceiver extends XGPushBaseReceiver {
    public static final String LogTag = "TPushReceiver";
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");


    private void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        Var.get("MainActivity.initMessageCount").setVar(Var.get("MainActivity.initMessageCount").getVar() + 1);
        Var.get("ContactListFragment.messageCount").setVar(Var.get("ContactListFragment.messageCount").getVar() + 1);

        JSONObject jsonObject = JSON.parseObject(notifiShowedRlt.getCustomContent());
        if (!StringUtils.isEmpty(jsonObject.toJSONString())) {
            System.err.println("---------------------jsonObject:" + jsonObject.toJSONString());
            if (jsonObject.containsKey("audio") && !StringUtils.isEmpty(jsonObject.getString("audio"))) {
                boolean isOpen = CacheKit.get().getBool("XGNoticeVoice", true);
                if (isOpen) {
                    SynthesizerPresenter.getInstance().start(jsonObject.getString("audio"));
                }
            }
        }
        LogUtil.e(LogTag, "onNotifactionShowedResult");
    }

    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        LogUtil.e(LogTag, "onUnregisterResult");
    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        LogUtil.e(LogTag, "onSetTagResult");
    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        LogUtil.e(LogTag, "onDeleteTagResult");

    }


    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
//        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
//            // 通知在通知栏被点击啦。。。。。
//            // APP自己处理点击的相关动作
//            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
//            Intent intent = new Intent(context, MessageListActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
        if (!TextUtils.isEmpty(message.getActivityName())) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            Intent intent = null;
            try {
                intent = new Intent(context, Class.forName("net.eanfang.worker." + message.getActivityName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        LogUtil.e(LogTag, "onRegisterResult");
    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        LogUtil.e(LogTag, "onTextMessage");
    }

}

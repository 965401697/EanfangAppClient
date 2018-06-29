package net.eanfang.worker.ui.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.camera.util.LogUtil;
import com.eanfang.application.EanfangApplication;
import com.eanfang.listener.MySynthesizerListener;
import com.eanfang.ui.base.SpeakTts;
import com.eanfang.util.Var;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.notice.MessageListActivity;

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

    private SpeechSynthesizer mTts;

    private void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        Var.get("MainActivity.initMessageCount").setVar(Var.get("MainActivity.initMessageCount").getVar() + 1);
        Var.get("ContactListFragment.messageCount").setVar(Var.get("ContactListFragment.messageCount").getVar() + 1);
        mTts = SpeakTts.getInstance().mTts();
        if (mTts == null) {
            SpeakTts.getInstance().initTts(EanfangApplication.getApplication().getApplicationContext(), new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        LogUtil.e("GG", "初始化失败，错误码：" + code);
                    }
                    mTts = SpeakTts.getInstance().mTts();
                }
            });
        }
        mTts.startSpeaking(notifiShowedRlt.getContent(), mTtsListener);
    }

    @Override
    public void onUnregisterResult(Context context, int errorCode) {
    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {

    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        if (context == null || message == null) {
            return;
        }
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            Intent intent = new Intent(context, MessageListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {

    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {

    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                mTts.destroy();
            } else if (error != null) {
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };
}

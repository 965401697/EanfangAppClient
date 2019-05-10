package net.eanfang.client.ui.receiver;

import android.content.Context;
import android.util.Log;

import com.eanfang.ui.base.voice.SynthesizerPresenter;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;


/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/13$  15:24$
 */
public class ReceiverInit {

    private volatile static ReceiverInit receiverInit;

    public static ReceiverInit getInstance() {
        if (receiverInit == null) {
            synchronized (SynthesizerPresenter.class) {
                if (receiverInit == null) {
                    receiverInit = new ReceiverInit();
                }
            }
        }
        return receiverInit;
    }

    public void inits(Context context, String telPhone) {

        //信鸽注册代码
        XGPushManager.registerPush(context, telPhone, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.e("TPush", "注册成功，设备token为：" + data);
                // Var.get("MainActivity.initXinGe").setVar(1);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                //Var.get("MainActivity.initXinGe").setVar(0);
            }
        });
    }

}

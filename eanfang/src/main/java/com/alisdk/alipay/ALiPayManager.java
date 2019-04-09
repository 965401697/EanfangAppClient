package com.alisdk.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * created by wtt
 * at 2019/4/9
 * summary:
 */
public class ALiPayManager implements IALiPay {
    private static final int SDK_PAY_FLAG = 1;
    private IALiPayCallBack mIALiPayCallBack;

    /**
     *
     * @param activity
     * @param orderInfo  app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。
     * @param isShowPayLoading
     * @param mIALiPayCallBack
     */
    @Override
    public void aLiPay(Activity activity, String orderInfo, boolean isShowPayLoading, IALiPayCallBack mIALiPayCallBack) {
        setmIALiPayCallBack(mIALiPayCallBack);
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(activity);
            Map<String, String> result = alipay.payV2(orderInfo, isShowPayLoading);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    payStatus(msg);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 支付宝状态
     * 9000 订单支付成功
     * 8000 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 4000 订单支付失败
     * 5000 重复请求
     * 6001 用户中途取消
     * 6002 网络连接出错
     * 6004 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 其它   其它支付错误
     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
     */
    private void payStatus(Message msg) {
        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
        // 同步返回需要验证的信息
        String resultInfo = payResult.getResult();
        String resultStatus = payResult.getResultStatus();

        switch (resultStatus) {
            case "9000":
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                if(getmIALiPayCallBack()!=null){
                    getmIALiPayCallBack().onSuccess();
                }
                break;
            case "6001":
                if(getmIALiPayCallBack()!=null){
                    getmIALiPayCallBack().onCancel();
                }
                break;
            default:
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                if(getmIALiPayCallBack()!=null){
                    getmIALiPayCallBack().onFail(resultInfo);
                }
                break;
        }
    }
    public IALiPayCallBack getmIALiPayCallBack() {
        return mIALiPayCallBack;
    }

    public void setmIALiPayCallBack(IALiPayCallBack mIALiPayCallBack) {
        this.mIALiPayCallBack = mIALiPayCallBack;
    }
}

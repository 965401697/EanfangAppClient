package com.alisdk.alipay;

import android.app.Activity;

/**
 * created by wtt
 * at 2019/4/9
 * summary:
 */
public class ALiPayProxy implements IALiPay {
    private static ALiPayProxy mALiPayProxy;
    private IALiPay mIALiPay;

    public static ALiPayProxy getInstance(IALiPay mIALiPay) {
        if (mALiPayProxy == null) {
            mALiPayProxy = new ALiPayProxy(mIALiPay);
        }
        return mALiPayProxy;
    }

    public ALiPayProxy(IALiPay mIALiPay) {
        this.mIALiPay = mIALiPay;
    }

    @Override
    public void aLiPay(Activity activity, String orderInfo, boolean isShowPayLoading,IALiPayCallBack mIALiPayCallBack) {
        mIALiPay.aLiPay(activity, orderInfo, isShowPayLoading,mIALiPayCallBack);
    }
}

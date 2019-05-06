package com.eanfang.kit.sdk.alisdk.alipay;

import android.app.Activity;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public interface IALiPay {
    void aLiPay(Activity activity, String orderInfo, boolean isShowPayLoading,IALiPayCallBack mIALiPayCallBack);
}

package com.tengxunsdk.wechatsdk;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public interface IWXPay {
    IWXPayAPI createWXAPI(Context context, String var1);

    boolean registerApp(IWXPayAPI mIWXAPI, String appid);

    void wxPay(IWXPayAPI mIWXAPI, String appid, String partnerid, String prepayid, String packageValue, String noncestr, String timestamp, String sign);
}

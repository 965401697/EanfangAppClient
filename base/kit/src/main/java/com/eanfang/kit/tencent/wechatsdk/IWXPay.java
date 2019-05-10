package com.eanfang.kit.tencent.wechatsdk;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public interface IWXPay {
    void init(Context context, String appid);

    IWXAPI createWXAPI(Context context, String var1);

    boolean registerApp(IWXAPI mIWXAPI, String appid);

    void wxPay(IWXAPI mIWXAPI, String appid, String partnerid, String prepayid, String noncestr, String timestamp, String sign);

    void wxPay(IWXAPI mIWXAPI,String appid,String sign);
}

package com.eanfang.kit.sdk.tengxunsdk.wechatsdk;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public class WXPayManager implements IWXPay {
    @Override
    public void init(Context context, String appid) {
        this.registerApp(this.createWXAPI(context, appid), appid);
    }

    @Override
    public IWXAPI createWXAPI(Context context, String appid) {
        return  WXAPIFactory.createWXAPI(context, appid);
    }

    @Override
    public boolean registerApp(IWXAPI mIWXAPI, String appid) {
        return mIWXAPI.registerApp(appid);
    }

    @Override
    public void wxPay(IWXAPI mIWXAPI, String appid, String partnerid, String prepayid, String noncestr, String timestamp, String sign) {
        PayReq request = new PayReq();
        request.appId = appid;
        request.partnerId = partnerid;
        request.prepayId = prepayid;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = noncestr;
        request.timeStamp = timestamp;
        request.signType = "MD5";
        request.sign = sign;
        mIWXAPI.sendReq(request);
    }

    @Override
    public void wxPay(IWXAPI mIWXAPI, String appid, String sign) {
        PayReq request = new PayReq();
        request.appId = appid;
        request.sign = sign;
        mIWXAPI.sendReq(request);
    }
}

package com.tengxunsdk.wechatsdk;

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
    public IWXPayAPI createWXAPI(Context context, String appid) {
        return (IWXPayAPI) WXAPIFactory.createWXAPI(context, appid);
    }

    @Override
    public boolean registerApp(IWXPayAPI mIWXAPI, String appid) {
        return mIWXAPI.registerApp(appid);
    }

    @Override
    public void wxPay(IWXPayAPI mIWXAPI, String appid, String partnerid, String prepayid, String packageValue, String noncestr, String timestamp, String sign) {
        PayReq request = new PayReq();
        request.appId = appid;
        request.partnerId = partnerid;
        request.prepayId = prepayid;
        request.packageValue = prepayid;
        request.nonceStr = noncestr;
        request.timeStamp = timestamp;
        request.sign = sign;
        mIWXAPI.sendReq(request);
    }
}

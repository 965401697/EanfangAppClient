package com.tengxunsdk.wechatsdk;

import android.content.Context;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public class WXPayProxy implements IWXPay {
    private Context context;
    private static WXPayProxy mWXPayProxy;
    private IWXPay mIWXPay;

    public static WXPayProxy getInstance(Context context, IWXPay mIWXPay) {
        if (mWXPayProxy == null) {
            mWXPayProxy = new WXPayProxy(context, mIWXPay);
        }
        return mWXPayProxy;
    }

    private WXPayProxy(Context context, IWXPay mIWXPay) {
        this.mIWXPay = mIWXPay;
        this.context = context.getApplicationContext();
    }

    public void init(String appid) {
        this.registerApp(this.createWXAPI(context, null), appid);
    }

    @Override
    public IWXPayAPI createWXAPI(Context context, String var1) {
        return mIWXPay.createWXAPI(context, var1);
    }

    @Override
    public boolean registerApp(IWXPayAPI mIWXAPI, String appid) {
        return mIWXPay.registerApp(mIWXAPI, appid);
    }

    @Override
    public void wxPay(IWXPayAPI mIWXAPI, String appid, String partnerid, String prepayid, String packageValue, String noncestr, String timestamp, String sign) {
        mIWXPay.wxPay(mIWXAPI, appid, partnerid, prepayid, packageValue, noncestr, timestamp, sign);
    }
}

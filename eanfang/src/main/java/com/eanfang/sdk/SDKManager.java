package com.eanfang.sdk;

import android.content.Context;

import com.eanfang.sdk.alisdk.alioss.IOSSStorage;
import com.eanfang.sdk.alisdk.alioss.OSSStorage;
import com.eanfang.sdk.alisdk.alipay.ALiPayManager;
import com.eanfang.sdk.alisdk.alipay.IALiPay;
import com.eanfang.sdk.tengxunsdk.bugly.BuglyManager;
import com.eanfang.sdk.tengxunsdk.bugly.IBugly;
import com.eanfang.sdk.tengxunsdk.wechatsdk.IWXPay;
import com.eanfang.sdk.tengxunsdk.wechatsdk.WXPayManager;
import com.eanfang.sdk.tengxunsdk.xingepush.IXGPush;
import com.eanfang.sdk.tengxunsdk.xingepush.XGPushManagerConfig;

/**
 * 第三方sdk的统一入口
 *
 * @author jornl
 * @date 2019-04-10
 */
public class SDKManager {
    private static IBugly BUGLY;
    private static IWXPay WXPay;
    private static IXGPush XGPUSH;
    private static IALiPay ALPAY;
    private static IOSSStorage OSSSTORAGE;

    public static IBugly getBugly() {
        if (BUGLY == null) {
            BUGLY = new BuglyManager();
        }
        return BUGLY;
    }

    public static IWXPay getWXPay() {
        if (WXPay == null) {
            WXPay = new WXPayManager();
        }
        return WXPay;
    }

    public static IXGPush getXGPush(Context context) {
        if (XGPUSH == null) {
            XGPUSH = new XGPushManagerConfig(context.getApplicationContext());
        }
        return XGPUSH;
    }

    public static IALiPay getALipay() {
        if (ALPAY == null) {
            ALPAY = new ALiPayManager();
        }
        return ALPAY;
    }

    public static IOSSStorage getIOSS() {
        if (OSSSTORAGE == null) {
            OSSSTORAGE = new OSSStorage();
        }
        return OSSSTORAGE;
    }
}

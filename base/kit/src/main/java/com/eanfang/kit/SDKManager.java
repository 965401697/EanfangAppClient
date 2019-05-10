package com.eanfang.kit;

import android.content.Context;

import com.eanfang.kit.ali.alioss.IOSSStorage;
import com.eanfang.kit.ali.alioss.OSSStorage;
import com.eanfang.kit.ali.alipay.ALiPayManager;
import com.eanfang.kit.ali.alipay.IALiPay;
import com.eanfang.kit.tencent.bugly.BuglyManager;
import com.eanfang.kit.tencent.bugly.IBugly;
import com.eanfang.kit.tencent.wechatsdk.IWXPay;
import com.eanfang.kit.tencent.wechatsdk.WXPayManager;
import com.eanfang.kit.tencent.xingepush.IXGPush;
import com.eanfang.kit.tencent.xingepush.XGPushManagerConfig;

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
//    private static IOSSStorage OSSSTORAGE;

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

    /**
     * oss令牌需要实时获取
     *
     * @return IOSSStorage
     */
    public static IOSSStorage getIOSS() {
        return new OSSStorage();
    }
}

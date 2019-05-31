package com.eanfang.base.kit;

import android.app.Activity;
import android.content.Context;

import com.eanfang.base.kit.ali.alioss.IOSSStorage;
import com.eanfang.base.kit.ali.alioss.OSSStorage;
import com.eanfang.base.kit.ali.alipay.ALiPayManager;
import com.eanfang.base.kit.ali.alipay.IALiPay;
import com.eanfang.base.kit.ali.oss.IOssService;
import com.eanfang.base.kit.ali.oss.OssKit;
import com.eanfang.base.kit.tencent.bugly.BuglyManager;
import com.eanfang.base.kit.tencent.bugly.IBugly;
import com.eanfang.base.kit.tencent.wechatsdk.IWXPay;
import com.eanfang.base.kit.tencent.wechatsdk.WXPayManager;
import com.eanfang.base.kit.tencent.xingepush.IXGPush;
import com.eanfang.base.kit.tencent.xingepush.XGPushManagerConfig;

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
     */
    public static IOssService ossKit(Activity activity) {
        return OssKit.get(activity);
    }
}

package com.eanfang.sdk;


import com.eanfang.sdk.tencent.bugly.Bugly;
import com.eanfang.sdk.tencent.bugly.IBugly;

/**
 * 第三方sdk的统一入口
 *
 * @author jornl
 * @date 2019-04-10
 */
public class SDKManager {
    private static IBugly BUGLY = new Bugly();

    public static IBugly getBugly() {
        if (BUGLY == null) {
            BUGLY = new Bugly();
        }
        return BUGLY;
    }
}

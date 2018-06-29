/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-2-10 上午9:41
 * Copyright (c) 2017. All rights reserved.
 */

package net.eanfang.client.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by mac on 17/1/4.
 */
public class AppRegister extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        final IWXAPI api = WXAPIFactory.createWXAPI(context, EanfangConst.WX_APPID_CLIENT,true);
//
//        // 注册api.registerApp(Constants.APP_ID);
//        api.registerApp(EanfangConst.WX_APPID_CLIENT);
    }
}

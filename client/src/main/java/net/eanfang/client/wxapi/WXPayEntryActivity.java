/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-2-10 上午9:41
 * Copyright (c) 2017. All rights reserved.
 */

package net.eanfang.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eanfang.config.EanfangConst;
import com.eanfang.ui.base.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.eanfang.client.ui.activity.pay.PayActivity;


/**
 * Created by mac on 16/12/8.
 * 微信支付
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e("onReq", "onReqonReqonReqonReq");
    }

    @Override
    public void onResp(BaseResp baseResp) {

        Log.e("onResp", "onResponResp");
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                    for (Activity a : transactionActivities) {
                        if (a instanceof PayActivity) {
                            ((PayActivity) a).finishSelf();
                        }
                    }
                    finish();
                    break;
                case -1:
                    Toast.makeText(this, "支付失败！！！", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this, "支付已取消", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
//            Intent intent = new Intent(EanfangConst.ACTION_WX_PAY_SUCCESS);
//            Bundle bundle = new Bundle();
//            bundle.putInt("weChatPaymentCode", baseResp.errCode);
//            intent.putExtras(bundle);
//            sendBroadcast(intent);
//            finish();


        } else {
            finish();
        }
//        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("支付结果");
//            builder.setMessage("支付结果：" + String.valueOf(baseResp.errCode));
//            builder.show();
//            WxPaymentUtils.weChatPaymentCode = baseResp.errCode;
//            Dlog.e("onPayFinish, weChatPaymentCode = " + WxPaymentUtils.weChatPaymentCode);


//            Intent intent = new Intent("Success");
//            Bundle bundle = new Bundle();
//            bundle.putInt("weChatPaymentCode", baseResp.errCode);
//            intent.putExtras(bundle);
//            sendBroadcast(intent);
//            finish();
//        }
    }
}

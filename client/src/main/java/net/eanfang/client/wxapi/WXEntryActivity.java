/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-2-10 上午9:41
 * Copyright (c) 2017. All rights reserved.
 */

package net.eanfang.client.wxapi;

import android.app.Activity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }

//    private IWXAPI iwxapi;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        iwxapi = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);
//        iwxapi.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        iwxapi.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//
//        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////            builder.setTitle("支付结果");
////            builder.setMessage("支付结果：" + String.valueOf(baseResp.errCode));
////            builder.show();
////            WxPaymentUtils.weChatPaymentCode = baseResp.errCode;
////            Dlog.e("onPayFinish, weChatPaymentCode = " + WxPaymentUtils.weChatPaymentCode);
//
//
//            Intent intent = new Intent("Success");
//            Bundle bundle = new Bundle();
//            bundle.putInt("weChatPaymentCode", baseResp.errCode);
//            intent.putExtras(bundle);
//            sendBroadcast(intent);
//            finish();
//        }
//    }
}
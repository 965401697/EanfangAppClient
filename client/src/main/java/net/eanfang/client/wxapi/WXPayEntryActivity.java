package net.eanfang.client.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.camera.util.ToastUtil;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "微信支付";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e("onReq", "onReqonReqonReqonReq");
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("onResp", "onResponResp");

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(EanfangConst.ACTION_WX_PAY_SUCCESS);
            Bundle bundle = new Bundle();
            bundle.putInt("weChatPaymentCode", resp.errCode);
            intent.putExtras(bundle);
            sendBroadcast(intent);
            finish();
        } else {
            finish();
        }
    }
}
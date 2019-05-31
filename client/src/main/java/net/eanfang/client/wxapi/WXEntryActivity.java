/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-2-10 上午9:41
 * Copyright (c) 2017. All rights reserved.
 */

package net.eanfang.client.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.eanfang.config.EanfangConst;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import cn.sharesdk.wechat.utils.WechatHandlerActivity;


public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    public static final String EXTRA_FLAG = "flag";
    public static final String EXTRA_URL = "url";

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, EanfangConst.WX_APPID_CLIENT);
        iwxapi.registerApp(EanfangConst.WX_APPID_CLIENT);
        iwxapi.handleIntent(getIntent(), this);
        int flag = getIntent().getIntExtra(EXTRA_FLAG, 0);
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        share(flag);
    }

    private void share(int type) {
        WXTextObject textObject = new WXTextObject();
        //内容
        textObject.text = "我发现了一个可以在线报修接单的app，快来注册试试吧 【" + mUrl + "】";
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        //描述
        msg.description = "我发现了一个可以在线报修接单的app，快来注册试试吧";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
//                startActivity(new Intent(WXEntryActivity.this,MainActivity.class));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        finish();
    }
}
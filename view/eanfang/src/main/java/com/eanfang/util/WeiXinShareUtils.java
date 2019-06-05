package com.eanfang.util;

import android.content.Context;

import com.eanfang.config.EanfangConst;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * @author liangkailun
 * Date ：2019-05-28
 * Describe :分享到微信和朋友圈的帮助类
 */
public class WeiXinShareUtils {

    private WeiXinShareUtils() {
    }

    private static WeiXinShareUtils instance;
    private IWXAPI api;
    private SendMessageToWX.Req req;

    public static WeiXinShareUtils getInstance() {
        synchronized (WeiXinShareUtils.class) {
            if (instance == null) {
                instance = new WeiXinShareUtils();
            }
        }
        return instance;
    }

    /**
     * 初始化分享功能
     *
     * @param context
     */
    public void init(Context context, String url) {
        api = WXAPIFactory.createWXAPI(context, EanfangConst.WX_APPID_CLIENT, true);
        //将应用appid注册到微信
        api.registerApp(EanfangConst.WX_APPID_CLIENT);
        WXTextObject textObject = new WXTextObject();
        //内容
        textObject.text = "我发现了一个可以在线报修接单的app，快来注册试试吧 【" + url + "】";
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        //描述
        msg.description = "我发现了一个可以在线报修接单的app，快来注册试试吧";
        req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;

    }

    /**
     * 分享消息到微信
     * type 0:分享到微信
     * type 1:分享到朋友圈
     */
    public void sendToWeiXin(int type) {
        if (type == 1) {
            //分享到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        api.sendReq(req);
    }
}

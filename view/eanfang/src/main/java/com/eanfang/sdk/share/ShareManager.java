package com.eanfang.sdk.share;

import android.content.Context;

import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class ShareManager implements IShare {
    private String title;
    private String content;
    private String url;
    /**
     * 分享到微信Wechat.NAME
     * 分享到微信朋友圈WechatMoments.NAME
     */
    private String name=Wechat.NAME;
    /**
     * 分享网页Platform.SHARE_WEBPAGE
     * 分享图片Platform.SHARE_IMAGE
     */
    private int shareType=Platform.SHARE_WEBPAGE;
    private static ShareManager shareManager;
    public static ShareManager getInstance(){
        if(shareManager==null){
            shareManager=new ShareManager();
        }
        return shareManager;
    }
    @Override
    public void init(Context context, String appKey, String appSecret) {
        MobSDK.init(context,appKey,appSecret);
    }

    @Override
    public IShare setTitle(String title) {
        this.title=title;
        return this;
    }

    @Override
    public IShare setText(String content) {
        this.content=content;
        return this;
    }

    @Override
    public IShare setUrl(String url) {
        this.url=url;
        return this;
    }

    @Override
    public IShare setShareType(int shareType) {
        this.shareType=shareType;
        return this;
    }

    @Override
    public IShare setPlatform(String name) {
        this.name=name;
        return this;
    }

    @Override
    public void wechatShare(PlatformActionListener l) {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        //微信分享网页的参数严格对照列表中微信分享网页的参数要求
        sp.setTitle(title);
        sp.setText(content);
        sp.setUrl(url);
        sp.setShareType(shareType);
        Platform wechat = ShareSDK.getPlatform(name);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        wechat.setPlatformActionListener(l);
        // 执行图文分享
        wechat.share(sp);
    }
}

package com.eanfang.sdk.share;

import android.content.Context;
import android.widget.Toast;

import com.mob.MobSDK;
import com.mob.moblink.ActionListener;
import com.mob.moblink.MobLink;
import com.mob.moblink.Scene;

import java.util.HashMap;

import cn.hutool.core.util.StrUtil;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class ShareManager implements IShare {
    private String title;
    private String content;
    private String url;
    /**
     * 不可为空；路径；一般用于不同界面间做路由选择
     */
    private String path;
    /**
     * 不可为空；来源标识；可用于在场景还原时辨别来源
     */
    private String source;
    /**
     * 可以为空；额外参数；在场景还原时能够重新得到；
     */
    private HashMap<String, Object> hashMap;
    /**
     * 分享到微信Wechat.NAME
     * 分享到微信朋友圈WechatMoments.NAME
     */
    private String name = Wechat.NAME;
    /**
     * 分享网页Platform.SHARE_WEBPAGE
     * 分享图片Platform.SHARE_IMAGE
     */
    private int shareType = Platform.SHARE_WEBPAGE;
    private static ShareManager shareManager;

    public static ShareManager getInstance() {
        if (shareManager == null) {
            shareManager = new ShareManager();
        }
        return shareManager;
    }

    @Override
    public void init(Context context, String appKey, String appSecret) {
        MobSDK.init(context, appKey, appSecret);
    }

    @Override
    public IShare setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public IShare setSource(String source) {
        this.source = source;
        return this;
    }

    @Override
    public IShare setParams(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
        return this;
    }

    @Override
    public IShare setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IShare setText(String content) {
        this.content = content;
        return this;
    }

    @Override
    public IShare setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IShare setShareType(int shareType) {
        this.shareType = shareType;
        return this;
    }

    @Override
    public IShare setPlatform(String name) {
        this.name = name;
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
    @Override
    public IShare getMobID() {
        if( StrUtil.isEmpty(path)||StrUtil.hasEmpty(source)){
            //请设置path/source
            return this;
        }
        //获取MobID
        Scene s = new Scene();
        s.path = path;
        s.source = source;
        s.params = hashMap;
        MobLink.getMobID(s, new ActionListener<String>() {
            @Override
            public void onResult(String mobID) {
                url=url+"/"+mobID;
//              Toast.makeText(context, "Get MobID Successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
//              Toast.makeText(context, "Get MobID Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return this;
    }
}

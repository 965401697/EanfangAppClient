package com.eanfang.sdk.share;

import android.content.Context;

import cn.sharesdk.framework.PlatformActionListener;

public interface IShare {
    void init(Context context,String appKey, String appSecret );
    IShare setTitle(String title);
    IShare setText(String content);
    IShare setUrl(String url);
    IShare setShareType(int shareType);
    IShare setPlatform(String  name);
    void wechatShare(PlatformActionListener l);
}

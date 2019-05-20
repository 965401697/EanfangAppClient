package com.eanfang.sdk.share;

import android.content.Context;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformActionListener;

public interface IShare {
    void init(Context context,String appKey, String appSecret );
    IShare setPath(String path);
    IShare setSource(String source);
    IShare setParams(HashMap<String, Object> hashMap);
    IShare setTitle(String title);
    IShare setText(String content);
    IShare setUrl(String url);
    IShare setShareType(int shareType);
    IShare setPlatform(String  name);
    IShare getMobID();
    void wechatShare(PlatformActionListener l);
}

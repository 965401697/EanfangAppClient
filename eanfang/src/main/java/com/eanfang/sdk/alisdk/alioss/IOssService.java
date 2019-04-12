package com.eanfang.sdk.alisdk.alioss;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSS;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 */
public interface IOssService {
    OSS init(Context context);

    void asyncPutFile(String objectKey,String filePath,IOSSCallBack mIOSSCallBack);
    void asyncPutImage(String objectKey,String filePath,IOSSCallBack mIOSSCallBack);
    void asyncPutImages();
    void asyncPutVideo(String objectKey,String filePath,IOSSCallBack mIOSSCallBack);

}

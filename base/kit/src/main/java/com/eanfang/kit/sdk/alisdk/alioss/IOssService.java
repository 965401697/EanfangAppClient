package com.eanfang.kit.sdk.alisdk.alioss;

import java.util.Map;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 */
public interface IOssService {
    /**
     * 上传单张图片
     */
  void  asyncPutImage(String objectKey, String urlPath, OSSCallBack ossCallBack);
    /**
     * 上传多张图片
     */
    void asyncPutImages(final Map<String, String> objectMap, final OSSCallBack callBack);
    /**
     * 上传视频
     */
    void asyncPutVideo(String objectKey, String videoPath, OSSCallBack ossCallBack);
}

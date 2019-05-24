package com.eanfang.base.kit.ali.oss;

import com.annimon.stream.function.Consumer;

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
    void asyncPutImage(String objectKey, String urlPath, Consumer<Boolean> consumer);

    /**
     * 上传多张图片
     */
    void asyncPutImages(Map<String, String> objectMap, Consumer<Boolean> consumer);

    /**
     * 上传视频
     */
    void asyncPutVideo(String objectKey, String videoPath, Consumer<Boolean> consumer);

    /**
     * 上传多个视频
     *
     * @param objectMap objectMap
     * @param consumer  consumer
     */
    void asyncPutVideos(Map<String, String> objectMap, Consumer<Boolean> consumer);
}

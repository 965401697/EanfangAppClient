package com.eanfang.base.kit.ali.oss;

import com.annimon.stream.function.Consumer;

import java.util.Map;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 *
 * @author jornl
 */
public interface IOssService {

    /**
     * 上传单张图片
     *
     * @param objectKey objectKey
     * @param urlPath   urlPath
     * @param consumer  consumer
     */
    void asyncPutImage(String objectKey, String urlPath, Consumer<Boolean> consumer);

    /**
     * 上传多张图片
     *
     * @param objectMap objectMap
     * @param consumer  consumer
     */
    void asyncPutImages(Map<String, String> objectMap, Consumer<Boolean> consumer);

    /**
     * 上传视频
     *
     * @param objectKey objectKey
     * @param videoPath videoPath
     * @param consumer  consumer
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

package com.eanfang.util;

import com.eanfang.BuildConfig;
import com.photopicker.com.widget.BGASortableNinePhotoLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 照片相关的 工具类
 * Created by jornl on 2017/10/11.
 */

public class PhotoUtils {

    /**
     * 获取 BGA控件中的 图片信息
     *
     * @param mPhotosSnpl
     * @param uploadMap
     * @param clean       是否清除map 重新计算
     * @return
     */
    public static List<String> getPhotoUrl(BGASortableNinePhotoLayout mPhotosSnpl, Map<String, String> uploadMap, boolean clean) {
        if (uploadMap == null) {
            uploadMap = new HashMap<>();
        }
        if (clean) {
            uploadMap.clear();
        }
        List<String> urls = new ArrayList<>();

        for (int i = 0; i < mPhotosSnpl.getData().size(); i++) {
            String path = mPhotosSnpl.getData().get(i);
            String object = UuidUtil.getUUID() + ".png";
            if (path == null || path.length() <= 0) {
                continue;
            } else if (path.startsWith("http")) {
                urls.add(path);
                continue;
            }
            uploadMap.put(object, path);
            urls.add(BuildConfig.OSS_SERVER + object);
        }
        for (int i = 0; i < 4 - mPhotosSnpl.getData().size(); i++) {
            urls.add("");
        }
        return urls;
    }
}

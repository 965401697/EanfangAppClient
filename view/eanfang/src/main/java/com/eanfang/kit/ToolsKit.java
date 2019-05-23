package com.eanfang.kit;

import android.net.Uri;

import com.eanfang.BuildConfig;

import cn.hutool.core.util.StrUtil;

/**
 * 系统中常用的小工具方法 集合
 *
 * @author jornl
 * @date 2019年5月17日 13:51:25
 */
public class ToolsKit {

    /**
     * 通过文件key 获取对应的oss路径
     *
     * @param key key
     * @return String
     */
    public static String getOssPath(String key) {
        if (StrUtil.isEmpty(key)) {
            return null;
        }
        return BuildConfig.OSS_SERVER + key;
    }

    /**
     * 通过文件key 获取对应的oss  uri
     *
     * @param key key
     * @return Uri
     */
    public static Uri getOssUri(String key) {
        String path = getOssPath(key);
        if (path != null) {
            return Uri.parse(path);
        }
        return null;
    }
}

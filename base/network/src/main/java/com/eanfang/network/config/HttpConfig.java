package com.eanfang.network.config;

import com.eanfang.network.converter.FastJsonDiskConverter;
import com.eanfang.network.exception.base.BaseException;
import com.zchu.rxcache.RxCache;

import java.io.File;

import lombok.Getter;
import lombok.Setter;


@Getter
public class HttpConfig {
    private String apiUrl = "";
    private String requestFrom = "";
    private String ossEndpoint = "";
    private String ossBucket = "";
    private String cachePath = "";
    private boolean isDebug = true;
    private int versionCode;
    @Setter
    private String token;
    /**
     * gzip压缩 默认开启
     */
    @Setter
    private boolean isGzip = false;

    private int app;

    private static HttpConfig config;

    private HttpConfig(String cachePath) {
        initRxCache(cachePath);
    }

    /**
     * 初始化config方法
     *
     * @param api  api地址
     * @param from 请求来源
     */
    public static void init(String api, String from, String ossEndpoint, String ossBucket, String cachePath, boolean isDebug, int versionCode) {
        config = new HttpConfig(cachePath);
        config.apiUrl = api;
        config.requestFrom = from.toUpperCase();
        config.ossEndpoint = ossEndpoint;
        config.ossBucket = ossBucket;
        config.cachePath = cachePath;
        config.isDebug = isDebug;
        config.versionCode = versionCode;
    }

    public static HttpConfig get() {
        if (config == null) {
            throw new BaseException(HttpCode.CODE_UNKNOWN, "please initialize the HttpConfig");
        }
        return config;
    }

    /**
     * @return 客户端0，技师端1
     */
    public int getApp() {
        if (requestFrom.toUpperCase().equals("CLIENT")) {
            return app = 0;
        } else if (requestFrom.toUpperCase().equals("WORKER")) {
            return app = 1;
        }
        return app = -1;
    }

    /**
     * 初始化RxCache
     *
     * @param cachePath cachePath
     */
    private void initRxCache(String cachePath) {
        RxCache.initializeDefault(new RxCache.Builder()
                .appVersion(versionCode)
                .diskDir(new File(cachePath + File.separator + "data-cache"))
                .diskConverter(new FastJsonDiskConverter())
                //50MB
                .diskMax((50 * 1024 * 1024L))
                //50MB
                .memoryMax(50 * 1024 * 1024)
                .setDebug(isDebug)
                .build());
    }
}

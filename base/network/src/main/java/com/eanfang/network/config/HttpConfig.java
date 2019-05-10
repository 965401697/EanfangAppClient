package com.eanfang.network.config;

import com.eanfang.network.exception.base.BaseException;

import lombok.Getter;
import lombok.Setter;


@Getter
public class HttpConfig {
    private String apiUrl = "";
    private String requestFrom = "";

    private String ossEndpoint = "";
    private String ossBucket = "";

    @Setter
    private String token;
    /**
     * gzip压缩 默认开启
     */
    @Setter
    private boolean isGzip = false;

    private int app;

    private static HttpConfig config;

    private HttpConfig() {
    }

    /**
     * 初始化config方法
     *
     * @param api  api地址
     * @param from 请求来源
     */
    public static void init(String api, String from, String ossEndpoint, String ossBucket) {
        config = new HttpConfig();
        config.apiUrl = api;
        config.requestFrom = from.toUpperCase();
        config.ossEndpoint = ossEndpoint;
        config.ossBucket = ossBucket;

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
}

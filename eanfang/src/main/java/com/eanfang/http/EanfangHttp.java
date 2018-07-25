package com.eanfang.http;


import com.eanfang.BuildConfig;
import com.eanfang.application.EanfangApplication;
import com.eanfang.util.StringUtils;
import com.okgo.OkGo;
import com.okgo.request.GetRequest;
import com.okgo.request.PostRequest;

/**
 * Created by jornl on 2017/8/30.
 */

public class EanfangHttp {
    private static OkGo http;

    private EanfangHttp() {
    }

    /**
     * get请求，EanfangStringCallback类型回调
     *
     * @param url
     * @return
     */
    public static GetRequest get(String url) {
        return getHttp().get(url);
    }

    /**
     * post请求，EanfangStringCallback类型回调
     *
     * @param url
     * @return
     */
    public static PostRequest post(String url) {
        return getHttp().post(url);
    }

    public static OkGo getHttp() {
        if (http == null) {
            synchronized (http) {
                if (http == null) {
                    EanfangApplication.get().initOkGo();
                }
            }
        }

        return http;
    }

    public static void setHttp(OkGo http) {
        EanfangHttp.http = http;
    }

    public static OkGo setClient() {
        getHttp().getCommonHeaders().put("Request-From", "CLIENT");
        return getHttp();
    }

    public static OkGo setWorker() {
        getHttp().getCommonHeaders().put("Request-From", "WORKER");
        return getHttp();
    }

    public static OkGo setToken(String token) {
        if (StringUtils.isEmpty(token)) {
            getHttp().getCommonHeaders().remove("YAF-Token");
        } else {
            getHttp().getCommonHeaders().put("YAF-Token", token);
        }
        return getHttp();

    }

}

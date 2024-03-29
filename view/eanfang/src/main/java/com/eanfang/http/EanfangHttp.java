package com.eanfang.http;

import com.eanfang.base.BaseApplication;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import cn.hutool.core.util.StrUtil;

/**
 * Created by jornl on 2017/8/30.
 */

/**
 * 该工具已废弃 请尽快移步 rds
 */
@Deprecated
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
            synchronized (EanfangHttp.class) {
                if (http == null) {
                    BaseApplication.get().initOkGo();
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
        if (StrUtil.isEmpty(token)) {
            getHttp().getCommonHeaders().remove("YAF-Token");
        } else {
            getHttp().getCommonHeaders().put("YAF-Token", token);
        }
        return getHttp();

    }

}

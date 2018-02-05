package com.eanfang.http;


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
        if (getHttp() == null) {
            new EanfangHttp();
        }

        return getHttp().<String>get(url);
    }

    /**
     * post请求，EanfangStringCallback类型回调
     *
     * @param url
     * @return
     */
    public static PostRequest post(String url) {
        if (getHttp() == null) {
            new EanfangHttp();
        }
        return getHttp().<String>post(url);
    }

    public static OkGo getHttp() {
        return http;
    }

    public static void setHttp(OkGo http) {
        EanfangHttp.http = http;
    }
}

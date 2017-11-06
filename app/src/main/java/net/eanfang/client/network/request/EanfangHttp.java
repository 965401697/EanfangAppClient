package net.eanfang.client.network.request;


import com.okgo.OkGo;
import com.okgo.request.GetRequest;
import com.okgo.request.PostRequest;

import net.eanfang.client.application.EanfangApplication;


/**
 * Created by jornl on 2017/8/30.
 */

public class EanfangHttp {
    private static OkGo http;

    private EanfangHttp() {
        http = EanfangApplication.get().getHttp();
    }

    /**
     * get请求，EanfangStringCallback类型回调
     *
     * @param url
     * @return
     */
    public static GetRequest get(String url) {
        if (http == null) {
            new EanfangHttp();
        }
        return http.<String>get(url);
    }

    /**
     * post请求，EanfangStringCallback类型回调
     *
     * @param url
     * @return
     */
    public static PostRequest post(String url) {
        if (http == null) {
            new EanfangHttp();
        }
        return http.<String>post(url);
    }

}

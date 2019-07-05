package com.eanfang.base.kit.ali.oss;

import com.eanfang.base.network.model.BaseResponseBody;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author jornl
 * @date 2019-07-01
 */
public interface IOssApi {

    /**
     * 获取oss 令牌
     *
     * @return OssBean
     */
    @GET("/yaf_sys/oss/sts")
    Observable<BaseResponseBody<OssBean>> getToken();
}

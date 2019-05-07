package com.eanfang.kit.ali.alioss;

import com.eanfang.network.model.BaseResponseBody;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface IOssApi {

    @GET("/yaf_sys/oss/sts")
    Observable<BaseResponseBody<OSSBean>> getToken();
}

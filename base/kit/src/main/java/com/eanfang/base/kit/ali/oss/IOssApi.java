package com.eanfang.base.kit.ali.oss;

import com.eanfang.base.network.model.BaseResponseBody;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface IOssApi {

    @GET("/yaf_sys/oss/sts")
    Observable<BaseResponseBody<OssBean>> getToken();
}

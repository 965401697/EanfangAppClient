package com.eanfang.rds.sys.api;
import com.eanfang.kit.bean.OSSBean;
import com.eanfang.network.model.BaseResponseBody;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface IOSSApi {
    @GET("/yaf_sys/oss/sts")
    Observable<BaseResponseBody<OSSBean>> getToken();
}

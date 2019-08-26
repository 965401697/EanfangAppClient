package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
public interface SecurityCompanyDetailApi {
    /**
     * 获取详情
     *
     * @param orgId
     * @return
     */
    @POST("/yaf_sys/orgunit/orgUnitInfo")
    Observable<BaseResponseBody<SecurityCompanyDetailBean>> getCompanyDetail(@Query("orgId") String orgId);
}

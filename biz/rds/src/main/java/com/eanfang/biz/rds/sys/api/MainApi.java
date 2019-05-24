package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.BaseDataBean;
import com.eanfang.biz.model.bean.ConstAllBean;
import com.eanfang.biz.model.entity.AccountEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainApi {

    /**
     * 获取基础数据
     *
     * @param md5 md5
     * @return BaseDataBean
     */
    @GET("/yaf_sys/basedata/listallcache/{md5}")
    Observable<BaseResponseBody<BaseDataBean>> getBaseData(@Path("md5") String md5);

    /**
     * 获取常量数据
     *
     * @param md5 md5
     * @return ConstAllBean
     */
    @GET("/yaf_sys/const/listallcache/{md5}")
    Observable<BaseResponseBody<ConstAllBean>> getConstData(@Path("md5") String md5);

    /**
     * 获取账号详情
     *
     * @param id accId
     * @return AccountEntity
     */
    @GET("/yaf_sys/account/info/{accId}")
    Observable<BaseResponseBody<AccountEntity>> getAccountInfo(@Path("accId") String id);
}

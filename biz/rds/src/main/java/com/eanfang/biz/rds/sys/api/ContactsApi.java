package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 通讯录相关接口
 *
 * @author jornl
 * @date 2019年8月8日
 */
public interface ContactsApi {

    /**
     * 加入默认公司接口
     *
     * @return LoginBean
     */
    @POST("/yaf_sys/sys/joinSystemCompany")
    Observable<BaseResponseBody<LoginBean>> joinDefCompany();
}

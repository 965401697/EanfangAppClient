package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.vo.LoginVo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author jornl
 * @date 2019-04-19 15:36:46
 * 登录api
 */
public interface LoginApi {

    /**
     * 密码登录
     *
     * @param loginVo loginVo
     * @return LoginBean
     */
    @POST("/yaf_sys/sys/login")
    Observable<BaseResponseBody<LoginBean>> loginPassword(@Body LoginVo loginVo);

    /**
     * 发送验证码
     *
     * @param phone phone
     * @return LoginBean
     */
    @GET("/yaf_sys/account/sendverify")
    Observable<BaseResponseBody<String>> verifyCode(@Query("mobile") String phone);

    /**
     * 验证码登录
     *
     * @param userName userName
     * @param code     code
     * @return LoginBean
     */
    @POST("/yaf_sys/sys/login_verify")
    Observable<BaseResponseBody<LoginBean>> loginVerify(@Query("mobile") String userName, @Query("verifycode") String code);


    /**
     * token登录
     *
     * @return LoginBean
     */
    @GET("/yaf_sys/sys/userinfo")
    Observable<BaseResponseBody<LoginBean>> loginToken();

    /**
     * 退出登录
     *
     * @return ok
     */
    @GET("/yaf_sys/sys/logout")
    Observable<BaseResponseBody<Object>> logout();

}

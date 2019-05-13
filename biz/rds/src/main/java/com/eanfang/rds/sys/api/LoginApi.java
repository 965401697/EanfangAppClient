package com.eanfang.rds.sys.api;

import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.vo.LoginVo;
import com.eanfang.network.model.BaseResponseBody;

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
     * @return
     */
    @GET("/yaf_sys/account/sendverify")
    Observable<BaseResponseBody<String>> verifyCode(@Query("mobile") String phone);

    /**
     * 验证码登录
     *
     * @param userName userName
     * @param code     code
     * @return
     */
    @POST("/yaf_sys/sys/login_verify")
    Observable<BaseResponseBody<LoginBean>> loginVerify(@Query("mobile") String userName, @Query("verifycode") String code);
}

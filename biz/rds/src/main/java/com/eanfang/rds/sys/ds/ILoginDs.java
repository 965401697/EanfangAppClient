package com.eanfang.rds.sys.ds;


import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.vo.LoginVo;
import com.eanfang.network.callback.RequestCallback;

/**
 * 登录相关接口
 */
public interface ILoginDs {

    /**
     * 密码登录
     *
     * @param loginVo  loginVo
     * @param callback callback
     */
    void loginPassword(LoginVo loginVo, RequestCallback<LoginBean> callback);

    /**
     * 获取验证码
     *
     * @param phone    phone
     * @param callback callback
     */
    void verifyCode(String phone, RequestCallback<String> callback);

    /**
     * 验证码登录
     *
     * @param userName userName
     * @param code     code
     * @param callback callback
     */
    void loginVerify(String userName, String code, RequestCallback<LoginBean> callback);
}

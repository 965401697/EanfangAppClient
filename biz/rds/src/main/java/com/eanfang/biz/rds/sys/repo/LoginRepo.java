package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.vo.LoginVo;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.LoginDs;

/**
 * @author jornl
 */
public class LoginRepo extends BaseRepo<LoginDs> {
    public LoginRepo(LoginDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 密码登录
     *
     * @param userName userName
     * @param password password
     * @return LoginBean
     */
    public MutableLiveData<LoginBean> loginPassword(String userName, String password) {
        MutableLiveData<LoginBean> loginMutableLiveData = new MutableLiveData<>();
        LoginVo vo = new LoginVo();
        vo.getUsername().set(userName);
        vo.getPassword().set(password);
        remoteDataSource.loginPassword(vo, loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }

    /**
     * 获取验证码接口
     *
     * @param phone phone
     * @return LoginBean
     */
    public MutableLiveData<String> verifyCode(String phone) {
        MutableLiveData<String> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.verifyCode(phone, loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }

    /**
     * 验证码登录
     *
     * @param userName userName
     * @param code     code
     * @return LoginBean
     */
    public MutableLiveData<LoginBean> loginVerify(String userName, String code) {
        MutableLiveData<LoginBean> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.loginVerify(userName, code, loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }

    /**
     * token 登录
     *
     * @return LoginBean
     */
    public MutableLiveData<LoginBean> loginToken() {
        MutableLiveData<LoginBean> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.loginToken(loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }

    /**
     * 退出登录
     *
     * @return MutableLiveData<Object>
     */
    public MutableLiveData<Object> logout() {
        MutableLiveData<Object> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.loginToken(loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }
}

package com.eanfang.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.vo.LoginVo;
import com.eanfang.rds.base.BaseRepo;
import com.eanfang.rds.sys.ds.LoginDs;

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
     * @return
     */
    public MutableLiveData<LoginBean> loginVerify(String userName, String code) {
        MutableLiveData<LoginBean> loginMutableLiveData = new MutableLiveData<>();
        remoteDataSource.loginVerify(userName, code, loginMutableLiveData::setValue);
        return loginMutableLiveData;
    }
}

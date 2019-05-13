package com.eanfang.rds.sys.ds;

import com.eanfang.model.bean.LoginBean;
import com.eanfang.model.vo.LoginVo;
import com.eanfang.network.callback.RequestCallback;
import com.eanfang.rds.base.BaseRemoteDataSource;
import com.eanfang.rds.base.BaseViewModel;
import com.eanfang.rds.sys.api.LoginApi;

public class LoginDs extends BaseRemoteDataSource implements ILoginDs {

    public LoginDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void loginPassword(LoginVo loginVo, RequestCallback<LoginBean> callback) {
        execute(getService(LoginApi.class).loginPassword(loginVo), callback);
    }

    @Override
    public void verifyCode(String phone, RequestCallback<String> callback) {
        execute(getService(LoginApi.class).verifyCode(phone), callback);
    }

    @Override
    public void loginVerify(String userName, String code, RequestCallback<LoginBean> callback) {
        //这是一个网络缓存的 demo，缓存登录接口信息，1小时内使用相同账号密码的登录会直接获取缓存
//        execute(getService(LoginApi.class).loginVerify(userName, code), callback, new CacheModel().setCacheStrategy(CacheStrategy.firstCacheTimeout(1000 * 60 * 60)).setClazz(LoginApi.class).setMethod("loginVerify").setValues(userName, code));
        execute(getService(LoginApi.class).loginVerify(userName, code), callback);

    }
}

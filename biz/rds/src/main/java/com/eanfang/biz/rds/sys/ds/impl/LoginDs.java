package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.vo.LoginVo;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.base.CacheModel;
import com.eanfang.biz.rds.sys.api.LoginApi;
import com.eanfang.biz.rds.sys.ds.ILoginDs;
import com.zchu.rxcache.stategy.CacheStrategy;

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

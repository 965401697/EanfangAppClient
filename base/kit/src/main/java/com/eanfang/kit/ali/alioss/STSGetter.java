package com.eanfang.kit.ali.alioss;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.eanfang.network.RetrofitManagement;
import com.eanfang.network.config.HttpConfig;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 重载OSSFederationCredentialProvider生成自己的获取STS的功能
 */
public class STSGetter extends OSSFederationCredentialProvider {
    private OSSBean ossBean;

    STSGetter() {
        RetrofitManagement.getInstance().getService(IOssApi.class, HttpConfig.get().getApiUrl()).getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((bean) -> ossBean = bean.getData());
    }

    @Override
    public OSSFederationToken getFederationToken() {
        for (int i = 0; i < 100; i++) {
            if (ossBean != null) {
                String ak = ossBean.getAccessKeyId();
                String sk = ossBean.getAccessKeySecret();
                String token = ossBean.getSecurity();
                String expiration = ossBean.getExpiration();
                return new OSSFederationToken(ak, sk, token, expiration);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

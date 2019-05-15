package com.eanfang.kit.ali.oss;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.eanfang.network.RetrofitManagement;
import com.eanfang.network.config.HttpConfig;
import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 使用 Retrofit请求token 并设置缓存 1小时失效
 */
public class STSGetter extends OSSFederationCredentialProvider {
    private OssBean ossBean;

    STSGetter() {
        RetrofitManagement.getInstance().getService(IOssApi.class, HttpConfig.get().getApiUrl()).getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //优先使用缓存 1小时过期
                .compose(RxCache.getDefault().transformObservable("IOssApi:getToken", OssBean.class, CacheStrategy.firstCacheTimeout(1000 * 60 * 60)))
                .map(new CacheResult.MapFunc<>())
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

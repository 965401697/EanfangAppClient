package com.eanfang.base.kit.ali.oss;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.eanfang.base.network.RetrofitManagement;
import com.eanfang.base.network.config.HttpConfig;
import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.data.CacheResult;
import com.zchu.rxcache.stategy.CacheStrategy;

import cn.hutool.core.thread.ThreadUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 使用 Retrofit请求token 并设置缓存 1小时失效
 *
 * @author jornl
 * @date 2019-07-01
 */
public class STSGetter extends OSSFederationCredentialProvider {
    private OssBean ossBean;

    STSGetter() {
        RetrofitManagement.getInstance().getService(IOssApi.class, HttpConfig.get().getApiUrl()).getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //优先使用缓存 10分钟过期
                .compose(RxCache.getDefault().transformObservable("IOssApi:getToken", OssBean.class, CacheStrategy.firstCacheTimeout(1000 * 60)))
                .map(new CacheResult.MapFunc<>())
                .subscribe((bean) -> ossBean = bean.getData());
    }

    @Override
    public OSSFederationToken getFederationToken() {
        for (int i = 0; i < 200; i++) {
            if (ossBean != null) {
                String ak = ossBean.getAccessKeyId();
                String sk = ossBean.getAccessKeySecret();
                String token = ossBean.getSecurity();
                String expiration = ossBean.getExpiration();
                return new OSSFederationToken(ak, sk, token, expiration);
            }
            ThreadUtil.safeSleep(50);
        }
        return null;
    }
}

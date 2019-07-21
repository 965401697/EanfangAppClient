package com.eanfang.base.kit.ali.oss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.base.network.config.HttpConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 使用 Retrofit请求token 并设置缓存 1小时失效
 *
 * @author jornl
 * @date 2019-07-01
 */
public class STSGetter extends OSSFederationCredentialProvider {
    public static final String STS_TOKEN_KEY = "STS_TOKEN_KEY";
    //    private static transient OssBean ossBean;
//
//    STSGetter() {
//        ossBean = null;
//        RetrofitManagement.getInstance().getService(IOssApi.class, HttpConfig.get().getApiUrl()).getToken()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                //优先使用缓存 10分钟过期
//                .compose(RxCache.getDefault().transformObservable("IOssApi:getToken", OssBean.class, CacheStrategy.firstCacheTimeout(1000 * 60)))
//                .map(new CacheResult.MapFunc<>())
//                .subscribe((bean) -> ossBean = bean.getData());
//    }

    @Override
    public OSSFederationToken getFederationToken() {
        OssBean ossBean = CacheKit.get().get(STS_TOKEN_KEY, OssBean.class);
        if (ossBean == null || ossBean.getAccessKeyId() == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();
            //创建请求对象
            Request request = new Request.Builder()
                    .url(HttpConfig.get().getApiUrl() + "/yaf_sys/oss/sts")
                    .header("YAF-Token", HttpConfig.get().getToken())
                    .header("Request-From", HttpConfig.get().getRequestFrom())
                    .build();
            //创建Call请求队列
            //请求都是放到一个队列里面的
            String json = null;
            try {
                json = client.newCall(request).execute().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject object = JSON.parseObject(json);
            ossBean = object.getJSONObject("data").toJavaObject(OssBean.class);
            CacheKit.get().put(STS_TOKEN_KEY, ossBean, CacheMod.Memory, 60 * 5);
        }
        String ak = ossBean.getAccessKeyId();
        String sk = ossBean.getAccessKeySecret();
        String token = ossBean.getSecurity();
        String expiration = ossBean.getExpiration();
        return new OSSFederationToken(ak, sk, token, expiration);

    }
}

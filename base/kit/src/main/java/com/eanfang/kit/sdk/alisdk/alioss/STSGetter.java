package com.eanfang.kit.sdk.alisdk.alioss;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.eanfang.kit.bean.OSSBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 重载OSSFederationCredentialProvider生成自己的获取STS的功能
 */
public class STSGetter extends OSSFederationCredentialProvider {
    private OSSBean ossBean;

    public STSGetter(OSSBean ossBean) {
        this.ossBean=ossBean;
    }

    @Override
    public OSSFederationToken getFederationToken() {
        if (ossBean != null) {
            String ak = ossBean.getAccessKeyId();
            String sk = ossBean.getAccessKeySecret();
            String token = ossBean.getSecurity();
            String expiration = ossBean.getExpiration();
            return new OSSFederationToken(ak, sk, token, expiration);
        }

        return null;
    }
}

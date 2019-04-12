package com.eanfang.sdk.alisdk.alioss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 */
public interface IOSSCallBack {
    void onSuccess(PutObjectRequest request, PutObjectResult result);
    void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException);
}

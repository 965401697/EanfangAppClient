package com.eanfang.sdk.alisdk.alioss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.eanfang.BuildConfig;
import com.eanfang.oss.STSGetter;
import com.eanfang.util.StringUtils;

import java.io.File;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 */
public class OssService implements IOssService {
    private OSS oss;

    @Override
    public OSS init(Context context) {
        // 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
        OSSCredentialProvider credentialProvider = new STSGetter();

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(10);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(3);
        OSS oss = new OSSClient(context, BuildConfig.OSS_ENDPOINT, credentialProvider, conf);
        this.oss = oss;
        return oss;
    }

    @Override
    public void asyncPutFile(String objectKey,String filePath,IOSSCallBack mIOSSCallBack) {
        putFile(objectKey,filePath,mIOSSCallBack);
    }

    @Override
    public void asyncPutImage(String objectKey, String filePath,IOSSCallBack mIOSSCallBack) {
        putFile(objectKey,filePath,mIOSSCallBack);
    }

    @Override
    public void asyncPutImages() {

    }

    @Override
    public void asyncPutVideo(String objectKey, String filePath,IOSSCallBack mIOSSCallBack) {

    }

    /**
     * 获取 PutObjectRequest
     *
     * @param objectKey
     * @param localFileUrl
     * @return
     */
    private PutObjectRequest getPutObjectRequest(String objectKey, String localFileUrl) {
        if (StringUtils.isEmpty(objectKey)) {
            return null;
        }
        final File file = new File(localFileUrl);
        if (!file.exists()) {
            return null;
        }
        // 构造上传请求
        return new PutObjectRequest(BuildConfig.OSS_BUCKET, objectKey, localFileUrl);
    }

    private void putFile(String objectKey,String filePath,IOSSCallBack mIOSSCallBack){
        // 构造上传请求。
        PutObjectRequest put = getPutObjectRequest(objectKey,filePath);

        // 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                mIOSSCallBack.onSuccess(request,result);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                mIOSSCallBack.onFailure(request,clientExcepion,serviceException);
            }
        });
        // task.cancel(); // 可以取消任务。
        // task.waitUntilFinished(); // 等待任务完成。
    }
}

package com.eanfang.oss;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.eanfang.util.LubanUtils;
import com.eanfang.util.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {

    //private MultiPartUploadManager multiPartUploadManager;
    //根据实际需求改变分片大小
    private final static int partSize = 256 * 1024;
    private final Integer UPLOAD_FAILED = -1;
    private final Integer UPLOAD_SUCCESS = 1;
    private OSS oss;
    private String bucket;
    private Activity activity;
    private AtomicReference<OSSCallBack> ossCallBack = new AtomicReference<>();


    public OssService(Activity activity, OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;
        this.activity = activity;
    }

    /**
     * 检测文件是否存在
     *
     * @param objectKey
     * @return
     */
    private boolean objectExist(String objectKey) {
        try {
            if (oss.doesObjectExist(bucket, objectKey)) {
                return true;
            }
        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("RequestId", e.getRequestId());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
        return false;
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
        return new PutObjectRequest(bucket, objectKey, localFileUrl);
    }


    public void putImage(String objectKey, String urlPath) {
        //图片压缩
        LubanUtils.compress(activity, urlPath, (path) -> {
            PutObjectRequest put = getPutObjectRequest(objectKey, path);
            JSONObject resultJson = new JSONObject();
            //如果为空  则跳过
            if (put == null) {
                resultJson.put("code", UPLOAD_SUCCESS);
                int curr = ossCallBack.get().getCurr().get() + 1;
                resultJson.put("curr", curr);
                EventBus.getDefault().post(resultJson);
                return;
            }

            // 异步上传时可以设置进度回调
//            put.setProgressCallback(ossCallBack);
//            if (ossCallBack.getTotal() <= ossCallBack.getCurr()) {
//                ossCallBack.isAllSuccess = true;
//            }
            oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    activity.runOnUiThread(() -> {
                        ossCallBack.get().onOssProgress(null, 0, 0);
                    });
                    resultJson.put("code", UPLOAD_SUCCESS);
                    int curr = ossCallBack.get().getCurr().get() + 1;
                    resultJson.put("curr", curr);
                    EventBus.getDefault().post(resultJson);
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    resultJson.put("code", UPLOAD_FAILED);
                    resultJson.put("curr", (ossCallBack.get().getCurr().get()));
                    EventBus.getDefault().post(resultJson);
                }
            });
        }, (e) -> {
            JSONObject resultJson = new JSONObject();
            resultJson.put("code", UPLOAD_FAILED);
            resultJson.put("curr", (ossCallBack.get().getCurr().get()));
            EventBus.getDefault().post(resultJson);
        });
    }

    public void asyncPutImage(String objectKey, String urlPath, OSSCallBack ossCallBack) {
        EventBus.getDefault().register(this);
        ossCallBack.getTotal().set(1);
        this.ossCallBack.set(ossCallBack);
        putImage(objectKey, urlPath);
    }

    public void asyncPutImages(final Map<String, String> objectMap, final OSSCallBack callBack) {

        //初始化 总数
        callBack.getTotal().set(objectMap.keySet().size());
        this.ossCallBack.set(callBack);
        EventBus.getDefault().register(this);
        //如果没有了 则成功
        if (objectMap == null || objectMap.size() <= 0 || objectMap.keySet().size() <= 0) {
            activity.runOnUiThread(() -> {
                ossCallBack.get().onSuccess(null, null);
                EventBus.getDefault().unregister(this);
            });
            return;
        }
        final List<String> keyList = new ArrayList(objectMap.keySet());

        for (int i = 0; i < keyList.size(); i++) {
            String objectKey = keyList.get(i);
            String localFileUrl = objectMap.get(objectKey);
            putImage(objectKey, localFileUrl);
        }
        //开始执行检测线程
//        asyncCheckPicExist(keyList.get(keyList.size() - 1), callBack);
    }


    @Subscribe
    public synchronized void onEvent(JSONObject result) {

        //获取当前成功的 数量  赋值给 ossCallBack
        Integer curr = result.getInteger("curr");
        Integer code = result.getInteger("code");
        //code 为 -1 代表失败
        if (code.equals(UPLOAD_FAILED)) {
            activity.runOnUiThread(() -> {
                ossCallBack.get().onFailure(null, null, null);
            });
            EventBus.getDefault().unregister(this);
            return;
        }
        ossCallBack.get().getCurr().set(curr);

        //如果当前上传的图片 >= 总数 则代表成功  直接解绑。
        Log.e("ossService", "onEvent: total:" + ossCallBack.get().getCurr().get() + "  curr:" + ossCallBack.get().getCurr().get());
        if (ossCallBack.get().getCurr().get() >= ossCallBack.get().getTotal().get()) {
            activity.runOnUiThread(() -> {
                ossCallBack.get().onSuccess(null, null);
            });
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 异步验证 图片是否上传成功
     *
     * @param lastKey
     * @param callBack
     */
//    public void asyncCheckPicExist(String lastKey, OSSCallBack callBack) {
//        //首次触发时间
//        long firstTime = System.currentTimeMillis();
//        //等待时间 1分钟
//        int waitTime = 1 * 60 * 1000;
//        //最长3分钟的等待
//        int maxTime = 3 * 60 * 1000;
//
//        Thread thread = new Thread(() -> {
//            while (true) {
//                //当前时间
//                long currTime = System.currentTimeMillis();
//                //如果超出最大时间限制 则失败
//                if ((currTime - firstTime) >= maxTime) {
//                    callBack.onFinish();
//                    if (callBack.getCurr() >= callBack.getTotal()) {
//                        return;
//                    }
//                    // callBack.onOssFail(null);
//                    return;
//                    //如果超过一分钟了
//                } else if ((currTime - firstTime) >= waitTime) {
//                    //如果正常回调上传成功
//                    if (callBack.getCurr() >= callBack.getTotal()) {
//                        return;
//                        //如果最后的元素上传成功
//                    } else if (objectExist(lastKey)) {
//                        callBack.setCurr(99);
//                        callBack.onSuccess(null, null);
//                        return;
//                    }
//                    //小于等待时间
//                }
//                try {
//                    Thread.currentThread().sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//
//                }
//            }
//        });
//        thread.start();
//    }
}

package com.eanfang.oss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.eanfang.util.ExecuteUtils;
import com.eanfang.util.LubanUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.Var;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.compress.CompressImageUtil;
import com.jph.takephoto.model.LubanOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {

    //private MultiPartUploadManager multiPartUploadManager;
    //根据实际需求改变分片大小
    private final static int partSize = 256 * 1024;
    private OSS oss;
    private String bucket;
    private Context context;
    private Integer total = 1;

    public OssService(Context context, OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;
        this.context = context;
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

    /**
     * 图片压缩处理
     */
    private void photoCompress(String localFileUrl, CompressImageUtil.CompressListener listener) {
        LubanOptions option = new LubanOptions.Builder()
                .setMaxHeight(720)
                .setMaxWidth(720)
                .setMaxSize(720 * 720)
                .create();
        CompressConfig config = CompressConfig.ofLuban(option);
        CompressImageUtil util = new CompressImageUtil(context, config);
        util.compress(localFileUrl, listener);
    }

    public void putImage(String objectKey, String urlPath) {
        //图片压缩
        LubanUtils.compress(context, urlPath, (path) -> {
            PutObjectRequest put = getPutObjectRequest(objectKey, path);
            //如果为空  则跳过
            if (put == null) {
                Var.get("OssService.asyncPutImages." + objectKey).setVar(1);
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
                    Var.get("OssService.asyncPutImages." + objectKey).setVar(1);
                    int count = Var.get("OssService.asyncPutImage.count").getVar() + 1;
                    Var.get("OssService.asyncPutImage.count").setVar(count);
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    //如果失败次数超过三次  则上传失败
                    if (Var.get("OssService.asyncPutImages." + objectKey).getVar() > 3) {
////                        ossCallBack.onFailure(request, clientException, serviceException);
                        Var.get("OssService.asyncPutImages." + objectKey).setVar(99);
                        return;
                    }
                    Var.get("OssService.asyncPutImages." + objectKey).setVar(Var.get("OssService.asyncPutImages." + objectKey).getVar() + 1);
                }
            });
        });
    }

    public void asyncPutImage(String objectKey, String urlPath, OSSCallBack ossCallBack) {
//        if (Var.get("OssService.asyncPutImage.count").getVar() <= 0) {
        Var.get("OssService.asyncPutImage.count").setChangeListener((var) -> {
            ExecuteUtils.execute(() -> {
                        ossCallBack.onProgress(null, 0L, 0L);
                        ossCallBack.setCurr(Var.get("OssService.asyncPutImage.count").getVar() + 1);
                        return Var.get("OssService.asyncPutImage.count").getVar();
                    }
                    , total, 0
                    , () -> {
                        Var.remove("OssService.asyncPutImage.count");
                        ossCallBack.isAllSuccess = true;
                        ossCallBack.onSuccess(null, null);
                    }
                    , () -> putImage(objectKey, urlPath));
        });
//        }
        Var.get("OssService.asyncPutImage.count").setVar(Var.get("OssService.asyncPutImage.count").getVar());
    }


    public void asyncPutImages(final Map<String, String> objectMap, final OSSCallBack callBack) {

        //如果没有了 则成功
        if (objectMap == null || objectMap.size() <= 0 || objectMap.keySet().size() <= 0) {
            callBack.isAllSuccess = true;
            callBack.onSuccess(null, null);
            return;
        }

        final List<String> keyList = new ArrayList(objectMap.keySet());
        total = keyList.size();
        //初始化 总数
        callBack.setTotal(keyList.size());
        for (int i = 0; i < keyList.size(); i++) {
            String objectKey = keyList.get(i);
            String localFileUrl = objectMap.get(objectKey);
            //监控 是否上传成功
            Var.get("OssService.asyncPutImages." + objectKey).setChangeListener((var) -> {
                ExecuteUtils.execute(() -> Var.get("OssService.asyncPutImages." + objectKey).getVar()
                        , 1, 0
                        , () -> Var.remove("OssService.asyncPutImages." + objectKey)
                        , () -> asyncPutImage(objectKey, localFileUrl, callBack));
            });
            //触发上传
            Var.get("OssService.asyncPutImages." + objectKey).setVar(0);
        }


        //开始执行检测线程
//        asyncCheckPicExist(keyList.get(keyList.size() - 1), callBack);

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

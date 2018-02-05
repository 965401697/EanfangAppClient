package com.eanfang.oss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.eanfang.util.StringUtils;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.compress.CompressImageUtil;
import com.jph.takephoto.model.LubanOptions;

import java.io.File;
import java.util.ArrayList;
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

    public OssService(Context context, OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;
        this.context = context;
        init();
    }

    private void init() {
        //this.multiPartUploadManager = new MultiPartUploadManager(oss, bucket, partSize);

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
            Log.w("AsyncPutImage", "objectKey空");
            return null;
        }
        final File file = new File(localFileUrl);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "文件没有找到");
            Log.w("LocalFile", localFileUrl);
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

    public void asyncPutImage(final String objectKey, final String localFileUrl, final OSSCallBack ossCallBack) {
        PutObjectRequest put = getPutObjectRequest(objectKey, localFileUrl);
        if (put == null) {
            return;
        }
        // 异步上传时可以设置进度回调
        put.setProgressCallback(ossCallBack);
        //图片压缩 回调
        CompressImageUtil.CompressListener listener = new CompressImageUtil.CompressListener() {
            @Override
            public void onCompressSuccess(String imgPath) {
                //上传图片
                put.setUploadFilePath(imgPath);
                oss.asyncPutObject(put, ossCallBack);
            }

            @Override
            public void onCompressFailed(String imgPath, String msg) {
                ossCallBack.onCompressFailed();
            }
        };
        //开始压缩
        photoCompress(localFileUrl, listener);

        //开始执行检测线程
        asyncCheckPicExist(objectKey, ossCallBack);
    }

    public void asyncPutImages(final Map<String, String> objectMap, final OSSCallBack callBack) {
        Set<String> set = objectMap.keySet();
        final List<String> keyList = new ArrayList(set);
        //初始化 总数
        callBack.setTotal(keyList.size());
        callBack.onStart();
        for (int i = 0; i < keyList.size(); i++) {
            final String objectKey = keyList.get(i);
            String localFileUrl = objectMap.get(objectKey);
            asyncPutImage(objectKey, localFileUrl, callBack);
            // 正在上传
        }

        //开始执行检测线程
        asyncCheckPicExist(keyList.get(keyList.size() - 1), callBack);

    }

    /**
     * 异步验证 图片是否上传成功
     *
     * @param lastKey
     * @param callBack
     */
    public void asyncCheckPicExist(String lastKey, OSSCallBack callBack) {
        //首次触发时间
        long firstTime = System.currentTimeMillis();
        //等待时间 1分钟
        int waitTime = 1 * 60 * 1000;
        //最长3分钟的等待
        int maxTime = 3 * 60 * 1000;

        Thread thread = new Thread(() -> {
            while (true) {
                //当前时间
                long currTime = System.currentTimeMillis();
                //如果超出最大时间限制 则失败
                if ((currTime - firstTime) >= maxTime) {
                    callBack.onFinish();
                    if (callBack.getCurr() >= callBack.getTotal()) {
                        return;
                    }
                    // callBack.onOssFail(null);
                    return;
                    //如果超过一分钟了
                } else if ((currTime - firstTime) >= waitTime) {
                    //如果正常回调上传成功
                    if (callBack.getCurr() >= callBack.getTotal()) {
                        return;
                        //如果最后的元素上传成功
                    } else if (objectExist(lastKey)) {
                        callBack.setCurr(99);
                        callBack.onSuccess(null, null);
                        return;
                    }
                    //小于等待时间
                }
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        thread.start();
    }
}

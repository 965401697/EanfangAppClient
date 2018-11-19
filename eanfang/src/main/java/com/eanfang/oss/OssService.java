package com.eanfang.oss;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import lombok.Getter;


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

    private List<OSSAsyncTask<PutObjectResult>> uploadThreads = new ArrayList<>();

    @Getter
    private static AtomicReference<OSSCallBack> ossCallBack = new AtomicReference<>();

    private Timer timer;
    private TimerTask task;


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

    /**
     * 上传视频
     */
    public synchronized void putVideo(String objectKey, String urlPath) {
        PutObjectRequest put = getPutObjectRequest(objectKey, urlPath);
        JSONObject resultJson = new JSONObject();
        //如果为空  则跳过
        if (put == null) {
            resultJson.put("code", UPLOAD_SUCCESS);
            int curr = this.getOssCallBack().get().getCurrent() + 1;
            getOssCallBack().get().setCurrent(curr);
            EventBus.getDefault().post(resultJson);
            return;
        }

        OSSAsyncTask<PutObjectResult> asyncTask = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                resultJson.put("code", UPLOAD_SUCCESS);
                synchronized (getOssCallBack().get()) {
                    int curr = getOssCallBack().get().getCurrent() + 1;
                    getOssCallBack().get().setCurrent(curr);
                }
                EventBus.getDefault().post(resultJson);

                activity.runOnUiThread(() -> {
                    getOssCallBack().get().onOssProgress(null, getOssCallBack().get().getCurrent(), getOssCallBack().get().getTotal());
                });

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                resultJson.put("code", UPLOAD_FAILED);
                EventBus.getDefault().post(resultJson);
            }
        });
        uploadThreads.add(asyncTask);

    }

    /**
     * 上传图片
     */
    public synchronized void putImage(String objectKey, String urlPath) {
        //图片压缩
        LubanUtils.compress(activity, urlPath, (path) -> {
            PutObjectRequest put = getPutObjectRequest(objectKey, path);
            JSONObject resultJson = new JSONObject();
            //如果为空  则跳过
            if (put == null) {
                resultJson.put("code", UPLOAD_SUCCESS);
                int curr = this.getOssCallBack().get().getCurrent() + 1;
                getOssCallBack().get().setCurrent(curr);
                EventBus.getDefault().post(resultJson);
                return;
            }

            // 异步上传时可以设置进度回调
//            put.setProgressCallback(ossCallBack);
//            if (ossCallBack.getTotal() <= ossCallBack.getCurr()) {
//                ossCallBack.isAllSuccess = true;
//            }

            OSSAsyncTask<PutObjectResult> asyncTask = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                    resultJson.put("code", UPLOAD_SUCCESS);
                    synchronized (getOssCallBack().get()) {
                        int curr = getOssCallBack().get().getCurrent() + 1;
                        getOssCallBack().get().setCurrent(curr);
                    }
                    EventBus.getDefault().post(resultJson);

                    activity.runOnUiThread(() -> {
                        getOssCallBack().get().onOssProgress(null, getOssCallBack().get().getCurrent(), getOssCallBack().get().getTotal());
                    });

                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    resultJson.put("code", UPLOAD_FAILED);
                    EventBus.getDefault().post(resultJson);
                }
            });
            uploadThreads.add(asyncTask);

        }, (e) -> {
            JSONObject resultJson = new JSONObject();
            resultJson.put("code", UPLOAD_FAILED);
            EventBus.getDefault().post(resultJson);
        });
    }

    /**
     * 上传视频
     */
    public void asyncPutVideo(String objectKey, String videoPath, OSSCallBack ossCallBack) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //清空线程
        uploadThreads.clear();
        this.getOssCallBack().get().setTotal(1);
        this.getOssCallBack().set(ossCallBack);
        putVideo(objectKey, videoPath);
        //开始执行检测线程
        asyncCheck();
    }

    /**
     * 上传单张图片
     */
    public void asyncPutImage(String objectKey, String urlPath, OSSCallBack ossCallBack) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //清空线程
        uploadThreads.clear();
        this.getOssCallBack().get().setTotal(1);
        this.getOssCallBack().set(ossCallBack);
        putImage(objectKey, urlPath);
        //开始执行检测线程
        asyncCheck();

    }

    /**
     * 上传多张图片
     */
    public void asyncPutImages(final Map<String, String> objectMap, final OSSCallBack callBack) {
        //初始化 总数
        this.getOssCallBack().get().setTotal(objectMap.keySet().size());
        this.getOssCallBack().set(callBack);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //清空线程
        uploadThreads.clear();

        //如果没有了 则成功
        if (objectMap == null || objectMap.size() <= 0 || objectMap.keySet().size() <= 0) {
            activity.runOnUiThread(() -> {
                this.getOssCallBack().get().onSuccess(null, null);
                //取消任务
                cancelTask();
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
        asyncCheck();
//        asyncCheckPicExist(keyList.get(keyList.size() - 1), callBack);
    }


    @Subscribe
    public void onEvent(JSONObject result) {
        synchronized (this) {
            //获取当前成功的 数量  赋值给 ossCallBack
            Integer code = result.getInteger("code");
            //code 为 -1 代表失败
            if (code.equals(UPLOAD_FAILED)) {
                activity.runOnUiThread(() -> {
                    this.getOssCallBack().get().onFailure(null, null, null);
                });
                //取消任务
                cancelTask();
                EventBus.getDefault().unregister(this);
                return;
            }
            int mTotal = this.getOssCallBack().get().getTotal();
            int mCurrent = this.getOssCallBack().get().getCurrent();
            //如果当前上传的图片 >= 总数 则代表成功  直接解绑。
            Log.e("ossService", "onEvent: total:" + mTotal + "  curr:" + mCurrent);
            if (mCurrent >= mTotal) {
                Log.e("ossServiceSuccess", "total:" + mTotal + "  curr:" + mCurrent);
                activity.runOnUiThread(() -> {
                    getOssCallBack().get().onSuccess(null, null);
                });
                //取消任务
                cancelTask();
                EventBus.getDefault().unregister(this);
            }

        }

    }

    public void cancelTask() {
        if (timer != null) {
            timer.cancel();
        }
        if (task != null) {
            task.cancel();
        }
        for (OSSAsyncTask<PutObjectResult> ossAsyncTask : uploadThreads) {
            ossAsyncTask.cancel();
            System.err.println("取消线程：" + ossAsyncTask.toString());
        }
        uploadThreads.clear();
    }

    public void asyncCheck() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (getOssCallBack().get().getCurrent() < getOssCallBack().get().getTotal()) {
                    System.err.println("asyncCheck:---------------");
                    activity.runOnUiThread(() -> {
                        getOssCallBack().get().onFailure(null, null, null);
                    });
                } else {
                    getOssCallBack().get().onSuccess(null, null);
                }
                EventBus.getDefault().unregister(this);
                cancelTask();
            }
        };
        //2分钟超时验证
        timer.schedule(task, 2 * 60 * 1000);

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

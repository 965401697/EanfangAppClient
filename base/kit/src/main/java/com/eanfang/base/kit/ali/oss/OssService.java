package com.eanfang.base.kit.ali.oss;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.annimon.stream.function.Consumer;
import com.eanfang.base.kit.loading.LoadKit;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 */
public class OssService implements IOssService {
    private static final String TAG = OssService.class.getSimpleName();
    private Activity activity;
    private OSS oss;
    private String bucket;
    private Dialog loading;


    //是否压缩完的标识
    private MutableLiveData<Boolean> isCompress = new MutableLiveData<>();
    //是否上传成功的标识
    private MutableLiveData<Boolean> isUpload = new MutableLiveData<>();

    OssService(Activity activity, OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;
        this.activity = activity;
        loading = LoadKit.dialog(activity, "上传准备中...");
    }

    /**
     * 获取 PutObjectRequest
     *
     * @param objectKey    objectKey
     * @param localFileUrl localFileUrl
     * @return PutObjectRequest
     */
    private PutObjectRequest getPutObjectRequest(String objectKey, String localFileUrl) {
        if (StrUtil.isEmpty(objectKey)) {
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
     * 上传文件通用方法
     *
     * @param objectMap objectMap
     */
    private void upFile(Map<String, String> objectMap) {
        if (oss == null) {
            Log.e(TAG, "oss == null 没有初始化OSS");
            return;
        }
        if (objectMap == null || objectMap.size() <= 0) {
            Log.e(TAG, "objectMap == null ");
            return;
        }
        //开始loading
        loading.show();
        AtomicInteger successCount = new AtomicInteger();
        Flowable.fromIterable(objectMap.keySet())
                .flatMap(key -> Flowable.just(key).map(fileKey -> {
//                    OSSAsyncTask<PutObjectResult> asyncTask;
                    PutObjectRequest put = getPutObjectRequest(fileKey, objectMap.get(fileKey));
                    return oss.putObject(put);
                }).subscribeOn(Schedulers.io()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result -> {
                    //上传成功
                    if (result != null && result.getStatusCode() == 200) {
                        successCount.getAndIncrement();
                        LoadKit.setText(loading, StrUtil.format("正在上传 {}/{}", successCount.get(), objectMap.size()));
                    }
                    //全部上传成功
                    if (successCount.get() >= objectMap.size()) {
                        LoadKit.setText(loading, "上传成功");
                        LoadKit.closeDialog(loading);
                        isUpload.setValue(true);
                    }
                }));
    }


    //压缩图片的方法
    private void compressPic(Map<String, String> objectMap) {
        AtomicInteger successCount = new AtomicInteger();
        loading.show();
        Flowable.fromIterable(objectMap.keySet())
                .flatMap((key) -> Flowable.just(key).map(fileKey -> Luban.with(activity).get(objectMap.get(fileKey)).getAbsolutePath() + "_F_" + fileKey).subscribeOn(Schedulers.io()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((path) -> {
                    String arr[] = path.split("_F_");
                    objectMap.put(arr[1], arr[0]);
                    successCount.getAndIncrement();

                    LoadKit.setText(loading, StrUtil.format("正在压缩图片 {}/{}", successCount.get(), objectMap.size()));
                    if (successCount.get() >= objectMap.size()) {
                        isCompress.setValue(true);
                        LoadKit.setText(loading, "压缩成功");
//                        LoadKit.closeDialog(loading);
                    }
                });
    }

    @Override
    public void asyncPutImage(String objectKey, String urlPath, Consumer<Boolean> consumer) {
        Map<String, String> objectMap = MapUtil.of(objectKey, urlPath);
        asyncPutImages(objectMap, consumer);
    }

    @Override
    public void asyncPutImages(Map<String, String> objectMap, Consumer<Boolean> consumer) {
        compressPic(objectMap);
        isCompress.observe((LifecycleOwner) activity, (isSuccess) -> {
            if (isSuccess) {
                upFile(objectMap);
            }
        });
        isUpload.observe((LifecycleOwner) activity, consumer::accept);
    }

    @Override
    public void asyncPutVideo(String objectKey, String videoPath, Consumer<Boolean> consumer) {
        Map<String, String> objectMap = MapUtil.of(objectKey, videoPath);
        asyncPutVideos(objectMap, consumer);
    }

    @Override
    public void asyncPutVideos(Map<String, String> objectMap, Consumer<Boolean> consumer) {
        isUpload.observe((LifecycleOwner) activity, (consumer::accept));
    }
}

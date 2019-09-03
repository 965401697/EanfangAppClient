package com.eanfang.base.kit.ali.oss;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.eanfang.base.kit.loading.LoadKit;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * created by wtt
 * at 2019/4/12
 * summary:
 *
 * @author jornl
 * @date 2019-07-01
 */
public class OssServiceImpl implements IOssService {
    private static final String TAG = OssServiceImpl.class.getSimpleName();
    private Activity activity;
    private OSS oss;
    private String bucket;
    private Dialog loading;


    /**
     * 是否压缩完的标识
     */
    private MutableLiveData<Boolean> isCompress = new MutableLiveData<>();
    /**
     * 是否上传成功的标识
     */
    private MutableLiveData<Boolean> isUpload = new MutableLiveData<>();

    OssServiceImpl(Activity activity, OSS oss, String bucket) {
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
                    PutObjectRequest put = getPutObjectRequest(fileKey, objectMap.get(fileKey));
                    try {
                        return oss.putObject(put);
                    } catch (Exception e) {
                        return null;
                    }
                }).subscribeOn(Schedulers.io()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result -> {
                    //上传成功
                    if (result != null && result.getStatusCode() == 200) {
                        successCount.getAndIncrement();
                        LoadKit.setText(loading, StrUtil.format("正在上传 {}/{}", successCount.get(), objectMap.size()));
                    } else {
                        LoadKit.setText(loading, "上传失败，请重试");
                        LoadKit.closeDialog(loading);
                    }
                    //全部上传成功
                    if (successCount.get() >= objectMap.size()) {
                        LoadKit.setText(loading, "上传成功");
                        LoadKit.closeDialog(loading);
                        isUpload.setValue(true);
                    }
                }));
    }

    /**
     * 压缩图片的方法
     *
     * @param objectMap objectMap
     */
    private void compressPic(Map<String, String> objectMap) {
        AtomicInteger successCount = new AtomicInteger();
        loading.show();
        List<String> keyList = Stream.of(objectMap.keySet().iterator()).toList();
        int count = objectMap.size();

        MutableLiveData<Integer> compressLiveData = new MutableLiveData<>();

        compressLiveData.observeForever((d) -> Luban.with(activity).load(objectMap.get(keyList.get(successCount.get()))).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                successCount.getAndIncrement();
                objectMap.remove(keyList.get(successCount.get() - 1));
                objectMap.put(keyList.get(successCount.get() - 1), file.getAbsolutePath());
                LoadKit.setText(loading, StrUtil.format("正在压缩 {}/{}", successCount.get(), count));
                if (successCount.get() >= count) {
                    isCompress.setValue(true);
                    LoadKit.setText(loading, "上传中...");
                    return;
                }
                compressLiveData.setValue(successCount.get());
            }

            @Override
            public void onError(Throwable e) {

            }
        }).launch());
        compressLiveData.setValue(0);
    }

    @Override
    public void asyncPutImage(String objectKey, String urlPath, Consumer<Boolean> consumer) {
        Map<String, String> objectMap = MapUtil.of(objectKey, urlPath);
        asyncPutImages(objectMap, consumer);
    }

    @Override
    public void asyncPutImages(Map<String, String> objectMap, Consumer<Boolean> consumer) {
        isUpload.observe((LifecycleOwner) activity, consumer::accept);
        if (objectMap != null && objectMap.size() > 0) {
            compressPic(objectMap);
            isCompress.observe((LifecycleOwner) activity, (isSuccess) -> {
                if (isSuccess) {
                    upFile(objectMap);
                }
            });
        } else {
            isUpload.setValue(true);
        }
    }

    @Override
    public void asyncPutVideo(String objectKey, String videoPath, Consumer<Boolean> consumer) {
        Map<String, String> objectMap = MapUtil.of(objectKey, videoPath);
        asyncPutVideos(objectMap, consumer);
    }

    @Override
    public void asyncPutVideos(Map<String, String> objectMap, Consumer<Boolean> consumer) {
        upFile(objectMap);
        isUpload.observe((LifecycleOwner) activity, (consumer::accept));
        if (objectMap != null && objectMap.size() > 0) {
            upFile(objectMap);
        } else {
            isUpload.setValue(true);
        }
    }
}

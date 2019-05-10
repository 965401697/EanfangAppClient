package com.eanfang.kit.ali.alioss;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.eanfang.kit.loading.LoadKit;
import com.eanfang.network.holder.ToastHolder;

import cn.hutool.core.util.StrUtil;

/**
 * Created by jornl on 2017/9/13.
 */

public abstract class OSSCallBack implements OSSProgressCallback<PutObjectRequest>, OSSCompletedCallback<PutObjectRequest, PutObjectResult> {

    private Activity activity;
    private boolean showDialog;
    private Dialog uploadDialog;

    private static Integer total;
    private static Integer current;

    private Handler handler;

    /**
     * @param activity   当前active
     * @param showDialog 是否显示dialog
     */
    public OSSCallBack(Activity activity, boolean showDialog) {
        this.activity = activity;
        this.showDialog = showDialog;
        init();
    }

    public static synchronized Integer getTotal() {
        return total;
    }

    public static synchronized void setTotal(Integer total) {
        OSSCallBack.total = total;
    }

    public static synchronized Integer getCurrent() {
        return current;
    }

    public static synchronized void setCurrent(Integer current) {
        OSSCallBack.current = current;
    }

    private void init() {
        setTotal(0);
        setCurrent(0);
        handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (showDialog) {
                uploadDialog = LoadKit.dialog(activity, "玩命上传中...");

                if (!uploadDialog.isShowing()) {
                    uploadDialog.show();
                }
            }
        });
    }

//    public void onStart() {
//        handler.post(() -> {
//
//        });
//    }

    public void onFinish() {
        handler.post(() -> {
            if (uploadDialog != null && uploadDialog.isShowing()) {
                LoadKit.closeDialog(uploadDialog);
            }
        });

    }


    @Override
    public final void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
        // Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
        onOssProgress(putObjectRequest, currentSize, totalSize);

    }

    @Override
    public final void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
        onFinish();
        onOssSuccess();
    }

    @Override
    public final void onFailure(PutObjectRequest putObjectRequest, ClientException
            e, ServiceException e1) {
        onFinish();
        onOssFail(null);
    }

    /**
     * 上传进度 回调
     *
     * @param request
     * @param currentSize
     * @param totalSize
     */

    public void onOssProgress(PutObjectRequest request, long currentSize, long totalSize) {
        handler.post(() -> {
            if (uploadDialog != null) {
                LoadKit.setText(uploadDialog, "正在上传（" + currentSize + "/" + totalSize + "）");
            }
        });
    }

    /**
     * 上传成功 回调
     */
    public void onOssSuccess() {
        ToastHolder.showToast(activity, "上传成功");
        ToastHolder.showToast(activity, "上传成功");
    }

    /**
     * 上传失败 回调
     */
    public void onOssFail(String message) {
        if (!StrUtil.isEmpty(message)) {
            ToastHolder.showToast(this.activity, message);
            return;
        }
        ToastHolder.showToast(this.activity, "哎呀，图片好像上传失败了，再试一下吧");
    }

    /**
     * 图片压缩失败
     */

    public void onCompressFailed() {
        onFinish();
        ToastHolder.showToast(this.activity, "哎呀，图片压缩失败了，再试一下吧");
    }
}

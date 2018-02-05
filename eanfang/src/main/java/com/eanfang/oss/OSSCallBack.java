package com.eanfang.oss;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.eanfang.ui.base.UploadDialogUtil;
import com.eanfang.util.ConnectivityChangeReceiver;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;

/**
 * Created by jornl on 2017/9/13.
 */

public abstract class OSSCallBack implements OSSProgressCallback<PutObjectRequest>, OSSCompletedCallback<PutObjectRequest, PutObjectResult> {

    private Activity activity;
    private boolean showDialog;
    private UploadDialogUtil.UploadDialog uploadDialog;
    private int total = 1;
    private int curr = 0;

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

    private void init() {
        handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (showDialog) {
                uploadDialog = UploadDialogUtil.uploadDialogUtil.buildUploadDialog(activity);
                uploadDialog.setTextContent("玩命上传中...");

                if (!uploadDialog.isShowing()) {
                    uploadDialog.show();
                }
            }
        });
    }

    public void onStart() {
        handler.post(() -> {
            if (uploadDialog != null) {
                uploadDialog.setTextContent("正在上传（" + getCurr() + "/" + getTotal() + "）");
            }
        });
    }

    public void onFinish() {
        handler.post(() -> {
            if (uploadDialog != null && uploadDialog.isShowing()) {
                uploadDialog.dismiss();
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
        //上传成功 当前++;
        setCurr(curr + 1);
        onStart();
        //全部上传成功
        if (getCurr() >= getTotal()) {
            onFinish();
            onOssSuccess();
            return;
        } else {
            //没有全部上传成功
        }

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

    }

    /**
     * 上传成功 回调
     */
    public void onOssSuccess() {
        ToastUtil.get().showToast(activity, "上传成功");
    }

    /**
     * 上传失败 回调
     */
    public void onOssFail(String message) {
        if (!ConnectivityChangeReceiver.isNetConnected(activity)) {
            ToastUtil.get().showToast(this.activity, "哎呀，网络连接好像失败了");
            return;
        } else if (!StringUtils.isEmpty(message)) {
            ToastUtil.get().showToast(this.activity, message);
            return;
        }
        ToastUtil.get().showToast(this.activity, "哎呀，图片好像上传失败了，再试一下吧");
    }

    /**
     * 图片压缩失败
     */

    public void onCompressFailed() {
        onFinish();
        ToastUtil.get().showToast(this.activity, "哎呀，图片压缩失败了，再试一下吧");
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }
}

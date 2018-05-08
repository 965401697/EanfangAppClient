/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-4-7 上午9:36
 * Copyright (c) 2017. All rights reserved.
 */

package com.project.eanfang.zxing.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.CustomeApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.ToastUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.project.eanfang.zxing.R;
import com.project.eanfang.zxing.camera.CameraManager;
import com.project.eanfang.zxing.manage.BeepManager;
import com.project.eanfang.zxing.manage.InactivityTimer;
import com.project.eanfang.zxing.manage.IntentSource;
import com.project.eanfang.zxing.view.ViewfinderView;
import com.yaf.base.entity.WorkerEntity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private ImageView iv_top_back; //左上角返回
    private TextView tv_top_title; //标题
    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;
    // 从哪来传输来的  参数
    private String mFromWhere = "";

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);
        mFromWhere = getIntent().getStringExtra("from");
        initView();
        initListener();
    }


    private void initView() {
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        tv_top_title.setText("二维码扫描");

    }

    private void initListener() {
        iv_top_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }
        beepManager.updatePrefs();
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        //historyManager = null; // Keep for onActivityResult
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // do nothing
    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
        }
        String resultString = rawResult.getText();
        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this, "扫描失败!", Toast.LENGTH_SHORT)
                    .show();
            // 扫码个人二维码
        } else if (resultString.contains("http://eanfang.net/codeLogin.html")) {
            //如果是true 则 解析扫到的二维码里的字符串 得到account的 ID
            String accountId = resultString.substring(resultString.indexOf("=") + 1);
            doGetAccount(accountId);
        } else {
            /**
             * 从哪里传输进来的
             * */
            switch (mFromWhere) {
                case "client_code":
                    String[] strs = resultString.split("/login/");
                    String dataStr = strs[1];
                    String[] data = dataStr.split("/");
                    String uuid = data[0];
                    String requestFrom = data[1];
                    doQRLoginPermission(uuid, requestFrom);
                    break;
                case "worker_code":
                    break;
            }
        }
    }

    /**
     *
     * 获取个人信息
     */
    private void doGetAccount(String accountId) {
        EanfangHttp.post(NewApiService.QR_GETACCOUNT)
                .params("accountId", accountId)
                .execute(new EanfangCallback<WorkerEntity>(CaptureActivity.this, true, WorkerEntity.class, bean -> {
                    doGetWorkDetail(bean);
                }));
    }

    /**
     *
     *  获取技师详情
     */
    private void doGetWorkDetail(WorkerEntity workerEntity) {
        EventBus.getDefault().post(workerEntity);
        finishSelf();
    }

    /**
     * 获取登录权限
     */
    private void doQRLoginPermission(String uuid, String requestFrom) {
        EanfangHttp.post(NewApiService.QR_CODE)
                .params("uuid", uuid)
                .params("requestFrom", requestFrom)
                .params("accountId", CustomeApplication.get().getAccId())
                .execute(new EanfangCallback<JSONObject>(CaptureActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        String isLogin = (String) bean.get("data");
                        if ("true".equals(isLogin)) {
                            // 进行登录
                            startActivity(new Intent(CaptureActivity.this, LoginConfirmActivity.class)
                                    .putExtra("which", requestFrom)
                                    .putExtra("uuid", uuid));
                        } else {
                            showToast("暂无权限");
                            finish();
                        }
                    }

                    public void onFail(Integer code, String message, com.alibaba.fastjson.JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        showToast("扫描失败");
                        finish();
                    }
                });
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // 打开Camera硬件设备
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_top_back) {
            finish();
        }
    }

    public void showToast(String message) {
        ToastUtil.get().showToast(this, message);
    }
}

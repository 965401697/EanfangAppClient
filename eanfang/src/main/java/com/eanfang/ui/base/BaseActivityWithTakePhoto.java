/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package com.eanfang.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.R;
import com.eanfang.application.CustomeApplication;
import com.eanfang.application.EanfangApplication;
import com.eanfang.util.PermissionsCallBack;
import com.eanfang.util.ToastUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * BaseAppCompatFragmentActivity
 *
 * @author hr
 * Created at 2016/1/1 11:33
 * @desc activity基类
 */

public abstract class BaseActivityWithTakePhoto extends TakePhotoActivity implements
        IBase, ActivityCompat.OnRequestPermissionsResultCallback {

    private int resultCode;
    private int limit = 0;
    private PermissionsCallBack permissionsCallBack;
    private ImageView iv_left;

    private AlertDialog.Builder builder;

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case PermissionsCallBack.callBackCode:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理
                    permissionsCallBack.callBack();
                } else {
                    // 没有获取到权限，做特殊处理
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "获取权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                    });
                }
                break;
            default:
                break;
        }
    }

    public void setPermissionsCallBack(PermissionsCallBack permissionsCallBack) {
        this.permissionsCallBack = permissionsCallBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Config.get().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
//        initState();
        CustomeApplication.get().push(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
        }
    }

    public void setLeftBack() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void setTitle(int id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }

    public void setTitle(String id) {
        ((TextView) findViewById(R.id.tv_title)).setText(id);
    }


    public void setRightImageResId(int resId) {
        ((ImageView) findViewById(R.id.iv_right)).setImageResource(resId);
    }

    public void setRightTitle(int id) {
        ((TextView) findViewById(R.id.tv_right)).setText(id);
    }

    public void setRightTitle(String id) {
        ((TextView) findViewById(R.id.tv_right)).setText(id);
    }

    public void setRightGone() {
        ((TextView) findViewById(R.id.tv_right)).setVisibility(View.GONE);
    }

    public void setRightTitleOnClickListener(View.OnClickListener listener) {
        findViewById(R.id.tv_right).setOnClickListener(listener);
    }

    public void setRightImageOnClickListener(View.OnClickListener listener) {
        findViewById(R.id.iv_right).setOnClickListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CustomeApplication.get().pull(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void finishSelf() {
        finish();
    }

    @Override
    public void showToast(int res) {
        ToastUtil.get().showToast(this, res);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.get().showToast(this, message);

    }

//
//    public LoginBean user() {
//        Object obj = CustomeApplication.get().get(LoginBean.class.getName());
//        if (obj instanceof LoginBean) {
//            return (LoginBean) obj;
//        }
//        return null;
//    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(BaseEvent baseEvent) {

    }

    private void initTakrPhotoOptions() {

    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        takeSuccess(result, resultCode);
        builder.create().dismiss();
    }

    public void takeSuccess(TResult result, int resultCode) {

    }


    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(800 >= 800 ? 800 : 800)
                .enableReserveRaw(true)
                .create();
        takePhoto.onEnableCompress(config, true);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        //取消剪裁大小限制
        // builder.setAspectX(1).setAspectY(5);

        builder.setWithOwnCrop(false);
        return builder.create();
    }

    public void takePhoto(Context context, int resultCode) {
        this.resultCode = resultCode;
//        TakePhoto takePhoto = getTakePhoto();
//        configCompress(takePhoto);
//        configTakePhotoOption(takePhoto);
////        takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
//
//        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        Uri imageUri = Uri.fromFile(file);
//        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
//        new Thread(() -> initDialog());
        initDialog(context);
    }

    private void initDialog(Context context) {
        if (!((Activity) context).isFinishing()) {
//              .setIcon(R.drawable.leak_canary_icon)
            builder = new AlertDialog.Builder(this)
                    .setTitle("选择图片：")
                    //设置两个item
                    .setItems(new String[]{"相机", "图库"}, new android.content.DialogInterface.OnClickListener() {
                        TakePhoto takePhoto = getTakePhoto();

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 1:
                                    configCompress(takePhoto);
                                    configTakePhotoOption(takePhoto);
                                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                                    break;
                                case 0:
//                                TakePhoto takePhoto = getTakePhoto();
                                    configCompress(takePhoto);
                                    configTakePhotoOption(takePhoto);
//        takePhoto.onPickMultipleWithCrop(limit, getCropOptions());

                                    File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                    if (!file.getParentFile().exists()) {
                                        file.getParentFile().mkdirs();
                                    }
                                    Uri imageUri = Uri.fromFile(file);
                                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                                    break;
                                default:
                                    break;
                            }

                        }
                    });
            builder.create().show();
        }
    }

}

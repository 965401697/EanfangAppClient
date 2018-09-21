package com.eanfang.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.eanfang.ui.base.BaseActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;

/**
 * Created by jornl on 2018/1/19.
 */

public class PermissionUtils {

    private static PermissionUtils permissionUtils = new PermissionUtils();

    private BaseActivity activity;

    private BaseActivityWithTakePhoto activityWithTakePhoto;


    private PermissionUtils() {
    }

    public static PermissionUtils get(BaseActivity activity) {
        if (permissionUtils == null) {
            synchronized (PermissionUtils.class) {
                if (permissionUtils == null) {
                    permissionUtils = new PermissionUtils();
                }
            }
        }
        permissionUtils.activity = activity;
        return permissionUtils;
    }

    public static PermissionUtils get(BaseActivityWithTakePhoto activityWithTakePhoto) {
        if (permissionUtils == null) {
            synchronized (PermissionUtils.class) {
                if (permissionUtils == null) {
                    permissionUtils = new PermissionUtils();
                }
            }
        }
        permissionUtils.activityWithTakePhoto = activityWithTakePhoto;
        return permissionUtils;
    }


    /**
     * 请求 定位权限
     *
     * @param callBack
     */
    public void getLocationPermission(PermissionsCallBack callBack) {
        if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                || !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                || !hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            getPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, callBack);
        } else {
            callBack.callBack();
        }
    }

    /**
     * 请求 相机权限
     *
     * @param callBack
     */
    public void getCameraPermission(PermissionsCallBack callBack) {
        if (!hasPermission(Manifest.permission.CAMERA)
                || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            getPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, callBack);
        } else {
            callBack.callBack();
        }

    }

    /**
     * 请求 录音权限
     *
     * @param callBack
     */
    public void getVoicePermission(PermissionsCallBack callBack) {
        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            getPermission(new String[]{Manifest.permission.RECORD_AUDIO}, callBack);
        } else {
            callBack.callBack();
        }

    }

    /**
     * 请求数据读取  写入权限
     *
     * @param callBack
     */
    public void getStoragePermission(PermissionsCallBack callBack) {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            getPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, callBack);
        } else {
            callBack.callBack();
        }

    }


    /**
     * 是否具有指定权限
     *
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        if (activity != null) {
            return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        } else if (activityWithTakePhoto != null) {
            return ActivityCompat.checkSelfPermission(activityWithTakePhoto, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void getPermission(String[] permissionArray, PermissionsCallBack callBack) {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, permissionArray, PermissionsCallBack.callBackCode);
            activity.setPermissionsCallBack(callBack);
        } else if (activityWithTakePhoto != null) {
            ActivityCompat.requestPermissions(activityWithTakePhoto, permissionArray, PermissionsCallBack.callBackCode);
            activityWithTakePhoto.setPermissionsCallBack(callBack);
        }
    }

    /**
     * 请求数据读取  写入权限  位置权限
     *
     * @param callBack
     */
    public void getStorageAndLocationPermission(PermissionsCallBack callBack) {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                || !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                || !hasPermission(Manifest.permission.READ_PHONE_STATE)
                || !hasPermission(Manifest.permission.READ_CONTACTS)
                || !hasPermission(Manifest.permission.CAMERA)
                || !hasPermission(Manifest.permission.RECORD_AUDIO)) {
            getPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.RECORD_AUDIO}, callBack == null ? (() -> {
            }) : callBack);
        } else {
            callBack.callBack();
        }

    }


    public interface PermissionsCallBack {
        /**
         * 获取权限 返回值
         */
        int callBackCode = 40410;

        void callBack();
    }

}






package com.eanfang.kit.rx;

import android.Manifest;
import android.app.Activity;

import com.annimon.stream.function.Supplier;
import com.eanfang.network.holder.ToastHolder;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RxPerm {

    private static RxPerm rxPerm;
    private RxPermissions rxPermissions;

    private RxPerm() {
    }

    public static RxPerm get(Activity activity) {
        if (rxPerm == null) {
            rxPerm = new RxPerm();
        }
        rxPerm.rxPermissions = new RxPermissions(activity);
        return rxPerm;
    }

    /**
     * 获取定位权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> locationPerm() {
        return rxPermissions.requestEach(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        );
    }

    /**
     * 获取定位权限
     *
     * @param success 成功回调
     */
    public void locationPerm(Supplier success) {
        locationPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.get();
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取定位权限失败，请手动开启");
                        return;
                    }
                });
    }

    /**
     * 获取相机权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> cameraPerm() {
        return rxPermissions.requestEach(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

    /**
     * 获取相机权限
     *
     * @param success 成功回调
     */
    public void cameraPerm(Supplier success) {
        cameraPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.get();
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取相机权限失败，请手动开启");
                        return;
                    }
                });
    }


    /**
     * 获取录音权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> voicePerm() {
        return rxPermissions.requestEach(
                Manifest.permission.RECORD_AUDIO
        );
    }

    /**
     * 获取录音权限
     *
     * @param success 成功回调
     */
    public void voicePerm(Supplier success) {
        cameraPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.get();
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取录音权限失败，请手动开启");
                        return;
                    }
                });
    }

    /**
     * 获取存储权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> storagePerm() {
        return rxPermissions.requestEach(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

    /**
     * 获取存储权限
     *
     * @param success 成功回调
     */
    public void storagePerm(Supplier success) {
        cameraPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.get();
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取存储权限失败，请手动开启");
                        return;
                    }
                });
    }

    /**
     * 获取通讯录权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> contactsPerm() {
        return rxPermissions.requestEach(
                Manifest.permission.READ_CONTACTS
        );
    }

    /**
     * 获取通讯录权限
     *
     * @param success 成功回调
     */
    public void contactsPerm(Supplier success) {
        cameraPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.get();
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取通讯录权限失败，请手动开启");
                        return;
                    }
                });
    }
}

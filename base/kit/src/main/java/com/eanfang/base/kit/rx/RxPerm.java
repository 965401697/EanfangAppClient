package com.eanfang.base.kit.rx;

import android.Manifest;
import android.app.Activity;

import com.annimon.stream.function.Consumer;
import com.eanfang.base.network.holder.ToastHolder;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

@SuppressWarnings("CheckResult")
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
     * 一口气获取所有权限的方法
     */
    public void getAllPerm() {
        storagePerm((isSuccess) -> {
        });
        locationPerm((isSuccess) -> {
        });
        cameraPerm((isSuccess) -> {
        });
        voicePerm((isSuccess) -> {
        });
        contactsPerm((isSuccess) -> {
        });
    }

    /**
     * 获取定位权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> locationPerm() {
        return rxPermissions.requestEachCombined(
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
    public void locationPerm(Consumer<Boolean> success) {
        locationPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.accept(true);
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取定位权限失败，请手动开启");
                        success.accept(false);
                    }
                });
    }

    /**
     * 获取相机权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> cameraPerm() {
        return rxPermissions.requestEachCombined(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

    /**
     * 获取相机权限
     *
     * @param success 成功回调
     */
    public void cameraPerm(Consumer<Boolean> success) {
        cameraPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.accept(true);
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取相机权限失败，请手动开启");
                        success.accept(false);
                    }
                });
    }


    /**
     * 获取录音权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> voicePerm() {
        return rxPermissions.requestEachCombined(
                Manifest.permission.RECORD_AUDIO
        );
    }

    /**
     * 获取录音权限
     *
     * @param success 成功回调
     */
    public void voicePerm(Consumer<Boolean> success) {
        voicePerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.accept(true);
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取录音权限失败，请手动开启");
                        success.accept(false);
                    }
                });
    }

    /**
     * 获取存储权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> storagePerm() {
        return rxPermissions.requestEachCombined(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
    }

    /**
     * 获取存储权限
     *
     * @param success 成功回调
     */
    public void storagePerm(Consumer<Boolean> success) {
        storagePerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.accept(true);
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取存储权限失败，请手动开启");
                        success.accept(false);
                    }
                });
    }

    /**
     * 获取通讯录权限
     *
     * @return Observable<Permission>
     */
    public Observable<Permission> contactsPerm() {
        return rxPermissions.requestEachCombined(
                Manifest.permission.READ_CONTACTS
        );
    }

    /**
     * 获取通讯录权限
     *
     * @param success 成功回调
     */
    public void contactsPerm(Consumer<Boolean> success) {
        contactsPerm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        success.accept(true);
                        return;
                    }
                    if (permission.shouldShowRequestPermissionRationale) {
                        ToastHolder.showToast("获取通讯录权限失败，请手动开启");
                        success.accept(false);
                    }
                });
    }

}

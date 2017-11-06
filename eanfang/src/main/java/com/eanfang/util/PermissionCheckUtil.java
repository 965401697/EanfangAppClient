package com.eanfang.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.content.ContextCompat;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/14  14:12
 * @email houzhongzhou@yeah.net
 * @desc 权限判断
 */

public class PermissionCheckUtil {

    private final Context mContext;

    public PermissionCheckUtil(Context mContext) {
        this.mContext = mContext;
    }

    // 判断权限集合
    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否有限
    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 相机权限检查
     * <p>
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
}

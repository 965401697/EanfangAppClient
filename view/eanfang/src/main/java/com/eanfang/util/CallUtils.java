package com.eanfang.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 拨打电话工具类
 * Created by hou on 2017/5/25.
 */

public class CallUtils {
    public static void call(Context mContext, String phoneNumber) {
//        PermissionCheckUtil permissionCheckUtil = new PermissionCheckUtil(mContext);
//
//        boolean lacks = permissionCheckUtil.hasPermissions(Manifest.permission.CALL_PHONE);
//        if (!lacks) {
//            ToastUtil.get().showToast(mContext, "缺少拨打电话的权限");
//            return;
//        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        mContext.startActivity(intent);
        return;
    }
}

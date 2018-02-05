package com.eanfang.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/9  8:38
 * @desc 网络监听的工具类，在网络时2秒内弹出异常界面
 */

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    /**
     * @return 判断是否有网络
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            NetworkInfo.State state = networkInfos[i].getState();
            if (NetworkInfo.State.CONNECTED == state) {


                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (isNetConnected(context)) {
            //网络正常,不执行任何操作.
            return;
        }
    }
}

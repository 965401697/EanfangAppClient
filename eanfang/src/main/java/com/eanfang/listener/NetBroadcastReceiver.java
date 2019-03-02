package com.eanfang.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.ConnectivityChangeUtil;

/**
 * @author guanluocang
 * @data 2019/2/27
 * @description
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetChangeListener listener = BaseFragment.netChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        Log.e("0228Net", "NetBroadcastReceiver changed");
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            boolean netWorkState = ConnectivityChangeUtil.isNetConnected(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            if (listener != null) {
                listener.onChangeListener(netWorkState);
            }
        }
    }

    // 自定义接口
    public interface NetChangeListener {
        void onChangeListener(boolean status);
    }
}

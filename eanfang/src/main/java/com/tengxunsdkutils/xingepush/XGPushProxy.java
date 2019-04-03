package com.tengxunsdkutils.xingepush;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public class XGPushProxy implements IXGPush {
    private static XGPushProxy mXGPushProxy;
    private static final String TAG = "XGPushProxy";
    private IXGPush ixgPush;
    private Context context;

    public static XGPushProxy getInstance(Context context, IXGPush ixgPush) {
        if (mXGPushProxy == null) {
            mXGPushProxy = new XGPushProxy(context, ixgPush);
        }
        return mXGPushProxy;
    }

    public XGPushProxy(Context context, IXGPush ixgPush) {
        this.ixgPush = ixgPush;
        this.context = context;
        this.setContext(context);
    }

    public void registerPush(String account) {
        this.registerPush(account, new IXGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.e(TAG, "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e(TAG, "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }

    public void delAccount(String account) {
        this.delAccount(account, new IXGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.e(TAG, "信鸽退出Success");
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e(TAG, "信鸽退出Fail");
            }
        });
    }

    @Override
    public void setContext(Context c) {
        ixgPush.setContext(c);
    }

    @Override
    public void registerPush(String account, IXGIOperateCallback callback) {
        ixgPush.registerPush(account, callback);
    }

    @Override
    public void unregisterPush() {
        ixgPush.unregisterPush();
    }

    @Override
    public void delAccount(String account, IXGIOperateCallback callback) {
        ixgPush.delAccount(account, callback);
    }

    @Override
    public IXGPushClickedResult onActivityStarted(Activity activity) {
        return ixgPush.onActivityStarted(activity);
    }

    @Override
    public void enableOtherPush(boolean flag) {
        ixgPush.enableOtherPush(flag);
    }

    @Override
    public void enableDebug(boolean debugMode) {
        ixgPush.enableDebug(debugMode);
    }

    @Override
    public void setHuaweiDebug(boolean isHuaweiDebug) {
        ixgPush.setHuaweiDebug(isHuaweiDebug);
    }

    @Override
    public void setMiPush(String appid, String appkey) {
        ixgPush.setMiPush(appid, appkey);
    }

    @Override
    public void setMzPush(String appid, String appkey) {
        ixgPush.setMzPush(appid, appkey);
    }
}

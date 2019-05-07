package com.eanfang.kit.tencent.xingepush;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * created by wtt
 * at 2019/4/2
 * summary:
 */
public class XGPushManagerConfig implements IXGPush {
    private static final String TAG = "XGPushManagerConfig";
    private Context context;

    public XGPushManagerConfig(Context context) {
        this.context=context;
    }

    @Override
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

    /**
     * 启动并注册APP，同时绑定账号,推荐有帐号体系的APP使用（3.2.2不包括3.2.2之前的版本使用，有注册回调）
     *
     * @param account
     * @param callback
     */
    public void registerPush(String account, IXGIOperateCallback callback) {
        XGPushManager.registerPush(context, account, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                callback.onSuccess(o, i);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                callback.onFail(o, i, s);
            }
        });
    }

    /**
     * 反注册，建议在不需要接收推送的时候调用
     */
    @Override
    public void unregisterPush() {
        XGPushManager.unregisterPush(context);
    }

    @Override
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

    /**
     * 解绑指定账号（3.2.2以及3.2.2之后的版本使用，有注册回调）
     *
     * @param account
     * @param callback
     */
    public void delAccount(final String account, IXGIOperateCallback callback) {
        XGPushManager.delAccount(context, account, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                callback.onSuccess(o, i);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                callback.onFail(o, i, s);
            }
        });
    }

    /**
     * Activity被打开的效果统计；获取下发的自定义key-value
     *
     * @param activity
     * @return
     */
    @Override
    public IXGPushClickedResult onActivityStarted(Activity activity) {
        XGPushClickedResult mXGPushClickedResult = XGPushManager.onActivityStarted(activity);
        if(mXGPushClickedResult!=null){
            IXGPushClickedResult mIXGPushClickedResult = new IXGPushClickedResult();
            mIXGPushClickedResult.setTitle(mXGPushClickedResult.getTitle());
            mIXGPushClickedResult.setMsgId(mXGPushClickedResult.getMsgId());
            mIXGPushClickedResult.setContent(mXGPushClickedResult.getContent());
            mIXGPushClickedResult.setActionType((int) mXGPushClickedResult.getActionType());
            return mIXGPushClickedResult;
        }
        return null;
    }

    /**
     * 设置支持第三方厂商推送
     *
     * @param flag
     */
    @Override
    public void enableOtherPush(boolean flag) {
        XGPushConfig.enableOtherPush(context, flag);
    }

    /**
     * 是否开启debug模式，即输出logcat日志重要：为保证数据的安全性，发布前必须设置为false）
     *
     * @param debugMode
     */
    @Override
    public void enableDebug(boolean debugMode) {
        XGPushConfig.enableDebug(context, debugMode);
    }

    /**
     * 华为手机的写日志定位问题
     *
     * @param isHuaweiDebug
     */
    @Override
    public void setHuaweiDebug(boolean isHuaweiDebug) {
        XGPushConfig.setHuaweiDebug(isHuaweiDebug);
    }

    @Override
    public void setMiPush(String appid, String appkey) {
        this.setMiPushAppId(context, appid);
        this.setMiPushAppKey(context, appkey);
    }

    @Override
    public void setMzPush(String appid, String appkey) {
        this.setMzPushAppId(context, appid);
        this.setMzPushAppKey(context, appkey);
    }

    /**
     * 设置小米推送APPID
     *
     * @param context
     * @param appid
     */
    private void setMiPushAppId(Context context, String appid) {
        XGPushConfig.setMiPushAppId(context, appid);
    }

    /**
     * 设置小米推送APPKEY
     *
     * @param context
     * @param appkey
     */
    private void setMiPushAppKey(Context context, String appkey) {
        XGPushConfig.setMiPushAppKey(context, appkey);
    }

    /**
     * 设置魅族推送APPID
     *
     * @param context
     * @param appid
     */
    private void setMzPushAppId(Context context, String appid) {
        XGPushConfig.setMiPushAppId(context, appid);
    }

    /**
     * 设置魅族推送APPKEY
     *
     * @param context
     * @param appkey
     */
    private void setMzPushAppKey(Context context, String appkey) {
        XGPushConfig.setMzPushAppKey(context, appkey);
    }
}

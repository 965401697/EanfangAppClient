package com.eanfang.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.loading.LoadKit;
import com.eanfang.config.ErrorCodeConst;
import com.eanfang.ui.activity.NoPermissionActivity;
import com.eanfang.util.ToastUtil;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;

import cn.hutool.core.util.StrUtil;

import static com.eanfang.config.ErrorCodeConst.MISSING_LOGIN;


/**
 * Created by jornl on 2017/8/30.
 */

/**
 * 实现接口 必须加泛型
 *
 * @param <T>
 */
public class EanfangCallback<T> extends StringCallback implements Callback<String> {

    /**
     * 加载弹窗
     */
    private Dialog loadingDialog;
    /**
     * 当前执行 请求的 activity
     */
    private Activity activity;
    private Type type;
    private Class<T> clazz;

    private ISuccess<T> iSuccess;

    private ISuccessArray<T> iSuccessArray;

    public EanfangCallback(Activity activity, boolean showDialog, Class clazz, ISuccess<T> iSuccess) {
        this.activity = activity;
        this.clazz = clazz;
        this.iSuccess = iSuccess;
        if (showDialog) {
            loadingDialog = LoadKit.dialog(activity);
        }
    }

    public EanfangCallback(Activity activity, boolean showDialog, Class clazz, boolean isArray, ISuccessArray<T> iSuccess) {
        this.activity = activity;
        this.clazz = clazz;
        this.iSuccessArray = iSuccess;
        if (showDialog) {
            loadingDialog = LoadKit.dialog(activity);
        }
    }

    /**
     * 实现接口 必须加泛型
     *
     * @param activity
     */
    public EanfangCallback(Activity activity, boolean showDialog, Type type) {
        this.activity = activity;
        this.type = type;
        if (showDialog) {
            loadingDialog = LoadKit.dialog(activity);
        }
    }

    /**
     * 实现接口 必须加泛型
     *
     * @param activity
     */
    public EanfangCallback(Activity activity, boolean showDialog, Class<T> clazz) {
        this.activity = activity;
        this.clazz = clazz;
        if (showDialog) {
            loadingDialog = LoadKit.dialog(activity);
        }
    }

    /**
     * 请求结束
     */
    @Override
    public final void onFinish() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            LoadKit.closeDialog(loadingDialog);
        }
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public final void onSuccess(Response<String> response) {
        try {
            if (StrUtil.isEmpty(response.toString()) || StrUtil.isEmpty(response.body())) {
                onServerError("服务器无响应");
                return;
            }
            //获得响应json
            JSONObject resultJson = null;

            Class<T> clazz = getClazz();
            //指定date类型自动格式化
            JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";

            resultJson = JSONObject.parseObject(response.body());
            Integer code = -100;
            String message = null;
            JSONObject resultObject = null;
            JSONArray resultArray = null;
            String resultString = null;
            //返回的编码
            if (resultJson.containsKey("code")) {
                code = resultJson.getInteger("code");
            }
            //消息
            if (resultJson.containsKey("message")) {
                message = resultJson.getString("message");
            }
            //消息数量
//            if (resultJson.containsKey("noticeCount")) {
//                updateNoticeCount(resultJson);
//            }
            if (resultJson.containsKey("data")) {
                if (clazz.getName().contains("String")) {
                    resultString = resultJson.get("data").toString();
                } else if (resultJson.get("data") instanceof JSONArray) {
                    resultArray = resultJson.getJSONArray("data");
                } else if (resultJson.get("data") instanceof JSONObject) {
                    resultObject = resultJson.getJSONObject("data");
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", resultJson.get("data").toString());
                    resultObject = jsonObject;
                }
            }

            switch (code) {
                //请求成功 回调 success
                case ErrorCodeConst.REQUEST_SUCCESS:
                    T result = null;
//                    List<T> list = new ArrayList();
                    if (resultArray != null) {
                        onSuccessArray(resultArray.toJavaList(clazz));
                    } else if (resultObject != null) {
                        try {
                            onSuccess(resultObject.toJavaObject(clazz));
                        } catch (Exception e) {
//                            e.printStackTrace();
                            ToastUtil.get().showToast(activity, e.getMessage());
//                            this.onSuccess((T) null);
                        }
                    } else {
//                        result = (T) resultString;
                        onSuccess((T) resultString);
                    }
                    break;
                //服务端无数据 回调
                case ErrorCodeConst.REQUEST_NO_DATA:
                    onNoData(message);
                    break;
                //请求缺少参数 回调
                case ErrorCodeConst.MISSING_PARAMETER:
                    onMissParam(message);
                    break;
                //服务端异常 回调
                case ErrorCodeConst.SERVICE_ERROR:
                    onServerError(message);
                    break;
                case ErrorCodeConst.REQUEST_COMMIT_AGAIN:
                    onCommitAgain();
                    break;
                case MISSING_LOGIN:
                case 50012:
                case 50013:
                    onFail(code, message, null);
                case 50014:
//                    onFail(code, message, null);
                    //taoken 过期  只弹出toast
                    EventBus.getDefault().post(code);
                    break;
                // 无权限
                case ErrorCodeConst.PERMISSION_DENIED:
                    Intent intent = new Intent(BaseApplication.get(), NoPermissionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    BaseApplication.get().startActivity(intent);
                    activity.finish();
                    break;
                default:
                    onFail(code, message, null);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onError("请求失败，请重试");
        }
    }


//    private void updateNoticeCount(JSONObject resultJson) {
//        String classMainName = "MainActivity.initMessageCount";
//        String classMyName = "ContactListFragment.messageCount";
//        int mainActivityCount = Var.get(classMainName).getVar();
//        int myFragmentCount = Var.get(classMyName).getVar();
//        int noticeCount = resultJson.getInteger("noticeCount");
//        if (mainActivityCount != noticeCount) {
//            Var.get(classMainName).setVar(noticeCount);
//        }
//        if (myFragmentCount != noticeCount) {
//            Var.get(classMyName).setVar(noticeCount);
//        }
//    }

    /**
     * 请求服务器失败
     *
     * @param response
     */
    @Override
    public final void onError(Response<String> response) {
        // onError(response.body());
        if (isConnected()) {
            onFail(0, "哎呀，服务器好像罢工了", null);
        } else {
            ToastUtil.get().showToast(this.activity, "网络中断，请检查网络连接");
        }
    }

    /**
     * 重复请求
     */
    public void onCommitAgain() {

    }

    /**
     * 请求成功 调用
     */
    public void onSuccess(T bean) {
        if (iSuccess != null) {
            iSuccess.success(bean);
        }
    }

    public void onSuccessArray(List<T> bean) {
        if (iSuccessArray != null) {
            iSuccessArray.success(bean);
        }
    }

    /**
     * 请求失败调用
     *
     * @param message
     */
    public void onError(String message) {
        ToastUtil.get().showToast(this.activity, message);
    }

    /**
     * 无数据 调用
     */
    public void onNoData(String message) {
        if (StrUtil.isEmpty(message)) {
            message = "没有数据啦";
        }
        ToastUtil.get().showToast(this.activity, message);
    }

    /**
     * 缺少参数 调用
     */
    public void onMissParam(String message) {
        if (!StrUtil.isEmpty(message)) {
            ToastUtil.get().showToast(this.activity, message);
        } else {
            ToastUtil.get().showToast(this.activity, "缺少请求参数，请返回后重试");
        }
    }

    /**
     * 服务器异常
     *
     * @param message
     */
    public void onServerError(String message) {
        if (!StrUtil.isEmpty(message)) {
            ToastUtil.get().showToast(this.activity, message);
        } else {
            ToastUtil.get().showToast(this.activity, "哎呀，服务器好像罢工了");
        }
    }

//    public void onMissingLogin() {
//        ToastUtil.get().showToast(this.activity, "当前登陆失效了，请重新登陆。");
//    }

    /**
     * 服务器返回其他异常情况
     *
     * @param code
     * @param message
     * @param jsonObject
     */
    public void onFail(Integer code, String message, JSONObject jsonObject) {
        ToastUtil.get().showToast(this.activity, message);
    }

    private Class getClazz() {
        if (type != null) {
            return (Class<T>) type;
        } else if (clazz != null) {
            return clazz;
        } else {
            return JSONObject.class;
        }
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ISuccess<T> {
        void success(T bean);
    }

    public interface ISuccessArray<T> {
        void success(List<T> bean);
    }

}

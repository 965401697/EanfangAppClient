package net.eanfang.client.network.request;

import android.app.Activity;
import android.app.Dialog;

import com.eanfang.util.DialogUtil;
import com.eanfang.util.ToastUtil;
import com.google.gson.Gson;
import com.okgo.callback.StringCallback;
import com.okgo.model.Response;
import com.okgo.request.base.Request;

import net.eanfang.client.config.ErrorCodeConst;
import net.eanfang.client.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;



/**
 * Created by jornl on 2017/8/30.
 */

/**
 * 实现接口 必须加泛型
 *
 * @param <T>
 */
public class EanfangCallback<T> extends StringCallback {

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

    /**
     * 实现接口 必须加泛型
     *
     * @param activity
     */
    public EanfangCallback(Activity activity, boolean showDialog) {
        this.activity = activity;
        if (showDialog) {
            loadingDialog = DialogUtil.createLoadingDialog(activity);
        }
    }

    public EanfangCallback(Activity activity, boolean showDialog, Class<T> clazz, ISuccess<T> iSuccess) {
        this.activity = activity;
        this.clazz = clazz;
        this.iSuccess = iSuccess;
        if (showDialog) {
            loadingDialog = DialogUtil.createLoadingDialog(activity);
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
            loadingDialog = DialogUtil.createLoadingDialog(activity);
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
            loadingDialog = DialogUtil.createLoadingDialog(activity);
        }
    }

    /**
     * 请求开始
     *
     * @param request
     */
    @Override
    public final void onStart(Request<String, ? extends Request> request) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 请求结束
     */
    @Override
    public final void onFinish() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
            }

        }
    }

    @Override
    public final void onSuccess(Response<String> response) {
        //获得响应json
        JSONObject resultJson = null;
        try {
            if (StringUtils.isEmpty(response.toString()) || StringUtils.isEmpty(response.body())) {
                onServerError("服务器无响应");
                return;
            }

            resultJson = new JSONObject(response.body());
            Integer code = -100;
            String message = "响应参数错误";
            JSONObject resultObject = null;
            if (resultJson.has("code")) {
                code = resultJson.getInt("code");
            }
            if (resultJson.has("message")) {
                message = resultJson.getString("message");
            }
            if (resultJson.has("data")) {
                resultObject = resultJson.getJSONObject("data");
            }
            Gson gson = new Gson();
            switch (code) {
                //请求成功 回调 success
                case ErrorCodeConst.REQUEST_SUCCESS:
                    T result = null;
                    //获取对象
                    if (resultObject != null && resultObject.length() > 0) {
                        if (type != null) {
                            result = gson.fromJson(resultObject.toString(), type);
                        } else if (clazz != null) {
                            result = gson.fromJson(resultObject.toString(), clazz);
                        } else {
                            Type genType = getClass().getGenericSuperclass();
                            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
                            if (((Class) type).getName().equals(JSONObject.class.getName())) {
                                result = (T) resultObject;
                            } else {
                                result = gson.fromJson(resultObject.toString(), type);
                            }
                        }
                    }
                    onSuccess(result);
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
                default:
                    onFail(code, message, resultObject);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 请求服务器失败
     *
     * @param response
     */
    @Override
    public final void onError(Response<String> response) {
        onError(response.body());
    }

    /**
     * 请求成功 调用
     */
    public void onSuccess(T bean) {
        if (iSuccess != null) {
            iSuccess.success(bean);
        }
    }

    /**
     * 请求失败调用
     *
     * @param message
     */
    public void onError(String message) {
        ToastUtil.get().showToast(this.activity, "系统异常，返回后请重试");
    }

    /**
     * 无数据 调用
     */
    public void onNoData(String message) {
        if (StringUtils.isEmpty(message)) {
            message = "没有数据啦";
        }
        ToastUtil.get().showToast(this.activity, message);
    }

    /**
     * 缺少参数 调用
     */
    public void onMissParam(String message) {

        ToastUtil.get().showToast(this.activity, "参数缺失");
    }

    /**
     * 服务器异常
     *
     * @param message
     */
    public void onServerError(String message) {
        ToastUtil.get().showToast(this.activity, "哎呀，服务器好像罢工了");
    }

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

    public interface ISuccess<T> {
        void success(T bean);
    }
}

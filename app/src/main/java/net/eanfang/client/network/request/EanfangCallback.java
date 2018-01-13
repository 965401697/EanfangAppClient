package net.eanfang.client.network.request;

import android.app.Activity;
import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.util.DialogUtil;
import com.eanfang.util.ToastUtil;
import com.okgo.callback.StringCallback;
import com.okgo.model.Response;
import com.okgo.request.base.Request;

import net.eanfang.client.config.ErrorCodeConst;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


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

    private ISuccessArray<T> iSuccessArray;

    /**
     * 实现接口 必须加泛型
     *
     * @param activity
     */
    // TODO: 2017/12/7 废弃
    public EanfangCallback(Activity activity, boolean showDialog) {
        this.activity = activity;
        if (showDialog) {
            loadingDialog = DialogUtil.createLoadingDialog(activity);
        }
    }

    public EanfangCallback(Activity activity, boolean showDialog, Class clazz, ISuccess<T> iSuccess) {
        this.activity = activity;
        this.clazz = clazz;
        this.iSuccess = iSuccess;
        if (showDialog) {
            loadingDialog = DialogUtil.createLoadingDialog(activity);
        }
    }

    // TODO: 2017/12/7 废弃 
    public EanfangCallback(Activity activity, boolean showDialog, ISuccess<T> iSuccess) {
        this.activity = activity;
        // this.clazz = clazz;
        this.iSuccess = iSuccess;
        if (showDialog) {
            loadingDialog = DialogUtil.createLoadingDialog(activity);
        }
    }

    public EanfangCallback(Activity activity, boolean showDialog, Class clazz, boolean isArray, ISuccessArray<T> iSuccess) {
        this.activity = activity;
        this.clazz = clazz;
        this.iSuccessArray = iSuccess;
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

    /*** @param response
     */
    @Override
    public final void onSuccess(Response<String> response) {
        //获得响应json
        JSONObject resultJson = null;
        //指定date类型自动格式化
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
        try {
            if (StringUtils.isEmpty(response.toString()) || StringUtils.isEmpty(response.body())) {
                onServerError("服务器无响应");
                return;
            }

             resultJson = JsonUtils.str2JSON(response.body());
            Integer code = -100;
            String message = "响应参数错误";
            JSONObject resultObject = null;
            JSONArray resultArray = null;
            if (resultJson.containsKey("code")) {
                code = resultJson.getInteger("code");
            }
            if (resultJson.containsKey("message")) {
                message = resultJson.getString("message");
            }
            if (resultJson.containsKey("data")) {
                if (resultJson.get("data") instanceof JSONArray) {
                    resultArray = resultJson.getJSONArray("data");
                } else {
                    resultObject = resultJson.getJSONObject("data");
                }
            }

            switch (code) {
                //请求成功 回调 success
                case ErrorCodeConst.REQUEST_SUCCESS:
                    T result = null;
                    List<T> list = new ArrayList();
                    Class<T> clazz = getClazz();

                    if (resultArray != null) {
                        for (int i = 0; i < resultArray.size(); i++) {
                            list.add(JSONObject.parseObject(resultArray.getString(i), clazz));
                        }
                        onSuccessArray(list);
                    } else if (resultObject != null) {
                        result = JSONObject.parseObject(resultObject.toJSONString(), clazz);
                        onSuccess(result);
                    }else {
                        onSuccess(result);
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

    public interface ISuccessArray<T> {
        void success(List<T> bean);
    }

    private Class getClazz() {
        if (type != null) {
            return (Class<T>) type;
        } else if (clazz != null) {
            return clazz;
        } else {
            Type genType = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            if (((Class) type).getName().equals(JSONObject.class.getName())) {
                return JSONObject.class;
            } else {
                return ((Class<T>) type);
            }
        }

    }

}

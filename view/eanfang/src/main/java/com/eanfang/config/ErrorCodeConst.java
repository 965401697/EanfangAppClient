package com.eanfang.config;

public class ErrorCodeConst {

    /**
     * 请求成功
     */
    public static final int REQUEST_SUCCESS = 20000;
    public static final String REQUEST_SUCCESS_STR = "请求成功";

    /**
     * 服务器错误
     */
    public static final int SERVICE_ERROR = 50000;
    public static final String SERVICE_ERROR_STR = "服务器出现异常，请稍后重试";

    /**
     * 权限不足
     */
    public static final int PERMISSION_DENIED = 50030;
    public static final String PERMISSION_DENIED_STR = "权限不足";

    /**
     * 请求缺少参数
     */
    public static final int MISSING_PARAMETER = 50050;
    public static final String MISSING_PARAMETER_STR = "缺少参数";

    /**
     * 请求失败
     */
    public static final int REQUEST_FAIL = 50024;
    public static final String REQUEST_FAIL_STR = "请求失败";

    /**
     * 登陆失效
     */
    public static final int MISSING_LOGIN = 50011;
    public static final String MISSING_LOGIN_STR = "登陆失效";

    /**
     * 没有查询到数据
     */
    public static final int REQUEST_NO_DATA = 50023;
    public static final String REQUEST_NO_DATA_STR = "暂无更多数据";

    public static final int REQUEST_COMMIT_AGAIN = 10000;
    public static final String REQUEST_COMMIT_AGAIN_STR = "重复提交太快";


}

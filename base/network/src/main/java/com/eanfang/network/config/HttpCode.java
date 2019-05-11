package com.eanfang.network.config;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class HttpCode {

    //请求太快了
    public static final int CODE_REQUEST_FAST = 10000;
    //请求成功
    public static final int CODE_SUCCESS = 20000;
    //未知异常 服务器错误
    public static final int CODE_UNKNOWN = 50000;
    //token失效
    public static final int CODE_TOKEN_INVALID = 50010;
    //账号密码错误
    public static final int CODE_ACCOUNT_INVALID = 50020;
    //无权限访问
    public static final int CODE_PERMISSION_INVALID = 50030;
    //缺少请求参数
    public static final int CODE_PARAMETER_INVALID = 50050;
    //请求来源错误
    public static final int CODE_FROM_INVALID = 50060;
    //没找到数据
    public static final int CODE_RESULT_INVALID = 50070;

    public static final int CODE_CONNECTION_FAILED = -5;

    public static final int CODE_FORBIDDEN = -6;


}

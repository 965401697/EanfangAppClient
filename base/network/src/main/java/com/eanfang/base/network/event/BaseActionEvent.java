package com.eanfang.base.network.event;

import com.eanfang.base.network.event.base.BaseEvent;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Getter
@Setter
public class BaseActionEvent extends BaseEvent {

    //默认值
    public static final int DEFAULT = 0;
    //显示loading
    public static final int SHOW_LOADING_DIALOG = 1;
    //关闭loading
    public static final int DISMISS_LOADING_DIALOG = 2;
    //显示吐司
    public static final int SHOW_TOAST = 3;
    //页面finish
    public static final int FINISH = 4;

    public static final int FINISH_WITH_RESULT_OK = 5;

    //请求成功
    public static final int SUCCESS = 200;
    //空数据
    public static final int EMPTY_DATA = 204;
    //登录失效
    public static final int TOKEN_ERROR = 401;
    //无权访问
    public static final int PERMISSION_ERROR = 407;
    //请求未找到
    public static final int NOT_FOUND = 404;
    //请求超时
    public static final int TIME_OUT = 408;
    //请求太快了
    public static final int REQUEST_FAST = 409;
    //缺少参数或参数错误
    public static final int PARAM_ERROR = 412;
    //服务器错误
    public static final int SERVER_ERROR = 500;


    private String message;

    public BaseActionEvent() {
        super(DEFAULT);
    }

    public BaseActionEvent(int action) {
        super(action);
    }
}
package com.eanfang.kit.tencent.xingepush;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public interface IXGIOperateCallback {
    void onSuccess(Object data, int flag);

    void onFail(Object data, int errCode, String msg);
}

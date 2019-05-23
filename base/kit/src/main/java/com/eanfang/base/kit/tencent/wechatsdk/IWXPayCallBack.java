package com.eanfang.base.kit.tencent.wechatsdk;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public interface IWXPayCallBack {
    void onSuccess();

    void onFail(String msg);

    void onCancel();
}

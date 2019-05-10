package com.eanfang.kit.ali.alipay;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public interface IALiPayCallBack {
    void onSuccess();

    void onFail(String msg);

    void onCancel();
}

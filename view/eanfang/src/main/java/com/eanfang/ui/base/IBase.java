package com.eanfang.ui.base;


/**
 * IBase
 *
 * @author wen
 * @desc activity扩展接口
 * @date 2017/3/2
 */
@Deprecated
public interface IBase {
    void finishSelf();

    void showToast(int res);

    void showToast(String message);

    boolean isFinishing();
}

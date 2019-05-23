package com.eanfang.sdk.areachoose;

/**
 * @author liangkailun
 * Date ：2019/5/9
 * Describe :地区选择改变的回调
 */
public interface AreaCheckChangeListener {
    void onCheckAreaChange(int onPos, int secPos, int thirdPos, boolean isCheck);
}

package net.eanfang.worker.ui.interfaces;

/**
 * @author liangkailun
 * Date ：2019/5/9
 * Describe :地区选择改变的回调
 */
public interface AreaCheckChangeListener {
    /**
     * 区域选择改变回调
     * @param onPos 省坐标
     * @param secPos 市坐标
     * @param thirdPos 区坐标
     * @param isCheck 是否选中
     */
    void onCheckAreaChange(int onPos,int secPos,int thirdPos,boolean isCheck);
}

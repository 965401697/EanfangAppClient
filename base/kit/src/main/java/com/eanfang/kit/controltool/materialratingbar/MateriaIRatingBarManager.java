package com.eanfang.kit.controltool.materialratingbar;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class MateriaIRatingBarManager implements IMaterialRatingBar {
    @Override
    public void setNumStars(MaterialRatingBar materialRatingBar, int numStars) {
        materialRatingBar.setNumStars(numStars);
    }

    @Override
    public void setIsIndicator(MaterialRatingBar materialRatingBar, boolean isIndicator) {
        materialRatingBar.setIsIndicator(isIndicator);
    }
}

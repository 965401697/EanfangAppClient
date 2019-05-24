package com.eanfang.base.widget.controltool.numberprogress;


import com.vector.update_app.view.NumberProgressBar;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class NumProgressManager implements INumberProgressBar {
    @Override
    public void setProgress(NumberProgressBar numberProgressBar, int progress) {
        numberProgressBar.setProgress(progress);
    }
}

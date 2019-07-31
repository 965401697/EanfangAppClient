package com.eanfang.base.widget.controltool.numberprogress;


import com.vector.update_app.view.UpdateNumberProgressBar;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class NumProgressManager implements INumberProgressBar {
    @Override
    public void setProgress(UpdateNumberProgressBar updateNumberProgressBar, int progress) {
        updateNumberProgressBar.setProgress(progress);
    }
}

/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-4-7 上午9:36
 * Copyright (c) 2017. All rights reserved.
 */

package com.project.eanfang.zxing.android;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * Simple listener used to exit the app in a few cases.
 * 用于在少数情况下退出App的监听
 *
 * @author Sean Owen
 */
public final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {

    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        run();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        run();
    }

    private void run() {
        activityToFinish.finish();
    }

}

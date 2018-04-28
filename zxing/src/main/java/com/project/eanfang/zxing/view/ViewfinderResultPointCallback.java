/*
 * Created by 岱青海蓝信息系统(北京)有限公司 on 17-4-7 上午9:36
 * Copyright (c) 2017. All rights reserved.
 */

package com.project.eanfang.zxing.view;


import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public final class ViewfinderResultPointCallback implements ResultPointCallback {

    private final ViewfinderView viewfinderView;

    public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
        this.viewfinderView = viewfinderView;
    }

    @Override
    public void foundPossibleResultPoint(ResultPoint point) {
        viewfinderView.addPossibleResultPoint(point);
    }

}

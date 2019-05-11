package com.eanfang.kit.loading.callback;

import android.content.Context;
import android.view.View;

import com.eanfang.kit.R;
import com.kingja.loadsir.callback.Callback;


/**
 * @author jornl
 * @date 2019-05-10 09:12:22
 * 加载中 的提示页
 */
public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.dialog_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}

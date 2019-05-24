package com.eanfang.base.kit.loading.callback;

import android.content.Context;
import android.view.View;

import com.eanfang.base.kit.R;
import com.kingja.loadsir.callback.Callback;


/**
 * @author jornl
 * @date 2019-05-10 09:12:22
 * 报修页面的 加载效果
 */
public class RepairPlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_repair_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}

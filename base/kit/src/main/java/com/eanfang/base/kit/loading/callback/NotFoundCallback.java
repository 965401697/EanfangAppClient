package com.eanfang.base.kit.loading.callback;

import com.eanfang.base.kit.R;
import com.kingja.loadsir.callback.Callback;

public class NotFoundCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_not_found;
    }
}


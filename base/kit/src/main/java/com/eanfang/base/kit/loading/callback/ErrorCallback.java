package com.eanfang.base.kit.loading.callback;


import com.eanfang.base.kit.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author jornl
 * @date 2019-05-10 09:12:22
 * 服务器报错提示页
 */
public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}

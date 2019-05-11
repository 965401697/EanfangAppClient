package com.eanfang.kit.loading.callback;

import com.eanfang.kit.R;
import com.kingja.loadsir.callback.Callback;


/**
 * @author jornl
 * @date 2019-05-10 09:12:22
 * 没有数据 的提示页
 */
public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}

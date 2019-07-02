package com.eanfang.base.kit.loading.callback;

import com.eanfang.base.kit.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author guanluocang
 * @data 2019/7/1
 * @description 通用提交确认页面
 */
public class CommitSuccessCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_commit_success;
    }

}

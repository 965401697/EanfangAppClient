/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package net.eanfang.client.ui.base;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.rx.RxDialog;
import com.eanfang.biz.model.vo.BaseVo;
import com.eanfang.ui.base.BaseActivity;


/**
 * BaseAppCompatFragmentActivity
 *
 * @author hr
 * Created at 2016/1/1 11:33
 * @desc activity基类
 */

public abstract class BaseClientActivity extends BaseActivity {

    @SuppressLint("CheckResult")
    public void into(InvokingData invokingData) {
        RxDialog dialog = new RxDialog(this);
        dialog.setTitle("提示")
                .setMessage("是否使用缓存数据")
                .setPositiveText("是")
                .setNegativeText("使用")
                .setNeutralText("不使用")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                    } else if (code.equals(RxDialog.NEGATIVE)) {
                        //使用
                        if (invokingData != null)
                            invokingData.invoke();

                    } else if (code.equals(RxDialog.NEUTRAL)) {
                        //不使用
                        CacheKit.get().remove("installOrder");
                    }
                });
    }

    public interface InvokingData {
        void invoke();
    }

    @SuppressLint("CheckResult")
    public void out(BaseVo baseVo) {
        RxDialog dialog = new RxDialog(this);
        dialog.setTitle("提示")
                .setMessage("是否放弃报装并保存已编辑信息？")
                .setPositiveText("是")
                .setNegativeText("否")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        CacheKit.get().putVo("installOrder", baseVo);
                        finish();
                    } else if (code.equals(RxDialog.NEGATIVE)) {
                    }
                });
    }

}

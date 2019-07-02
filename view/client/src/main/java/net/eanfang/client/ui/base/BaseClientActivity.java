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
    public void into(String key,InvokingData invokingData) {
        RxDialog dialog = new RxDialog(this);
        dialog.setTitle("提示")
                .setMessage("是否使用缓存数据")
                .setPositiveText("是")
                .setNegativeText("否")
//                .setNeutralText("不使用")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        //使用
                        if (invokingData != null)
                            invokingData.invoke();
                    } else if (code.equals(RxDialog.NEGATIVE)) {
                        //不使用
                        CacheKit.get().remove(key);

                    } else if (code.equals(RxDialog.NEUTRAL)) {

                    }
                });
    }

    public interface InvokingData {
        void invoke();
    }

    @SuppressLint("CheckResult")
    public void out(String key,BaseVo baseVo) {
        RxDialog dialog = new RxDialog(this);
        dialog.setTitle("提示")
                .setMessage("是否放弃报装并保存已编辑信息？")
                .setPositiveText("是")
                .setNegativeText("否")
                .dialogToObservable()
                .subscribe((code) -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        CacheKit.get().putVo(key, baseVo);
                        finish();
                    } else if (code.equals(RxDialog.NEGATIVE)) {
                        finish();
                    }
                });
    }

}

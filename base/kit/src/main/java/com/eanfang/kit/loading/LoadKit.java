package com.eanfang.kit.loading;

import android.app.Dialog;
import android.content.Context;

import com.eanfang.kit.loading.callback.EmptyCallback;
import com.eanfang.kit.loading.callback.ErrorCallback;
import com.eanfang.kit.loading.callback.LoadingCallback;
import com.eanfang.kit.loading.callback.NotFoundCallback;
import com.eanfang.kit.loading.callback.PermissionCallback;
import com.eanfang.kit.loading.callback.TimeoutCallback;
import com.eanfang.network.holder.ContextHolder;
import com.kingja.loadsir.core.LoadSir;

import cn.hutool.core.util.StrUtil;

public class LoadKit {

    public static Dialog dialog() {
        return dialog(null, null);
    }

    /**
     * 获取一个弹窗loading实例
     *
     * @param context context
     * @param msg     msg
     * @return Dialog
     */
    public static Dialog dialog(Context context, String msg) {
        return DialogLoading.createLoadingDialog(context != null ? context : ContextHolder.getContext(), StrUtil.isNotEmpty(msg) ? msg : "加载中...");
    }

    /**
     * 设置弹窗loading的文本
     *
     * @param dialog dialog
     * @param text   text
     */
    public static void setText(Dialog dialog, String text) {
        DialogLoading.setText(dialog, text);
    }

    /**
     * 关闭弹窗
     *
     * @param dialog dialog
     */
    public static void closeDialog(Dialog dialog) {
        DialogLoading.closeDialog(dialog);
    }

    /**
     * 初始化 LoadSir
     */
    public static void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new NotFoundCallback())
                .addCallback(new PermissionCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
}

package com.eanfang.kit.loading;

import android.app.Dialog;
import android.content.Context;

import com.eanfang.network.holder.ContextHolder;

import cn.hutool.core.util.StrUtil;

public class LoadKit {

    public static Dialog dialog() {
        return dialog(null, null);
    }

    public static Dialog dialog(Context context, String msg) {
        return Loading.createLoadingDialog(context != null ? context : ContextHolder.getContext(), StrUtil.isNotEmpty(msg) ? msg : "加载中...");
    }

    public static void setText(Dialog dialog, String text) {
        Loading.setText(dialog, text);
    }

    public static void closeDialog(Dialog dialog) {
        Loading.closeDialog(dialog);
    }
}

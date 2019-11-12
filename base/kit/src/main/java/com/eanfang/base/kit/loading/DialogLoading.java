package com.eanfang.base.kit.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.base.kit.R;
import com.wang.avi.AVLoadingIndicatorView;

import cn.hutool.core.util.StrUtil;

class DialogLoading {

    static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = v.findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
        AVLoadingIndicatorView avlView = v.findViewById(R.id.avi);
        avlView.show();
        //没有文字 隐藏
        if (!StrUtil.isEmpty(msg)) {
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(msg);// 设置加载信息
        } else {
            tipTextView.setVisibility(View.GONE);
        }

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);

//        loadingDialog.setOnShowListener((listener) -> avlView.show());
//        loadingDialog.setOnDismissListener((listener) -> avlView.hide());

        return loadingDialog;
    }

    static void setText(Dialog dialog, String text) {
        TextView textView = dialog.findViewById(R.id.tipTextView);
        textView.setText(text);
    }

    /**
     * 关闭dialog
     * <p>
     * http://blog.csdn.net/qq_21376985
     *
     * @param mDialogUtils
     */
    static void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
            mDialogUtils.cancel();
        }
    }

}

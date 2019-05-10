package com.eanfang.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.R;


/**
 * @author wen
 *         Created at 2017/3/2
 * @desc
 */
public class ToastUtil {
    static volatile ToastUtil toastUtil = new ToastUtil();
    Toast toast;

    private ToastUtil() {
    }

    public static ToastUtil get() {
        if (toastUtil == null) {
            synchronized (ToastUtil.class) {
                if (toastUtil == null) {
                    toastUtil = new ToastUtil();
                }
            }
        }
        return toastUtil;
    }

    public void showToast(Context context, String str) {
        dimissToast();
        toast = new Toast(context instanceof Activity ? context.getApplicationContext() : context);
        View view = LayoutInflater.from(context).inflate(R.layout.j_toast, null);
        toast.setView(view);
        ((TextView) view.findViewById(R.id.progress_message)).setText(str);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    public void dimissToast() {
        if (toast != null) {
            toast.cancel();
        }
        toast = null;
    }

    public void showToast(Context context, int id) {
        if (id == -1) {
            return;
        }
        String str = context.getString(id);
        if (str != null) {
            this.showToast(context, str);
        }
    }


}

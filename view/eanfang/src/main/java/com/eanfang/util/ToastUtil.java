package com.eanfang.util;

import android.content.Context;
import android.widget.Toast;

import com.eanfang.network.holder.ToastHolder;


/**
 * @author wen
 * Created at 2017/3/2
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
        ToastHolder.showToast(context, str);
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

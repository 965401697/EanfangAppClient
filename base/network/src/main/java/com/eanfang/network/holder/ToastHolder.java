package com.eanfang.network.holder;

import android.content.Context;
import android.widget.Toast;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Getter
@Setter
public class ToastHolder {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message) {
        showToast(ContextHolder.getContext(), message);
    }

}

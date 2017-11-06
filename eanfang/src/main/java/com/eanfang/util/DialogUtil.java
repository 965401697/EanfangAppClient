package com.eanfang.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eanfang.R;


/**
 * Created by qianjin on 2015/9/22.
 */
public class DialogUtil {

    public static AlertDialog createAlertInfoDialog(Context context, String title, String msg) {

        TextView view = new TextView(context);
        view.setText("\n" + msg + "\n");
        view.setGravity(Gravity.CENTER);
        view.setTextSize(18);
        view.setPadding(20, 0, 20, 0);

        return new AlertDialog.Builder(context).setView(view)
                .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }




    public static Dialog createTipDialog(final Context context, String title, String msgContent, View.OnClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.tip_dialog, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg_title);
        TextView tvMsgContent = (TextView) view.findViewById(R.id.tv_msg_content);
        tvMsg.setText(title);
        tvMsgContent.setText(msgContent);
        final Dialog dialog = new Dialog(context, R.style.my_tip_dialog);
        dialog.setContentView(view);
        view.findViewById(R.id.btn_ok).setOnClickListener(listener);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;

    }

    public static Dialog createAuthDialog(final Context context, String title, String msgContent) {
        View view = LayoutInflater.from(context).inflate(R.layout.tip_auth_dialog, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg_title);
        TextView tvMsgContent = (TextView) view.findViewById(R.id.tv_msg_content);
        tvMsg.setText(title);
        tvMsgContent.setText(msgContent);
        final Dialog dialog = new Dialog(context, R.style.my_tip_dialog);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    public static Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(v);
        return dialog;
    }


}

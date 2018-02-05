/*
 * Copyright (c) 2016. Huangrui All rights reserved.
 */

package net.eanfang.worker.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.eanfang.worker.R;


/**
 * jorn
 */
public class UploadDialogUtil {
    public static UploadDialogUtil uploadDialogUtil = new UploadDialogUtil();
    UploadDialog uploadDialog;

    public static UploadDialogUtil get() {
        return uploadDialogUtil;
    }

    /**
     * @param context activity
     */
    public UploadDialog buildUploadDialog(final Context context) {
        if (!(context instanceof Activity))
            return null;
//        if (uploadDialog != null) {
//            uploadDialog.cancel();
        uploadDialog = null;
//        }
        if (uploadDialog == null)
            uploadDialog = new UploadDialog(context);

        uploadDialog.setCanceledOnTouchOutside(false);
        uploadDialog.setOnCancelListener((DialogInterface.OnCancelListener) dialog -> {
        });
//        if (uploadDialog != null && !uploadDialog.isShowing()) {
//            uploadDialog.show();
//        }
        return uploadDialog;
    }


    public void cancleProgressDialog() {
        if (uploadDialog != null) {
            uploadDialog.dismiss();
        }
    }


    public class UploadDialog extends Dialog {
        public TextView text;
        Context context;
        private AnimationDrawable animationDrawable = null;

        public UploadDialog(Context context) {
            super(context, R.style.dialog);
            this.context = context;
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.j_progress_view, null);
            LinearLayout linearLayout = new LinearLayout(context);

            text = (TextView) view.findViewById(R.id.progress_message);
            ImageView loadingImage = (ImageView) view
                    .findViewById(R.id.progress_view);
            animationDrawable = (AnimationDrawable) loadingImage.getDrawable();
            if (animationDrawable != null) {
                animationDrawable.setOneShot(false);
                animationDrawable.start();
            }
            setContentView(view);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }

        public void setTextContent(String content) {
            text.setText(content);
        }
    }


}

package com.eanfang.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by jornl on 2017/9/28.
 */

public class TrueFalseDialog {

    private Activity activity;
    private String title;
    private String message;
    private AlertDialog.Builder builder;
    private TrueButton trueButton;
    private FalseButton falseButton;

    public TrueFalseDialog(@NonNull Activity activity, String title, String message, TrueButton trueButton) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.trueButton = trueButton;
        initData();
    }

    public TrueFalseDialog(@NonNull Activity activity, String title, String message, TrueButton trueButton, FalseButton falseButton) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.trueButton = trueButton;
        this.falseButton = falseButton;
        initData();
    }

    private final void initData() {
        builder = new AlertDialog.Builder(activity);
        getBuilder().setTitle(this.title);
        getBuilder().setMessage(this.message);

        getBuilder().setPositiveButton("是", (dialog, which) -> {
            trueButton.click();
            dialog.dismiss();
        });
        getBuilder().setNegativeButton("否", (dialog, which) -> {
            if (falseButton != null) {
                falseButton.click();
            }
            dialog.dismiss();
        });
    }

    public final void showDialog() {
        this.getBuilder().create().show();
    }

    public AlertDialog.Builder getBuilder() {
        return builder;
    }

    public interface TrueButton {
        /**
         * 是 按钮被点击
         */
        void click();
    }

    public interface FalseButton {
        /**
         * 否 按钮被点击
         */
        void click();
    }
}

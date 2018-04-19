package com.eanfang.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.eanfang.R;


/**
 * @author hou
 *         对话框基础类
 */
public abstract class BaseDialog extends Dialog {

    protected Activity context = null;
    protected Handler hander = new Handler();

    public BaseDialog(Activity context) {
        super(context);
        this.context = context;
        setCanceledOnTouchOutside(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public BaseDialog(Activity context, boolean isfull) {
        super(context, R.style.BaseDialog);
        this.context = context;
        if (isfull) {
            setOwnerActivity((Activity) context);
        }
        setCanceledOnTouchOutside(true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCustomView(savedInstanceState);
    }


    protected abstract void initCustomView(Bundle savedInstanceState);

    /**
     * 显示提示
     *
     * @param msgRid 要显示的提示文字资源ID
     */
    protected void showToast(final int msgRid) {
        hander.post(() -> Toast.makeText(context, msgRid, Toast.LENGTH_SHORT).show());

    }

    public void jumpInDialog(Activity activity, Class<?> cls,String title, String type) {
        Intent intent = new Intent(activity,cls);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    public void jumpInDialog(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 显示提示
     *
     * @param msg 要显示的提示文字
     */
    protected void showToast(final String msg) {
        hander.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());

    }

    public void setDialogSizeHalf() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.99);
        lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7);
        getWindow().setAttributes(lp);
    }

    public void setDialogSizeHalfRetangle() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        int min = Math.min((int) (context.getResources().getDisplayMetrics().widthPixels * 0.99), (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7));

        lp.width = min;
        lp.height = min;
        getWindow().setAttributes(lp);
    }

    public void setDialogHeiht(int height) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.height = Math.min(lp.height, height);
        getWindow().setBackgroundDrawableResource(R.drawable.circle_corner);
        getWindow().setAttributes(lp);
    }

    public void setDialogSizeHalfRetangleT() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        int min = Math.min((int) (context.getResources().getDisplayMetrics().widthPixels * 0.99), (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7));
        lp.width = min;
        lp.height = min;
        getWindow().setAttributes(lp);
    }

    public void setDialogSizeMatch() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels);
        lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels);
        getWindow().setAttributes(lp);
    }

    public void setDialogSize1() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.99);
        getWindow().setAttributes(lp);
    }

    public void setDialogSize(float percent) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.8);
        lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * percent);
        getWindow().setAttributes(lp);
    }

    public void setDialogSize(float width, float heigh) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * width);
        lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * heigh);
        getWindow().setAttributes(lp);
    }

    public void setDialogWidth(float width) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * width);
        getWindow().setAttributes(lp);
    }

}

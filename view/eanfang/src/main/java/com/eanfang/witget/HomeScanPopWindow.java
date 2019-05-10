package com.eanfang.witget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.eanfang.R;

/**
 * @author guanluocang
 * @data 2018/9/13
 * @description
 */

public class HomeScanPopWindow extends PopupWindow {

    private Activity context;

    public HomeScanPopWindow(final Activity context, boolean isVisable, View.OnClickListener itemsOnClick) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.layout_pop_scan, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        //登录
        RelativeLayout rl_scan_login = content.findViewById(R.id.rl_scan_login);
        ImageView iv_login = content.findViewById(R.id.iv_login);
        //加好友
        RelativeLayout rl_scan_addfriend = content.findViewById(R.id.rl_scan_addfriend);
        ImageView iv_addfriend = content.findViewById(R.id.iv_addfriend);
        //扫设备
        RelativeLayout rl_scan_device = content.findViewById(R.id.rl_scan_device);
        ImageView iv_device = content.findViewById(R.id.iv_device);
        //扫报修
        RelativeLayout rl_scan_repair = content.findViewById(R.id.rl_scan_reapir);

        // 技师端
        if (isVisable) {
            rl_scan_repair.setVisibility(View.GONE);
            iv_device.setImageResource(R.mipmap.ic_worker_scan_device);
            iv_login.setImageResource(R.mipmap.ic_worker_scan_login);
            iv_addfriend.setImageResource(R.mipmap.ic_worker_scan_addfriend);

        }
        rl_scan_device.setOnClickListener(itemsOnClick);
        rl_scan_repair.setOnClickListener(itemsOnClick);
        rl_scan_login.setOnClickListener(itemsOnClick);
        rl_scan_addfriend.setOnClickListener(itemsOnClick);

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0+
        context.getWindow().setAttributes(lp);
    }
}

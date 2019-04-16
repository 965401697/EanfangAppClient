package com.eanfang.witget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eanfang.R;
import com.picker.common.util.ScreenUtils;


/**
 * @author guanluocang
 * @data 2019/1/8
 * @description 分享的popwindow
 */

public class SharePopWindow extends PopupWindow {
    private Activity context;

    public SharePopWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_share_pop, null);
        TextView tvShareWx = view.findViewById(R.id.tv_share_wx);
        TextView tvShareContact = view.findViewById(R.id.tv_share_contact);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        tvShareWx.setOnClickListener(itemsOnClick);
        tvShareContact.setOnClickListener(itemsOnClick);
        btnCancel.setOnClickListener(itemsOnClick);

        int width = (ScreenUtils.widthPixels(context));
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0));
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0+
        context.getWindow().setAttributes(lp);
    }
}

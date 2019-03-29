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
 * @description 通用popwindow
 */

public class TakeVideoPopWindow extends PopupWindow {
    private Activity context;

    public TakeVideoPopWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_takevideo_pop, null);
        TextView tvTake = (TextView) view.findViewById(R.id.tv_take);
        TextView tvSelect = (TextView) view.findViewById(R.id.tv_select);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        tvTake.setOnClickListener(itemsOnClick);
        tvSelect.setOnClickListener(itemsOnClick);
        btnCancel.setOnClickListener(itemsOnClick);

        int width = (int) (ScreenUtils.widthPixels(context));
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
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }
}

package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;


import net.eanfang.worker.R;

import cn.qqtheme.framework.util.ScreenUtils;

/**
 * @author Guanluocang
 * @date on 2018/5/3  15:46
 * @decision 工作台 五金店 选择地图 PopWindow
 */

public class WorkSpaceSelectMapPopWindow extends PopupWindow {

    private Activity mContext;

    public WorkSpaceSelectMapPopWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_pop_select_map, null);
        Button btnGaoDe = view.findViewById(R.id.btn_gaodeMap);
        Button btnBaiDu = view.findViewById(R.id.btn_baiduMap);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnGaoDe.setOnClickListener(itemsOnClick);
        btnBaiDu.setOnClickListener(itemsOnClick);
        btnCancel.setOnClickListener(itemsOnClick);

        int width = (int) (ScreenUtils.widthPixels(context) * 0.9);
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0));
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0+
        mContext.getWindow().setAttributes(lp);
    }


}

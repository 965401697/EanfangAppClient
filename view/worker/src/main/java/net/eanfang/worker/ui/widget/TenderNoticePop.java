package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.picker.common.util.ScreenUtils;

import net.eanfang.worker.R;

/**
 * @author guanluocang
 * @data 2019/5/29
 * @description
 */
public class TenderNoticePop extends PopupWindow {

    private Activity mContext;
    private String mType;
    private ImageView ivNoticing;
    private ImageView ivOvering;
    private TextView tvNoticing;
    private TextView tvOvering;
    private LinearLayout llNoticing;
    private LinearLayout llOvering;

    public TenderNoticePop(Activity context, String type, View.OnClickListener itemsOnClick, int width) {
        super(context);
        this.mType = type;
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_tender_notice_pop, null);
        tvNoticing = view.findViewById(R.id.tv_noticing);
        tvOvering = view.findViewById(R.id.tv_overing);
        ivNoticing = view.findViewById(R.id.iv_noticing);
        ivOvering = view.findViewById(R.id.iv_overing);
        llNoticing = view.findViewById(R.id.ll_noticing);
        llOvering = view.findViewById(R.id.ll_overing);

        llNoticing.setOnClickListener(itemsOnClick);
        llOvering.setOnClickListener(itemsOnClick);

//        int width = (int) (ScreenUtils.widthPixels(context) * 0.9);
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0));
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        setAnimationStyle(R.style.AnimationPreview);
        update();
        doChange(mType);
    }

    public void doChange(String type) {
        if (type.equals("notice")) {
            ivNoticing.setVisibility(View.VISIBLE);
            ivOvering.setVisibility(View.INVISIBLE);
            tvNoticing.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            tvOvering.setTextColor(ContextCompat.getColor(mContext, R.color.color_client_neworder));
        } else {
            ivNoticing.setVisibility(View.INVISIBLE);
            ivOvering.setVisibility(View.VISIBLE);
            tvNoticing.setTextColor(ContextCompat.getColor(mContext, R.color.color_client_neworder));
            tvOvering.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }
}

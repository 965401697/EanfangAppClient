package com.eanfang.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.widget.R;


/**
 * @author WQ
 */
public class TwoTextPicText extends LinearLayout {
    private ImageView customPicIv;
    private TextView customDateTv;
    private TextView customTextTv;

    public TwoTextPicText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_pictext, this);
        customPicIv = (ImageView) view.findViewById(R.id.custom_pic_iv);
        customTextTv = (TextView) view.findViewById(R.id.custom_text_tv);
        customDateTv = (TextView) view.findViewById(R.id.custom_date_tv);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoTextPicText);
        if (typedArray != null) {
            int picBackgroud = typedArray.getResourceId(R.styleable.TwoTextPicText_pic, 0);
            float picWidth = typedArray.getDimension(R.styleable.TwoTextPicText_pic_width, 100);
            float picHeight = typedArray.getDimension(R.styleable.TwoTextPicText_pic_height, 100);
            customPicIv.setBackgroundResource(picBackgroud);
            customPicIv.setMinimumWidth((int) picWidth);
            customPicIv.setMinimumHeight((int) picHeight);

            String picText = typedArray.getString(R.styleable.TwoTextPicText_text_a);
            int picTextColor = typedArray.getColor(R.styleable.TwoTextPicText_text_color_a, Color.BLACK);
            int picTextSize = typedArray.getDimensionPixelSize(R.styleable.TwoTextPicText_text_size_a, 24);
            customTextTv.setText(picText);
            customTextTv.setTextColor(picTextColor);
            customTextTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,picTextSize);


            String picTextB = typedArray.getString(R.styleable.TwoTextPicText_text_b);
            int picTextColorB = typedArray.getColor(R.styleable.TwoTextPicText_text_color_b, Color.RED);
            int picTextSizeB = typedArray.getDimensionPixelSize(R.styleable.TwoTextPicText_text_size_b, 24);
            customDateTv.setText(picTextB);
            customDateTv.setTextColor(picTextColorB);
            customDateTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,picTextSizeB);

            typedArray.recycle();
        }
    }

    public void setTestAstring(String string) {
        customTextTv.setText(string);
    }

    public void setTestBstring(String string) {
        customDateTv.setText(string);
    }

}
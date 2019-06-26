package com.eanfang.sdk.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.base.network.config.HttpConfig;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class MyFlowLayoutView extends TagFlowLayout {
    private Context context;

    public MyFlowLayoutView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public MyFlowLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyFlowLayoutView(Context context) {
        super(context);
        this.context = context;
    }


    public void setData(List<String> list) {
        setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_trouble_result_item, MyFlowLayoutView.this, false);
                switch (HttpConfig.get().getApp()) {
                    case 0:
                        tv.setBackground(context.getResources().getDrawable(R.drawable.select_client));
                        tv.setTextColor(R.drawable.select_trouble_repair_result);
                        break;
                    case 1:
                        tv.setBackground(context.getResources().getDrawable(R.drawable.select_camera_back));
                        tv.setTextColor(R.drawable.select_trouble_repair_result);
                        break;
                }
                tv.setText(s);
                return tv;
            }
        });
    }
}

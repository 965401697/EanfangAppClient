package com.eanfang.kit.loading.callback;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.kit.R;
import com.eanfang.network.config.HttpConfig;
import com.kingja.loadsir.callback.Callback;

public class PermissionCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_no_permission;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
        if (HttpConfig.get().getApp() == 1) {
            view.findViewById(R.id.ll_footer).setBackgroundColor(Color.parseColor("#F7FAFF"));
            ((ImageView) view.findViewById(R.id.iv_logo)).setImageDrawable(view.getResources().getDrawable(R.drawable.worker_logo));
            ((TextView) view.findViewById(R.id.tv_desc)).setText("安防监控施工过程管理神器");
        }
//        findViewById(R.id.tv_know).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }


}

package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.CallUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  16:23
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class HelpLineView extends BaseDialog {
    @BindView(R.id.ll_repair_phone)
    LinearLayout ll_repair_phone;
    @BindView(R.id.ll_install_phone)
    LinearLayout ll_install_phone;

    @BindView(R.id.ll_lease_phone)
    LinearLayout ll_lease_phone;

    @BindView(R.id.ll_complaint_phone)
    LinearLayout ll_complaint_phone;

    @BindView(R.id.ll_cooperation_phone)
    LinearLayout ll_cooperation_phone;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public HelpLineView(Activity context, boolean isfull) {
        super(context, isfull);
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us_service);
        ButterKnife.bind(this);
        tvTitle.setText("客服热线");
        ivLeft.setOnClickListener(v -> dismiss());
    }

    @OnClick({R.id.ll_repair_phone, R.id.ll_install_phone, R.id.ll_lease_phone, R.id.ll_complaint_phone, R.id.ll_cooperation_phone})
    void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_repair_phone:
                CallUtils.call(context, "400-8909-280");
                break;
            case R.id.ll_install_phone:
                CallUtils.call(context, "400-8909-280");
                break;
            case R.id.ll_lease_phone:
                CallUtils.call(context, "400-8909-280");
                break;
            case R.id.ll_complaint_phone:
                CallUtils.call(context, "400-8909-280");
                break;
            case R.id.ll_cooperation_phone:
                CallUtils.call(context, "139-1025-9250");
                break;

        }
    }
}

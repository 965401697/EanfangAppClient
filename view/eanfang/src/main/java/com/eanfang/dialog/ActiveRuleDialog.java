package com.eanfang.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.ui.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019-05-20
 * Describe :活动规则页面
 */
public class ActiveRuleDialog extends BaseDialog {
    private final Activity mContext;
    @BindView(R2.id.img_close)
    ImageView mImgClose;
    @BindView(R2.id.tv_content)
    TextView mTvContent;
    @BindView(R2.id.bg_active)
    ConstraintLayout mBgActive;

    public ActiveRuleDialog(Activity context) {
        super(context, true);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_active_rule);
        ButterKnife.bind(this);
        mImgClose.setOnClickListener(v -> {
            dismiss();
        });
        mBgActive.setOnClickListener(v -> {
            dismiss();
        });
        String msg = mContext.getResources().getString(R.string.text_active_rule);
        mTvContent.setText(Html.fromHtml(msg));
    }
}

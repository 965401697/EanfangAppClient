package com.eanfang.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liangkailun
 * Date ：2019-05-20
 * Describe :活动规则页面
 */
public class ActiveRuleActivity extends BaseActivity {
    @BindView(R2.id.img_close)
    ImageView mImgClose;
    @BindView(R2.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_active_rule);
        ButterKnife.bind(this);
        mImgClose.setOnClickListener(v -> {
            finishSelf();
        });
        String msg = getResources().getString(R.string.text_active_rule);
        mTvContent.setText(Html.fromHtml(msg));
    }
}

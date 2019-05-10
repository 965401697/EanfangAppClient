package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/29  16:37
 * @email houzhongzhou@yeah.net
 * @desc 提交认证
 */

public class CommitVerfiyView extends BaseDialog {
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    private Activity mContext;
    private OnBtnClickListener onBtnClickListener;

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    public interface OnBtnClickListener {
        void onClickCallback(View view);
    }

    public CommitVerfiyView(Activity context, OnBtnClickListener onBtnClickListener) {
        super(context);
        this.mContext = context;
        this.onBtnClickListener = onBtnClickListener;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_commit_verfiy);
        ButterKnife.bind(this);
        tvNo.setOnClickListener(v -> dismiss());
        tvYes.setOnClickListener(v -> onBtnClickListener.onClickCallback(v));
    }
}

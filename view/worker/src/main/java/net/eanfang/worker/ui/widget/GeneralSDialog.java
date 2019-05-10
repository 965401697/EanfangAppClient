package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/3/5
 * @description
 */

public class GeneralSDialog extends BaseDialog {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    private String mContent;
    protected Activity mContext;

    private OnConfimClickListener mOnConfimClickListener;

    public GeneralSDialog(Activity context) {
        super(context);
        this.mContext = context;
    }

    public GeneralSDialog(Activity context, String content, OnConfimClickListener onConfimClickListener) {
        super(context);
        this.context = context;
        this.mContent = content;
        this.mOnConfimClickListener = onConfimClickListener;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_gendral_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvContent.setText(mContent);
    }

    @OnClick({R.id.iv_close, R.id.tv_cancle, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_cancle:
                dismiss();
                break;
            case R.id.tv_confim:
                mOnConfimClickListener.onConfim();
                break;
            default:
                break;
        }
    }


    public interface OnConfimClickListener {
        void onConfim();
    }
}

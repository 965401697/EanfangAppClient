package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/4/29
 * @description 公共dialog 确认取消
 */


public class CommonView extends BaseDialog {
    @BindView(R.id.et_input_company)
    EditText etInputCompany;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tl_close)
    RelativeLayout tlClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Activity mContext;
    private String mTitle;
    private String mContent;
    //回调函数
    private ConfirmListener mConfimListener;

    public CommonView(Activity context) {
        super(context);
        this.mContext = context;
    }

    public CommonView(Activity context, String title, String content, ConfirmListener confirmListener) {
        super(context);
        this.mConfimListener = confirmListener;
        this.mTitle = title;
        this.mContent = content;
        this.mContext = context;
    }

    public interface ConfirmListener {
        void doConfim(String mContent);
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_create_team);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText(mTitle);
        etInputCompany.setText(mContent);
        tvCancle.setOnClickListener(v -> dismiss());
        tlClose.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> mConfimListener.doConfim(doSetContent()));
    }

    public String doSetContent() {
        String mContens = etInputCompany.getText().toString().trim();
        if (StringUtils.isEmpty(mContens)) {
            return null;
        }
        dismiss();
        return mContens;
    }

}

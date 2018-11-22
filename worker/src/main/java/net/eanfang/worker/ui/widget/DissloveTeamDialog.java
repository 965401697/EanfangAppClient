package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/11/21
 * @description 解散团队
 */

public class DissloveTeamDialog extends BaseDialog {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    private Activity mContext;
    //回调函数
    private OnForgetPasswordListener onForgetPasswordListener;
    private OnConfirmListener onConfirmListener;

    public DissloveTeamDialog(Activity context) {
        super(context);
        this.mContext = context;
    }

    public DissloveTeamDialog(Activity context, OnForgetPasswordListener mOnForgetPasswordListener, OnConfirmListener mOnConfirmListener) {
        super(context);
        this.mContext = context;
        this.onForgetPasswordListener = mOnForgetPasswordListener;
        this.onConfirmListener = mOnConfirmListener;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_disslove_team);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.tv_cancle, R.id.tv_confim, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_cancle:
                dismiss();
                break;
            case R.id.tv_forget:
                onForgetPasswordListener.doForget();
                break;
            case R.id.tv_confim:
                doDisslove();
                break;
            default:
                break;
        }
    }

    private void doDisslove() {
        String password = etPassword.getText().toString().trim();
        if (StringUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }
        EanfangHttp.post(NewApiService.DISSLOVE_COMPANY)
                //公司ID
                .params("id", EanfangApplication.getApplication().getUser()
                        .getAccount().getDefaultUser().getCompanyEntity().getCompanyId())
                //密码
                .params("passwd", password)
                .execute(new EanfangCallback<JSONObject>(mContext, false, JSONObject.class, bean -> {
                    onConfirmListener.doConfirm();
                    dismiss();
                }));
    }

    public interface OnForgetPasswordListener {
        void doForget();
    }

    public interface OnConfirmListener {
        void doConfirm();
    }
}

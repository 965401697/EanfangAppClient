package net.eanfang.worker.ui.activity.techniciancertification;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONPObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthStatusBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
import com.eanfang.util.ToastUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.my.certification.CertificationInfoActivity;
import net.eanfang.worker.ui.activity.my.certification.OtherDataActivity;
import net.eanfang.worker.ui.activity.my.certification.SkillInfoDetailActivity;
import net.eanfang.worker.ui.activity.my.certification.SkillTypeActivity;
import net.eanfang.worker.ui.widget.WQLeftRightClickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ
 */
public class TechnicianCertificationActivity extends BaseActivity {

    @BindView(R.id.sm_rz_wq_tv)
    WQLeftRightClickTextView smRzWqTv;
    @BindView(R.id.fw_rz_wq_tv)
    WQLeftRightClickTextView fwRzWqTv;
    @BindView(R.id.bz_xx_wq_tv)
    WQLeftRightClickTextView bzXxWqTv;
    @BindView(R.id.zz_nl_wq_tv)
    WQLeftRightClickTextView zzNlWqTv;
    @BindView(R.id.js_rz_gg_iv)
    ImageView jsRzGgIv;
    private int verify = -1;
    private int realVerify = -1;
    private AuthStatusBean mAuthStatusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_certification);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("技师认证");
        SpannableString spannableString = new SpannableString("实名认证（必填）");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F3F")), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE );
        smRzWqTv.setText(spannableString);
        SpannableString spannableStringb = new SpannableString("服务认证（必填）");
        spannableStringb.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F3F")), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE );
        fwRzWqTv.setText(spannableStringb);

        setRightTitleOnClickListener(view -> {
            if (verify == 1) {
                showToast("信息认证中，不能撤销");
                return;
            } else if (verify == 0) {
                showToast("认证信息还没有提交，不能撤销");
                return;
            }
            new TrueFalseDialog(TechnicianCertificationActivity.this, "系统提示", "是否撤销认证并保存信息", () -> EanfangHttp.post(NewApiService.WORKER_AUTH_REVOKE + EanfangApplication.getApplication().getAccId()).execute(new EanfangCallback<JSONPObject>(TechnicianCertificationActivity.this, true, JSONPObject.class, bean -> {
                verify = 0;
                setRightTitle("");
            }))).showDialog();
        });
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    private void initData() {
        EanfangHttp.post(UserApi.POST_WORKER_AUTH_STATUS).params("accId", EanfangApplication.getApplication().getAccId()).execute(new EanfangCallback<AuthStatusBean>(this, true, AuthStatusBean.class, (bean) -> {
            verify = bean.getVerify();
            realVerify = bean.getRealVerify();
            if (verify == 2) {
                setRightTitle("重新认证");
            }
            mAuthStatusBean = bean;
        }));
    }


    @OnClick({R.id.sm_rz_wq_tv, R.id.fw_rz_wq_tv, R.id.bz_xx_wq_tv, R.id.zz_nl_wq_tv, R.id.js_rz_gg_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sm_rz_wq_tv:
                if (realVerify == 0) {
                    JumpItent.jump(this, CertificationInfoActivity.class);
                } else {
                    if (mAuthStatusBean == null) {
                        ToastUtil.get().showToast(this, "请稍后操作");
                        return;
                    }
                    startActivity(new Intent(this, RealNameAuthenticationActivity.class).putExtra("status", mAuthStatusBean.getBase()).putExtra("statusB", mAuthStatusBean.getApt()));
                }
                break;
            case R.id.fw_rz_wq_tv:
                if (realVerify == 1) {
                    ToastUtil.get().showToast(this, "请先进行实名认证");
                    return;
                }
                if (verify == 1 || verify == 2) {
                    JumpItent.jump(this, SkillInfoDetailActivity.class);
                } else {
                    if (mAuthStatusBean == null) {
                        ToastUtil.get().showToast(this, "请稍后操作");
                        return;
                    }
                    startActivity(new Intent(this, SkillTypeActivity.class).putExtra("status", mAuthStatusBean.getApt()));
                }
                break;
            case R.id.bz_xx_wq_tv:
                startActivity(new Intent(this, OtherDataActivity.class));
                break;
            case R.id.zz_nl_wq_tv:
                startActivity(new Intent(this, JsQualificationsAndAbilitiesActivity.class));
                break;
            case R.id.js_rz_gg_iv:
                break;
            default:
        }
    }
}

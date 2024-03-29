package net.eanfang.worker.ui.activity.authentication;


import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.base.widget.customview.SuperTextView;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.CompanyPagesData;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

import static com.eanfang.apiservice.NewApiService.COMPANY_SECURITY_HOMEPAGE;

/**
 * @author WQ
 */
public class CompanyPagesActivity extends BaseActivity {

    @BindView(R.id.gs_gm_tv)
    SuperTextView gsGmTv;
    @BindView(R.id.hy_lx_tv)
    SuperTextView hyLxTv;
    @BindView(R.id.bg_dz_tv)
    SuperTextView bgDzTv;
    @BindView(R.id.xx_dz_tv)
    SuperTextView xxDzTv;
    @BindView(R.id.dz_yx_tv)
    SuperTextView dzYxTv;
    @BindView(R.id.dw_dh_tv)
    SuperTextView dwDhTv;
    @BindView(R.id.gs_log_sdv)
    CircleImageView gsLogSdv;
    @BindView(R.id.gs_name_tv)
    TextView gsNameTv;
    @BindView(R.id.iv_verify)
    ImageView ivVerify;
    @BindView(R.id.tv_auth_status)
    TextView tvAuthStatus;
    @BindView(R.id.gs_xq_iv)
    ImageView gsXqIv;
    @BindView(R.id.gs_xq_tv)
    TextView gsXqTv;
    private Long mOrgId;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_pages);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("企业主页");
    }

    private void initData() {
        mOrgId = getIntent().getLongExtra("mOrgId", 0);
        EanfangHttp.post(COMPANY_SECURITY_HOMEPAGE).params("orgId", mOrgId).execute(new EanfangCallback<>(this, true, CompanyPagesData.class, this::setData));
    }

    private void setData(CompanyPagesData data) {
        if (!StrUtil.isEmpty(data.getAreaCode())) {
            bgDzTv.setRightString(Config.get().getAddressByCode(data.getAreaCode()));
        } else {
            bgDzTv.setRightString("");
        }
        xxDzTv.setRightString(data.getOfficeAddress() + "");
        dzYxTv.setRightString(data.geteMail() + "");
        dwDhTv.setRightString(data.getTelPhone() + "");
        gsNameTv.setText(data.getName() + "");
        GlideUtil.intoImageView(this,com.eanfang.BuildConfig.OSS_SERVER + Uri.parse(data.getLogoPic()),gsLogSdv);
        status = data.getStatus();
        switch (status) {
            case 1:
                tvAuthStatus.setText("认证中");
                break;
            case 2:
                ivVerify.setImageResource(R.mipmap.ic_contact_auth);
                tvAuthStatus.setText("已认证");
                break;
            case 3:
                tvAuthStatus.setText("认证拒绝");
                break;
            case 4:
                break;
            default:
        }
        switch (data.getScale()) {
            case 0:
                gsGmTv.setRightString("微企业");
                break;
            case 1:
                gsGmTv.setRightString("小企业");
                break;
            case 2:
                gsGmTv.setRightString("中企业");
                break;
            case 3:
                gsGmTv.setRightString("大企业");
                break;
            case 4:
                gsGmTv.setRightString("超大企业");
                break;
            default:
        }
        if (!StrUtil.isEmpty(data.getTradeTypeCode())) {
            hyLxTv.setRightString(Config.get().getBaseNameByCode(Constant.INDUSTRY, data.getTradeTypeCode(), 2));
        } else {
            hyLxTv.setRightString("");
        }
        if (data.getIntro() == null) {
            gsXqTv.setVisibility(View.GONE);
        } else {
            gsXqTv.setText("公司介绍: " + data.getIntro());
        }
    }
}

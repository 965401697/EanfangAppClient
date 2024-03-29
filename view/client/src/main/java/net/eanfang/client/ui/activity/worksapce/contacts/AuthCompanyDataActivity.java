package net.eanfang.client.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.AuthCompanyBaseInfoBean;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClienActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:00
 * @email houzhongzhou@yeah.net
 * @desc 公司认证
 */

public class AuthCompanyDataActivity extends BaseClienActivity {

    //营业执照
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    // 公司logo
    @BindView(R.id.iv_uploadlogo)
    ImageView ivUpload2;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.ed_company_number)
    EditText edCompanyNumber;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.ll_type)
    RelativeLayout llType;
    @BindView(R.id.tv_office_address)
    TextView tvOfficeAddress;
    @BindView(R.id.ll_office_address)
    LinearLayout llOfficeAddress;
    @BindView(R.id.et_detail_office_address)
    EditText etDetailOfficeAddress;
    @BindView(R.id.et_legal_persion)
    EditText etLegalPersion;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_company_scale)
    TextView tvCompanyScale;
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.et_desc)
    EditText etDesc;

    private AuthCompanyBaseInfoBean byNetBean;
    private Long orgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_auth_company);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
    @Override
    public void initView() {
        setTitle("查看信息");
        setLeftBack(true);
        orgid = getIntent().getLongExtra("orgid", 0);
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    byNetBean = beans;
                    fillData();
                }));
    }

    private void fillData() {
        if (byNetBean != null) {
            if (!StrUtil.isEmpty(byNetBean.getName())) {
                etCompany.setText(byNetBean.getName());
            }
            if (byNetBean.getLicenseCode() != null) {
                edCompanyNumber.setText(byNetBean.getLicenseCode());
            }
            if (byNetBean.getRegisterAssets() != null) {
                etMoney.setText(byNetBean.getRegisterAssets());
            }
            if (byNetBean.getTradeTypeCode() != null) {
                tvType.setText(Config.get().getBaseNameByCode(byNetBean.getTradeTypeCode(), Constant.INDUSTRY));
            }
            if (byNetBean.getScale() >= 0) {
                tvCompanyScale.setText(GetConstDataUtils.getOrgUnitScaleList().get(byNetBean.getScale()));
            }
            if (byNetBean.getLegalName() != null) {
                etLegalPersion.setText(byNetBean.getLegalName());
            }
            if (byNetBean.getTelPhone() != null) {
                etPhone.setText(byNetBean.getTelPhone());
            }
            if (byNetBean.getIntro() != null) {
                etDesc.setText(byNetBean.getIntro());
            }
            if (byNetBean.getOfficeAddress() != null) {
                etDetailOfficeAddress.setText(byNetBean.getOfficeAddress());
            }
            if (byNetBean.getAreaCode() != null) {
                tvOfficeAddress.setText(Config.get().getAddressByCode(byNetBean.getAreaCode()));
            }
            if (!StrUtil.isEmpty(byNetBean.getLogoPic())) {
                GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + byNetBean.getLogoPic(),ivUpload2);
            }
            if (!StrUtil.isEmpty(byNetBean.getLicensePic())) {
                GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + byNetBean.getLicensePic(),ivUpload);
            }
        }
        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        if (byNetBean.getStatus() != 0 && byNetBean.getStatus() != 3) {
            ivUpload.setEnabled(false);
            ivUpload2.setEnabled(false);
            llOfficeAddress.setEnabled(false);
            llCompanyScale.setEnabled(false);
            llType.setEnabled(false);
            setOnFouse(etCompany);
            setOnFouse(edCompanyNumber);
            setOnFouse(etMoney);
            setOnFouse(etLegalPersion);
            setOnFouse(etDetailOfficeAddress);
            setOnFouse(etPhone);
            setOnFouse(etDesc);
        }
        if (byNetBean.getStatus() != 2) {
          setRightClick(false);
        }
    }


    private void setOnFouse(EditText editText) {
        editText.setEnabled(false);
    }

}

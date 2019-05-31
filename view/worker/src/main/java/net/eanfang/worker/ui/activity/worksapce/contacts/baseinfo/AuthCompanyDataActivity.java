package net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:00
 * @email houzhongzhou@yeah.net
 * @desc 安防公司认证 完善基本资料
 */

public class AuthCompanyDataActivity extends BaseActivityWithTakePhoto {

    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.iv_upload2)
    ImageView ivUpload2;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.ed_company_number)
    EditText edCompanyNumber;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    RelativeLayout llType;
    @BindView(R.id.ll_office_address)
    LinearLayout llOfficeAddress;
    // 公司规模
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.tv_office_address)
    TextView tvOfficeAddress;
    @BindView(R.id.et_detail_office_address)
    EditText etDetailOfficeAddress;
    @BindView(R.id.et_legal_persion)
    EditText etLegalPersion;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_company_scale)
    TextView tvCompanyScale;
    // 公司规模
    @BindView(R.id.et_desc)
    EditText etDesc;

    private AuthCompanyBaseInfoBean byNetBean;
    private Long orgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_company);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        setTitle("查看信息");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    byNetBean = beans;
                    fillData();
                }));
    }

    /**
     * 初始化  填充数据
     */
    private void fillData() {

        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        // 0 草稿 3 认证拒绝 1 认证中 2 认证通过
        if (byNetBean.getStatus() != 0 && byNetBean.getStatus() != 3) {
            ivUpload.setEnabled(false);
            ivUpload2.setEnabled(false);
            etCompany.setEnabled(false);
            edCompanyNumber.setEnabled(false);
            etMoney.setEnabled(false);
            llType.setEnabled(false);
            llOfficeAddress.setEnabled(false);
            tvOfficeAddress.setEnabled(false);
            etLegalPersion.setEnabled(false);
            llCompanyScale.setEnabled(false);
            etPhone.setEnabled(false);
            etDetailOfficeAddress.setEnabled(false);
            etDesc.setEnabled(false);
        }
        if (byNetBean.getStatus() != 2) {
            setRightGone();
        }
        if (byNetBean != null) {
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
            if (!StringUtils.isEmpty(byNetBean.getLogoPic())) {
                GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + byNetBean.getLogoPic(),ivUpload2);
                byNetBean.setLogoPic(byNetBean.getLogoPic());
            }
            if (!StringUtils.isEmpty(byNetBean.getLicensePic())) {
                GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + byNetBean.getLicensePic(),ivUpload);

                byNetBean.setLicensePic(byNetBean.getLicensePic());
            }
            if (!StringUtils.isEmpty(byNetBean.getName())) {
                etCompany.setText(byNetBean.getName());
            }
        }
    }

    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_INSERT)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("保存成功");
                    finishSelf();
                }));
    }


}

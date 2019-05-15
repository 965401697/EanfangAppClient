package net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.eanfang.biz.model.entity.OrgUnitEntity;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/10/17
 * @description 公司认证 基本资料 第一步
 */

public class AuthCompanyFirstActivity extends BaseActivityWithTakePhoto {

    /**
     * 地址回掉code
     */
    private final int ADDRESS_CALLBACK_CODE = 100;
    /**
     * 营业执照 take photo回调 code
     */
    private final int LICENSE_CALLBACK_CODE = 300;

    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.ed_company_number)
    EditText edCompanyNumber;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_legal_persion)
    EditText etLegalPersion;
    @BindView(R.id.tv_office_address)
    TextView tvOfficeAddress;
    @BindView(R.id.ll_office_address)
    LinearLayout llOfficeAddress;
    @BindView(R.id.et_detail_office_address)
    EditText etDetailOfficeAddress;
    @BindView(R.id.iv_upload)
    SimpleDraweeView ivUpload;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private String orgName;
    private Long orgid;

    private String itemcity;
    private String itemzone;

    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_company_first);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("完善资料");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        orgName = getIntent().getStringExtra("orgName");
        etCompany.setText(orgName);
    }

    private void initListener() {
        ivUpload.setOnClickListener((v) -> {
            PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthCompanyFirstActivity.this, LICENSE_CALLBACK_CODE));
        });

        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(AuthCompanyFirstActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });

        btnComplete.setOnClickListener((v) -> {
            // 完善资料
            doVerify();
        });
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    infoBean = beans;
                    fillData();
                }));
    }

    /**
     * 进行字段的约束判断
     */
    public void doVerify() {
        if (StringUtils.isEmpty(etCompany.getText().toString().trim())) {
            showToast("请输入单位名称");
            return;
        } else if (StringUtils.isEmpty(edCompanyNumber.getText().toString().trim())) {
            showToast("请输入营业执照号码");
            return;
        } else if (StringUtils.isEmpty(etMoney.getText().toString().trim())) {
            showToast("请输入注册资本");
            return;
        } else if (StringUtils.isEmpty(etDetailOfficeAddress.getText().toString().trim())) {
            showToast("请输入办公地址");
            return;
        } else if (StringUtils.isEmpty(etLegalPersion.getText().toString().trim())) {
            showToast("请输入法人代表");
            return;
        } else {
            setData();
        }
    }

    private void setData() {
        infoBean.setName(etCompany.getText().toString().trim());
        infoBean.setLicenseCode(edCompanyNumber.getText().toString().trim());
        infoBean.setRegisterAssets(etMoney.getText().toString().trim());
        infoBean.setOrgId(orgid);
        infoBean.setLegalName(etLegalPersion.getText().toString().trim());
        infoBean.setTelPhone(etPhone.getText().toString().trim());
        infoBean.setOfficeAddress(etDetailOfficeAddress.getText().toString().trim());
        if (!StringUtils.isEmpty(itemcity) && !StringUtils.isEmpty(itemzone)) {
            infoBean.setAreaCode(Config.get().getAreaCodeByName(itemcity, itemzone));
        }
//        if (infoBean.getAdminUserId() == null) {
//            infoBean.setAdminUserId(EanfangApplication.getApplication().getUserId());
//        }

        String json = JSONObject.toJSONString(infoBean);
        commit(json);
    }

    /**
     * 初始化  填充数据
     */
    private void fillData() {

        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        // 0 草稿 3 认证拒绝 1 认证中 2 认证通过
        if (infoBean.getStatus() != 0) {
            etCompany.setEnabled(false);
        }
        if (infoBean.getStatus() != 0 && infoBean.getStatus() != 3) {
            btnComplete.setVisibility(View.GONE);
            ivUpload.setEnabled(false);
            edCompanyNumber.setEnabled(false);
            etMoney.setEnabled(false);
            llOfficeAddress.setEnabled(false);
            tvOfficeAddress.setEnabled(false);
            etLegalPersion.setEnabled(false);
            etPhone.setEnabled(false);
            etDetailOfficeAddress.setEnabled(false);
        }
        if (infoBean != null) {
            if (infoBean.getLicenseCode() != null) {
                edCompanyNumber.setText(infoBean.getLicenseCode());
            }
            if (infoBean.getRegisterAssets() != null) {
                etMoney.setText(infoBean.getRegisterAssets());
            }
            if (infoBean.getLegalName() != null) {
                etLegalPersion.setText(infoBean.getLegalName());
            }
            if (infoBean.getTelPhone() != null) {
                etPhone.setText(infoBean.getTelPhone());
            }
            if (infoBean.getOfficeAddress() != null) {
                etDetailOfficeAddress.setText(infoBean.getOfficeAddress());
            }
            if (infoBean.getAreaCode() != null) {
                tvOfficeAddress.setText(Config.get().getAddressByCode(infoBean.getAreaCode()));
            }
            if (!StringUtils.isEmpty(infoBean.getLicensePic())) {
                ivUpload.setImageURI(BuildConfig.OSS_SERVER + infoBean.getLicensePic());
                infoBean.setLicensePic(infoBean.getLicensePic());
            }
        }
    }


    /**
     * 图片选择 回调
     *
     * @param result
     * @param resultCode
     */
    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = "org/" + UuidUtil.getUUID() + ".png";

        if (resultCode == LICENSE_CALLBACK_CODE) {
            infoBean.setLicensePic(imgKey);
            ivUpload.setImageURI("file://" + image.getOriginalPath());
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_INSERT)
                .upJson(json)
                .execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
                    doJumpSecond();
                }));
    }

    /**
     * 地图选址 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == ADDRESS_CALLBACK_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            itemcity = item.getCity();
            itemzone = item.getAddress();
            tvOfficeAddress.setText(item.getProvince() + itemcity + itemzone);

            //将选择的地址 取 显示值
            etDetailOfficeAddress.setText(item.getName());

        }
    }

    /**
     * 跳转第二部
     */
    private void doJumpSecond() {
        Bundle bundle = new Bundle();
        bundle.putLong("mOrgId", orgid);
        JumpItent.jump(AuthCompanyFirstActivity.this, AuthCompanySecondActivity.class, bundle);
    }

}

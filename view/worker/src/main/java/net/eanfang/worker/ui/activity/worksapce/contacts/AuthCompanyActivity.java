package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.SelectAddressItem;

import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.PermKit;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.eanfang.biz.model.entity.BaseDataEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:00
 * @email houzhongzhou@yeah.net
 * @desc 安防公司认证
 */

public class AuthCompanyActivity extends BaseActivityWithTakePhoto {
    /**
     * 营业执照 take photo回调 code
     */
    private final int LICENSE_CALLBACK_CODE = 300;
    /**
     * 公司LOGO take photo 回调 code
     */
    private final int ADPIC_CALLBACK_CODE = 400;
    /**
     * 地址回掉code
     */
    private final int ADDRESS_CALLBACK_CODE = 100;
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
    // 公司规模
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.et_desc)
    EditText etDesc;

    @BindView(R.id.btn_complete)
    Button btnComplete;
    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();
    private AuthCompanyBaseInfoBean byNetBean;
    private String firstTraed, secondTraed;
    private String longitude, orgName;
    private String latitude;
    private String itemcity;
    private String itemzone;
    private Long orgid;

    // 区别
    //  private String mAssign = "";

    // 是否编辑
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_company);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }


    private void initView() {
        setTitle("填写企业信息");
        setRightTitle("编辑");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        orgName = getIntent().getStringExtra("orgName");
        // mAssign = getIntent().getStringExtra("assign");

        // 完善资料
        btnComplete.setText("保存资料");
        etCompany.setText(orgName);

    }

    private void initListener() {
        ivUpload.setOnClickListener((v) -> {
            RxPerm.get(this).cameraPerm((isSuccess)-> takePhoto(AuthCompanyActivity.this, LICENSE_CALLBACK_CODE));
        });
        ivUpload2.setOnClickListener((v -> {
            RxPerm.get(this).cameraPerm((isSuccess)-> takePhoto(AuthCompanyActivity.this, ADPIC_CALLBACK_CODE));
        }));

        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(AuthCompanyActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "",
                tvCompanyScale, GetConstDataUtils.getOrgUnitScaleList()));

        btnComplete.setOnClickListener((v) -> {
            // 完善资料
            if (WorkerApplication.get().getAccId().equals(byNetBean.getAccId())) {
                if (isEdit) {
                    doUndoVerify();
                } else {
                    doVerify();
                }
            } else {
                showToast("抱歉，您没有权限！");
            }
        });
        setRightTitleOnClickListener((v) -> {
            if (!PermKit.get().getWorkerCompanyBackPerm()) {
                return;
            }
            showToast("可以进行编辑");
            isEdit = true;
            setRightGone();
            doRevoke();
        });
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    byNetBean = beans;
                    fillData();
                }));
    }

    /**
     * 行业类型
     */
    private void showTradType() {
        List<BaseDataEntity> baseDataBeanList = Config.get().getIndustryList();
        List<BaseDataEntity> tradeFirst = Stream.of(baseDataBeanList).filter(beanFirst -> beanFirst.getLevel() == 2).toList();
        List<String> tradeFirststr = Stream.of(tradeFirst).map(first -> first.getDataName()).toList();
        List<List<String>> secondStr = Stream.of(tradeFirst).map(firtstr -> Stream.of(baseDataBeanList).filter(second -> second.getLevel() == 3 && second.getDataCode().startsWith(firtstr.getDataCode())).map(second -> second.getDataName()).toList()).toList();

        PickerSelectUtil.linkagePicker(this, "行业类型", tradeFirststr, secondStr, ((first, second) -> {
            firstTraed = first;
            secondTraed = second;
            String tradeStr = first + " - " + second;
            tvType.setText(tradeStr);
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
        } else if (StringUtils.isEmpty(tvType.getText().toString().trim())) {
            showToast("请选择行业类型");
            return;
        } else if (StringUtils.isEmpty(etLegalPersion.getText().toString().trim())) {
            showToast("请输入法人代表");
            return;
        } else if (StringUtils.isEmpty(etDesc.getText().toString().trim())) {
            showToast("请输入单位简介");
            return;
        } else {
            setData();
        }
    }

    private void setData() {
        infoBean.setName(etCompany.getText().toString().trim());
        infoBean.setLicenseCode(edCompanyNumber.getText().toString().trim());
        infoBean.setRegisterAssets(etMoney.getText().toString().trim());
        if (byNetBean.getTradeTypeCode().equals("")) {
            infoBean.setTradeTypeCode(Config.get().getBaseCodeByName(secondTraed, 2, Constant.INDUSTRY).get(0));
        } else {
            infoBean.setTradeTypeCode(byNetBean.getTradeTypeCode());
        }

        infoBean.setScale(GetConstDataUtils.getOrgUnitScaleList().indexOf(tvCompanyScale.getText().toString().trim()));
        infoBean.setStatus(1);
        infoBean.setOrgId(orgid);

        infoBean.setLegalName(etLegalPersion.getText().toString().trim());
        infoBean.setTelPhone(etPhone.getText().toString().trim());
        infoBean.setUnitType(3);
        infoBean.setIntro(etDesc.getText().toString().trim());
        infoBean.setOfficeAddress(etDetailOfficeAddress.getText().toString().trim());
        if (byNetBean.getAreaCode().equals("")) {
            infoBean.setAreaCode(Config.get().getAreaCodeByName(itemcity, itemzone));
        } else {
            infoBean.setAreaCode(byNetBean.getAreaCode());
        }
        if (byNetBean.getAdminUserId().equals("")) {
            infoBean.setAdminUserId(WorkerApplication.get().getUserId());
        } else {
            infoBean.setAdminUserId(byNetBean.getAdminUserId());
        }

        String json = JSONObject.toJSONString(infoBean);
        commit(json);
    }

    /**
     * 初始化  填充数据
     */
    private void fillData() {

        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        // 0 草稿 3 认证拒绝 1 认证中 2 认证通过
        if (byNetBean.getStatus() != 0 && byNetBean.getStatus() != 3) {
            btnComplete.setVisibility(View.GONE);
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
                infoBean.setLogoPic(byNetBean.getLogoPic());
            }
            if (!StringUtils.isEmpty(byNetBean.getLicensePic())) {
                GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + byNetBean.getLicensePic(),ivUpload);
                infoBean.setLicensePic(byNetBean.getLicensePic());
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


    /**
     * 进行撤销认证操作
     */
    public void doUndoVerify() {
        new TrueFalseDialog(this, "系统提示", "是否撤销认证并保存信息", () -> {
            EanfangHttp.post(NewApiService.COMPANY_SECURITY_AUTH_REVOKE + byNetBean.getOrgId()).
                    execute(new EanfangCallback<JSONPObject>(this, true, JSONPObject.class, bean -> {
                        doVerify();
                    }));
        }).showDialog();
    }

    /**
     * 重新编辑
     */
    private void doRevoke() {
        ivUpload.setEnabled(true);
        ivUpload2.setEnabled(true);
        etCompany.setEnabled(true);
        edCompanyNumber.setEnabled(true);
        etMoney.setEnabled(true);
        llType.setEnabled(true);
        llOfficeAddress.setEnabled(true);
        tvOfficeAddress.setEnabled(true);
        etLegalPersion.setEnabled(true);
        llCompanyScale.setEnabled(true);
        etPhone.setEnabled(true);
        etDetailOfficeAddress.setEnabled(true);
        etDesc.setEnabled(true);
        btnComplete.setVisibility(View.VISIBLE);
        setRightGone();
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
        String imgKey = UuidUtil.getUUID() + ".png";

        if (resultCode == LICENSE_CALLBACK_CODE) {
            infoBean.setLicensePic(imgKey);
            GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivUpload);
        } else if (resultCode == ADPIC_CALLBACK_CODE) {
            infoBean.setLogoPic(imgKey);
            GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivUpload2);
        }
        SDKManager.ossKit(this).asyncPutImage(imgKey, image.getOriginalPath(),(isSuccess) -> {});

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
            latitude = item.getLatitude().toString();
            longitude = item.getLongitude().toString();
            itemcity = item.getCity();
            itemzone = item.getAddress();
            tvOfficeAddress.setText(item.getProvince() + itemcity + itemzone);

            //将选择的地址 取 显示值
            etDetailOfficeAddress.setText(item.getName());

        }
    }


}

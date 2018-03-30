package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthCompanyBaseInfoBean;
import com.eanfang.model.SelectAddressItem;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.widget.CommitVerfiyView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:00
 * @email houzhongzhou@yeah.net
 * @desc 公司认证
 */

public class AuthCompanyActivity extends BaseActivityWithTakePhoto {
    @BindView(R.id.iv_upload)
    SimpleDraweeView ivUpload;
    @BindView(R.id.iv_upload2)
    SimpleDraweeView ivUpload2;
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
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_register_address)
    TextView tvRegisterAddress;
    @BindView(R.id.ll_register_address)
    LinearLayout llRegisterAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
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
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.tv_company_scale)
    TextView tvCompanyScale;
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.et_desc)
    EditText etDesc;
    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();
    private AuthCompanyBaseInfoBean byNetBean;
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
    private String firstTraed, secondTraed;
    private String longitude, orgName;
    private String latitude;
    private String itemcity;
    private String itemzone;
    private Long orgid;
    private CommitVerfiyView verfiyView;

    private Config config;
    private GetConstDataUtils constDataUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_company);
        ButterKnife.bind(this);
        initView();
        initData();
        config = Config.get(this);
        constDataUtils = GetConstDataUtils.get(config);
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
            if (byNetBean.getLicenseCode() != null) {
                edCompanyNumber.setText(byNetBean.getLicenseCode());
            }
            if (byNetBean.getRegisterAssets() != null) {
                etMoney.setText(byNetBean.getRegisterAssets());
            }
            if (byNetBean.getTradeTypeCode() != null) {
                tvType.setText(config.getBaseNameByCode(byNetBean.getTradeTypeCode(), Constant.INDUSTRY));
            }
            if (byNetBean.getScale() >= 0) {
                tvCompanyScale.setText(constDataUtils.getOrgUnitScaleList().get(byNetBean.getScale()));
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
                tvOfficeAddress.setText(config.getAddressByCode(byNetBean.getAreaCode()));
            }
            if (byNetBean.getLogoPic() != null) {
                ivUpload2.setImageURI(BuildConfig.OSS_SERVER + byNetBean.getLogoPic());
                infoBean.setLogoPic(byNetBean.getLogoPic());
            }
            if (byNetBean.getLicensePic() != null) {
                ivUpload.setImageURI(BuildConfig.OSS_SERVER + byNetBean.getLicensePic());
                infoBean.setLicensePic(byNetBean.getLogoPic());
            }
        }
    }

    private void initView() {
        setTitle("填写企业信息");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        orgName = getIntent().getStringExtra("orgName");
        etCompany.setText(orgName);
        ivUpload.setOnClickListener((v) -> {
            PermissionUtils.get(this).getCameraPermission(() -> takePhoto(LICENSE_CALLBACK_CODE));
        });
        ivUpload2.setOnClickListener((v -> {
            PermissionUtils.get(this).getCameraPermission(() -> takePhoto(ADPIC_CALLBACK_CODE));
        }));

        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(AuthCompanyActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "",
                tvCompanyScale, constDataUtils.getOrgUnitScaleList()));
        btnComplete.setOnClickListener((v) -> {
            if (EanfangApplication.getApplication().getAccId().equals(byNetBean.getAccId())) {
                setData();
            } else {
                showToast("抱歉，您没有权限！");
            }

        });

    }

    /**
     * 行业类型
     */
    private void showTradType() {
        List<BaseDataEntity> baseDataBeanList = config.getIndustryList();
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
            ivUpload.setImageURI("file://" + image.getOriginalPath());
        } else if (resultCode == ADPIC_CALLBACK_CODE) {
            infoBean.setLogoPic(imgKey);
            ivUpload2.setImageURI("file://" + image.getOriginalPath());
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

    private void setData() {
        infoBean.setName(etCompany.getText().toString().trim());
        infoBean.setLicenseCode(edCompanyNumber.getText().toString().trim());
        infoBean.setRegisterAssets(etMoney.getText().toString().trim());

        infoBean.setTradeTypeCode(config.getBaseCodeByName(secondTraed, 2, Constant.INDUSTRY).get(0));
        infoBean.setScale(constDataUtils.getOrgUnitScaleList().indexOf(tvCompanyScale.getText().toString().trim()));
        infoBean.setStatus(1);
        infoBean.setOrgId(orgid);

        infoBean.setLegalName(etLegalPersion.getText().toString().trim());
        infoBean.setTelPhone(etPhone.getText().toString().trim());
        infoBean.setUnitType(3);
        infoBean.setIntro(etDesc.getText().toString().trim());
        infoBean.setOfficeAddress(etDetailOfficeAddress.getText().toString().trim());
        if (infoBean.getAreaCode() != null) {
            infoBean.setAreaCode(byNetBean.getAreaCode());
        } else {
            infoBean.setAreaCode(config.getAreaCodeByName(itemcity, itemzone));
        }
        if (infoBean.getAdminUserId() != null) {
            infoBean.setAdminUserId(byNetBean.getAdminUserId());
        } else {
            infoBean.setAdminUserId(EanfangApplication.getApplication().getUserId());
        }

        String json = JSONObject.toJSONString(infoBean);
        commit(json);
    }

    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_INSERT)
                .upJson(json)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    verfiyView = new CommitVerfiyView(this, view -> commitVerfiy(verfiyView));
                    verfiyView.show();
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
            latitude = item.getLatitude().toString();
            longitude = item.getLongitude().toString();
            itemcity = item.getCity();
            itemzone = item.getAddress();
            tvOfficeAddress.setText(item.getProvince() + itemcity + itemzone);

            //将选择的地址 取 显示值
            etDetailOfficeAddress.setText(item.getName());

        }
    }

    private void commitVerfiy(CommitVerfiyView verfiyView) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SEND_VERIFY + byNetBean.getOrgId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    showToast("已提交认证");
                    verfiyView.dismiss();
                    finishSelf();
                }));
    }
}

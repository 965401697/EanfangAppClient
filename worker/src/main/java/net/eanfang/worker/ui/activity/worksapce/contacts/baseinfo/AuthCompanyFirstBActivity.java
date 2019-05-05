package net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
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
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.yaf.sys.entity.BaseDataEntity;
import com.yaf.sys.entity.OrgUnitEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.authentication.SubmitSuccessfullyQyActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author WQ
 */
public class AuthCompanyFirstBActivity extends BaseActivityWithTakePhoto {
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

    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.tv_office_address)
    TextView tvOfficeAddress;
    @BindView(R.id.ll_office_address)
    LinearLayout llOfficeAddress;
    @BindView(R.id.et_detail_office_address)
    EditText etDetailOfficeAddress;

    /**
     * 公司LOGO take photo 回调 code
     */
    private final int ADPIC_CALLBACK_CODE = 400;
    @BindView(R.id.tv_company_scale)
    TextView tvCompanyScale;
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    RelativeLayout llType;
    @BindView(R.id.iv_upload2)
    SimpleDraweeView ivUpload2;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.tv_title_b)
    TextView tvTitle;
    @BindView(R.id.tv_right_b)
    TextView tvRightB;
    @BindView(R.id.iv_left_b)
    ImageView ivLeftB;
    @BindView(R.id.tv_type_temp)
    TextView tvTypeTemp;
    @BindView(R.id.iv_type)
    ImageView ivType;
    private String orgName;
    private Long orgid;
    private String secondTraed;
    private String itemcity;
    private String itemzone;

    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_company_first_b);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tvTitle.setText("完善资料");
        ivLeftB.setOnClickListener(view -> finish());
        orgid = getIntent().getLongExtra("orgid", 0);
        orgName = getIntent().getStringExtra("orgName");
        etCompany.setText(orgName);
        tvRightB.setText("保存");
        tvRightB.setOnClickListener(view -> doVerify());
    }

    private void initListener() {
        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        ivUpload2.setOnClickListener((v -> {
            PermissionUtils.get(this).getCameraPermission(() -> takePhoto(this, ADPIC_CALLBACK_CODE));
        }));
        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "", tvCompanyScale, GetConstDataUtils.getOrgUnitScaleList()));
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
            secondTraed = second;
            String tradeStr = first + " - " + second;
            tvType.setText(tradeStr);
        }));
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid).execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
            infoBean = beans;
            Log.d("584866", "fillData: " + infoBean.getStatus());

            fillData();
        }));
    }

    /**
     * 进行字段的约束判断
     */
    public void doVerify() {
        if (StringUtils.isEmpty(etPhone.getText().toString().trim())) {
            showToast("请输入单位电话");
            return;
        }  if (StringUtils.isEmpty(etCompany.getText().toString().trim())) {
            showToast("请输入单位名称");
            return;
        } else if (!isEmail(etMoney.getText().toString().trim())) {
            showToast("请输入电子邮箱");
            return;
        } else if (StringUtils.isEmpty(tvType.getText().toString().trim())) {
            showToast("请选择行业类型");
            return;
        } else if (StringUtils.isEmpty(etDesc.getText().toString().trim())) {
            showToast("请输入单位简介");
            return;
        } else if (StringUtils.isEmpty(etDetailOfficeAddress.getText().toString().trim())) {
            showToast("请输入办公地址");
            return;
        } else {
            setData();
        }
    }

    private void setData() {
        infoBean.setName(etCompany.getText().toString().trim());
        infoBean.seteMail(etMoney.getText().toString().trim());
        infoBean.setOrgId(orgid);

        infoBean.setTelPhone(etPhone.getText().toString().trim());
        infoBean.setOfficeAddress(etDetailOfficeAddress.getText().toString().trim());
        if (!StringUtils.isEmpty(itemcity) && !StringUtils.isEmpty(itemzone)) {
            infoBean.setAreaCode(Config.get().getAreaCodeByName(itemcity, itemzone));
        }
        if (!StringUtils.isEmpty(secondTraed)) {
            infoBean.setTradeTypeCode(Config.get().getBaseCodeByName(secondTraed, 2, Constant.INDUSTRY).get(0));
        }
        // 公司规模
        infoBean.setScale(GetConstDataUtils.getOrgUnitScaleList().indexOf(tvCompanyScale.getText().toString().trim()));
        infoBean.setStatus(1);
        infoBean.setOrgId(orgid);
        infoBean.setUnitType(3);
        infoBean.setIntro(etDesc.getText().toString().trim());
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
        Log.d("5848", "fillData: " + infoBean.getStatus());
        if (infoBean.getStatus() == 1) {
//            setRightTitle("");
//            setRightTitleOnClickListener(null);
            tvRightB.setVisibility(View.GONE);
            etMoney.setEnabled(false);
            llOfficeAddress.setEnabled(false);
            tvOfficeAddress.setEnabled(false);
            etPhone.setEnabled(false);
            etDetailOfficeAddress.setEnabled(false);
            ivUpload2.setEnabled(false);
            llType.setEnabled(false);
            llCompanyScale.setEnabled(false);
            etDesc.setEnabled(false);
        }

        if (infoBean != null) {
            if (infoBean.getTradeTypeCode() != null) {
                tvType.setText(Config.get().getBaseNameByCode(infoBean.getTradeTypeCode(), Constant.INDUSTRY));
            }
            if (infoBean.getScale() >= 0) {
                tvCompanyScale.setText(GetConstDataUtils.getOrgUnitScaleList().get(infoBean.getScale()));
            }
            if (infoBean.getIntro() != null) {
                etDesc.setText(infoBean.getIntro());
            }
            if (!StringUtils.isEmpty(infoBean.getLogoPic())) {
                ivUpload2.setImageURI(BuildConfig.OSS_SERVER + infoBean.getLogoPic());
                infoBean.setLogoPic(infoBean.getLogoPic());
            }
        }
        if (infoBean != null) {
            if (infoBean.getRegisterAssets() != null) {
                etMoney.setText(infoBean.geteMail());
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
        }
    }

    @Override
    public void takeSuccess(TResult result, int resultCode) {
        super.takeSuccess(result, resultCode);
        if (result == null || result.getImage() == null) {
            return;
        }
        TImage image = result.getImage();
        String imgKey = "org/" + UuidUtil.getUUID() + ".png";
        if (resultCode == ADPIC_CALLBACK_CODE) {
            infoBean.setLogoPic(imgKey);
            ivUpload2.setImageURI("file://" + image.getOriginalPath());
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });
    }

    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_INSERT).upJson(json).execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
            doJumpSecond();
        }));
    }

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

    private void doJumpSecond() {
        Intent intent = new Intent(this, SubmitSuccessfullyQyActivity.class);
        intent.putExtra("mOrgId", orgid);
        intent.putExtra("status", 0);
        intent.putExtra("mOrgName", orgName);
        intent.putExtra("order", 6);
        startActivity(intent);
        finish();
    }
    /**
     * 判断邮箱格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
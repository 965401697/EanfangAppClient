package net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.picture.IPictureCallBack;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.biz.model.entity.OrgUnitEntity;

import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.authentication.SubmitSuccessfullyQyActivity;
import net.eanfang.worker.ui.activity.my.PersonInfoActivity;
import net.eanfang.worker.ui.base.BaseWorkeActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author WQ
 */
public class AuthCompanyFirstBActivity extends BaseWorkeActivity {
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
    ImageView ivUpload2;
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
        setContentView(R.layout.activity_auth_company_first_b);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
    @Override
    public void initView() {
        tvTitle.setText("完善资料");
        ivLeftB.setOnClickListener(view -> finish());
        orgid = getIntent().getLongExtra("orgid", 0);
        orgName = getIntent().getStringExtra("orgName");
        etCompany.setText(orgName);
        tvRightB.setText("保存");
        tvRightB.setOnClickListener(view -> doVerify());
        setSeCt();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSeCt() {//解决与NestedScrollView冲突
        etDesc.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
    }

    private void initListener() {
        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        ivUpload2.setOnClickListener((v -> {
            RxPerm.get(this).cameraPerm((isSuccess) -> imageV());
        }));
        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "", tvCompanyScale, GetConstDataUtils.getOrgUnitScaleList()));
    }

    private void imageV() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = "org/" + UuidUtil.getUUID() + ".png";
            infoBean.setLogoPic(imgKey);
            GlideUtil.intoImageView(AuthCompanyFirstBActivity.this, "file://" + list.get(0).getPath(), ivUpload2);
            SDKManager.ossKit(AuthCompanyFirstBActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
            });
        }
    };

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

    public void doVerify() {
        if (StringUtils.isEmpty(etCompany.getText().toString().trim())) {
            showToast("请输入单位名称");
            return;
        }
        if (StringUtils.isEmpty(tvType.getText().toString().trim())) {
            showToast("请选择行业类型");
            return;
        }
        if (StringUtils.isEmpty(etDetailOfficeAddress.getText().toString().trim())) {
            showToast("请输入办公地址");
            return;
        }
        if (!isEmail(etMoney.getText().toString().trim())) {
            showToast("请输入正确的电子邮箱");
            return;
        }
        if (StringUtils.isEmpty(etPhone.getText().toString().trim())) {
            showToast("请输入单位电话");
            return;
        }
        if (StringUtils.isEmpty(etDesc.getText().toString().trim())) {
            showToast("请输入公司简介");
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
        if (infoBean.getStatus() != 0) {
            etCompany.setEnabled(false);
        }
        Log.d("5848", "fillData: " + infoBean.getStatus());
        if (infoBean.getStatus() == 1) {
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
                GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + infoBean.getLogoPic(), ivUpload2);
                infoBean.setLogoPic(infoBean.getLogoPic());
            }
        }
        if (infoBean != null) {
            if (infoBean.geteMail() != null) {
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
package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.model.entity.OrgUnitEntity;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import com.eanfang.ui.activity.SelectAddressActivity;
import com.eanfang.ui.base.BaseActivityWithTakePhoto;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermissionUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import net.eanfang.client.R;
import net.eanfang.client.ui.fragment.ContactsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guanluocang
 * 2018年10月12日 10:48:52
 *
 * @desc 新公司认证
 */
public class AuthCompanyFirstActivity extends BaseActivityWithTakePhoto {

    @BindView(R.id.et_company)
    EditText etCompany;
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
    @BindView(R.id.iv_uploadlogo)
    ImageView ivUploadlogo;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.btn_complete)
    Button btnComplete;


    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();
    private AuthCompanyBaseInfoBean byNetBean;

    /**
     * 公司LOGO take photo 回调 code
     */
    private final int ADPIC_CALLBACK_CODE = 400;
    /**
     * 地址回掉code
     */
    private final int ADDRESS_CALLBACK_CODE = 100;
    private String longitude, orgName;
    private String latitude;
    private String itemcity;
    private String itemzone;
    private Long orgid;

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
        // 完善资料
        etCompany.setText(orgName);
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
            if (!StringUtils.isEmpty(byNetBean.getAreaCode())) {
                tvOfficeAddress.setText(Config.get().getAddressByCode(byNetBean.getAreaCode()));
            }
            if (!StringUtils.isEmpty(byNetBean.getLogoPic())) {
                GlideUtil.intoImageView(this,BuildConfig.OSS_SERVER + byNetBean.getLogoPic(),ivUploadlogo);
                infoBean.setLogoPic(byNetBean.getLogoPic());
            }
        }
        if (byNetBean.getStatus() != 0) {
            etCompany.setEnabled(false);
        }
        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        if (byNetBean.getStatus() != 0 && byNetBean.getStatus() != 3) {
            btnComplete.setVisibility(View.GONE);
            ivUploadlogo.setEnabled(false);
            llOfficeAddress.setEnabled(false);
            setOnFouse(etCompany);
            setOnFouse(etLegalPersion);
            setOnFouse(etDetailOfficeAddress);
            setOnFouse(etPhone);
            setOnFouse(etDesc);
        }
        if (byNetBean.getStatus() != 2) {
            setRightGone();
        }
    }

    private void setOnFouse(EditText editText) {
        editText.setEnabled(false);
    }


    private void initListener() {
        ivUploadlogo.setOnClickListener((v -> {
            PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthCompanyFirstActivity.this, ADPIC_CALLBACK_CODE));
        }));

        llOfficeAddress.setOnClickListener((v) -> {
            Intent intent = new Intent(AuthCompanyFirstActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, ADDRESS_CALLBACK_CODE);
        });
        // 进行认证
        btnComplete.setOnClickListener((v) -> {
            // 完善资料
            doVerify();
        });
    }

    /**
     * 进行字段的约束判断
     */
    public void doVerify() {
        if (StringUtils.isEmpty(etCompany.getText().toString().trim())) {
            showToast("请输入单位名称");
            return;
        } else if (StringUtils.isEmpty(etDetailOfficeAddress.getText().toString().trim())) {
            showToast("请输入办公地址");
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

    /**
     * 提交的数据
     */
    private void setData() {
        infoBean.setName(etCompany.getText().toString().trim());

        infoBean.setOrgId(orgid);

        infoBean.setLegalName(etLegalPersion.getText().toString().trim());
        infoBean.setTelPhone(etPhone.getText().toString().trim());
        infoBean.setUnitType(3);
        infoBean.setIntro(etDesc.getText().toString().trim());
        infoBean.setScale(byNetBean.getScale());
        infoBean.setOfficeAddress(etDetailOfficeAddress.getText().toString().trim());
        if (!StringUtils.isEmpty(itemcity) && !StringUtils.isEmpty(itemzone)) {
            infoBean.setAreaCode(Config.get().getAreaCodeByName(itemcity, itemzone));
        } else {
            infoBean.setAreaCode(byNetBean.getAreaCode());
        }
//        if (infoBean.getAdminUserId() != null) {
//            infoBean.setAdminUserId(byNetBean.getAdminUserId());
//        } else {
//            infoBean.setAdminUserId(EanfangApplication.getApplication().getUserId());
//        }

        String json = JSONObject.toJSONString(infoBean);
        commit(json);
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

        if (resultCode == ADPIC_CALLBACK_CODE) {
            infoBean.setLogoPic(imgKey);
            GlideUtil.intoImageView(this,"file://" + image.getOriginalPath(),ivUploadlogo);
        }
        SDKManager.ossKit(this).asyncPutImage(imgKey, image.getOriginalPath(),(isSuccess) -> {});
    }

    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_INSERT)
                .upJson(json)
                .execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
                    //保存成功后，提交认证
//                    byNetBean = new AuthCompanyBaseInfoBean();
//                    byNetBean.setOrgId(bean.getOrgId());
//                    doJumpSecond();
                    ContactsFragment.isRefresh = true;
                    showToast("提交成功");
                    finish();
                }));
    }

    /**
     * 跳转第二部
     */
    private void doJumpSecond() {
        Bundle bundle = new Bundle();
        bundle.putLong("mOrgId", byNetBean.getOrgId());
        JumpItent.jump(AuthCompanyFirstActivity.this, AuthCompanySecondActivity.class, bundle);
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

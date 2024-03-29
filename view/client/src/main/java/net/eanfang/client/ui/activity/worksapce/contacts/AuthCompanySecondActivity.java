package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.bean.Message;

import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.biz.model.entity.OrgUnitEntity;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.base.BaseClienActivity;
import net.eanfang.client.ui.fragment.ContactsFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * Created by guanluocang
 * 2018年10月12日 10:48:52
 *
 * @desc 公司认证 第二步
 */
public class AuthCompanySecondActivity extends BaseClienActivity {

    @BindView(R.id.ed_company_number)
    EditText edCompanyNumber;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.tv_company_scale)
    TextView tvCompanyScale;
    @BindView(R.id.ll_company_scale)
    LinearLayout llCompanyScale;
    @BindView(R.id.tv_type_temp)
    TextView tvTypeTemp;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.ll_type)
    RelativeLayout llType;
    @BindView(R.id.iv_upload)
    ImageView ivUpload;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();
    private AuthCompanyBaseInfoBean byNetBean;

    /**
     * 营业执照 take photo回调 code
     */
    private final int LICENSE_CALLBACK_CODE = 300;
    private String firstTraed, secondTraed;
    private Long orgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_auth_company_second);
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
        setTitle("完善资料");
        setLeftBack(true);
        orgid = getIntent().getLongExtra("mOrgId", 0);
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
                tvType.setText(Config.get().getBaseNameByCode(byNetBean.getTradeTypeCode(), Constant.INDUSTRY));
            }
            if (byNetBean.getScale() >= 0) {
                tvCompanyScale.setText(GetConstDataUtils.getOrgUnitScaleList().get(byNetBean.getScale()));
            }
            if (!StrUtil.isEmpty(byNetBean.getLicensePic())) {
                GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + byNetBean.getLicensePic(), ivUpload);
                infoBean.setLicensePic(byNetBean.getLicensePic());
            }
        }
        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        if (byNetBean.getStatus() != 0 && byNetBean.getStatus() != 3) {
            btnComplete.setVisibility(View.GONE);
            ivUpload.setEnabled(false);
            llCompanyScale.setEnabled(false);
            llType.setEnabled(false);
            setOnFouse(edCompanyNumber);
            setOnFouse(etMoney);
        }
        if (byNetBean.getStatus() != 2) {
            setRightClick(false);
        }
    }

    private void setOnFouse(EditText editText) {
        editText.setEnabled(false);
    }

    private void initListener() {
        ivUpload.setOnClickListener((v) -> {
            RxPerm.get(this).cameraPerm((isSuccess) -> imageV());
        });

        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "",
                tvCompanyScale, GetConstDataUtils.getOrgUnitScaleList()));
        // 进行认证
        btnComplete.setOnClickListener((v) -> {
            // 完善资料
            doVerify();
        });
    }

    private void imageV() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = StrUtil.uuid() + ".png";
            infoBean.setLicensePic(imgKey);
            GlideUtil.intoImageView(AuthCompanySecondActivity.this, "file://" + list.get(0).getPath(), ivUpload);
            SDKManager.ossKit(AuthCompanySecondActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
            });
        }
    };

    /**
     * 进行字段的约束判断
     */
    public void doVerify() {
        if (StrUtil.isEmpty(edCompanyNumber.getText().toString().trim())) {
            showToast("请输入营业执照号码");
            return;
        } else if (StrUtil.isEmpty(etMoney.getText().toString().trim())) {
            showToast("请输入注册资本");
            return;
        } else if (StrUtil.isEmpty(tvType.getText().toString().trim())) {
            showToast("请选择行业类型");
            return;
        } else {
            setData();
        }
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
     * 提交的数据
     */
    private void setData() {
        // 行业类型
        if (!StrUtil.isEmpty(secondTraed)) {
            infoBean.setTradeTypeCode(Config.get().getBaseCodeByName(secondTraed, 2, Constant.INDUSTRY).get(0));
        }
        // 公司规模
        infoBean.setScale(GetConstDataUtils.getOrgUnitScaleList().indexOf(tvCompanyScale.getText().toString().trim()));
        infoBean.setLicenseCode(edCompanyNumber.getText().toString().trim());
        infoBean.setRegisterAssets(etMoney.getText().toString().trim());

        infoBean.setStatus(1);
        infoBean.setOrgId(orgid);

        infoBean.setUnitType(3);
//        if (infoBean.getAdminUserId() != null) {
//            infoBean.setAdminUserId(byNetBean.getAdminUserId());
//        } else {
//            infoBean.setAdminUserId(ClientApplication.get().getUserId());
//        }

        String json = JSONObject.toJSONString(infoBean);
        commit(json);
    }


    /**
     * 保存资料
     */
    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_INSERT)
                .upJson(json)
                .execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
                    //保存成功后，提交认证
                    byNetBean = new AuthCompanyBaseInfoBean();
                    byNetBean.setOrgId(bean.getOrgId());
                    commitVerfiy();
                }));
    }

    /**
     * 提交认证
     */
    private void commitVerfiy() {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SEND_VERIFY + byNetBean.getOrgId())
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    ContactsFragment.isRefresh = true;//从认领企业过来 完成认证刷新公司
                    submitSuccess();
                }));
    }

    private void submitSuccess() {
        Intent intent = new Intent(AuthCompanySecondActivity.this, StateChangeActivity.class);
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgTitle("您的资料已提交成功");
        message.setTip("确定");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);
        EventBus.getDefault().post("customerIsAuthing");
        finish();
        ClientApplication.get().closeActivity(AuthCompanyFirstActivity.class);
    }
}

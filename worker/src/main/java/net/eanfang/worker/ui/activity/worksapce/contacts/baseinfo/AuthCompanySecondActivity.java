package net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.eanfang.model.Message;
import com.eanfang.oss.OSSCallBack;
import com.eanfang.oss.OSSUtils;
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
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/10/17
 * @description 公司认证 基本资料 第二步
 */
public class AuthCompanySecondActivity extends BaseActivityWithTakePhoto {

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
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private Long orgid;

    private String secondTraed;

    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_company_second);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        setTitle("完善资料");
        setLeftBack();
        orgid = getIntent().getLongExtra("mOrgId", 0);
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    infoBean = beans;
                    fillData();
                }));
    }

    private void initListener() {
        ivUpload2.setOnClickListener((v -> {
            PermissionUtils.get(this).getCameraPermission(() -> takePhoto(AuthCompanySecondActivity.this, ADPIC_CALLBACK_CODE));
        }));

        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "",
                tvCompanyScale, GetConstDataUtils.getOrgUnitScaleList()));

        btnComplete.setOnClickListener((v) -> {
            doVerify();
        });
    }

    /**
     * 初始化  填充数据
     */
    private void fillData() {
        //如果不是 状态0草稿  或者3认证拒绝  隐藏提交按钮
        // 0 草稿 3 认证拒绝 1 认证中 2 认证通过
        if (infoBean.getStatus() != 0 && infoBean.getStatus() != 3) {
            btnComplete.setVisibility(View.GONE);
            ivUpload2.setEnabled(false);
            llType.setEnabled(false);
            llCompanyScale.setEnabled(false);
            etDesc.setEnabled(false);
        }
        if (infoBean.getStatus() != 2) {
            setRightGone();
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
    }

    private void setData() {
        // 行业类型
        infoBean.setTradeTypeCode(Config.get().getBaseCodeByName(secondTraed, 2, Constant.INDUSTRY).get(0));
        // 公司规模
        infoBean.setScale(GetConstDataUtils.getOrgUnitScaleList().indexOf(tvCompanyScale.getText().toString().trim()));
        infoBean.setStatus(1);
        infoBean.setOrgId(orgid);
        infoBean.setUnitType(3);
        infoBean.setIntro(etDesc.getText().toString().trim());
        if (infoBean.getAdminUserId() == null) {
            infoBean.setAdminUserId(EanfangApplication.getApplication().getUserId());
        } else {
            infoBean.setAdminUserId(infoBean.getAdminUserId());
        }
        String json = JSONObject.toJSONString(infoBean);
        commit(json);
    }


    /**
     * 进行字段的约束判断
     */
    public void doVerify() {
        if (StringUtils.isEmpty(tvType.getText().toString().trim())) {
            showToast("请选择行业类型");
            return;
        } else if (StringUtils.isEmpty(etDesc.getText().toString().trim())) {
            showToast("请输入单位简介");
            return;
        } else {
            setData();
        }
    }

    /**
     * 保存资料
     */
    private void commit(String json) {
        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_INSERT)
                .upJson(json)
                .execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
                    submitSuccess();
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
        String imgKey = "org/" + UuidUtil.getUUID() + ".png";

        if (resultCode == ADPIC_CALLBACK_CODE) {
            infoBean.setLogoPic(imgKey);
            ivUpload2.setImageURI("file://" + image.getOriginalPath());
        }
        OSSUtils.initOSS(this).asyncPutImage(imgKey, image.getOriginalPath(), new OSSCallBack(this, true) {
        });

    }

    private void submitSuccess() {
        Intent intent = new Intent(AuthCompanySecondActivity.this, StateChangeActivity.class);
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setTitle("提交成功");
        message.setMsgTitle("尊敬的用户，您必须进行资质认证才可以接单，并获得更多订单");
        message.setMsgContent("");
        message.setTip("");
        message.setShowOkBtn(true);
        message.setShowLogo(true);
        bundle.putSerializable("message", message);
        intent.putExtras(bundle);
        startActivity(intent);
        finishSelf();
        EanfangApplication.get().closeActivity(AuthCompanyFirstActivity.class.getName());
    }
}

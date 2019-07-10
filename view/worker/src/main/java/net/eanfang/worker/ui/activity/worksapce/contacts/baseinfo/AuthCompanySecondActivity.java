package net.eanfang.worker.ui.activity.worksapce.contacts.baseinfo;

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
import com.eanfang.biz.model.AuthCompanyBaseInfoBean;
import com.eanfang.biz.model.Message;

import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.UuidUtil;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.biz.model.entity.OrgUnitEntity;
import com.luck.picture.lib.entity.LocalMedia;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.base.BaseWorkeActivity;
import net.eanfang.worker.ui.fragment.ContactsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/10/17
 * @description 公司认证 基本资料 第二步
 */
public class AuthCompanySecondActivity extends BaseWorkeActivity {

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
    ImageView ivUpload2;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private Long orgid;

    private String secondTraed;

    private AuthCompanyBaseInfoBean infoBean = new AuthCompanyBaseInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_auth_company_second);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
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
                    infoBean = beans;
                    fillData();
                }));
    }

    private void initListener() {
        ivUpload2.setOnClickListener((v -> {
            RxPerm.get(this).cameraPerm((isSuccess) -> imageV());
        }));

        llType.setOnClickListener(v -> showTradType());
        llCompanyScale.setOnClickListener(v -> PickerSelectUtil.singleTextPicker(this, "",
                tvCompanyScale, GetConstDataUtils.getOrgUnitScaleList()));

        btnComplete.setOnClickListener((v) -> {
            doVerify();
        });
    }

    private void imageV() {
        SDKManager.getPicture().create(this).takePhoto(iPictureCallBack);
    }

    IPictureCallBack iPictureCallBack = new IPictureCallBack() {
        @Override
        public void onSuccess(List<LocalMedia> list) {
            String imgKey = "org/" + UuidUtil.getUUID() + ".png";
            infoBean.setLogoPic(imgKey);
            GlideUtil.intoImageView(AuthCompanySecondActivity.this, "file://" + list.get(0).getPath(), ivUpload2);
            SDKManager.ossKit(AuthCompanySecondActivity.this).asyncPutImage(imgKey, list.get(0).getPath(), (isSuccess) -> {
            });
        }
    };

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
            setRightClick(false);
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
    }

    private void setData() {
        // 行业类型
        if (!StringUtils.isEmpty(secondTraed)) {
            infoBean.setTradeTypeCode(Config.get().getBaseCodeByName(secondTraed, 2, Constant.INDUSTRY).get(0));
        }
        // 公司规模
        infoBean.setScale(GetConstDataUtils.getOrgUnitScaleList().indexOf(tvCompanyScale.getText().toString().trim()));
        infoBean.setStatus(1);
        infoBean.setOrgId(orgid);
        infoBean.setUnitType(3);
        infoBean.setIntro(etDesc.getText().toString().trim());
//        if (infoBean.getAdminUserId() == null) { todo 李旭让去掉
//            infoBean.setAdminUserId(WorkerApplication.get().getUserId());
//        } else {
//            infoBean.setAdminUserId(infoBean.getAdminUserId());
//        }
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
                    ContactsFragment.isRefresh = true;//从认领企业过来 完成认证刷新公司
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


    private void submitSuccess() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        message.setMsgContent("尊敬的用户，您必须进行资质认证才可以接单，并获得更多订单");
        message.setTip("前往资质认证");
        bundle.putSerializable("message", message);
        message.setMsgHelp("go_quaility");
        bundle.putLong("orgid", orgid);
        JumpItent.jump(AuthCompanySecondActivity.this, StateChangeActivity.class, bundle);
        finish();
        WorkerApplication.get().closeActivity(AuthCompanyFirstActivity.class);
    }
}

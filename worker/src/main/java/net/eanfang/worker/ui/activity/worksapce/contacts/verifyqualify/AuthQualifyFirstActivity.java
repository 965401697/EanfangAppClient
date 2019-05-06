package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthCompanyBaseInfoBean;
import com.eanfang.model.GrantChange;
import com.eanfang.model.QualifyFirstBean;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PickerSelectUtil;
import com.eanfang.util.StringUtils;
import com.eanfang.util.V;
import com.yaf.base.entity.ShopCompanyEntity;
import com.yaf.sys.entity.BaseDataEntity;
import com.yaf.sys.entity.OrgUnitEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/10/23
 * @description 安防公司认证基本资料
 */


public class AuthQualifyFirstActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    // 从业年限
    @BindView(R.id.ll_time_limit)
    LinearLayout llTimeLimit;
    @BindView(R.id.tv_limit)
    TextView tvLimit;

    // 能力等级
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.ll_ability)
    LinearLayout llAbility;
    // 系统类别
    @BindView(R.id.tag_system_type)
    TagFlowLayout tagSystemType;
    // 业务类型
    @BindView(R.id.tag_business_type)
    TagFlowLayout tagBusinessType;

    @BindView(R.id.tv_confim)
    TextView tvConfim;

    @BindView(R.id.rb_company)
    RadioButton rbCompany;
    @BindView(R.id.rv_vendor)
    RadioButton rvVendor;
    @BindView(R.id.rg_company_type)
    RadioGroup rgCompanyType;

    private Long orgid;
    private int verifyStatus;

    // 获取系统类别
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    // 业务类型
    List<BaseDataEntity> businessTypeList = Config.get().getServiceList(1);

    // Orgunit
    private OrgUnitEntity orgUnitEntity = new OrgUnitEntity();
    private ShopCompanyEntity shopCompanyEntity = new ShopCompanyEntity();

    // 系统类别
    private GrantChange grantChange_system = new GrantChange();
    private SystypeBean byNetGrant_system;
    // 业务类别
    private GrantChange grantChange_business = new GrantChange();
    private SystypeBean byNetGrant_business;

    // 公司类型 公司  1   厂商 2
    private int mCompanyType = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_qualify_first);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setTitle("服务认证");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        // TODO: 2018/11/30  今天集合的对象引用 有待优化
        for (BaseDataEntity b : businessTypeList) {
            b.setCheck(false);
        }
        for (BaseDataEntity s : systemTypeList) {
            s.setCheck(false);
        }
    }

    private void initData() {
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid).execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    initSystemData();
                    initBusinessData();
                    initBaseInfo();
                }));

    }

    private void initBaseInfo() {
        EanfangHttp.post(UserApi.FIRST_QUALIFY)
                .params("orgId", orgid + "")
                .execute(new EanfangCallback<QualifyFirstBean>(this, true, QualifyFirstBean.class, (bean) -> {
                    tvAbility.setText(V.v(() -> GetConstDataUtils.getWorkingLevelList().get(bean.getOrgUnit().getShopCompanyEntity().getWorkingLevel())));
                    tvLimit.setText(V.v(() -> GetConstDataUtils.getWorkingYearList().get(bean.getOrgUnit().getShopCompanyEntity().getWorkingYear())));
                    // 厂商
                    if (V.v(() -> bean.getOrgUnit().getShopCompanyEntity().getIs_manufacturer() == 2)) {
                        rvVendor.setChecked(true);
                    } else if (V.v(() -> bean.getOrgUnit().getShopCompanyEntity().getIs_manufacturer() == 1)) {// 公司
                        rbCompany.setChecked(true);
                    }
                }));
    }

    private void initBusinessData() {
        // 业务类别
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_SYS_INFO + orgid + "/BIZ_TYPE")
                .execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
                    byNetGrant_business = bean;
                    addBusResult();
                }));

    }

    private void initSystemData() {
        // 系统类别
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_SYS_INFO + orgid + "/SYS_TYPE")
                .execute(new EanfangCallback<SystypeBean>(this, true, SystypeBean.class, (bean) -> {
                    byNetGrant_system = bean;
                    // 系统类别
                    addSysResult();
                }));

    }


    private void initListener() {
        rgCompanyType.setOnCheckedChangeListener(this);
        tvConfim.setOnClickListener((v) -> {
//            if (verifyStatus == 0 || verifyStatus == 3) {
                doVerify();
//            }
        });

        //从业年限
        llTimeLimit.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvLimit, GetConstDataUtils.getWorkingYearList());
        });

        //能力等级
        llAbility.setOnClickListener((v) -> {
            PickerSelectUtil.singleTextPicker(this, "", tvAbility, GetConstDataUtils.getWorkingLevelList());
        });
    }

    private void doVerify() {
        // 系统类别
        List<Integer> checkList_system = Stream.of(systemTypeList).filter(beans -> beans.isCheck() == true && Stream.of(byNetGrant_system.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0).map(beans -> beans.getDataId()).distinct().toList();
        List<Integer> unCheckList_system = Stream.of(systemTypeList).filter(beans -> beans.isCheck() == false && Stream.of(byNetGrant_system.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0).map(beans -> beans.getDataId()).distinct().toList();
        grantChange_system.setAddIds(checkList_system);
        grantChange_system.setDelIds(unCheckList_system);
        if (checkList_system.size() <= 0) {// 当前本地提示没有进行选择
            if (byNetGrant_system.getList().size() <= 0) {// 网络获取提示没有进行选择
                showToast("请选择一种系统类别");
                return;
            } else {
                if (unCheckList_system.size() == byNetGrant_system.getList().size()) {
                    showToast("请选择一种系统类别");
                    return;
                }
            }
        }

        // 业务类别
        List<Integer> checkList_business = Stream.of(businessTypeList).filter(beans -> beans.isCheck() == true && Stream.of(byNetGrant_business.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() == 0).map(beans -> beans.getDataId()).distinct().toList();
        List<Integer> unCheckList_business = Stream.of(businessTypeList).filter(beans -> beans.isCheck() == false && Stream.of(byNetGrant_business.getList()).filter(existsBean -> existsBean.getDataId().equals(beans.getDataId())).count() > 0).map(beans -> beans.getDataId()).distinct().toList();

        grantChange_business.setAddIds(checkList_business);
        grantChange_business.setDelIds(unCheckList_business);
        if (checkList_business.size() <= 0) {
            if (byNetGrant_business.getList().size() <= 0) {
                showToast("请选择一种业务类别");
                return;
            } else {
                if (unCheckList_business.size() == byNetGrant_business.getList().size()) {
                    showToast("请选择一种业务类别");
                    return;
                }
            }
        }
        shopCompanyEntity.setWorking_level(1);
        shopCompanyEntity.setWorking_year(1);
        shopCompanyEntity.setIs_manufacturer(mCompanyType);
        orgUnitEntity.setOrgId(orgid);
        orgUnitEntity.setShopCompanyEntity(shopCompanyEntity);

        commitData();
    }

    public void addSysResult() {
        for (int i = 0; i < systemTypeList.size(); i++) {
            for (int j = 0; j < byNetGrant_system.getList().size(); j++) {
                if (systemTypeList.get(i).getDataId().equals(byNetGrant_system.getList().get(j).getDataId())) {
                    systemTypeList.get(i).setCheck(true);
                }
            }
        }
        tagSystemType.setAdapter(new TagAdapter<BaseDataEntity>(systemTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AuthQualifyFirstActivity.this).inflate(R.layout.layout_trouble_result_item, tagSystemType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }

            @Override
            public boolean setSelected(int position, BaseDataEntity baseDataEntity) {
                Long coutn = Stream.of(byNetGrant_system.getList()).filter(bean -> bean.getDataId().equals(baseDataEntity.getDataId())).count();
                if (coutn > 0) {
                    return true;
                }
                return false;
            }
        });

        tagSystemType.setOnTagClickListener((view, position, parent) -> {
            systemTypeList.get(position).setCheck(!systemTypeList.get(position).isCheck());
            return true;
        });

    }

    public void addBusResult() {
        // 业务类型
        for (int i = 0; i < businessTypeList.size(); i++) {
            for (int j = 0; j < byNetGrant_business.getList().size(); j++) {
                if (businessTypeList.get(i).getDataId().equals(byNetGrant_business.getList().get(j).getDataId())) {
                    businessTypeList.get(i).setCheck(true);
                }
            }
        }
        tagBusinessType.setAdapter(new TagAdapter<BaseDataEntity>(businessTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(AuthQualifyFirstActivity.this).inflate(R.layout.layout_trouble_result_item, tagBusinessType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }

            @Override
            public boolean setSelected(int position, BaseDataEntity baseDataEntity) {
                Long coutn = Stream.of(byNetGrant_business.getList()).filter(bean -> bean.getDataId().equals(baseDataEntity.getDataId())).count();
                if (coutn > 0) {
                    return true;
                }
                return false;
            }
        });
        tagBusinessType.setOnTagClickListener((view, position, parent) -> {
            businessTypeList.get(position).setCheck(!businessTypeList.get(position).isCheck());
            return true;
        });
    }


    private void commitData() {

        HashMap hashMapData = new HashMap();
        hashMapData.put("orgUnit", orgUnitEntity);
        hashMapData.put("companySysGrantChange", grantChange_system);
        hashMapData.put("companyBizGrantChange", grantChange_business);

        String requestContent = com.alibaba.fastjson.JSONObject.toJSONString(hashMapData);
        EanfangHttp.post(UserApi.GET_WORKER_COMPANY_QUALIFY)
                .upJson(requestContent)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    jump();
                }));


    }

    private void jump() {
        Bundle bundle = new Bundle();
        bundle.putLong("orgid", orgid);
        bundle.putInt("verifyStatus", verifyStatus);
        JumpItent.jump(AuthQualifyFirstActivity.this, AuthQualifySecondActivity.class, bundle);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            //安防工程公司
            case R.id.rb_company:
                mCompanyType = 1;
                break;
            // 生产厂商
            case R.id.rv_vendor:
                mCompanyType = 2;
                break;
        }

    }
}

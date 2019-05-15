package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.AptitudeCertificateBean;
import com.eanfang.biz.model.QualifyFirstBean;
import com.eanfang.biz.model.entity.BaseDataEntity;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.V;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.QualifyListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2018/10/24
 * @description 查看已认证状态 资质信息
 */

public class QualifyDataActivity extends BaseActivity {

    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.tv_ability)
    TextView tvAbility;
    @BindView(R.id.rb_company)
    RadioButton rbCompany;
    @BindView(R.id.rv_vendor)
    RadioButton rvVendor;
    @BindView(R.id.rg_company_type)
    RadioGroup rgCompanyType;
    @BindView(R.id.tag_system_type)
    TagFlowLayout tagSystemType;
    @BindView(R.id.tag_business_type)
    TagFlowLayout tagBusinessType;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private QualifyListAdapter qualifyListAdapter;
    private Long orgid;


    // 获取系统类别
    List<BaseDataEntity> mSystemTypeAllList = Config.get().getBusinessList(1);
    List<BaseDataEntity> systemTypeList = new ArrayList<>();
    // 业务类别
    List<BaseDataEntity> mBusinessTypeAllList = Config.get().getServiceList(1);
    List<BaseDataEntity> businessTypeList = new ArrayList<>();


    // 认证状态
    private String isAuth = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_data);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("技能资质");
        setLeftBack();
        orgid = getIntent().getLongExtra("orgid", 0);
        isAuth = getIntent().getStringExtra("isAuth");
    }

    private void initData() {
        tagSystemType.setEnabled(false);
        tagBusinessType.setEnabled(false);

        initBaseInfo();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        qualifyListAdapter = new QualifyListAdapter(false);
        qualifyListAdapter.bindToRecyclerView(recyclerView);

        qualifyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", (Serializable) adapter.getData().get(position));
                bundle.putString("isAuth", isAuth);
                JumpItent.jump(QualifyDataActivity.this, AddAuthQualifyActivity.class, bundle);
            }
        });
        getQuailifyData();
    }

    private void initBaseInfo() {
        EanfangHttp.post(UserApi.FIRST_QUALIFY)
                .params("orgId", orgid + "")
                .execute(new EanfangCallback<QualifyFirstBean>(this, true, QualifyFirstBean.class, (bean) -> {
                    tvAbility.setText(V.v(() -> GetConstDataUtils.getWorkingLevelList().get(bean.getOrgUnit().getShopCompanyEntity().getWorkingLevel())));
                    tvLimit.setText(V.v(() -> GetConstDataUtils.getWorkingYearList().get(V.v(() -> bean.getOrgUnit().getShopCompanyEntity().getWorkingYear()))));
                    // 厂商
                    if (V.v(() -> bean.getOrgUnit().getShopCompanyEntity().getIsManufacturer()) == 2) {
                        rvVendor.setChecked(true);
                    } else if (V.v(() -> bean.getOrgUnit().getShopCompanyEntity().getIsManufacturer()) == 1) {// 公司
                        rbCompany.setChecked(true);
                    }
                    initBusinessData(bean);
                }));
    }


    /**
     * 资质证书
     */
    private void getQuailifyData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orgId", orgid + "");
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<AptitudeCertificateBean>(this, true, AptitudeCertificateBean.class) {
                    @Override
                    public void onSuccess(AptitudeCertificateBean bean) {
                        if (bean.getList().size() > 0) {
                            qualifyListAdapter.setNewData(bean.getList());
                        }
                    }
                });
    }

    private void initBusinessData(QualifyFirstBean qualifyFirstBean) {
        // 系统类别 datatype ：1
        for (BaseDataEntity baseDataEntity : qualifyFirstBean.getCompany2baseDataList()) {
            if (baseDataEntity.getDataType() == 1) {
                systemTypeList.add(baseDataEntity);
            }
        }
        // 业务类别 datatype ：2
        for (BaseDataEntity baseDataEntity : qualifyFirstBean.getCompany2baseDataList()) {
            if (baseDataEntity.getDataType() == 2) {
                businessTypeList.add(baseDataEntity);
            }
        }
        addSysResult();
        addBusResult();
    }

    public void addSysResult() {
        for (int i = 0; i < mSystemTypeAllList.size(); i++) {
            for (int j = 0; j < systemTypeList.size(); j++) {
                if (mSystemTypeAllList.get(i).getDataId().equals(systemTypeList.get(j).getDataId())) {
                    mSystemTypeAllList.get(i).setCheck(true);
                }
            }
        }
        tagSystemType.setAdapter(new TagAdapter<BaseDataEntity>(mSystemTypeAllList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(QualifyDataActivity.this).inflate(R.layout.layout_trouble_result_item, tagSystemType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }

            @Override
            public boolean setSelected(int position, BaseDataEntity baseDataEntity) {
                Long coutn = Stream.of(systemTypeList).filter(bean -> bean.getDataId().equals(baseDataEntity.getDataId())).count();
                return coutn > 0;
            }

        });
    }

    public void addBusResult() {
        // 业务类型
        for (int i = 0; i < mBusinessTypeAllList.size(); i++) {
            for (int j = 0; j < businessTypeList.size(); j++) {
                if (mBusinessTypeAllList.get(i).getDataId().equals(businessTypeList.get(j).getDataId())) {
                    mBusinessTypeAllList.get(i).setCheck(true);
                }
            }
        }
        tagBusinessType.setAdapter(new TagAdapter<BaseDataEntity>(mBusinessTypeAllList) {
            @Override
            public View getView(FlowLayout parent, int position, BaseDataEntity mrepairResult) {
                TextView tv = (TextView) LayoutInflater.from(QualifyDataActivity.this).inflate(R.layout.layout_trouble_result_item, tagBusinessType, false);
                tv.setText(mrepairResult.getDataName());
                return tv;
            }

            @Override
            public boolean setSelected(int position, BaseDataEntity baseDataEntity) {
                Long coutn = Stream.of(businessTypeList).filter(bean -> bean.getDataId().equals(baseDataEntity.getDataId())).count();
                return coutn > 0;
            }
        });
    }

    @OnClick(R.id.tv_area)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLook", true);
        bundle.putLong("orgid", orgid);
        JumpItent.jump(QualifyDataActivity.this, AuthQualifySecondActivity.class, bundle);
    }
}

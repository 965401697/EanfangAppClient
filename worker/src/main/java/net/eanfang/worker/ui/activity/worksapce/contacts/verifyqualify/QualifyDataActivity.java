package net.eanfang.worker.ui.activity.worksapce.contacts.verifyqualify;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AuthCompanyBaseInfoBean;
import com.eanfang.model.QualifyListBean;
import com.eanfang.model.SystypeBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.yaf.sys.entity.BaseDataEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.QualifyListAdapter;

import java.io.Serializable;
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
    List<BaseDataEntity> systemTypeList = Config.get().getBusinessList(1);
    private SystypeBean byNetGrant_system;
    // 业务类别
    List<BaseDataEntity> businessTypeList = Config.get().getServiceList(1);
    private SystypeBean byNetGrant_business;

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
        EanfangHttp.get(UserApi.GET_COMPANY_ORG_INFO + orgid)
                .execute(new EanfangCallback<AuthCompanyBaseInfoBean>(this, true, AuthCompanyBaseInfoBean.class, (beans) -> {
                    tagSystemType.setEnabled(false);
                    tagBusinessType.setEnabled(false);
                    initSystemData();
                    initBusinessData();
                }));

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

    /**
     * 资质证书
     */
    private void getQuailifyData() {
        QueryEntry queryEntry = new QueryEntry();

        queryEntry.getEquals().put("accId", String.valueOf(EanfangApplication.get().getAccId()));
        queryEntry.getEquals().put("type", "0");
        EanfangHttp.post(UserApi.LIST_QUALIFY)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<QualifyListBean>(this, true, QualifyListBean.class) {
                    @Override
                    public void onSuccess(QualifyListBean bean) {
                        if (bean.getList().size() > 0) {
                            qualifyListAdapter.setNewData(bean.getList());
                        }
                    }
                });
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
                TextView tv = (TextView) LayoutInflater.from(QualifyDataActivity.this).inflate(R.layout.layout_trouble_result_item, tagSystemType, false);
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
                TextView tv = (TextView) LayoutInflater.from(QualifyDataActivity.this).inflate(R.layout.layout_trouble_result_item, tagBusinessType, false);
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
    }

    @OnClick(R.id.tv_area)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLook", true);
        bundle.putLong("orgid", orgid);
        JumpItent.jump(QualifyDataActivity.this, AuthQualifySecondActivity.class, bundle);
    }
}

package net.eanfang.worker.ui.activity.authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.AllZzJxTjListBean;
import com.eanfang.model.JxSbZzNlListBean;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MechanicalOrganizationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WQ 1组织&2机械 复用界面
 */
public class MechanicalOrganizationActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    private int isAuth;
    private int type;
    private Long mOrgId;
    private MechanicalOrganizationAdapter mechanicalOrganizationAdapter;
    private List<JxSbZzNlListBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanical_organization);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setLeftBack();
        setRightTitle("保存");
        isAuth = getIntent().getIntExtra("isAuth", 0);
        mOrgId = getIntent().getLongExtra("orgid", 0);
        type = getIntent().getIntExtra("type", 1);
        setTitle(type == 1 ? "组织能力" : "机械设备");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        mechanicalOrganizationAdapter = new MechanicalOrganizationAdapter();
        mechanicalOrganizationAdapter.bindToRecyclerView(recyclerView);
        initData();
    }

    private void initData() {
        EanfangHttp.post(type == 1 ? UserApi.XG_SG_ZZ_NL_QY_NL : UserApi.XG_JX_SB_QY_NL).params("orgId", mOrgId).execute(new EanfangCallback<JxSbZzNlListBean>(this, true, JxSbZzNlListBean.class, (bean) -> {
            Log.d("wq66", "onSuccess: " + bean.toString());
            list = bean.getList();
            mechanicalOrganizationAdapter.setNewData(list);
            recyclerView.setItemViewCacheSize(list.size());
        }));
    }

    @OnClick(R.id.tv_right)
    public void onClick() {
        setData();
    }

    List<AllZzJxTjListBean.CompanyAbilityGrantChangeBean.AddListBean> addListBeans = new ArrayList<>();
    List<AllZzJxTjListBean.CompanyAbilityGrantChangeBean.DelListBean> delListBeans = new ArrayList<>();

    List<AllZzJxTjListBean.CompanyToolGrantChangeBean.AddListBeanX> addListBeansB = new ArrayList<>();
    List<AllZzJxTjListBean.CompanyToolGrantChangeBean.DelListBeanX> delListBeansB = new ArrayList<>();

    @SuppressLint("LongLogTag")
    private void setData() {
        AllZzJxTjListBean allZzJxTjListBean = new AllZzJxTjListBean();
        AllZzJxTjListBean.OrgUnitBean orgUnitBean = new AllZzJxTjListBean.OrgUnitBean();
        orgUnitBean.setOrgId(mOrgId);
        AllZzJxTjListBean.CompanyAbilityGrantChangeBean companyAbilityGrantChangeBean = new AllZzJxTjListBean.CompanyAbilityGrantChangeBean();
        AllZzJxTjListBean.CompanyToolGrantChangeBean companyToolGrantChangeBean = new AllZzJxTjListBean.CompanyToolGrantChangeBean();
        delListBeans.clear();
        delListBeans.clear();
        addListBeansB.clear();
        delListBeansB.clear();
        if (type == 1) {
            for (JxSbZzNlListBean.ListBean bean : list) {
                if (bean.getCompany2baseDataEntity().getRemark().equals("0")) {
                    delListBeans.add(new AllZzJxTjListBean.CompanyAbilityGrantChangeBean.DelListBean(bean.getDataId(), bean.getCompany2baseDataEntity().getRemark(), bean.getCompany2baseDataEntity().getUnits()));
                } else {
                    addListBeans.add(new AllZzJxTjListBean.CompanyAbilityGrantChangeBean.AddListBean(bean.getDataId(), bean.getCompany2baseDataEntity().getRemark(), bean.getCompany2baseDataEntity().getUnits()));
                }
            }
            companyAbilityGrantChangeBean.setAddList(addListBeans);
            companyAbilityGrantChangeBean.setDelList(delListBeans);
            companyToolGrantChangeBean=null;
        } else {
            for (JxSbZzNlListBean.ListBean bean : list) {
                if (bean.getCompany2baseDataEntity().getRemark().equals("0")) {
                    delListBeansB.add(new AllZzJxTjListBean.CompanyToolGrantChangeBean.DelListBeanX(bean.getDataId(), bean.getCompany2baseDataEntity().getRemark(),bean.getCompany2baseDataEntity().getUnits()));
                } else {
                    addListBeansB.add(new AllZzJxTjListBean.CompanyToolGrantChangeBean.AddListBeanX(bean.getDataId(), bean.getCompany2baseDataEntity().getRemark(), bean.getCompany2baseDataEntity().getUnits()));
                }
            }
            companyToolGrantChangeBean.setAddList(addListBeansB);
            companyToolGrantChangeBean.setDelList(delListBeansB);
            companyAbilityGrantChangeBean=null;
        }
        allZzJxTjListBean.setCompanyAbilityGrantChange(companyAbilityGrantChangeBean);
        allZzJxTjListBean.setCompanyToolGrantChange(companyToolGrantChangeBean);
        allZzJxTjListBean.setOrgUnit(orgUnitBean);
        Log.d("wq5565", "setData: "+"\n"+UserApi.ALL_ZZ_JX_TJ+"\n"+JSONObject.toJSONString(allZzJxTjListBean));
        EanfangHttp.post(UserApi.ALL_ZZ_JX_TJ).upJson(JSONObject.toJSONString(allZzJxTjListBean)).execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
            finish();
        }));
    }
}

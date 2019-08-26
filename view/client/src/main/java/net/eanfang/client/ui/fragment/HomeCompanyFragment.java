package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.bean.CompanyBean;
import com.eanfang.biz.model.bean.HomeCompanyBean;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.LocationUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.MainActivity;
import net.eanfang.client.ui.activity.worksapce.SelectCompanyActivity;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.activity.worksapce.repair.QuickRepairActivity;
import net.eanfang.client.ui.activity.worksapce.repair.SecurityCompanyDetailActivity;
import net.eanfang.client.ui.adapter.FragmentHomeCompanyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hutool.core.thread.ThreadUtil;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :安防公司、找技师页面
 */
public class HomeCompanyFragment extends BaseFragment {
    private static final String PAGE_TYPE = "pageType";
    @BindView(R.id.rec_home_company)
    RecyclerView mRecHomeCompany;
    @BindView(R.id.tv_home_company_more)
    TextView mTvHomeComanyMore;
    private FragmentHomeCompanyAdapter adapter;
    /**
     * 0 安防公司
     * 1 找技师
     */
    private int mPageType;
    private int mCityId = 102;
    private int mProvinceId;

    private RepairOrderEntity mRepairOrderEntity;

    public static HomeCompanyFragment getInstance(int pageType) {
        HomeCompanyFragment homeCompanyFragment = new HomeCompanyFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(PAGE_TYPE, pageType);
        homeCompanyFragment.setArguments(arguments);
        return homeCompanyFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_company;
    }

    @Override
    protected void initData(Bundle arguments) {
        if (arguments != null) {
            mPageType = arguments.getInt(PAGE_TYPE, 0);
        }
    }

    @Override
    protected void onLazyLoad() {
        getLocation();
        if (adapter == null) {
            return;
        }
    }

    /**
     * 请求数据
     */
    private void initListData(int areaId) {
        EanfangHttp.post(NewApiService.HOME_COMPANY_LIST)
                .params("page", "1")
                .params("size", "2")
                .params("areaId", areaId)
                .execute(new EanfangCallback<CompanyBean>(getActivity(), true, CompanyBean.class, bean -> {
                    boolean toRequestNew = (bean.getList() == null || bean.getList().size() == 0);
                    if (toRequestNew) {
                        initListData(mProvinceId);
                        return;
                    }
                    adapter.getData().clear();
                    ArrayList<HomeCompanyBean> homeCompanyBeans = new ArrayList<>();
                    for (CompanyBean.ListBean companyBean : bean.getList()) {
                        HomeCompanyBean homeCompanyBean = new HomeCompanyBean();
                        homeCompanyBean.setAreaCode(companyBean.getCompanyEntity().getAreaCode());
                        homeCompanyBean.setDesignCount(companyBean.getDesignCount());
                        homeCompanyBean.setGoodRate(companyBean.getGoodRate());
                        homeCompanyBean.setInstallCount(companyBean.getInstallCount());
                        homeCompanyBean.setLogoPic(companyBean.getCompanyEntity().getLogoPic());
                        homeCompanyBean.setName(companyBean.getCompanyEntity().getName());
                        homeCompanyBean.setRepairCount(companyBean.getRepairCount());
                        homeCompanyBean.setStatus(companyBean.getCompanyEntity().getStatus());
                        homeCompanyBean.setPageType(0);
                        homeCompanyBean.setOrgEntity(companyBean.getOrgEntity());
                        homeCompanyBeans.add(homeCompanyBean);
                    }
                    adapter.setNewData(homeCompanyBeans);
                }));
    }

    @Override
    protected void initView() {

        mRecHomeCompany.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecHomeCompany.addItemDecoration(new DividerItemDecoration(getContext()));

        adapter = new FragmentHomeCompanyAdapter();
        adapter.bindToRecyclerView(mRecHomeCompany);

    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            Bundle mBundleOrgDetail = new Bundle();
            mBundleOrgDetail.putString("mOrgId", String.valueOf(adapter.getData().get(position).getOrgEntity().getOrgId()));
            JumpItent.jump(getActivity(), SecurityCompanyDetailActivity.class, mBundleOrgDetail);
        }));
        adapter.setOnItemChildClickListener(((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_home_company_install:
                    Bundle bundle_install = new Bundle();
                    bundle_install.putString("type", "install");
                    JumpItent.jump(getActivity(), QuickRepairActivity.class, bundle_install);
                    break;
                case R.id.btn_home_company_repair:
                    mRepairOrderEntity = new RepairOrderEntity();
                    Bundle bundle_repair = new Bundle();
                    bundle_repair.putString("type", "repair");
                    mRepairOrderEntity.setAssigneeCompanyId(adapter.getData().get(position).getOrgEntity().getCompanyId());
                    mRepairOrderEntity.setAssigneeTopCompanyId(adapter.getData().get(position).getOrgEntity().getTopCompanyId());
                    mRepairOrderEntity.setAssigneeOrgCode(adapter.getData().get(position).getOrgEntity().getOrgCode());
                    bundle_repair.putSerializable("mRepairOrderEntity", mRepairOrderEntity);
                    JumpItent.jump(getActivity(), QuickRepairActivity.class, bundle_repair);
                    break;
                default:
                    break;
            }
        }));
        /**
         * 0 安防公司
         * 1 找技师
         */
        mTvHomeComanyMore.setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("areaId", mCityId);
            JumpItent.jump(getActivity(), SelectCompanyActivity.class, bundle);
        });
    }


    /**
     * 获取当前定位
     */
    private void getLocation() {
        ThreadUtil.excAsync(() -> {
            LocationUtil.location((MainActivity) getActivity(), (location) -> {
                String code = Config.get().getAreaCodeByName(location.getCity(), location.getDistrict());
                int cityId = Config.get().getBaseIdByCode(code, 2, Constant.AREA);
                mProvinceId = Config.get().getBaseIdByCode(code, 1, Constant.AREA);
                if (cityId != 0 && mCityId != cityId) {
                    mCityId = cityId;
                }
                initListData(mCityId);
            });
        }, false);
    }

}

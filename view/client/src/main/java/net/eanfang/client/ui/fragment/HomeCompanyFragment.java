package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.CompanyBean;
import com.eanfang.biz.model.HomeWorkerBean;
import com.eanfang.biz.model.security.HomeCompanyBean;
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
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
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
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private int mAreaId = 102;
    private boolean mIsRequestOne = false;

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
        getLocation();
        if (arguments != null) {
            mPageType = arguments.getInt(PAGE_TYPE, 0);
        }
    }

    @Override
    protected void onLazyLoad() {
        if (adapter == null) {
            return;
        }
        initListData(mAreaId);
    }

    /**
     * 请求数据
     */
    private void initListData(int areaId) {
        if (mPageType == 0) {
            EanfangHttp.post(NewApiService.HOME_COMPANY_LIST).params("page", "1").params("size", "2").params("areaId", areaId).execute(new EanfangCallback<CompanyBean>(getActivity(), true, CompanyBean.class, bean -> {
                boolean toRequestNew = !mIsRequestOne && (bean.getList() == null || bean.getList().size() == 0);
                if (toRequestNew) {
                    mIsRequestOne = true;
                    initListData(102);
                    return;
                }
                mIsRequestOne = false;
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
                    homeCompanyBeans.add(homeCompanyBean);
                }
                adapter.setNewData(homeCompanyBeans);
            }));
        } else {
            adapter.setOnItemClickListener((adapter, view, position) -> {
                HomeCompanyBean homeCompanyBean = (HomeCompanyBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
                intent.putExtra("companyUserId", homeCompanyBean.getCompanyUserId());
                intent.putExtra("workerId", String.valueOf(homeCompanyBean.getId()));
                intent.putExtra("doorFee", 0);
                startActivity(intent);
            });
            EanfangHttp.post(NewApiService.HOME_WORKER_LIST).params("page", "1").params("size", "2").params("areaId", areaId).execute(new EanfangCallback<HomeWorkerBean>(getActivity(), true, HomeWorkerBean.class, bean -> {
                boolean toRequestNew = !mIsRequestOne && (bean.getList() == null || bean.getList().size() == 0);
                if (toRequestNew) {
                    mIsRequestOne = true;
                    initListData(102);
                    return;
                }
                mIsRequestOne = false;
                adapter.getData().clear();
                ArrayList<HomeCompanyBean> homeCompanyBeans = new ArrayList<>();
                for (HomeWorkerBean.ListBean companyBean : bean.getList()) {
                    homeCompanyBeans.add(companyBean.getHomeCompanyBean());
                }
                adapter.setNewData(homeCompanyBeans);
            }));
        }
    }

    @Override
    protected void initView() {
        mRecHomeCompany.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecHomeCompany.addItemDecoration(new DividerItemDecoration(getContext()));

        ThreadUtil.execute(() -> {
            for (int i = 0; i < 50; i++) {
                if (Config.get().getBaseDataBean() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter = new FragmentHomeCompanyAdapter();
                        adapter.bindToRecyclerView(mRecHomeCompany);
                        onLazyLoad();
                    });
                    return;
                }
                ThreadUtil.safeSleep(1000);
            }
        });

    }

    @Override
    protected void setListener() {
        /**
         * 0 安防公司
         * 1 找技师
         */
        mTvHomeComanyMore.setOnClickListener((v) -> {
            if (mPageType == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("areaId", mAreaId);
                JumpItent.jump(getActivity(), SelectCompanyActivity.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHome", true);
                JumpItent.jump(getActivity(), SelectWorkerActivity.class, bundle);
            }
        });
    }


    /**
     * 获取当前定位
     */
    private void getLocation() {
        ThreadUtil.excAsync(() -> {
            LocationUtil.location((MainActivity) getActivity(), (location) -> {
                String code = Config.get().getAreaCodeByName(location.getCity(), location.getDistrict());
                int areaId = Config.get().getBaseIdByCode(code, 2, Constant.AREA);
                if (areaId != 0 && mAreaId != areaId) {
                    mAreaId = areaId;
                    initListData(mAreaId);
                }
            });
        }, false);
    }
}

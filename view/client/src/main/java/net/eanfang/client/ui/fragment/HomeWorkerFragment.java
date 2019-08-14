package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.bean.HomeCompanyBean;
import com.eanfang.biz.model.bean.HomeWorkerBean;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;
import com.eanfang.util.LocationUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.MainActivity;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.activity.worksapce.repair.QuickRepairActivity;
import net.eanfang.client.ui.adapter.FragmentHomeCompanyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hutool.core.thread.ThreadUtil;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :找技师页面
 */
public class HomeWorkerFragment extends BaseFragment {
    @BindView(R.id.rec_home_company)
    RecyclerView mRecHomeCompany;
    @BindView(R.id.tv_home_company_more)
    TextView mTvHomeComanyMore;
    private FragmentHomeCompanyAdapter adapter;
    /**
     * 0 安防公司
     * 1 找技师
     */
    private int mCityId = 102;
    private int mProvinceId;
    private String mAreaCode = "";

    public static HomeWorkerFragment getInstance(int pageType) {
        HomeWorkerFragment homeCompanyFragment = new HomeWorkerFragment();
        Bundle arguments = new Bundle();
        homeCompanyFragment.setArguments(arguments);
        return homeCompanyFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_home_company;
    }

    @Override
    protected void initData(Bundle arguments) {
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
        EanfangHttp.post(NewApiService.HOME_WORKER_LIST).params("page", "1").params("size", "2").params("areaId", areaId).execute(new EanfangCallback<HomeWorkerBean>(getActivity(), true, HomeWorkerBean.class, bean -> {
            boolean toRequestNew = (bean.getList() == null || bean.getList().size() == 0);
            if (toRequestNew) {
                initListData(mProvinceId);
                return;
            }
            adapter.getData().clear();
            ArrayList<HomeCompanyBean> homeCompanyBeans = new ArrayList<>();
            for (HomeWorkerBean.ListBean companyBean : bean.getList()) {
                homeCompanyBeans.add(companyBean.getHomeCompanyBean());
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
        adapter.setOnItemClickListener((adapter, view, position) -> {
            HomeCompanyBean homeCompanyBean = (HomeCompanyBean) adapter.getData().get(position);
            Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
            intent.putExtra("companyUserId", homeCompanyBean.getCompanyUserId());
            intent.putExtra("workerId", String.valueOf(homeCompanyBean.getId()));
            intent.putExtra("doorFee", 0);
            startActivity(intent);
        });

        adapter.setOnItemChildClickListener(((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_home_company_install:
                    Bundle bundle_install = new Bundle();
                    bundle_install.putString("type", "install");
                    bundle_install.putString("assigneeUserId", adapter.getData().get(position).get);
                    bundle_install.putString("assigneeCompanyId", "install");
                    bundle_install.putString("assigneeTopCompanyId", "install");
                    bundle_install.putString("assigneeOrgCode", "install");
                    JumpItent.jump(getActivity(), QuickRepairActivity.class, bundle_install);
                    break;
                case R.id.btn_home_company_repair:
                    Bundle bundle_repair = new Bundle();
                    bundle_repair.putString("type", "repair");
                    bundle_repair.putString("assigneeUserId", "install");
                    bundle_repair.putString("assigneeCompanyId", "install");
                    bundle_repair.putString("assigneeTopCompanyId", "install");
                    bundle_repair.putString("assigneeOrgCode", "install");
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
            bundle.putBoolean("isHome", true);
            bundle.putString("mAreaCode", mAreaCode);
            JumpItent.jump(getActivity(), SelectWorkerActivity.class, bundle);
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
                mAreaCode = code;
                mProvinceId = Config.get().getBaseIdByCode(code, 1, Constant.AREA);
                if (cityId != 0 && mCityId != cityId) {
                    mCityId = cityId;
                }
                initListData(mCityId);
            });
        }, false);
    }

}

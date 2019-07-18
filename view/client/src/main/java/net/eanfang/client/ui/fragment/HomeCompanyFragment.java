package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.CompanyBean;
import com.eanfang.biz.model.HomeWorkerBean;
import com.eanfang.biz.model.security.HomeCompanyBean;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
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
        if (adapter == null) {
            return;
        }
        if (mPageType == 0) {
            EanfangHttp.post(NewApiService.HOME_COMPANY_LIST).params("page", "1").params("size", "2").execute(new EanfangCallback<CompanyBean>(getActivity(), true, CompanyBean.class, bean -> {
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
            EanfangHttp.post(NewApiService.HOME_WORKER_LIST).params("page", "1").params("size", "2").execute(new EanfangCallback<HomeWorkerBean>(getActivity(), true, HomeWorkerBean.class, bean -> {
                Log.d("INSERT_HOME_WORKER", bean.toString());
                adapter.getData().clear();
                ArrayList<HomeCompanyBean> homeCompanyBeans = new ArrayList<>();
                for (HomeWorkerBean.ListBean companyBean : bean.getList()) {
                    HomeCompanyBean homeCompanyBean = new HomeCompanyBean();
                    homeCompanyBean.setAreaCode(companyBean.getAccountEntity().getAreaCode());
                    homeCompanyBean.setDesignCount(companyBean.getDesignNum());
                    homeCompanyBean.setGoodRate(companyBean.getGoodRate());
                    homeCompanyBean.setInstallCount(companyBean.getInstallNum());
                    homeCompanyBean.setLogoPic(companyBean.getAccountEntity().getAvatar());
                    homeCompanyBean.setName(companyBean.getAccountEntity().getRealName());
                    homeCompanyBean.setRepairCount(companyBean.getRepairCount());
                    homeCompanyBean.setLevel(companyBean.getVerifyEntity().getWorkingLevel());
                    homeCompanyBean.setPractitionerYears(companyBean.getVerifyEntity().getWorkingYear());
                    homeCompanyBean.setPageType(1);
                    homeCompanyBeans.add(homeCompanyBean);
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
                JumpItent.jump(getActivity(), SelectWorkerActivity.class);
            } else {
                JumpItent.jump(getActivity(), SelectWorkerActivity.class);
            }
        });
    }
}

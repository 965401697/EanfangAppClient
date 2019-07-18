package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.CompanyBean;
import com.eanfang.biz.model.security.HomeCompanyBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivitySelectCompanyBinding;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.adapter.FragmentHomeCompanyAdapter;

import java.util.ArrayList;

/**
 * @author guanluocang
 * @data 2019/7/17
 * @description 选择安防公司
 */

public class SelectCompanyActivity extends BaseActivity {

    private FragmentHomeCompanyAdapter adapter;
    private ActivitySelectCompanyBinding activitySelectCompanyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBindingUtil.setContentView(this, R.layout.activity_select_company);
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void initView() {
        adapter = new FragmentHomeCompanyAdapter();
        activitySelectCompanyBinding.rvCompany.setLayoutManager(new LinearLayoutManager(this));
        activitySelectCompanyBinding.rvCompany.addItemDecoration(new DividerItemDecoration(getContext()));
        adapter.bindToRecyclerView(activitySelectCompanyBinding.rvCompany);
    }

    private void initData() {
        EanfangHttp.post(NewApiService.HOME_COMPANY_LIST)
                .params("page", "1")
                .params("size", "2")
                .execute(new EanfangCallback<CompanyBean>(this, true, CompanyBean.class, bean -> {
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
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}

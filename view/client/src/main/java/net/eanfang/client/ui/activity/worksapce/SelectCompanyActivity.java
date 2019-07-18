package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
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

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author guanluocang
 * @data 2019/7/17
 * @description 选择安防公司
 */

public class SelectCompanyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private FragmentHomeCompanyAdapter adapter;
    private ActivitySelectCompanyBinding activitySelectCompanyBinding;

    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activitySelectCompanyBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_company);
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void initView() {
        setTitle("找安防公司");
        setLeftBack(true);
        adapter = new FragmentHomeCompanyAdapter();
        activitySelectCompanyBinding.rvCompany.setLayoutManager(new LinearLayoutManager(this));
        activitySelectCompanyBinding.rvCompany.addItemDecoration(new DividerItemDecoration(getContext()));
        adapter.bindToRecyclerView(activitySelectCompanyBinding.rvCompany);
    }

    private void initData() {
        EanfangHttp.post(NewApiService.HOME_COMPANY_LIST)
                .params("page", page)
                .params("size", 10)
                .execute(new EanfangCallback<CompanyBean>(this, true, CompanyBean.class) {
                    @Override
                    public void onSuccess(CompanyBean bean) {
                        if (page == 1) {
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
                            activitySelectCompanyBinding.swipeFresh.setRefreshing(false);
                            adapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                adapter.loadMoreEnd();
                                //释放对象
                            }
                        } else {
//                            adapter.addData(bean.getList());
                            adapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                adapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        activitySelectCompanyBinding.swipeFresh.setRefreshing(false);
                        adapter.loadMoreEnd();//没有数据了
                    }

                    @Override
                    public void onCommitAgain() {
                        activitySelectCompanyBinding.swipeFresh.setRefreshing(false);
                    }
                });
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        dataOption(BOTTOM_REFRESH);
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page = 1;
                initData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                initData();
                break;
            default:
                break;
        }
    }
}

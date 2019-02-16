package net.eanfang.worker.ui.fragment;

import android.view.View;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityHotBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.ui.adapter.HomeSecurityAdapter;

public class SecurityHotFragment extends TemplateItemListFragment {

    private String mTitle;
    private QueryEntry mQueryEntry;
    private HomeSecurityAdapter homeSecurityAdapter;

    public static SecurityHotFragment getInstance(String title) {
        SecurityHotFragment sf = new SecurityHotFragment();
        sf.mTitle = title;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

//    @Override
//    protected int setLayoutResouceId() {
//        return R.layout.fragment_security_hot;
//    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void initAdapter() {
        homeSecurityAdapter = new HomeSecurityAdapter(mRecyclerView, getActivity());
//        homeSecurityAdapter.bindToRecyclerView(mRecyclerView);
//        homeSecurityAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(homeSecurityAdapter);
    }

    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        EanfangHttp.post(NewApiService.SECURITY_HOT)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityHotBean>(getActivity(), true, SecurityHotBean.class) {

                    @Override
                    public void onSuccess(SecurityHotBean bean) {
                        if (mPage == 1) {
                            homeSecurityAdapter.getData().clear();
                            homeSecurityAdapter.setData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
//                            homeSecurityAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
//                                homeSecurityAdapter.loadMoreEnd();
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            homeSecurityAdapter.addMoreData(bean.getList());
//                            homeSecurityAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
//                                homeSecurityAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
//                        homeSecurityAdapter.loadMoreEnd();//没有数据了
                        if (homeSecurityAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

}

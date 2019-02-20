package net.eanfang.worker.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityFoucsListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.security.SecurityFocusListAdapter;
import net.eanfang.worker.ui.widget.DividerItemDecoration;

public class SecurituFoucsFragment extends TemplateItemListFragment {

    private String mTitle;

    private QueryEntry mQueryEntry;
    private SecurityFocusListAdapter securityFocusListAdapter;

    public static SecurituFoucsFragment getInstance(String title) {
        SecurituFoucsFragment sf = new SecurituFoucsFragment();
        sf.mTitle = title;
        return sf;
    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void initAdapter() {
        securityFocusListAdapter = new SecurityFocusListAdapter(getActivity());
        securityFocusListAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        securityFocusListAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    @Override
    protected void getData() {
        if (mQueryEntry == null)
            mQueryEntry = new QueryEntry();
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        mQueryEntry.getEquals().put("followUserId", EanfangApplication.get().getUserId() + "");
        EanfangHttp.post(NewApiService.SERCURITY_FOUCS)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityFoucsListBean>(getActivity(), true, SecurityFoucsListBean.class) {
                    @Override
                    public void onSuccess(SecurityFoucsListBean bean) {
                        if (mPage == 1) {
                            securityFocusListAdapter.getData().clear();
                            securityFocusListAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
//                            securityHotListAdapter.loadMoreComplete();
//                            if (bean.getList().size() < 10) {
//                                securityHotListAdapter.loadMoreEnd();
//                                mQueryEntry = null;
//                            }

//                            if (bean.getList().size() > 0) {
//                                mTvNoData.setVisibility(View.GONE);
//                            } else {
//                                mTvNoData.setVisibility(View.VISIBLE);
//                            }


                        } else {
//                            securityHotListAdapter.addData(bean.getList());
//                            securityHotListAdapter.loadMoreComplete();
//                            if (bean.getList().size() < 10) {
//                                securityHotListAdapter.loadMoreEnd();
//                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
//                        securityHotListAdapter.loadMoreEnd();//没有数据了
                        if (securityFocusListAdapter.getData().size() == 0) {
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

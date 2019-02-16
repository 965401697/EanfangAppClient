package net.eanfang.worker.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.HomeSecurityAdapter;
import net.eanfang.worker.ui.widget.DividerItemDecoration;

import org.json.JSONObject;

public class SecurituFoucsFragment extends TemplateItemListFragment {

    private String mTitle;

    private QueryEntry mQueryEntry;
    private HomeSecurityAdapter homeSecurityAdapter;

    public static SecurituFoucsFragment getInstance(String title) {
        SecurituFoucsFragment sf = new SecurituFoucsFragment();
        sf.mTitle = title;
        return sf;
    }

    public String getmTitle() {
        return mTitle;
    }

//    @Override
//    protected int setLayoutResouceId() {
//        return R.layout.fragment_securitu_foucs;
//    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void initAdapter() {
        homeSecurityAdapter = new HomeSecurityAdapter(mRecyclerView,getActivity());
//        homeSecurityAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//        homeSecurityAdapter.setOnLoadMoreListener(this);
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
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        if (mPage == 1) {
                            homeSecurityAdapter.getData().clear();
//                            homeSecurityAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
//                            homeSecurityAdapter.loadMoreComplete();
//                            if (bean.getList().size() < 10) {
//                                homeSecurityAdapter.loadMoreEnd();
//                                mQueryEntry = null;
//                            }

//                            if (bean.getList().size() > 0) {
//                                mTvNoData.setVisibility(View.GONE);
//                            } else {
//                                mTvNoData.setVisibility(View.VISIBLE);
//                            }


                        } else {
//                            homeSecurityAdapter.addData(bean.getList());
//                            homeSecurityAdapter.loadMoreComplete();
//                            if (bean.getList().size() < 10) {
//                                homeSecurityAdapter.loadMoreEnd();
//                            }
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

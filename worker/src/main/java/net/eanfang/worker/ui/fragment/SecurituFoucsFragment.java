package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityFoucsListBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityDetailActivity;
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
        securityFocusListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_isFocus:
                    doFoucus(securityFocusListAdapter.getData().get(position));
                    break;
                case R.id.ll_like:
                    doLike(securityFocusListAdapter.getData().get(position));
                    break;
                case R.id.ll_comments:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", securityFocusListAdapter.getData().get(position));
                    JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
                    break;
                case R.id.iv_share:
                    showToast("分享");
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 进行点赞
     */
    private void doLike(SecurityFoucsListBean.ListBean listBean) {
        SecurityLikeBean securityLikeBean = new SecurityLikeBean();
//        securityLikeBean.setAsId(listBean.getSpcId());
        securityLikeBean.setType("0");
        /**
         * 0 点赞 1 未点赞
         * */
//        if (listBean.getLikeStatus() == 0) {
//            securityLikeBean.setLikeStatus("1");
//        } else {
//            securityLikeBean.setLikeStatus("0");
//        }
        securityLikeBean.setLikeUserId(EanfangApplication.get().getUserId());
        securityLikeBean.setLikeCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityLikeBean.setLikeTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_LIKE)
                .upJson(JSONObject.toJSONString(securityLikeBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
                    mRecyclerView.scrollToPosition(0);
                    getData();
                }));
    }

    /**
     * 进行关注
     */
    private void doFoucus(SecurityFoucsListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

//        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
//        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
//        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
                    showToast("关注成功");
                    mRecyclerView.scrollToPosition(0);
                    getData();
                }));
    }

    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
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
                            securityFocusListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityFocusListAdapter.loadMoreEnd();
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            securityFocusListAdapter.addData(bean.getList());
                            securityFocusListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityFocusListAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        securityFocusListAdapter.loadMoreEnd();//没有数据了
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

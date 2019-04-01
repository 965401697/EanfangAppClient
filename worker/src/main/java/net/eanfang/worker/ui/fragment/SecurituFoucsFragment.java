package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.worker.ui.adapter.security.SecurityListAdapter;

public class SecurituFoucsFragment extends TemplateItemListFragment {

    private String mTitle;

    private QueryEntry mQueryEntry;
    private SecurityListAdapter securityListAdapter;

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
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void initAdapter() {
        securityListAdapter = new SecurityListAdapter(EanfangApplication.get().getApplicationContext());
        RecyclerView.RecycledViewPool pool = mRecyclerView.getRecycledViewPool();
        pool.setMaxRecycledViews(0, 10);
        mRecyclerView.setRecycledViewPool(pool);
        securityListAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        mRecyclerView.addItemDecoration(new BGASpaceItemDecoration(20));
        securityListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        securityListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_isFocus:
                    doFoucus(securityListAdapter.getData().get(position));
                    break;
                case R.id.ll_like:
                    doLike(securityListAdapter.getData().get(position));
                    break;
                case R.id.ll_comments:
                case R.id.iv_share:
                case R.id.ll_pic:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", securityListAdapter.getData().get(position));
                    bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
                    JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
                    break;
                default:
                    break;
            }
        });
        securityListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", securityListAdapter.getData().get(position));
            bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
            JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
        });
    }

    /**
     * 进行点赞
     */
    private void doLike(SecurityListBean.ListBean listBean) {
        SecurityLikeBean securityLikeBean = new SecurityLikeBean();
        securityLikeBean.setAsId(listBean.getSpcId());
        securityLikeBean.setType("0");
        /**
         *状态：0 点赞 1 未点赞
         * */
        if (listBean.getLikeStatus() == 0) {
            securityLikeBean.setLikeStatus("1");
        } else {
            securityLikeBean.setLikeStatus("0");
        }
        securityLikeBean.setLikeUserId(EanfangApplication.get().getUserId());
        securityLikeBean.setLikeCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityLikeBean.setLikeTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_LIKE)
                .upJson(JSONObject.toJSONString(securityLikeBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
//                    mRecyclerView.scrollToPosition(0);
                    getData();
                }));
    }

    /**
     * 取消关注
     */
    private void doFoucus(SecurityListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_DELETEFOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
                    showToast("已取消关注");
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
                .execute(new EanfangCallback<SecurityListBean>(getActivity(), true, SecurityListBean.class) {
                    @Override
                    public void onSuccess(SecurityListBean bean) {
                        if (mPage == 1) {
                            securityListAdapter.getData().clear();
                            securityListAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            securityListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityListAdapter.loadMoreEnd();
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            securityListAdapter.addData(bean.getList());
                            securityListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityListAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        securityListAdapter.loadMoreEnd();//没有数据了
                        if (securityListAdapter.getData().size() == 0) {
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

    /**
     * 刷新已读未读的状态
     */
    public void refreshStatus() {
        getData();
    }

}

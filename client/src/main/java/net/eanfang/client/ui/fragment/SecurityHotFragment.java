package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityHotListBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.client.ui.adapter.security.SecurityHotListAdapter;

import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;

public class SecurityHotFragment extends TemplateItemListFragment {

    private String mTitle;
    private QueryEntry mQueryEntry;
    private SecurityHotListAdapter securityHotListAdapter;

    public static SecurityHotFragment getInstance(String title) {
        SecurityHotFragment sf = new SecurityHotFragment();
        sf.mTitle = title;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void setListener() {
        securityHotListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_isFocus:
                    doFoucus(securityHotListAdapter.getData().get(position));
                    break;
                case R.id.ll_like:
                    doLike(securityHotListAdapter.getData().get(position));
                    break;
                case R.id.ll_comments:
                case R.id.ll_pic:
                case R.id.iv_share:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", securityHotListAdapter.getData().get(position));
                    bundle.putInt("friend", securityHotListAdapter.getData().get(position).getFriend());
                    bundle.putString("type", "hot");
                    JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
                    break;
//                case R.id.iv_share:
//                    showToast("分享");
//                    break;
                default:
                    break;
            }
        });
        securityHotListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", securityHotListAdapter.getData().get(position));
            bundle.putInt("friend", securityHotListAdapter.getData().get(position).getFriend());
            bundle.putString("type", "hot");
            JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
        });
    }

    @Override
    protected void initAdapter() {
        securityHotListAdapter = new SecurityHotListAdapter(getActivity());
        securityHotListAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        securityHotListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.addOnScrollListener(new BGARVOnScrollListener(getActivity()));

    }

    /**
     * 进行点赞
     */
    private void doLike(SecurityHotListBean.ListBean listBean) {
        SecurityLikeBean securityLikeBean = new SecurityLikeBean();
        securityLikeBean.setAsId(listBean.getSpcId());
        securityLikeBean.setType("0");
        /**
         * 状态：0 点赞 1 未点赞
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
     * 进行关注
     */
    private void doFoucus(SecurityHotListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
                    showToast("关注成功");
//                    mRecyclerView.scrollToPosition(0);
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
        EanfangHttp.post(NewApiService.SECURITY_HOT)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityHotListBean>(getActivity(), true, SecurityHotListBean.class) {

                    @Override
                    public void onSuccess(SecurityHotListBean bean) {
                        if (mPage == 1) {
                            securityHotListAdapter.getData().clear();
                            securityHotListAdapter.setNewData(bean.getList());
                            securityHotListAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                            securityHotListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityHotListAdapter.loadMoreEnd();
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            securityHotListAdapter.addData(bean.getList());
                            securityHotListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityHotListAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        securityHotListAdapter.loadMoreEnd();//没有数据了
                        if (securityHotListAdapter.getData().size() == 0) {
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

package net.eanfang.client.ui.fragment.security;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.security.SecurityCommentListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.client.ui.adapter.security.SecurityCommentListAdapter;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;


/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈评论 我收到的评论
 */

public class SecurityReceiveFragment extends TemplateItemListFragment {
    private String mTitle;
    private QueryEntry mQueryEntry;
    private SecurityCommentListAdapter securityCommentListAdapter;

    public static SecurityReceiveFragment getInstance(String title) {
        SecurityReceiveFragment sf = new SecurityReceiveFragment();
        sf.mTitle = title;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        securityCommentListAdapter = new SecurityCommentListAdapter(true);
        securityCommentListAdapter.bindToRecyclerView(mRecyclerView);
        securityCommentListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        securityCommentListAdapter.setOnItemClickListener((adapter, view, position) -> {
            doJump(position, false);
        });
    }

    public void doJump(int position, boolean isCommon) {
        Bundle bundle = new Bundle();
        bundle.putLong("spcId", securityCommentListAdapter.getData().get(position).getSpcId());
        bundle.putBoolean("isCommon", isCommon);
        JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

    @Override
    protected void getData() {
        mQueryEntry = new QueryEntry();
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        //我发出的评论
        mQueryEntry.getEquals().put("asAccId", EanfangApplication.get().getAccId() + "");
        EanfangHttp.post(NewApiService.SERCURITY_COMMENT_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityCommentListBean>(getActivity(), true, SecurityCommentListBean.class) {
                    @Override
                    public void onSuccess(SecurityCommentListBean bean) {
                        if (mPage == 1) {
                            securityCommentListAdapter.getData().clear();
                            securityCommentListAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            securityCommentListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityCommentListAdapter.loadMoreEnd();
                                securityCommentListAdapter.removeAllFooterView();
                            }
                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            securityCommentListAdapter.addData(bean.getList());
                            securityCommentListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityCommentListAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        securityCommentListAdapter.loadMoreEnd();//没有数据了
                        if (securityCommentListAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        mQueryEntry = null;
        mPage = 1;
        getData();
    }
}

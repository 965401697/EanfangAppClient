package net.eanfang.worker.ui.fragment.security;

import android.os.Bundle;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.bean.security.SecurityCommentListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.worker.ui.adapter.security.SecurityCommentListAdapter;
import net.eanfang.worker.ui.fragment.TemplateItemListFragment;

/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈发出的评论
 */

public class SecuritySendFragment extends TemplateItemListFragment {
    private String mTitle;
    private QueryEntry mQueryEntry;
    private SecurityCommentListAdapter securityCommentListAdapter;

    public static SecuritySendFragment getInstance(String title) {
        SecuritySendFragment sf = new SecuritySendFragment();
        sf.mTitle = title;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected void initAdapter() {
        securityCommentListAdapter = new SecurityCommentListAdapter(false);
        securityCommentListAdapter.bindToRecyclerView(mRecyclerView);
        securityCommentListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        securityCommentListAdapter.setOnItemClickListener((adapter, view, position) -> {
            doJump(position, false);
        });
    }

    public void doJump(int position, boolean isCommon) {
        Bundle bundle = new Bundle();
        bundle.putLong("spcId", securityCommentListAdapter.getData().get(position).getAskSpCircleEntity().getSpcId());
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
        mQueryEntry.getEquals().put("commentsAnswerAccId", WorkerApplication.get().getAccId()+ "");
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

    @Override
    protected ViewModel initViewModel() {
        return null;
    }
}

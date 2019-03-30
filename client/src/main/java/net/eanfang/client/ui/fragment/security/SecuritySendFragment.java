package net.eanfang.client.ui.fragment.security;

import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityCommentListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.ui.adapter.security.SecurityCommentListAdapter;
import net.eanfang.client.ui.fragment.TemplateItemListFragment;


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
        securityCommentListAdapter = new SecurityCommentListAdapter();
        securityCommentListAdapter.bindToRecyclerView(mRecyclerView);
        securityCommentListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        securityCommentListAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
    }

    @Override
    protected void getData() {
        mQueryEntry = new QueryEntry();
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        //我发出的评论
        mQueryEntry.getEquals().put("commentsAnswerId", EanfangApplication.get().getUserId() + "");
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
}

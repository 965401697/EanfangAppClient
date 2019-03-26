package net.eanfang.worker.ui.activity.worksapce.security;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityHotListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.security.SecurityHotListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/3/26
 * @description 安防圈点赞列表
 */

public class SecurityPersonalPublicListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_security)
    RecyclerView rvSecurity;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout swipreFresh;

    private QueryEntry queryEntry;
    private int mPage = 1;
    private SecurityHotListAdapter securityHotListAdapter;

    private String mType = "";
    String mLike = "like";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_like_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        mType = getIntent().getStringExtra("type");
        setLeftBack();
        /**
         * commnet 评论列表 about 艾特
         * */
        if (mLike.equals(mType)) {
            setTitle("点赞");
        } else {
            setTitle("@我的");
        }
        securityHotListAdapter = new SecurityHotListAdapter(SecurityPersonalPublicListActivity.this);
        securityHotListAdapter.bindToRecyclerView(rvSecurity);

        rvSecurity.setLayoutManager(new LinearLayoutManager(this));
        swipreFresh.setOnRefreshListener(this);

        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
        securityHotListAdapter.setOnLoadMoreListener(this, rvSecurity);

        securityHotListAdapter.setOnItemClickListener((adapter, view, position) -> {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("bean", securityHotListAdapter.getData().get(position));
//            bundle.putInt("friend", securityHotListAdapter.getData().get(position).getFriend());
//            bundle.putString("type", "hot");
//            JumpItent.jump(SecurityPersonalPublicListActivity.this, SecurityDetailActivity.class, bundle);
        });
    }

    private void initData() {
        if (queryEntry == null) {
            queryEntry = new QueryEntry();
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        String mUrl;
        if (mLike.equals(mType)) {
            mUrl = NewApiService.SERCURITY_LIKE_LIST;
            queryEntry.getEquals().put("likeUserId", EanfangApplication.get().getUserId() + "");
        } else {
            mUrl = NewApiService.SERCURITY_ABOUT_LIST;
            queryEntry.getLike().put("atUserId", EanfangApplication.get().getUserId() + "");
        }

        EanfangHttp.post(mUrl)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SecurityHotListBean>(SecurityPersonalPublicListActivity.this, true, SecurityHotListBean.class) {
                    @Override
                    public void onSuccess(SecurityHotListBean bean) {
                        if (mPage == 1) {

                            securityHotListAdapter.getData().clear();
                            securityHotListAdapter.setNewData(bean.getList());
                            securityHotListAdapter.disableLoadMoreIfNotFullPage(rvSecurity);

                            swipreFresh.setRefreshing(false);
                            securityHotListAdapter.loadMoreComplete();

                            if (bean.getList().size() < 10) {
                                securityHotListAdapter.loadMoreEnd();
                                queryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                tvNoDatas.setVisibility(View.GONE);
                            } else {
                                tvNoDatas.setVisibility(View.VISIBLE);
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
                        swipreFresh.setRefreshing(false);
                        securityHotListAdapter.loadMoreEnd();//没有数据了
                        if (securityHotListAdapter.getData().size() == 0) {
                            tvNoDatas.setVisibility(View.VISIBLE);
                        } else {
                            tvNoDatas.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCommitAgain() {
                        swipreFresh.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        //下拉永远第一页
        mPage = 1;
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        initData();
    }
}

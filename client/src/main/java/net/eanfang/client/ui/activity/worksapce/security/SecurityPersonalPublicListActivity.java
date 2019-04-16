package net.eanfang.client.ui.activity.worksapce.security;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.worker.ui.adapter.security.SecurityListAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    private SecurityListAdapter securityListAdapter;

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
        securityListAdapter = new SecurityListAdapter(SecurityPersonalPublicListActivity.this);
        securityListAdapter.bindToRecyclerView(rvSecurity);

        rvSecurity.setLayoutManager(new LinearLayoutManager(this));
        swipreFresh.setOnRefreshListener(this);

        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
        securityListAdapter.setOnLoadMoreListener(this, rvSecurity);

        securityListAdapter.setOnItemClickListener((adapter, view, position) -> {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("bean", securityListAdapter.getData().get(position));
//            bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
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
                .execute(new EanfangCallback<SecurityListBean>(SecurityPersonalPublicListActivity.this, true, SecurityListBean.class) {
                    @Override
                    public void onSuccess(SecurityListBean bean) {
                        if (mPage == 1) {

                            securityListAdapter.getData().clear();
                            securityListAdapter.setNewData(bean.getList());
                            securityListAdapter.disableLoadMoreIfNotFullPage(rvSecurity);

                            swipreFresh.setRefreshing(false);
                            securityListAdapter.loadMoreComplete();

                            if (bean.getList().size() < 10) {
                                securityListAdapter.loadMoreEnd();
                                queryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                tvNoDatas.setVisibility(View.GONE);
                            } else {
                                tvNoDatas.setVisibility(View.VISIBLE);
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
                        swipreFresh.setRefreshing(false);
                        securityListAdapter.loadMoreEnd();//没有数据了
                        if (securityListAdapter.getData().size() == 0) {
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

package net.eanfang.worker.ui.activity.worksapce.security;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityPersonalBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.security.SecurityListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/3/20
 * @description 安防圈个人中心
 */

public class SecurityPersonalActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.iv_head)
    SimpleDraweeView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_certifi)
    ImageView ivCertifi;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.tv_focus_count)
    TextView tvFocusCount;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_aboutme)
    TextView tvAboutme;
    @BindView(R.id.tv_allstatae)
    TextView tvAllstatae;
    @BindView(R.id.rv_security)
    RecyclerView rvSecurity;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout swipreFresh;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private QueryEntry queryEntry;
    private int mPage = 1;
    private SecurityListAdapter securityListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_personal);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setLeftBack();
        setTitle("我的安防圈");
        securityListAdapter = new SecurityListAdapter(SecurityPersonalActivity.this);
        securityListAdapter.bindToRecyclerView(rvSecurity);

        rvSecurity.setLayoutManager(new LinearLayoutManager(this));
        swipreFresh.setOnRefreshListener(this);

        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
        securityListAdapter.setOnLoadMoreListener(this, rvSecurity);

        ivHead.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + EanfangApplication.get().getUser().getAccount().getAvatar())));
        tvName.setText(EanfangApplication.getApplication().getUser().getAccount().getNickName());
        rvSecurity.setNestedScrollingEnabled(false);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    loadMore();
                }
            }
        });

        securityListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean", securityListAdapter.getData().get(position));
            bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
            JumpItent.jump(SecurityPersonalActivity.this, SecurityDetailActivity.class, bundle);
        });

    }

    private void loadMore() {
        mPage++;
        initData();
    }

    private void initData() {
        if (queryEntry == null) {
            queryEntry = new QueryEntry();
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);
        queryEntry.getEquals().put("publisherUserId", EanfangApplication.get().getUserId() + "");
        EanfangHttp.post(NewApiService.SERCURITY_PERSONAL)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SecurityPersonalBean>(SecurityPersonalActivity.this, true, SecurityPersonalBean.class) {
                    @Override
                    public void onSuccess(SecurityPersonalBean bean) {
                        if (mPage == 1) {
                            //粉丝数
                            tvFansCount.setText(bean.getAsFollowerCount() + "");
                            //关注数
                            tvFocusCount.setText(bean.getFollowerCount() + "");
                            //点赞数量数
                            tvLikeCount.setText(bean.getLikeCount() + "");
                            //全部动态数
                            tvAllstatae.setText(bean.getSpccount() + "");

                            securityListAdapter.getData().clear();
                            securityListAdapter.setNewData(bean.getPageUtil().getList());
                            securityListAdapter.disableLoadMoreIfNotFullPage(rvSecurity);

                            swipreFresh.setRefreshing(false);
                            securityListAdapter.loadMoreComplete();

                            if (bean.getPageUtil().getList().size() < 10) {
                                securityListAdapter.loadMoreEnd();
                                queryEntry = null;
                            }

                            if (bean.getPageUtil().getList().size() > 0) {
                                tvNoDatas.setVisibility(View.GONE);
                            } else {
                                tvNoDatas.setVisibility(View.VISIBLE);
                            }

                        } else {
                            securityListAdapter.addData(bean.getPageUtil().getList());
                            securityListAdapter.loadMoreComplete();
                            if (bean.getPageUtil().getList().size() < 10) {
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


    @OnClick({R.id.tv_comment, R.id.tv_like, R.id.tv_aboutme})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 评论列表
            case R.id.tv_comment:
                JumpItent.jump(SecurityPersonalActivity.this, SecurityCommentListActivity.class);
                break;
            // 点赞列表
            case R.id.tv_like:
                Bundle bundle_like = new Bundle();
                bundle_like.putString("type", "like");
                JumpItent.jump(SecurityPersonalActivity.this, SecurityPersonalPublicListActivity.class, bundle_like);
                break;
            case R.id.tv_aboutme:
                Bundle bundle_about = new Bundle();
                bundle_about.putString("type", "about");
                JumpItent.jump(SecurityPersonalActivity.this, SecurityPersonalPublicListActivity.class, bundle_about);
                break;
            default:
                break;
        }
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        //下拉永远第一页
        mPage = 1;
        initData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        initData();
    }
}

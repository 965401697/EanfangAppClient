package net.eanfang.client.ui.activity.worksapce.security;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.model.security.SecurityPersonalTopBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.facebook.drawee.view.SimpleDraweeView;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.online.FaultExplainActivity;
import net.eanfang.client.ui.adapter.security.SecurityListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

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
    @BindView(R.id.ll_securitypersonal)
    LinearLayout llSecuritypersonal;
    @BindView(R.id.tv_secuirtypersonal)
    TextView tvSecuirtypersonal;

    private QueryEntry queryEntry;
    private int mPage = 1;
    private SecurityListAdapter securityListAdapter;


    private QBadgeView qBadgeViewComment = new QBadgeView(EanfangApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewAbout = new QBadgeView(EanfangApplication.get().getApplicationContext());

    /**
     * 点击头像进入查看他人个人中心
     */
    private boolean isLookOther = false;
    private Long mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_personal);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        initData();
        initPersonalData();
    }


    private void initView() {
        setLeftBack();
        isLookOther = getIntent().getBooleanExtra("isLookOther", false);
        mUserId = getIntent().getLongExtra("mUserId", 0);
        if (isLookOther) {
            setTitle("用户主页");
            llSecuritypersonal.setVisibility(View.GONE);
            tvSecuirtypersonal.setText("用户动态");
        } else {
            setTitle("我的安防圈");
            llSecuritypersonal.setVisibility(View.VISIBLE);
            tvSecuirtypersonal.setText("我的动态");
        }
        securityListAdapter = new SecurityListAdapter(EanfangApplication.get().getApplicationContext(), false);
        securityListAdapter.bindToRecyclerView(rvSecurity);

        rvSecurity.setLayoutManager(new LinearLayoutManager(this));
        swipreFresh.setOnRefreshListener(this);

        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
        securityListAdapter.setOnLoadMoreListener(this, rvSecurity);

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
            doJump(position, false);
        });
        securityListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_comments:
                    doJump(position, true);
                    break;
                case R.id.tv_isFocus:
                case R.id.ll_like:
                case R.id.ll_pic:
                case R.id.iv_share:
                case R.id.ll_question:
                case R.id.rl_video:
                    doJump(position, false);
                    break;
                default:
                    break;
            }
        });


        qBadgeViewComment.bindTarget(findViewById(R.id.tv_comment))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(3, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(15, 0, true)
                .setBadgeTextSize(11, true);

        qBadgeViewAbout.bindTarget(findViewById(R.id.tv_aboutme))
                .setBadgeBackgroundColor(0xFFFF0000)
                .setBadgePadding(3, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setGravityOffset(15, 0, true)
                .setBadgeTextSize(11, true);

    }

    public void doJump(int position, boolean isCommon) {
        //专家问答
        if (securityListAdapter.getData().get(position).getType() == 1) {
            Bundle bundle_question = new Bundle();
            bundle_question.putInt("QuestionIdZ", Integer.parseInt(securityListAdapter.getData().get(position).getQuestionId()));
            JumpItent.jump(SecurityPersonalActivity.this, FaultExplainActivity.class, bundle_question);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("spcId", securityListAdapter.getData().get(position).getSpcId());
            bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
            bundle.putBoolean("isCommon", isCommon);
            JumpItent.jump(SecurityPersonalActivity.this, SecurityDetailActivity.class, bundle);
        }
    }

    private void loadMore() {
        mPage++;
        initData();
    }

    private void initPersonalData() {
        String url = null;
        if (queryEntry == null) {
            queryEntry = new QueryEntry();
        }
        if (isLookOther) {
            queryEntry.getEquals().put("publisherUserId", mUserId + "");
            url = NewApiService.SERCURITY_PERSONAL_OTHER_TOP;
        } else {
            queryEntry.getEquals().put("publisherUserId", EanfangApplication.get().getUserId() + "");
            url = NewApiService.SERCURITY_PERSONAL_TOP;
        }
        EanfangHttp.post(url)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SecurityPersonalTopBean>(SecurityPersonalActivity.this, true, SecurityPersonalTopBean.class, bean -> {
                    ivHead.setImageURI((Uri.parse(BuildConfig.OSS_SERVER + bean.getUserEntity().getAccountEntity().getAvatar())));
                    tvName.setText(bean.getUserEntity().getAccountEntity().getRealName());
                    //粉丝数
                    tvFansCount.setText(bean.getAsFollowerCount() + "");
                    //关注数
                    tvFocusCount.setText(bean.getFollowerCount() + "");
                    //点赞数量数
                    tvLikeCount.setText(bean.getLikeCount() + "");
                    //全部动态数
                    tvAllstatae.setText(bean.getSpccount() + "");
                    //评论未读
                    qBadgeViewComment.setBadgeNumber(bean.getCommentNoRead());
                    //艾特我未读
                    qBadgeViewAbout.setBadgeNumber(bean.getNoReadCount());
                }));

    }

    private void initData() {
        String url = null;
        if (queryEntry == null) {
            queryEntry = new QueryEntry();
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);
        if (isLookOther) {
            queryEntry.getEquals().put("publisherUserId", mUserId + "");
            url = NewApiService.SERCURITY_PERSONAL_OTHER;
        } else {
            queryEntry.getEquals().put("publisherUserId", EanfangApplication.get().getUserId() + "");
            url = NewApiService.SERCURITY_PERSONAL;
        }

        EanfangHttp.post(url)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SecurityListBean>(SecurityPersonalActivity.this, true, SecurityListBean.class) {
                    @Override
                    public void onSuccess(SecurityListBean bean) {
                        if (mPage == 1) {
                            securityListAdapter.getData().clear();
                            securityListAdapter.setNewData(bean.getList());
                            securityListAdapter.disableLoadMoreIfNotFullPage(rvSecurity);
                            securityListAdapter.notifyDataSetChanged();
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


    @OnClick({R.id.tv_comment, R.id.tv_like, R.id.tv_aboutme, R.id.ll_foucs})
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
            // 艾特我的列表
            case R.id.tv_aboutme:
                Bundle bundle_about = new Bundle();
                bundle_about.putString("type", "about");
                JumpItent.jump(SecurityPersonalActivity.this, SecurityPersonalPublicListActivity.class, bundle_about);
                break;
            case R.id.ll_foucs:
                if (!isLookOther) {
                    Bundle bundle_foucus = new Bundle();
                    bundle_foucus.putString("type", "foucs");
                    JumpItent.jump(SecurityPersonalActivity.this, SecurityPersonalPublicListActivity.class, bundle_foucus);
                }
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

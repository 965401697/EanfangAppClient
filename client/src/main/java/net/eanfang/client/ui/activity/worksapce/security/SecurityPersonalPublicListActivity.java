package net.eanfang.client.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityFoucsListBean;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.online.FaultExplainActivity;
import net.eanfang.client.ui.adapter.security.SecurityFoucsListAdapter;
import net.eanfang.client.ui.adapter.security.SecurityListAdapter;

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
    private SecurityFoucsListAdapter securityFoucsListAdapter;

    private String mType = "";
    String mLike = "like";
    String mAbout = "about";
    String mFoucs = "foucs";

    /**
     * 关注和取关
     */
    private boolean isFoucs = true;
    private SecurityFoucsListBean.ListBean securityFoucsListBean;

    /**
     * 创建安防圈 艾特人
     */
    private boolean isCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_like_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mType = getIntent().getStringExtra("type");
        isCreate = getIntent().getBooleanExtra("create", false);
        /**
         * commnet 评论列表 about 艾特 foucs 关注的人
         * */
        if (mLike.equals(mType)) {
            setTitle("点赞");
            initLikeAndAboutAdapter();
            initData();
        } else if (mAbout.equals(mType)) {
            setTitle("@我的");
            initLikeAndAboutAdapter();
            initData();
        } else if (mFoucs.equals(mType)) {
            setTitle("我关注的人");
            initFoucsAdapter();
            initFoucsData();
        }
    }

    private void initView() {
        setLeftBack();
        rvSecurity.setLayoutManager(new LinearLayoutManager(this));
        swipreFresh.setOnRefreshListener(this);
        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
    }

    /***
     * 点赞、我的
     * */
    public void initLikeAndAboutAdapter() {
        /**
         * 点赞不需要已读未读
         * */
        if (mLike.equals(mType)) {
            securityListAdapter = new SecurityListAdapter(EanfangApplication.get().getApplicationContext(), false);
        } else {
            securityListAdapter = new SecurityListAdapter(EanfangApplication.get().getApplicationContext(), true);
        }
        securityListAdapter.bindToRecyclerView(rvSecurity);
        securityListAdapter.setOnLoadMoreListener(this, rvSecurity);
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
    }

    public void doJump(int position, boolean isCommon) {
        //专家问答
        if (securityListAdapter.getData().get(position).getType() == 1) {
            Bundle bundle_question = new Bundle();
            bundle_question.putInt("QuestionIdZ", Integer.parseInt(securityListAdapter.getData().get(position).getQuestionId()));
            JumpItent.jump(SecurityPersonalPublicListActivity.this, FaultExplainActivity.class, bundle_question);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("spcId", securityListAdapter.getData().get(position).getSpcId());
            bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
            bundle.putBoolean("isCommon", isCommon);
            JumpItent.jump(SecurityPersonalPublicListActivity.this, SecurityDetailActivity.class, bundle);
        }
    }

    /**
     * 关注人的列表
     */
    public void initFoucsAdapter() {
        securityFoucsListAdapter = new SecurityFoucsListAdapter(EanfangApplication.getApplication().getApplicationContext(), isCreate);
        securityFoucsListAdapter.bindToRecyclerView(rvSecurity);
        securityFoucsListAdapter.setOnLoadMoreListener(this, rvSecurity);
        securityFoucsListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_isFocus) {
                securityFoucsListBean = (SecurityFoucsListBean.ListBean) adapter.getData().get(position);
                doFoucsOrUnFoucs(securityFoucsListBean, position);
            }
        });
        securityFoucsListAdapter.setOnItemClickListener((adapter, view, position) -> {
            securityFoucsListBean = (SecurityFoucsListBean.ListBean) adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra("foucsAccountEntity", securityFoucsListBean.getUserEntity());
            setResult(RESULT_OK, intent);
            finishSelf();
        });
    }

    /**
     * 关注和取关
     */
    private void doFoucsOrUnFoucs(SecurityFoucsListBean.ListBean listBean, int position) {
        if (isFoucs) {
            doUnFoucs(listBean, position);
        } else {
            doFoucs(listBean, position);
        }
    }

    private void doUnFoucs(SecurityFoucsListBean.ListBean listBean, int position) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getAskSpCircleEntity().getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getAskSpCircleEntity().getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getAskSpCircleEntity().getPublisherTopCompanyId());
        securityFoucsBean.setAsAccId(listBean.getUserEntity().getAccountEntity().getAccId());
        securityFoucsBean.setFollowAccId(EanfangApplication.get().getAccId());
        EanfangHttp.post(NewApiService.SERCURITY_DELETEFOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    showToast("已取消关注");
                    listBean.setFollowsStatus(1);
                    securityFoucsListAdapter.getData().set(position, listBean);
                    securityFoucsListAdapter.notifyItemChanged(position);
                    isFoucs = false;
                }));
    }

    private void doFoucs(SecurityFoucsListBean.ListBean listBean, int position) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getAskSpCircleEntity().getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getAskSpCircleEntity().getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getAskSpCircleEntity().getPublisherTopCompanyId());

        securityFoucsBean.setAsAccId(listBean.getUserEntity().getAccountEntity().getAccId());
        securityFoucsBean.setFollowAccId(EanfangApplication.get().getAccId());
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    showToast("关注成功");
                    listBean.setFollowsStatus(0);
                    securityFoucsListAdapter.getData().set(position, listBean);
                    securityFoucsListAdapter.notifyItemChanged(position);
                    isFoucs = true;
                }));
    }

    private void initData() {
        if (queryEntry == null) {
            queryEntry = new QueryEntry();
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        String mUrl = null;
        if (mLike.equals(mType)) {
            mUrl = NewApiService.SERCURITY_LIKE_LIST;
            queryEntry.getEquals().put("likeUserId", EanfangApplication.get().getUserId() + "");
        } else if (mAbout.equals(mType)) {
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
                                rvSecurity.setVisibility(View.VISIBLE);
                            } else {
                                tvNoDatas.setVisibility(View.VISIBLE);
                                rvSecurity.setVisibility(View.GONE);
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

    private void initFoucsData() {
        if (queryEntry == null) {
            queryEntry = new QueryEntry();
        }
        queryEntry.setPage(mPage);
        queryEntry.setSize(10);

        String mUrl = null;
        mUrl = NewApiService.SERCURITY_FOUCS_LIST;
        queryEntry.getEquals().put("followUserId", EanfangApplication.get().getUserId() + "");
        EanfangHttp.post(mUrl)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SecurityFoucsListBean>(SecurityPersonalPublicListActivity.this, true, SecurityFoucsListBean.class) {
                    @Override
                    public void onSuccess(SecurityFoucsListBean bean) {
                        if (mPage == 1) {
                            securityFoucsListAdapter.getData().clear();
                            securityFoucsListAdapter.setNewData(bean.getList());
                            securityFoucsListAdapter.disableLoadMoreIfNotFullPage(rvSecurity);

                            swipreFresh.setRefreshing(false);
                            securityFoucsListAdapter.loadMoreComplete();

                            if (bean.getList().size() < 10) {
                                securityFoucsListAdapter.loadMoreEnd();
                                queryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                tvNoDatas.setVisibility(View.GONE);
                                rvSecurity.setVisibility(View.VISIBLE);
                            } else {
                                tvNoDatas.setVisibility(View.VISIBLE);
                                rvSecurity.setVisibility(View.GONE);
                            }

                        } else {
                            securityFoucsListAdapter.addData(bean.getList());
                            securityFoucsListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityFoucsListAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        swipreFresh.setRefreshing(false);
                        securityFoucsListAdapter.loadMoreEnd();//没有数据了
                        if (securityFoucsListAdapter.getData().size() == 0) {
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
        if (mLike.equals(mType) || mAbout.equals(mType)) {
            initData();
        } else if (mFoucs.equals(mType)) {
            initFoucsData();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        if (mLike.equals(mType) || mAbout.equals(mType)) {
            initData();
        } else if (mFoucs.equals(mType)) {
            initFoucsData();
        }
    }
}

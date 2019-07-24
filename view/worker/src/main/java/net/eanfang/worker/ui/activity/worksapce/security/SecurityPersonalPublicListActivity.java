package net.eanfang.worker.ui.activity.worksapce.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.security.SecurityFoucsBean;
import com.eanfang.biz.model.security.SecurityFoucsListBean;
import com.eanfang.biz.model.security.SecurityListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.im.SelectIMContactActivity;
import net.eanfang.worker.ui.activity.worksapce.online.FaultExplainActivity;
import net.eanfang.worker.ui.adapter.security.SecurityFoucsListAdapter;
import net.eanfang.worker.ui.adapter.security.SecurityListAdapter;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2019/3/26
 * @description 安防圈点赞列表
 */

public class SecurityPersonalPublicListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int REFRESH_ITEM = 1011;
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
    private SecurityListBean.ListBean securityDetailBean;
    private int mPosition;
    private ArrayList<String> picList = new ArrayList<>();
    private String[] pics = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_security_like_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        setLeftBack();
        mType = getIntent().getStringExtra("type");
        isCreate = getIntent().getBooleanExtra("create", false);
        rvSecurity.setLayoutManager(new LinearLayoutManager(this));
        swipreFresh.setOnRefreshListener(this);
        rvSecurity.addItemDecoration(new BGASpaceItemDecoration(20));
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

    /***
     * 点赞、我的
     * */
    public void initLikeAndAboutAdapter() {
        /**
         * 点赞不需要已读未读
         * */
        if (mLike.equals(mType)) {
            securityListAdapter = new SecurityListAdapter( false);
        } else {
            securityListAdapter = new SecurityListAdapter(true);
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
                case R.id.ll_share:
                    doShare(securityListAdapter.getData().get(position));
                    break;
                case R.id.ll_pic:
                    picList.clear();
                    pics = securityListAdapter.getData().get(position).getSpcImg().split(",");
                    picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> BuildConfig.OSS_SERVER + (url)).toList());
                    ImagePerviewUtil.perviewImage(SecurityPersonalPublicListActivity.this, picList);
                    break;
                case R.id.tv_isFocus:
                case R.id.ll_like:
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
            bundle.putBoolean("isCommon", isCommon);
            mPosition = position;
            securityDetailBean = securityListAdapter.getData().get(position);
            JumpItent.jump(SecurityPersonalPublicListActivity.this, SecurityDetailActivity.class, bundle, REFRESH_ITEM);
        }
    }

    /**
     * 关注人的列表
     */
    public void initFoucsAdapter() {
        securityFoucsListAdapter = new SecurityFoucsListAdapter( isCreate);
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
        doUnFoucs(listBean, position);
    }

    private void doUnFoucs(SecurityFoucsListBean.ListBean listBean, int position) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(WorkerApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getAskSpCircleEntity().getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getAskSpCircleEntity().getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getAskSpCircleEntity().getPublisherTopCompanyId());
        securityFoucsBean.setAsAccId(listBean.getUserEntity().getAccountEntity().getAccId());
        securityFoucsBean.setFollowAccId(WorkerApplication.get().getAccId());
        /**
         * 状态：0 关注 1 未关注
         * */
        securityFoucsBean.setFollowsStatus(listBean.getFollowsStatus() == 0 ? 1 : 0);
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, bean -> {
                    if (isFoucs) {
                        showToast("已取消关注");
                        listBean.setFollowsStatus(1);
                        securityFoucsListAdapter.getData().set(position, listBean);
                        securityFoucsListAdapter.notifyItemChanged(position);
                        isFoucs = false;
                    } else {
                        showToast("关注成功");
                        listBean.setFollowsStatus(0);
                        securityFoucsListAdapter.getData().set(position, listBean);
                        securityFoucsListAdapter.notifyItemChanged(position);
                        isFoucs = true;
                    }

                }));
    }

    /**
     * 分享 分享到好友
     */
    private void doShare(SecurityListBean.ListBean listBean) {
        //分享聊天
        if (listBean != null) {
            Intent intent = new Intent(SecurityPersonalPublicListActivity.this, SelectIMContactActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("id", String.valueOf(listBean.getSpcId()));
            bundle.putString("orderNum", listBean.getPublisherOrg().getOrgName());
            if (!StringUtils.isEmpty(listBean.getSpcImg())) {
                bundle.putString("picUrl", listBean.getSpcImg().split(",")[0]);
            }
            bundle.putString("creatTime", listBean.getSpcContent());
            bundle.putString("workerName", listBean.getAccountEntity().getRealName());
            bundle.putString("status", String.valueOf(listBean.getFollowsStatus()));
            bundle.putString("shareType", "8");
            intent.putExtras(bundle);
            startActivity(intent);
        }
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
            queryEntry.getEquals().put("likeAccId", WorkerApplication.get().getAccId() + "");
        } else if (mAbout.equals(mType)) {
            mUrl = NewApiService.SERCURITY_ABOUT_LIST;
            queryEntry.getLike().put("atUserId", WorkerApplication.get().getAccId() + "");
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
        queryEntry.getEquals().put("followAccId", WorkerApplication.get().getAccId() + "");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REFRESH_ITEM) {
            refreshItemStatus(data);
        }
    }

    /**
     * 刷新点赞 关注状态
     */

    public void refreshItemStatus(Intent intentData) {
        if (securityDetailBean != null && intentData != null) {
            SecurityListBean.ListBean mSecurityDetailBean = (SecurityListBean.ListBean) intentData.getSerializableExtra("itemStatus");
            if (intentData.getBooleanExtra("isLikeEdit", false)) {
                securityDetailBean.setLikeStatus(mSecurityDetailBean.getLikeStatus());
                securityDetailBean.setLikesCount(mSecurityDetailBean.getLikesCount());
                securityListAdapter.getData().remove(mPosition);
            }
            if (intentData.getBooleanExtra("isFoucsEdit", false)) {
                for (int i = 0; i < securityListAdapter.getData().size(); i++) {
                    if (securityListAdapter.getData().get(i).getAccountEntity().getAccId().equals(mSecurityDetailBean.getAccountEntity().getAccId())) {
                        securityListAdapter.getData().get(i).setFollowsStatus(mSecurityDetailBean.getFollowsStatus());
                    }
                }
            }
            securityDetailBean.setReadCount(mSecurityDetailBean.getReadCount());
            securityDetailBean.setReadStatus(mSecurityDetailBean.getReadStatus());
            /**
             * 是否删除
             * */
            if (intentData.getBooleanExtra("isDelete", false)) {
                securityListAdapter.getData().remove(securityDetailBean);
            }
            securityListAdapter.notifyDataSetChanged();
            if (securityListAdapter.getData() != null && securityListAdapter.getData().size() > 0) {
                tvNoDatas.setVisibility(View.GONE);
                rvSecurity.setVisibility(View.VISIBLE);
            } else {
                tvNoDatas.setVisibility(View.VISIBLE);
                rvSecurity.setVisibility(View.GONE);
            }
        }
    }
}

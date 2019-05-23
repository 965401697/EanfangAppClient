package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.security.SecurityFoucsBean;
import com.eanfang.model.security.SecurityLikeBean;
import com.eanfang.model.security.SecurityListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.im.SelectIMContactActivity;
import net.eanfang.client.ui.activity.worksapce.online.FaultExplainActivity;
import net.eanfang.client.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.client.ui.adapter.security.SecurityListAdapter;
import net.eanfang.client.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;

public class SecurityHotFragment extends TemplateItemListFragment {

    private String mTitle;
    private QueryEntry mQueryEntry;
    private SecurityListAdapter securityListAdapter;
    public static final int REFRESH_ITEM = 1010;
    private SecurityListBean.ListBean securityDetailBean;
    private int mPosition;
    private ArrayList<String> picList = new ArrayList<>();
    private String[] pics = null;


    public static SecurityHotFragment getInstance(String title) {
        SecurityHotFragment sf = new SecurityHotFragment();
        sf.mTitle = title;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }


    @Override
    protected void setListener() {
        securityListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_isFocus:
                    doFoucus(securityListAdapter.getData().get(position));
                    break;
                case R.id.ll_like:
                    doLike(position, securityListAdapter.getData().get(position));
                    break;
                case R.id.ll_comments:
                    doJump(position, true);
                    break;

                case R.id.ll_share:
                    doShare(securityListAdapter.getData().get(position));
                    break;
                case R.id.ll_pic:
                    picList.clear();
                    pics = securityListAdapter.getData().get(position).getSpcImg().split(",");
                    picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> BuildConfig.OSS_SERVER + (url).toString()).toList());
                    ImagePerviewUtil.perviewImage(getActivity(), picList);
                    break;
                case R.id.ll_question:
                case R.id.rl_video:
                    doJump(position, false);
                    break;
                default:
                    break;
            }
        });
        securityListAdapter.setOnItemClickListener((adapter, view, position) -> {
            doJump(position, false);
        });
    }

    public void doJump(int position, boolean isCommon) {
        //专家问答
        if (securityListAdapter.getData().get(position).getType() == 1) {
            Bundle bundle_question = new Bundle();
            bundle_question.putInt("QuestionIdZ", Integer.parseInt(securityListAdapter.getData().get(position).getQuestionId()));
            JumpItent.jump(getActivity(), FaultExplainActivity.class, bundle_question);
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("spcId", securityListAdapter.getData().get(position).getSpcId());
            bundle.putInt("friend", securityListAdapter.getData().get(position).getFriend());
            bundle.putBoolean("isCommon", isCommon);
            securityDetailBean = securityListAdapter.getData().get(position);
            mPosition = position;
            getActivity().startActivityForResult(new Intent(getActivity(), SecurityDetailActivity.class).putExtras(bundle), REFRESH_ITEM);
        }
    }

    @Override
    protected void initAdapter() {
        securityListAdapter = new SecurityListAdapter(EanfangApplication.get().getApplicationContext(), false);
        RecyclerView.RecycledViewPool pool = mRecyclerView.getRecycledViewPool();
        pool.setMaxRecycledViews(0, 10);
        mRecyclerView.setRecycledViewPool(pool);
        securityListAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        mRecyclerView.addItemDecoration(new BGASpaceItemDecoration(20));
        securityListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.addOnScrollListener(new BGARVOnScrollListener(getActivity()));

    }


    /**
     * 进行点赞
     */
    private void doLike(int position, SecurityListBean.ListBean listBean) {
        SecurityLikeBean securityLikeBean = new SecurityLikeBean();
        securityLikeBean.setAsId(listBean.getSpcId());
        securityLikeBean.setType("0");
        /**
         *状态：0 点赞 1 未点赞
         * */
        if (listBean.getLikeStatus() == 0) {
            listBean.setLikeStatus(1);
            listBean.setLikesCount(listBean.getLikesCount() - 1);
            securityLikeBean.setLikeStatus("1");
        } else {
            listBean.setLikeStatus(0);
            listBean.setLikesCount(listBean.getLikesCount() + 1);
            securityLikeBean.setLikeStatus("0");
        }
        securityLikeBean.setLikeUserId(EanfangApplication.get().getUserId());
        securityLikeBean.setLikeCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityLikeBean.setLikeTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_LIKE)
                .upJson(JSONObject.toJSONString(securityLikeBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
                    getActivity().runOnUiThread(() -> {
                        securityListAdapter.notifyItemChanged(position);
                    });
                }));
    }


    /**
     * 取消关注
     */
    private void doFoucus(SecurityListBean.ListBean listBean) {
        SecurityFoucsBean securityFoucsBean = new SecurityFoucsBean();
        securityFoucsBean.setFollowUserId(EanfangApplication.get().getUserId());
        securityFoucsBean.setFollowCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getCompanyId());
        securityFoucsBean.setFollowTopCompanyId(EanfangApplication.get().getUser().getAccount().getDefaultUser().getTopCompanyId());

        securityFoucsBean.setAsUserId(listBean.getPublisherUserId());
        securityFoucsBean.setAsCompanyId(listBean.getPublisherCompanyId());
        securityFoucsBean.setAsTopCompanyId(listBean.getPublisherTopCompanyId());
        securityFoucsBean.setAsAccId(listBean.getPublisherUser().getAccId());
        securityFoucsBean.setFollowAccId(EanfangApplication.get().getAccId());
        /**
         * 状态：0 关注 1 未关注
         * */
        if (listBean.getFollowsStatus() == 0) {
            listBean.setFollowsStatus(1);
            securityFoucsBean.setFollowsStatus(1);
        } else {
            listBean.setFollowsStatus(0);
            securityFoucsBean.setFollowsStatus(0);
        }
        EanfangHttp.post(NewApiService.SERCURITY_FOUCUS)
                .upJson(JSONObject.toJSONString(securityFoucsBean))
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, bean -> {
                    for (int i = 0; i < securityListAdapter.getData().size(); i++) {
                        if (securityListAdapter.getData().get(i).getAccountEntity().getAccId().equals(listBean.getAccountEntity().getAccId())) {
                            securityListAdapter.getData().get(i).setFollowsStatus(listBean.getFollowsStatus());
                            securityListAdapter.notifyDataSetChanged();
                        }
                    }
                }));
    }

    /**
     * 分享 分享到好友
     */
    private void doShare(SecurityListBean.ListBean listBean) {
        //分享聊天
        if (listBean != null) {
            Intent intent = new Intent(getActivity(), SelectIMContactActivity.class);
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

    @Override
    protected void getData() {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        mQueryEntry.setPage(mPage);
        mQueryEntry.setSize(10);
        EanfangHttp.post(NewApiService.SECURITY_HOT)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SecurityListBean>(getActivity(), true, SecurityListBean.class) {

                    @Override
                    public void onSuccess(SecurityListBean bean) {
                        if (mPage == 1) {
                            securityListAdapter.getData().clear();
                            securityListAdapter.setNewData(bean.getList());
                            securityListAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                            securityListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityListAdapter.loadMoreEnd();
                                mQueryEntry = null;
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
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
                        mSwipeRefreshLayout.setRefreshing(false);
                        securityListAdapter.loadMoreEnd();//没有数据了
                        if (securityListAdapter.getData().size() == 0) {
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

    @Override
    public void onRefresh() {
        mQueryEntry = null;
        mPage = 1;
        getData();
    }

    /**
     * 刷新 创建安防圈
     */
    public void refreshStatus() {
        mQueryEntry = null;
        mPage = 1;
        getData();
    }

    /**
     * 刷新点赞 关注状态
     */
    public void refreshItemStatus(Intent intentData) {
        /**
         * 是否删除
         * */
        if (intentData.getBooleanExtra("isDelete", false)) {
            securityListAdapter.getData().remove(securityDetailBean);
            securityListAdapter.notifyDataSetChanged();
            if (securityListAdapter.getData() != null && securityListAdapter.getData().size() > 0) {
                mTvNoData.setVisibility(View.GONE);
            } else {
                mTvNoData.setVisibility(View.VISIBLE);
            }
        } else if (securityDetailBean != null && intentData != null) {
            SecurityListBean.ListBean mSecurityDetailBean = (SecurityListBean.ListBean) intentData.getSerializableExtra("itemStatus");
            /**
             * 是否点赞
             * */
            if (intentData.getBooleanExtra("isLikeEdit", false)) {
                securityDetailBean.setLikeStatus(mSecurityDetailBean.getLikeStatus());
                securityDetailBean.setLikesCount(mSecurityDetailBean.getLikesCount());
            }
            /**
             * 是否关注
             * */
            if (intentData.getBooleanExtra("isFoucsEdit", false)) {
                securityDetailBean.setFollowsStatus(mSecurityDetailBean.getFollowsStatus());
                Iterator<SecurityListBean.ListBean> iterator = securityListAdapter.getData().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getAccountEntity().getAccId().equals(mSecurityDetailBean.getAccountEntity().getAccId())) {
                        iterator.remove();
                    }
                }
            }
            /**
             * 是否评论
             * */
            if (intentData.getBooleanExtra("isCommentEdit", false)) {
                securityDetailBean.setCommentCount(mSecurityDetailBean.getCommentCount());
            }
            securityDetailBean.setReadCount(mSecurityDetailBean.getReadCount());
            securityListAdapter.notifyDataSetChanged();
            if (securityListAdapter.getData() != null && securityListAdapter.getData().size() > 0) {
                mTvNoData.setVisibility(View.GONE);
            } else {
                mTvNoData.setVisibility(View.VISIBLE);
            }
        }
    }


}

package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Stream;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.bean.security.SecurityLikeBean;
import com.eanfang.bean.security.SecurityLikeStatusBean;
import com.eanfang.bean.security.SecurityListBean;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.online.FaultExplainActivity;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityCreateActivity;
import net.eanfang.worker.ui.activity.worksapce.security.SecurityDetailActivity;
import net.eanfang.worker.ui.adapter.security.SecurityListAdapter;
import net.eanfang.worker.util.ImagePerviewUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeSecurityFragment extends HomeTemplateItemListFragment implements SecurityListAdapter.OnPhotoClickListener {


    private SecurityListAdapter securityListAdapter;


    private ArrayList<String> picList = new ArrayList<>();
    private String[] pics = null;
    private Boolean mIsCreate = false;

    public static HomeSecurityFragment getInstance(Boolean isCreate) {
        HomeSecurityFragment homeFindFragment = new HomeSecurityFragment();
        homeFindFragment.mIsCreate = isCreate;
        return homeFindFragment;
    }

    @Override
    protected void initAdapter() {
        securityListAdapter = new SecurityListAdapter(false, this);
        securityListAdapter.bindToRecyclerView(mRecyclerView);
        securityListAdapter.setOnLoadMoreListener(this, mRecyclerView);
        if (mIsCreate) {
            templateItemListBinding.ivCreateSecurity.setVisibility(View.VISIBLE);
        } else {
            templateItemListBinding.ivCreateSecurity.setVisibility(View.GONE);
        }
        initSecurity();
        super.initAdapter();
    }

    /**
     * 安防圈
     */
    private void initSecurity() {
        initListener();
    }

    private void initListener() {
        templateItemListBinding.ivCreateSecurity.setOnClickListener((v) -> {
            JumpItent.jump(getActivity(), SecurityCreateActivity.class);
        });
        securityListAdapter.setOnItemClickListener((adapter, view, position) -> {
            doJump(position, false);
        });
        securityListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_comments:
                    doJump(position, true);
                    break;
                case R.id.ll_like:
                    doLike(position, securityListAdapter.getData().get(position));
                    break;
                case R.id.tv_isFocus:
                case R.id.iv_share:
                case R.id.ll_question:
                case R.id.rl_video:
                case R.id.rl_content:
                    doJump(position, false);
                    break;
                default:
                    break;
            }
        });
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
            listBean.setLikesCount(listBean.getLikesCount());
            securityLikeBean.setLikeStatus("1");
        } else {
            listBean.setLikeStatus(0);
            listBean.setLikesCount(listBean.getLikesCount());
            securityLikeBean.setLikeStatus("0");
        }
        securityLikeBean.setLikeUserId(WorkerApplication.get().getUserId());
        securityLikeBean.setLikeCompanyId(WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyId());
        securityLikeBean.setLikeTopCompanyId(WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getTopCompanyId());
        EanfangHttp.post(NewApiService.SERCURITY_LIKE)
                .upJson(JSONObject.toJSONString(securityLikeBean))
                .execute(new EanfangCallback<SecurityLikeStatusBean>(getActivity(), true, SecurityLikeStatusBean.class, bean -> {
                    getActivity().runOnUiThread(() -> {
                        securityListAdapter.getData().get(position).setLikeStatus(bean.getLikeStatus());
                        securityListAdapter.getData().get(position).setLikesCount(bean.getLikesCount());
                        securityListAdapter.notifyItemChanged(position);
                    });
                }));
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
            bundle.putBoolean("isCommon", isCommon);
            JumpItent.jump(getActivity(), SecurityDetailActivity.class, bundle);
        }
    }

    private void doGetSecurityData(int mPage) {
        QueryEntry mQueryEntry = new QueryEntry();
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
                            securityListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                securityListAdapter.loadMoreEnd();
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

                });
    }

    @Override
    protected void getData() {
        doGetSecurityData(mPage);
    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    public void onPhotoClick(int position, int mWhich) {
        picList.clear();
        pics = securityListAdapter.getData().get(position).getSpcImg().split(",");
        picList.addAll(Stream.of(Arrays.asList(pics)).map(url -> BuildConfig.OSS_SERVER + (url)).toList());
        ImagePerviewUtil.perviewImage(getActivity(), picList, mWhich);
    }
}

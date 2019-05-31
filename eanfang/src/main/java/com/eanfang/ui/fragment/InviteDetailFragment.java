package com.eanfang.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.InviteDetailBean;
import com.eanfang.model.InviteDetailBean2;
import com.eanfang.ui.adapter.InviteDetailArrayAdapter;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.QueryEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :邀请明细
 */
public class InviteDetailFragment extends BaseFragment {

    private static final String EXTRA_STATE = "extraState";
    private static final String TAG = "InviteDetailFragment";
    @BindView(R2.id.recycler_invite)
    RecyclerView mRecyclerInvite;
    Unbinder unbinder;
    private int mState;
    private static final String PARAM_ACCID_KEY1 = "inviteAccId";
    private static final String PARAM_ACCID_KEY2 = "accId";
    private static final String PARAM_CATEGORY_KEY = "detailedCategory";
    private InviteDetailArrayAdapter mInviteDetailArrayAdapter;
    private int mTotalPage;
    private int mCurrPage = 1;

    public static InviteDetailFragment getInstance(int state) {
        InviteDetailFragment fragment = new InviteDetailFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_invite_detail;
    }

    @Override
    protected void initData(Bundle arguments) {
        Bundle args = getArguments();
        if (args != null) {
            mState = args.getInt(EXTRA_STATE, 0);
        }
    }

    @Override
    protected void initView() {
        mRecyclerInvite.setLayoutManager(new LinearLayoutManager(getActivity()));
        mInviteDetailArrayAdapter = new InviteDetailArrayAdapter(mState, getActivity());
        mInviteDetailArrayAdapter.bindToRecyclerView(mRecyclerInvite);
        mInviteDetailArrayAdapter.setOnLoadMoreListener(() -> {
            if (mCurrPage < mTotalPage) {
                mCurrPage++;
                initDate();
            }
        }, mRecyclerInvite);
        initDate();
    }

    @Override
    protected void setListener() {

    }

    /**
     * 网络请求数据
     */
    private void initDate() {
        if (mState == 1) {
            QueryEntry queryEntry = new QueryEntry();
            queryEntry.getEquals().put(PARAM_ACCID_KEY2, String.valueOf(EanfangApplication.get().getAccId()));
            queryEntry.setPage(mCurrPage);
            EanfangHttp.post(NewApiService.WITHDRAWALS_CATEGORY_LIST)
                    .upJson(JSON.toJSONString(queryEntry))
                    .execute(new EanfangCallback<InviteDetailBean>(getActivity(), true, InviteDetailBean.class, bean -> {

                        mTotalPage = bean.getTotalPage();
                        if (bean.getCurrPage() == 1) {
                            mInviteDetailArrayAdapter.getData().clear();
                            mInviteDetailArrayAdapter.setNewData(bean.getList());
                        } else {
                            mInviteDetailArrayAdapter.addData(bean.getList());
                        }
                        mInviteDetailArrayAdapter.loadMoreComplete();
                        if (bean.getCurrPage() >= mTotalPage) {
                            mInviteDetailArrayAdapter.loadMoreEnd();
                        }
                    }));
        } else {
            QueryEntry queryEntry = new QueryEntry();
            queryEntry.getEquals().put(PARAM_ACCID_KEY1, String.valueOf(EanfangApplication.get().getAccId()));
            queryEntry.getEquals().put(PARAM_CATEGORY_KEY, String.valueOf(mState));
            queryEntry.setPage(mCurrPage);
            EanfangHttp.post(NewApiService.CATEGORY_LIST)
                    .upJson(JSON.toJSONString(queryEntry))
                    .execute(new EanfangCallback<InviteDetailBean2>(getActivity(), true, InviteDetailBean2.class, bean -> {
                        ArrayList<InviteDetailBean.ListBean> listBeans = new ArrayList<>();
                        for (InviteDetailBean2.ListBean listBean1 : bean.getList()) {
                            InviteDetailBean.ListBean listBean = new InviteDetailBean.ListBean();
                            listBean.setAccountPhone(listBean1.getInviteMobile());
                            listBean.setWithdrawalMoney(listBean1.getBountyAmount());
                            listBean.setEditTime(listBean1.getCreateTime());
                            listBeans.add(listBean);
                        }

                        mTotalPage = bean.getTotalPage();
                        if (bean.getCurrPage() == 1) {
                            mInviteDetailArrayAdapter.getData().clear();
                            mInviteDetailArrayAdapter.setNewData(listBeans);
                        } else {
                            mInviteDetailArrayAdapter.addData(listBeans);
                        }
                        mInviteDetailArrayAdapter.loadMoreComplete();
                        if (bean.getCurrPage() >= mTotalPage) {
                            mInviteDetailArrayAdapter.loadMoreEnd();
                        }

                    }));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

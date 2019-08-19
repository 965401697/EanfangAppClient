package net.eanfang.client.ui.activity.leave_post;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityLeavePostManageBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertHistoryBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostHistoryListViewModel;
import net.eanfang.client.ui.adapter.LeavePostHistoryAdapter;


/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :历史报警页面
 */
public class LeavePostHistoryActivity extends BaseActivity {
    private ActivityLeavePostManageBinding mBinding;
    private LeavePostHistoryAdapter mAdapter;
    private LeavePostHistoryListViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_manage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setLeftBack(true);
        setTitle("历史报警");
        setRightClick("筛选", R.drawable.icon_screen, view -> mViewModel.gotoScreenPage(LeavePostHistoryActivity.this));
        mAdapter = new LeavePostHistoryAdapter(R.layout.item_leave_post_history);
        mBinding.recLeavePostManage.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostManage);
        mAdapter.setOnItemClickListener((adapter, view, position) -> mViewModel.gotoHistoryDetailPage(LeavePostHistoryActivity.this, adapter, position));
        mAdapter.setOnLoadMoreListener(() -> mViewModel.loadMoreData(), mBinding.recLeavePostManage);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostHistoryListViewModel.class);
        mViewModel.getHistoryListData(ClientApplication.get().getCompanyId());
        mViewModel.getLeavePostHistoryListData().observe(this, this::setData);
        return mViewModel;
    }

    private void setData(LeavePostAlertHistoryBean leavePostAlertHistoryBean) {
        if (leavePostAlertHistoryBean == null) {
            return;
        }
        if (leavePostAlertHistoryBean.getCurrPage() == 1) {
            mAdapter.setNewData(leavePostAlertHistoryBean.getList());
        } else {
            mAdapter.addData(leavePostAlertHistoryBean.getList());
        }
        mAdapter.loadMoreComplete();
        if (leavePostAlertHistoryBean.getCurrPage() >= leavePostAlertHistoryBean.getTotalPage()) {
            mAdapter.loadMoreEnd();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            mViewModel.setResultData(data);
        }
    }
}

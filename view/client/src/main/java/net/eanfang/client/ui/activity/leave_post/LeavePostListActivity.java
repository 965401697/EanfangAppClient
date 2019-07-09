package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostManageBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePosAlarmAllViewModel;
import net.eanfang.client.ui.adapter.LeavePostDetailAdapter;

import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :历史记录列表页
 */
public class LeavePostListActivity extends BaseActivity {
    private ActivityLeavePostManageBinding mBinding;
    private LeavePostDetailAdapter mAdapter;
    private LeavePosAlarmAllViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_manage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        int type = getIntent().getIntExtra("type", 0);
        int dateType = getIntent().getIntExtra("dateType", 0);
        if (type == 0) {
            mViewModel.getAlertRankingList(dateType);
        } else {
            mViewModel.getStationRankingList(dateType);
        }
        setTitle("全部");
        setLeftBack(true);
        mAdapter = new LeavePostDetailAdapter();
        mBinding.recLeavePostManage.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostManage);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> mViewModel.gotoAlertDetailPage(LeavePostListActivity.this, adapter, position));
        mAdapter.setOnLoadMoreListener(() -> mViewModel.loadMoreData(type), mBinding.recLeavePostManage);

    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePosAlarmAllViewModel.class);
        mViewModel.getStationDetailData().observe(this, this::setAlertAdapter);
        mViewModel.getAlertDetailData().observe(this, this::setAlertAdapter);
        return mViewModel;
    }

    private void setAlertAdapter(List<LeavePostDetailBean> leavePostDetailBeans) {
        if (mViewModel.getMCurrentPage() == 1) {
            mAdapter.setNewData(leavePostDetailBeans);
        } else {
            mAdapter.addData(leavePostDetailBeans);
        }
        mAdapter.loadMoreComplete();
        if (mViewModel.getMCurrentPage() >= mViewModel.getMTotalPage()) {
            mAdapter.loadMoreEnd();
        }
    }

}

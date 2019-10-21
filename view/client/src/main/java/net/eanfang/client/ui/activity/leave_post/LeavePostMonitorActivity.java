package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.LeavePostDetailBean;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostCheckListBinding;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostMonitorViewModel;
import net.eanfang.client.ui.adapter.LeavePostDetailAdapter;

import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :检测设备
 */
public class LeavePostMonitorActivity extends BaseActivity {
    private ActivityLeavePostCheckListBinding mBinding;
    private LeavePostDetailAdapter mAdapter;
    private LeavePostMonitorViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_check_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("监测设备");
        setRightClick("确定", view -> mViewModel.setResult(LeavePostMonitorActivity.this));
        mAdapter = new LeavePostDetailAdapter();
        mBinding.recLeavePostCheckList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostCheckList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> mViewModel.setItemCheck(adapter, view, position));
        mBinding.leavePostCheckListSearch.setCursorVisible(false);
        mBinding.leavePostCheckListSearch.setFocusable(false);
        mBinding.leavePostCheckListSearch.setFocusableInTouchMode(false);
        mBinding.leavePostCheckListSearch.setOnClickListener(view -> mViewModel.gotoSecondPage(LeavePostMonitorActivity.this));

        mAdapter.setOnLoadMoreListener(() -> mViewModel.loadMoreData(), mBinding.recLeavePostCheckList);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostMonitorViewModel.class);
        mViewModel.getLeavePostDetailData().observe(this, this::setData);
        mViewModel.getMonitorList(null);
        return mViewModel;
    }

    private void setData(List<LeavePostDetailBean> leavePostDetailBeans) {
        if (mViewModel.getMLeavePostMonitorBean() != null) {
            if (mViewModel.getMLeavePostMonitorBean().getCurrPage() == 1) {
                mAdapter.setNewData(leavePostDetailBeans);
            } else {
                mAdapter.addData(leavePostDetailBeans);
            }
            mAdapter.loadMoreComplete();
            if (mViewModel.getMLeavePostMonitorBean().getCurrPage() >= mViewModel.getMLeavePostMonitorBean().getTotalPage()) {
                mAdapter.loadMoreEnd();
            }
        }
    }

}

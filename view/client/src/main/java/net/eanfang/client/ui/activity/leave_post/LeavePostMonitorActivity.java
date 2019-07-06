package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostCheckListBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
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
        mAdapter = new LeavePostDetailAdapter();
        mBinding.recLeavePostCheckList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostCheckList);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostMonitorViewModel.class);
        mViewModel.getLeavePostDetailData().observe(this, this::setData);
        mViewModel.getMonitorList();
        return mViewModel;
    }

    private void setData(List<LeavePostDetailBean> leavePostDetailBeans) {
        mAdapter.setNewData(leavePostDetailBeans);
    }

}

package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostManageBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePosAlarmRankingViewModel;
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
    private LeavePosAlarmRankingViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_manage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            mViewModel.getAlertRankingList();
        } else {
            mViewModel.getStationRankingList();
        }
        setTitle("全部");
        setLeftBack(true);
        mAdapter = new LeavePostDetailAdapter();
        mBinding.recLeavePostManage.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostManage);
    }


    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePosAlarmRankingViewModel.class);
        mViewModel.getStationDetailData().observe(this, this::setStationAdapter);
        mViewModel.getAlertDetailData().observe(this, this::setAlertAdapter);
        return mViewModel;
    }

    private void setAlertAdapter(List<LeavePostDetailBean> leavePostDetailBeans) {
        mAdapter.setNewData(leavePostDetailBeans);
    }

    private void setStationAdapter(List<LeavePostDetailBean> leavePostDetailBeans) {
        mAdapter.setNewData(leavePostDetailBeans);
    }


}

package net.eanfang.client.ui.activity.leave_post;

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
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostManageDetailViewModel;
import net.eanfang.client.ui.adapter.LeavePostDetailAdapter;

import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :岗位管理详情
 */
public class LeavePostManageDetailActivity extends BaseActivity {
    private ActivityLeavePostManageBinding mBinding;
    private LeavePostDetailAdapter mAdapter;
    private LeavePostManageDetailViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_manage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        String areaName = getIntent().getStringExtra("areaName");
        setTitle(areaName + "地区岗位");
        mViewModel.getDeviceListData(ClientApplication.get().getCompanyId(), areaName);
        mAdapter = new LeavePostDetailAdapter();
        mBinding.recLeavePostManage.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostManage);
        mAdapter.setOnItemClickListener((adapter, view, position) -> mViewModel.gotoPostDetailPage(LeavePostManageDetailActivity.this, adapter, position));
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostManageDetailViewModel.class);
        mViewModel.getLeavePostDetailBean().observe(this, this::setData);
        return mViewModel;
    }

    private void setData(List<LeavePostDetailBean> leavePostDetailBeans) {
        mAdapter.setNewData(leavePostDetailBeans);
    }


}

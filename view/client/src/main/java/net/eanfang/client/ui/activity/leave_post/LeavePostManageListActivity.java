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
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostManageListViewModel;
import net.eanfang.client.ui.adapter.LeavePostManageListAdapter;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :岗位管理
 */
public class LeavePostManageListActivity extends BaseActivity {
    private ActivityLeavePostManageBinding mBinding;
    private LeavePostManageListAdapter mAdapter;
    private LeavePostManageListViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_manage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        setRightImageResId(R.mipmap.ic_add);
        setRightClick(view -> mViewModel.gotoAddPostPage(LeavePostManageListActivity.this));
        setTitle("岗位管理");
        mAdapter = new LeavePostManageListAdapter(R.layout.item_leave_post_manage_list);
        mBinding.recLeavePostManage.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostManage);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mViewModel.setItemClick(this, adapter, position);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostManageListViewModel.class);
        mViewModel.getLeavePostManageListData().observe(this, this::setData);
        mViewModel.getPostManageList(ClientApplication.get().getCompanyId());
        return mViewModel;
    }

    private void setData(LeavePostManageListBean leavePostManageListBeans) {
        mAdapter.setNewData(leavePostManageListBeans.getList());
    }

}

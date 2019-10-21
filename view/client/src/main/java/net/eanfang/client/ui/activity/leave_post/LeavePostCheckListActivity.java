package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.entity.station.StationDetectStationsEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityLeavePostCheckListBinding;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostCheckViewModel;
import net.eanfang.client.ui.adapter.LeavePostCheckListAdapter;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗列表
 */
public class LeavePostCheckListActivity extends BaseActivity {
    private LeavePostCheckListAdapter mAdapter;
    private ActivityLeavePostCheckListBinding mBinding;
    private LeavePostCheckViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_check_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("图像查岗");
        setLeftBack(true);
        mBinding.leavePostCheckListSearch.setCursorVisible(false);
        mBinding.leavePostCheckListSearch.setFocusable(false);
        mBinding.leavePostCheckListSearch.setFocusableInTouchMode(false);
        mBinding.leavePostCheckListSearch.setOnClickListener(view -> mViewModel.gotoCheckSecondPage(LeavePostCheckListActivity.this));
        mAdapter = new LeavePostCheckListAdapter(R.layout.item_leave_post_check);
        mBinding.recLeavePostCheckList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostCheckList);
        mAdapter.setOnLoadMoreListener(() -> mViewModel.loadMore(), mBinding.recLeavePostCheckList);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mViewModel.gotoCheckDetailPage(this, adapter, position);
        });

    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostCheckViewModel.class);
        mViewModel.getLeavePostDeviceList().observe(this, this::setData);
        mViewModel.getDeviceListData(ClientApplication.get().getCompanyId(), null);
        return mViewModel;
    }

    private void setData(PageBean<StationDetectStationsEntity> bean) {
        if (bean.getCurrPage() == 1) {
            mAdapter.setNewData(bean.getList());
        } else {
            mAdapter.addData(bean.getList());
        }
        mAdapter.loadMoreComplete();
        if (bean.getCurrPage() >= bean.getTotalPage()) {
            mAdapter.loadMoreEnd();
        }
    }

}

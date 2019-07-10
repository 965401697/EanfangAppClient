package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityLeavePostCheckListBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceListBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostCheckViewModel;
import net.eanfang.client.ui.adapter.LeavePostCheckListAdapter;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗二级搜索页列表
 */
public class LeavePostCheckListSecondActivity extends BaseActivity {
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
        setTitle("搜索岗位");
        setLeftBack(true);
        mBinding.leavePostCheckListSearch.setHint("请输入岗位名称或编号");
        mBinding.leavePostCheckListSearch.setHorizontallyScrolling(false);
        mBinding.leavePostCheckListSearch.setMaxLines(Integer.MAX_VALUE);
        mBinding.leavePostCheckListSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                mViewModel.getDeviceListData(ClientApplication.get().getCompanyId(),mBinding.leavePostCheckListSearch.getText().toString());
                return true;
            }
            return false;
        });
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
        return mViewModel;
    }

    private void setData(LeavePostDeviceListBean bean) {
        if (bean.getCurrPage() == 1) {
            mAdapter.setNewData(bean.getList());
        } else {
            mAdapter.addData(bean.getList());
        }
        mAdapter.loadMoreComplete();
        if (bean.getCurrPage() >= bean.getTotalCount()) {
            mAdapter.loadMoreEnd();
        }
    }

}

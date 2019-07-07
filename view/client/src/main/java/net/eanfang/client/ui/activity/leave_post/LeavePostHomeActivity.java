package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityLeavePostBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeTopBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostHomeViewModel;
import net.eanfang.client.ui.adapter.LeavePostHomeAdapter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗主页
 */
public class LeavePostHomeActivity extends BaseActivity {
    private ActivityLeavePostBinding mBinding;
    private LeavePostHomeAdapter mLeavePostHomeAdapter;
    private LeavePostHomeViewModel mLeavePostHomeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("脱岗监测");
        setLeftBack(true);
        mLeavePostHomeAdapter = new LeavePostHomeAdapter(R.layout.item_home_leave_post);
        mBinding.recLeavePostTodo.setLayoutManager(new LinearLayoutManager(this));
        mLeavePostHomeAdapter.bindToRecyclerView(mBinding.recLeavePostTodo);
        mLeavePostHomeAdapter.setOnLoadMoreListener(() -> mLeavePostHomeViewModel.loadMoreData(), mBinding.recLeavePostTodo);

        mBinding.imgLeavePostCheck.setOnClickListener(view -> mLeavePostHomeViewModel.gotoCheckListPage(this));
        mBinding.imgLeavePostManage.setOnClickListener(view -> mLeavePostHomeViewModel.gotoManagePage(this));
        mBinding.imgLeavePostHistory.setOnClickListener(view -> mLeavePostHomeViewModel.gotoHistoryPage(this));
        mLeavePostHomeAdapter.setOnItemClickListener((adapter, view, position) -> mLeavePostHomeViewModel.gotoLeavePostDetailPage(LeavePostHomeActivity.this, adapter, position));

    }

    @Override
    protected ViewModel initViewModel() {
        mLeavePostHomeViewModel = LViewModelProviders.of(this, LeavePostHomeViewModel.class);
        mLeavePostHomeViewModel.getLeavePostHomeTopData().observe(this, this::setTopData);
        mLeavePostHomeViewModel.getLeavePostHomeUnhandledAlert().observe(this, this::initBottomData);
        mLeavePostHomeViewModel.getHomeTopData(ClientApplication.get().getCompanyId());
        mLeavePostHomeViewModel.getHomeUnhandledAlert(ClientApplication.get().getCompanyId());
        return mLeavePostHomeViewModel;
    }

    private void initBottomData(LeavePostHomeUnHandledAlertBean leavePostHomeUnHandledAlertBean) {
        if (leavePostHomeUnHandledAlertBean == null) {
            finishWithResultOk();
            return;
        }
        LeavePostHomeUnHandledAlertBean.UnhandledAlertListBean unhandledAlertListBean = leavePostHomeUnHandledAlertBean.getUnhandledAlertList();
        mBinding.tvLeavePostTodoCount.setText(getString(R.string.text_leave_post_todo_count, leavePostHomeUnHandledAlertBean.getUnhandledAlertCount()));
        if (unhandledAlertListBean.getCurrPage() == 1) {
            mLeavePostHomeAdapter.setNewData(unhandledAlertListBean.getList());
        } else {
            mLeavePostHomeAdapter.addData(unhandledAlertListBean.getList());
        }
        mLeavePostHomeAdapter.loadMoreComplete();
        if (unhandledAlertListBean.getCurrPage() >= unhandledAlertListBean.getTotalPage()) {
            mLeavePostHomeAdapter.loadMoreEnd();
        }
    }

    /**
     * 数据请求成功的回调
     *
     * @param leavePostHomeBean
     */
    private void setTopData(LeavePostHomeTopBean leavePostHomeBean) {
        mBinding.tvLeavePostPostCount.setText(String.valueOf(leavePostHomeBean.getStationDetect().getStationCount()));
        mBinding.tvLeavePostWarnCount.setText(String.valueOf(leavePostHomeBean.getTodayAlertCount()));
        mBinding.tvLeavePostAllWarn.setText(String.valueOf(leavePostHomeBean.getTotalAlertCount()));
        int changeCount = leavePostHomeBean.getDifference();
        if (changeCount > 0) {
            mBinding.tvLeavePostChangeCount.setText(String.format("+%d", changeCount));
        } else {
            mBinding.tvLeavePostChangeCount.setText(String.valueOf(changeCount));
        }
        mBinding.tvLeavePostDate.setText(String.format("%s\t\t%s\t\t%s", GetDateUtils.dateToTime(leavePostHomeBean.getNow()), leavePostHomeBean.getLunarDate(), GetDateUtils.dateToWeek(leavePostHomeBean.getNow())));
    }


}

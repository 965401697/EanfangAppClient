package net.eanfang.client.ui.activity.leave_post;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.DateKit;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityLeavePostBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeTopBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostHomeViewModel;
import net.eanfang.client.ui.adapter.LeavePostHomeAdapter;

import cn.hutool.core.date.DateUtil;

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

        mBinding.tvImgCheckup.setOnClickListener(view -> mLeavePostHomeViewModel.gotoCheckListPage(this));
        mBinding.tvPostManagement.setOnClickListener(view -> mLeavePostHomeViewModel.gotoManagePage(this));
        mBinding.tvCheckupHistory.setOnClickListener(view -> mLeavePostHomeViewModel.gotoHistoryPage(this));
        mLeavePostHomeAdapter.setOnItemClickListener((adapter, view, position) -> mLeavePostHomeViewModel.gotoLeavePostDetailPage(LeavePostHomeActivity.this, adapter, position));
        mBinding.tvLeavePostChangeCount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.tvLeavePostAllWarn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.tvLeavePostWarnCount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mBinding.tvLeavePostPostCount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
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
            return;
        }
        LeavePostHomeUnHandledAlertBean.UnhandledAlertListBean unhandledAlertListBean = leavePostHomeUnHandledAlertBean.getUnhandledAlertList();
        String todoText = getString(R.string.text_leave_post_todo_count, leavePostHomeUnHandledAlertBean.getUnhandledAlertCount());
        SpannableString spannableString = new SpannableString(todoText);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6419")), 8, todoText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(18, true);
        spannableString.setSpan(span, 8, todoText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBinding.tvLeavePostTodoCount.setText(spannableString);
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
        mBinding.tvLeavePostDate.setText(String.format("%s\t\t%s\t\t%s", DateUtil.parse(leavePostHomeBean.getNow()).toString("yyyy年MM月dd日"), leavePostHomeBean.getLunarDate(), DateKit.get(leavePostHomeBean.getNow()).weekName()));
    }


}

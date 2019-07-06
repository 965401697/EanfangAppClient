package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostAlarmRankingBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDefaultRankingBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePosAlarmRankingViewModel;
import net.eanfang.client.ui.adapter.LeavePostRankingAdapter;
import net.eanfang.client.ui.adapter.YearMonthDayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :报警排名页面
 */
public class LeavePostAlarmRankingActivity extends BaseActivity {
    private ActivityLeavePostAlarmRankingBinding mBinding;
    private LeavePosAlarmRankingViewModel mViewModel;
    private List<String> mYears;
    private List<String> mMonths;
    private List<String> mDays;
    private LeavePostRankingAdapter mAdapter;
    private int mJumpAllType = 0;
    private String[] addOnDayMonth = new String[]{"1", "3", "5", "7", "8", "10", "12"};

    {
        mYears = new ArrayList<>();
        mMonths = new ArrayList<>();
        mDays = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            if (i == 0) {
                mMonths.add("请选择");
                continue;
            }
            mMonths.add(String.valueOf(i));
        }
        mYears.add("2019");
        for (int i = 0; i < 31; i++) {
            if (i == 0) {
                mDays.add("请选择");
                continue;
            }
            mDays.add(String.valueOf(i));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_alarm_ranking);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        setTitle("报警排名");
        setRightTitle("历史详情");
        mBinding.recChooseYear.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recChooseMonth.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recChooseDay.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recLeavePostAlarmRanking.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LeavePostRankingAdapter();
        mAdapter.bindToRecyclerView(mBinding.recLeavePostAlarmRanking);
        setRightBack(view -> mViewModel.gotoHistoryPage(LeavePostAlarmRankingActivity.this));
        initChooseView();
        mAdapter.setOnItemClickListener((adapter, view, position) -> mViewModel.gotoAlertDetailPage(LeavePostAlarmRankingActivity.this, adapter, position));
        mBinding.tvLeavePostRankingAll.setOnClickListener(view -> mViewModel.gotoAllAlert(LeavePostAlarmRankingActivity.this, mJumpAllType));

        mBinding.tvLeavePostRankingPost.setOnClickListener(view -> {
            if (mViewModel.getStationRankingData().getValue() == null) {
                mViewModel.getStationRankingList();
            } else {
                mAdapter.setNewData(mViewModel.getStationRankingData().getValue());
            }
            mJumpAllType = 0;
            mBinding.tvLeavePostRankingQuestion.setChecked(false);
            mBinding.tvLeavePostRankingPost.setChecked(true);
        });
        mBinding.tvLeavePostRankingQuestion.setOnClickListener(view -> {
            if (mViewModel.getAlertRankingData().getValue() == null) {
                mViewModel.getAlertRankingList();
            } else {
                mAdapter.setNewData(mViewModel.getAlertRankingData().getValue());
            }
            mJumpAllType = 1;
            mBinding.tvLeavePostRankingPost.setChecked(false);
            mBinding.tvLeavePostRankingQuestion.setChecked(true);
        });
        mBinding.tvOk.setOnClickListener(view -> {
            if (mBinding.tvLeavePostRankingPost.isChecked()) {
                mViewModel.getStationRankingList();
            } else {
                mViewModel.getStationRankingList();
            }
        });
        mBinding.tvLeavePostRankingPost.setChecked(true);
    }

    /**
     * 初始化选择器控件
     */
    private void initChooseView() {
        String defaultDay = "请选择";
        YearMonthDayAdapter yearAdapter = new YearMonthDayAdapter(mYears);
        yearAdapter.bindToRecyclerView(mBinding.recChooseYear);
        YearMonthDayAdapter monthAdapter = new YearMonthDayAdapter(mMonths);
        monthAdapter.bindToRecyclerView(mBinding.recChooseMonth);
        YearMonthDayAdapter dayAdapter = new YearMonthDayAdapter(mDays);
        dayAdapter.bindToRecyclerView(mBinding.recChooseDay);

        yearAdapter.setOnItemClickListener((adapter, view, position) -> setViewText(mBinding.tvLeavePostAlarmRankingYear, mBinding.recChooseYear, adapter, position));

        monthAdapter.setOnItemClickListener((adapter, view, position) -> setViewText(mBinding.tvLeavePostAlarmRankingMonth, mBinding.recChooseMonth, adapter, position));

        dayAdapter.setOnItemClickListener((adapter, view, position) -> setViewText(mBinding.tvLeavePostAlarmRankingDay, mBinding.recChooseDay, adapter, position));

        mBinding.bgYear.setOnClickListener(view -> {
            mBinding.recChooseMonth.setVisibility(View.GONE);
            mBinding.recChooseDay.setVisibility(View.GONE);
            if (mBinding.recChooseYear.getVisibility() == View.VISIBLE) {
                mBinding.recChooseYear.setVisibility(View.GONE);
            } else {
                mBinding.recChooseYear.setVisibility(View.VISIBLE);
            }

        });
        mBinding.bgMonth.setOnClickListener(view -> {
            mBinding.recChooseYear.setVisibility(View.GONE);
            mBinding.recChooseDay.setVisibility(View.GONE);
            if (defaultDay.equals(mBinding.tvLeavePostAlarmRankingYear.getText().toString())) {
                showToast("请先选择年份");
                return;
            }
            if (mBinding.recChooseMonth.getVisibility() == View.VISIBLE) {
                mBinding.recChooseMonth.setVisibility(View.GONE);
            } else {
                mBinding.recChooseMonth.setVisibility(View.VISIBLE);
            }
        });

        mBinding.bgDay.setOnClickListener(view -> {
            mBinding.recChooseYear.setVisibility(View.GONE);
            mBinding.recChooseMonth.setVisibility(View.GONE);
            if (defaultDay.equals(mBinding.tvLeavePostAlarmRankingMonth.getText().toString())) {
                showToast("请先选择月份");
                return;
            }
            if (Arrays.asList(addOnDayMonth).contains(mBinding.tvLeavePostAlarmRankingMonth.getText().toString())) {
                if (!mDays.contains("31")) {
                    mDays.add("31");
                }
            } else {
                mDays.remove("31");
            }
            dayAdapter.setNewData(mDays);
            if (mBinding.recChooseDay.getVisibility() == View.VISIBLE) {
                mBinding.recChooseDay.setVisibility(View.GONE);
            } else {
                mBinding.recChooseDay.setVisibility(View.VISIBLE);
            }

        });

    }

    private void setViewText(TextView textView, RecyclerView gongRec, BaseQuickAdapter adapter, int position) {
        String text = String.valueOf(adapter.getData().get(position));
        textView.setText(text);
        gongRec.setVisibility(View.GONE);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePosAlarmRankingViewModel.class);
        mViewModel.getAlertRankingData().observe(this, this::setAlertData);
        mViewModel.getStationRankingData().observe(this, this::setStationData);
        mViewModel.setBinding(mBinding);
        mViewModel.getStationRankingList();
        return mViewModel;
    }

    private void setStationData(List<LeavePostDefaultRankingBean> stationRankingList) {
        mAdapter.setNewData(stationRankingList);
    }

    private void setAlertData(List<LeavePostDefaultRankingBean> alertRankingList) {
        mAdapter.setNewData(alertRankingList);
    }

}

package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostAlarmRankingBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDefaultRankingBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePosAlarmRankingViewModel;
import net.eanfang.client.ui.adapter.LeavePostRankingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :报警排名页面
 */
public class LeavePostAlarmRankingActivity extends BaseActivity {
    private ActivityLeavePostAlarmRankingBinding mBinding;
    private LeavePosAlarmRankingViewModel mViewModel;
    private LeavePostRankingAdapter mAdapter;
    private int mJumpAllType = 0;
    private int dateType = 0;
    private ArrayList<CheckBox> mCheckBoxes;
    private String[] changePromptingText = new String[]{"较去年\t", "较上季\t", "较上月\t", "较上周\t", "较昨天\t"};

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
        mCheckBoxes = new ArrayList<>();
        mCheckBoxes.add(mBinding.checkboxLeavePostRankingYear);
        mCheckBoxes.add(mBinding.checkboxLeavePostRankingQuarter);
        mCheckBoxes.add(mBinding.checkboxLeavePostRankingMonth);
        mCheckBoxes.add(mBinding.checkboxLeavePostRankingWeek);
        mCheckBoxes.add(mBinding.checkboxLeavePostRankingDay);
        mBinding.recLeavePostAlarmRanking.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LeavePostRankingAdapter();
        mAdapter.bindToRecyclerView(mBinding.recLeavePostAlarmRanking);
        setRightClick(view -> mViewModel.gotoHistoryPage(LeavePostAlarmRankingActivity.this));
        mAdapter.setOnItemClickListener((adapter, view, position) -> mViewModel.gotoAlertDetailPage(LeavePostAlarmRankingActivity.this, adapter, position));
        mBinding.tvLeavePostRankingAll.setOnClickListener(view -> mViewModel.gotoAllAlert(LeavePostAlarmRankingActivity.this, mJumpAllType, dateType));

        mBinding.tvLeavePostRankingPost.setOnClickListener(view -> {
            if (mViewModel.getStationRankingData().getValue() == null) {
                mViewModel.getStationRankingList(dateType);
            } else {
                mAdapter.setNewData(mViewModel.getStationRankingData().getValue());
            }
            mJumpAllType = 0;
            mBinding.tvLeavePostRankingQuestion.setChecked(false);
            mBinding.tvLeavePostRankingPost.setChecked(true);
            mBinding.tvRankPostName.setText("岗位名称");
        });
        mBinding.tvLeavePostRankingQuestion.setOnClickListener(view -> {
            if (mViewModel.getAlertRankingData().getValue() == null) {
                mViewModel.getAlertRankingList(dateType);
            } else {
                mAdapter.setNewData(mViewModel.getAlertRankingData().getValue());
            }
            mJumpAllType = 1;
            mBinding.tvLeavePostRankingPost.setChecked(false);
            mBinding.tvLeavePostRankingQuestion.setChecked(true);
            mBinding.tvRankPostName.setText("问题类型");
        });

        mBinding.checkboxLeavePostRankingYear.setOnClickListener(view -> {
            dateType = 0;
            getData();
        });
        mBinding.checkboxLeavePostRankingQuarter.setOnClickListener(view -> {
            dateType = 1;
            getData();
        });
        mBinding.checkboxLeavePostRankingMonth.setOnClickListener(view -> {
            dateType = 2;
            getData();
        });
        mBinding.checkboxLeavePostRankingWeek.setOnClickListener(view -> {
            dateType = 3;
            getData();
        });
        mBinding.checkboxLeavePostRankingDay.setOnClickListener(view -> {
            dateType = 4;
            getData();
        });
        mBinding.tvLeavePostRankingPost.setChecked(true);
        mBinding.checkboxLeavePostRankingYear.setChecked(true);
    }

    private void getData() {
        if (mBinding.tvLeavePostRankingPost.isChecked()) {
            mViewModel.getStationRankingList(dateType);
        } else {
            mViewModel.getStationRankingList(dateType);
        }
        for (CheckBox checkBox : mCheckBoxes){
            checkBox.setChecked(mCheckBoxes.indexOf(checkBox) == dateType);
        }
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePosAlarmRankingViewModel.class);
        mViewModel.getAlertRankingData().observe(this, this::setAlertData);
        mViewModel.getStationRankingData().observe(this, this::setStationData);
        mViewModel.setBinding(mBinding);
        mViewModel.getStationRankingList(0);
        return mViewModel;
    }

    private void setStationData(List<LeavePostDefaultRankingBean> stationRankingList) {
        mAdapter.setNewData(stationRankingList);
        mBinding.tvRankCompare.setText(changePromptingText[dateType]);
    }

    private void setAlertData(List<LeavePostDefaultRankingBean> alertRankingList) {
        mAdapter.setNewData(alertRankingList);
        mBinding.tvRankCompare.setText(changePromptingText[dateType]);
    }

}

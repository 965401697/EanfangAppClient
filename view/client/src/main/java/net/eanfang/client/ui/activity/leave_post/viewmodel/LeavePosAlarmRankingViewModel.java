package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.databinding.ActivityLeavePostAlarmRankingBinding;
import net.eanfang.client.ui.activity.leave_post.LeavePostHistoryActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostHistoryDetailActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostListActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertRankingListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDefaultRankingBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostStationRankingListBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :报警排名
 */
public class LeavePosAlarmRankingViewModel extends BaseViewModel {
    private ActivityLeavePostAlarmRankingBinding mBinding;
    @Getter
    private MutableLiveData<List<LeavePostDefaultRankingBean>> stationRankingData;
    @Getter
    private MutableLiveData<List<LeavePostDefaultRankingBean>> alertRankingData;
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> alertDetailData;
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> stationDetailData;
    private LeavePostRepo mLeavePostHomeRepo;
    private int mCurrentPage = 1;
    private Long mCompanyId;

    public LeavePosAlarmRankingViewModel() {
        stationRankingData = new MutableLiveData<>();
        alertRankingData = new MutableLiveData<>();
        alertDetailData = new MutableLiveData<>();
        stationDetailData = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    public void setBinding(ActivityLeavePostAlarmRankingBinding binding) {
        this.mBinding = binding;
    }

    /**
     * 岗位排名
     */
    public void getStationRankingList() {
        String defaultText = "请选择";
        QueryEntry queryEntry = new QueryEntry();
        if (mBinding != null) {
            String year = mBinding.tvLeavePostAlarmRankingYear.getText().toString();
            if (defaultText.equals(year)) {
                showToast("请选择年份");
                return;
            }
            queryEntry.getEquals().put("year", year);
            if (!defaultText.equals(mBinding.tvLeavePostAlarmRankingMonth.getText().toString())) {
                queryEntry.getEquals().put("month", mBinding.tvLeavePostAlarmRankingMonth.getText().toString());
            }
            if (!defaultText.equals(mBinding.tvLeavePostAlarmRankingDay.getText().toString())) {
                queryEntry.getEquals().put("day", mBinding.tvLeavePostAlarmRankingDay.getText().toString());
            }
        }
        queryEntry.getEquals().put("isQuarter", "false");
        queryEntry.setSize(5);
        queryEntry.setPage(1);
        mLeavePostHomeRepo.postRankingListData(queryEntry).observe(lifecycleOwner, leavePostStationRankingListBean -> {
            ArrayList<LeavePostDefaultRankingBean> list = new ArrayList<>();
            ArrayList<LeavePostDetailBean> detailBeans = new ArrayList<>();
            for (LeavePostStationRankingListBean.ListBean bean : leavePostStationRankingListBean.getList()) {
                list.add(bean.getRankingBean());
                detailBeans.add(bean.getLeavePostDetailBean());
            }
            stationRankingData.setValue(list);
            stationDetailData.setValue(detailBeans);
        });
    }

    /**
     * 问题排名
     */
    public void getAlertRankingList() {
        String defaultText = "请选择";
        QueryEntry queryEntry = new QueryEntry();
        if (mBinding != null) {
            String year = mBinding.tvLeavePostAlarmRankingYear.getText().toString();
            if (!defaultText.equals(year)) {
                queryEntry.getEquals().put("year", year);
                if (!defaultText.equals(mBinding.tvLeavePostAlarmRankingMonth.getText().toString())) {
                    queryEntry.getEquals().put("month", mBinding.tvLeavePostAlarmRankingMonth.getText().toString());
                }
                if (!defaultText.equals(mBinding.tvLeavePostAlarmRankingDay.getText().toString())) {
                    queryEntry.getEquals().put("day", mBinding.tvLeavePostAlarmRankingDay.getText().toString());
                }
            }
        }
        queryEntry.getEquals().put("isQuarter", "false");
        mLeavePostHomeRepo.alertRankingList(queryEntry).observe(lifecycleOwner, leavePostAlertRankingListBean -> {
            ArrayList<LeavePostDefaultRankingBean> rankingBeans = new ArrayList<>();
            ArrayList<LeavePostDetailBean> detailBeans = new ArrayList<>();
            for (LeavePostAlertRankingListBean.ListBean bean : leavePostAlertRankingListBean.getList()) {
                rankingBeans.add(bean.getRankingBean());
                detailBeans.add(bean.getLeavePostDetailBean());
            }
            alertRankingData.setValue(rankingBeans);
            alertDetailData.setValue(detailBeans);
        });
    }

    public void gotoHistoryPage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostHistoryActivity.class));

    }

    public void gotoAlertDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostDefaultRankingBean bean = (LeavePostDefaultRankingBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostHistoryDetailActivity.class);
        intent.putExtra("stationId", bean.getStationId());
        intent.putExtra("date", bean.getDate());
        activity.startActivity(intent);
    }

    /**
     * 跳转全部报警页面
     */
    public void gotoAllAlert(Activity activity, int jumpType) {
        activity.startActivity(new Intent(activity, LeavePostListActivity.class).putExtra("type", jumpType));
    }
}

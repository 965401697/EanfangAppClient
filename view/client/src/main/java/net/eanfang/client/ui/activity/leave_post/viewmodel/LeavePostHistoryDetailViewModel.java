package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.ui.activity.leave_post.LeavePostDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;


import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗检测首页
 */
public class LeavePostHistoryDetailViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<LeavePostHistoryDayBean> historyDayData;
    @Getter
    private MutableLiveData<LeavePostHomeUnHandledAlertBean> leavePostHomeUnhandledAlert;
    private LeavePostRepo mLeavePostHomeRepo;
    private int mCurrentPage = 1;
    private Date mDate;
    private String mStationId;

    public LeavePostHistoryDetailViewModel() {
        historyDayData = new MutableLiveData<>();
        leavePostHomeUnhandledAlert = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 历史报警天列表
     *
     * @param queryDate
     * @param stationId
     */
    public void historyDayData(Date queryDate, String stationId) {
        mDate = queryDate;
        this.mStationId = stationId;
        mLeavePostHomeRepo.leavePostAlertInfoList(GetDateUtils.dateToDateString(queryDate), stationId).observe(lifecycleOwner, historyDayData::setValue);
    }

    public void gotoAlarmDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostHistoryDayBean.AlertListBean bean = (LeavePostHistoryDayBean.AlertListBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostDetailActivity.class);
        intent.putExtra("alertId", bean.getAlertId());
        activity.startActivity(intent);
    }

    public void setLastDay() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(mDate);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        historyDayData(ca.getTime(), mStationId);
    }

    public void setNextDay(View view) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(mDate);
        ca.add(Calendar.DAY_OF_MONTH, +1);
        Date date = ca.getTime();
        if (!date.after(new Date())) {
            historyDayData(ca.getTime(), mStationId);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}

package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.DateKit;

import net.eanfang.client.ui.activity.leave_post.LeavePostDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;


import java.text.MessageFormat;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗监测首页
 */
public class LeavePostHistoryDetailViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<LeavePostHistoryDayBean> historyDayData;
    @Getter
    private MutableLiveData<LeavePostHomeUnHandledAlertBean> leavePostHomeUnhandledAlert;
    private LeavePostRepo mLeavePostHomeRepo;
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
        mLeavePostHomeRepo.leavePostAlertInfoList(DateUtil.date(queryDate).toDateStr(), stationId).observe(lifecycleOwner, historyDayData::setValue);
    }

    public void gotoAlarmDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostHistoryDayBean.AlertListBean bean = (LeavePostHistoryDayBean.AlertListBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostDetailActivity.class);
        intent.putExtra("alertId", bean.getAlertId());
        activity.startActivity(intent);
    }

    public void setLastDay(TextView date) {
        LeavePostHistoryDayBean dayBean = historyDayData.getValue();
        if (dayBean != null && dayBean.getLastAlertDate() != null) {
            historyDayData(dayBean.getLastAlertDate(), mStationId);
            date.setText(MessageFormat.format("{0}\t\t{1}", DateUtil.date(dayBean.getLastAlertDate()).toString("yyyy年MM月dd日"), DateKit.get(dayBean.getLastAlertDate()).weekName()));
        } else {
            showToast("最后一条数据了");
        }

    }

    public void setNextDay(TextView date) {
        LeavePostHistoryDayBean dayBean = historyDayData.getValue();
        if (dayBean != null && dayBean.getNextAlertDate() != null) {
            historyDayData(dayBean.getNextAlertDate(), mStationId);
            date.setText(MessageFormat.format("{0}\t\t{1}", DateUtil.date(dayBean.getNextAlertDate()).toString("yyyy年MM月dd日"), DateKit.get(dayBean.getNextAlertDate()).weekName()));
        } else {
            showToast("最后一条数据了");
        }
    }
}

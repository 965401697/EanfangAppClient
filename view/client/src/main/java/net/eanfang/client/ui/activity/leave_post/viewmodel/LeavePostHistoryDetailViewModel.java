package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.LeavePostDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

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
        mLeavePostHomeRepo.leavePostAlertInfoList(queryDate, stationId).observe(lifecycleOwner, historyDayData::setValue);
    }

    public void gotoAlarmDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostHistoryDayBean.AlertListBean bean = (LeavePostHistoryDayBean.AlertListBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostDetailActivity.class);
        intent.putExtra("alertId", bean.getAlertId());
        activity.startActivity(intent);
    }
}

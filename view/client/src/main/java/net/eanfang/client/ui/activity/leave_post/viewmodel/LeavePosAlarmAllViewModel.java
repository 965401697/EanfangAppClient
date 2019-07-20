package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.databinding.ActivityLeavePostAlarmRankingBinding;
import net.eanfang.client.ui.activity.leave_post.LeavePostHistoryDetailActivity;
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
public class LeavePosAlarmAllViewModel extends BaseViewModel {
    private ActivityLeavePostAlarmRankingBinding mBinding;
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> alertDetailData;
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> stationDetailData;
    @Getter
    private int mTotalPage;
    private LeavePostRepo mLeavePostHomeRepo;
    @Getter
    private int mCurrentPage = 1;
    private int mDateType;

    public LeavePosAlarmAllViewModel() {
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
    public void getStationRankingList(int dateType) {
        mDateType = dateType;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("dateType", String.valueOf(dateType));
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        mLeavePostHomeRepo.postRankingListData(queryEntry).observe(lifecycleOwner, leavePostStationRankingListBean -> {
            mTotalPage = leavePostStationRankingListBean.getTotalPage();
            ArrayList<LeavePostDefaultRankingBean> list = new ArrayList<>();
            ArrayList<LeavePostDetailBean> detailBeans = new ArrayList<>();
            for (LeavePostStationRankingListBean.ListBean bean : leavePostStationRankingListBean.getList()) {
                list.add(bean.getRankingBean(dateType));
                detailBeans.add(bean.getLeavePostDetailBean());
            }
            stationDetailData.setValue(detailBeans);
        });
    }

    /**
     * 问题排名
     */
    public void getAlertRankingList(int dateType) {
        mDateType = dateType;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("dateType", String.valueOf(dateType));
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        mLeavePostHomeRepo.alertRankingList(queryEntry).observe(lifecycleOwner, leavePostAlertRankingListBean -> {
            mTotalPage = leavePostAlertRankingListBean.getTotalPage();
            ArrayList<LeavePostDefaultRankingBean> rankingBeans = new ArrayList<>();
            ArrayList<LeavePostDetailBean> detailBeans = new ArrayList<>();
            for (LeavePostAlertRankingListBean.ListBean bean : leavePostAlertRankingListBean.getList()) {
                rankingBeans.add(bean.getRankingBean(dateType));
                detailBeans.add(bean.getLeavePostDetailBean());
            }
            alertDetailData.setValue(detailBeans);
        });
    }

    public void gotoAlertDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostDetailBean bean = (LeavePostDetailBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostHistoryDetailActivity.class);
        intent.putExtra("stationId", bean.getStationId());
        intent.putExtra("date", bean.getTime());
        activity.startActivity(intent);
    }

    public void loadMoreData(int type) {
        if (mCurrentPage < mTotalPage) {
            mCurrentPage++;
            if (type == 0) {
                getAlertRankingList(mDateType);
            } else {
                getStationRankingList(mDateType);
            }
        }
    }

}

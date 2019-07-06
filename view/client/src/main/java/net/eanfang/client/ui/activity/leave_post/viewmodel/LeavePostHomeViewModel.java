package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.LeavePostAlarmRankingActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostCheckListActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostDetailActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostManageListActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeTopBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗检测首页
 */
public class LeavePostHomeViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<LeavePostHomeTopBean> leavePostHomeTopData;
    @Getter
    private MutableLiveData<LeavePostHomeUnHandledAlertBean> leavePostHomeUnhandledAlert;
    private LeavePostRepo mLeavePostHomeRepo;
    private int mCurrentPage = 1;
    private Long mCompanyId;

    public LeavePostHomeViewModel() {
        leavePostHomeTopData = new MutableLiveData<>();
        leavePostHomeUnhandledAlert = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 获取脱岗首页上部分数据
     *
     * @param companyId
     */
    public void getHomeTopData(Long companyId) {
        mLeavePostHomeRepo.homeTopData(companyId).observe(lifecycleOwner, leavePostHomeTopData::setValue);
    }

    /**
     * 获取待处理数据
     *
     * @param companyId
     */
    public void getHomeUnhandledAlert(Long companyId) {
        this.mCompanyId = companyId;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        queryEntry.getEquals().put("companyId", String.valueOf(companyId));
        queryEntry.getEquals().put("status", "0");
        queryEntry.getOrderBy().put("alertTime", "DESC");
        mLeavePostHomeRepo.homeUnhandledAlert(queryEntry).observe(lifecycleOwner, leavePostHomeUnhandledAlert::setValue);
    }

    /**
     * 跳转图像查岗页面
     *
     * @param activity
     */
    public void gotoCheckListPage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostCheckListActivity.class));
    }

    /**
     * 跳转岗位管理页面
     *
     * @param activity
     */
    public void gotoManagePage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostManageListActivity.class));
    }

    /**
     * 跳转报警历史页面
     *
     * @param activity
     */
    public void gotoHistoryPage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostAlarmRankingActivity.class));
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        mCurrentPage++;
        getHomeUnhandledAlert(this.mCompanyId);
    }

    /**
     * 跳转脱岗监测详情页
     *
     * @param activity
     * @param adapter
     * @param position
     */
    public void gotoLeavePostDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostHomeUnHandledAlertBean.UnhandledAlertListBean.ListBean bean = (LeavePostHomeUnHandledAlertBean.UnhandledAlertListBean.ListBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostDetailActivity.class);
        intent.putExtra("alertId", bean.getAlertId());
        activity.startActivity(intent);
    }
}

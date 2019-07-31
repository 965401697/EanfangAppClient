package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.StringUtils;

import net.eanfang.client.ui.activity.leave_post.LeavePostDetailActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostHistoryDetailActivity;
import net.eanfang.client.ui.activity.leave_post.LeavePostScreenActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertHistoryBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.Objects;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-07-03
 * Describe :历史报警记录
 */
public class LeavePostHistoryListViewModel extends BaseViewModel {

    @Getter
    private MutableLiveData<LeavePostAlertHistoryBean> leavePostHistoryListData;
    private LeavePostRepo mLeavePostHomeRepo;
    private int mCurrentPage = 1;
    private Long mCompanyId;
    private int mStationId = -1;
    private String mPlaceName;
    private int mStatus = -1;

    public LeavePostHistoryListViewModel() {
        leavePostHistoryListData = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 获取历史报警记录
     *
     * @param companyId
     */
    public void getHistoryListData(Long companyId) {
        this.mCompanyId = companyId;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        queryEntry.getOrderBy().put("alertTime", "DESC");
        queryEntry.getEquals().put("companyId", String.valueOf(companyId));
        if (mStationId > 0) {
            queryEntry.getEquals().put("stationId", String.valueOf(mStationId));
        }
        if (!StrUtil.isEmpty(mPlaceName)) {
            queryEntry.getEquals().put("placeName", mPlaceName);
        }
        if (mStatus >= 0) {
            queryEntry.getEquals().put("status", String.valueOf(mStatus));
        }
        mLeavePostHomeRepo.alertHistoryList(queryEntry).observe(lifecycleOwner, leavePostHistoryListData::setValue);
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        if (mCurrentPage < Objects.requireNonNull(leavePostHistoryListData.getValue()).getTotalPage()) {
            mCurrentPage++;
            getHistoryListData(this.mCompanyId);
        }
    }

    /**
     * 跳转筛选页面
     * @param activity
     */
    public void gotoScreenPage(Activity activity) {
        activity.startActivityForResult(new Intent(activity, LeavePostScreenActivity.class), 0);
    }

    public void gotoHistoryDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostAlertHistoryBean.ListBean bean = (LeavePostAlertHistoryBean.ListBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostDetailActivity.class);
        intent.putExtra("alertId", bean.getAlertId());
        activity.startActivity(intent);
    }

    public void setResultData(Intent data) {
        mStationId = data.getIntExtra("stationId", -1);
        mPlaceName = data.getStringExtra("placeName");
        mStatus = data.getIntExtra("status", -1);
        mCurrentPage = 1;
        getHistoryListData(this.mCompanyId);
    }
}

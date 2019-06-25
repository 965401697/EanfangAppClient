package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.LeavePostCheckDetailActivity;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceListBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :查岗
 */
public class LeavePostCheckViewModel extends BaseViewModel {
    private int currentPage = 1;
    private Long mCompanyId;
    @Getter
    private MutableLiveData<LeavePostDeviceListBean> leavePostDeviceList;
    private LeavePostRepo mLeavePostHomeRepo;

    public LeavePostCheckViewModel() {
        leavePostDeviceList = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    public void getDeviceListData(Long companyId) {
        mCompanyId = companyId;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", String.valueOf(companyId));
        queryEntry.setPage(currentPage);
        queryEntry.setSize(10);
        mLeavePostHomeRepo.deviceListData(queryEntry).observe(lifecycleOwner, leavePostDeviceList::setValue);
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        currentPage++;
        getDeviceListData(mCompanyId);
    }

    /**
     * 跳转图像查岗详情页
     *
     * @param activity activity
     * @param adapter  adapter
     * @param position 位置
     */
    public void gotoCheckDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostDeviceListBean.ListBean bean = (LeavePostDeviceListBean.ListBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostCheckDetailActivity.class);
        intent.putExtra("stationId", bean.getStationId());
        intent.putExtra("isShowTopContent", true);
        activity.startActivity(intent);
    }
}

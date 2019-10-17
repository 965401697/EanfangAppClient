package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.LeavePostDetailBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.station.StationDetectStationsEntity;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.LeavePostAddPostActivity;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-07-02
 * Describe :岗位管理详情页
 */
public class LeavePostManageDetailViewModel extends BaseViewModel {

    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> leavePostDetailBean;

    private LeavePostRepo mLeavePostRepo;
    private int mCurrentPage = 1;
    private Long mCompanyId;

    public LeavePostManageDetailViewModel() {
        leavePostDetailBean = new MutableLiveData<>();
        mLeavePostRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 获取设备数据
     *
     * @param companyId
     */
    public void getDeviceListData(Long companyId, String areaName) {
        this.mCompanyId = companyId;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("companyId", String.valueOf(companyId));
        queryEntry.getEquals().put("stationPlaceName", areaName);
        queryEntry.setPage(mCurrentPage);
        queryEntry.setSize(10);
        mLeavePostRepo.deviceListData(queryEntry).observe(lifecycleOwner, leavePostDeviceListBean -> {
            ArrayList<LeavePostDetailBean> leavePostDetailBeans = new ArrayList<>();
            for (StationDetectStationsEntity bean : leavePostDeviceListBean.getList()) {
                leavePostDetailBeans.add(bean.getLeavePostDetailBean());
            }
            leavePostDetailBean.setValue(leavePostDetailBeans);
        });
    }

    public void gotoPostDetailPage(Activity activity, BaseQuickAdapter adapter, int position) {
        LeavePostDetailBean bean = (LeavePostDetailBean) adapter.getData().get(position);
        Intent intent = new Intent(activity, LeavePostAddPostActivity.class);
        intent.putExtra("postType", 1);
        intent.putExtra("stationId", bean.getStationId());
        activity.startActivity(intent);
    }
}

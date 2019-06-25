package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.CallUtils;

import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :图片查岗详情
 */
public class LeavePostCheckDetailViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<LeavePostDeviceInfoBean> leavePostDeviceDetail;
    private LeavePostRepo mLeavePostHomeRepo;

    public LeavePostCheckDetailViewModel() {
        leavePostDeviceDetail = new MutableLiveData<>();
        mLeavePostHomeRepo = new LeavePostRepo(new LeavePostDs(this));
    }

    /**
     * 图片查岗页获取数据
     *
     * @param stationId
     */
    public void getDeviceListData(int stationId) {
        mLeavePostHomeRepo.deviceInfoData(String.valueOf(stationId)).observe(lifecycleOwner, leavePostDeviceDetail::setValue);
    }

    /**
     * 获取负责人列表
     *
     * @param alertId
     */
    public void getContactsListData(Long alertId) {
        mLeavePostHomeRepo.contactsList(alertId).observe(lifecycleOwner, leavePostDeviceDetail::setValue);
    }

    public void callToPerson(Context context, BaseQuickAdapter adapter, int position) {
        LeavePostDeviceInfoBean.ChargeStaffListBean bean = (LeavePostDeviceInfoBean.ChargeStaffListBean) adapter.getData().get(position);
        CallUtils.call(context, bean.getMobile());
    }
}

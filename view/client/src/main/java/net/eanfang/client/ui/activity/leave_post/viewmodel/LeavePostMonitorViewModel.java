package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.LeavePostDetailBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.LeavePostMonitorSecondActivity;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗监测首页
 */
public class LeavePostMonitorViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> leavePostDetailData;
    @Getter
    private PageBean<Ys7DevicesEntity> mLeavePostMonitorBean;
    private LeavePostRepo mLeavePostRepo;
    private int mCurrentPage = 1;
    private Ys7DevicesEntity mDeviceEntityBean;
    private int lastPosition = -1;
    private String mDeviceName;

    public LeavePostMonitorViewModel() {
        leavePostDetailData = new MutableLiveData<>();
        mLeavePostRepo = new LeavePostRepo(new LeavePostDs(this));
        mDeviceEntityBean = new Ys7DevicesEntity();
    }

    /**
     * 获取脱岗首页上部分数据
     */
    public void getMonitorList(String deviceName) {
        this.mDeviceName = deviceName;
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getNotEquals().put("status", "2");
        queryEntry.getOrderBy().put("isInUse", "ASC");
        queryEntry.getOrderBy().put("createTime", "DESC");
        if (!StrUtil.isEmpty(deviceName)){
            queryEntry.getLike().put("deviceName", deviceName);
        }
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        mLeavePostRepo.postMonitorData(queryEntry).observe(lifecycleOwner, leavePostMonitorBean -> {
            if (leavePostMonitorBean == null){
                return;
            }
            mLeavePostMonitorBean = leavePostMonitorBean;
            ArrayList<LeavePostDetailBean> list = new ArrayList<>();
            for (Ys7DevicesEntity entity : leavePostMonitorBean.getList()) {
                list.add(LeavePostDetailBean.getLeavePostDetailBean(entity));
            }
            leavePostDetailData.setValue(list);
        });
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        if (mLeavePostMonitorBean != null && mCurrentPage < mLeavePostMonitorBean.getTotalPage()) {
            mCurrentPage++;
            getMonitorList(mDeviceName);
        }
    }

    public void setResult(Activity activity) {
        if (lastPosition != -1) {
            Intent intent = new Intent();
            intent.putExtra("DeviceEntityBean",mDeviceEntityBean);
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        } else {
            showToast("请选择设备");
        }
    }

    /**
     * 设置item选中
     * @param adapter
     * @param view
     * @param position
     */
    public void setItemCheck(BaseQuickAdapter adapter, View view, int position) {
        if (lastPosition == position) {
            return;
        }
        if (lastPosition != -1) {
            LeavePostDetailBean bean = (LeavePostDetailBean) adapter.getData().get(lastPosition);
            bean.setChoosePosition(position);
            adapter.setData(lastPosition,bean);
            adapter.notifyItemChanged(lastPosition);
        }
        LeavePostDetailBean bean = (LeavePostDetailBean) adapter.getData().get(position);
        bean.setChoosePosition(position);
        adapter.setData(position,bean);
        adapter.notifyItemChanged(position);
        lastPosition = position;
        mDeviceEntityBean = bean.getMDeviceEntityBean();
    }

    public void gotoSecondPage(Activity activity) {
        activity.startActivity(new Intent(activity, LeavePostMonitorSecondActivity.class));

    }
}

package net.eanfang.client.ui.activity.leave_post.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import androidx.lifecycle.MutableLiveData;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostMonitorBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;
import net.eanfang.client.ui.activity.leave_post.repo.LeavePostRepo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :脱岗检测首页
 */
public class LeavePostMonitorViewModel extends BaseViewModel {
    @Getter
    private MutableLiveData<List<LeavePostDetailBean>> leavePostDetailData;
    @Getter
    private LeavePostMonitorBean mLeavePostMonitorBean;
    private LeavePostRepo mLeavePostRepo;
    private int mCurrentPage = 1;
    private LeavePostAddPostPostBean.DeviceEntityBean mDeviceEntityBean;
    private int lastPosition = -1;
    private String mDeviceName;

    public LeavePostMonitorViewModel() {
        leavePostDetailData = new MutableLiveData<>();
        mLeavePostRepo = new LeavePostRepo(new LeavePostDs(this));
        mDeviceEntityBean = new LeavePostAddPostPostBean.DeviceEntityBean();
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
        if (!StringUtils.isEmpty(deviceName)){
            queryEntry.getLike().put("deviceName", deviceName);
        }
        queryEntry.setSize(10);
        queryEntry.setPage(mCurrentPage);
        mLeavePostRepo.postMonitorData(queryEntry).observe(lifecycleOwner, leavePostMonitorBean -> {
            mLeavePostMonitorBean = leavePostMonitorBean;
            ArrayList<LeavePostDetailBean> list = new ArrayList<>();
            for (LeavePostMonitorBean.ListBean bean : leavePostMonitorBean.getList()) {
                list.add(bean.getLeavePostDetailBean());
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
            bean.setChoosePosition(lastPosition);
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
}

package net.eanfang.client.viewmodel.device;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorDeviceDetailBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorDeviceDetailTimeAdapter;
import net.eanfang.client.ui.widget.SlidingRuleView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/27
 * @description
 */
public class MonitorDeviceDetailViewModle extends BaseViewModel implements SlidingRuleView.DoGetValueListener {

    private MonitorRepo monitorRepo;
    @Getter
    @Setter
    private ActivityMonitorDeviceDetailBinding monitorDeviceDetailBinding;

    public Long mDeviceId;
    public String mImagePath = null;
    public String mDeviceName = null;
    public String mDeviceGroupName = null;
    public Long mGroupId;
    public Long mCompanyId;
    public String mobile;
    public String mShopName;
    /**
     * 序列号
     */
    public String mDeviceSerial = null;
    private MonitorDeviceDetailTimeAdapter monitorDeviceDetailTimeAdapter;
    private List<String> mTimeList = new ArrayList<>();

    public MonitorDeviceDetailViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    public void initTimeAdapter() {
        monitorDeviceDetailTimeAdapter = new MonitorDeviceDetailTimeAdapter(onItemClickListener);
        LinearLayoutManager manager = new LinearLayoutManager(monitorDeviceDetailBinding.getRoot().getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        monitorDeviceDetailBinding.rvTime.setLayoutManager(manager);
        monitorDeviceDetailTimeAdapter.bindToRecyclerView(monitorDeviceDetailBinding.rvTime);
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                mTimeList.add("0" + i + "时");
            } else {
                mTimeList.add(i + "时");
            }
        }
        monitorDeviceDetailTimeAdapter.setNewData(mTimeList);
    }

    public void init() {
        monitorDeviceDetailBinding.viewTimeLine.setOnScrollListener(this);
        doGetDeviceDetail();
    }

    /**
     * 获取设备详情
     */
    private void doGetDeviceDetail() {
        monitorRepo.doGetDeviceDetail(mDeviceId).observe(lifecycleOwner, deviceDetail -> {
            mImagePath = deviceDetail.getLivePic();
            mDeviceName = deviceDetail.getDeviceName();
            mDeviceGroupName = deviceDetail.getRealTimeGroupEntity().getGroupName();
            mDeviceSerial = deviceDetail.getYs7DeviceSerial();
            mGroupId = deviceDetail.getRealTimeGroupEntity().getGroupId();
            mCompanyId = deviceDetail.getCompanyId();
            mobile = deviceDetail.getOrgUnitEntity().getAccountEntity().getMobile();
            mShopName = deviceDetail.getOrgUnitEntity().getName();

        });
    }

    MonitorDeviceDetailTimeAdapter.OnItemClickListener onItemClickListener = new MonitorDeviceDetailTimeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int posititon, String mTime) {
            Log.e("GG", "time+" + mTime);
        }
    };

    @Override
    public void doGetValue(String mValue) {
        Log.e("GG", "value" + mValue);
    }
}

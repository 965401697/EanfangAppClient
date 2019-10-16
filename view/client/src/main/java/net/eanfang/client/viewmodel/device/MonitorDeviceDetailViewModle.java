package net.eanfang.client.viewmodel.device;

import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorDeviceDetailBinding;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/27
 * @description
 */
public class MonitorDeviceDetailViewModle extends BaseViewModel {

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

    public MonitorDeviceDetailViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }


    public void init() {
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


}

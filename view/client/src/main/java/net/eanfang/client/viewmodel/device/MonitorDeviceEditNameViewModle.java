package net.eanfang.client.viewmodel.device;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorDeviceUpdateNameBinding;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/24
 * @description 修改设备名称
 */
public class MonitorDeviceEditNameViewModle extends BaseViewModel {

    @Getter
    @Setter
    private ActivityMonitorDeviceUpdateNameBinding monitorGroupEditNameBinding;

    private MonitorRepo monitorRepo;
    private MonitorUpdataVo monitorUpdataVo;

    @Getter
    private MutableLiveData<MonitorUpdataVo> editNameLiveData;

    public MonitorDeviceEditNameViewModle() {
        editNameLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
        monitorUpdataVo = new MonitorUpdataVo();
    }

    /**
     * 保存名名称
     */
    public void doSaveDeviceInfo(String mGroupId) {
        String mGroupName = monitorGroupEditNameBinding.etGroupName.getText().toString().trim();
        if (!StrUtil.isEmpty(mGroupName)) {
            monitorUpdataVo.getDeviceId().set(mGroupId);
            monitorUpdataVo.getDeviceName().set(monitorGroupEditNameBinding.etGroupName.getText().toString().trim());
            monitorRepo.doUpdataDeviceName(monitorUpdataVo).observe(lifecycleOwner, updateNameBean -> {
                updateNameBean = new MonitorUpdataVo();
                updateNameBean.getGroupName().set(mGroupName);
                editNameLiveData.setValue(updateNameBean);
            });
        } else {
            showToast("设备名称不能为空");
        }

    }
}

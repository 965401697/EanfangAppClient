package net.eanfang.client.viewmodel.monitor;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorGroupEditNameBinding;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/24
 * @description 修改分组名称
 */
public class MonitorGroupEditNameViewModle extends BaseViewModel {

    @Getter
    @Setter
    private ActivityMonitorGroupEditNameBinding monitorGroupEditNameBinding;

    private MonitorRepo monitorRepo;
    private MonitorUpdataVo monitorUpdataVo;

    @Getter
    private MutableLiveData<MonitorUpdataVo> editNameLiveData;

    public MonitorGroupEditNameViewModle() {
        editNameLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
        monitorUpdataVo = new MonitorUpdataVo();
    }

    /**
     * 保存名名称
     */
    public void doSaveGroupInfo(Long mGroupId) {
        String mGroupName = monitorGroupEditNameBinding.etGroupName.getText().toString().trim();
        if (!StrUtil.isEmpty(mGroupName)) {
            monitorUpdataVo.getGroupId().set(String.valueOf(mGroupId));
            monitorUpdataVo.getGroupName().set(mGroupName);
            monitorRepo.doUpdataGroupInfo(monitorUpdataVo).observe(lifecycleOwner, updateNameBean -> {
                updateNameBean = new MonitorUpdataVo();
                updateNameBean.getGroupName().set(mGroupName);
                editNameLiveData.setValue(updateNameBean);
            });
        } else {
            showToast("分组名称不能为空");
        }

    }
}

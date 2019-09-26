package net.eanfang.client.viewmodel.monitor;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorGroupCreateBinding;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/25
 * @description 创建分组
 */
public class MonitorGroupCreatViewModle extends BaseViewModel {

    @Getter
    @Setter
    private ActivityMonitorGroupCreateBinding monitorGroupCreateBinding;
    private MonitorRepo monitorRepo;

    private MonitorUpdataVo monitorUpdataVo;
    public String mTopGroupId;
    public String mTopGroupName;
    @Getter
    private MutableLiveData<MonitorUpdataVo> monitorUpdataVoMutableLiveData;

    public MonitorGroupCreatViewModle() {
        monitorUpdataVo = new MonitorUpdataVo();
        monitorUpdataVoMutableLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }


    public void doCreateGroup() {
        String mGroupName = monitorGroupCreateBinding.etGroupName.getText().toString().trim();
        if (!StrUtil.isEmpty(mGroupName) && !StrUtil.isEmpty(mTopGroupId)) {
            monitorUpdataVo.getGroupName().set(mGroupName);
            monitorUpdataVo.getParentGroupId().set(mTopGroupId);
            monitorUpdataVo.getParentGroupName().set(mTopGroupName);
            monitorRepo.doCreateGroupInfo(monitorUpdataVo).observe(lifecycleOwner, createSuccess -> {
                monitorUpdataVoMutableLiveData.setValue(createSuccess);
            });
        } else {
            showToast("信息不能为空");
        }

    }
}

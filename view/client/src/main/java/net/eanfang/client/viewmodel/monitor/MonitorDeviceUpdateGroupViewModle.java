package net.eanfang.client.viewmodel.monitor;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.biz.model.bean.monitor.MonitorGroupListBean;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorDeviceUpdateGroupBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorDeviceUpdateGroupAdapter;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/25
 * @description 选择上级分组
 */
public class MonitorDeviceUpdateGroupViewModle extends BaseViewModel {
    @Getter
    @Setter
    private ActivityMonitorDeviceUpdateGroupBinding monitorDeviceUpdateGroupBinding;
    private MonitorRepo monitorRepo;
    private MonitorDeviceUpdateGroupAdapter monitorDeviceUpdateGroupAdapter;

    public String mTopGroupName;
    public Long mTopGroupId;

    private MonitorUpdataVo monitorUpdataVo;
    public String mDeviceId;
    @Getter
    private MutableLiveData<MonitorUpdataVo> editTopGroupLiveData;

    public MonitorDeviceUpdateGroupViewModle() {
        monitorUpdataVo = new MonitorUpdataVo();
        editTopGroupLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    /**
     * 获取分组管理
     */
    public void doGetGroupList(String mCompanyId) {
        monitorRepo.doGetMonitorDeviceUpdateGroupList(mCompanyId).observe(lifecycleOwner, groupMangerBean -> {
            if (groupMangerBean != null) {
                monitorDeviceUpdateGroupBinding.tvCompanyName.setText(groupMangerBean.get(0).getParentGroupName());
                initGroupManagerListLeft(groupMangerBean);
            }
        });
    }

    private void initGroupManagerListLeft(List<MonitorGroupListBean> groupMangerBean) {
        monitorDeviceUpdateGroupAdapter = new MonitorDeviceUpdateGroupAdapter(onFirstlistener);
        monitorDeviceUpdateGroupBinding.rvGroup.setLayoutManager(new LinearLayoutManager(monitorDeviceUpdateGroupBinding.getRoot().getContext()));
        monitorDeviceUpdateGroupAdapter.bindToRecyclerView(monitorDeviceUpdateGroupBinding.rvGroup);
        monitorDeviceUpdateGroupAdapter.setNewData(groupMangerBean);
    }

    /**
     * 分组点击
     */
    MonitorDeviceUpdateGroupAdapter.OnFirstItemClickListener onFirstlistener = new MonitorDeviceUpdateGroupAdapter.OnFirstItemClickListener() {
        @Override
        public void onItemClick(int position, String mGroupName, Long mGroupId) {
            if (!monitorDeviceUpdateGroupAdapter.getData().get(position).isHaveSubGroup()) {
                mTopGroupName = monitorDeviceUpdateGroupAdapter.getData().get(position).getGroupName();
                mTopGroupId = monitorDeviceUpdateGroupAdapter.getData().get(position).getGroupId();
            }
        }
    };

    /**
     * 保存名名称
     */
    public void doSaveTopGroupInfo(Long mGroupId) {
        if (!StrUtil.isEmpty(mTopGroupName)) {
            monitorUpdataVo.getGroupId().set(String.valueOf(mGroupId));
            monitorUpdataVo.getDeviceId().set(mDeviceId);
            monitorRepo.doUpdataDeviceGroup(monitorUpdataVo).observe(lifecycleOwner, updateNameBean -> {
                editTopGroupLiveData.setValue(updateNameBean);
            });
        } else {
            showToast("请选择分组");
        }

    }


}

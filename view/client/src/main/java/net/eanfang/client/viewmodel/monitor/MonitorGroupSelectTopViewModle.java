package net.eanfang.client.viewmodel.monitor;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.biz.model.bean.monitor.RealTimeGroupEntity;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorGroupSelectTopBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorGroupSelectTopAdapter;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/25
 * @description 选择上级分组
 */
public class MonitorGroupSelectTopViewModle extends BaseViewModel {
    @Getter
    @Setter
    private ActivityMonitorGroupSelectTopBinding monitorGroupSelectTopBinding;
    private MonitorRepo monitorRepo;
    private MonitorGroupSelectTopAdapter monitorGroupMangerAdapter;

    public String mTopGroupName;
    public Long mTopGroupId;

    private MonitorUpdataVo monitorUpdataVo;

    @Getter
    private MutableLiveData<MonitorUpdataVo> editTopGroupLiveData;

    public MonitorGroupSelectTopViewModle() {
        monitorUpdataVo = new MonitorUpdataVo();
        editTopGroupLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    /**
     * 获取分组管理
     */
    public void doGetGroupList(String companyId) {
        monitorRepo.doGetGroupManagerList(companyId).observe(lifecycleOwner, groupMangerBean -> {
            if (groupMangerBean != null) {
                initGroupManagerListLeft(groupMangerBean);
            }
        });
    }

    private void initGroupManagerListLeft(List<RealTimeGroupEntity> groupMangerBean) {
        monitorGroupMangerAdapter = new MonitorGroupSelectTopAdapter(onFirstlistener);
        monitorGroupSelectTopBinding.rvGroup.setLayoutManager(new LinearLayoutManager(monitorGroupSelectTopBinding.getRoot().getContext()));
        monitorGroupMangerAdapter.bindToRecyclerView(monitorGroupSelectTopBinding.rvGroup);
        monitorGroupMangerAdapter.setNewData(groupMangerBean);
    }

    /**
     * 分组点击
     */
    MonitorGroupSelectTopAdapter.OnFirstItemClickListener onFirstlistener = new MonitorGroupSelectTopAdapter.OnFirstItemClickListener() {
        @Override
        public void onItemClick(int position, String mGroupName, Long mGroupId) {
            if (monitorGroupMangerAdapter.getData().get(position).isHaveSubGroup()) {
                mTopGroupName = monitorGroupMangerAdapter.getData().get(position).getGroupName();
                mTopGroupId = monitorGroupMangerAdapter.getData().get(position).getGroupId();
            }
        }
    };

    /**
     * 保存名名称
     */
    public void doSaveTopGroupInfo(Long mGroupId) {
        if (!StrUtil.isEmpty(mTopGroupName)) {
            monitorUpdataVo.getParentGroupId().set(String.valueOf(mTopGroupId));
            monitorUpdataVo.getGroupId().set(String.valueOf(mGroupId));
            monitorUpdataVo.getGroupName().set(mTopGroupName);
            monitorRepo.doUpdataGroupInfo(monitorUpdataVo).observe(lifecycleOwner, updateNameBean -> {
                editTopGroupLiveData.setValue(updateNameBean);
            });
        } else {
            showToast("请选择分组");
        }

    }


}

package net.eanfang.client.viewmodel.monitor;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.biz.model.bean.monitor.RealTimeGroupEntity;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;
import com.eanfang.util.JumpItent;

import net.eanfang.client.databinding.ActivityMonitorGroupManagerBinding;
import net.eanfang.client.ui.activity.worksapce.monitor.group.MonitorGroupEditGroupActivity;
import net.eanfang.client.ui.adapter.monitor.MonitorGroupMangerAdapter;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/24
 * @description 分组管理
 */
public class MonitorGroupManagerViewModle extends BaseViewModel {

    @Getter
    @Setter
    private ActivityMonitorGroupManagerBinding groupManagerBinding;
    private MonitorRepo monitorRepo;

    private MonitorGroupMangerAdapter monitorGroupMangerAdapter;

    private String mCompanyId;

    public MonitorGroupManagerViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    /**
     * 获取分组管理
     */
    public void doGetGroupList(String companyId) {
        mCompanyId = companyId;
        monitorRepo.doGetGroupManagerList(companyId).observe(lifecycleOwner, groupMangerBean -> {
            if (groupMangerBean != null) {
                initGroupManagerListLeft(groupMangerBean);
            }
        });
    }

    private void initGroupManagerListLeft(List<RealTimeGroupEntity> groupMangerBean) {
        monitorGroupMangerAdapter = new MonitorGroupMangerAdapter(onFirstlistener);
        groupManagerBinding.rvGroup.setLayoutManager(new LinearLayoutManager(groupManagerBinding.getRoot().getContext()));
        monitorGroupMangerAdapter.bindToRecyclerView(groupManagerBinding.rvGroup);
        monitorGroupMangerAdapter.setNewData(groupMangerBean);
    }

    /**
     * 分组点击 获取右边设备
     */
    MonitorGroupMangerAdapter.OnFirstItemClickListener onFirstlistener = new MonitorGroupMangerAdapter.OnFirstItemClickListener() {
        @Override
        public void onItemClick(int position, String mDeviceName) {
//            if (!monitorGroupMangerAdapter.getData().get(position).isHaveSubGroup()) {
            /**
             * 没有groupid 的是临时分组
             * */
            if (monitorGroupMangerAdapter.getData().get(position).getGroupId() != null) {
                Bundle bundle = new Bundle();
                bundle.putString("groupName", monitorGroupMangerAdapter.getData().get(position).getGroupName());
                bundle.putLong("groupId", monitorGroupMangerAdapter.getData().get(position).getGroupId());
                bundle.putString("topGroupName", monitorGroupMangerAdapter.getData().get(position).getParentGroupName());
                bundle.putInt("deviceCount", monitorGroupMangerAdapter.getData().get(position).getDeviceCount());
                bundle.putLong("configId", monitorGroupMangerAdapter.getData().get(position).getConfigId());
                bundle.putString("mCompanyId", mCompanyId);
                if (monitorGroupMangerAdapter.getData().get(position).isHaveSubGroup()) {
                    if (!CollectionUtil.isEmpty(monitorGroupMangerAdapter.getData().get(position).getSubGroupList()) && monitorGroupMangerAdapter.getData().get(position).getSubGroupList().size() > 0) {
                        bundle.putBoolean("isHaveSubGroup", true);
                    } else {
                        bundle.putBoolean("isHaveSubGroup", false);
                    }
                    if (!CollectionUtil.isEmpty(monitorGroupMangerAdapter.getData().get(position).getDeviceList()) && monitorGroupMangerAdapter.getData().get(position).getDeviceList().size() > 0) {
                        bundle.putBoolean("isHaveDeviceList", true);
                    } else {
                        bundle.putBoolean("isHaveDeviceList", false);
                    }
                } else {
                    bundle.putBoolean("isSecond", true);
                }
                JumpItent.jump((Activity) groupManagerBinding.getRoot().getContext(), MonitorGroupEditGroupActivity.class, bundle);
            }
//            }
        }
    };

}

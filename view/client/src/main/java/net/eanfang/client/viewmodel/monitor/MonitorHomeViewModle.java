package net.eanfang.client.viewmodel.monitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.bean.monitor.MonitorGroupListBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;
import com.eanfang.util.JumpItent;

import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMonitorListBinding;
import net.eanfang.client.ui.activity.worksapce.monitor.device.MonitorDeviceDetailActivity;
import net.eanfang.client.ui.activity.worksapce.monitor.device.MonitorDeviceManagerActivity;
import net.eanfang.client.ui.adapter.monitor.MonitorListLeftAdapter;
import net.eanfang.client.ui.adapter.monitor.MonitorListRightAdapter;
import net.eanfang.client.ui.widget.MonitorSelectCompanyView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/9
 * @description
 */
public class MonitorHomeViewModle extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private MonitorListRightAdapter monitorListRightAdapter;
    private MonitorListLeftAdapter monitorListLeftAdapter;
    private MonitorRepo monitorRepo;

    @Getter
    @Setter
    private ActivityMonitorListBinding monitorListBinding;

    private List<MonitorGroupListBean> monitorGroupList = new ArrayList<>();

    /***
     *分组id
     */
    public Long mLeftGroupId;
    public QueryEntry mDeviceQueryEntry;
    public int mDevicePage = 1;

    /**
     * 切换公司 pop
     */
    private MonitorSelectCompanyView selectCompanyPop;
    public String mChangeCompanyId;
    public String mChangeCompanyName;

    public MonitorHomeViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    /**
     * 选择公司
     */
    public void doSelectCompany() {
        String accId = String.valueOf(ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getAccId());
        monitorRepo.doSelectCompany(accId).observe(lifecycleOwner, companyBean -> {
            selectCompanyPop = new MonitorSelectCompanyView((Activity) monitorListBinding.getRoot().getContext(), companyBean, ((name, companyId) -> {
                monitorListBinding.tvCompanyName.setText(name);
                monitorListBinding.tvCompanyNameTwo.setText(name);
                mChangeCompanyName = name;
                mChangeCompanyId = String.valueOf(companyId);
                doGetMonitorList(mChangeCompanyId);
                selectCompanyPop.dismiss();
            }));
            selectCompanyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    selectCompanyPop.backgroundAlpha(1.0f);
                }
            });
            selectCompanyPop.showAsDropDown(monitorListBinding.tvCompanyName);
        });
    }

    /**
     * 初始化
     */
    private void initMonitorListLeft(List<MonitorGroupListBean> groupList) {
        monitorListLeftAdapter = new MonitorListLeftAdapter(onFirstlistener);
        monitorListBinding.rvLeftGroup.setLayoutManager(new LinearLayoutManager(monitorListBinding.getRoot().getContext()));
        monitorListLeftAdapter.bindToRecyclerView(monitorListBinding.rvLeftGroup);
        monitorListLeftAdapter.setNewData(groupList);

        monitorListRightAdapter = new MonitorListRightAdapter();
        monitorListBinding.rvVideoList.setLayoutManager(new LinearLayoutManager(monitorListBinding.getRoot().getContext()));
        monitorListRightAdapter.bindToRecyclerView(monitorListBinding.rvVideoList);
        monitorListRightAdapter.setOnLoadMoreListener(this, monitorListBinding.rvVideoList);
        monitorListBinding.swipreFresh.setOnRefreshListener(this);
        monitorListRightAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("deviceSerial", monitorListRightAdapter.getData().get(position).getYs7DeviceSerial());
            bundle.putString("mDeviceName", monitorListRightAdapter.getData().get(position).getDeviceName());
            bundle.putString("mChangeCompanyId", mChangeCompanyId);
            bundle.putLong("mDeviceId", monitorListRightAdapter.getData().get(position).getDeviceId() != null ? monitorListRightAdapter.getData().get(position).getDeviceId() : 0);
            bundle.putLong("mLeftGroupId", mLeftGroupId != null ? mLeftGroupId : 0);
            bundle.putInt("position", position);
            bundle.putSerializable("deviceList", (Serializable) monitorListRightAdapter.getData());
            JumpItent.jump((Activity) monitorListBinding.getRoot().getContext(), MonitorDeviceDetailActivity.class, bundle);
        }));
        monitorListRightAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("imagePath", monitorListRightAdapter.getData().get(position).getLivePic());
            bundle.putString("deviceName", monitorListRightAdapter.getData().get(position).getDeviceName());
            bundle.putString("deviceSerial", monitorListRightAdapter.getData().get(position).getYs7DeviceSerial());
            bundle.putString("deviceId", String.valueOf(monitorListRightAdapter.getData().get(position).getDeviceId()));
            bundle.putString("companyId", String.valueOf(monitorListRightAdapter.getData().get(position).getCompanyId()));
            if (monitorListRightAdapter.getData().get(position).getRealTimeGroupEntity() != null) {
                bundle.putString("deviceGroupName", monitorListRightAdapter.getData().get(position).getRealTimeGroupEntity().getGroupName());
                bundle.putString("groupId", String.valueOf(monitorListRightAdapter.getData().get(position).getRealTimeGroupEntity().getGroupId()));
            }
            JumpItent.jump((Activity) monitorListBinding.getRoot().getContext(), MonitorDeviceManagerActivity.class, bundle);
        }));

        ThreadUtil.sleep(1000);
        for (MonitorGroupListBean monitorGroupListBean : monitorGroupList) {
            if (monitorGroupListBean.isChecked()) {
                mLeftGroupId = monitorGroupListBean.getGroupId();
                doGetRightDeviceList(mDevicePage, monitorGroupListBean.getGroupId());
            }
        }
    }

    /**
     * 获取左边分组列表
     */
    public void doGetMonitorList(String mCompanyId) {
        mChangeCompanyId = mCompanyId;
        monitorRepo.doGetMonitorList(mCompanyId).observe(lifecycleOwner, monitorBean -> {
            if (monitorBean != null) {
                monitorGroupList = monitorBean;
                initMonitorListLeft(monitorGroupList);
            }
        });
    }

    /**
     * 分组点击 获取右边设备
     */
    MonitorListLeftAdapter.OnFirstItemClickListener onFirstlistener = new MonitorListLeftAdapter.OnFirstItemClickListener() {
        @Override
        public void onItemClick(int position, String mDeviceName) {
            if (!monitorListLeftAdapter.getData().get(position).isHaveSubGroup() || monitorListLeftAdapter.getData().get(position).isFirstHaveDevice()) {
                mLeftGroupId = monitorListLeftAdapter.getData().get(position).getGroupId();
                doGetRightDeviceList(mDevicePage, mLeftGroupId);
            }
        }
    };

    /**
     * 获取设备 数据
     */
    public void doGetRightDeviceList(int page, Long mLeftGroupId) {
        if (mDeviceQueryEntry == null) {
            mDeviceQueryEntry = new QueryEntry();
        }
        if (mLeftGroupId != null && !StrUtil.isEmpty(String.valueOf(mLeftGroupId))) {
            mDeviceQueryEntry.getEquals().put("groupId", String.valueOf(mLeftGroupId));
        }
        mDeviceQueryEntry.getEquals().put("companyId", mChangeCompanyId);
        mDeviceQueryEntry.setPage(page);
        monitorRepo.doGetMonitorDeviceList(mDeviceQueryEntry).observe(lifecycleOwner, monitorDeviceBean -> {
            if (monitorDeviceBean != null && !CollectionUtil.isEmpty(monitorDeviceBean.getList())) {
                if (mDevicePage == 1) {
                    monitorListRightAdapter.getData().clear();
                    monitorListRightAdapter.setNewData(monitorDeviceBean.getList());
                    monitorListBinding.swipreFresh.setRefreshing(false);
                    monitorListRightAdapter.loadMoreComplete();
                    if (monitorDeviceBean.getList().size() < 10) {
                        monitorListRightAdapter.loadMoreEnd();
                        mDeviceQueryEntry = null;
                    }
                    if (monitorDeviceBean.getList().size() > 0) {
                        monitorListBinding.tvNoData.setVisibility(View.GONE);
                    } else {
                        monitorListBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    monitorListRightAdapter.addData(monitorDeviceBean.getList());
                    monitorListRightAdapter.loadMoreComplete();
                    if (monitorDeviceBean.getList().size() < 10) {
                        monitorListRightAdapter.loadMoreEnd();
                    }
                }
            } else {
                mDeviceQueryEntry = null;
                monitorListBinding.tvNoData.setVisibility(View.VISIBLE);
                monitorListBinding.swipreFresh.setRefreshing(false);
            }
        });
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onRefresh() {
        mDevicePage = 1;
        doGetRightDeviceList(mDevicePage, mLeftGroupId);
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onLoadMoreRequested() {
        mDevicePage++;
        doGetRightDeviceList(mDevicePage, mLeftGroupId);
    }

}

package net.eanfang.client.viewmodel.monitor;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.model.vo.MonitorDeleteVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityMonitorGroupEditDeviceBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorDeleteDevicetAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/25
 * @description 删除设备
 */
public class MonitorGroupEditDeviceViewModle extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Getter
    @Setter
    private ActivityMonitorGroupEditDeviceBinding monitorGroupEditDeviceBinding;

    private MonitorDeleteDevicetAdapter monitorDeleteDevicetAdapter;
    private MonitorRepo monitorRepo;
    /***
     *分组id
     */
    public QueryEntry mDeviceQueryEntry;
    public int mDevicePage = 1;
    public String mGroupId;
    public String mConfigId;
    public String mCompanyId;

    private MonitorDeleteVo monitorDeleteVo;
    private List<Ys7DevicesEntity> deviceList = new ArrayList<>();
    @Getter
    private MutableLiveData<MonitorDeleteVo> monitorDeleteVoMutableLiveData;
    @Getter
    private MutableLiveData<PageBean<Ys7DevicesEntity>> monitorDeviceMutableLiveData;

    public MonitorGroupEditDeviceViewModle() {
        monitorDeleteVo = new MonitorDeleteVo();
        monitorDeleteVoMutableLiveData = new MutableLiveData<>();
        monitorDeviceMutableLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));

    }

    public void initAdapter() {
        monitorDeleteDevicetAdapter = new MonitorDeleteDevicetAdapter();
        monitorGroupEditDeviceBinding.rvVideoList.setLayoutManager(new LinearLayoutManager(monitorGroupEditDeviceBinding.getRoot().getContext()));
        monitorDeleteDevicetAdapter.bindToRecyclerView(monitorGroupEditDeviceBinding.rvVideoList);
        monitorDeleteDevicetAdapter.setOnLoadMoreListener(this, monitorGroupEditDeviceBinding.rvVideoList);
        monitorGroupEditDeviceBinding.swipreFresh.setOnRefreshListener(this);

        monitorDeleteDevicetAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
            if (view.getId() == R.id.iv_delete) {
                Ys7DevicesEntity ys7DevicesEntity = new Ys7DevicesEntity();
                ys7DevicesEntity.setDeviceId(monitorDeleteDevicetAdapter.getData().get(position).getDeviceId());
                ys7DevicesEntity.setFunctionInUse(monitorDeleteDevicetAdapter.getData().get(position).getFunctionInUse());
                deviceList.add(ys7DevicesEntity);
                monitorDeleteDevicetAdapter.remove(position);
                monitorDeleteDevicetAdapter.notifyDataSetChanged();
            }
        }));
    }

    /**
     * 获取设备 数据
     */
    public void doGetRightDeviceList(int page) {
        if (mDeviceQueryEntry == null) {
            mDeviceQueryEntry = new QueryEntry();
        }
        if (mGroupId != null && !StrUtil.isEmpty(mGroupId)) {
            mDeviceQueryEntry.getEquals().put("groupId", mGroupId);
        }
        mDeviceQueryEntry.getEquals().put("companyId", mCompanyId);
        mDeviceQueryEntry.setPage(page);
        monitorRepo.doGetMonitorDeviceList(mDeviceQueryEntry).observe(lifecycleOwner, monitorDeviceBean -> {
            if (monitorDeviceBean != null && !CollectionUtil.isEmpty(monitorDeviceBean.getList())) {
                if (mDevicePage == 1) {
                    monitorDeleteDevicetAdapter.getData().clear();
                    monitorDeleteDevicetAdapter.setNewData(monitorDeviceBean.getList());
                    monitorGroupEditDeviceBinding.swipreFresh.setRefreshing(false);
                    monitorDeleteDevicetAdapter.loadMoreComplete();
                    if (monitorDeviceBean.getList().size() < 10) {
                        monitorDeleteDevicetAdapter.loadMoreEnd();
                    }
                    if (monitorDeviceBean.getList().size() > 0) {
                        monitorGroupEditDeviceBinding.tvNoData.setVisibility(View.GONE);
                    } else {
                        monitorGroupEditDeviceBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    monitorDeleteDevicetAdapter.addData(monitorDeviceBean.getList());
                    monitorDeleteDevicetAdapter.loadMoreComplete();
                    if (monitorDeviceBean.getList().size() < 10) {
                        monitorDeleteDevicetAdapter.loadMoreEnd();
                    }
                }
            } else {
                monitorGroupEditDeviceBinding.swipreFresh.setRefreshing(false);
                monitorDeleteDevicetAdapter.loadMoreEnd();//没有数据了
                if (monitorDeleteDevicetAdapter.getData().size() == 0) {
                    monitorGroupEditDeviceBinding.tvNoData.setVisibility(View.VISIBLE);
                } else {
                    monitorGroupEditDeviceBinding.tvNoData.setVisibility(View.GONE);
                }
            }
            monitorDeviceMutableLiveData.setValue(monitorDeviceBean);
        });
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onRefresh() {
        mDevicePage = 1;
        doGetRightDeviceList(mDevicePage);
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onLoadMoreRequested() {
        mDevicePage++;
        doGetRightDeviceList(mDevicePage);

    }

    /**
     * 保存删除
     */
    public void doSaveDelete() {
        monitorDeleteVo.getConfigId().set(mConfigId);
        monitorDeleteVo.getGroupId().set(mGroupId);
        monitorDeleteVo.getDeviceList().set(deviceList);
        monitorRepo.doDeleteDevice(monitorDeleteVo).observe(lifecycleOwner, deleteSuccess -> {
            monitorDeleteVoMutableLiveData.setValue(deleteSuccess);
        });
    }

}

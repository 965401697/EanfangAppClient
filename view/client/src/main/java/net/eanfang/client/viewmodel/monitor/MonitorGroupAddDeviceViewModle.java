package net.eanfang.client.viewmodel.monitor;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.model.vo.MonitorDeleteVo;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorGroupAddDeviceBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorAddDevicetAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/25
 * @description
 */
public class MonitorGroupAddDeviceViewModle extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Getter
    @Setter
    private ActivityMonitorGroupAddDeviceBinding monitorGroupAddDeviceBinding;
    private MonitorRepo monitorRepo;
    private MonitorAddDevicetAdapter monitorAddDevicetAdapter;
    /***
     *分组id
     */
    public QueryEntry mDeviceQueryEntry;
    public int mDevicePage = 1;
    public String mCompanyId;
    public String mGroupId;

    public String mDeviceName;

    private MonitorDeleteVo monitorDeleteVo;

    private List<Ys7DevicesEntity> mAddDeviceList = new ArrayList<>();

    @Getter
    @Setter
    private MutableLiveData<MonitorDeleteVo> monitorDeleteVoMutableLiveData;

    public MonitorGroupAddDeviceViewModle() {
        monitorDeleteVo = new MonitorDeleteVo();
        monitorDeleteVoMutableLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    public void initAdapter() {
        monitorAddDevicetAdapter = new MonitorAddDevicetAdapter(onAddListener);
        monitorGroupAddDeviceBinding.rvVideoList.setLayoutManager(new LinearLayoutManager(monitorGroupAddDeviceBinding.getRoot().getContext()));
        monitorAddDevicetAdapter.bindToRecyclerView(monitorGroupAddDeviceBinding.rvVideoList);
        monitorAddDevicetAdapter.setOnLoadMoreListener(this, monitorGroupAddDeviceBinding.rvVideoList);
        monitorGroupAddDeviceBinding.swipreFresh.setOnRefreshListener(this);

    }

    /**
     * 分组点击
     */
    MonitorAddDevicetAdapter.OnAddDeviceItemClickListener onAddListener = new MonitorAddDevicetAdapter.OnAddDeviceItemClickListener() {
        @Override
        public void onItemClick(int position, List<Ys7DevicesEntity> addDeviceList) {
            mAddDeviceList.addAll(addDeviceList);
        }
    };

    public void doGetDeviceList(int page, String companyName) {
        if (mDeviceQueryEntry == null) {
            mDeviceQueryEntry = new QueryEntry();
        }
        if (!StrUtil.isEmpty(companyName)) {
            mDeviceQueryEntry.getLike().put("deviceName", companyName);
        }
        mDeviceQueryEntry.getEquals().put("companyId", mCompanyId);
        mDeviceQueryEntry.setPage(page);
        monitorRepo.doGetAddDeviceList(mDeviceQueryEntry).observe(lifecycleOwner, addDeviceListBean -> {
            if (addDeviceListBean != null && !CollectionUtil.isEmpty(addDeviceListBean.getList())) {
                if (mDevicePage == 1) {
                    monitorAddDevicetAdapter.getData().clear();
                    monitorAddDevicetAdapter.setNewData(addDeviceListBean.getList());
                    monitorGroupAddDeviceBinding.swipreFresh.setRefreshing(false);
                    monitorAddDevicetAdapter.loadMoreComplete();
                    if (addDeviceListBean.getList().size() < 10) {
                        monitorAddDevicetAdapter.loadMoreEnd();
                        mDeviceQueryEntry = null;
                    }
                    if (addDeviceListBean.getList().size() > 0) {
                        monitorGroupAddDeviceBinding.tvNoData.setVisibility(View.GONE);
                    } else {
                        monitorGroupAddDeviceBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    monitorAddDevicetAdapter.addData(addDeviceListBean.getList());
                    monitorAddDevicetAdapter.loadMoreComplete();
                    if (addDeviceListBean.getList().size() < 10) {
                        monitorAddDevicetAdapter.loadMoreEnd();
                    }
                }
            } else {
                monitorGroupAddDeviceBinding.swipreFresh.setRefreshing(false);
                monitorAddDevicetAdapter.loadMoreEnd();//没有数据了
                if (monitorAddDevicetAdapter.getData().size() == 0) {
                    monitorGroupAddDeviceBinding.tvNoData.setVisibility(View.VISIBLE);
                } else {
                    monitorGroupAddDeviceBinding.tvNoData.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onRefresh() {
        mDevicePage = 1;
        doGetDeviceList(mDevicePage, mDeviceName);
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onLoadMoreRequested() {
        mDevicePage++;
        doGetDeviceList(mDevicePage, mDeviceName);
    }

    /**
     * 添加设备
     */
    public void doAddDevice() {
        monitorDeleteVo.getGroupId().set(mGroupId);
        monitorDeleteVo.getDeviceList().set(mAddDeviceList);
        monitorRepo.doAddDevice(monitorDeleteVo).observe(lifecycleOwner, addSuccessful -> {
            monitorDeleteVoMutableLiveData.setValue(addSuccessful);
        });
    }
}

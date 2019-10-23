package net.eanfang.client.viewmodel.device;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorDeviceListBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorSearchAdapter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/10/11
 * @description 设备管理
 */
public class MonitorDeviceListViewModle extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private MonitorRepo monitorRepo;

    @Getter
    @Setter
    private ActivityMonitorDeviceListBinding monitorDeviceListBinding;


    private MonitorSearchAdapter monitorSearchAdapter;
    /**
     * 搜索设备
     */
    public QueryEntry mSearchDeviceQueryEntry;
    public int mSearchDevicePage = 1;

    private String mDeviceName;

    public String mChangeCompanyId;

    public Long mGroupId;

    public MonitorDeviceListViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    public void initAdapter() {
        monitorSearchAdapter = new MonitorSearchAdapter();
        monitorDeviceListBinding.rvDeviceList.setLayoutManager(new LinearLayoutManager(monitorDeviceListBinding.getRoot().getContext()));
        monitorSearchAdapter.bindToRecyclerView(monitorDeviceListBinding.rvDeviceList);
        monitorSearchAdapter.setOnLoadMoreListener(this, monitorDeviceListBinding.rvDeviceList);
        monitorDeviceListBinding.swipreFresh.setOnRefreshListener(this);
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onRefresh() {
        mSearchDevicePage = 1;
        doSearchDevice(mDeviceName);
    }

    /**
     * 设备刷新和加载
     */
    @Override
    public void onLoadMoreRequested() {
        mSearchDevicePage = 1;
        doSearchDevice(mDeviceName);
    }

    public void doSearchDevice(String deviceName) {
        mDeviceName = deviceName;
        if (mSearchDeviceQueryEntry == null) {
            mSearchDeviceQueryEntry = new QueryEntry();
        }
        if (mGroupId != null && mGroupId != 0 && !StrUtil.isEmpty(String.valueOf(mGroupId))) {
            mSearchDeviceQueryEntry.getEquals().put("groupId", String.valueOf(mGroupId));
        }
        mSearchDeviceQueryEntry.getEquals().put("companyId", mChangeCompanyId);

        mSearchDeviceQueryEntry.getLike().put("deviceName", mDeviceName);
        mSearchDeviceQueryEntry.setPage(mSearchDevicePage);
        monitorRepo.doGetMonitorDeviceList(mSearchDeviceQueryEntry).observe(lifecycleOwner, monitorDeviceBean -> {
            if (monitorDeviceBean != null && !CollectionUtil.isEmpty(monitorDeviceBean.getList())) {
                if (mSearchDevicePage == 1) {
                    monitorSearchAdapter.getData().clear();
                    monitorSearchAdapter.setNewData(monitorDeviceBean.getList());
                    monitorDeviceListBinding.swipreFresh.setRefreshing(false);
                    monitorSearchAdapter.loadMoreComplete();
                    monitorDeviceListBinding.tvSerarchNum.setText("为您搜到" + monitorDeviceBean.getList().size() + "个结果");
                    hideKeyboard();
                    if (monitorDeviceBean.getList().size() < 10) {
                        monitorSearchAdapter.loadMoreEnd();
                        mSearchDeviceQueryEntry = null;
                    }
                    if (monitorDeviceBean.getList().size() > 0) {
                        monitorDeviceListBinding.tvNoData.setVisibility(View.GONE);
                    } else {
                        monitorDeviceListBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    monitorSearchAdapter.addData(monitorDeviceBean.getList());
                    monitorSearchAdapter.loadMoreComplete();
                    if (monitorDeviceBean.getList().size() < 10) {
                        monitorSearchAdapter.loadMoreEnd();
                    }
                }
            } else {
                monitorDeviceListBinding.swipreFresh.setRefreshing(false);
                monitorSearchAdapter.loadMoreEnd();//没有数据了
                if (monitorSearchAdapter.getData().size() == 0) {
                    monitorDeviceListBinding.tvNoData.setVisibility(View.VISIBLE);
                } else {
                    monitorDeviceListBinding.tvNoData.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        //隐藏布局
        View view = ((Activity) monitorDeviceListBinding.getRoot().getContext()).getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) monitorDeviceListBinding.getRoot().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

package net.eanfang.client.viewmodel.monitor;

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

import net.eanfang.client.databinding.ActivityMonitorSearchBinding;
import net.eanfang.client.ui.adapter.monitor.MonitorSearchAdapter;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/23
 * @description
 */
public class MonitorSearchViewModle extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private MonitorRepo monitorRepo;
    @Getter
    @Setter
    private ActivityMonitorSearchBinding monitorSearchBinding;
    private MonitorSearchAdapter monitorSearchAdapter;
    /**
     * 搜索设备
     */
    public QueryEntry mSearchDeviceQueryEntry;
    public int mSearchDevicePage = 1;
    private String mDeviceName;

    public String mChangeCompanyId;

    public MonitorSearchViewModle() {
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }

    public void initAdapter() {
        monitorSearchAdapter = new MonitorSearchAdapter();
        monitorSearchBinding.rvDeviceList.setLayoutManager(new LinearLayoutManager(monitorSearchBinding.getRoot().getContext()));
        monitorSearchAdapter.bindToRecyclerView(monitorSearchBinding.rvDeviceList);
        monitorSearchAdapter.setOnLoadMoreListener(this, monitorSearchBinding.rvDeviceList);
        monitorSearchBinding.swipreFresh.setOnRefreshListener(this);
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

    /**
     * 实时监控搜索设备
     */
    public void doSearchDevice(String deviceName) {
        mDeviceName = deviceName;
        if (mSearchDeviceQueryEntry == null) {
            mSearchDeviceQueryEntry = new QueryEntry();
        }
        mSearchDeviceQueryEntry.getEquals().put("companyId", mChangeCompanyId);
        mSearchDeviceQueryEntry.setPage(mSearchDevicePage);
        mSearchDeviceQueryEntry.getLike().put("deviceName", mDeviceName);
        monitorRepo.doSearchDevice(mSearchDeviceQueryEntry).observe(lifecycleOwner, searchDeviceBean -> {
            if (searchDeviceBean != null && !CollectionUtil.isEmpty(searchDeviceBean.getList())) {
                if (mSearchDevicePage == 1) {
                    monitorSearchAdapter.getData().clear();
                    monitorSearchAdapter.setNewData(searchDeviceBean.getList());
                    monitorSearchBinding.swipreFresh.setRefreshing(false);
                    monitorSearchAdapter.loadMoreComplete();
                    hideKeyboard();
                    if (searchDeviceBean.getList().size() < 10) {
                        monitorSearchAdapter.loadMoreEnd();
                    }
                    if (searchDeviceBean.getList().size() > 0) {
                        monitorSearchBinding.tvNoData.setVisibility(View.GONE);
                    } else {
                        monitorSearchBinding.tvNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    monitorSearchAdapter.addData(searchDeviceBean.getList());
                    monitorSearchAdapter.loadMoreComplete();
                    if (searchDeviceBean.getList().size() < 10) {
                        monitorSearchAdapter.loadMoreEnd();
                    }
                }
            } else {
                monitorSearchBinding.swipreFresh.setRefreshing(false);
            }
        });
    }

    /**
     * 隐藏键盘与布局
     */
    private void hideKeyboard() {
        //隐藏布局
        View view = ((Activity) monitorSearchBinding.getRoot().getContext()).getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) monitorSearchBinding.getRoot().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.bean.monitor.MonitorListBean;
import com.eanfang.biz.model.bean.monitor.RealTimeGroupEntity;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.model.vo.MonitorDeleteVo;
import com.eanfang.biz.model.vo.MonitorReportVo;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.MonitorApi;
import com.eanfang.biz.rds.sys.ds.IMonitorDs;

import org.json.JSONObject;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/9/18
 * @description
 */
public class MonitorDs extends BaseRemoteDataSource implements IMonitorDs {

    public MonitorDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    /**
     * 实时监控首页
     */
    @Override
    public void getMonitorList(String mCompanyId, RequestCallback<MonitorListBean> callback) {
        execute(getService(MonitorApi.class).getMonitorList(mCompanyId), callback);
    }

    /**
     * 实时监控首页 获取设备
     */
    @Override
    public void getMonitorDeviceList(QueryEntry queryEntry, RequestCallback<PageBean<Ys7DevicesEntity>> callback) {
        execute(getService(MonitorApi.class).getMonitorDeviceList(queryEntry), callback);
    }

    /**
     * 实时监控首页 选择公司
     */
    @Override
    public void getMonitorCompanyList(String accId, RequestCallback<List<OrgEntity>> callback) {
        execute(getService(MonitorApi.class).getMonitorCompanyList(accId), callback);
    }

    /**
     * 搜索设备
     */
    @Override
    public void doSearchDeviceList(QueryEntry queryEntry, RequestCallback<PageBean<Ys7DevicesEntity>> callback) {
        execute(getService(MonitorApi.class).getMonitorSearchDeviceList(queryEntry), callback);
    }

    /**
     * 分组管理页面
     */
    @Override
    public void doGetGroupList(String mCompanyId, RequestCallback<List<RealTimeGroupEntity>> callback) {
        execute(getService(MonitorApi.class).getMonitorGroupManagerList(mCompanyId), callback);
    }

    /**
     * 删除分组
     */
    @Override
    public void doDeleteGroup(String mGroupId, RequestCallback<JSONObject> callback) {
        execute(getService(MonitorApi.class).doDeleteGroup(mGroupId), callback);
    }

    /**
     * 修改分组信息
     */
    @Override
    public void doUpdateGroupInfo(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback) {
        execute(getService(MonitorApi.class).doUpdateGroupInfo(monitorUpdataVo), callback);
    }

    /**
     * 创建分组
     */
    @Override
    public void doCreateGroupInfo(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback) {
        execute(getService(MonitorApi.class).doCreateGroupInfo(monitorUpdataVo), callback);
    }

    /**
     * 删除设备
     */
    @Override
    public void doDeleteDevice(MonitorDeleteVo monitorDeleteVo, RequestCallback<MonitorDeleteVo> callback) {
        execute(getService(MonitorApi.class).doDeleteDevice(monitorDeleteVo), callback);
    }

    /**
     * 增加设备
     */
    @Override
    public void doAddDevice(MonitorDeleteVo monitorDeleteVo, RequestCallback<MonitorDeleteVo> callback) {
        execute(getService(MonitorApi.class).doAddDevice(monitorDeleteVo), callback);
    }

    /**
     * 获取增加设备
     */
    @Override
    public void doGetAddDeviceList(QueryEntry queryEntry, RequestCallback<PageBean<Ys7DevicesEntity>> callback) {
        execute(getService(MonitorApi.class).doGetAddDeviceList(queryEntry), callback);
    }

    /**
     * 获取设备详情
     *
     * @param deviceId
     * @param callback
     */
    @Override
    public void doGetDeviceDetail(Long deviceId, RequestCallback<Ys7DevicesEntity> callback) {
        execute(getService(MonitorApi.class).doGetDeviceDetail(deviceId), callback);
    }

    /**
     * 修改设备名称
     *
     * @param monitorUpdataVo
     * @param callback
     */

    @Override
    public void doUpdateDeviceName(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback) {
        execute(getService(MonitorApi.class).doUpdateDeviceName(monitorUpdataVo), callback);
    }

    /**
     * 修改设备分组
     *
     * @param monitorUpdataVo
     * @param callback
     */
    @Override
    public void doUpdateDeviceGroup(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback) {
        execute(getService(MonitorApi.class).doUpdateDeviceGroup(monitorUpdataVo), callback);
    }

    /**
     * 获取设备分组信息
     *
     * @param mGroupId
     * @param callback
     */
    @Override
    public void doGetDeviceInfo(String mGroupId, RequestCallback<RealTimeGroupEntity> callback) {
        execute(getService(MonitorApi.class).doGetDeviceInfo(mGroupId), callback);
    }

    /**
     * 生成汇报
     */
    @Override
    public void doCreateReport(MonitorReportVo monitorReportVo, RequestCallback<MonitorReportVo> callback) {
        execute(getService(MonitorApi.class).doCreateReport(monitorReportVo), callback);
    }

}

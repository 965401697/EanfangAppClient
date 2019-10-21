package com.eanfang.biz.rds.sys.ds;

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

import org.json.JSONObject;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/9/18
 * @description
 */
public interface IMonitorDs {
    /**
     * 实时监控首页
     *
     * @param mCompanyId
     * @param callback
     */
    void getMonitorList(String mCompanyId, RequestCallback<MonitorListBean> callback);

    /**
     * 实时监控首页 获取设备
     *
     * @param queryEntry
     * @param callback
     */
    void getMonitorDeviceList(QueryEntry queryEntry, RequestCallback<PageBean<Ys7DevicesEntity>> callback);

    /**
     * 实时监控首页 选择公司
     *
     * @param accId
     * @param callback
     */
    void getMonitorCompanyList(String accId, RequestCallback<List<OrgEntity>> callback);

    /**
     * 搜索设备
     *
     * @param queryEntry
     * @param callback
     */
    void doSearchDeviceList(QueryEntry queryEntry, RequestCallback<PageBean<Ys7DevicesEntity>> callback);

    /**
     * 分组管理页面
     *
     * @param mCompanyId
     * @param callback
     */
    void doGetGroupList(String mCompanyId, RequestCallback<List<RealTimeGroupEntity>> callback);

    /**
     * 删除分组
     *
     * @param mGroupId
     * @param callback
     */
    void doDeleteGroup(String mGroupId, RequestCallback<JSONObject> callback);

    /**
     * 修改分组信息
     *
     * @param monitorUpdataVo
     * @param callback
     */
    void doUpdateGroupInfo(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback);

    /**
     * 创建分组
     *
     * @param monitorUpdataVo
     * @param callback
     */
    void doCreateGroupInfo(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback);

    /**
     * 删除设备
     */
    void doDeleteDevice(MonitorDeleteVo monitorDeleteVo, RequestCallback<MonitorDeleteVo> callback);

    /**
     * 增加设备
     */
    void doAddDevice(MonitorDeleteVo monitorDeleteVo, RequestCallback<MonitorDeleteVo> callback);

    /**
     * 获取增加设备
     *
     * @param queryEntry
     * @param callback
     */
    void doGetAddDeviceList(QueryEntry queryEntry, RequestCallback<PageBean<Ys7DevicesEntity>> callback);

    /**
     * 获取设备详情
     *
     * @param deviceId
     * @param callback
     */
    void doGetDeviceDetail(Long deviceId, RequestCallback<Ys7DevicesEntity> callback);

    /**
     * 修改设备名称
     * @param monitorUpdataVo
     * @param callback
     */
    void doUpdateDeviceName(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback);

    /**
     * 修改设备分组
     * @param monitorUpdataVo
     * @param callback
     */
    void doUpdateDeviceGroup(MonitorUpdataVo monitorUpdataVo, RequestCallback<MonitorUpdataVo> callback);

    /**
     * 生成汇报
     * @param monitorReportVo
     * @param callback
     */
    void doCreateReport(MonitorReportVo monitorReportVo,RequestCallback<MonitorReportVo> callback);
}

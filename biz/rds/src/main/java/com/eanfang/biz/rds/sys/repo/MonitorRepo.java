package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.bean.monitor.MonitorGroupListBean;
import com.eanfang.biz.model.bean.monitor.RealTimeGroupEntity;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.model.vo.MonitorDeleteVo;
import com.eanfang.biz.model.vo.MonitorReportVo;
import com.eanfang.biz.model.vo.MonitorUpdataVo;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @author guanluocang
 * @data 2019/9/18
 * @description
 */
public class MonitorRepo extends BaseRepo<MonitorDs> {

    public MonitorRepo(MonitorDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 实时监控首页
     */
    public MutableLiveData<List<MonitorGroupListBean>> doGetMonitorList(String companyId) {
        MutableLiveData<List<MonitorGroupListBean>> monitorListMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getMonitorList(companyId, (val) -> {
            List<MonitorGroupListBean> groupList = new ArrayList<>();
            for (int j = 0; j < val.getGroupList().size(); j++) {
                MonitorGroupListBean firstGroup = new MonitorGroupListBean();
                firstGroup.setParentGroupName(val.getOrgUnit().getName());
                firstGroup.setGroupId(val.getGroupList().get(j).getGroupId());
                firstGroup.setGroupName(val.getGroupList().get(j).getGroupName());
                /**
                 * 有二级分组
                 * */
                if (!CollectionUtil.isEmpty(val.getGroupList().get(j).getSubGroupList())) {
                    firstGroup.setHaveSubGroup(true);
                    groupList.add(firstGroup);
                    for (int i = 0; i < val.getGroupList().get(j).getSubGroupList().size(); i++) {
                        MonitorGroupListBean secondGroup = new MonitorGroupListBean();
                        if (i == 0 && j == 0) {
                            secondGroup.setChecked(true);
                        }
                        secondGroup.setGroupId(val.getGroupList().get(j).getSubGroupList().get(i).getGroupId());
                        secondGroup.setGroupName(val.getGroupList().get(j).getSubGroupList().get(i).getGroupName());
                        groupList.add(secondGroup);
                    }
                } else {
                    /**
                     * 没有二级分组
                     *
                     * 判断有无devicelist  有说明当前分组下存在设备
                     * */
                    if (j == 0) {
                        firstGroup.setChecked(true);
                    }
                    firstGroup.setHaveSubGroup(true);
                    firstGroup.setFirstHaveDevice(true);
                    groupList.add(firstGroup);
                }
            }
            monitorListMutableLiveData.setValue(groupList);
        });
        return monitorListMutableLiveData;
    }

    /**
     * 实时监控首页
     */
    public MutableLiveData<List<MonitorGroupListBean>> doGetMonitorDeviceUpdateGroupList(String companyId) {
        MutableLiveData<List<MonitorGroupListBean>> monitorListMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getMonitorList(companyId, (val) -> {
            List<MonitorGroupListBean> groupList = new ArrayList<>();
            for (int j = 0; j < val.getGroupList().size(); j++) {
                MonitorGroupListBean firstGroup = new MonitorGroupListBean();
                firstGroup.setParentGroupName(val.getOrgUnit().getName());
                firstGroup.setGroupId(val.getGroupList().get(j).getGroupId());
                firstGroup.setGroupName(val.getGroupList().get(j).getGroupName());
                /**
                 * 有二级分组
                 * */
                if (!CollectionUtil.isEmpty(val.getGroupList().get(j).getSubGroupList())) {
                    firstGroup.setHaveSubGroup(true);
                    groupList.add(firstGroup);
                    for (int i = 0; i < val.getGroupList().get(j).getSubGroupList().size(); i++) {
                        MonitorGroupListBean secondGroup = new MonitorGroupListBean();
                        if (i == 0 && j == 0) {
                            secondGroup.setChecked(true);
                        }
                        secondGroup.setGroupId(val.getGroupList().get(j).getSubGroupList().get(i).getGroupId());
                        secondGroup.setGroupName(val.getGroupList().get(j).getSubGroupList().get(i).getGroupName());
                        groupList.add(secondGroup);
                    }
                } else {
                    /**
                     * 没有二级分组
                     *
                     * 判断有无devicelist  有说明当前分组下存在设备
                     * */
                    if (j == 0) {
                        firstGroup.setChecked(true);
                    }
                    firstGroup.setHaveSubGroup(false);
                    firstGroup.setFirstHaveDevice(true);
                    groupList.add(firstGroup);
                }
            }
            monitorListMutableLiveData.setValue(groupList);
        });
        return monitorListMutableLiveData;
    }

    /**
     * 实时监控首页 设备
     */
    public MutableLiveData<PageBean<Ys7DevicesEntity>> doGetMonitorDeviceList(QueryEntry queryEntry) {
        MutableLiveData<PageBean<Ys7DevicesEntity>> monitorDeviceListMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getMonitorDeviceList(queryEntry, (val) -> {
            monitorDeviceListMutableLiveData.setValue(val);
        });
        return monitorDeviceListMutableLiveData;
    }

    /**
     * 实时监控首页 切换公司
     */
    public MutableLiveData<List<OrgEntity>> doSelectCompany(String accId) {
        MutableLiveData<List<OrgEntity>> monitorCompanyListMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getMonitorCompanyList(accId, (val) -> {
            monitorCompanyListMutableLiveData.setValue(val);
        });
        return monitorCompanyListMutableLiveData;
    }

    /**
     * 实时监控 搜索设备
     */
    public MutableLiveData<PageBean<Ys7DevicesEntity>> doSearchDevice(QueryEntry queryEntry) {
        queryEntry.setSize(10);
        MutableLiveData<PageBean<Ys7DevicesEntity>> monitorSearchDeviceListMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doSearchDeviceList(queryEntry, (val) -> {
            monitorSearchDeviceListMutableLiveData.setValue(val);
        });
        return monitorSearchDeviceListMutableLiveData;
    }

    /**
     * 分组管理页面
     */
    public MutableLiveData<List<RealTimeGroupEntity>> doGetGroupManagerList(String companyId) {
        MutableLiveData<List<RealTimeGroupEntity>> monitorGroupManagerListMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doGetGroupList(companyId, (val) -> {
            List<RealTimeGroupEntity> groupList = new ArrayList<>();
            for (int j = 0; j < val.size(); j++) {
                RealTimeGroupEntity firstGroup = new RealTimeGroupEntity();
                firstGroup.setGroupId(val.get(j).getGroupId());
                firstGroup.setGroupName(val.get(j).getGroupName());
                firstGroup.setDeviceCount(val.get(j).getDeviceCount());
                firstGroup.setConfigId(val.get(j).getConfigId());
                firstGroup.setParentGroupName(val.get(j).getParentGroupName());
                firstGroup.setSubGroupList(val.get(j).getSubGroupList());
                firstGroup.setDeviceList(val.get(j).getDeviceList());
                /**
                 * 有二级分组
                 * */
                if (!CollectionUtil.isEmpty(val.get(j).getSubGroupList())) {
                    firstGroup.setHaveSubGroup(true);
                    groupList.add(firstGroup);
                    for (int i = 0; i < val.get(j).getSubGroupList().size(); i++) {
                        RealTimeGroupEntity secondGroup = new RealTimeGroupEntity();
                        secondGroup.setGroupId(val.get(j).getSubGroupList().get(i).getGroupId());
                        secondGroup.setGroupName(val.get(j).getSubGroupList().get(i).getGroupName());
                        secondGroup.setParentGroupName(val.get(j).getSubGroupList().get(i).getParentGroupName());
                        secondGroup.setConfigId(val.get(j).getSubGroupList().get(i).getConfigId());
                        secondGroup.setDeviceCount(val.get(j).getSubGroupList().get(i).getDeviceCount());
                        secondGroup.setDeviceList(val.get(j).getSubGroupList().get(i).getDeviceList());
                        groupList.add(secondGroup);
                    }
                } else {
                    firstGroup.setHaveSubGroup(true);
                    groupList.add(firstGroup);
                }
            }
            monitorGroupManagerListMutableLiveData.setValue(groupList);
        });
        return monitorGroupManagerListMutableLiveData;
    }

    /**
     * 删除分组
     */
    public MutableLiveData<JSONObject> doDeleteGroup(String companyId) {
        MutableLiveData<JSONObject> monitorDeleteGroupMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doDeleteGroup(companyId, (val) -> {
            monitorDeleteGroupMutableLiveData.setValue(val);
        });
        return monitorDeleteGroupMutableLiveData;
    }

    /**
     * 修改 名字 分组 设备
     */
    public MutableLiveData<MonitorUpdataVo> doUpdataGroupInfo(MonitorUpdataVo monitorUpdataVo) {
        MutableLiveData<MonitorUpdataVo> monitorUpdateGroupMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doUpdateGroupInfo(monitorUpdataVo, (val) -> {
            monitorUpdateGroupMutableLiveData.setValue(val);
        });
        return monitorUpdateGroupMutableLiveData;
    }

    /**
     * 保存名字
     */
    public MutableLiveData<MonitorUpdataVo> doCreateGroupInfo(MonitorUpdataVo monitorUpdataVo) {
        MutableLiveData<MonitorUpdataVo> monitorCreateGroupMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doCreateGroupInfo(monitorUpdataVo, (val) -> {
            monitorCreateGroupMutableLiveData.setValue(val);
        });
        return monitorCreateGroupMutableLiveData;
    }

    /**
     * 删除设备
     */
    public MutableLiveData<MonitorDeleteVo> doDeleteDevice(MonitorDeleteVo monitorUpdataVo) {
        MutableLiveData<MonitorDeleteVo> monitorDeleteDeviceMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doDeleteDevice(monitorUpdataVo, (val) -> {
            monitorDeleteDeviceMutableLiveData.setValue(val);
        });
        return monitorDeleteDeviceMutableLiveData;
    }

    /**
     * 增加设备
     */
    public MutableLiveData<MonitorDeleteVo> doAddDevice(MonitorDeleteVo monitorUpdataVo) {
        MutableLiveData<MonitorDeleteVo> monitorAddDeviceMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doAddDevice(monitorUpdataVo, (val) -> {
            monitorAddDeviceMutableLiveData.setValue(val);
        });
        return monitorAddDeviceMutableLiveData;
    }

    /**
     * 获取添加设备列表
     */
    public MutableLiveData<PageBean<Ys7DevicesEntity>> doGetAddDeviceList(QueryEntry queryEntry) {
        queryEntry.setSize(10);
        MutableLiveData<PageBean<Ys7DevicesEntity>> monitorGetAddDeviceListMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doGetAddDeviceList(queryEntry, (val) -> {
            monitorGetAddDeviceListMutableLiveData.setValue(val);
        });
        return monitorGetAddDeviceListMutableLiveData;
    }

    /**
     * 获取设备详情
     *
     * @param deviceId
     * @return
     */
    public MutableLiveData<Ys7DevicesEntity> doGetDeviceDetail(Long deviceId) {
        MutableLiveData<Ys7DevicesEntity> monitorDeviceDetailMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doGetDeviceDetail(deviceId, (val) -> {
            monitorDeviceDetailMutableLiveData.setValue(val);
        });
        return monitorDeviceDetailMutableLiveData;
    }

    /**
     * 修改设备名称
     */
    public MutableLiveData<MonitorUpdataVo> doUpdataDeviceName(MonitorUpdataVo monitorUpdataVo) {
        MutableLiveData<MonitorUpdataVo> monitorUpdateDeviceNameMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doUpdateDeviceName(monitorUpdataVo, (val) -> {
            monitorUpdateDeviceNameMutableLiveData.setValue(val);
        });
        return monitorUpdateDeviceNameMutableLiveData;
    }

    /**
     * 修改设备分组
     */
    public MutableLiveData<MonitorUpdataVo> doUpdataDeviceGroup(MonitorUpdataVo monitorUpdataVo) {
        MutableLiveData<MonitorUpdataVo> monitorUpdateDeviceGroupMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doUpdateDeviceGroup(monitorUpdataVo, (val) -> {
            monitorUpdateDeviceGroupMutableLiveData.setValue(val);
        });
        return monitorUpdateDeviceGroupMutableLiveData;
    }

    /**
     * 生成汇报
     */
    public MutableLiveData<MonitorReportVo> doCreateDeviceReport(MonitorReportVo monitorReportVo) {
        MutableLiveData<MonitorReportVo> monitorDeviceReportMutableLiveData = new MutableLiveData<>();
        remoteDataSource.doCreateReport(monitorReportVo, (val) -> {
            monitorDeviceReportMutableLiveData.setValue(val);
        });
        return monitorDeviceReportMutableLiveData;
    }


}


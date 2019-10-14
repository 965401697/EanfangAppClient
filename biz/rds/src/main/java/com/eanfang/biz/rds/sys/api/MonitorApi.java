package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.PageBean;
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

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author guanluocang
 * @data 2019/9/18
 * @description 实时监控
 */
public interface MonitorApi {
    /**
     * 实时监控列表
     *
     * @param companyId
     * @return
     */
    @POST("/yaf_real_time/realtimeconfig/homepage")
    Observable<BaseResponseBody<MonitorListBean>> getMonitorList(@Query("companyId") String companyId);

    /**
     * 分组下的设备
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_real_time/realtimegroup/deviceListByGroup")
    Observable<BaseResponseBody<PageBean<Ys7DevicesEntity>>> getMonitorDeviceList(@Body QueryEntry queryMap);

    /**
     * 选择公司
     *
     * @param accId
     * @return
     */
    @POST("/yaf_real_time/realtimeconfig/orgListOpenRealTime")
    Observable<BaseResponseBody<List<OrgEntity>>> getMonitorCompanyList(@Query("accId") String accId);

    /**
     * 搜索设备
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_real_time/realtimeconfig/realTimeDeviceListByName")
    Observable<BaseResponseBody<PageBean<Ys7DevicesEntity>>> getMonitorSearchDeviceList(@Body QueryEntry queryMap);

    /**
     * 分组管理页面
     *
     * @param companyId
     * @return
     */
    @POST("/yaf_real_time/realtimeconfig/groupList")
    Observable<BaseResponseBody<List<RealTimeGroupEntity>>> getMonitorGroupManagerList(@Query("companyId") String companyId);

    /**
     * 删除分组
     *
     * @param mGroupId
     * @return
     */
    @POST("/yaf_real_time/realtimegroup/delete")
    Observable<BaseResponseBody<JSONObject>> doDeleteGroup(@Query("groupId") String mGroupId);

    /**
     * 修改分组信息
     *
     * @param monitorUpdataVo
     * @return
     */
    @POST("/yaf_real_time/realtimegroup/update")
    Observable<BaseResponseBody<MonitorUpdataVo>> doUpdateGroupInfo(@Body MonitorUpdataVo monitorUpdataVo);

    /**
     * 创建分组
     *
     * @param monitorUpdataVo
     * @return
     */
    @POST("/yaf_real_time/realtimegroup/insert")
    Observable<BaseResponseBody<JSONObject>> doCreateGroupInfo(@Body MonitorUpdataVo monitorUpdataVo);


    /**
     * 删除设备
     *
     * @param monitorDeleteVo
     * @return
     */
    @POST("/yaf_real_time/realtimegroup/deleteGroupDevice")
    Observable<BaseResponseBody<MonitorDeleteVo>> doDeleteDevice(@Body MonitorDeleteVo monitorDeleteVo);

    /**
     * 增加设备
     *
     * @param monitorDeleteVo
     * @return
     */
    @POST("/yaf_real_time/realtimegroup/insertGroupDevice")
    Observable<BaseResponseBody<MonitorDeleteVo>> doAddDevice(@Body MonitorDeleteVo monitorDeleteVo);

    /**
     * 获取增加设备
     */
    @POST("/yaf_real_time/realTimeDevice/deviceListNoRealTime")
    Observable<BaseResponseBody<PageBean<Ys7DevicesEntity>>> doGetAddDeviceList(@Body QueryEntry queryMap);


    /**
     * 获取设备详情
     *
     * @param deviceId
     * @return
     */
    @POST("/yaf_real_time/realTimeDevice/infoWithGroup")
    Observable<BaseResponseBody<Ys7DevicesEntity>> doGetDeviceDetail(@Query("deviceId") Long deviceId);

    /**
     * 修改设备名称
     *
     * @param monitorDeleteVo
     * @return
     */
    @POST("/yaf_real_time/realTimeDevice/updateDevice")
    Observable<BaseResponseBody<Ys7DevicesEntity>> doUpdateDeviceName(@Body MonitorUpdataVo monitorDeleteVo);

    /**
     * 修改设备分组
     *
     * @param monitorDeleteVo
     * @return
     */
    @POST("/yaf_real_time/realTimeDevice/changeDeviceGroup")
    Observable<BaseResponseBody<Ys7DevicesEntity>> doUpdateDeviceGroup(@Body MonitorUpdataVo monitorDeleteVo);

    /**
     * 生成汇报
     *
     * @param monitorReportVo
     * @return
     */
    @POST("/yaf_real_time/realtimereport/insert")
    Observable<BaseResponseBody<MonitorReportVo>> doCreateReport(@Body MonitorReportVo monitorReportVo);

}

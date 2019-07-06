package net.eanfang.client.ui.activity.leave_post;


import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.QueryEntry;

import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertHistoryBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertInfoDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertRankingListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostChooseAreaBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeTopBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostLatestAlertBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostMonitorBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostStationRankingListBean;

import org.json.JSONObject;

import java.util.Date;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :脱岗Api
 */
public interface LeavePostApi {

    /**
     * 脱岗检测首页上部分
     *
     * @param companyId 公司id
     * @return
     */
    @POST("/yaf_station/stationDetect/homepage")
    Observable<BaseResponseBody<LeavePostHomeTopBean>> homeData(@Query("companyId") Long companyId);

    /**
     * 脱岗检测首页待处理
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/unhandledAlertList")
    Observable<BaseResponseBody<LeavePostHomeUnHandledAlertBean>> homeUnhandledAlert(@Body QueryEntry queryEntry);

    /**
     * 图像查岗
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectStations/list")
    Observable<BaseResponseBody<LeavePostDeviceListBean>> deviceList(@Body QueryEntry queryEntry);

    /**
     * 监测岗位
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectDevices/customerDeviceList")
    Observable<BaseResponseBody<LeavePostMonitorBean>> postMonitor(@Body QueryEntry queryEntry);

    /**
     * 添加岗位
     *
     * @param addPostPostBean
     * @return
     */
    @POST("/yaf_station/stationDetectStations/insert")
    Observable<BaseResponseBody<JSONObject>> addPost(@Body LeavePostAddPostPostBean addPostPostBean);

    /**
     * 报警排名：按照时间和地区查询报警地理分布(默认查询当天)
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/alertsRanking")
    Observable<BaseResponseBody<String>> alertsRanking(@Body QueryEntry queryEntry);

    /**
     * 报警历史记录
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/alertList")
    Observable<BaseResponseBody<LeavePostAlertHistoryBean>> alertHistoryList(@Body QueryEntry queryEntry);

    /**
     * 获取责任联系人列表
     *
     * @param alertId 公司id
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/contactsList")
    Observable<BaseResponseBody<LeavePostDeviceInfoBean>> contactsList(@Query("alertId") Long alertId);

    /**
     * 首页最新脱岗警报
     *
     * @param companyId 公司id
     * @param date      取消时间
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/latestAlert")
    Observable<BaseResponseBody<LeavePostLatestAlertBean>> leavePostLatestAlert(@Query("companyId") Long companyId, @Query("date") Date date);

    /**
     * 岗位管理列表页
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectStations/listByPlaceName")
    Observable<BaseResponseBody<LeavePostManageListBean>> postManageList(
            @Body QueryEntry queryEntry);

    /**
     * 报警记录：根据日期查询当日报警记录列表
     *
     * @param queryDate 查询时间
     * @param stationId 岗位
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/alertInfoByDay")
    Observable<BaseResponseBody<LeavePostHistoryDayBean>> leavePostAlertInfoList(@Query("queryDate") Date queryDate, @Query("stationId") String stationId);

    /**
     * 报警记录：报警详情
     *
     * @param alertId 查询时间
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/alertInfo")
    Observable<BaseResponseBody<LeavePostAlertInfoDetailBean>> leavePostAlertInfoDetail(@Query("alertId") String alertId);

    /**
     * 报警记录：岗位详情
     *
     * @param stationId 岗位id
     * @return
     */
    @POST("/yaf_station/stationDetectStations/info")
    Observable<BaseResponseBody<LeavePostDeviceInfoBean>> postInfo(@Query("stationId") String stationId);

    /**
     * 历史报警--筛选---地区列表和岗位名称
     *
     * @param companyId
     * @return
     */
    @POST("/yaf_station/stationDetectStations/areaAndNameList")
    Observable<BaseResponseBody<LeavePostAlertInfoDetailBean>> leavePostAreaAndnameList(@Query("companyId") long companyId);

    /**
     * 报警排名---->岗位报警排名
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/stationRankingList")
    Observable<BaseResponseBody<LeavePostStationRankingListBean>> postRankingList(@Body QueryEntry queryEntry);

    /**
     * 历史报警--筛选---地区列表
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectStations/areaList")
    Observable<BaseResponseBody<LeavePostChooseAreaBean>> chooseAreaData(@Body QueryEntry queryEntry);

    /**
     * 历史报警--筛选---地区列表
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectStations/stationListByPlace")
    Observable<BaseResponseBody<LeavePostChooseAreaBean>> choosePostData(@Body QueryEntry queryEntry);

    /**
     * 报警排名---->问题汇总排名
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_station/stationDetectAlerts/alertRankingList")
    Observable<BaseResponseBody<LeavePostAlertRankingListBean>> alertRankingList(@Body QueryEntry queryEntry);


}

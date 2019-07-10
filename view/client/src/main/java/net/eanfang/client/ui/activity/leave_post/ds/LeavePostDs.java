package net.eanfang.client.ui.activity.leave_post.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;

import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertHistoryBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertInfoDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertRankingListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostChooseAreaBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeTopBean;
import net.eanfang.client.ui.activity.leave_post.LeavePostApi;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostMonitorBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostStationRankingListBean;

import org.json.JSONObject;


/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :脱岗监测
 */
public class LeavePostDs extends BaseRemoteDataSource {

    public LeavePostDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    /**
     * 获取监测首页上部分数据接口
     *
     * @param companyId
     * @param callback
     */
    public void getLeavePostHomeData(Long companyId, RequestCallback<LeavePostHomeTopBean> callback) {
        execute(getService(LeavePostApi.class).homeData(companyId), callback);
    }

    /**
     * 获取监测首页未处理接口
     *
     * @param queryEntry
     * @param callback
     */
    public void getHomeUnhandledAlert(QueryEntry queryEntry, RequestCallback<LeavePostHomeUnHandledAlertBean> callback) {
        execute(getService(LeavePostApi.class).homeUnhandledAlert(queryEntry), callback);
    }

    /**
     * 获取岗位列表页面
     *
     * @param queryEntry
     * @param callback
     */
    public void getLeavePostDeviceList(QueryEntry queryEntry, RequestCallback<LeavePostDeviceListBean> callback) {
        execute(getService(LeavePostApi.class).deviceList(queryEntry), callback);
    }

    /**
     * 获取岗位详情页面
     *
     * @param stationId
     * @param callback
     */
    public void getLeavePostPostInfo(String stationId, RequestCallback<LeavePostDeviceInfoBean> callback) {
        execute(getService(LeavePostApi.class).postInfo(stationId), callback);
    }

    /**
     * 岗位管理列表页
     *
     * @param queryEntry
     */
    public void getPostManageList(QueryEntry queryEntry, RequestCallback<LeavePostManageListBean> callback) {
        execute(getService(LeavePostApi.class).postManageList(queryEntry), callback);
    }

    /**
     * 岗位监测
     *
     * @param queryEntry
     * @param callback
     */
    public void getLeavePostMonitorData(QueryEntry queryEntry, RequestCallback<LeavePostMonitorBean> callback) {
        execute(getService(LeavePostApi.class).postMonitor(queryEntry), callback);
    }

    /**
     * 报警排名---->岗位报警排名
     *
     * @param queryEntry
     * @param callback
     */
    public void postRankingListData(QueryEntry queryEntry, RequestCallback<LeavePostStationRankingListBean> callback) {
        execute(getService(LeavePostApi.class).postRankingList(queryEntry), callback);
    }

    /**
     * 历史报警--筛选---地区列表
     *
     * @param queryEntry
     * @param callback
     */
    public void chooseAreaData(QueryEntry queryEntry, RequestCallback<LeavePostChooseAreaBean> callback) {
        execute(getService(LeavePostApi.class).chooseAreaData(queryEntry), callback);
    }

    /**
     * 历史报警--筛选---地区列表
     *
     * @param queryEntry
     * @param callback
     */
    public void choosePostData(QueryEntry queryEntry, RequestCallback<LeavePostChooseAreaBean> callback) {
        execute(getService(LeavePostApi.class).choosePostData(queryEntry), callback);
    }

    /**
     * 报警排名---->问题汇总排名
     *
     * @param queryEntry
     * @param callback
     */
    public void alertRankingList(QueryEntry queryEntry, RequestCallback<LeavePostAlertRankingListBean> callback) {
        execute(getService(LeavePostApi.class).alertRankingList(queryEntry), callback);
    }

    /**
     * 报警历史记录
     *
     * @param queryEntry
     * @param callback
     */
    public void alertHistoryList(QueryEntry queryEntry, RequestCallback<LeavePostAlertHistoryBean> callback) {
        execute(getService(LeavePostApi.class).alertHistoryList(queryEntry), callback);
    }

    /**
     * 添加岗位
     *
     * @param addPostPostBean
     * @param callback
     */
    public void addPost(LeavePostAddPostPostBean addPostPostBean, RequestCallback<JSONObject> callback) {
        execute(getService(LeavePostApi.class).addPost(addPostPostBean), callback);
    }

    /**
     * 更新岗位
     *
     * @param addPostPostBean
     * @param callback
     */
    public void updatePost(LeavePostAddPostPostBean addPostPostBean, RequestCallback<JSONObject> callback) {
        execute(getService(LeavePostApi.class).updatePost(addPostPostBean), callback);
    }

    /**
     * 报警记录：根据日期查询当日报警记录列表
     *
     * @param queryDate
     * @param stationId
     * @param callback
     */
    public void leavePostAlertInfoList(String queryDate, String stationId, RequestCallback<LeavePostHistoryDayBean> callback) {
        execute(getService(LeavePostApi.class).leavePostAlertInfoList(queryDate, stationId), callback);
    }

    /**
     * 报警记录：报警详情
     *
     * @param alertId  查询时间
     * @param callback
     */
    public void leavePostAlertInfoDetail(String alertId, RequestCallback<LeavePostAlertInfoDetailBean> callback) {
        execute(getService(LeavePostApi.class).leavePostAlertInfoDetail(alertId), callback);
    }

    /**
     * 获取责任联系人列表
     *
     * @param alertId  查询时间
     * @param callback
     */
    public void contactsList(Long alertId, RequestCallback<LeavePostDeviceInfoBean> callback) {
        execute(getService(LeavePostApi.class).contactsList(alertId), callback);
    }
}

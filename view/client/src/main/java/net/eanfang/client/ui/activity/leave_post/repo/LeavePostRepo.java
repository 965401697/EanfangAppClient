package net.eanfang.client.ui.activity.leave_post.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;
import com.eanfang.biz.model.entity.station.StationDetectStationsEntity;
import com.eanfang.biz.rds.base.BaseRepo;

import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertHistoryBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertInfoDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertRankingListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostChooseAreaBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeTopBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHomeUnHandledAlertBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostManageListBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostStationRankingListBean;
import net.eanfang.client.ui.activity.leave_post.ds.LeavePostDs;

import org.json.JSONObject;

/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :脱岗监测网络请求repo
 */
public class LeavePostRepo extends BaseRepo<LeavePostDs> {

    public LeavePostRepo(LeavePostDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 首页上部分数据
     *
     * @param companyId
     * @return
     */
    public MutableLiveData<LeavePostHomeTopBean> homeTopData(Long companyId) {
        MutableLiveData<LeavePostHomeTopBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getLeavePostHomeData(companyId, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 首页待处理数据
     *
     * @param queryEntry
     * @return
     */
    public MutableLiveData<LeavePostHomeUnHandledAlertBean> homeUnhandledAlert(QueryEntry queryEntry) {
        MutableLiveData<LeavePostHomeUnHandledAlertBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getHomeUnhandledAlert(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 图像查岗列表页
     *
     * @param queryEntry
     * @return
     */
    public MutableLiveData<PageBean<StationDetectStationsEntity>> deviceListData(QueryEntry queryEntry) {
        MutableLiveData<PageBean<StationDetectStationsEntity>> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getLeavePostDeviceList(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 图像查岗详情页
     *
     * @param stationId
     * @return
     */
    public MutableLiveData<LeavePostDeviceInfoBean> deviceInfoData(String stationId) {
        MutableLiveData<LeavePostDeviceInfoBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getLeavePostPostInfo(stationId, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 岗位管理列表页
     *
     * @param queryEntry
     * @return
     */
    public MutableLiveData<LeavePostManageListBean> postManageListData(QueryEntry queryEntry) {
        MutableLiveData<LeavePostManageListBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getPostManageList(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 岗位监测
     *
     * @return
     */
    public MutableLiveData<PageBean<Ys7DevicesEntity>> postMonitorData(QueryEntry queryEntry) {
        MutableLiveData<PageBean<Ys7DevicesEntity>> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getLeavePostMonitorData(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报警历史记录
     *
     * @return
     */
    public MutableLiveData<LeavePostAlertHistoryBean> alertHistoryList(QueryEntry queryEntry) {
        MutableLiveData<LeavePostAlertHistoryBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.alertHistoryList(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 添加岗位
     *
     * @return
     */
    public MutableLiveData<JSONObject> addPost(LeavePostAddPostPostBean addPostPostBean) {
        MutableLiveData<JSONObject> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.addPost(addPostPostBean, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 更新岗位
     *
     * @return
     */
    public MutableLiveData<JSONObject> updatePost(LeavePostAddPostPostBean addPostPostBean) {
        MutableLiveData<JSONObject> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.updatePost(addPostPostBean, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报警排名---->岗位报警排名
     *
     * @return
     */
    public MutableLiveData<LeavePostStationRankingListBean> postRankingListData(QueryEntry queryEntry) {
        MutableLiveData<LeavePostStationRankingListBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.postRankingListData(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报警排名---->问题汇总排名
     *
     * @return
     */
    public MutableLiveData<LeavePostAlertRankingListBean> alertRankingList(QueryEntry queryEntry) {
        MutableLiveData<LeavePostAlertRankingListBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.alertRankingList(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 历史报警--筛选---地区列表
     *
     * @return
     */
    public MutableLiveData<LeavePostChooseAreaBean> chooseAreaData(QueryEntry queryEntry) {
        MutableLiveData<LeavePostChooseAreaBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.chooseAreaData(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 历史报警--筛选---根据地区查询岗位列表
     *
     * @return
     */
    public MutableLiveData<LeavePostChooseAreaBean> choosePostData(QueryEntry queryEntry) {
        MutableLiveData<LeavePostChooseAreaBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.choosePostData(queryEntry, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报警记录：根据日期查询当日报警记录列表
     *
     * @return
     */
    public MutableLiveData<LeavePostHistoryDayBean> leavePostAlertInfoList(String queryDate, String stationId) {
        MutableLiveData<LeavePostHistoryDayBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.leavePostAlertInfoList(queryDate, stationId, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报警记录：报警详情
     *
     * @return
     */
    public MutableLiveData<LeavePostAlertInfoDetailBean> leavePostAlertInfoDetail(String alertId) {
        MutableLiveData<LeavePostAlertInfoDetailBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.leavePostAlertInfoDetail(alertId, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报警记录：报警详情
     *
     * @return
     */
    public MutableLiveData<LeavePostDeviceInfoBean> contactsList(int alertId) {
        MutableLiveData<LeavePostDeviceInfoBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.contactsList(alertId, mutableLiveData::setValue);
        return mutableLiveData;
    }
}

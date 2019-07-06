package net.eanfang.client.ui.activity.leave_post.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-07-03
 * Describe :问题汇总排名
 */
@Data
@NoArgsConstructor
public class LeavePostAlertRankingListBean {


    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {

        private int alertDeviceId;
        private int alertId;
        private String alertImgUrl;
        private String alertName;
        private String alertTime;
        private String alertVideoUrl;
        private int alertsCount;
        private String backTime;
        private int compareValue;
        private int detectId;
        private DevicesEntityBean devicesEntity;
        private String handelUserId;
        private String leaveTime;
        private int stationId;
        private StationsEntityBean stationsEntity;
        private int status;
        private String updateTime;
        private List<String> alertImgList;
        private List<String> alertVideoList;
        private LeavePostDetailBean mLeavePostDetailBean;
        private LeavePostDefaultRankingBean mRankingBean;

        public LeavePostDefaultRankingBean getRankingBean() {
            mRankingBean = new LeavePostDefaultRankingBean();
            mRankingBean.setAlertName(alertName);
            mRankingBean.setAlertsCount(alertsCount + "次");
            mRankingBean.setCompareValue(compareValue);
            mRankingBean.setDate(alertTime);
            mRankingBean.setStationId(stationId);
            return mRankingBean;
        }

        public LeavePostDetailBean getLeavePostDetailBean() {
            mLeavePostDetailBean = new LeavePostDetailBean();
            mLeavePostDetailBean.setPageType(2);
            mLeavePostDetailBean.setName(stationsEntity.getStationName());
            mLeavePostDetailBean.setAreaCode(stationsEntity.getStationPlaceName());
            mLeavePostDetailBean.setPosition(stationsEntity.getStationName());
            mLeavePostDetailBean.setImg(devicesEntity.getLivePic());
            mLeavePostDetailBean.setCount(alertsCount);
            mLeavePostDetailBean.setTime(alertTime);
            return mLeavePostDetailBean;
        }

        @NoArgsConstructor
        @Data
        public static class DevicesEntityBean {
            /**
             * deviceId : 11
             * deviceName : 还是辣的很的测试设备
             * livePic : ys7/img/1920190701171719.jpg
             */

            private int deviceId;
            private String deviceName;
            private String livePic;
        }

        @NoArgsConstructor
        @Data
        public static class StationsEntityBean {
            /**
             * companyId : 1126289639904178178
             * detectId : 29
             * stationArea : 测试岗位位置狠辣的
             * stationCode : PS-00221
             * stationId : 19
             * stationName : 测试岗位还是很辣的
             * stationPlaceName : 北京
             */

            private String companyId;
            private int detectId;
            private String stationArea;
            private String stationCode;
            private int stationId;
            private String stationName;
            private String stationPlaceName;
        }
    }
}

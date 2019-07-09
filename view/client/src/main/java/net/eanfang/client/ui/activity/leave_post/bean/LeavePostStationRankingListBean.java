package net.eanfang.client.ui.activity.leave_post.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-07-03
 * Describe :
 */
@Data
@NoArgsConstructor
public class LeavePostStationRankingListBean {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;


    @NoArgsConstructor
    @Data
    public static class ListBean {
        /**
         * alertDeviceId : 11
         * alertId : 23
         * alertImgList : ["aaa.img","bbbb.img"]
         * alertImgUrl : aaa.img,bbbb.img
         * alertName : 狠辣的测试报警脱岗011
         * alertTime : 2019-07-01 11:18:26
         * alertVideoList : ["aaa.mp4","bbb.mp4"]
         * alertVideoUrl : aaa.mp4,bbb.mp4
         * alertsCount : 1
         * backTime : 2019-07-01 11:54:02
         * compareValue : 1
         * detectId : 29
         * devicesEntity : {"deviceId":11,"deviceName":"还是辣的很的测试设备","livePic":"ys7/img/1920190701171719.jpg"}
         * handelUserId : 980351716160487425
         * leaveTime : 2019-07-01 11:19:02
         * stationId : 19
         * stationsEntity : {"companyId":"1126289639904178178","detectId":29,"stationArea":"测试岗位位置狠辣的","stationCode":"PS-00221","stationId":19,"stationName":"测试岗位还是很辣的","stationPlaceName":"北京"}
         * status : 1
         * updateTime : 2019-07-03 14:54:06
         */

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

        public LeavePostDefaultRankingBean getRankingBean(int dateType) {
            mRankingBean = new LeavePostDefaultRankingBean();
            if (stationsEntity != null) {
                mRankingBean.setAlertName(stationsEntity.getStationName());
            }
            mRankingBean.setAlertsCount(alertsCount + "次");
            mRankingBean.setCompareValue(compareValue);
            mRankingBean.setStationId(stationId);
            mRankingBean.setDate(alertTime);
            mRankingBean.setRankingType(dateType);
            return mRankingBean;
        }

        public LeavePostDetailBean getLeavePostDetailBean() {
            mLeavePostDetailBean = new LeavePostDetailBean();
            mLeavePostDetailBean.setPageType(2);
            if (stationsEntity != null) {
                mLeavePostDetailBean.setName(stationsEntity.getStationName());
                mLeavePostDetailBean.setAreaCode(stationsEntity.getStationPlaceName());
                mLeavePostDetailBean.setPosition(stationsEntity.getStationName());
            }
            if (devicesEntity != null) {
                mLeavePostDetailBean.setImg(devicesEntity.getLivePic());
            }
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

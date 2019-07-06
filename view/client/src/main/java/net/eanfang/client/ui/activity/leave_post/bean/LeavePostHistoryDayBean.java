package net.eanfang.client.ui.activity.leave_post.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-07-02
 * Describe :每日报警记录
 */
@NoArgsConstructor
@Data
public class LeavePostHistoryDayBean implements Serializable {

    private StationBean station;
    private List<AlertListBean> alertList;
    /**
     * 报警数量
     */
    private int alertCount;

    @NoArgsConstructor
    @Data
    public static class AlertListBean {
        /**
         * absencePeriod : 0
         * alertDeviceId : 5
         * alertId : 10
         * alertName : 测试报警脱岗
         * alertTime : 2019-06-25 10:42:22
         * detectId : 21
         * stationId : 15
         * status : 0
         * updateTime : 2019-06-25 11:54:42
         * backTime : 2019-06-25 11:04:02
         * createTime : 2019-06-25 11:01:26
         * handelUserId : 980351716160487425
         * leaveTime : 2019-06-25 10:35:22
         */

        private int absencePeriod;
        private int alertDeviceId;
        private int alertId;
        private String alertName;
        private String alertTime;
        private String detectId;
        private int stationId;
        private int status;
        private String updateTime;
        private String backTime;
        private String createTime;
        private String handelUserId;
        private String leaveTime;
    }


    @NoArgsConstructor
    @Data
    public static class StationBean {
        /**
         * companyId : 1134307027488649218
         * createTime : 2019-06-26 10:18:17
         * createUserId : 979995434422681603
         * detectId : 21
         * deviceEntity : {"deviceId":5,"deviceName":"还是辣的很的测试设备","livePic":"ys7/img/1520190702110545.jpg"}
         * deviceId : 5
         * deviceName : 测试绑定设备
         * stationArea : 测试岗位位置
         * stationCode : PS-001
         * stationId : 15
         * stationName : 测试岗位001
         * stationPlaceCode : 3.13.1.1
         * stationPlaceName : 北京
         * status : 1
         * topCompanyId : 1134307027488649218
         */

        private String companyId;
        private String createTime;
        private String createUserId;
        private int detectId;
        private DeviceEntityBean deviceEntity;
        private int deviceId;
        private String deviceName;
        private String stationArea;
        private String stationCode;
        private int stationId;
        private String stationName;
        private String stationPlaceCode;
        private String stationPlaceName;
        private int status;
        private String topCompanyId;

        @NoArgsConstructor
        @Data
        public static class DeviceEntityBean {
            /**
             * deviceId : 5
             * deviceName : 还是辣的很的测试设备
             * livePic : ys7/img/1520190702110545.jpg
             */

            private int deviceId;
            private String deviceName;
            private String livePic;
        }
    }

}

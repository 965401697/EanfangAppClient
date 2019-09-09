package net.eanfang.client.ui.activity.leave_post.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :报警详情
 */
@Data
@NoArgsConstructor
public class LeavePostAlertInfoDetailBean {


    private int absencePeriod;
    private int alertDeviceId;
    private int alertId;
    private String alertImgUrl;
    private String alertName;
    private String alertTime;
    private String alertVideoUrl;
    private String backTime;
    private String detectId;
    private DevicesEntityBean devicesEntity;
    private String handelUserId;
    private String leaveTime;
    private int stationId;
    private StationsEntityBean stationsEntity;
    private int status;
    private String updateTime;
    private List<String> alertImgList;
    private List<String> alertVideoList;

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
        private String ys7DeviceSerial;
    }

    @NoArgsConstructor
    @Data
    public static class StationsEntityBean {
        /**
         * stationCode : PS-00221
         * stationId : 19
         * stationName : 测试岗位还是很辣的
         * stationPlaceName : 北京
         */

        private String stationCode;
        private int stationId;
        private String stationName;
        private String stationPlaceName;
    }
}

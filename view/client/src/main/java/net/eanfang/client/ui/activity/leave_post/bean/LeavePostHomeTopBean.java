package net.eanfang.client.ui.activity.leave_post.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :脱岗监测首页上部分数据
 */
@NoArgsConstructor
@Data
public class LeavePostHomeTopBean implements Serializable {

    /**
     * todayAlertCount : 0
     * lunarDate : 五月廿九
     * now : 2019-07-01 14:59:17
     * difference : 0
     * stationDetect : {"adminUserId":"1134307027488649219","beginTime":"2019-06-19 00:00:00","companyId":"1134307027488649218","detectId":21,"detectType":"2,3","endTime":"2020-06-20 00:00:00","orgName":"测试太卡了","stationCount":10,"status":0,"topCompanyId":"1134307027488649218"}
     * totalAlertCount : 0
     */

    private int todayAlertCount;
    private String lunarDate;
    private String now;
    private int difference;
    private StationDetectBean stationDetect;
    private int totalAlertCount;

    @NoArgsConstructor
    @Data
    public static class StationDetectBean {
        /**
         * adminUserId : 1134307027488649219
         * beginTime : 2019-06-19 00:00:00
         * companyId : 1134307027488649218
         * detectId : 21
         * detectType : 2,3
         * endTime : 2020-06-20 00:00:00
         * orgName : 测试太卡了
         * stationCount : 10
         * status : 0
         * topCompanyId : 1134307027488649218
         */

        private String adminUserId;
        private String beginTime;
        private String companyId;
        private int detectId;
        private String detectType;
        private String endTime;
        private String orgName;
        private int stationCount;
        private int status;
        private String topCompanyId;
    }
}

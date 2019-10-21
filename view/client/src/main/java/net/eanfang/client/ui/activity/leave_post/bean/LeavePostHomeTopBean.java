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
     * ys7Account : {"accountId":42,"adminUserId":"1003832818015506434","companyId":"1003832818015506433","orgName":"测试啊","status":1,"topCompanyId":"1003832818015506433"}
     * todayAlertCount : 0
     * lunarDate : 八月十九
     * now : 2019-09-17 13:42:57
     * difference : 0
     * configEntity : {"beginTime":"2019-09-16 15:48:58","endTime":"2019-09-16 15:49:00","stationCount":0}
     * totalAlertCount : 0
     */

    private Ys7AccountBean ys7Account;
    private int todayAlertCount;
    private String lunarDate;
    private String now;
    private int difference;
    private ConfigEntityBean configEntity;
    private int totalAlertCount;

    @Data
    public static class Ys7AccountBean {
        /**
         * accountId : 42
         * adminUserId : 1003832818015506434
         * companyId : 1003832818015506433
         * orgName : 测试啊
         * status : 1
         * topCompanyId : 1003832818015506433
         */

        private int accountId;
        private String adminUserId;
        private String companyId;
        private String orgName;
        private int status;
        private String topCompanyId;


    }

    @Data
    public static class ConfigEntityBean {
        /**
         * beginTime : 2019-09-16 15:48:58
         * endTime : 2019-09-16 15:49:00
         * stationCount : 0
         */

        private String beginTime;
        private String endTime;
        private int stationCount;
    }
}

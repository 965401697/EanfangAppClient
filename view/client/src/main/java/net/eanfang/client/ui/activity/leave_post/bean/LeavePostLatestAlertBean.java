package net.eanfang.client.ui.activity.leave_post.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :最新脱岗报警
 */
@NoArgsConstructor
@Data
public class LeavePostLatestAlertBean {

    /**
     * alertDeviceId : 5
     * alertId : 10
     * alertName : 测试报警脱岗
     * alertTime : 2019-06-25 10:42:22
     * detectId : 21
     * stationId : 15
     * status : 0
     * updateTime : 2019-06-25 11:54:42
     */

    private int alertDeviceId;
    private int alertId;
    private String alertName;
    private String alertTime;
    private String detectId;
    private int stationId;
    private int status;
    private String updateTime;
}

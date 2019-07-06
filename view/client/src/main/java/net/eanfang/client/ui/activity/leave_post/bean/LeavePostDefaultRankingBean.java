package net.eanfang.client.ui.activity.leave_post.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-07-03
 * Describe :展示排名数据
 */
@Getter
@Setter
public class LeavePostDefaultRankingBean {
    private String alertName;
    private String alertsCount;
    private int compareValue;
    private int stationId;
    private String date;
}

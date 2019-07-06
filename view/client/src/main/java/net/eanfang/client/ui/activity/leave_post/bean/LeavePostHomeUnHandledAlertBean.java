package net.eanfang.client.ui.activity.leave_post.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-07-01
 * Describe :脱岗监测首页待处理数据
 */
@NoArgsConstructor
@Data
public class LeavePostHomeUnHandledAlertBean {


    private int unhandledAlertCount;
    private UnhandledAlertListBean unhandledAlertList;

    @NoArgsConstructor
    @Data
    public static class UnhandledAlertListBean {

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
             * alertName : 狠辣的测试报警脱岗011
             * alertTime : 2019-07-01 11:18:26
             * detectId : 29
             * leaveTime : 2019-07-01 11:19:02
             * stationId : 19
             * stationsEntity : {"companyId":"1126289639904178178","detectId":29,"stationId":19,"stationName":"测试岗位还是很辣的"}
             * status : 0
             * updateTime : 2019-07-01 14:45:11
             * createTime : 2019-06-28 17:23:15
             */

            private int alertDeviceId;
            private int alertId;
            private String alertName;
            private String alertTime;
            private String detectId;
            private String leaveTime;
            private int stationId;
            private StationsEntityBean stationsEntity;
            private int status;
            private String updateTime;
            private String createTime;

            @NoArgsConstructor
            @Data
            public static class StationsEntityBean {
                /**
                 * companyId : 1126289639904178178
                 * detectId : 29
                 * stationId : 19
                 * stationName : 测试岗位还是很辣的
                 */

                private String companyId;
                private int detectId;
                private int stationId;
                private String stationName;
            }
        }
    }
}

package net.eanfang.client.ui.activity.leave_post.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-27
 * Describe :报警历史记录
 */
@NoArgsConstructor
@Data
public class LeavePostAlertHistoryBean implements Serializable {

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
        private String backTime;
        private String createTime;
        private int detectId;
        private String leaveTime;
        private int stationId;
        private StationsEntityBean stationsEntity;
        /**
         * 事件状态
         * 0：待处理，1：已处理，2：已删除
         */
        private int status;
        private String updateTime;
        private List<String> alertImgList;
        private List<String> alertVideoList;

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

package net.eanfang.client.ui.activity.leave_post.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-07-01
 * Describe :岗位管理列表页
 */
@Data
@NoArgsConstructor
public class LeavePostManageListBean {

    /**
     * currPage : 1
     * list : [{"total":1,"stationList":[{"companyId":"1126289639904178178","createTime":"2019-06-27 16:58:41","createUserId":"979993533551525889","detectId":29,"deviceId":11,"deviceName":"测试绑定设备","stationArea":"测试岗位位置狠辣的","stationCode":"PS-00221","stationId":19,"stationName":"测试岗位还是很辣的","stationPlaceCode":"3.13.1.1","stationPlaceName":"北京","status":1,"topCompanyId":"1126289639904178178"}],"placeName":"北京"}]
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {
        /**
         * total : 1
         * stationList : [{"companyId":"1126289639904178178","createTime":"2019-06-27 16:58:41","createUserId":"979993533551525889","detectId":29,"deviceId":11,"deviceName":"测试绑定设备","stationArea":"测试岗位位置狠辣的","stationCode":"PS-00221","stationId":19,"stationName":"测试岗位还是很辣的","stationPlaceCode":"3.13.1.1","stationPlaceName":"北京","status":1,"topCompanyId":"1126289639904178178"}]
         * placeName : 北京
         */

        private int total;
        private String placeName;
        private List<StationListBean> stationList;

        @NoArgsConstructor
        @Data
        public static class StationListBean {
            /**
             * companyId : 1126289639904178178
             * createTime : 2019-06-27 16:58:41
             * createUserId : 979993533551525889
             * detectId : 29
             * deviceId : 11
             * deviceName : 测试绑定设备
             * stationArea : 测试岗位位置狠辣的
             * stationCode : PS-00221
             * stationId : 19
             * stationName : 测试岗位还是很辣的
             * stationPlaceCode : 3.13.1.1
             * stationPlaceName : 北京
             * status : 1
             * topCompanyId : 1126289639904178178
             */

            private String companyId;
            private String createTime;
            private String createUserId;
            private int detectId;
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
        }
    }
}

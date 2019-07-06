package net.eanfang.client.ui.activity.leave_post.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-07-04
 * Describe :历史报警--筛选---地区列表
 */
@NoArgsConstructor
@Data
public class LeavePostChooseAreaBean {

    /**
     * currPage : 1
     * list : [{"stationPlaceName":"北京"}]
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
         * stationPlaceName : 北京
         */

        private String stationPlaceName;
        private String stationName;
        private int stationId;
    }
}

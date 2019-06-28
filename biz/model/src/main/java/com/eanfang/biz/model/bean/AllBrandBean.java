package com.eanfang.biz.model.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe :全部品牌
 */
@NoArgsConstructor
@Data
public class AllBrandBean {


    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {
        /**
         * dataCode : 5.1.10
         * dataId : 4485
         * dataName : 安迅士（Axis）
         * dataType : 5
         * leaf : true
         * level : 3
         * remarkInfo : /model/4485.jpg
         * sortNum : 0
         */

        private String dataCode;
        private int dataId;
        private String dataName;
        private int dataType;
        private boolean leaf;
        private int level;
        private String remarkInfo;
        private int sortNum;
    }
}

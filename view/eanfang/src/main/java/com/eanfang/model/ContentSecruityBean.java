package com.eanfang.model;

import java.util.List;

import lombok.Data;

/**
 * @author liangkailun
 * Date ：2019/5/6
 * Describe :内容审核结果bean类
 */
@Data
public class ContentSecruityBean {
    private long log_id;
    private ResultBean result;
    @Data
    public static class ResultBean {
        /**
         * 0 通过  1  未通过  2  需复查
         */
        private int spam;
        private List<AuditBean> review;
        private List<AuditBean> reject;
        private List<AuditBean> pass;
        @Data
        public static class AuditBean {
            private double score;
            private int label;
            private List<String> hit;

        }
    }
}

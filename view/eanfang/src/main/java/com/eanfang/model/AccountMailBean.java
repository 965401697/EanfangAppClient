package com.eanfang.model;
import com.eanfang.base.BaseApplication;

import java.util.List;

import lombok.Data;

/**
 * @author liangkailun
 * Date ：2019/4/29
 * Describe :通讯录好友表
 */
@Data
public class AccountMailBean {

    private AccountMailListBean accountMailList;
    private AccountEntityBean accountEntity;
    @Data
    public static class AccountMailListBean {
        private List<EntityListBean> entityList;

        @Data
        public static class EntityListBean {

            private int id;
            private int accid;
            private String mobile;
            private String userName;
            private String workUnit;
            private String email;
            private String remark;
            private String group;
            private String other;
        }
    }
    @Data
    public static class AccountEntityBean {
        /**
         * accId : 1234
         */

        private String accId = String.valueOf(BaseApplication.get().getAccId());
    }
}

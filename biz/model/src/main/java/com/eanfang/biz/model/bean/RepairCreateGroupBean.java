package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/10/21
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairCreateGroupBean implements Serializable {

    private String orderId;
    private GroupCreatBean sysGroup;
    private List<SysGroupUsersBean> sysGroupUsers;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SysGroupUsersBean implements Serializable {
        private String accId;
        private int groupId;
    }
}

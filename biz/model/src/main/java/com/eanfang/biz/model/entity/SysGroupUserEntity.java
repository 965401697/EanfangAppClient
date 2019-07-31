package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-04-17 13:35:52
 */
@Getter
@Setter
@Accessors(chain = true)
public class SysGroupUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    private Long groupId;
    private Long accId;
    private Date createTime;
    private int flag;
    private Integer status;
    private AccountEntity accountEntity;

}

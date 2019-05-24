package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-04-17 14:33:29
 */
@Getter
@Setter
@Accessors(chain = true)
public class SysGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long groupId;
    private String groupName;
    private Long createUser;
    private String rcloudGroupId;
    private Date createTime;

    private String headPortrait;

    private String notice;

    private String qrCode;
}

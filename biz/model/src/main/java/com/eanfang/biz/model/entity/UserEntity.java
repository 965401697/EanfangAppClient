package com.eanfang.biz.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Accessors(chain = true)
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户ID
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    private Long userId;

    //账号ID
    private Long accId;

    //状态 0：正常  1：禁用  2：删除 3:禁用且删除
    private Integer status;

    //归属总公司
    private Long topCompanyId;

    //归属公司
    private Long companyId;

    //归属部门
    private Long departmentId;

    //账号类别Const.UserType
    private Integer userType;

    //临时工Const.TempStaff
    private Integer tempStaff;

    //创建人
    private Long createUser;

    //创建时间
    private Date createTime;

    //更新人
    private Long updateUser;

    //更新时间
    private Date updateTime;
    //是否只接合作业务订单（0否，1是）
    private Integer isAllotCooperation;

    /*手工代码写在下面*/

    /**
     * 用户信息扩展
     */
    private Object userExtInfo;

    private AccountEntity accountEntity;

    private UserEntity createUserEntity;

    private UserEntity updateUserEntity;

    /**
     * 归属总公司
     */
    private OrgEntity topCompanyEntity;

    /**
     * 技师认证
     */
    private WorkerEntity workerEntity;

    /**
     * 归属公司
     */
    private OrgEntity companyEntity;

    /**
     * 归属部门
     */
    private OrgEntity departmentEntity;

}

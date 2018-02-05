package com.yaf.sys.entity;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


/**
 * 用户
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-27 16:40:54
 */

@TableName(value = "sys_user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户ID
    //@TableField(value = "user_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    @TableId(value = "user_id", type = IdType.ID_WORKER)
    private Long userId;

    //账号ID
    //@TableField(value = "acc_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long accId;

    //状态 0：正常  1：禁用  2：删除 3:禁用且删除
    //@TableField(value = "status")
    @Digits(integer = 3, fraction = 0)
    private Integer status;

    //归属总公司
    //@TableField(value = "top_company_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long topCompanyId;

    //归属公司
    //@TableField(value = "company_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long companyId;

    //归属部门
    //@TableField(value = "department_id")
    @Digits(integer = 19, fraction = 0)
    private Long departmentId;

    //账号类别Const.UserType
    //@TableField(value = "user_type")
    @Digits(integer = 3, fraction = 0)
    @Getter
    @Setter
    private Integer userType;

    //创建人
    //@TableField(value = "create_user")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long createUser;

    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    //更新人
    //@TableField(value = "update_user")
    @Digits(integer = 19, fraction = 0)
    private Long updateUser;

    //更新时间
    //@TableField(value = "update_time")
    private Date updateTime;


    /**
     * 设置：用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：账号ID
     */
    public void setAccId(Long accId) {
        this.accId = accId;
    }

    /**
     * 获取：账号ID
     */
    public Long getAccId() {
        return accId;
    }

    /**
     * 设置：状态 0：正常  1：禁用  2：删除 3:禁用且删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态 0：正常  1：禁用  2：删除 3:禁用且删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：归属总公司
     */
    public void setTopCompanyId(Long topCompanyId) {
        this.topCompanyId = topCompanyId;
    }

    /**
     * 获取：归属总公司
     */
    public Long getTopCompanyId() {
        return topCompanyId;
    }

    /**
     * 设置：归属公司
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：归属公司
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * 设置：归属部门
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取：归属部门
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置：创建人
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：更新人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取：更新人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置：更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    /*手工代码写在下面*/

    /**
     * 用户信息扩展
     */
    @Getter
    @Setter
    @TableField(exist = false)
    private Object userExtInfo;

    @Getter
    @Setter
    @TableField(exist = false)
    private AccountEntity accountEntity;

    @Getter
    @Setter
    @TableField(exist = false)
    private UserEntity createUserEntity;

    @Getter
    @Setter
    @TableField(exist = false)
    private UserEntity updateUserEntity;

    /**
     * 归属总公司
     */
    @Getter
    @Setter
    @TableField(exist = false)
    private OrgEntity topCompanyEntity;


    /**
     * 归属公司
     */
    @Getter
    @Setter
    @TableField(exist = false)
    private OrgEntity companyEntity;

    /**
     * 归属部门
     */
    @Getter
    @Setter
    @TableField(exist = false)
    private OrgEntity departmentEntity;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UserEntity) {
            if (this.userId == null || other == null) {
                return false;
            }

            return this.userId.equals(((UserEntity) other).userId);
        }
        return false;
    }

}

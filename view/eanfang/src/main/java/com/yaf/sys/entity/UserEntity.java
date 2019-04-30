package com.yaf.sys.entity;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;



/**
 * 用户
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-27 16:40:54
 */

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
    
	//创建人
	private Long createUser;

	//创建时间
	private Date createTime;

	//更新人
	private Long updateUser;

	//更新时间
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
	private Object userExtInfo;
    
    private AccountEntity accountEntity;

    private UserEntity createUserEntity;

    private UserEntity updateUserEntity;

    /**
     * 归属总公司
     */
    private OrgEntity topCompanyEntity;


    /**
     * 归属公司
     */
    private OrgEntity companyEntity;

    /**
     * 归属部门
     */
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
    		if(this.userId == null || other== null) {
                return false;
            }
    		
            return this.userId.equals(((UserEntity) other).userId);   
        }   
        return false; 
    }

    public Integer getUserType() {
        return this.userType;
    }

    public Object getUserExtInfo() {
        return this.userExtInfo;
    }

    public AccountEntity getAccountEntity() {
        return this.accountEntity;
    }

    public UserEntity getCreateUserEntity() {
        return this.createUserEntity;
    }

    public UserEntity getUpdateUserEntity() {
        return this.updateUserEntity;
    }

    public OrgEntity getTopCompanyEntity() {
        return this.topCompanyEntity;
    }

    public OrgEntity getCompanyEntity() {
        return this.companyEntity;
    }

    public OrgEntity getDepartmentEntity() {
        return this.departmentEntity;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public void setUserExtInfo(Object userExtInfo) {
        this.userExtInfo = userExtInfo;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setCreateUserEntity(UserEntity createUserEntity) {
        this.createUserEntity = createUserEntity;
    }

    public void setUpdateUserEntity(UserEntity updateUserEntity) {
        this.updateUserEntity = updateUserEntity;
    }

    public void setTopCompanyEntity(OrgEntity topCompanyEntity) {
        this.topCompanyEntity = topCompanyEntity;
    }

    public void setCompanyEntity(OrgEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public void setDepartmentEntity(OrgEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }
}

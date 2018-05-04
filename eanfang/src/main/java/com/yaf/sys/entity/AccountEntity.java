package com.yaf.sys.entity;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


/**
 * 系统账号
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-27 16:26:00
 */

@TableName(value = "sys_account")
public class AccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //账号
    //@TableField(value = "acc_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    @TableId(value = "acc_id", type = IdType.ID_WORKER)
    private Long accId;

    //账号类型0普通用户1内置用户2技师用户
    //@TableField(value = "acc_type")
    @Getter
    @Setter
    private Integer accType;
    
    //手机
    //@TableField(value = "mobile")
    @NotBlank
    @Size(min = 1, max = 11)
    private String mobile;

    //昵称
    //@TableField(value = "nick_name")
    @NotBlank
    @Size(min = 1, max = 32)
    private String nickName;

    //电邮
    //@TableField(value = "email")
    @Size(min = 0, max = 200)
    private String email;

    //头像
    //@TableField(value = "avatar")
    @Size(min = 0, max = 255)
    private String avatar;

    //密码
    //@TableField(value = "passwd")
    @Size(min = 0, max = 50)
    private String passwd;

    //真实姓名
    //@TableField(value = "real_name")
    @NotBlank
    @Size(min = 1, max = 32)
    private String realName;

    //注册时间
    //@TableField(value = "reg_time")
    private Date regTime;

    //状态 0：正常  1：禁用  2：删除 3:禁用且删除
    //@TableField(value = "status")
    @Digits(integer = 3, fraction = 0)
    private Integer status;

    //登录次数
    //@TableField(value = "login_count")
    @Digits(integer = 10, fraction = 0)
    private Integer loginCount;

    //最后登录时间
    //@TableField(value = "last_login_time")
    private Date lastLoginTime;

	//性别0女1男
	//@TableField(value = "gender")
	@Digits(integer=3,fraction=0)
	@Getter
	@Setter
	private Integer gender;
	
	//生日
	//@TableField(value = "birthday")
	@Getter
	@Setter
	private Date birthday;
	
	//证件号码
	//@TableField(value = "id_card")
	@Size(min = 0, max = 18)
	@Getter
	@Setter
	private String idCard;
	
	//所在城市编号
	//@TableField(value = "area_code")
	@Size(min = 0, max = 20)
	@Getter
	@Setter
	private String areaCode;
	
	//详细地址
	//@TableField(value = "adress")
	@Size(min = 0, max = 200)
	@Getter
	@Setter
	private String address;

	//个人二维码
    @Size(min = 0, max = 200)
    @Getter
    @Setter
    private String qrCode;
    /**
     * 设置：账号
     */
    public void setAccId(Long accId) {
        this.accId = accId;
    }

    /**
     * 获取：账号
     */
    public Long getAccId() {
        return accId;
    }

    /**
     * 设置：手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取：昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置：电邮
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：电邮
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取：头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置：密码
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * 获取：密码
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 设置：真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取：真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置：注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * 获取：注册时间
     */
    public Date getRegTime() {
        return regTime;
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
     * 设置：登录次数
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * 获取：登录次数
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * 设置：最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取：最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /*手工代码写在下面*/
	/**
	 * 用户信息扩展
	 */ 
    @Getter
    @Setter
	@TableField(exist = false)
	private Object accountExtInfo;

    @Getter
    @Setter
    @TableField(exist = false)
    private UserEntity defaultUser;

    @Getter
    @Setter
    @TableField(exist = false)
    private Long nullUser;


    /*归属的公司列表*/
    @Getter
    @Setter
    @TableField(exist = false)
    Set<OrgEntity> belongCompanys;
    
    /*归属的部门列表*/
    @Getter
    @Setter
    @TableField(exist = false)
    Set<Long> belongDepartments;

    /*适合当前域名的公司id列表*/
    @Getter
    @Setter
    @TableField(exist = false)
    Set<Long> allowCurDomainCompanys;
    
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accId == null) ? 0 : accId.hashCode());
		return result;
	}
	@Override
    public boolean equals(Object other) {
    	if (other instanceof AccountEntity) {
    		if(this.accId == null || other== null)
    			return false;
    		
            return this.accId.equals(((AccountEntity) other).accId);   
        }   
        return false; 
    }
	

	}

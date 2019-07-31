package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * @ClassName: HonorCertificateEntity 
 * @Description:荣誉证书表
 * @author LCH
 * @date 2018年10月9日 下午8:29:40 
 *
 */
public class HonorCertificateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	//主键
	//@TableField(value = "id")
	//数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
	private Long id;
	//所属的acc_id
	//@TableField(value = "acc_id")
	private Long accId;
	//组织机构id
	//@TableField(value = "org_id")
	private Long orgId;
	//所属的user_id
	//@TableField(value = "user_id")
	private Long userId;
	//荣誉名称
	//@TableField(value = "honor_name")
	private String honorName;
	//颁发机构
	//@TableField(value = "award_org")
	private String awardOrg;
	//颁发时间
	//@TableField(value = "award_time")
	private Date awardTime;
	//颁发证书照片
	//@TableField(value = "honor_pics")
	private String honorPics;
	//荣誉说明
	//@TableField(value = "intro")
	private String intro;
	//荣誉证书类型(0=技师的荣誉证书，1=专家的荣誉证书)
	//@TableField(value = "type")
	private Integer type;
    
	    /**
	 * 设置：主键
	 */
	public void setId(Long id) {
	    this.id = id;
	}
	
	/**
	 * 获取：主键
	 */
	public Long getId() {
	    return id;
	}
	    /**
	 * 设置：所属的acc_id
	 */
	public void setAccId(Long accId) {
	    this.accId = accId;
	}
	
	/**
	 * 获取：所属的acc_id
	 */
	public Long getAccId() {
	    return accId;
	}
	    /**
	 * 设置：所属的user_id
	 */
	public void setUserId(Long userId) {
	    this.userId = userId;
	}
	
	/**
	 * 获取：所属的user_id
	 */
	public Long getUserId() {
	    return userId;
	}
	    /**
	 * 设置：荣誉名称
	 */
	public void setHonorName(String honorName) {
	    this.honorName = honorName;
	}
	
	/**
	 * 获取：荣誉名称
	 */
	public String getHonorName() {
	    return honorName;
	}
	    /**
	 * 设置：颁发机构
	 */
	public void setAwardOrg(String awardOrg) {
	    this.awardOrg = awardOrg;
	}
	
	/**
	 * 获取：颁发机构
	 */
	public String getAwardOrg() {
	    return awardOrg;
	}
	    /**
	 * 设置：颁发时间
	 */
	public void setAwardTime(Date awardTime) {
	    this.awardTime = awardTime;
	}
	
	/**
	 * 获取：颁发时间
	 */
	public Date getAwardTime() {
	    return awardTime;
	}
	    /**
	 * 设置：颁发证书照片
	 */
	public void setHonorPics(String honorPics) {
	    this.honorPics = honorPics;
	}
	
	/**
	 * 获取：颁发证书照片
	 */
	public String getHonorPics() {
	    return honorPics;
	}
	    /**
	 * 设置：荣誉说明
	 */
	public void setIntro(String intro) {
	    this.intro = intro;
	}
	
	/**
	 * 获取：荣誉说明
	 */
	public String getIntro() {
	    return intro;
	}
	    /**
	 * 设置：荣誉证书类型(0=技师的荣誉证书，1=专家的荣誉证书)
	 */
	public void setType(Integer type) {
	    this.type = type;
	}
	
	/**
	 * 获取：荣誉证书类型(0=技师的荣誉证书，1=专家的荣誉证书)
	 */
	public Integer getType() {
	    return type;
	}
        
    @Override
    public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
    public boolean equals(Object other) {
    	if (other instanceof HonorCertificateEntity) {
    		if(this.id == null || other == null) {
                return false;
            }
    		
            return this.id.equals(((HonorCertificateEntity) other).id);   
        }   
        return false; 
    }
    @Override	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}

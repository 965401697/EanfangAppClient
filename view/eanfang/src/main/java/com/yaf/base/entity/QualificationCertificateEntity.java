package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * @ClassName: QualificationCertificateEntity 
 * @Description:资质证书表
 * @author LCH
 * @date 2018年10月9日 下午8:30:58 
 *
 */
public class QualificationCertificateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	//主键
	        //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
	private Long id;
	//所属的acc_id
	private Long accId;
	//所属的user_id
	private Long userId;
	//证书名称
	private String certificateName;
	//颁发机构
	private String awardOrg;
	//资质等级
	private String certificateLevel;
	//证书编号
	private String certificateNumber;
	//证书生效时间
	private Date beginTime;
	//证书失效时间
	private Date endTime;
	//证书照片
	private String certificatePics;
	//资质证书类型(0=技师的资质证书，1=专家的资质证书)
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
	 * 设置：证书名称
	 */
	public void setCertificateName(String certificateName) {
	    this.certificateName = certificateName;
	}
	
	/**
	 * 获取：证书名称
	 */
	public String getCertificateName() {
	    return certificateName;
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
	 * 设置：资质等级
	 */
	public void setCertificateLevel(String certificateLevel) {
	    this.certificateLevel = certificateLevel;
	}
	
	/**
	 * 获取：资质等级
	 */
	public String getCertificateLevel() {
	    return certificateLevel;
	}
	    /**
	 * 设置：证书编号
	 */
	public void setCertificateNumber(String certificateNumber) {
	    this.certificateNumber = certificateNumber;
	}
	
	/**
	 * 获取：证书编号
	 */
	public String getCertificateNumber() {
	    return certificateNumber;
	}
	    /**
	 * 设置：证书生效时间
	 */
	public void setBeginTime(Date beginTime) {
	    this.beginTime = beginTime;
	}
	
	/**
	 * 获取：证书生效时间
	 */
	public Date getBeginTime() {
	    return beginTime;
	}
	    /**
	 * 设置：证书失效时间
	 */
	public void setEndTime(Date endTime) {
	    this.endTime = endTime;
	}
	
	/**
	 * 获取：证书失效时间
	 */
	public Date getEndTime() {
	    return endTime;
	}
	    /**
	 * 设置：证书照片
	 */
	public void setCertificatePics(String certificatePics) {
	    this.certificatePics = certificatePics;
	}
	
	/**
	 * 获取：证书照片
	 */
	public String getCertificatePics() {
	    return certificatePics;
	}
	    /**
	 * 设置：资质证书类型(0=技师的资质证书，1=专家的资质证书)
	 */
	public void setType(Integer type) {
	    this.type = type;
	}
	
	/**
	 * 获取：资质证书类型(0=技师的资质证书，1=专家的资质证书)
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
    	if (other instanceof QualificationCertificateEntity) {
    		if(this.id == null || other == null) {
                return false;
            }
    		
            return this.id.equals(((QualificationCertificateEntity) other).id);   
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
}

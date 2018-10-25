package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;



/**
 * 
 * @ClassName: AptitudeCertificateEntity 
 * @Description:安防公司/生产厂商资质证书表
 * @author LCH
 * @date 2018年10月10日 下午7:30:21 
 *
 */
@TableName(value = "shop_aptitude_certificate" )
@Getter
@Setter
public class AptitudeCertificateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	//主键
	//@TableField(value = "id")
	        //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
	@TableId(value = "id" , type = IdType.AUTO)
	private Long id;
	//组织机构id
	//@TableField(value = "org_id")
	private Long orgId;
	//证书名称
	//@TableField(value = "certificate_name")
	private String certificateName;
	//颁发机构
	//@TableField(value = "award_org")
	private String awardOrg;
	//资质等级
	//@TableField(value = "certificate_level")
	private String certificateLevel;
	//证书编号
	//@TableField(value = "certificate_number")
	private String certificateNumber;
	//证书生效时间
	//@TableField(value = "begin_time")
	private Date beginTime;
	//证书失效时间
	//@TableField(value = "end_time")
	private Date endTime;
	//证书照片
	//@TableField(value = "certificate_pics")
	private String certificatePics;
	//资质证书类型(0=安防公司资质证书，1=生产厂商的资质证书)
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
	 * 设置：组织机构id
	 */
	public void setOrgId(Long orgId) {
	    this.orgId = orgId;
	}
	
	/**
	 * 获取：组织机构id
	 */
	public Long getOrgId() {
	    return orgId;
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
	 * 设置：资质证书类型(0=安防公司资质证书，1=生产厂商的资质证书)
	 */
	public void setType(Integer type) {
	    this.type = type;
	}
	
	/**
	 * 获取：资质证书类型(0=安防公司资质证书，1=生产厂商的资质证书)
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
    	if (other instanceof AptitudeCertificateEntity) {
    		if(this.id == null || other == null)
    			return false;
    		
            return this.id.equals(((AptitudeCertificateEntity) other).id);   
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

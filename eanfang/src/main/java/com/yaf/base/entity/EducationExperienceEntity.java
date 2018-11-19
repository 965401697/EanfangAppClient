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
 * @ClassName: EducationExperienceEntity 
 * @Description:教育经历表
 * @author LCH
 * @date 2018年10月9日 下午8:28:13 
 *
 */
@TableName(value = "tech_education_experience" )
@Getter
@Setter
public class EducationExperienceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	//主键
	//@TableField(value = "id")
	//数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	//所属的acc_id
	//@TableField(value = "acc_id")
	private Long accId;
	//所属的user_id
	//@TableField(value = "user_id")
	private Long userId;
	//学校名称
	//@TableField(value = "school_name")
	private String schoolName;
	//专业名称
	//@TableField(value = "major_name")
	private String majorName;
	//学历or文凭
	//@TableField(value = "diploma")
	private Integer diploma;
	//证书编号
	//@TableField(value = "certificate_number")
	private String certificateNumber;
	//开始时间
	//@TableField(value = "begin_time")
	private Date beginTime;
	//结束时间
	//@TableField(value = "end_time")
	private Date endTime;
	//证书照片
	//@TableField(value = "certificate_pics")
	private String certificatePics;
	//教育证书类型(0=技师的教育经历，1=专家的教育经历）
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
	 * 设置：学校名称
	 */
	public void setSchoolName(String schoolName) {
	    this.schoolName = schoolName;
	}
	
	/**
	 * 获取：学校名称
	 */
	public String getSchoolName() {
	    return schoolName;
	}
	    /**
	 * 设置：专业名称
	 */
	public void setMajorName(String majorName) {
	    this.majorName = majorName;
	}
	
	/**
	 * 获取：专业名称
	 */
	public String getMajorName() {
	    return majorName;
	}
	    /**
	 * 设置：学历or文凭
	 */
	public void setDiploma(Integer diploma) {
	    this.diploma = diploma;
	}
	
	/**
	 * 获取：学历or文凭
	 */
	public Integer getDiploma() {
	    return diploma;
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
	 * 设置：开始时间
	 */
	public void setBeginTime(Date beginTime) {
	    this.beginTime = beginTime;
	}
	
	/**
	 * 获取：开始时间
	 */
	public Date getBeginTime() {
	    return beginTime;
	}
	    /**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
	    this.endTime = endTime;
	}
	
	/**
	 * 获取：结束时间
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
	 * 设置：教育证书类型(0=技师的教育经历，1=专家的教育经历）
	 */
	public void setType(Integer type) {
	    this.type = type;
	}
	
	/**
	 * 获取：教育证书类型(0=技师的教育经历，1=专家的教育经历）
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
    	if (other instanceof EducationExperienceEntity) {
    		if(this.id == null || other == null)
    			return false;
    		
            return this.id.equals(((EducationExperienceEntity) other).id);   
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

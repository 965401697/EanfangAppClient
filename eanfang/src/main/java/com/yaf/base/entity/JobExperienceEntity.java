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
 * @ClassName: JobExperienceEntity 
 * @Description:工作经历表
 * @author LCH
 * @date 2018年10月9日 下午8:31:57 
 *
 */
@TableName(value = "tech_job_experience" )
@Getter
@Setter
public class JobExperienceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	//主键
	//@TableField(value = "id")
	//数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
	@TableId(value = "id" , type = IdType.AUTO)
	private Long id;
	//所属的acc_id
	//@TableField(value = "acc_id")
	private Long accId;
	//所属的user_id
	//@TableField(value = "user_id")
	private Long userId;
	//公司名称
	//@TableField(value = "company_name")
	private String companyName;
	//职位
	//@TableField(value = "job")
	private String job;
	//工作地点
	//@TableField(value = "workplace")
	private String workplace;
	//开始时间
	//@TableField(value = "begin_time")
	private Date beginTime;
	//结束时间
	//@TableField(value = "end_time")
	private Date endTime;
	//工作描述
	//@TableField(value = "job_intro")
	private String jobIntro;
	//名片或工牌
	//@TableField(value = "card_pics")
	private String cardPics;
	//类型(0=技师的工作经历，1=专家的工作经历)
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
	 * 设置：公司名称
	 */
	public void setCompanyName(String companyName) {
	    this.companyName = companyName;
	}
	
	/**
	 * 获取：公司名称
	 */
	public String getCompanyName() {
	    return companyName;
	}
	    /**
	 * 设置：职位
	 */
	public void setJob(String job) {
	    this.job = job;
	}
	
	/**
	 * 获取：职位
	 */
	public String getJob() {
	    return job;
	}
	    /**
	 * 设置：工作地点
	 */
	public void setWorkplace(String workplace) {
	    this.workplace = workplace;
	}
	
	/**
	 * 获取：工作地点
	 */
	public String getWorkplace() {
	    return workplace;
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
	 * 设置：工作描述
	 */
	public void setJobIntro(String jobIntro) {
	    this.jobIntro = jobIntro;
	}
	
	/**
	 * 获取：工作描述
	 */
	public String getJobIntro() {
	    return jobIntro;
	}
	    /**
	 * 设置：名片或工牌
	 */
	public void setCardPics(String cardPics) {
	    this.cardPics = cardPics;
	}
	
	/**
	 * 获取：名片或工牌
	 */
	public String getCardPics() {
	    return cardPics;
	}
	    /**
	 * 设置：类型(0=技师的工作经历，1=专家的工作经历)
	 */
	public void setType(Integer type) {
	    this.type = type;
	}
	
	/**
	 * 获取：类型(0=技师的工作经历，1=专家的工作经历)
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
    	if (other instanceof JobExperienceEntity) {
    		if(this.id == null || other == null) {
                return false;
            }
    		
            return this.id.equals(((JobExperienceEntity) other).id);   
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

package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 提问表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-01-23 13:05:45
 */
@TableName(value = "ask_questions")
public class AskQuestionsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //问题id
    //@TableField(value = "question_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "question_id", type = IdType.AUTO)
    private Long questionId;
    //提问者user_id
    //@TableField(value = "question_user_id")
    private Long questionUserId;
    //提问者公司id
    //@TableField(value = "question_company_id")
    private Long questionCompanyId;
    //提问者总公司id
    //@TableField(value = "question_top_company_id")
    private Long questionTopCompanyId;
    //提问创建时间
    //@TableField(value = "question_create_date")
    private Date questionCreateDate;
    //设备编码
    //@TableField(value = "data_code")
    private String dataCode;
    //品牌编码
    //@TableField(value = "model_code")
    private String modelCode;
    //内容
    //@TableField(value = "question_content")
    private String questionContent;
    //图片url
    //@TableField(value = "question_pics")
    private String questionPics;
    //浏览次数
    //@TableField(value = "question_view_count")
    private Long questionViewCount;
    //故障简述
    //@TableField(value = "question_sketch")
    private String questionSketch;
    //故障类型id（基础数据表）
    //@TableField(value = "failure_type_id")
    private String failureTypeId;
    //故障类型编码（基础数据表）
    //@TableField(value = "business_one_code")
    private String businessOneCode;
    //问题状态，0正常，1禁用，2删除
    //@TableField(value = "question_status")
    private Integer questionStatus;
    //故障列表id
    //@TableField(value = "question_status")
    private Long deviceFailureId;//免费提问由integer转换成Long


    //@Getter
   // @Setter
    //@TableField(exist = false)
    private ExpertsCertificationEntity expertsCertification;

    public ExpertsCertificationEntity getExpertsCertification() {
        return expertsCertification;
    }

    public void setExpertsCertification(ExpertsCertificationEntity expertsCertification) {
        this.expertsCertification = expertsCertification;
    }

    public Long getDeviceFailureId() {
        return deviceFailureId;
    }

    public void setDeviceFailureId(Long deviceFailureId) {
        this.deviceFailureId = deviceFailureId;
    }

    /**
     * 获取：问题状态
     */
    public Integer getQuestionStatus() {
        return questionStatus;
    }

    /**
     * 设置：问题状态
     */
    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    /**
     * 设置：问题id
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取：问题id
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 设置：提问者user_id
     */
    public void setQuestionUserId(Long questionUserId) {
        this.questionUserId = questionUserId;
    }

    /**
     * 获取：提问者user_id
     */
    public Long getQuestionUserId() {
        return questionUserId;
    }

    /**
     * 设置：提问者公司id
     */
    public void setQuestionCompanyId(Long questionCompanyId) {
        this.questionCompanyId = questionCompanyId;
    }

    /**
     * 获取：提问者公司id
     */
    public Long getQuestionCompanyId() {
        return questionCompanyId;
    }

    /**
     * 设置：提问者总公司id
     */
    public void setQuestionTopCompanyId(Long questionTopCompanyId) {
        this.questionTopCompanyId = questionTopCompanyId;
    }

    /**
     * 获取：提问者总公司id
     */
    public Long getQuestionTopCompanyId() {
        return questionTopCompanyId;
    }

    /**
     * 设置：提问创建时间
     */
    public void setQuestionCreateDate(Date questionCreateDate) {
        this.questionCreateDate = questionCreateDate;
    }

    /**
     * 获取：提问创建时间
     */
    public Date getQuestionCreateDate() {
        return questionCreateDate;
    }

    /**
     * 设置：设备编码
     */
    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    /**
     * 获取：设备编码
     */
    public String getDataCode() {
        return dataCode;
    }

    /**
     * 设置：品牌编码
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    /**
     * 获取：品牌编码
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * 设置：内容
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * 获取：内容
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * 设置：图片url
     */
    public void setQuestionPics(String questionPics) {
        this.questionPics = questionPics;
    }

    /**
     * 获取：图片url
     */
    public String getQuestionPics() {
        return questionPics;
    }

    /**
     * 设置：浏览次数
     */
    public void setQuestionViewCount(Long questionViewCount) {
        this.questionViewCount = questionViewCount;
    }

    /**
     * 获取：浏览次数
     */
    public Long getQuestionViewCount() {
        return questionViewCount;
    }

    /**
     * 设置：故障简述
     */
    public void setQuestionSketch(String questionSketch) {
        this.questionSketch = questionSketch;
    }

    /**
     * 获取：故障简述
     */
    public String getQuestionSketch() {
        return questionSketch;
    }

    /**
     * 设置：故障类型id（基础数据表）
     */
    public void setFailureTypeId(String failureTypeId) {
        this.failureTypeId = failureTypeId;
    }

    /**
     * 获取：故障类型id（基础数据表）
     */
    public String getFailureTypeId() {
        return failureTypeId;
    }

    /**
     * 设置：故障类型编码（基础数据表）
     */
    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    /**
     * 获取：故障类型编码（基础数据表）
     */
    public String getBusinessOneCode() {
        return businessOneCode;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AskQuestionsEntity) {
            if (this.questionId == null || other == null)
                return false;

            return this.questionId.equals(((AskQuestionsEntity) other).questionId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
        return result;
    }
}

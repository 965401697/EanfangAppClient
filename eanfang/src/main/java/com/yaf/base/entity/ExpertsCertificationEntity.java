package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 专家认证注册表信息
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-06 10:36:26
 */
@TableName(value = "experts_certification")
public class ExpertsCertificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //所属的user_id
    //@TableField(value = "user_id")
    private Long userId;
    //所属的acc_id
    //@TableField(value = "acc_id")
    private Long accId;
    //专家名称
    //@TableField(value = "expert_name")
    private String expertName;
    //专家联系电话
    //@TableField(value = "phoneNumber")
    private String phonenumber;
    //收款类型（'1':支付宝 '2':微信 '3':银行卡）
    //@TableField(value = "pay_type")
    private Integer payType;
    //收款账号
    //@TableField(value = "pay_account")
    private String payAccount;
    //从业年限
    //@TableField(value = "working_age")
    private Integer workingAge;
    //认证类型(0:行业专家 1：厂家售后专家)
    //@TableField(value = "approve_type")
    private Integer approveType;
    //性别
    private Integer gender;
    //系统类别
    //@TableField(value = "system_type")
    private Integer systemType;
    //职称证书
    //@TableField(value = "job_LCE")
    private Integer jobLce;
    //职称级别(0一级,1二级,2三级,3四级,4五级)
    //@TableField(value = "job_level")
    private Integer jobLevel;
    //就职单位
    //@TableField(value = "company")
    private String company;
    //厂商名称
    private String brandName;
    //负责品牌（对应sys_base_data表的data_code之间逗号分隔）
    //@TableField(value = "responsible_brand")
    private String responsibleBrand;
    //授权书-URL
    //@TableField(value = "impower_url")
    private String impowerUrl;
    //身份证号码
    //@TableField(value="id_card")
    private String idCard;
    //身份证正面
    //@TableField(value = "id_card_front")
    private String idCardFront;
    //身份证背面
    //@TableField(value = "id_card_side")
    private String idCardSide;
    //手持身份证
    //@TableField(value = "id_card_hand")
    private String idCardHand;
    //职称证书-URL
    //@TableField(value = "job_LCE_URL")
    private String jobLceUrl;
    //个人简介
    //@TableField(value = "intro")
    private String intro;
    //0未认证, 1等待认证，2认证通过，3认证拒绝，4禁用，5删除
    //@TableField(value = "status")
    private Integer status;
    //头像照片
    //@TableField(value = "avatar_photo")
    private String avatarPhoto;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //更新时间
    //@TableField(value = "update_time")
    private Date updateTime;
    //认证时间
    //@TableField(value = "approve_time")
    private Date approveTime;
    //认证操作人员
    //@TableField(value = "approve_user_name")
    private String approveUserName;
    //认证备注信息（审核意见）
    //@TableField(value = "approve_message")
    private String approveMessage;

    /**
     * 设置：主键自增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键自增
     */
    public Long getId() {
        return id;
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
     * 设置：专家名称
     */
    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取：专家名称
     */
    public String getExpertName() {
        return expertName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 设置：专家联系电话
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * 获取：专家联系电话
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * 设置：收款类型（'1':支付宝 '2':微信 '3':银行卡）
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取：收款类型（'1':支付宝 '2':微信 '3':银行卡）
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置：收款账号
     */
    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    /**
     * 获取：收款账号
     */
    public String getPayAccount() {
        return payAccount;
    }

    /**
     * 设置：从业年限
     */
    public void setWorkingAge(Integer workingAge) {
        this.workingAge = workingAge;
    }

    /**
     * 获取：从业年限
     */
    public Integer getWorkingAge() {
        return workingAge;
    }

    /**
     * 设置：认证类型(0:行业专家 1：厂家售后专家)
     */
    public void setApproveType(Integer approveType) {
        this.approveType = approveType;
    }

    /**
     * 获取：认证类型(0:行业专家 1：厂家售后专家)
     */
    public Integer getApproveType() {
        return approveType;
    }

    /**
     * 设置：系统类别
     */
    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    /**
     * 获取：系统类别
     */
    public Integer getSystemType() {
        return systemType;
    }

    /**
     * 设置：职称证书
     */
    public void setJobLce(Integer jobLce) {
        this.jobLce = jobLce;
    }

    /**
     * 获取：职称证书
     */
    public Integer getJobLce() {
        return jobLce;
    }

    /**
     * 设置：职称级别(0一级,1二级,2三级,3四级,4五级)
     */
    public void setJobLevel(Integer jobLevel) {
        this.jobLevel = jobLevel;
    }

    /**
     * 获取：职称级别(0一级,1二级,2三级,3四级,4五级)
     */
    public Integer getJobLevel() {
        return jobLevel;
    }

    /**
     * 设置：就职单位
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 获取：就职单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置：负责品牌（对应sys_base_data表的data_code之间逗号分隔）
     */
    public void setResponsibleBrand(String responsibleBrand) {
        this.responsibleBrand = responsibleBrand;
    }

    /**
     * 获取：负责品牌（对应sys_base_data表的data_code之间逗号分隔）
     */
    public String getResponsibleBrand() {
        return responsibleBrand;
    }

    /**
     * 设置：授权书-URL
     */
    public void setImpowerUrl(String impowerUrl) {
        this.impowerUrl = impowerUrl;
    }

    /**
     * 获取：授权书-URL
     */
    public String getImpowerUrl() {
        return impowerUrl;
    }

    /**
     * 设置：身份证正面
     */
    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    /**
     * 获取：身份证正面
     */
    public String getIdCardFront() {
        return idCardFront;
    }

    /**
     * 设置：身份证背面
     */
    public void setIdCardSide(String idCardSide) {
        this.idCardSide = idCardSide;
    }

    /**
     * 获取：身份证背面
     */
    public String getIdCardSide() {
        return idCardSide;
    }

    /**
     * 设置：手持身份证
     */
    public void setIdCardHand(String idCardHand) {
        this.idCardHand = idCardHand;
    }

    /**
     * 获取：手持身份证
     */
    public String getIdCardHand() {
        return idCardHand;
    }

    /**
     * 设置：职称证书-URL
     */
    public void setJobLceUrl(String jobLceUrl) {
        this.jobLceUrl = jobLceUrl;
    }

    /**
     * 获取：职称证书-URL
     */
    public String getJobLceUrl() {
        return jobLceUrl;
    }

    /**
     * 设置：个人简介
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * 获取：个人简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置：0未认证, 1等待认证，2认证通过，3认证拒绝，4禁用，5删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：0未认证, 1等待认证，2认证通过，3认证拒绝，4禁用，5删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：头像照片
     */
    public void setAvatarPhoto(String avatarPhoto) {
        this.avatarPhoto = avatarPhoto;
    }

    /**
     * 获取：头像照片
     */
    public String getAvatarPhoto() {
        return avatarPhoto;
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
     * 设置：更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：身份证号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置：身份证号码
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置：认证时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    /**
     * 获取：认证时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 设置：认证操作人员
     */
    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    /**
     * 获取：认证操作人员
     */
    public String getApproveUserName() {
        return approveUserName;
    }

    /**
     * 设置：认证备注信息（审核意见）
     */
    public void setApproveMessage(String approveMessage) {
        this.approveMessage = approveMessage;
    }

    /**
     * 获取：认证备注信息（审核意见）
     */
    public String getApproveMessage() {
        return approveMessage;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExpertsCertificationEntity) {
            if (this.id == null || other == null)
                return false;

            return this.id.equals(((ExpertsCertificationEntity) other).id);
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

    @Getter
    @Setter
    @TableField(exist = false)
    private AccountEntity accountEntity;

    @Getter
    @Setter
    @TableField(exist = false)
    private UserEntity userEntity;


}

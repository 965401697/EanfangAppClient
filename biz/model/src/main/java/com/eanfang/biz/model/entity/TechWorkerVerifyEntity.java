package com.eanfang.biz.model.entity;


import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 技师认证信息
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2018-01-10 10:30:14
 */

public class TechWorkerVerifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int isEnable;

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    private Long id;

    //所属的acc_id
    //@TableField(value = "acc_id")
    private Long accId;

    //所属的user_id
    //@TableField(value = "user_id")
    private Long userId;

    //紧急联系人联系人姓名
    //@TableField(value = "contact_name")
    private String contactName;

    //紧急联系人电话
    //@TableField(value = "contact_phone")
    private String contactPhone;

    //pay_type
    //@TableField(value = "pay_type")
    private Integer payType;

    //支付账号
    //@TableField(value = "pay_account")
    private String payAccount;

    //working_level
    //@TableField(value = "working_level")
    private Integer workingLevel;

    //working_year
    //@TableField(value = "working_year")
    private Integer workingYear;

    //头像
    //@TableField(value = "avatar_photo")
    @Getter
    @Setter
    private String avatarPhoto;

    @Override
    public String toString() {
        return "TechWorkerVerifyEntity{" +
                "isEnable=" + isEnable +
                ", id=" + id +
                ", accId=" + accId +
                ", userId=" + userId +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", payType=" + payType +
                ", payAccount='" + payAccount + '\'' +
                ", workingLevel=" + workingLevel +
                ", workingYear=" + workingYear +
                ", avatarPhoto='" + avatarPhoto + '\'' +
                ", crimePic='" + crimePic + '\'' +
                ", idCardFront='" + idCardFront + '\'' +
                ", idCardSide='" + idCardSide + '\'' +
                ", idCardHand='" + idCardHand + '\'' +
                ", accidentPics='" + accidentPics + '\'' +
                ", intro='" + intro + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", verifyTime=" + verifyTime +
                ", verifyUserName='" + verifyUserName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", idCardName='" + idCardName + '\'' +
                ", idCardGender='" + idCardGender + '\'' +
                ", idCardBirth='" + idCardBirth + '\'' +
                ", verifyMessage='" + verifyMessage + '\'' +
                ", idCardNum='" + idCardNum + '\'' +
                ", accountEntity=" + accountEntity +
                ", userEntity=" + userEntity +
                '}';
    }

    //无犯罪证明图片
    //@TableField(value = "crime_pic")
    private String crimePic;

    //身份证正面图片
    //@TableField(value = "id_card_front")
    private String idCardFront;

    //身份证反面
    //@TableField(value = "id_card_side")
    private String idCardSide;

    //手持身份证图片
    //@TableField(value = "id_card_hand")
    private String idCardHand;

    //accident_pics
    //@TableField(value = "accident_pics")
    private String accidentPics;


    //个人简介
    //@TableField(value = "intro")
    private String intro;

    //0认证中，1认证通过，2认证拒绝，3禁用/删除
    //@TableField(value = "status")
    private Integer status;

    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    //更新时间
    @Getter
    @Setter
    //@TableField(value = "update_time")
    private Date updateTime;


    //认证时间
    //@TableField(value = "verify_time")
    private Date verifyTime;

    //认证操作人员
    //@TableField(value = "verify_user_name")
    private String verifyUserName;
    private String eMail;
    private String idCardName;
    private String idCardGender;
    private String idCardBirth;

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardGender() {
        return idCardGender;
    }

    public void setIdCardGender(String idCardGender) {
        this.idCardGender = idCardGender;
    }

    public String getIdCardBirth() {
        return idCardBirth;
    }

    public void setIdCardBirth(String idCardBirth) {
        this.idCardBirth = idCardBirth;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    //备注信息（审核意见）
    //@TableField(value = "verify_message")
    private String verifyMessage;

    private String idCardNum;

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

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
     * 设置：紧急联系人联系人姓名
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 获取：紧急联系人联系人姓名
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 设置：紧急联系人电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取：紧急联系人电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置：pay_type
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取：pay_type
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置：支付账号
     */
    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    /**
     * 获取：支付账号
     */
    public String getPayAccount() {
        return payAccount;
    }

    /**
     * 设置：working_level
     */
    public void setWorkingLevel(Integer workingLevel) {
        this.workingLevel = workingLevel;
    }

    /**
     * 获取：working_level
     */
    public Integer getWorkingLevel() {
        return workingLevel;
    }

    /**
     * 设置：working_year
     */
    public void setWorkingYear(Integer workingYear) {
        this.workingYear = workingYear;
    }

    /**
     * 获取：working_year
     */
    public Integer getWorkingYear() {
        return workingYear;
    }

    /**
     * 设置：无犯罪证明图片
     */
    public void setCrimePic(String crimePic) {
        this.crimePic = crimePic;
    }

    /**
     * 获取：无犯罪证明图片
     */
    public String getCrimePic() {
        return crimePic;
    }

    /**
     * 设置：身份证正面图片
     */
    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    /**
     * 获取：身份证正面图片
     */
    public String getIdCardFront() {
        return idCardFront;
    }

    /**
     * 设置：身份证反面
     */
    public void setIdCardSide(String idCardSide) {
        this.idCardSide = idCardSide;
    }

    /**
     * 获取：身份证反面
     */
    public String getIdCardSide() {
        return idCardSide;
    }

    /**
     * 设置：手持身份证图片
     */
    public void setIdCardHand(String idCardHand) {//手持身份证图片 idCardHand  , 身份证反面 idCardSide
        this.idCardHand = idCardHand;
    }

    /**
     * 获取：手持身份证图片
     */
    public String getIdCardHand() {
        return idCardHand;
    }


    /**
     * 设置：accident_pics
     */
    public void setAccidentPics(String accidentPics) {
        this.accidentPics = accidentPics;
    }

    /**
     * 获取：accident_pics
     */
    public String getAccidentPics() {
        return accidentPics;
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
     * 设置：0认证中，1认证通过，2认证拒绝，3禁用/删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：0认证中，1认证通过，2认证拒绝，3禁用/删除
     */
    public Integer getStatus() {
        return status;
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
     * 设置：认证时间
     */
    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    /**
     * 获取：认证时间
     */
    public Date getVerifyTime() {
        return verifyTime;
    }

    /**
     * 设置：认证操作人员
     */
    public void setVerifyUserName(String verifyUserName) {
        this.verifyUserName = verifyUserName;
    }

    /**
     * 获取：认证操作人员
     */
    public String getVerifyUserName() {
        return verifyUserName;
    }

    /**
     * 设置：备注信息（审核意见）
     */
    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }

    /**
     * 获取：备注信息（审核意见）
     */
    public String getVerifyMessage() {
        return verifyMessage;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TechWorkerVerifyEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((TechWorkerVerifyEntity) other).id);
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
    /*手工代码写在下面*/

    @Getter
    @Setter
    private AccountEntity accountEntity;

    @Getter
    @Setter
    private UserEntity userEntity;
}

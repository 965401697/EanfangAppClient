package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.UserEntity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 技师认证信息
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2018-01-10 10:30:14
 */

public class TechWorkerVerifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    private Long id;

    //所属的acc_id
    //@TableField(value = "acc_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long accId;

    //所属的user_id
    //@TableField(value = "user_id")
    @Digits(integer = 19, fraction = 0)
    private Long userId;

    //紧急联系人联系人姓名
    //@TableField(value = "contact_name")
    @Size(min = 0, max = 5)
    private String contactName;

    //紧急联系人电话
    //@TableField(value = "contact_phone")
    @Size(min = 0, max = 12)
    private String contactPhone;

    //pay_type
    //@TableField(value = "pay_type")
    @Digits(integer = 3, fraction = 0)
    private Integer payType;

    //支付账号
    //@TableField(value = "pay_account")
    @Size(min = 0, max = 20)
    private String payAccount;

    //working_level
    //@TableField(value = "working_level")
    @Digits(integer = 3, fraction = 0)
    private Integer workingLevel;

    //working_year
    //@TableField(value = "working_year")
    @Digits(integer = 3, fraction = 0)
    private Integer workingYear;

    //头像
    //@TableField(value = "avatar_photo")
    @NotBlank
    @Size(min = 0, max = 255)
    private String avatarPhoto;


    //无犯罪证明图片
    //@TableField(value = "crime_pic")
    @Size(min = 0, max = 50)
    private String crimePic;

    //身份证正面图片
    //@TableField(value = "id_card_front")
    @Size(min = 0, max = 50)
    private String idCardFront;

    //身份证反面
    //@TableField(value = "id_card_side")
    @Size(min = 0, max = 50)
    private String idCardSide;

    //手持身份证图片
    //@TableField(value = "id_card_hand")
    @Size(min = 0, max = 50)
    private String idCardHand;

    //accident_pics
    //@TableField(value = "accident_pics")
    @Size(min = 0, max = 200)
    private String accidentPics;



    //个人简介
    //@TableField(value = "intro")
    @Size(min = 0, max = 500)
    private String intro;

    //0认证中，1认证通过，2认证拒绝，3禁用/删除
    //@TableField(value = "status")
    @Digits(integer = 3, fraction = 0)
    private Integer status;

    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    //更新时间
    //@TableField(value = "update_time")
    private Date updateTime;


    //认证时间
    //@TableField(value = "verify_time")
    private Date verifyTime;

    //认证操作人员
    //@TableField(value = "verify_user_name")
    @Size(min = 0, max = 10)
    private String verifyUserName;

    //备注信息（审核意见）
    //@TableField(value = "verify_message")
    @Size(min = 0, max = 200)
    private String verifyMessage;


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
    public void setIdCardHand(String idCardHand) {
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
    public String toString() {
        return JSON.toJSONString(this);
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

    private AccountEntity accountEntity;

    private UserEntity userEntity;

    public String getAvatarPhoto() {
        return this.avatarPhoto;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public AccountEntity getAccountEntity() {
        return this.accountEntity;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public void setAvatarPhoto(String avatarPhoto) {
        this.avatarPhoto = avatarPhoto;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

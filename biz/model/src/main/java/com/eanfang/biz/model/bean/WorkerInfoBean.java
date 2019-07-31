package com.eanfang.biz.model.bean;

import java.io.Serializable;

import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/1/30  20:13
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Setter
public class WorkerInfoBean implements Serializable {

    /**
     * accId : 948067875447500802
     * accidentPics :
     * contactName : 王五
     * contactPhone : 13810729881
     * crimePic :
     * honorPics :
     * id : 955307375881928706
     * idCardFront :
     * idCardHand :
     * idCardSide :
     * intro : 我是技师我的简单介绍
     * payAccount : 829292201
     * payType : 0
     * status : 0
     * userId : 948067875938234369
     * workingLevel : 0
     * workingYear : 1
     */

    private Long accId;
    private String accidentPics;
    private String contactName;
    private String contactPhone;
    private String crimePic;
    private String honorPics;
    private Long id;
    private String idCardFront;
    private String idCardHand;
    private String idCardSide;
    private String intro;
    private String payAccount;
    private int payType;
    private int status;
    private Long userId;
    private int workingLevel;
    private int workingYear;
    private String avatarPhoto;
    private String idCardNum;
    /**
     * 性别
     */
    private String idCardGender;
    /**
     * 姓名
     */
    private String idCardName;

    public Long getAccId() {
        return accId;
    }

    public String getAccidentPics() {
        return accidentPics == null ? "" : accidentPics;
    }

    public String getContactName() {
        return contactName == null ? "" : contactName;
    }

    public String getContactPhone() {
        return contactPhone == null ? "" : contactPhone;
    }

    public String getCrimePic() {
        return crimePic == null ? "" : crimePic;
    }

    public String getHonorPics() {
        return honorPics == null ? "" : honorPics;
    }

    public Long getId() {
        return id;
    }

    public String getIdCardFront() {
        return idCardFront == null ? "" : idCardFront;
    }

    public String getIdCardHand() {
        return idCardHand == null ? "" : idCardHand;
    }

    public String getIdCardSide() {
        return idCardSide == null ? "" : idCardSide;
    }

    public String getIntro() {
        return intro == null ? "" : intro;
    }

    public String getPayAccount() {
        return payAccount == null ? "" : payAccount;
    }

    public int getPayType() {
        return payType;
    }

    public int getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public int getWorkingLevel() {
        return workingLevel;
    }

    public int getWorkingYear() {
        return workingYear;
    }

    public String getAvatarPhoto() {
        return avatarPhoto == null ? "" : avatarPhoto;
    }

    public String getIdCardNum() {
        return idCardNum == null ? "" : idCardNum;
    }

    public String getIdCardGender() {
        return idCardGender;
    }

    public String getIdCardName() {
        return idCardName;
    }
}


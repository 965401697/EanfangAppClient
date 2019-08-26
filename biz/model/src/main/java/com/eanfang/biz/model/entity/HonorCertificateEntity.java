package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author LCH
 * @ClassName: HonorCertificateEntity
 * @Description:荣誉证书表
 * @date 2018年10月9日 下午8:29:40
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String awardDate;
    //颁发证书照片
    //@TableField(value = "honor_pics")
    private String honorPics;
    //荣誉说明
    //@TableField(value = "intro")
    private String intro;
    //荣誉证书类型(0=技师的荣誉证书，1=专家的荣誉证书)
    //@TableField(value = "type")
    private Integer type;

}

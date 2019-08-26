package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author LCH
 * @ClassName: QualificationCertificateEntity
 * @Description:资质证书表
 * @date 2018年10月9日 下午8:30:58
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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
    private String beginDate;
    //证书失效时间
    private Date endTime;
    private String endDate;
    //证书照片
    private String certificatePics;
    //资质证书类型(0=技师的资质证书，1=专家的资质证书)
    private Integer type;
}

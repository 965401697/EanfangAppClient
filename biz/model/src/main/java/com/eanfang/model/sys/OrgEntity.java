package com.eanfang.model.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class OrgEntity implements Serializable, Cloneable {

    private Long adminUserId;

    //组织机构ID
    private Long orgId;

    //上级机构
    private Long parentOrgId;

    //归属总公司
    private Long topCompanyId;

    //归属公司
    private Long companyId;

    //机构编码
    private String orgCode;

    //层级
    private Integer level;

    //机构名称
    private String orgName;

    //机构类型  0总公司,1分子公司,2部门
    private Integer orgType;

    //排序号
    private Integer sortNum;

    //取值参考verifyStatus
    private Integer verifyStatus;

    //更新人
    private Long updateUser;

    //更新时间
    private Date updateTime;

    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */


    /**
     * 部门归属的公司
     */
    private OrgEntity belongCompany;

    /**
     * 部门顶级公司
     */
    private OrgEntity belongTopCompany;

    private UserEntity updateUserEntity;

    private UserEntity adminUserEntity;

    private List<OrgEntity> children;

    private List<UserEntity> staff;

    private OrgUnitEntity orgUnitEntity;

    private ShopCompanyEntity shopCompanyEntity;

    private Object parentEntity;

    private int countStaff = 0;

    private boolean flag;


}

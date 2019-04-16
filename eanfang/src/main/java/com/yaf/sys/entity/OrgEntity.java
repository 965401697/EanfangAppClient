package com.yaf.sys.entity;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 组织机构
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-11-27 16:34:05
 */

@TableName(value = "sys_org")
public class OrgEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private static final OrgEntity EMPTY = new OrgEntity();

    public static OrgEntity newInstance() {
        return EMPTY.clone();
    }

    @Override
    protected OrgEntity clone() {
        try {
            return (OrgEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    //组织机构ID
    //@TableField(value = "org_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    @TableId(value = "org_id", type = IdType.ID_WORKER)
    private Long orgId;

    //上级机构
    //@TableField(value = "parent_org_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long parentOrgId;

    //归属总公司
    //@TableField(value = "top_company_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long topCompanyId;

    //归属公司
    //@TableField(value = "company_id")
    //@NotNull 公司类节点在提交成功之后才获得company_id
    @Digits(integer = 19, fraction = 0)
    private Long companyId;

    //机构编码
    //@TableField(value = "org_code")
    @NotBlank
    @Size(min = 1, max = 30)
    private String orgCode;

    //层级
    //@TableField(value = "level")
    @NotNull
    @Digits(integer = 10, fraction = 0)
    private Integer level;

    //机构名称
    //@TableField(value = "org_name")
    //@NotBlank
    @Size(min = 0, max = 30)
    private String orgName;

    //机构类型  0总公司,1分子公司,2部门
    //@TableField(value = "org_type")
    @NotNull
    @Digits(integer = 3, fraction = 0)
    private Integer orgType;

    //排序号
    //@TableField(value = "sort_num")
    @Digits(integer = 5, fraction = 0)
    private Integer sortNum;

    //是否认证0未认证号1已认证
    //@TableField(value = "is_verify")
    @Digits(integer = 3, fraction = 0)
    private Integer VerifyStatus;

    public Integer getVerifyStatus() {
        return VerifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        VerifyStatus = verifyStatus;
    }

    /* 当org为公司时，adminUserId为管理员，登录时自动设置*/
    private Long adminUserId;
    //更新人
    //@TableField(value = "update_user")
    @Digits(integer = 19, fraction = 0)
    private Long updateUser;

    //更新时间
    //@TableField(value = "update_time")
    private Date updateTime;

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * 设置：组织机构ID
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取：组织机构ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置：上级机构
     */
    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    /**
     * 获取：上级机构
     */
    public Long getParentOrgId() {
        return parentOrgId;
    }

    /**
     * 设置：归属总公司
     */
    public void setTopCompanyId(Long topCompanyId) {
        this.topCompanyId = topCompanyId;
    }

    /**
     * 获取：归属总公司
     */
    public Long getTopCompanyId() {
        return topCompanyId;
    }

    /**
     * 设置：归属公司
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：归属公司
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * 设置：机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取：机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置：机构名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取：机构名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置：机构类型  0总公司,1分子公司,2部门
     */
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    /**
     * 获取：机构类型  0总公司,1分子公司,2部门
     */
    public Integer getOrgType() {
        return orgType;
    }

    /**
     * 设置：排序号
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * 获取：排序号
     */
    public Integer getSortNum() {
        return sortNum;
    }



    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */


    /**
     * 部门归属的公司
     */
    @TableField(exist = false)
    private OrgEntity belongCompany;

    /**
     * 部门顶级公司
     */
    @TableField(exist = false)
    private OrgEntity belongTopCompany;

    @TableField(exist = false)
    private UserEntity updateUserEntity;


    @TableField(exist = false)
    private List<OrgEntity> children;

    @TableField(exist = false)
    private List<UserEntity> staff;

    @TableField(exist = false)
    private OrgUnitEntity orgUnitEntity;

    @TableField(exist = false)
    private Object parentEntity;

    public void addStaff(UserEntity user) {
        if (staff == null) {
            staff = new LinkedList<UserEntity>();
        }
        if (!staff.contains(user)) {
            staff.add(user);
        }
    }

    public void addChild(OrgEntity child) {
        if (children == null) {
            children = new LinkedList<>();
        }
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof OrgEntity) {
            if (this.orgId == null || other == null) {
                return false;
            }

            return this.orgId.equals(((OrgEntity) other).orgId);
        }
        return false;
    }

    public int calculateLevel() {
        if (orgCode == null) {
            return 0;
        }
        int lv = 1;
        for (int i = 0; i < orgCode.length(); i++) {
            if (orgCode.charAt(i) == '.') {
                lv++;
            }
        }
        return lv;
    }

    public Integer getLevel() {
        if (level != null && level > 0) {
            return level;
        }
        level = calculateLevel();
        return level;
    }


    public OrgEntity getBelongCompany() {
        return this.belongCompany;
    }

    public OrgEntity getBelongTopCompany() {
        return this.belongTopCompany;
    }

    public void setBelongCompany(OrgEntity belongCompany) {
        this.belongCompany = belongCompany;
    }

    public void setBelongTopCompany(OrgEntity belongTopCompany) {
        this.belongTopCompany = belongTopCompany;
    }

    public void setParentEntity(Object parentEntity) {
        this.parentEntity = parentEntity;
    }

    public Long getAdminUserId() {
        return this.adminUserId;
    }

    public Long getUpdateUser() {
        return this.updateUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public UserEntity getUpdateUserEntity() {
        return this.updateUserEntity;
    }

    public List<OrgEntity> getChildren() {
        return this.children;
    }

    public List<UserEntity> getStaff() {
        return this.staff;
    }

    public OrgUnitEntity getOrgUnitEntity() {
        return this.orgUnitEntity;
    }

    public Object getParentEntity() {
        return this.parentEntity;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateUserEntity(UserEntity updateUserEntity) {
        this.updateUserEntity = updateUserEntity;
    }

    public void setChildren(List<OrgEntity> children) {
        this.children = children;
    }

    public void setStaff(List<UserEntity> staff) {
        this.staff = staff;
    }

    public void setOrgUnitEntity(OrgUnitEntity orgUnitEntity) {
        this.orgUnitEntity = orgUnitEntity;
    }
}

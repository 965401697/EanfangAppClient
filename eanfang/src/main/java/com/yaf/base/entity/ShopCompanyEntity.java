package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.BaseDataEntity;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.OrgUnitEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * 安防公司资料及认证信息
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-22 14:22:20
 */
@TableName(value = "shop_company")
@Getter
@Setter
public class ShopCompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //组织机构id
    @TableId(value = "org_id", type = IdType.INPUT)
    private Long orgId;

    //管理员用户 id
    //@TableField(value = " admin_user_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    //@TableField(value = "admin_user_id")
    private Long adminUserId;

    //从业等级
    //@TableField(value = "working_level")
    private Integer workingLevel;
    //从业年限
    //@TableField(value = "working_year")
    private Integer workingYear;
    //法人授权图片
    //@TableField(value = "legal_authorization_pic")
    private String legalAuthorizationPic;
    //荣誉证书，多张图片，逗号分隔
    //@TableField(value = "honor_pics")
    private String honorPics;
    //资质证书，多张，逗号分隔
    //@TableField(value = "aptitude_pics")
    private String aptitudePics;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //报修单数量
    //@TableField(value = "repair_count")
    private Integer repairCount;
    //报装单数量
    //@TableField(value = "install_count")
    private Integer installCount;
    //设计单数量
    //@TableField(value = "design_count")
    private Integer designCount;
    //维保单数量
    //@TableField(value = "maintain_count")
    private Integer maintainCount;

    //接包数量
    //@TableField(value = "receive_count")
    private Integer receiveCount;
    //好评率
    //@TableField(value = "good_rate")
    private Integer goodRate;
    //口碑值
    //@TableField(value = "public_praise")
    private Integer publicPraise;
    //评分项1
    //@TableField(value = "item1")
    private Integer item1;
    //评分项2
    //@TableField(value = "item2")
    private Integer item2;
    //评分项3
    //@TableField(value = "item3")
    private Integer item3;
    //评分项4
    //@TableField(value = "item4")
    private Integer item4;
    //评分项5
    //@TableField(value = "item5")
    private Integer item5;
    //好评个数
    //@TableField(value = "good_num")
    private Integer goodNum;
    //收到评价数量
    //@TableField(value = "evaluate_num")
    private Integer evaluateNum;
    //整体评价（1：好评，2：中评，3：差评）
    //@TableField(value = "general_evaluation")
    private Integer generalEvaluation;
    //是否生产厂商
    //@TableField(value = "is_manufacturer")
    private Integer isManufacturer;
    //是否官网显示（0：不显示，1：显示）
    //@TableField(value = "show_www")
    private Integer showWww;
    // 从业年限
    private int working_level;
    // 能力等级
    private int working_year;
    // 公司类型
    private int is_manufacturer;

    /**
     * 设置：管理员用户 id
     */
    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    /**
     * 获取：管理员用户 id
     */
    public Long getAdminUserId() {
        return adminUserId;
    }

    /**
     * 获取：组织机构id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置：组织机构id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取：从业等级
     */
    public Integer getWorkingLevel() {
        return workingLevel;
    }

    /**
     * 设置：从业等级
     */
    public void setWorkingLevel(Integer workingLevel) {
        this.workingLevel = workingLevel;
    }

    /**
     * 获取：从业年限
     */
    public Integer getWorkingYear() {
        return workingYear;
    }

    /**
     * 设置：从业年限
     */
    public void setWorkingYear(Integer workingYear) {
        this.workingYear = workingYear;
    }

    /**
     * 获取：法人授权图片
     */
    public String getLegalAuthorizationPic() {
        return legalAuthorizationPic;
    }

    /**
     * 设置：法人授权图片
     */
    public void setLegalAuthorizationPic(String legalAuthorizationPic) {
        this.legalAuthorizationPic = legalAuthorizationPic;
    }

    /**
     * 获取：荣誉证书，多张图片，逗号分隔
     */
    public String getHonorPics() {
        return honorPics;
    }

    /**
     * 设置：荣誉证书，多张图片，逗号分隔
     */
    public void setHonorPics(String honorPics) {
        this.honorPics = honorPics;
    }

    /**
     * 获取：资质证书，多张，逗号分隔
     */
    public String getAptitudePics() {
        return aptitudePics;
    }

    /**
     * 设置：资质证书，多张，逗号分隔
     */
    public void setAptitudePics(String aptitudePics) {
        this.aptitudePics = aptitudePics;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：报修数量
     */
    public Integer getRepairCount() {
        return repairCount;
    }

    /**
     * 设置：报修数量
     */
    public void setRepairCount(Integer repairCount) {
        this.repairCount = repairCount;
    }

    /**
     * 获取：报装数量
     */
    public Integer getInstallCount() {
        return installCount;
    }

    /**
     * 设置：报装数量
     */
    public void setInstallCount(Integer installCount) {
        this.installCount = installCount;
    }

    /**
     * 获取：接包数量
     */
    public Integer getReceiveCount() {
        return receiveCount;
    }

    /**
     * 设置：接包数量
     */
    public void setReceiveCount(Integer receiveCount) {
        this.receiveCount = receiveCount;
    }

    /**
     * 获取：好评率
     */
    public Integer getGoodRate() {
        return goodRate;
    }

    /**
     * 设置：好评率
     */
    public void setGoodRate(Integer goodRate) {
        this.goodRate = goodRate;
    }

    /**
     * 获取：口碑值
     */
    public Integer getPublicPraise() {
        return publicPraise;
    }

    /**
     * 设置：口碑值
     */
    public void setPublicPraise(Integer publicPraise) {
        this.publicPraise = publicPraise;
    }

    /**
     * 获取：评分项1
     */
    public Integer getItem1() {
        return item1;
    }

    /**
     * 设置：评分项1
     */
    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    /**
     * 获取：评分项2
     */
    public Integer getItem2() {
        return item2;
    }

    /**
     * 设置：评分项2
     */
    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    /**
     * 获取：评分项3
     */
    public Integer getItem3() {
        return item3;
    }

    /**
     * 设置：评分项3
     */
    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    /**
     * 获取：评分项4
     */
    public Integer getItem4() {
        return item4;
    }

    /**
     * 设置：评分项4
     */
    public void setItem4(Integer item4) {
        this.item4 = item4;
    }

    /**
     * 获取：评分项5
     */
    public Integer getItem5() {
        return item5;
    }

    /**
     * 设置：评分项5
     */
    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    public OrgUnitEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(OrgUnitEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public OrgEntity getOrgEntity() {
        return orgEntity;
    }

    public void setOrgEntity(OrgEntity orgEntity) {
        this.orgEntity = orgEntity;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ShopCompanyEntity) {
            if (this.adminUserId == null || other == null) {
                return false;
            }

            return this.adminUserId.equals(((ShopCompanyEntity) other).adminUserId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adminUserId == null) ? 0 : adminUserId.hashCode());
        return result;
    }



    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    /**
     * 公司组织信息
     */
    @TableField(exist = false)
    private OrgEntity orgEntity;

    /**
     * 公司认证信息
     */
    @TableField(exist = false)
    private OrgUnitEntity companyEntity;

    /**
     * 管理员用户信息
     */
    @TableField(exist = false)
    private UserEntity adminUserEntity;


    @TableField(exist = false)
    private AccountEntity accountEntity;
    @TableField(exist = false)
    private CooperationEntity cooperationEntity;
    @TableField(exist = false)
    private List<BaseDataEntity> baseDataEntityList;


    /**
     * 好评率百分比展示字段
     */
    @TableField(exist = false)
    private BigDecimal goodReputation;

}

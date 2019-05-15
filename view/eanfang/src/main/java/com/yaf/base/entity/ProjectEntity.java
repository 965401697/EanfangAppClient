package com.yaf.base.entity;

import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.OrgEntity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 项目表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:05:54
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity implements Serializable {

    private String address;
    private String areaCode;
    private String buildAssumeUserId;
    private String buildCompanyId;
    private String buildOrgCode;
    private String buildOtherManId;
    private String buildSafeManId;
    private String buildTechniqueManId;
    private String businessOneCode;
    private String createTime;
    private String createUserId;
    private String endTime;
    private String id;
    private String latitude;
    private String longitude;
    private String otherMan;
    private OwnerAssumeUserEntityBean ownerAssumeUserEntity;
    private OrgEntity ownerOrgEntity;
    private String pactCode;
    private int placeId;
    private String projectAssumeUserId;
    private String projectBudget;
    private String projectCompanyId;
    private String projectDuration;
    private String projectIntro;
    private String projectName;
    private int projectNature;
    private String projectOrgCode;
    private String projectPicture;
    private String projectTopCompanyId;
    private String startTime;
    private int status;
    private String supervisorAssumeMan;
    private String supervisorCompany;
    private String supervisorOrgCode;
    private String supervisorOtherMan;
    private String supervisorTopCompanyId;
    private String buildTopCompanyId;
    private String updateTime;
    private String updateUserId;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OwnerAssumeUserEntityBean implements Serializable{

        private String accId;
        private AccountEntity accountEntity;
        private boolean companyAdmin;
        private String companyId;
        private String departmentId;
        private int status;
        private boolean superAdmin;
        private boolean sysAdmin;
        private String topCompanyId;
        private String updateTime;
        private String updateUser;
        private String userId;
        private int userType;


    }

}

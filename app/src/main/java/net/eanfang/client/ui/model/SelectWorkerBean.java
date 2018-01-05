package net.eanfang.client.ui.model;

import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgEntity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SelectWorkerBean implements Serializable {

    /**
     * accId : 936487014348365825
     * accountEntity : {"accId":"936487014348365825","avatar":"0","realName":"张三","subsystemAdmin":false,"superAdmin":false}
     * companyEntity : {"orgName":"家乐福双井公司"}
     * goodRate : 100
     * id : 6
     * lat : 39.929004
     * lon : 116.575179
     * placeCode : 3.11.1.5
     * publicPraise : 0
     * verifyEntity : {"accId":"936487014348365825","realName":"张三","userId":"936487014465806338","workingYear":"5"}
     * verifyId : 5
     */

    private Long accId;
    private AccountEntity accountEntity;
    private OrgEntity companyEntity;
    private int goodRate;
    private Long id;
    private String lat;
    private String lon;
    private String placeCode;
    private int publicPraise;
    private VerifyEntityBean verifyEntity;
    private Long verifyId;
    private Long companyUserId;

    public Long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Long companyUserId) {
        this.companyUserId = companyUserId;
    }

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public OrgEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(OrgEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public int getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(int goodRate) {
        this.goodRate = goodRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public int getPublicPraise() {
        return publicPraise;
    }

    public void setPublicPraise(int publicPraise) {
        this.publicPraise = publicPraise;
    }

    public VerifyEntityBean getVerifyEntity() {
        return verifyEntity;
    }

    public void setVerifyEntity(VerifyEntityBean verifyEntity) {
        this.verifyEntity = verifyEntity;
    }

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }


    public static class VerifyEntityBean implements Serializable {
        /**
         * accId : 936487014348365825
         * realName : 张三
         * userId : 936487014465806338
         * workingYear : 5
         */

        private Long accId;
        private String realName;
        private String workingYear;

        public Long getAccId() {
            return accId;
        }

        public void setAccId(Long accId) {
            this.accId = accId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getWorkingYear() {
            return workingYear;
        }

        public void setWorkingYear(String workingYear) {
            this.workingYear = workingYear;
        }
    }
}


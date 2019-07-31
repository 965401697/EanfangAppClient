package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.ExpertsCertificationEntity;
import com.eanfang.biz.model.entity.BaseDataEntity;

import java.util.List;

/**
 * Created by Our on 2019/1/17.
 */

public class ExpertVerifySkillBean {
    private ExpertsCertificationEntity expertVerify;
    private List<BaseDataEntity> baseData2userList;


    public ExpertsCertificationEntity getExpertVerify() {
        return expertVerify;
    }

    public void setExpertVerify(ExpertsCertificationEntity expertVerify) {
        this.expertVerify = expertVerify;
    }

    public List<BaseDataEntity> getBaseData2userList() {
        return baseData2userList;
    }


    public void setBaseData2userList(List<BaseDataEntity> baseData2userList) {
        this.baseData2userList = baseData2userList;
    }


}

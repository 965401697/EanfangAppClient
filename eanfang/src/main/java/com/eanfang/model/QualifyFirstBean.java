package com.eanfang.model;

import com.yaf.sys.entity.BaseDataEntity;
import com.yaf.sys.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2018/10/26
 * @description
 */

public class QualifyFirstBean implements Serializable {

    private OrgUnitEntity orgUnit;
    private List<BaseDataEntity> company2baseDataList;

    public OrgUnitEntity getOrgUnit() {
        return this.orgUnit;
    }

    public List<BaseDataEntity> getCompany2baseDataList() {
        return this.company2baseDataList;
    }

    public void setOrgUnit(OrgUnitEntity orgUnit) {
        this.orgUnit = orgUnit;
    }

    public void setCompany2baseDataList(List<BaseDataEntity> company2baseDataList) {
        this.company2baseDataList = company2baseDataList;
    }
}

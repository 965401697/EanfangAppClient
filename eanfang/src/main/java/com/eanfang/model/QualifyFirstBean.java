package com.eanfang.model;

import com.yaf.sys.entity.BaseDataEntity;
import com.yaf.sys.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2018/10/26
 * @description
 */

@Getter
@Setter
public class QualifyFirstBean implements Serializable {

    private OrgUnitEntity orgUnit;
    private List<BaseDataEntity> company2baseDataList;

}

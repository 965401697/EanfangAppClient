package com.eanfang.model.datastatistics;

import com.yaf.sys.entity.OrgEntity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/17$  18:23$
 */
@Getter
@Setter
public class DataStatisticsCompany implements Serializable {


    /**
     * countStaff : 0
     * level : 2
     * orgCode : c.c2
     * orgId : 1072683738892140545
     * orgName : 我的子公司
     * orgUnitEntity : {"orgId":"1072683738892140545","unitType":2}
     * parentOrgId : 985770118343135234
     */

    private int countStaff;
    private int level;
    private String orgCode;
    private String orgId;
    private String orgName;
    private OrgEntity orgUnitEntity;
    private String parentOrgId;
}

package com.eanfang.biz.model.bean.monitor;

import com.eanfang.biz.model.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/19
 * @description
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MonitorListBean implements Serializable {
    private OrgUnitEntity orgUnit;
    private List<RealTimeGroupEntity> groupList;
}

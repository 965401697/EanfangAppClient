package com.eanfang.biz.model.bean.monitor;

import com.eanfang.biz.model.entity.OrgUnitEntity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/19
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitorGroupListBean implements Serializable {
    private OrgUnitEntity orgUnit;

    private Long groupId;
    private String groupName;
    private String parentGroupName;
    private int deviceCount;
    private boolean isHaveSubGroup = false;
    private boolean isFirstHaveDevice = false;
    private boolean isChecked = false;
}

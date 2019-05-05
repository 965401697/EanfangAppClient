package com.eanfang.model.sys;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseDataEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    //数据标识
    private Integer dataId;

    //编码
    private String dataCode;

    //数据名
    private String dataName;

    //排序号
    private Integer sortNum;

    //备注
    private String remarkInfo;


    /*手工代码写在下面*/

    private Long parentId;

    private boolean check;

    private Integer dataType;

    public Integer getDataType() {

        if (dataType != null) {
            return dataType;
        }

        if (dataCode == null) {
            return null;
        }

        int pos = dataCode.indexOf(".");

        if (pos == -1) {
            dataType = Integer.parseInt(dataCode);
        } else {
            dataType = Integer.parseInt(dataCode.substring(0, pos));
        }
        return dataType;
    }

    private Integer level;

    private int calculateLevel() {
        if (dataCode == null) {
            return 0;
        }
        int lv = 1;
        for (int i = 0; i < dataCode.length(); i++) {
            if (dataCode.charAt(i) == '.') {
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

    private List<BaseDataEntity> children = null;

    public void addChild(BaseDataEntity child) {
        if (children == null) {
            children = new LinkedList<>();
        }
        if (!children.contains(child)) {
            children.add(child);
        }
    }


}

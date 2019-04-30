package com.eanfang.model.sys;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
public class BaseDataEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    //数据标识
    private Long dataId;

    //编码
    private String dataCode;

    //数据名
    private String dataName;

    //排序号
    private Integer sortNum;

    //备注
    private String remarkInfo;


    /*手工代码写在下面*/

}

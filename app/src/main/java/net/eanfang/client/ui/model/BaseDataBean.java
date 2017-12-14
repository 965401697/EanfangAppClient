package net.eanfang.client.ui.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2017/12/11  17:05
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Getter
@Setter
public class BaseDataBean implements Serializable {

    /**
     * dataCode : 1
     * dataId : 1
     * dataName : 系统类别
     * dataType : 1
     * remarkInfo :
     * sortNum : 0
     */

    private String dataCode;
    private int dataId;
    private String dataName;
    private int dataType;
    private String remarkInfo;
    private int sortNum;
    private int level;
}


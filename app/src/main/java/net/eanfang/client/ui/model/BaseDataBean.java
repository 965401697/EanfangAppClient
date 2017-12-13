package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2017/12/11  17:05
 * @email houzhongzhou@yeah.net
 * @desc
 */
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

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}


package com.eanfang.model;

import com.yaf.sys.entity.BaseDataEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/12/11  17:05
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class BaseDataBean implements Serializable {

    private List<BaseDataEntity> data;
    private String MD5;

    public List<BaseDataEntity> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<BaseDataEntity> data) {
        this.data = data;
    }

    public String getMD5() {
        return MD5 == null ? "" : MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }
}


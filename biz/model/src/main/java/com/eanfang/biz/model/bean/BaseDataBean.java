package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.BaseDataEntity;

import java.io.Serializable;
import java.util.List;

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

    private List<BaseDataEntity> data;
    private String MD5;

    public String getMD5() {
        return MD5 == null ? "" : MD5;
    }
}


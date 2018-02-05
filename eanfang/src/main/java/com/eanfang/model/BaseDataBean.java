package com.eanfang.model;

import com.yaf.sys.entity.BaseDataEntity;

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
}


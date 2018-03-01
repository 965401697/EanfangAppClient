package com.eanfang.model;

import com.yaf.base.entity.RepairFailureEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/2/28  14:23
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Setter
@Getter
public class LeaveBugBean implements Serializable {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<RepairFailureEntity> list;
}

package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/2/9  16:55
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Getter
@Setter
public class NoticeListBean implements Serializable {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<NoticeEntity> list;
}

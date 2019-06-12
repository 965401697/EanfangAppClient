package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/10
 * @description
 */
@Getter
@Setter
public class PageBean<T> implements Serializable {
    private Integer currPage;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private List<T> list;
}

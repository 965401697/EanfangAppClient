package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/10
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> implements Serializable {
    private Integer currPage;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;
    private List<T> list;
}

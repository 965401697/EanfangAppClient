package com.eanfang.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : lin
 * @date : 2017/11/20 20:11
 * @description :
 */
@Getter
@Setter
public class QueryEntry implements Serializable{

    /**
     * equals =
     */
    private Map<String, String> equals = new HashMap<>();
    /**
     * notEquals  !=
     */
    private Map<String, String> notEquals = new HashMap<>();
    /**
     * gt  >
     */
    private Map<String, String> gt = new HashMap<>();
    /**
     * gtEquals  >=
     */
    private Map<String, String> gtEquals = new HashMap<>();
    /**
     * lt  <
     */
    private Map<String, String> lt = new HashMap<>();
    /**
     * ltEquals  <=
     */
    private Map<String, String> ltEquals = new HashMap<>();
    /**
     * like  like ''
     */
    private Map<String, String> like = new HashMap<>();
    /**
     * notLike   not like ''
     */
    private Map<String, String> notLike = new HashMap<>();
    /**
     * isNull  is null
     */
    private Map<String, String> isNull = new HashMap<>();
    /**
     * notNull   not null
     */
    private Map<String, String> notNull = new HashMap<>();
    /**
     * isIn  in()
     */
    private Map<String, List<String>> isIn = new HashMap<>();
    /**
     * notIn  not in()
     */
    private Map<String, List<String>> notIn = new HashMap<>();

    /**
     * groupBy   group by
     */
    private List<String> groupBy = new ArrayList<>();


    /**
     * orderBy   order by  desc
     */
    private Map<String, String> orderBy = new HashMap<>();

    /**
     * 页
     */
    private Integer page = null;
    /**
     * 行
     */
    private Integer size = null;

    public Map<String, String> getNotEquals() {
        return notEquals;
    }

    public void setNotEquals(Map<String, String> notEquals) {
        this.notEquals = notEquals;
    }

    public Map<String, String> getGt() {
        return gt;
    }

    public void setGt(Map<String, String> gt) {
        this.gt = gt;
    }

    public Map<String, String> getGtEquals() {
        return gtEquals;
    }

    public void setGtEquals(Map<String, String> gtEquals) {
        this.gtEquals = gtEquals;
    }

    public Map<String, String> getLt() {
        return lt;
    }

    public void setLt(Map<String, String> lt) {
        this.lt = lt;
    }

    public Map<String, String> getLtEquals() {
        return ltEquals;
    }

    public void setLtEquals(Map<String, String> ltEquals) {
        this.ltEquals = ltEquals;
    }

    public Map<String, String> getLike() {
        return like;
    }

    public void setLike(Map<String, String> like) {
        this.like = like;
    }

    public Map<String, String> getNotLike() {
        return notLike;
    }

    public void setNotLike(Map<String, String> notLike) {
        this.notLike = notLike;
    }

    public Map<String, String> getIsNull() {
        return isNull;
    }

    public void setIsNull(Map<String, String> isNull) {
        this.isNull = isNull;
    }

    public Map<String, String> getNotNull() {
        return notNull;
    }

    public void setNotNull(Map<String, String> notNull) {
        this.notNull = notNull;
    }

    public Map<String, List<String>> getIsIn() {
        return isIn;
    }

    public void setIsIn(Map<String, List<String>> isIn) {
        this.isIn = isIn;
    }

    public Map<String, List<String>> getNotIn() {
        return notIn;
    }

    public void setNotIn(Map<String, List<String>> notIn) {
        this.notIn = notIn;
    }

    public List<String> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<String> groupBy) {
        this.groupBy = groupBy;
    }

    public Map<String, String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Map<String, String> orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Map<String, String> getEquals() {
        return equals;
    }

    public void setEquals(Map<String, String> equals) {
        this.equals = equals;
    }
}

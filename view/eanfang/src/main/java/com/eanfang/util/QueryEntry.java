package com.eanfang.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lin
 * @date : 2017/11/20 20:11
 * @description :
 */
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

    public Map<String, String> getEquals() {
        return this.equals;
    }

    public Map<String, String> getNotEquals() {
        return this.notEquals;
    }

    public Map<String, String> getGt() {
        return this.gt;
    }

    public Map<String, String> getGtEquals() {
        return this.gtEquals;
    }

    public Map<String, String> getLt() {
        return this.lt;
    }

    public Map<String, String> getLtEquals() {
        return this.ltEquals;
    }

    public Map<String, String> getLike() {
        return this.like;
    }

    public Map<String, String> getNotLike() {
        return this.notLike;
    }

    public Map<String, String> getIsNull() {
        return this.isNull;
    }

    public Map<String, String> getNotNull() {
        return this.notNull;
    }

    public Map<String, List<String>> getIsIn() {
        return this.isIn;
    }

    public Map<String, List<String>> getNotIn() {
        return this.notIn;
    }

    public List<String> getGroupBy() {
        return this.groupBy;
    }

    public Map<String, String> getOrderBy() {
        return this.orderBy;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setEquals(Map<String, String> equals) {
        this.equals = equals;
    }

    public void setNotEquals(Map<String, String> notEquals) {
        this.notEquals = notEquals;
    }

    public void setGt(Map<String, String> gt) {
        this.gt = gt;
    }

    public void setGtEquals(Map<String, String> gtEquals) {
        this.gtEquals = gtEquals;
    }

    public void setLt(Map<String, String> lt) {
        this.lt = lt;
    }

    public void setLtEquals(Map<String, String> ltEquals) {
        this.ltEquals = ltEquals;
    }

    public void setLike(Map<String, String> like) {
        this.like = like;
    }

    public void setNotLike(Map<String, String> notLike) {
        this.notLike = notLike;
    }

    public void setIsNull(Map<String, String> isNull) {
        this.isNull = isNull;
    }

    public void setNotNull(Map<String, String> notNull) {
        this.notNull = notNull;
    }

    public void setIsIn(Map<String, List<String>> isIn) {
        this.isIn = isIn;
    }

    public void setNotIn(Map<String, List<String>> notIn) {
        this.notIn = notIn;
    }

    public void setGroupBy(List<String> groupBy) {
        this.groupBy = groupBy;
    }

    public void setOrderBy(Map<String, String> orderBy) {
        this.orderBy = orderBy;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

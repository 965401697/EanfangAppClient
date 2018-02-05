package com.eanfang.util;

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
public class QueryEntry {

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
}

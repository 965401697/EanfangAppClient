package com.eanfang.biz.rds.base;

import com.alibaba.fastjson.JSON;
import com.zchu.rxcache.stategy.IStrategy;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
//@Setter
@Accessors(chain = true)
public class CacheModel {

    /**
     * 缓存模式
     */
    @Setter
    private IStrategy cacheStrategy;
    /**
     * api的class
     */
    @Setter
    private Class clazz;
    /**
     * 方法名
     */
    @Setter
    private String method;
    /**
     * 方法参数对应的值
     */

    private Object[] values;

    /**
     * 获得缓存key
     *
     * @return String
     */
    public String getKey() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(clazz.getSimpleName()).append(":").append(method).append(":");
        if (values != null && values.length != 0) {
            for (Object value : values) {
                if (value != null) {
                    sbd.append(JSON.toJSONString(value)).append(":");
                }
            }
        }
        return sbd.toString();
    }

    public CacheModel setValues(Object... objects) {
        values = objects;
        return this;
    }

}

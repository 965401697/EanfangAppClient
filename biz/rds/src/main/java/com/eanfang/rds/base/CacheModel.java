package com.eanfang.rds.base;

import com.zchu.rxcache.stategy.IStrategy;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CacheModel {

    /**
     * 缓存模式
     */
    private IStrategy cacheStrategy;
    /**
     * api的class
     */
    private Class clazz;
    /**
     * 方法名
     */
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
        for (Object value : values) {
            sbd.append(value).append(":");
        }
        return sbd.toString();
    }

//    public void setValues(Object... objects) {
//
//    }

}

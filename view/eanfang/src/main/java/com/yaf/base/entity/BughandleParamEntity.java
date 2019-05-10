package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;


/**
 * 故障处理参数表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-08 15:11:19
 */
public class BughandleParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //故障处理表ID
    //@TableField(value = "bus_bughandle_id")
    private Long busBughandleId;
    //参数名
    //@TableField(value = "param_name")
    private String paramName;
    //参数值
    //@TableField(value = "param_value")
    private String paramValue;

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：故障处理表ID
     */
    public Long getBusBughandleId() {
        return busBughandleId;
    }

    /**
     * 设置：故障处理表ID
     */
    public void setBusBughandleId(Long busBughandleId) {
        this.busBughandleId = busBughandleId;
    }

    /**
     * 获取：参数名
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置：参数名
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 获取：参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 设置：参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BughandleParamEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((BughandleParamEntity) other).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}

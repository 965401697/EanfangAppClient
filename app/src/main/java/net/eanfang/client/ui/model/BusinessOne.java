package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/4/15.
 */

public class BusinessOne implements Serializable {
    /**
     * 一级业务类型名称
     */
    private String name;
    /**
     * 以及业务类型编码（）
     */
    private String code;


    /**
     * 一级业务类型名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 以及业务类型编码（）
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

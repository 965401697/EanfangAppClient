package com.eanfang.model.security;

import java.io.Serializable;

/**
 * @author guanluocang
 * @data 2019/2/14
 * @description
 */

public class SecurityCreateBean implements Serializable {
    private String spcContent;
    private String spcImg;

    public SecurityCreateBean(String spcContent, String spcImg) {
        this.spcContent = spcContent;
        this.spcImg = spcImg;
    }

    public SecurityCreateBean() {
    }

    public String getSpcContent() {
        return this.spcContent;
    }

    public String getSpcImg() {
        return this.spcImg;
    }

    public void setSpcContent(String spcContent) {
        this.spcContent = spcContent;
    }

    public void setSpcImg(String spcImg) {
        this.spcImg = spcImg;
    }
}

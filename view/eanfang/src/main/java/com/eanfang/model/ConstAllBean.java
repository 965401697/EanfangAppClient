package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstAllBean implements Serializable {
    private ConstBean data;
    private String MD5;

    public String getMD5() {
        return MD5 == null ? "" : MD5;
    }
}


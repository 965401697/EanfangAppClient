package com.eanfang.biz.model.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RongTokenBean {
    private String token;
    private String userId;
}

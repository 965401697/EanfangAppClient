package com.eanfang.model;

import lombok.Data;

/**
 * @author liangkailun
 * Date ：2019/5/6
 * Describe :内容安全获取token
 */
@Data
public class ContentSecruityTokenBean {

    private String refresh_token;
    /**
     *token有效期
     */
    private int expires_in;
    private String session_key;
    /**
     *请求内容审核接口的token
     */
    private String access_token;
    private String scope;
    private String session_secret;
}

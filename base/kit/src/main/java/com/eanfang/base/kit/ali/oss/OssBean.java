package com.eanfang.base.kit.ali.oss;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jornl
 * @date 2019-07-01
 */
@Getter
@Setter
class OssBean {
    private String accessKeyId;
    private String accessKeySecret;
    private String security;
    private String expiration;
}

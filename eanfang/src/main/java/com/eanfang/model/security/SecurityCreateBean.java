package com.eanfang.model.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/2/14
 * @description
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityCreateBean implements Serializable {
    private String spcContent;
    private String spcImg;
}

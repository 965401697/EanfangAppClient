package com.eanfang.model.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/4/9
 * @description
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SecurityCommonBean implements Serializable {
    private String mUserName;
    private Long mUserId;

    @Override
    public String toString() {
        return "SecurityCommonBean{" +
                "mUserName='" + mUserName + '\'' +
                ", mUserId=" + mUserId +
                '}';
    }
}

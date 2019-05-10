package com.eanfang.model.security;

import com.eanfang.model.sys.UserEntity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/4/10
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityPersonalTopBean implements Serializable {

    private UserEntity userEntity;
    private int spccount;
    private int likeCount;
    private int noReadCount;
    private int followerCount;
    private int commentNoRead;
    private int asFollowerCount;
}

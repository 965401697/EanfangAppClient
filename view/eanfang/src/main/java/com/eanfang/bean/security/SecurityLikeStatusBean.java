package com.eanfang.bean.security;

import android.content.Intent;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/6/3
 * @description
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityLikeStatusBean implements Serializable {
    private Integer likeStatus;
    private Integer likesCount;
}

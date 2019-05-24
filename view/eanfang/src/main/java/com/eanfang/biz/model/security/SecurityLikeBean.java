package com.eanfang.biz.model.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/2/20
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityLikeBean implements Serializable {
    /**
     * 0 安防圈  1 问答
     */
    private String type;
    private Long asId;
    /**
     * 0 赞  1 取消
     */
    private String likeStatus;
    private Long likeUserId;
    private Long likeCompanyId;
    private Long likeTopCompanyId;
}

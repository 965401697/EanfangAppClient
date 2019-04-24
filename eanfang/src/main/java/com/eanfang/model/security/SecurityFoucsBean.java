package com.eanfang.model.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/2/18
 * @description
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityFoucsBean implements Serializable {
    /**
     * 关注人id
     */
    private Long followUserId;
    /**
     * 关注人公司id
     */
    private Long followCompanyId;
    /**
     * 关注人总id
     */
    private Long followTopCompanyId;
    /**
     * 被关注人id
     */
    private Long asUserId;
    /**
     * 被关注人公司
     */
    private Long asCompanyId;
    /**
     * 被关注人总公司
     */
    private Long asTopCompanyId;
    /**
     * 关注人accid
     */
    private Long followAccId;
    /**
     * 被关注人accid
     */
    private Long asAccId;
    private int followsStatus;
}

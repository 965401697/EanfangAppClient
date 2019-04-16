package com.eanfang.model.security;

import java.io.Serializable;

/**
 * @author guanluocang
 * @data 2019/2/18
 * @description
 */

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

    public SecurityFoucsBean(Long followUserId, Long followCompanyId, Long followTopCompanyId, Long asUserId, Long asCompanyId, Long asTopCompanyId) {
        this.followUserId = followUserId;
        this.followCompanyId = followCompanyId;
        this.followTopCompanyId = followTopCompanyId;
        this.asUserId = asUserId;
        this.asCompanyId = asCompanyId;
        this.asTopCompanyId = asTopCompanyId;
    }

    public SecurityFoucsBean() {
    }

    public Long getFollowUserId() {
        return this.followUserId;
    }

    public Long getFollowCompanyId() {
        return this.followCompanyId;
    }

    public Long getFollowTopCompanyId() {
        return this.followTopCompanyId;
    }

    public Long getAsUserId() {
        return this.asUserId;
    }

    public Long getAsCompanyId() {
        return this.asCompanyId;
    }

    public Long getAsTopCompanyId() {
        return this.asTopCompanyId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public void setFollowCompanyId(Long followCompanyId) {
        this.followCompanyId = followCompanyId;
    }

    public void setFollowTopCompanyId(Long followTopCompanyId) {
        this.followTopCompanyId = followTopCompanyId;
    }

    public void setAsUserId(Long asUserId) {
        this.asUserId = asUserId;
    }

    public void setAsCompanyId(Long asCompanyId) {
        this.asCompanyId = asCompanyId;
    }

    public void setAsTopCompanyId(Long asTopCompanyId) {
        this.asTopCompanyId = asTopCompanyId;
    }
}

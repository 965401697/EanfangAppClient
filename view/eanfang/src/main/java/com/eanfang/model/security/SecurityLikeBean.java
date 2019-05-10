package com.eanfang.model.security;

import java.io.Serializable;

/**
 * @author guanluocang
 * @data 2019/2/20
 * @description
 */
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

    public SecurityLikeBean(String type, Long asId, String likeStatus, Long likeUserId, Long likeCompanyId, Long likeTopCompanyId) {
        this.type = type;
        this.asId = asId;
        this.likeStatus = likeStatus;
        this.likeUserId = likeUserId;
        this.likeCompanyId = likeCompanyId;
        this.likeTopCompanyId = likeTopCompanyId;
    }

    public SecurityLikeBean() {
    }

    public String getType() {
        return this.type;
    }

    public Long getAsId() {
        return this.asId;
    }

    public String getLikeStatus() {
        return this.likeStatus;
    }

    public Long getLikeUserId() {
        return this.likeUserId;
    }

    public Long getLikeCompanyId() {
        return this.likeCompanyId;
    }

    public Long getLikeTopCompanyId() {
        return this.likeTopCompanyId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAsId(Long asId) {
        this.asId = asId;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public void setLikeUserId(Long likeUserId) {
        this.likeUserId = likeUserId;
    }

    public void setLikeCompanyId(Long likeCompanyId) {
        this.likeCompanyId = likeCompanyId;
    }

    public void setLikeTopCompanyId(Long likeTopCompanyId) {
        this.likeTopCompanyId = likeTopCompanyId;
    }
}

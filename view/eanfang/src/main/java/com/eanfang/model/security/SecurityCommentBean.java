package com.eanfang.model.security;

import java.io.Serializable;

/**
 * @author guanluocang
 * @data 2019/3/4
 * @description 安防圈评论Bean
 */
public class SecurityCommentBean implements Serializable {

    private String type;
    private String status;
    private String commentsContent;
    private String commentsImg;
    private Long asId;
    private Long commentsAnswerId;
    private Long commentsCompanyId;
    private Long commentsTopCompanyId;

    public SecurityCommentBean(String type, String status, String commentsContent, String commentsImg, Long asId, Long commentsAnswerId, Long commentsCompanyId, Long commentsTopCompanyId) {
        this.type = type;
        this.status = status;
        this.commentsContent = commentsContent;
        this.commentsImg = commentsImg;
        this.asId = asId;
        this.commentsAnswerId = commentsAnswerId;
        this.commentsCompanyId = commentsCompanyId;
        this.commentsTopCompanyId = commentsTopCompanyId;
    }

    public SecurityCommentBean() {
    }

    public String getType() {
        return this.type;
    }

    public String getStatus() {
        return this.status;
    }

    public String getCommentsContent() {
        return this.commentsContent;
    }

    public String getCommentsImg() {
        return this.commentsImg;
    }

    public Long getAsId() {
        return this.asId;
    }

    public Long getCommentsAnswerId() {
        return this.commentsAnswerId;
    }

    public Long getCommentsCompanyId() {
        return this.commentsCompanyId;
    }

    public Long getCommentsTopCompanyId() {
        return this.commentsTopCompanyId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCommentsContent(String commentsContent) {
        this.commentsContent = commentsContent;
    }

    public void setCommentsImg(String commentsImg) {
        this.commentsImg = commentsImg;
    }

    public void setAsId(Long asId) {
        this.asId = asId;
    }

    public void setCommentsAnswerId(Long commentsAnswerId) {
        this.commentsAnswerId = commentsAnswerId;
    }

    public void setCommentsCompanyId(Long commentsCompanyId) {
        this.commentsCompanyId = commentsCompanyId;
    }

    public void setCommentsTopCompanyId(Long commentsTopCompanyId) {
        this.commentsTopCompanyId = commentsTopCompanyId;
    }
}

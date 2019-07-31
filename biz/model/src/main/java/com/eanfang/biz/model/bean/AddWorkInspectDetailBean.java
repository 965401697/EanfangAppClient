package com.eanfang.biz.model.bean;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2017/12/19  15:26
 * @email houzhongzhou@yeah.net
 * @desc 检查明细新增
 */
public class AddWorkInspectDetailBean implements Serializable {

    /**
     * sysWorkInspectDetailId : 941492980165455874
     * disposeInfo : 这个工作检查应该这样处理。。。
     * remarkInfo : 这是备注信息
     * pictures : 多个图片地铁
     */

    private long oaWorkInspectDetailId;
    private String disposeInfo;
    private String remarkInfo;
    private String pictures;
    private Long oaWorkInspectId;
    private String mp4Path;
    private String collaborativeUser;

    public long getOaWorkInspectDetailId() {
        return this.oaWorkInspectDetailId;
    }

    public String getDisposeInfo() {
        return this.disposeInfo;
    }

    public String getRemarkInfo() {
        return this.remarkInfo;
    }

    public String getPictures() {
        return this.pictures;
    }

    public Long getOaWorkInspectId() {
        return this.oaWorkInspectId;
    }

    public String getMp4Path() {
        return this.mp4Path;
    }

    public String getCollaborativeUser() {
        return this.collaborativeUser;
    }

    public void setOaWorkInspectDetailId(long oaWorkInspectDetailId) {
        this.oaWorkInspectDetailId = oaWorkInspectDetailId;
    }

    public void setDisposeInfo(String disposeInfo) {
        this.disposeInfo = disposeInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public void setOaWorkInspectId(Long oaWorkInspectId) {
        this.oaWorkInspectId = oaWorkInspectId;
    }

    public void setMp4Path(String mp4Path) {
        this.mp4Path = mp4Path;
    }

    public void setCollaborativeUser(String collaborativeUser) {
        this.collaborativeUser = collaborativeUser;
    }
}

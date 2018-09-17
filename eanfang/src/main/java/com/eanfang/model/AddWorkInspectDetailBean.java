package com.eanfang.model;

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

    public long getOaWorkInspectDetailId() {
        return oaWorkInspectDetailId;
    }

    public void setOaWorkInspectDetailId(long oaWorkInspectDetailId) {
        this.oaWorkInspectDetailId = oaWorkInspectDetailId;
    }

    public String getDisposeInfo() {
        return disposeInfo == null ? "" : disposeInfo;
    }

    public void setDisposeInfo(String disposeInfo) {
        this.disposeInfo = disposeInfo;
    }

    public String getRemarkInfo() {
        return remarkInfo == null ? "" : remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public String getPictures() {
        return pictures == null ? "" : pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public Long getOaWorkInspectId() {
        return oaWorkInspectId;
    }

    public void setOaWorkInspectId(Long oaWorkInspectId) {
        this.oaWorkInspectId = oaWorkInspectId;
    }
}

package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/4/19  10:13
 * @email houzhongzhou@yeah.net
 * @desc
 */
public class CameraBean implements Serializable{
    public CameraBean() {
    }

    private String projectName;
    private String localPosition;
    private String netAddress;
    private String localAddress;
    private String projectContent;

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLocalPosition() {
        return localPosition == null ? "" : localPosition;
    }

    public void setLocalPosition(String localPosition) {
        this.localPosition = localPosition;
    }

    public String getNetAddress() {
        return netAddress == null ? "" : netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public String getLocalAddress() {
        return localAddress == null ? "" : localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getProjectContent() {
        return projectContent == null ? "" : projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }
}

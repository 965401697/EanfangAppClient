package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by wen on 2017/6/3.
 */

public class ApplyTaskBean implements Serializable {

    private String uid;
    private String taskpublishuid;
    private String applypersonname;
    private String applypersonphone;
    private String applycompany;
    private String doortodoortime;
    private String predicttime;
    private String projectquote;
    private String description;
    private String pic1;
    private String pic2;
    private String pic3;

    private String createtime;

    public String getTaskpublishuid() {
        return taskpublishuid;
    }

    public void setTaskpublishuid(String taskpublishuid) {
        this.taskpublishuid = taskpublishuid;
    }

    public String getApplypersonname() {
        return applypersonname;
    }

    public void setApplypersonname(String applypersonname) {
        this.applypersonname = applypersonname;
    }

    public String getApplypersonphone() {
        return applypersonphone;
    }

    public void setApplypersonphone(String applypersonphone) {
        this.applypersonphone = applypersonphone;
    }

    public String getApplycompany() {
        return applycompany;
    }

    public void setApplycompany(String applycompany) {
        this.applycompany = applycompany;
    }

    public String getDoortodoortime() {
        return doortodoortime;
    }

    public void setDoortodoortime(String doortodoortime) {
        this.doortodoortime = doortodoortime;
    }

    public String getPredicttime() {
        return predicttime;
    }

    public void setPredicttime(String predicttime) {
        this.predicttime = predicttime;
    }

    public String getProjectquote() {
        return projectquote;
    }

    public void setProjectquote(String projectquote) {
        this.projectquote = projectquote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * taskpublishuid : 被申请任务的uid
     * applypersonname : 联系人姓名
     * applypersonphone : 联系人电话
     * applycompany : 申请公司名
     * doortodoortime : 2017-04-30 19:20:20
     * predicttime : 预计工期
     * projectquote : 20000.5
     * description : 项目描述
     * pic1 : 图1
     * pic2 :
     * pic3 :
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * 消息定时推送表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2018-09-20 13:59:23
 */
public class NoticePushEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //消息标题
    private String noticeTitle;
    //消息描述
    private String noticeDescribe;
    //消息内容
    private String noticeContent;
    //发送时间
    private Date sendTime;
    //审核人ID
    private Long auditorUserId;
    //审核时间
    private Date auditTime;
    //创建人
    private Long createUserId;
    //创建时间
    private Date createTime;
    //发送类型（0，短信发送；1，推送消息；2,全部）
    private Integer sendType;
    //短信消息模板ID
    private Long sysSmsTemplateId;
    //推送类型（0,客户端；1,技师端；2,全部）
    private Integer pushType;
    //账号类型（0,客户；1,技师；2,全部）
    private Integer accountType;

    private String mobiles;

    private Integer pushFinish;

    private int status;
    private String picture;
    private String url;
    private UserEntity createUser;
    private UserEntity auditUser;

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：消息标题
     */
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    /**
     * 获取：消息标题
     */
    public String getNoticeTitle() {
        return noticeTitle;
    }

    /**
     * 设置：消息描述
     */
    public void setNoticeDescribe(String noticeDescribe) {
        this.noticeDescribe = noticeDescribe;
    }

    /**
     * 获取：消息描述
     */
    public String getNoticeDescribe() {
        return noticeDescribe;
    }

    /**
     * 设置：消息内容
     */
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /**
     * 获取：消息内容
     */
    public String getNoticeContent() {
        return noticeContent;
    }

    /**
     * 设置：发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取：发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置：审核人ID
     */
    public void setAuditorUserId(Long auditorUserId) {
        this.auditorUserId = auditorUserId;
    }

    /**
     * 获取：审核人ID
     */
    public Long getAuditorUserId() {
        return auditorUserId;
    }

    /**
     * 设置：审核时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取：审核时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置：创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：发送类型（0，短信发送；1，推送消息；2,全部）
     */
    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    /**
     * 获取：发送类型（0，短信发送；1，推送消息；2,全部）
     */
    public Integer getSendType() {
        return sendType;
    }

    /**
     * 设置：短信消息模板ID
     */
    public void setSysSmsTemplateId(Long sysSmsTemplateId) {
        this.sysSmsTemplateId = sysSmsTemplateId;
    }

    /**
     * 获取：短信消息模板ID
     */
    public Long getSysSmsTemplateId() {
        return sysSmsTemplateId;
    }

    /**
     * 设置：推送类型（0,客户端；1,技师端；2,全部）
     */
    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    /**
     * 获取：推送类型（0,客户端；1,技师端；2,全部）
     */
    public Integer getPushType() {
        return pushType;
    }

    /**
     * 设置：账号类型（0,客户；1,技师；2,全部）
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 获取：账号类型（0,客户；1,技师；2,全部）
     */
    public Integer getAccountType() {
        return accountType;
    }

    public Integer getPushFinish() {
        return pushFinish;
    }

    public void setPushFinish(Integer pushFinish) {
        this.pushFinish = pushFinish;
    }


    public UserEntity getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }

    public UserEntity getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(UserEntity auditUser) {
        this.auditUser = auditUser;
    }


    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NoticePushEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((NoticePushEntity) other).id);
        }
        return false;
    }

    //阅读的状态
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}

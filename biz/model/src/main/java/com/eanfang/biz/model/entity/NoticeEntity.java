package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统通知
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2018-01-10 17:11:39
 */

public class NoticeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //通知ID
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER，普通自增长使用IdType.ID_AUTO
    private Long id;

    //接收人
    //@TableField(value = "recive_acc_id")
    private Long reciveAccId;

    //发送人ID
    //@TableField(value = "sender_user_id")
    private Long senderUserId;

    //通知类别
    //@TableField(value = "notice_type")
    private Integer noticeType;

    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    //状态0未读1已读2已删除
    //@TableField(value = "status")
    private Integer status;

    //参数
    //@TableField(value = "params")
    private String params;

    //归属公司
    //@TableField(value = "company_id")
    private Long companyId;

    /**
     * 设置：通知ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：通知ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：接收人
     */
    public void setReciveAccId(Long reciveAccId) {
        this.reciveAccId = reciveAccId;
    }

    /**
     * 获取：接收人
     */
    public Long getReciveAccId() {
        return reciveAccId;
    }

    /**
     * 设置：发送人ID
     */
    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    /**
     * 获取：发送人ID
     */
    public Long getSenderUserId() {
        return senderUserId;
    }

    /**
     * 设置：通知类别
     */
    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    /**
     * 获取：通知类别
     */
    public Integer getNoticeType() {
        return noticeType;
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
     * 设置：状态0未读1已读2已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态0未读1已读2已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取：参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置：归属公司
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：归属公司
     */
    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NoticeEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((NoticeEntity) other).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*手工代码写在下面*/
    private AccountEntity reciveAccEntity;

    private UserEntity senderEntity;

    private OrgEntity companyEntity;

    //各业务根据params扩展自己的信息
    Object extInfo;

    // 通知标题，通过NoticeConst配置内容自动生成
    String content;

    String title;


    public AccountEntity getReciveAccEntity() {
        return this.reciveAccEntity;
    }

    public UserEntity getSenderEntity() {
        return this.senderEntity;
    }

    public OrgEntity getCompanyEntity() {
        return this.companyEntity;
    }

    public Object getExtInfo() {
        return this.extInfo;
    }

    public String getContent() {
        return this.content;
    }

    public String getTitle() {
        return this.title;
    }

    public void setReciveAccEntity(AccountEntity reciveAccEntity) {
        this.reciveAccEntity = reciveAccEntity;
    }

    public void setSenderEntity(UserEntity senderEntity) {
        this.senderEntity = senderEntity;
    }

    public void setCompanyEntity(OrgEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public void setExtInfo(Object extInfo) {
        this.extInfo = extInfo;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

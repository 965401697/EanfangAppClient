package com.yaf.base.entity;

import com.eanfang.biz.model.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * 工作检查明细处理表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2017-12-05 20:41:57
 */
public class WorkInspectDetailDisposeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //工作检查明细表ID
    //@TableField(value = "oa_work_inspect_detail_id")
    private Long oaWorkInspectId;
    //处理明细
    //@TableField(value = "dispose_info")
    private String disposeInfo;
    //备注
    //@TableField(value = "remark_info")
    private String remarkInfo;
    //图片
    //@TableField(value = "pictures")
    private String pictures;
    //视频地址
  	//@TableField(value = "mp4_path")
  	private String mp4Path;
    //创建人ID
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建人总公司
    //@TableField(value = "create_top_company_id")
    private Long createTopCompanyId;
    //创建人编码
    //@TableField(value = "create_org_code")
    private String createOrgCode;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //状态
    private Integer status;
    //协同人
    private String collaborativeUser;
    //------------------------------------------业务字段，不存在于数据库--------------------------------------
    //创建人信息
    private UserEntity createUser;

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：工作检查明细表ID
     */
    public Long getOaWorkInspectId() {
        return oaWorkInspectId;
    }

    /**
     * 设置：工作检查明细表ID
     */
    public void setOaWorkInspectId(Long oaWorkInspectId) {
        this.oaWorkInspectId = oaWorkInspectId;
    }

    /**
     * 获取：处理明细
     */
    public String getDisposeInfo() {
        return disposeInfo;
    }

    /**
     * 设置：处理明细
     */
    public void setDisposeInfo(String disposeInfo) {
        this.disposeInfo = disposeInfo;
    }

    /**
     * 获取：备注
     */
    public String getRemarkInfo() {
        return remarkInfo;
    }

    /**
     * 设置：备注
     */
    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    /**
     * 获取：图片
     */
    public String getPictures() {
        return pictures;
    }

    /**
     * 设置：图片
     */
    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
    /**
	 * 设置：视频地址
	 */
    public String getMp4Path() {
		return mp4Path;
	}
    /**
	 * 获取：视频地址
	 */
	public void setMp4Path(String mp4Path) {
		this.mp4Path = mp4Path;
	}
    /**
     * 获取：创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人总公司
     */
    public Long getCreateTopCompanyId() {
        return createTopCompanyId;
    }

    /**
     * 设置：创建人总公司
     */
    public void setCreateTopCompanyId(Long createTopCompanyId) {
        this.createTopCompanyId = createTopCompanyId;
    }

    /**
     * 获取：创建人编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置：创建人编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建人信息
     */
    public UserEntity getCreateUser() {
        return createUser;
    }

    /**
     * 设置：创建人信息
     */
    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getCollaborativeUser() {
		return collaborativeUser;
	}

	public void setCollaborativeUser(String collaborativeUser) {
		this.collaborativeUser = collaborativeUser;
	}

    

}

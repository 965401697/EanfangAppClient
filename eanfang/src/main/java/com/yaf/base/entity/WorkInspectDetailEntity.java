package com.yaf.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;


/**
 * 工作检查明细表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2017-12-05 20:41:57
 */
@TableName(value = "oa_work_inspect_detail")
public class WorkInspectDetailEntity implements Serializable, MultiItemEntity {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    //工作检查表ID
    //@TableField(value = "oa_work_inspect_id")
    private Long oaWorkInspectId;
    //标题
    //@TableField(value = "title")
    private String title;
    //位置区域
    //@TableField(value = "region")
    private String region;
    //三级业务类型编码（基础数据表）
    //@TableField(value = "business_three_code")
    private String businessThreeCode;
    //检查内容
    //@TableField(value = "info")
    private String info;
    //图片地址（多个图片地址用逗号分割）
    //@TableField(value = "pictures")
    private String pictures;
    //视频地址
    //@TableField(value = "mp4_path")
    private String mp4Path;
    //状态
    private Integer status;
    private int itemType;
    //-----------------------------------业务字段，不存在于数据库----------------------------------------
    //工作检查明细处理列表
    @TableField(exist = false)
    private List<WorkInspectDetailDisposeEntity> WorkInspectDetailDisposes;

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
     * 获取：工作检查表ID
     */
    public Long getOaWorkInspectId() {
        return oaWorkInspectId;
    }

    /**
     * 设置：工作检查表ID
     */
    public void setOaWorkInspectId(Long oaWorkInspectId) {
        this.oaWorkInspectId = oaWorkInspectId;
    }

    /**
     * 获取：标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：位置区域
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置：位置区域
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取：三级业务类型编码（基础数据表）
     */
    public String getBusinessThreeCode() {
        return businessThreeCode;
    }

    /**
     * 设置：三级业务类型编码（基础数据表）
     */
    public void setBusinessThreeCode(String businessThreeCode) {
        this.businessThreeCode = businessThreeCode;
    }

    /**
     * 获取：检查内容
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置：检查内容
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 获取：图片地址（多个图片地址用逗号分割）
     */
    public String getPictures() {
        return pictures;
    }

    /**
     * 设置：图片地址（多个图片地址用逗号分割）
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
     * 获取：工作检查明细处理列表
     */
    public List<WorkInspectDetailDisposeEntity> getWorkInspectDetailDisposes() {
        return WorkInspectDetailDisposes;
    }

    /**
     * 设置:工作检查明细处理列表
     */
    public void setWorkInspectDetailDisposes(List<WorkInspectDetailDisposeEntity> workInspectDetailDisposes) {
        WorkInspectDetailDisposes = workInspectDetailDisposes;
    }

    /**
     * 获取状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}

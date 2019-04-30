package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;



/**
 * 维保执行计划的故障处理（上门解决）
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:06:54
 */
public class ShopBughandleMaintenanceDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            //
        //@TableField(value = "id")
                    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
            private Long id;
            //故障处理确认信息表ID
        //@TableField(value = "shop_bughandle_maintenance_confirm_id")
            private Long shopBughandleMaintenanceConfirmId;
            //重点检查设备表id
        //@TableField(value = "shop_maintenance_exam_device_id")
            private Long shopMaintenanceExamDeviceId;
            //检查过程与方法
        //@TableField(value = "check_process")
            private String checkProcess;
            //处理措施
        //@TableField(value = "measure")
            private String measure;
            //使用建议
        //@TableField(value = "use_advice")
            private String useAdvice;
            //维保前照片（多张图片地址用逗号分割）
        //@TableField(value = "before_pictures")
            private String beforePictures;
            //维保后照片（多张图片地址用逗号分割）
        //@TableField(value = "after_pictures")
            private String afterPictures;
            //处理后现场照片（多张图片地址用逗号分割）
        //@TableField(value = "locale_pictures")
            private String localePictures;
            //功能正常照片（多张图片地址用逗号分割）
        //@TableField(value = "normal_pictures")
            private String normalPictures;
            //创建人
        //@TableField(value = "create_user_id")
            private Long createUserId;
            //创建时间
        //@TableField(value = "create_time")
            private Date createTime;
            //修改人
        //@TableField(value = "edit_user_id")
            private Long editUserId;
            //修改时间
        //@TableField(value = "edit_time")
            private Date editTime;
            //审核状态 1审核通过，0审核不通过
        //@TableField(value = "audit_status")
            private Integer auditStatus;
    
            /**
         * 设置：
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * 获取：
         */
        public Long getId() {
            return id;
        }
            /**
         * 设置：故障处理确认信息表ID
         */
        public void setShopBughandleMaintenanceConfirmId(Long shopBughandleMaintenanceConfirmId) {
            this.shopBughandleMaintenanceConfirmId = shopBughandleMaintenanceConfirmId;
        }

        /**
         * 获取：故障处理确认信息表ID
         */
        public Long getShopBughandleMaintenanceConfirmId() {
            return shopBughandleMaintenanceConfirmId;
        }
            /**
         * 设置：重点检查设备表id
         */
        public void setShopMaintenanceExamDeviceId(Long shopMaintenanceExamDeviceId) {
            this.shopMaintenanceExamDeviceId = shopMaintenanceExamDeviceId;
        }

        /**
         * 获取：重点检查设备表id
         */
        public Long getShopMaintenanceExamDeviceId() {
            return shopMaintenanceExamDeviceId;
        }
            /**
         * 设置：检查过程与方法
         */
        public void setCheckProcess(String checkProcess) {
            this.checkProcess = checkProcess;
        }

        /**
         * 获取：检查过程与方法
         */
        public String getCheckProcess() {
            return checkProcess;
        }
            /**
         * 设置：处理措施
         */
        public void setMeasure(String measure) {
            this.measure = measure;
        }

        /**
         * 获取：处理措施
         */
        public String getMeasure() {
            return measure;
        }
            /**
         * 设置：使用建议
         */
        public void setUseAdvice(String useAdvice) {
            this.useAdvice = useAdvice;
        }

        /**
         * 获取：使用建议
         */
        public String getUseAdvice() {
            return useAdvice;
        }
            /**
         * 设置：维保前照片（多张图片地址用逗号分割）
         */
        public void setBeforePictures(String beforePictures) {
            this.beforePictures = beforePictures;
        }

        /**
         * 获取：维保前照片（多张图片地址用逗号分割）
         */
        public String getBeforePictures() {
            return beforePictures;
        }
            /**
         * 设置：维保后照片（多张图片地址用逗号分割）
         */
        public void setAfterPictures(String afterPictures) {
            this.afterPictures = afterPictures;
        }

        /**
         * 获取：维保后照片（多张图片地址用逗号分割）
         */
        public String getAfterPictures() {
            return afterPictures;
        }
            /**
         * 设置：处理后现场照片（多张图片地址用逗号分割）
         */
        public void setLocalePictures(String localePictures) {
            this.localePictures = localePictures;
        }

        /**
         * 获取：处理后现场照片（多张图片地址用逗号分割）
         */
        public String getLocalePictures() {
            return localePictures;
        }
            /**
         * 设置：功能正常照片（多张图片地址用逗号分割）
         */
        public void setNormalPictures(String normalPictures) {
            this.normalPictures = normalPictures;
        }

        /**
         * 获取：功能正常照片（多张图片地址用逗号分割）
         */
        public String getNormalPictures() {
            return normalPictures;
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
         * 设置：修改人
         */
        public void setEditUserId(Long editUserId) {
            this.editUserId = editUserId;
        }

        /**
         * 获取：修改人
         */
        public Long getEditUserId() {
            return editUserId;
        }
            /**
         * 设置：修改时间
         */
        public void setEditTime(Date editTime) {
            this.editTime = editTime;
        }

        /**
         * 获取：修改时间
         */
        public Date getEditTime() {
            return editTime;
        }
            /**
         * 设置：审核状态 1审核通过，0审核不通过
         */
        public void setAuditStatus(Integer auditStatus) {
            this.auditStatus = auditStatus;
        }

        /**
         * 获取：审核状态 1审核通过，0审核不通过
         */
        public Integer getAuditStatus() {
            return auditStatus;
        }
        
    @Override
    public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
    public boolean equals(Object other) {
    	if (other instanceof ShopBughandleMaintenanceDetailEntity) {
    		if(this.id == null || other == null) {
                return false;
            }
    		
            return this.id.equals(((ShopBughandleMaintenanceDetailEntity) other).id);   
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
}

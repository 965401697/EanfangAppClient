package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;



/**
 * 维保执行计划重点检查设备表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:07:40
 */
@TableName(value = "shop_maintenance_exam_device" )
public class ShopMaintenanceExamDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            //主键递增
        //@TableField(value = "id")
                    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
        @TableId(value = "id" , type = IdType.AUTO)
            private Long id;
            //维保执行计划表id
        //@TableField(value = "shop_maintenance_order_id")
            private Long shopMaintenanceOrderId;
          //设备名称，三级业务类型(基础数据表)
        //@TableField(value = "business_three_code")
            private String businessThreeCode;
            //品牌型号编码（基础数据表）
        //@TableField(value = "business_four_code")
            private String businessFourCode;
            //安装位置
        //@TableField(value = "location")
            private String location;
            //位置编号
        //@TableField(value = "location_number")
            private String locationNumber;
            //设备图片(多张图片地址用逗号分割)
        //@TableField(value = "picture")
            private String picture;
            //设备状况备注信息
        //@TableField(value = "device_info")
            private String deviceInfo;
            //维修次数
        //@TableField(value = "repair_count")
            private Integer repairCount;
            //创建人（故障处理人）
        //@TableField(value = "create_user_id")
            private Long createUserId;
            //创建时间（故障处理时间）
        //@TableField(value = "create_time")
            private Date createTime;
            //修改人
        //@TableField(value = "edit_user_id")
            private Long editUserId;
            //修改时间
        //@TableField(value = "edit_time")
            private Date editTime;
            //状态（0：待修复，1：修复完成，2：遗留）
        //@TableField(value = "status")
            private Integer status;
    
            /**
         * 设置：主键递增
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * 获取：主键递增
         */
        public Long getId() {
            return id;
        }
            /**
         * 设置：维保执行计划表id
         */
        public void setShopMaintenanceOrderId(Long shopMaintenanceOrderId) {
            this.shopMaintenanceOrderId = shopMaintenanceOrderId;
        }

        /**
         * 获取：维保执行计划表id
         */
        public Long getShopMaintenanceOrderId() {
            return shopMaintenanceOrderId;
        }
        /**
         * 设置：设备名称(三级业务类型编码（基础数据表）)
         */
        public String getBusinessThreeCode() {
			return businessThreeCode;
		}
        /**
         * 获取：设备名称(三级业务类型编码（基础数据表）)
         */
		public void setBusinessThreeCode(String businessThreeCode) {
			this.businessThreeCode = businessThreeCode;
		}

        /**
         * 设置：品牌（基础数据表）
         */
		public String getBusinessFourCode() {
			return businessFourCode;
		}

		/**
         * 获取：品牌（基础数据表）
         */
		public void setBusinessFourCode(String businessFourCode) {
			this.businessFourCode = businessFourCode;
		}
            /**
         * 设置：安装位置
         */
        public void setLocation(String location) {
            this.location = location;
        }

		/**
         * 获取：安装位置
         */
        public String getLocation() {
            return location;
        }
            /**
         * 设置：位置编号
         */
        public void setLocationNumber(String locationNumber) {
            this.locationNumber = locationNumber;
        }

        /**
         * 获取：位置编号
         */
        public String getLocationNumber() {
            return locationNumber;
        }
            /**
         * 设置：设备图片(多张图片地址用逗号分割)
         */
        public void setPicture(String picture) {
            this.picture = picture;
        }

        /**
         * 获取：设备图片(多张图片地址用逗号分割)
         */
        public String getPicture() {
            return picture;
        }
            /**
         * 设置：设备状况备注信息
         */
        public void setDeviceInfo(String deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        /**
         * 获取：设备状况备注信息
         */
        public String getDeviceInfo() {
            return deviceInfo;
        }
            /**
         * 设置：维修次数
         */
        public void setRepairCount(Integer repairCount) {
            this.repairCount = repairCount;
        }

        /**
         * 获取：维修次数
         */
        public Integer getRepairCount() {
            return repairCount;
        }
            /**
         * 设置：创建人（故障处理人）
         */
        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        /**
         * 获取：创建人（故障处理人）
         */
        public Long getCreateUserId() {
            return createUserId;
        }
            /**
         * 设置：创建时间（故障处理时间）
         */
        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        /**
         * 获取：创建时间（故障处理时间）
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
         * 设置：状态（0：待修复，1：修复完成，2：遗留）
         */
        public void setStatus(Integer status) {
            this.status = status;
        }

        /**
         * 获取：状态（0：待修复，1：修复完成，2：遗留）
         */
        public Integer getStatus() {
            return status;
        }
        
    @Override
    public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
    public boolean equals(Object other) {
    	if (other instanceof ShopMaintenanceExamDeviceEntity) {
    		if(this.id == null || other == null)
    			return false;
    		
            return this.id.equals(((ShopMaintenanceExamDeviceEntity) other).id);   
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
    
    
    
    
    
    
    
    
    
    //重点检查设备详细信息
    @TableField(exist = false)
    private ShopBughandleMaintenanceDetailEntity maintenanceDetailEntity;

	public ShopBughandleMaintenanceDetailEntity getMaintenanceDetailEntity() {
		return maintenanceDetailEntity;
	}

	public void setMaintenanceDetailEntity(ShopBughandleMaintenanceDetailEntity maintenanceDetailEntity) {
		this.maintenanceDetailEntity = maintenanceDetailEntity;
	}
    
    
    
}

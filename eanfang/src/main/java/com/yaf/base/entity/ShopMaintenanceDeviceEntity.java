package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * 维保执行计划主要设备表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:07:11
 */
@Getter
@Setter
@TableName(value = "shop_maintenance_device" )
public class ShopMaintenanceDeviceEntity implements Serializable {
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
            //规格/型号（基础数据表）
        //@TableField(value = "specification")
            private String specification;
            //单位
        //@TableField(value = "unit")
            private String unit;
            //数量
        //@TableField(value = "amount")
            private Integer amount;
            //位置编号
        //@TableField(value = "location_number")
            private String locationNumber;
            //设备状况备注信息
        //@TableField(value = "device_info")
            private String deviceInfo;
    
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
         * 设置：规格/型号（基础数据表）
         */
        public void setSpecification(String specification) {
            this.specification = specification;
        }

        

		/**
         * 获取：规格/型号（基础数据表）
         */
        public String getSpecification() {
            return specification;
        }
            /**
         * 设置：单位
         */
        public void setUnit(String unit) {
            this.unit = unit;
        }

        /**
         * 获取：单位
         */
        public String getUnit() {
            return unit;
        }
            /**
         * 设置：数量
         */
        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        /**
         * 获取：数量
         */
        public Integer getAmount() {
            return amount;
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
        
    @Override
    public String toString() {
		return JSON.toJSONString(this);
	}
	@Override
    public boolean equals(Object other) {
    	if (other instanceof ShopMaintenanceDeviceEntity) {
    		if(this.id == null || other == null)
    			return false;
    		
            return this.id.equals(((ShopMaintenanceDeviceEntity) other).id);   
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

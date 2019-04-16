package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * 为报计划故障处理确认信息表（上门解决）
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:06:15
 */
@TableName(value = "shop_bughandle_maintenance_confirm")
public class ShopBughandleMaintenanceConfirmEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键递增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //维保执行计划订单id
    //@TableField(value = "shop_maintenance_order_id")
    private Long shopMaintenanceOrderId;
    //完成时间
    //@TableField(value = "over_time")
    private Date overTime;
    //维修工时
    //@TableField(value = "work_hour")
    private String workHour;
    //录像机天数
    //@TableField(value = "store_days")
    private Integer storeDays;
    //录像机存储天数正常，(0：否，1：是)
    //@TableField(value = "is_vcr_store_day_normal")
    private Integer isVcrStoreDayNormal;
    //报警打印功能是否正常（0：否，1：是）
    //@TableField(value = "is_alarm_printer")
    private Integer isAlarmPrinter;
    //所有设备时间同步(0：否，1：是)
    //@TableField(value = "is_time_right")
    private Integer isTimeRight;
    //各类设备数据远传功能是否正常(0：否，1：是)
    //@TableField(value = "is_machine_data_remote")
    private Integer isMachineDataRemote;
    //协助人员
    //@TableField(value = "team_worker")
    private String teamWorker;
    //遗留问题
    //@TableField(value = "leftover_problem")
    private String leftoverProblem;
    //电视墙/操作台正面全貌(多张照片地址用逗号分割)
    //@TableField(value = "front_pictures")
    private String frontPictures;
    //电视墙/操作台背面全貌(多张照片地址用逗号分割)
    //@TableField(value = "reverse_side_pictures")
    private String reverseSidePictures;
    //机柜正面/背面(多张照片地址用逗号分割)
    //@TableField(value = "equipment_cabinet_pictures")
    private String equipmentCabinetPictures;
    //单据照片(多张照片地址用逗号分割)
    //@TableField(value = "invoices_pictures")
    private String invoicesPictures;
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
    //状态（0：系统运行正常，1：系统运行异常）
    //@TableField(value = "status")
    private Integer status;
    //签到经度
    //@TableField(value = "sign_longitude")
    private String signLongitude;
    //签到纬度
    //@TableField(value = "sign_latitude")
    private String signLatitude;
    //是否正常范围签到（0：是，1：否）
    //@TableField(value = "sign_scope")
    private Integer signScope;
    //签到地点code
    //@TableField(value = "sign_in_code")
    private String signInCode;
    //签到详细地址
    //@TableField(value = "sign_in_address")
    private String signInAddress;
    //当前报修 最新回电时间
    //@TableField(value = "reply_time")
    private Date replyTime;
    //当前报修 最新预约时间
    //@TableField(value = "book_time")
    private Date bookTime;
    //当前报修 最新签到时间
    //@TableField(value = "sing_in_time")
    private Date singInTime;
    //当前报修 最新完工时间
    //@TableField(value = "finish_work_time")
    private Date finishWorkTime;
    //当前报修 最新确认完工时间
    //@TableField(value = "confirm_time")
    private Date confirmTime;
    //签退时间
    //@TableField(value = "sign_out_time")
    private Date signOutTime;
    //签退地点 code
    //@TableField(value = "sign_out_code")
    private String signOutCode;
    //签退 详细地址
    //@TableField(value = "sign_out_address")
    private String signOutAddress;
    //审核状态 1审核通过，0审核不通过
    //@TableField(value = "audit_status")
    private Integer auditStatus;

    private String maintenanceSuggest;

    /**1
     *
     *
     *
     * 设置：主键递增
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getMaintenanceSuggest() {
        return maintenanceSuggest;
    }

    public void setMaintenanceSuggest(String maintenanceSuggest) {
        this.maintenanceSuggest = maintenanceSuggest;
    }

    /**
     * 获取：主键递增
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：维保执行计划订单id
     */
    public void setShopMaintenanceOrderId(Long shopMaintenanceOrderId) {
        this.shopMaintenanceOrderId = shopMaintenanceOrderId;
    }

    /**
     * 获取：维保执行计划订单id
     */
    public Long getShopMaintenanceOrderId() {
        return shopMaintenanceOrderId;
    }

    /**
     * 设置：完成时间
     */
    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    /**
     * 获取：完成时间
     */
    public Date getOverTime() {
        return overTime;
    }

    /**
     * 设置：维修工时
     */
    public void setWorkHour(String workHour) {
        this.workHour = workHour;
    }

    /**
     * 获取：维修工时
     */
    public String getWorkHour() {
        return workHour;
    }

    /**
     * 设置：录像机天数
     */
    public void setStoreDays(Integer storeDays) {
        this.storeDays = storeDays;
    }

    /**
     * 获取：录像机天数
     */
    public Integer getStoreDays() {
        return storeDays;
    }

    /**
     * 设置：录像机存储天数正常，(0：否，1：是)
     */
    public void setIsVcrStoreDayNormal(Integer isVcrStoreDayNormal) {
        this.isVcrStoreDayNormal = isVcrStoreDayNormal;
    }

    /**
     * 获取：录像机存储天数正常，(0：否，1：是)
     */
    public Integer getIsVcrStoreDayNormal() {
        return isVcrStoreDayNormal;
    }

    /**
     * 设置：报警打印功能是否正常（0：否，1：是）
     */
    public void setIsAlarmPrinter(Integer isAlarmPrinter) {
        this.isAlarmPrinter = isAlarmPrinter;
    }

    /**
     * 获取：报警打印功能是否正常（0：否，1：是）
     */
    public Integer getIsAlarmPrinter() {
        return isAlarmPrinter;
    }

    /**
     * 设置：所有设备时间同步(0：否，1：是)
     */
    public void setIsTimeRight(Integer isTimeRight) {
        this.isTimeRight = isTimeRight;
    }

    /**
     * 获取：所有设备时间同步(0：否，1：是)
     */
    public Integer getIsTimeRight() {
        return isTimeRight;
    }

    /**
     * 设置：各类设备数据远传功能是否正常(0：否，1：是)
     */
    public void setIsMachineDataRemote(Integer isMachineDataRemote) {
        this.isMachineDataRemote = isMachineDataRemote;
    }

    /**
     * 获取：各类设备数据远传功能是否正常(0：否，1：是)
     */
    public Integer getIsMachineDataRemote() {
        return isMachineDataRemote;
    }

    /**
     * 设置：协助人员
     */
    public void setTeamWorker(String teamWorker) {
        this.teamWorker = teamWorker;
    }

    /**
     * 获取：协助人员
     */
    public String getTeamWorker() {
        return teamWorker;
    }

    /**
     * 设置：遗留问题
     */
    public void setLeftoverProblem(String leftoverProblem) {
        this.leftoverProblem = leftoverProblem;
    }

    /**
     * 获取：遗留问题
     */
    public String getLeftoverProblem() {
        return leftoverProblem;
    }

    /**
     * 设置：电视墙/操作台正面全貌(多张照片地址用逗号分割)
     */
    public void setFrontPictures(String frontPictures) {
        this.frontPictures = frontPictures;
    }

    /**
     * 获取：电视墙/操作台正面全貌(多张照片地址用逗号分割)
     */
    public String getFrontPictures() {
        return frontPictures;
    }

    /**
     * 设置：电视墙/操作台背面全貌(多张照片地址用逗号分割)
     */
    public void setReverseSidePictures(String reverseSidePictures) {
        this.reverseSidePictures = reverseSidePictures;
    }

    /**
     * 获取：电视墙/操作台背面全貌(多张照片地址用逗号分割)
     */
    public String getReverseSidePictures() {
        return reverseSidePictures;
    }

    /**
     * 设置：机柜正面/背面(多张照片地址用逗号分割)
     */
    public void setEquipmentCabinetPictures(String equipmentCabinetPictures) {
        this.equipmentCabinetPictures = equipmentCabinetPictures;
    }

    /**
     * 获取：机柜正面/背面(多张照片地址用逗号分割)
     */
    public String getEquipmentCabinetPictures() {
        return equipmentCabinetPictures;
    }

    /**
     * 设置：单据照片(多张照片地址用逗号分割)
     */
    public void setInvoicesPictures(String invoicesPictures) {
        this.invoicesPictures = invoicesPictures;
    }

    /**
     * 获取：单据照片(多张照片地址用逗号分割)
     */
    public String getInvoicesPictures() {
        return invoicesPictures;
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
     * 设置：状态（0：系统运行正常，1：系统运行异常）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态（0：系统运行正常，1：系统运行异常）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：签到经度
     */
    public void setSignLongitude(String signLongitude) {
        this.signLongitude = signLongitude;
    }

    /**
     * 获取：签到经度
     */
    public String getSignLongitude() {
        return signLongitude;
    }

    /**
     * 设置：签到纬度
     */
    public void setSignLatitude(String signLatitude) {
        this.signLatitude = signLatitude;
    }

    /**
     * 获取：签到纬度
     */
    public String getSignLatitude() {
        return signLatitude;
    }

    /**
     * 设置：是否正常范围签到（0：是，1：否）
     */
    public void setSignScope(Integer signScope) {
        this.signScope = signScope;
    }

    /**
     * 获取：是否正常范围签到（0：是，1：否）
     */
    public Integer getSignScope() {
        return signScope;
    }

    /**
     * 设置：签到地点code
     */
    public void setSignInCode(String signInCode) {
        this.signInCode = signInCode;
    }

    /**
     * 获取：签到地点code
     */
    public String getSignInCode() {
        return signInCode;
    }

    /**
     * 设置：签到详细地址
     */
    public void setSignInAddress(String signInAddress) {
        this.signInAddress = signInAddress;
    }

    /**
     * 获取：签到详细地址
     */
    public String getSignInAddress() {
        return signInAddress;
    }

    /**
     * 设置：当前报修 最新回电时间
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 获取：当前报修 最新回电时间
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * 设置：当前报修 最新预约时间
     */
    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    /**
     * 获取：当前报修 最新预约时间
     */
    public Date getBookTime() {
        return bookTime;
    }

    /**
     * 设置：当前报修 最新签到时间
     */
    public void setSingInTime(Date singInTime) {
        this.singInTime = singInTime;
    }

    /**
     * 获取：当前报修 最新签到时间
     */
    public Date getSingInTime() {
        return singInTime;
    }

    /**
     * 设置：当前报修 最新完工时间
     */
    public void setFinishWorkTime(Date finishWorkTime) {
        this.finishWorkTime = finishWorkTime;
    }

    /**
     * 获取：当前报修 最新完工时间
     */
    public Date getFinishWorkTime() {
        return finishWorkTime;
    }

    /**
     * 设置：当前报修 最新确认完工时间
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * 获取：当前报修 最新确认完工时间
     */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
     * 设置：签退时间
     */
    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    /**
     * 获取：签退时间
     */
    public Date getSignOutTime() {
        return signOutTime;
    }

    /**
     * 设置：签退地点 code
     */
    public void setSignOutCode(String signOutCode) {
        this.signOutCode = signOutCode;
    }

    /**
     * 获取：签退地点 code
     */
    public String getSignOutCode() {
        return signOutCode;
    }

    /**
     * 设置：签退 详细地址
     */
    public void setSignOutAddress(String signOutAddress) {
        this.signOutAddress = signOutAddress;
    }

    /**
     * 获取：签退 详细地址
     */
    public String getSignOutAddress() {
        return signOutAddress;
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
        if (other instanceof ShopBughandleMaintenanceConfirmEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ShopBughandleMaintenanceConfirmEntity) other).id);
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

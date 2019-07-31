package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 故障处理确认信息表（上门解决）
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-08 15:11:19
 */
public class BughandleConfirmEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前故障处理的处理明细
     */
    List<BughandleDetailEntity> detailEntityList;
    /**
     * 转单记录
     */
    TransferLogEntity transferLogEntity;
    /**
     * 当前故障处理人
     */
    UserEntity createUserEntity;
    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //报修订单表ID
    //@TableField(value = "bus_repair_order_id")
    private Long busRepairOrderId;
    //完成时间
    //@TableField(value = "over_time")
    private Date overTime;
    //维修工时
    //@TableField(value = "work_hour")
    private String workHour;
    //录像机存储天数正常，(0：否，1：是)
    //@TableField(value = "is_vcr_store_day_normal")
    private Integer isVcrStoreDayNormal;
    //机器时间是否正常（0：否，1：是）
    //@TableField(value = "is_machine_time_normal")
    private Integer isMachineTimeNormal;
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
    //录像机天数
    //@TableField(value = "store_days")
    private int storeDays;
    //报警打印机是否正常（0：否，1：是）
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
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;


    //签退时间
    //@TableField(value = "sign_out_time")
    private Date signOutTime;
    //签退地点
    //@TableField(value = "sign_out_code")
    private String signOutCode;
    //签退地点
    //@TableField(value = "sign_out_address")
    private String signOutAddress;
    //签退经度
    //@TableField(value="sign_out_longitude")
    private String signOutLongitude;
    //签退纬度
    //@TableField(value="sign_out_latitude")
    private String signOutLatitude;

    //修改人
    //@TableField(value = "edit_user_id")
    private Long editUserId;
    //修改时间
    //@TableField(value = "edit_time")
    private Date editTime;
    //状态
    //@TableField(value = "status")
    private Integer status;
    //当前维修确认单的回电时间
    //@TableField(value = "reply_time")
    private Date replyTime;
    //当前维修确认单的预约时间
    //@TableField(value = "book_time")
    private Date bookTime;
    //当前维修确认单 签到时间
    //@TableField(value = "sing_in_time")
    private Date singInTime;
    //当前维修确认单 完工时间
    //@TableField(value = "finish_work_time")
    private Date finishWorkTime;
    //当前维修确认单 确认时间
    //@TableField(value = "confirm_time")
    private Date confirmTime;
    //电视墙/操作台正面全貌 视频地址
    private String front_mp4_path;
    //电视墙/操作台背面全貌 视频地址
    private String reverse_side_mp4_path;
    //电视墙/操作台背面全貌 视频地址
    private String equipment_cabinet_mp4_path;

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
     * 获取：报修订单表ID
     */
    public Long getBusRepairOrderId() {
        return busRepairOrderId;
    }

    /**
     * 设置：报修订单表ID
     */
    public void setBusRepairOrderId(Long busRepairOrderId) {
        this.busRepairOrderId = busRepairOrderId;
    }

    /**
     * 获取：完成时间
     */
    public Date getOverTime() {
        return overTime;
    }

    /**
     * 设置：完成时间
     */
    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    /**
     * 获取：维修工时
     */
    public String getWorkHour() {
        return workHour;
    }

    /**
     * 设置：维修工时
     */
    public void setWorkHour(String workHour) {
        this.workHour = workHour;
    }

    /**
     * 获取：录像机存储天数正常，(0：否，1：是)
     */
    public Integer getIsVcrStoreDayNormal() {
        return isVcrStoreDayNormal;
    }

    /**
     * 设置：录像机存储天数正常，(0：否，1：是)
     */
    public void setIsVcrStoreDayNormal(Integer isVcrStoreDayNormal) {
        this.isVcrStoreDayNormal = isVcrStoreDayNormal;
    }

    /**
     * 获取：机器时间是否正常（0：否，1：是）
     */
    public Integer getIsMachineTimeNormal() {
        return isMachineTimeNormal;
    }

    /**
     * 设置：机器时间是否正常（0：否，1：是）
     */
    public void setIsMachineTimeNormal(Integer isMachineTimeNormal) {
        this.isMachineTimeNormal = isMachineTimeNormal;
    }

    /**
     * 获取：遗留问题
     */
    public String getLeftoverProblem() {
        return leftoverProblem;
    }

    /**
     * 设置：遗留问题
     */
    public void setLeftoverProblem(String leftoverProblem) {
        this.leftoverProblem = leftoverProblem;
    }

    /**
     * 获取：电视墙/操作台正面全貌(多张照片地址用逗号分割)
     */
    public String getFrontPictures() {
        return frontPictures;
    }

    /**
     * 设置：电视墙/操作台正面全貌(多张照片地址用逗号分割)
     */
    public void setFrontPictures(String frontPictures) {
        this.frontPictures = frontPictures;
    }

    /**
     * 获取：电视墙/操作台背面全貌(多张照片地址用逗号分割)
     */
    public String getReverseSidePictures() {
        return reverseSidePictures;
    }

    /**
     * 设置：电视墙/操作台背面全貌(多张照片地址用逗号分割)
     */
    public void setReverseSidePictures(String reverseSidePictures) {
        this.reverseSidePictures = reverseSidePictures;
    }

    /**
     * 获取：机柜正面/背面(多张照片地址用逗号分割)
     */
    public String getEquipmentCabinetPictures() {
        return equipmentCabinetPictures;
    }

    /**
     * 设置：机柜正面/背面(多张照片地址用逗号分割)
     */
    public void setEquipmentCabinetPictures(String equipmentCabinetPictures) {
        this.equipmentCabinetPictures = equipmentCabinetPictures;
    }

    /**
     * 获取：单据照片(多张照片地址用逗号分割)
     */
    public String getInvoicesPictures() {
        return invoicesPictures;
    }

    /**
     * 设置：单据照片(多张照片地址用逗号分割)
     */
    public void setInvoicesPictures(String invoicesPictures) {
        this.invoicesPictures = invoicesPictures;
    }

    /**
     * 获取：录像机天数
     */
    public int getStoreDays() {
        return storeDays;
    }

    /**
     * 设置：录像机天数
     */
    public void setStoreDays(int storeDays) {
        this.storeDays = storeDays;
    }

    /**
     * 获取：报警打印机是否正常（0：否，1：是）
     */
    public Integer getIsAlarmPrinter() {
        return isAlarmPrinter;
    }

    /**
     * 设置：报警打印机是否正常（0：否，1：是）
     */
    public void setIsAlarmPrinter(Integer isAlarmPrinter) {
        this.isAlarmPrinter = isAlarmPrinter;
    }

    /**
     * 获取：所有设备时间同步(0：否，1：是)
     */
    public Integer getIsTimeRight() {
        return isTimeRight;
    }

    /**
     * 设置：所有设备时间同步(0：否，1：是)
     */
    public void setIsTimeRight(Integer isTimeRight) {
        this.isTimeRight = isTimeRight;
    }

    /**
     * 获取：各类设备数据远传功能是否正常(0：否，1：是)
     */
    public Integer getIsMachineDataRemote() {
        return isMachineDataRemote;
    }

    /**
     * 设置：各类设备数据远传功能是否正常(0：否，1：是)
     */
    public void setIsMachineDataRemote(Integer isMachineDataRemote) {
        this.isMachineDataRemote = isMachineDataRemote;
    }

    /**
     * 获取：协助人员
     */
    public String getTeamWorker() {
        return teamWorker;
    }

    /**
     * 设置：协助人员
     */
    public void setTeamWorker(String teamWorker) {
        this.teamWorker = teamWorker;
    }

    /**
     * 获取：签退时间
     */
    public Date getSignOutTime() {
        return signOutTime;
    }

    /**
     * 设置：签退时间
     */
    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
     * 获取：修改人
     */
    public Long getEditUserId() {
        return editUserId;
    }

    /**
     * 设置：修改人
     */
    public void setEditUserId(Long editUserId) {
        this.editUserId = editUserId;
    }

    /**
     * 获取：修改时间
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 设置：修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    /**
     * 获取：当前维修确认单的回电时间
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * 设置：当前维修确认单的回电时间
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 获取：当前维修确认单的预约时间
     */
    public Date getBookTime() {
        return bookTime;
    }

    /**
     * 设置：当前维修确认单的预约时间
     */
    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    /**
     * 获取：当前维修确认单 签到时间
     */
    public Date getSingInTime() {
        return singInTime;
    }

    /**
     * 设置：当前维修确认单 签到时间
     */
    public void setSingInTime(Date singInTime) {
        this.singInTime = singInTime;
    }

    /**
     * 获取：当前维修确认单 完工时间
     */
    public Date getFinishWorkTime() {
        return finishWorkTime;
    }

    /**
     * 设置：当前维修确认单 完工时间
     */
    public void setFinishWorkTime(Date finishWorkTime) {
        this.finishWorkTime = finishWorkTime;
    }

    /**
     * 获取：当前维修确认单 确认时间
     */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
     * 设置：当前维修确认单 确认时间
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }




    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     *
     */

//    /**
//     * 报修真实故障
//     */
//    List<RepairFailureEntity> failureEntityList;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BughandleConfirmEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((BughandleConfirmEntity) other).id);
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

    public List<BughandleDetailEntity> getDetailEntityList() {
        return this.detailEntityList;
    }

    public TransferLogEntity getTransferLogEntity() {
        return this.transferLogEntity;
    }

    public UserEntity getCreateUserEntity() {
        return this.createUserEntity;
    }

    public String getSignOutCode() {
        return this.signOutCode;
    }

    public String getSignOutAddress() {
        return this.signOutAddress;
    }

    public String getSignOutLongitude() {
        return this.signOutLongitude;
    }

    public String getSignOutLatitude() {
        return this.signOutLatitude;
    }

    public String getFront_mp4_path() {
        return this.front_mp4_path;
    }

    public String getReverse_side_mp4_path() {
        return this.reverse_side_mp4_path;
    }

    public String getEquipment_cabinet_mp4_path() {
        return this.equipment_cabinet_mp4_path;
    }

    public void setDetailEntityList(List<BughandleDetailEntity> detailEntityList) {
        this.detailEntityList = detailEntityList;
    }

    public void setTransferLogEntity(TransferLogEntity transferLogEntity) {
        this.transferLogEntity = transferLogEntity;
    }

    public void setCreateUserEntity(UserEntity createUserEntity) {
        this.createUserEntity = createUserEntity;
    }

    public void setSignOutCode(String signOutCode) {
        this.signOutCode = signOutCode;
    }

    public void setSignOutAddress(String signOutAddress) {
        this.signOutAddress = signOutAddress;
    }

    public void setSignOutLongitude(String signOutLongitude) {
        this.signOutLongitude = signOutLongitude;
    }

    public void setSignOutLatitude(String signOutLatitude) {
        this.signOutLatitude = signOutLatitude;
    }

    public void setFront_mp4_path(String front_mp4_path) {
        this.front_mp4_path = front_mp4_path;
    }

    public void setReverse_side_mp4_path(String reverse_side_mp4_path) {
        this.reverse_side_mp4_path = reverse_side_mp4_path;
    }

    public void setEquipment_cabinet_mp4_path(String equipment_cabinet_mp4_path) {
        this.equipment_cabinet_mp4_path = equipment_cabinet_mp4_path;
    }
}

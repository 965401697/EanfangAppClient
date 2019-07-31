package com.eanfang.biz.model.entity;


/**
 * @author liangkailun on 2019/3/29.
 * Describe:技工订单操作类,控制保修管控页面按钮文本和展示状态.
 */
public class WorkerOrderOerationEntity {

    /**
     * 左侧按钮操作文本集合
     */
    private String[] mDoSomethingLeft = {
            "", "", "改约", "联系客户", "联系客户", "查看故障处理", ""
    };

    /**
     * 右侧按钮操作文本集合
     */
    private String[] mDoSomethingRight = {
            "联系客户", "马上回电", "签到"
            , "完工", "查看故障处理", "评价客户", "联系客户"
    };

    /**
     * 左侧按钮展示状态
     */
    private final boolean[] mShowFirstBtn = {
            false, false, true, true, false, true, false
    };

    /**
     * 右侧按钮展示状态
     */
    private final boolean[] mShowSecondBtn = {
            true, true, true, true, true, false, false
    };

    /**
     * 左侧按钮操作展示文本
     */
    private String mOperationLeft;

    /**
     * 右侧按钮操作展示文本
     */
    private String mOperationRight;

    /**
     * 订单是否完成
     */
    private boolean mIsFinished;

    /**
     * 是否显示左侧操作
     */
    private boolean mIsShowOperationLeft;

    /**
     * 是否显示操作右侧
     */
    private boolean mIsShowOperationRight;

    /**
     * @param status 订单状态  0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成
     * @param custom 客户本人
     */
    public WorkerOrderOerationEntity(int status, boolean custom) {
        this.mOperationLeft = mDoSomethingLeft[status];
        this.mOperationRight = mDoSomethingRight[status];
        //status = 5设置订单完成
        this.mIsFinished = status == 5;
        //左侧按钮展示逻辑 (显示查看故障处理)
        this.mIsShowOperationLeft = (custom && mShowFirstBtn[status]) || status == 5;
        //右侧按钮展示逻辑（显示查看故障处理）
        this.mIsShowOperationRight = (custom && mShowSecondBtn[status]) || status == 4;
    }

    public String getOperationLeft() {
        return mOperationLeft;
    }

    public String getOperationRight() {
        return mOperationRight;
    }

    public boolean isFinished() {
        return mIsFinished;
    }

    public boolean isShowOperationLeft() {
        return mIsShowOperationLeft;
    }

    public boolean isShowOperationRight() {
        return mIsShowOperationRight;
    }

}

package com.eanfang.biz.model.entity;


/**
 * @author liangkailun on 2019/3/29.
 *
 * Describe:订单操作类,控制保修管控页面按钮文本和展示状态.
 */
public class ClientOrderOerationEntity {

    /**
     * 左侧按钮操作文本集合
     */
    private String[] mDoSomethingLeft = {
            "联系技师", "完工报告"
    };
    /**
     * 右侧按钮操作文本集合
     */
    private String[] mDoSomethingRight = {
            "立即付款", "联系技师", "联系技师",
            "联系技师", "确认完工", "评价技师", "联系技师"
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
    public ClientOrderOerationEntity(int status, boolean custom) {
        this.mOperationLeft = status != 5 ? mDoSomethingLeft[0] : mDoSomethingLeft[1];
        this.mOperationRight = mDoSomethingRight[status];
        //status = 5 设置订单完成
        this.mIsFinished = status == 5;
        //左侧按钮展示逻辑
        this.mIsShowOperationLeft = status == 5;
        //右侧按钮展示逻辑
        this.mIsShowOperationRight = custom && mShowSecondBtn[status];
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

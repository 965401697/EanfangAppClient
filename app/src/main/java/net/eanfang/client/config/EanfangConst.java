package net.eanfang.client.config;

public class EanfangConst {


    /**
     * 工作任务 状态 未读
     */
    public static final int WORK_TASK_STATUS_UNREAD = 0;
    /**
     * 工作任务 状态 已读
     */
    public static final int WORK_TASK_STATUS_READ = 1;
    /**
     * 下拉刷新
     */
    public static final int TOP_REFRESH = 1;

    /**
     * 上拉加载
     */
    public static final int BOTTOM_REFRESH = 2;

    /**
     * 任务汇报明细 完成工作
     */
    public static final int TYPE_REPORT_DETAIL_FINISH = 0;
    /**
     * 任务汇报明细 发现问题
     */
    public static final int TYPE_REPORT_DETAIL_FIND = 1;
    /**
     * 任务汇报明细 后续计划
     */
    public static final int TYPE_REPORT_DETAIL_PLAN = 2;

    /**
     * 工作检查 已创建 待处理
     */
    public static final int WORK_INSPECT_STATUS_CREATE = 0;
    public static final String WORK_INSPECT_STATUS_CREATE_STR = "待处理";
    /**
     * 工作检查 已处理 待审核
     */
    public static final int WORK_INSPECT_STATUS_REVIEW = 1;
    public static final String WORK_INSPECT_STATUS_REVIEW_STR = "待审核";

    /**
     * 工作检查 未通过 待处理
     */
    public static final int WORK_INSPECT_STATUS_FAIL = 1;
    public static final String WORK_INSPECT_STATUS_FAIL_STR = "已拒绝";
    /**
     * 工作检查 处理完成
     */
    public static final int WORK_INSPECT_STATUS_FINISH = 2;
    public static final String WORK_INSPECT_STATUS_FINISH_STR = "已完成";
}

package net.eanfang.client.config;

public class EanfangConst {

    /*
     * 是 否
     */
    public static final String EANFANG_TRUE = "1";
    public static final String EANFANG_FALSE = "0";
    public static final String EANFANG_TRUE_STR = "是";
    public static final String EANFANG_FALSE_STR = "否";

    /*
     * 七大 业务类型
     */
    public static final String BUG_ONE_CODE_C = "C";
    public static final String BUG_ONE_CODE_A = "A";
    public static final String BUG_ONE_CODE_I = "I";
    public static final String BUG_ONE_CODE_V = "V";
    public static final String BUG_ONE_CODE_P = "P";
    public static final String BUG_ONE_CODE_B = "B";
    public static final String BUG_ONE_CODE_E = "E";
    public static final String BUG_ONE_CODE_C_LOW = "c";
    public static final String BUG_ONE_CODE_A_LOW = "a";
    public static final String BUG_ONE_CODE_I_LOW = "i";
    public static final String BUG_ONE_CODE_V_LOW = "v";
    public static final String BUG_ONE_CODE_P_LOW = "p";
    public static final String BUG_ONE_CODE_B_LOW = "b";
    public static final String BUG_ONE_CODE_E_LOW = "e";
    public static final String BUG_ONE_NAME_C = "电视监控";
    public static final String BUG_ONE_NAME_A = "门禁一卡通";
    public static final String BUG_ONE_NAME_I = "防盗报警";
    public static final String BUG_ONE_NAME_V = "可视对讲";
    public static final String BUG_ONE_NAME_P = "停车场";
    public static final String BUG_ONE_NAME_B = "公共广播";
    public static final String BUG_ONE_NAME_E = "EAS系统";

    /*
     * 维修结论
     */
    public static final String REPAIR_CONCLUSION_NAME_0 = "未修复";
    public static final String REPAIR_CONCLUSION_NAME_1 = "维修完成";
    public static final String REPAIR_CONCLUSION_NAME_2 = "报价更换";

    /*
     * app故障统计 类型
     */
    public static final String BUG_DETAIL_STATISTIC_COUNT_LOW = "count";
    public static final String BUG_DETAIL_STATISTIC_FINISH_LOW = "finish";
    public static final String BUG_DETAIL_STATISTIC_QUOTE_LOW = "quote";
    public static final String BUG_DETAIL_STATISTIC_LOSE_LOW = "lose";

    /*
     * 用户类型
     */
    public static final String PERSON_TYPE_WORKER = "worker";
    public static final String PERSON_TYPE_CLIENT = "client";
    public static final String PERSON_TYPE_PLATFORM = "platform";

    /**
     * 未认证
     */
    public static final String COMPANY_VERIFY_NO = "0";
    /**
     * 认证通过
     */
    public static final String COMPANY_VERIFY_PASS = "1";
    /**
     * 认证不通过
     */
    public static final String COMPANY_VERIFY_FAIL = "2";
    /**
     * 认证中
     */
    public static final String COMPANY_VERIFY_WAY = "3";

    /**
     * 维修单状态 待客户支付
     */
    public static final int REPAIR_STATUS_PAY = 0;
    public static final String REPAIR_STATUS_PAY_STR = "预付费";
    /**
     * 维修单状态 待技师回电
     */
    public static final int REPAIR_STATUS_CALL_PHONE = 1;
    public static final String REPAIR_STATUS_CALL_PHONE_STR = "已报修";
    /**
     * 维修单状态 待技师上门
     */
    public static final int REPAIR_STATUS_VISIT_IN = 2;
    public static final String REPAIR_STATUS_VISIT_IN_STR = "待上门";
    /**
     * 维修单状态 待技师完工
     */
    public static final int REPAIR_STATUS_FINISH_WORK = 3;
    public static final String REPAIR_STATUS_FINISH_WORK_STR = "待完工";
    /**
     * 维修单状态 待客户完工
     */
    public static final int REPAIR_STATUS_CONFIRM_WORK = 4;
    public static final String REPAIR_STATUS_CONFIRM_WORK_STR = "待确认";
    /**
     * 维修单状态 待客户评价
     */
    public static final int REPAIR_STATUS_EVALUATE = 5;
    public static final String REPAIR_STATUS_EVALUATE_STR = "待评价";
    /**
     * 维修单状态 订单完成
     */
    public static final int REPAIR_STATUS_FINISH_ORDER = 6;
    public static final String REPAIR_STATUS_FINISH_ORDER_STR = "已完成";

    /**
     * 工作任务 状态 未读
     */
    public static final int WORK_TASK_STATUS_UNREAD = 0;
    /**
     * 工作任务 状态 已读
     */
    public static final int WORK_TASK_STATUS_READ = 1;
    /**
     * 工作任务 状态 不可见（删除）
     */
    public static final String WORK_TASK_STATUS_INVISIBLE = "2";
    /**
     * 我公司的订单
     */
    public static final String TYPE_MY_COMPANY_ORDER = "0";

    /**
     * 我创建的订单
     */
    public static final String TYPE_MY_CREATE_ORDER = "1";
    /**
     * 我接受的订单
     */
    public static final String TYPE_MY_RECEIVE_ORDER = "2";

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
    public static final String TYPE_REPORT_DETAIL_FINISH = "0";
    /**
     * 任务汇报明细 发现问题
     */
    public static final String TYPE_REPORT_DETAIL_FIND = "1";
    /**
     * 任务汇报明细 后续计划
     */
    public static final String TYPE_REPORT_DETAIL_PLAN = "2";

    /**
     * 工作检查 已创建 待处理
     */
    public static final String WORK_INSPECT_STATUS_CREATE = "0";
    public static final String WORK_INSPECT_STATUS_CREATE_STR = "待处理";
    /**
     * 工作检查 已处理 待审核
     */
    public static final String WORK_INSPECT_STATUS_REVIEW = "1";
    public static final String WORK_INSPECT_STATUS_REVIEW_STR = "待审核";

    /**
     * 工作检查 未通过 待处理
     */
    public static final String WORK_INSPECT_STATUS_FAIL = "2";
    public static final String WORK_INSPECT_STATUS_FAIL_STR = "已拒绝";
    /**
     * 工作检查 处理完成
     */
    public static final String WORK_INSPECT_STATUS_FINISH = "3";
    public static final String WORK_INSPECT_STATUS_FINISH_STR = "已完成";
}

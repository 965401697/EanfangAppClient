package com.eanfang.config;

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

    /**
     * 融云的token
     */
    public static final String RONG_YUN_TOKEN = "rong_yun_token";
    /**
     * 融云的群组id
     */
    public static final String RONG_YUN_ID = "rong_yun_id";
    /**
     * 服务器的群组id
     */
    public static final String GROUP_ID = "group_id";
    /**
     * 二维码扫描添加好友标识
     */
    public static final String QR_ADD_FRIEND = "scan_frind_code";
    /**
     * 二维码扫描标识
     */
    //客户端
    public static final String QR_CLIENT = "client_code";
    //技师端
    public static final String QR_WORKER = "worker_code";
    //微信支付 appid 客户端
    public static final String WX_APPID_CLIENT = "wx11d1a11a2f79200a";

    //微信支付 appid 技师端
    public static final String WX_APPID_WORKER = "wx5e7969853addd542";

    // 微信支付成功 action
    public static final String ACTION_WX_PAY_SUCCESS = "net.eanfang.client.action.repair_wx_pay_success";

    // 小米推送 Appid Appkey
    public static final String XIAOMI_APPID_WORKER = "2882303761517837079";
    public static final String XIAOMI_APPKEY_WORKER = "5641783759079";

    public static final String XIAOMI_APPID_CLIENT = "2882303761517848571";
    public static final String XIAOMI_APPKEY_CLIENT = "5151784822571";

    // 魅族 Appid Appkey
    public static final String MEIZU_APPID_WORKER = "114610";
    public static final String MEIZU_APPKEY_WORKER = "0415a897aa1e48b2a850e6896de87a18";

    public static final String MEIZU_APPID_CLIENT = "115184";
    public static final String MEIZU_APPKEY_CLIENT = "2762bc44bf064b21ad851a26e19083bf";
}

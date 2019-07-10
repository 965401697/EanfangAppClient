package com.eanfang.config;

public interface EanfangConst {

    interface STR {
        String RONG_YUN_TOKEN = "rong_yun_token";
    }

    /**
     * 工作任务 状态 未读
     */
    int WORK_TASK_STATUS_UNREAD = 0;
    /**
     * 工作任务 状态 已读
     */
    int WORK_TASK_STATUS_READ = 1;
    /**
     * 下拉刷新
     */
    int TOP_REFRESH = 1;

    /**
     * 上拉加载
     */
    int BOTTOM_REFRESH = 2;

    /**
     * 任务汇报明细 完成工作
     */
    int TYPE_REPORT_DETAIL_FINISH = 0;
    /**
     * 任务汇报明细 发现问题
     */
    int TYPE_REPORT_DETAIL_FIND = 1;
    /**
     * 任务汇报明细 后续计划
     */
    int TYPE_REPORT_DETAIL_PLAN = 2;

    /**
     * 工作检查 已创建 待处理
     */
    int WORK_INSPECT_STATUS_CREATE = 0;
    String WORK_INSPECT_STATUS_CREATE_STR = "待处理";
    /**
     * 工作检查 已处理 待审核
     */
    int WORK_INSPECT_STATUS_REVIEW = 1;
    String WORK_INSPECT_STATUS_REVIEW_STR = "待审核";

    /**
     * 工作检查 未通过 待处理
     */
    int WORK_INSPECT_STATUS_FAIL = 2;
    String WORK_INSPECT_STATUS_FAIL_STR = "已拒绝";
    /**
     * 工作检查 处理完成
     */
    int WORK_INSPECT_STATUS_FINISH = 3;
    String WORK_INSPECT_STATUS_FINISH_STR = "已完成";

    /**
     * 融云的token
     * {@link STR}
     */
    @Deprecated
    String RONG_YUN_TOKEN = "rong_yun_token";
    /**
     * 融云的群组id
     */
    String RONG_YUN_ID = "rong_yun_id";
    /**
     * 服务器的群组id
     */
    String GROUP_ID = "group_id";
    /**
     * 二维码扫描添加好友标识
     */
    String QR_ADD_FRIEND = "scan_frind_code";
    /**
     * 二维码扫描标识
     */
    //客户端
    String QR_CLIENT = "client_code";
    //技师端
    String QR_WORKER = "worker_code";
    //微信支付 appid 客户端
    String WX_APPID_CLIENT = "wx11d1a11a2f79200a";
    String WX_SECRET_CLIENT = "367df9bffcfc33d64c50860012ee2b37";

    //微信支付 appid 技师端
    String WX_APPID_WORKER = "wx5e7969853addd542";
    String WX_SECRET_WORKER = "584a16f5561e6506fe7dc454eb9ade78";

    // 微信支付成功 action
    String ACTION_WX_PAY_SUCCESS = "net.eanfang.client.action.repair_wx_pay_success";

    // 小米推送 Appid Appkey
    String XIAOMI_APPID_WORKER = "2882303761517837079";
    String XIAOMI_APPKEY_WORKER = "5641783759079";

    String XIAOMI_APPID_CLIENT = "2882303761517848571";
    String XIAOMI_APPKEY_CLIENT = "5151784822571";

    // 魅族推送 Appid Appkey
    String MEIZU_APPID_WORKER = "114610";
    String MEIZU_APPKEY_WORKER = "0415a897aa1e48b2a850e6896de87a18";

    String MEIZU_APPID_CLIENT = "115184";
    String MEIZU_APPKEY_CLIENT = "2762bc44bf064b21ad851a26e19083bf";

    // 讯飞ID
    @Deprecated
    String XUNFEI_APPID = "5b8cd9c3";
    //萤石云app key
    String YING_SHI_YUN_APP_KEY = "e1c0c37930874e04b8db23168def1ddc";
}

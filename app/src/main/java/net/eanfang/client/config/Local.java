package net.eanfang.client.config;

/**
 * Created by wen on 2017/3/11.
 */
public class Local {

    //校验数据
    public static final int CHECK_TOKEN_SUCCESS = 10003;
    public static final int CHECK_TOKEN_FAIL = 10004;
    public static final int CHECK_TOKEN_ERROR = 10005;

    //搜索绑定公司

    public static final int QUOTATION_COMMIT = 10034;
    public static final int QUOTE_SELECT_REP_ORDER = 10035;

    //支付宝
    public static final int ALIPAY_SUCCESS = 10038;
    public static final int ALIPAY_FAIL = 10039;

    //工作台客户端--同意报价
    public static final int AGREE_PAY_SUCCESS = 10066;
    public static final int AGREE_PAY_FAIL = 10067;
    //微信支付
    public static final int WX_PAY_SUCCESS = 10068;
    public static final int WX_PAY_FAIL = 10069;


    //已发布任务-关闭发包
    public static final int CLOSE_PUBLISH_SUCCESS = 10094;
    public static final int CLOSE_PUBLISH_FAIL = 10095;
    //已发布任务-确认验收
    public static final int TASK_CHECK_SUCCESS = 10096;
    public static final int TASK_CHECK_FAIL = 10097;

    //消息列表

    public static final int CHECK_TOKEN_HOME_SUCCESS = 20106;

    //任务详情

    public static final int NEW_MESSAGE_RECEIVED = 20112;


    //工作台客户端--故障统计 故障列表
    public static final int BUG_DETAIL_LIST_SUCCESS = 20130;
    public static final int REPAIR_DETAIL_LIST_SUCCESS = 20131;

}

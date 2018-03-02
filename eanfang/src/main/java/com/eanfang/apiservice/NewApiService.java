package com.eanfang.apiservice;

import com.eanfang.BuildConfig;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:45
 * @email houzhongzhou@yeah.net
 * @desc
 */

public interface NewApiService {

    String YAF_SYS = "/yaf_sys";

    /**
     * 获取基础数据
     */
    String GET_BASE_DATA = BuildConfig.API_HOST + "/yaf_sys/basedata/listall";
    /**
     * 获取基础数据 缓存
     */
    String GET_BASE_DATA_CACHE = BuildConfig.API_HOST + "/yaf_sys/basedata/listallcache/";

    /**
     * 获取静态常量
     */
    String GET_CONST = BuildConfig.API_HOST + "/yaf_sys/const/list";
    /**
     * 获取静态常量 缓存
     */
    String GET_CONST_CACHE = BuildConfig.API_HOST + "/yaf_sys/const/listallcache/";

    /**
     * 获取公司列表
     */

    String GET_COMPANY_ALL_LIST = BuildConfig.API_HOST + "/yaf_sys/org/listcompanyall";
    /**
     * 切换公司
     */

    String SWITCH_COMPANY_ALL_LIST = BuildConfig.API_HOST + "/yaf_sys/sys/change";

    /**
     * 新增任务
     */
    String ADD_WORK_TASK = BuildConfig.API_HOST + "/yaf_oa/workTask/insert";

    /**
     * 获取协同人员
     */
    String GET_COLLEAGUE = BuildConfig.API_HOST + "/yaf_sys/user/olleague";

    /**
     * 获取任务列表
     */
    String GET_WORK_TASK_LIST = BuildConfig.API_HOST + "/yaf_oa/workTask/list";

    /**
     * 获取任务详情
     */

    String GET_WORK_TASK_INFO = BuildConfig.API_HOST + "/yaf_oa/workTask/detail";
    /**
     * 工作任务首次阅读
     */
    String WORK_TASK_FIRST_READ = BuildConfig.API_HOST + "/yaf_oa/workTask/read";

    /**
     * 新增汇报
     */
    String ADD_WORK_REPORT = BuildConfig.API_HOST + "/yaf_oa/workReport/insert";

    /**
     * 获取汇报列表
     */
    String GET_WORK_REPORT_LIST = BuildConfig.API_HOST + "/yaf_oa/workReport/list";
    /**
     * 获取汇报详情
     */
    String GET_WORK_REPORT_INFO = BuildConfig.API_HOST + "/yaf_oa/workReport/detail";
    /**
     * 获取汇报详情
     */
    String WORK_REPORT_FIRST_READ = BuildConfig.API_HOST + "/yaf_oa/workReport/read";

    /**
     * 新增设计
     */

    String ADD_WORK_DESIGN = BuildConfig.API_HOST + "/yaf_oa/designOrder/insert";

    /**
     * 获取设计列表
     */

    String GET_WORK_DESIGN_LIST = BuildConfig.API_HOST + "/yaf_oa/designOrder/list";

    /**
     * 获取设计详情
     */
    String GET_WORK_DESIGN_INFO = BuildConfig.API_HOST + "/yaf_oa/designOrder/detail";

    /**
     * 新增工作检查
     */
    String ADD_WORK_CHECK = BuildConfig.API_HOST + "/yaf_oa/workInspect/insert";

    /**
     * 获取检查列表
     */
    String GET_WORK_CHECK_LIST = BuildConfig.API_HOST + "/yaf_oa/workInspect/list";
    /**
     * 获取检查详情
     */
    String GET_WORK_CHECK_INFO = BuildConfig.API_HOST + "/yaf_oa/workInspect/detail";
    /**
     * 新增工作检查处理信息
     */
    String ADD_WORK_CHECK_DETAIL = BuildConfig.API_HOST + "/yaf_oa/workInspect/insert/dispose";

    /**
     * 工作检查审核
     */
    String GET_WORK_CHCEK_ADUIT = BuildConfig.API_HOST + "/yaf_oa/workInspect/audit";

    /**
     * 推送
     */
    String GET_PUSH_MSG_LIST = BuildConfig.API_HOST + "/yaf_sys/notice/list";

    /**
     * 推送已读Or未读
     */
    String GET_PUSH_READ_OR_UNREAD = BuildConfig.API_HOST + "/yaf_sys/notice/read/";

    /**
     * 新增报装
     */
    String ADD_WORK_INSTALL = BuildConfig.API_HOST + "/yaf_install_order/install/insert";

    /**
     * 报装列表
     */
    String GET_WORK_INSTALL_LIST = BuildConfig.API_HOST + "/yaf_install_order/install/list";

    /**
     * 报装详情
     */
    String GET_WORK_INSTALL_INFO = BuildConfig.API_HOST + "/yaf_install_order/install/detail";

    /**
     * 新增维保
     */
    String POST_ADD_MAINTAIN = BuildConfig.API_HOST + "/yaf_maintain/maintain/insert";
    /**
     * 维保列表
     */
    String QUERY_HISTORY_RECORD_LIST = BuildConfig.API_HOST + "/yaf_maintain/maintain/list";
    /**
     * 维保详情
     */
    String MAINTENANCE_HISTORY_DETAIL = BuildConfig.API_HOST + "/yaf_maintain/maintain/detail";

    /**
     * 报价
     */
    String QUOTE_ORDER_INSERT = BuildConfig.API_HOST + "/yaf_quote_order/quote/insert";
    String QUOTE_ORDER_LIST = BuildConfig.API_HOST + "/yaf_quote_order/quote/list";
    String QUOTE_ORDER_UPDATE = BuildConfig.API_HOST + "/yaf_quote_order/quote/update";
    String QUOTE_ORDER_DETAIL = BuildConfig.API_HOST + "/yaf_quote_order/quote/detail";


    /**
     * 支付宝 报修接口
     */
    String ALIPAY_REPAIR = BuildConfig.API_HOST + YAF_SYS + "/aliPay/repair";
    /**
     * 支付宝 报价接口
     */
    String ALIPAY_QUOTE = BuildConfig.API_HOST + YAF_SYS + "/aliPay/quote";
    /**
     * 支付宝 发包接口
     */
    String ALIPAY_PUBLISH = BuildConfig.API_HOST + YAF_SYS + "/aliPay/publish";

    /**
     * 微信 报修接口
     */
    String WEI_XIN_REPAIR = BuildConfig.API_HOST + YAF_SYS + "/weixin/repair";
    /**
     * 微信 报价接口
     */
    String WEI_XIN_QUOTE = BuildConfig.API_HOST + YAF_SYS + "/weixin/quote";
    /**
     * 微信 发包接口
     */
    String WEI_XIN_PUBLISH = BuildConfig.API_HOST + YAF_SYS + "/weixin/publish";

    /**
     * 发包相关
     */
    String TASK_PUBLISH_ADD = BuildConfig.API_HOST + "/yaf_task_publish/taskPublish/insert";
    String TASK_PUBLISH_LIST = BuildConfig.API_HOST + "/yaf_task_publish/taskPublish/list";
    String ACCEPT_TASK_PUBLISH_LIST = BuildConfig.API_HOST + "/yaf_task_publish/taskPublish/publishList";
    String TASK_APPLY_PUBLISH_LIST = BuildConfig.API_HOST + "/yaf_task_publish/taskApply/publishList";
    String TASK_PUBLISH_DETAIL = BuildConfig.API_HOST + "/yaf_task_publish/taskPublish/detail";
    String TASK_PUBLISH_CLOSE = BuildConfig.API_HOST + "/yaf_task_publish/taskPublish/close";
    String TASK_PUBLISH_FINISH = BuildConfig.API_HOST + "/yaf_task_publish/taskPublish/finish";
    String TASK_APPLY_LIST = BuildConfig.API_HOST + "/yaf_task_publish/taskApply/list";
    String TASK_APPLY_LIST_DETAIL = BuildConfig.API_HOST + "/yaf_task_publish/taskApply/detail";
    String TASK_APPLY_LIST_UPDATE = BuildConfig.API_HOST + "/yaf_task_publish/taskApply/update";
    String TAKE_APPLY_LIST_ADD = BuildConfig.API_HOST + "/yaf_task_publish/taskApply/insert";
    String TAKE_APPLY_LIST_CHECK = BuildConfig.API_HOST + "/yaf_task_publish/taskApply/check";

}

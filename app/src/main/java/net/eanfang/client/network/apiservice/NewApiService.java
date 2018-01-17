package net.eanfang.client.network.apiservice;

import net.eanfang.client.BuildConfig;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:45
 * @email houzhongzhou@yeah.net
 * @desc
 */

public interface NewApiService {
    /**
     * 获取基础数据
     */
    String GET_BASE_DATA = BuildConfig.API_HOST + "/yaf_sys/basedata/listall";

    /**
     * 获取静态常量
     */
    String GET_CONST = BuildConfig.API_HOST + "/yaf_sys/const/list";

    /**
     * 获取公司列表
     */

    String GET_COMPANY_ALL_LIST = BuildConfig.API_HOST + "/yaf_sys/org/listcompanyall";
    /**
     * 切换公司
     */

    String SWITCH_COMPANY_ALL_LIST = BuildConfig.API_HOST + "/yaf_sys/sys/change";

    /**
     * 任务
     */
    String ADD_WORK_TASK = BuildConfig.API_HOST + "/yaf_oa/workTask/insert";
    String GET_COLLEAGUE = BuildConfig.API_HOST + "/yaf_sys/user/olleague";
    String GET_WORK_TASK_LIST = BuildConfig.API_HOST + "/yaf_oa/workTask/list";
    String GET_WORK_TASK_INFO = BuildConfig.API_HOST + "/yaf_oa/workTask/detail";
    String WORK_TASK_FIRST_READ = BuildConfig.API_HOST + "/yaf_oa/workTask/read";

    /**
     * 汇报
     */
    String ADD_WORK_REPORT = BuildConfig.API_HOST + "/yaf_oa/workReport/insert";
    String GET_WORK_REPORT_LIST = BuildConfig.API_HOST + "/yaf_oa/workReport/list";
    String GET_WORK_REPORT_INFO = BuildConfig.API_HOST + "/yaf_oa/workReport/detail";
    String WORK_REPORT_FIRST_READ = BuildConfig.API_HOST + "/yaf_oa/workReport/read";

    /**
     * 设计
     */

    String ADD_WORK_DESIGN = BuildConfig.API_HOST + "/yaf_oa/designOrder/insert";
    String GET_WORK_DESIGN_LIST = BuildConfig.API_HOST + "/yaf_oa/designOrder/list";
    String GET_WORK_DESIGN_INFO = BuildConfig.API_HOST + "/yaf_oa/designOrder/detail";

    /**
     * 工作检查
     */
    String ADD_WORK_CHECK = BuildConfig.API_HOST + "/yaf_oa/workInspect/insert";
    String GET_WORK_CHECK_LIST = BuildConfig.API_HOST + "/yaf_oa/workInspect/list";
    String GET_WORK_CHECK_INFO = BuildConfig.API_HOST + "/yaf_oa/workInspect/detail";
    String ADD_WORK_CHECK_DETAIL = BuildConfig.API_HOST + "/yaf_oa/workInspect/insert/dispose";
    String GET_WORK_CHCEK_ADUIT = BuildConfig.API_HOST + "/yaf_oa/workInspect/audit";

    /**
     * 推送
     */
    String GET_PUSH_MSG_LIST = BuildConfig.API_HOST + "/yaf_oa/pushMessage/list";
    String GET_PUSH_READ_OR_UNREAD = BuildConfig.API_HOST + "/yaf_oa/pushMessage/detail";

    /**
     * 报装相关
     */
    String ADD_WORK_INSTALL = BuildConfig.API_HOST + "/yaf_install_order/install/insert";
    String GET_WORK_INSTALL_LIST = BuildConfig.API_HOST + "/yaf_install_order/install/list";
    String GET_WORK_INSTALL_INFO = BuildConfig.API_HOST + "/yaf_install_order/install/detail";

    /**
     * 报价
     */
    String QUOTE_ORDER_INSERT = BuildConfig.API_HOST + "/yaf_quote_order/quote/insert";
    String QUOTE_ORDER_LIST = BuildConfig.API_HOST + "/yaf_quote_order/quote/list";
    String QUOTE_ORDER_UPDATE = BuildConfig.API_HOST + "/yaf_quote_order/quote/update";
    String QUOTE_ORDER_DETAIL = BuildConfig.API_HOST + "/yaf_quote_order/quote/detail";

}

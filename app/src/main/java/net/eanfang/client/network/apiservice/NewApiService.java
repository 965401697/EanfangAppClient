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
    String GET_PUSH_MSG_LIST = BuildConfig.API_HOST + "/yaf_oa/pushMessage/list";

    /**
     * 推送已读Or未读
     */
    String GET_PUSH_READ_OR_UNREAD = BuildConfig.API_HOST + "/yaf_oa/pushMessage/detail";

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
}

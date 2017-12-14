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
    String ADD_WORK_TASK = BuildConfig.API_HOST + "/yaf_oa/worktask/insert";

    /**
     * 获取协同人员
     */
    String GET_COLLEAGUE = BuildConfig.API_HOST + "/yaf_sys/user/olleague";

    /**
     * 获取任务列表
     */
    String GET_WORK_TASK_LIST = BuildConfig.API_HOST + "/yaf_oa/worktask/list";

    /**
     * 获取任务详情
     */

    String GET_WORK_TASK_INFO = BuildConfig.API_HOST + "/yaf_oa/worktask/detail";
    /**
     * 工作任务首次阅读
     */
    String WORK_TASK_FIRST_READ = BuildConfig.API_HOST + "/yaf_oa/worktask/read";

    /**
     * 新增汇报
     */
    String ADD_WORK_REPORT = BuildConfig.API_HOST + "/yaf_oa/workreport/insert";
}

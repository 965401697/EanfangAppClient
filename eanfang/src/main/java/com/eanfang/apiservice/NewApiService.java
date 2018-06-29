package com.eanfang.apiservice;

import com.eanfang.BuildConfig;

import static com.eanfang.apiservice.BaseService.BASE_URL;

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
     * 基础数据 树
     */
    String GET_BASE_DATA_CACHE_TREE = BuildConfig.API_HOST + "/yaf_sys/basedata/treecache/";
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
    String GET_PUSH_MSG_INFO = BuildConfig.API_HOST + "/yaf_sys/notice/info/";
    //推送消息  全部阅读
    String GET_PUSH_READ_ALL = BuildConfig.API_HOST + "/yaf_sys/notice/readall/";
    /**
     * 获取推送消息数量
     */
    String GET_PUSH_COUNT = BuildConfig.API_HOST + "/yaf_sys/notice/count/sysBiz";

    /**
     * app首页 获取最新通知
     */
    String GET_PUSH_NEWS = BuildConfig.API_HOST + "/yaf_sys/notice/news";


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
    String WEI_XIN_REPAIR = BuildConfig.API_HOST + YAF_SYS + "/wxPay/repair";
    /**
     * 微信 报价接口
     */
    String WEI_XIN_QUOTE = BuildConfig.API_HOST + YAF_SYS + "/wxPay/quote";
    /**
     * 微信 发包接口
     */
    String WEI_XIN_PUBLISH = BuildConfig.API_HOST + YAF_SYS + "/wxPay/publish";

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
    /**
     * 报装完工
     */
    String INSTALL_FINISH_WORK = BuildConfig.API_HOST + "/yaf_install_order/install/updateFinish";

    /**
     * 扫描二维码 获取权限
     */
    String QR_CODE = BuildConfig.API_HOST + "/yaf_sys/scan";

    /**
     * 确认登录
     */
    String QR_LOGIN = BuildConfig.API_HOST + "/yaf_sys/toAccredit";

    /**
     * 取消登录
     */
    String QR_LOGIN_CANCEL = BuildConfig.API_HOST + "/yaf_sys/toCancel";

    // 是否是技师 是 则返回个人信息
    String QR_GETACCOUNT = BuildConfig.API_HOST + "/yaf_sys/code/getAccount";
    /**
     * 开店日志提交数据
     */
    String OA_SUB_OPEN_SHOP = BuildConfig.API_HOST + "/yaf_oa/oaopenshoplog/insert";
    /**
     * 开店日志列表
     */
    String OA_OPEN_SHOP_LIST = BuildConfig.API_HOST + "/yaf_oa/oaopenshoplog/list";
    /**
     * 开店日志详情
     */
    String OA_OPEN_SHOP_DETAIL = BuildConfig.API_HOST + "/yaf_oa/oaopenshoplog/info";
    /**
     * 布防日志列表
     */
    String OA_DEFEND_LOG_LIST = BuildConfig.API_HOST + "/yaf_oa/protectionlog/list";
    /**
     * 提交布防日志
     */
    String OA_SUB_DEFEND_LOG = BuildConfig.API_HOST + "/yaf_oa/protectionlog/insert";
    /**
     * 布防日志详情
     */
    String OA_DEFEND_LOG_DETAIL = BuildConfig.API_HOST + "/yaf_oa/protectionlog/detail";
    /**
     * 布防日志状态更新
     */
    String OA__DEFEND_LOG_UPDATE = BuildConfig.API_HOST + "/yaf_oa/protectionlog/update";


    /**
     * 报装单确认
     * post
     */
    String INSTALL_ORDER_CONFIRM = BASE_URL + "/installorderconfirm";
    /**
     * 收藏公司
     * post
     */

    String COLLECTION_COMPANY_WORK = BASE_URL + "/collectinstallcompany";
    /**
     * 公司详情
     * get
     */

    String COMPANY_DETAIL = BASE_URL + "/getworkcompanydetail";


    /**
     * 查看发票
     * get
     */
    String LOOK_FA_PIAO = BASE_URL + "/getinvoicebyordernum";

    /**
     * 发票
     * post
     */
    String FA_PIAO = BASE_URL + "/addinvoice";
    /**
     * 设置管理员
     * post
     */
    String SET_TRANS_ADMIN = BASE_URL + "/yaf_sys/account/transadmin";
    /**
     * 创建部门
     * post
     */
    String CREAT_SECTION = BASE_URL + "/yaf_sys/org/insertdepartment";
    /**
     * 当前登录人可以访问的角色
     * post
     */
    String MY_LIST_ROLE = BASE_URL + "/yaf_sys/role/listmyrole";
    /**
     * 当前搜索人角色
     * post
     */
    String MY_CURREMT_LIST_ROLE = BASE_URL + "/yaf_sys/role/listbyuser/";
    /**
     * 添加员工
     * post
     */
    String ADD_STAFF = BASE_URL + "/yaf_sys/staff/insert";
    /**
     * 添加角色
     * post
     */
    String ADD_STAFF_ROLE = BASE_URL + "/yaf_sys/staff/grant";
    /**
     * 获的合作业务的list
     * post
     */
    String GET_COOPERATION_LIST = BASE_URL + "/yaf_shop/cooperation/list";
    /**
     * 查看合作业务的详情
     * post
     */
    String GET_COOPERATION_DETAIL = BASE_URL + "/yaf_shop/cooperation/detail";
    /**
     * 客户审核合作业务
     * post
     */
    String COOPERATION_AUDIT = BASE_URL + "/yaf_shop/cooperation/audit";
    /**
     * 搜索客户公司
     * post
     */
    String COOPERATION_WORKCOMPANY_SEARCH = BASE_URL + "/yaf_shop/workCompany/search";
    /**
     * 搜索客户公司
     * post
     */
    String COOPERATION_WORKCOMPANY_SUB = BASE_URL + "/yaf_shop/cooperation/create";
    /**
     * 故障记录列表
     * post
     */
    String FAULT_RECORD_LIST = BASE_URL + "/yaf_repair/failure/record/list";
    /**
     * 故障记录统计
     * post
     */
    String FAULT_RECORD_TOTAL = BASE_URL + "/yaf_repair/failure/record/total";
    /**
     * 客户端设备列表
     * post
     */
    String DEVICE_LIST_CLIENT = BASE_URL + "/yaf_device/custDevice/list";
    /**
     * 技师端设备列表
     * post
     */
    String DEVICE_LIST_WORKER = BASE_URL + "/yaf_device/shopDevice/list";
    /**
     * 添加设备库列表
     * post
     */
    String DEVICE_LIST_ADD = BASE_URL + "/yaf_device/custDevice/choose";
    /**
     * 设备詳情
     * post
     */
    String DEVICE_DETAIL = BASE_URL + "/yaf_device/custDevice/detail";
    /**
     * 技师端设备詳情
     * post
     */
    String DEVICE_DETAIL_WORKER = BASE_URL + "/yaf_device/shopDevice/detail";
    /**
     * 变更记录列表
     * post
     */
    String DEVICE_CHANGE_LIST = BASE_URL + "/yaf_device/custdevicechangelog/list";
    /**
     * 变更记录详情
     * post
     */
    String DEVICE_CHANGE_DETAIL = BASE_URL + "/yaf_device/custdevicechangelog/info/";

}

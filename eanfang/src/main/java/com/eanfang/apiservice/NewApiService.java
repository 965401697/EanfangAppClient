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
     * 下载技师端url
     */
    String DOWNLOAD_WORKER = BuildConfig.API_HOST + YAF_SYS + "/update/worker/download";

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

    String GET_COMPANY_ALL_LIST = BuildConfig.API_HOST + "/yaf_sys/org/listCompanyByAccId";
    /**
     * 切换公司
     */

    String SWITCH_COMPANY_ALL_LIST = BuildConfig.API_HOST + "/yaf_sys/sys/change";
    /**
     * 解散公司
     */
    String DISSLOVE_COMPANY = BuildConfig.API_HOST + "/yaf_sys/orgunit/deleteTeam";

    /**
     * 新增任务
     */
    String
            ADD_WORK_TASK = BuildConfig.API_HOST + "/yaf_oa/workTask/insert";

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
    String GET_UNREAD_MSG = BuildConfig.API_HOST + "/yaf_sys/notice/maintain";

    /**
     * 获取官方消息通知
     */
    String GET_OFFICIAL_MSG = BuildConfig.API_HOST + "/yaf_sys/noticepush/appList";
    /**
     * 获取官方消息状态接口
     */
    String GET_OFFICIAL_CHANGE_STATUS = BuildConfig.API_HOST + "/yaf_sys/noticepush/updateStatus";


    /**
     * 推送已读Or未读
     */
    String GET_PUSH_MSG_INFO = BuildConfig.API_HOST + "/yaf_sys/notice/info/";
    //推送消息  全部阅读
    String GET_PUSH_READ_ALL = BuildConfig.API_HOST + "/yaf_sys/notice/readall/";
    //推送消息  全部删除
    String GET_PUSH_DELETE_ALL = BuildConfig.API_HOST + "/yaf_sys/notice/delete/";

    /**
     * app首页 获取最新通知
     */
//    String GET_PUSH_NEWS = BuildConfig.API_HOST + "/yaf_sys/notice/news";

    String GET_PUSH_NEWS_CLIENT = BuildConfig.API_HOST + "/yaf_sys/notice/newsClient";

    String GET_PUSH_NEWS_WORKER = BuildConfig.API_HOST + "/yaf_sys/notice/newsWorker";


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
     * 我的信息
     */
    String My_Info_LIST = BuildConfig.API_HOST + "/yaf_sys/expertsOnline/myInfo";
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
    String FA_PIAO = BASE_URL + "/yaf_sys/invoice/create";
    /**
     * /**
     * 发票
     * post
     */
    String INVOICE_UPDATE = BASE_URL + "/yaf_sys/invoice/update";
    /**
     * 根据orderId查发票
     * post
     */
    String GET_INVOICE_INFO = BASE_URL + "/yaf_sys/invoice/info";

    /**
     * 地址列表
     * post
     */
    String GET_ADDRESS_LIST = BASE_URL + "/yaf_sys/receive/list";
    /**
     * 创建地址
     * post
     */
    String CREATE_ADDRESS = BASE_URL + "/yaf_sys/receive/create";
    /**
     * 删除地址
     * post
     */
    String DELETE_ADDRESS = BASE_URL + "/yaf_sys/receive/delete";
    /**
     * 修改地址
     * post
     */
    String UPDATE_ADDRESS = BASE_URL + "/yaf_sys/receive/update";
    /**
     * 设置默认地址
     * post
     */
    String SET_DEFAULT_ADDRESS = BASE_URL + "/yaf_sys/receive/setDefault";
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
     * 筛选合作业务的公司专用接口（根据assginee或owner信息筛选出合作业务的公司信息）
     */
    String GET_SELECT_COOPERATION_LIST = BASE_URL + "/yaf_shop/cooperation/selectCoopCompany";
    /**
     * 查看合作业务的详情
     * post
     */
    String GET_COOPERATION_DETAIL = BASE_URL + "/yaf_shop/cooperation/detail";
    /**
     * 客户解绑合作业务
     * post
     */
    String COOPERATION_DELETE = BASE_URL + "/yaf_shop/cooperation/delete";

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
     * 模糊查询故障记录列表
     * post
     */
    String FAULT_RECORD_LIST = BASE_URL + "/yaf_repair/failure/record/list ";
    /**
     * 故障记录列表
     * post
     */
    String COMMENT_FAULT_RECORD_LIST = BASE_URL + "/yaf_sys/expertsOnline/questionsWithExpert";
    /**
     * 故障记录列表首页e
     * post
     */
    String CommonQuestions = BASE_URL + "/yaf_sys/expertsOnline/commonQuestions";
    /**
     * 评论列表
     * post
     */
    String Reply_List = BASE_URL + "/yaf_sys/expertsOnline/replyList";

    /**
     * 添加评论
     * post
     */
    String Reply_Add = BASE_URL + "/yaf_sys/expertsOnline/replyAdd";
    /**
     * 故障解答
     * post
     */
    String ANSWER_List_With_Question = BASE_URL + "/yaf_sys/expertsOnline/answerListWithQuestion";
    /**
     * 赞
     * post
     */
    String ANSWER_Change_Like_Status = BASE_URL + "/yaf_sys/expertsOnline/changeLikeStatus";
    /**
     * 我的回答
     * post
     */
    String Answer_Add = BASE_URL + "/yaf_sys/expertsOnline/answerAdd";

    /**
     * 故障解答
     * post
     */
    String ANSWER_Expert_More_Details = BASE_URL + "/yaf_sys/expertsOnline/expertMoreDetails";
    /**
     * 按某一类问题筛选免费提问列表
     * post
     */
    String COMMENT_FAULT_RECORD_TYPE = BASE_URL + "/yaf_sys/expertsOnline/similarQuestions";
    /**
     * 故障记录统计
     * post
     */
    String FAULT_RECORD_TOTAL = BASE_URL + "/yaf_repair/failure/record/total";
    /**
     * 客户端设备列表  技师端设备列表 用一个
     * post
     */
    String DEVICE_LIST_CLIENT = BASE_URL + "/yaf_device/custDevice/list";
    /**
     * 添加设备库列表
     * post
     */
    String DEVICE_LIST_ADD = BASE_URL + "/yaf_device/custDevice/choose";
    /**
     * 设备詳情  客户端 和技师端 共用
     * post
     */
    String DEVICE_DETAIL = BASE_URL + "/yaf_device/custDevice/detail";

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
    /**
     * 技师认证编辑
     */
    String WORKER_AUTH_REVOKE = BASE_URL + "/yaf_sys/techworkerverify/rollBack/";
    /**
     * 专家认证撤回
     */
    String EXPERT_AUTH_REVOKE = BASE_URL + "/yaf_sys/expertscertification/rollBack/";
    /**
     * 企业管理
     */
    String BUSINESS_MANAGEMENT = BASE_URL + "/yaf_sys/orgunit/shop/management/";
//    String BUSINESS_MANAGEMENT = "http://192.168.0.110:8080/yaf_sys/orgunit/shop/management/";
    /**
     * 安防公司编辑
     */
    String COMPANY_SECURITY_AUTH_REVOKE = BASE_URL + "/yaf_sys/orgunit/shop/rollback/";
    /**
     * 企业主页
     */
    String COMPANY_SECURITY_HOMEPAGE = BASE_URL + "/yaf_sys/orgunit/shop/homepage";

    /**
     * 企业认证编辑
     */
    String COMPANY_ENTERPRISE_AUTH_REVOKE = BASE_URL + "/yaf_sys/orgunit/ent/rollback/";

    /**
     * 报修统计接口
     */
    String REPAIR_DATA_STATISTICE = BASE_URL + "/yaf_statistics/failureStatistics/app/allCount";
    /**
     * 报装统计接口
     */
    String INSTALL_DATA_STATISTICE = BASE_URL + "/yaf_statistics/repairStatistics/app/installAllCount";
    /**
     * 免费设计统计接口
     */
    String DESIGN_DATA_STATISTICE = BASE_URL + "/yaf_statistics/repairStatistics/app/designAllCount";
    /**
     * 维保管控列表接口
     */
    String MAINTENANCE_GET_LIST = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/list";
    /**
     * 维保管控详情接口
     */
    String MAINTENANCE_GET_DETAIL = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/detail";
    /**
     * 维保管控预约接口接口
     */
    String MAINTENANCE_APPOINT_TIME = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/update";
    /**
     * 维保管控改约接口
     */
    String MAINTENANCE_UPDATE_APPOINT_TIME = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/Appointment";
    /**
     * 维保管控预约签到
     */
    String MAINTENANCE_SINGIN = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/signIn";
    /**
     * 客户端确认维保完工
     */
    String MAINTENANCE_CLIENT_CONFIRM = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/confirm";
    /**
     * 维保处理提交
     */
    String MAINTENANCE_DISPOSE = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/dispose";
    /**
     * 维保详情的状态
     */
    String MAINTENANCE_DETAIL_STATUS = BASE_URL + "/yaf_maintain/shopmaintenanceorder/flow";
    /**
     * 待验收的查看处理信息 传ID
     */
    String MAINTENANCE_DETAIL_DISPOSE = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/detailDispose";

    /**
     * 报修统计获取公司
     */
    String REPAIR_DATA_COMPANGY = BASE_URL + "/yaf_sys/org/company/listsubcompany";

    /**
     * 面谈员工列表
     */
    String WORK_TALK = BASE_URL + "/yaf_oa/facetoworker/list";
    /**
     * 面谈员工添加
     */
    String WORK_TALK_ADD = BASE_URL + "/yaf_oa/facetoworker/insert";
    /**
     * 面谈员工详情
     */
    String WORK_TALK_DETAIL = BASE_URL + "/yaf_oa/facetoworker/info/";
    /**
     * 交接班创建
     */
    String WORK_TRANSFER_ADD = BASE_URL + "/yaf_oa/exchangelog/insert";
    /**
     * 交接班列表
     */
    String WORK_TRANSFER_LIST = BASE_URL + "/yaf_oa/exchangelog/list";
    /**
     * 交接班详情
     */
    String WORK_TRANSFER_DETAIL = BASE_URL + "/yaf_oa/exchangelog/info/";
    /**
     * 交接班确认交接
     */
    String WORK_TRANSFER_CONFIM = BASE_URL + "/yaf_oa/exchangelog/update/";
    /**
     * 首页统计数据
     */
    String HOME_DATASTASTISTICS = BASE_URL + "/yaf_statistics/repairOrderStatistics/app/Count";
    /**
     * 报修的选择的项目列表
     */
    String REPAIR_PROJECT_LIST = BASE_URL + "/yaf_project/project/projectList";

    /**
     * 报修支付优惠码
     */
    String REPAIR_PAY_COUPON = BASE_URL + "/yaf_sys/couponPay/repair";

    /**
     * 技师端扫一扫客户设备库，查看当前设备是否是待维保的设备，并返回维保信息和维保明细信息。
     */
    String SCAN_DEVICE_DEAL = BASE_URL + "/yaf_maintain/shopmaintenanceorder/app/maintenanceDetailByDeviceId";

    /**
     * 维保完工查看重点设备
     */
    String MAINTENANCE_DEVICE_DEAL = BASE_URL + "/yaf_maintain/shopmaintenanceorder/prepare";

    /**
     * 招标信息列表
     */
    String TENDER_LIST = BASE_URL + "/yaf_ifb/ifborder/list";
    /**
     * 招标信息详情
     */
    String TENDER_DETAIL = BASE_URL + "/yaf_ifb/ifborder/info";

    /**
     * 安防圈 首页三条
     */
    String SECURITY_RECOMMEND = BASE_URL + "/yaf_spc/spcircle/weightList";
    /**
     * 安防圈 热门列表
     */
    String SECURITY_HOT = BASE_URL + "/yaf_spc/spcircle/list";
    /**
     * 安防圈 关注列表
     */
    String SERCURITY_FOUCS = BASE_URL + "/yaf_spc/spcircle/followersList";
    /**
     * 安防圈 创建
     */
    String SERCURITY_CREATE = BASE_URL + "/yaf_spc/spcircle/create";
    /**
     * 安防圈 关注 取消关注
     */
    String SERCURITY_FOUCUS = BASE_URL + "/yaf_spc/spcircle/followers";
    /**
     * 安防圈 点赞
     */
    String SERCURITY_LIKE = BASE_URL + "/yaf_spc/spcircle/likes";
    /**
     * 安防圈详情
     */
    String SERCURITY_DETAIL = BASE_URL + "/yaf_spc/spcircle/detail";
    /**
     * 安防圈评论
     */
    String SERCURITY_COMMENT = BASE_URL + "/yaf_spc/spcircle/comment";
    /**
     * 安防圈评论详情
     */
    String SERCURITY_COMMENT_DETAIL = BASE_URL + "/yaf_spc/spcircle/commentDetail";
    /**
     * 安防圈删除评论
     */
    String SERCURITY_COMMENT_DELETE = BASE_URL + "/yaf_spc/spcircle/deleteComment";

    /**
     * 安防圈个人中心
     */
    String SERCURITY_PERSONAL = BASE_URL + "/yaf_spc/spcircle/centerList";
    String SERCURITY_PERSONAL_TOP = BASE_URL + "/yaf_spc/spcircle/centerManList";

    String SERCURITY_PERSONAL_OTHER = BASE_URL + "/yaf_spc/spcircle/avatarCenterList";
    String SERCURITY_PERSONAL_OTHER_TOP = BASE_URL + "/yaf_spc/spcircle/avatarCenterManList";

    /**
     * 安防圈评论列表
     */
    String SERCURITY_COMMENT_LIST = BASE_URL + "/yaf_spc/spcircle/commentList";
    /**
     * 安防圈点赞列表
     */
    String SERCURITY_LIKE_LIST = BASE_URL + "/yaf_spc/spcircle/likeList";
    /**
     * 安防圈艾特列表
     */
    String SERCURITY_ABOUT_LIST = BASE_URL + "/yaf_spc/spcircle/atList";
    /**
     * 安防圈我关注的人
     */
    String SERCURITY_FOUCS_LIST = BASE_URL + "/yaf_spc/spcircle/followerManList";
    /**
     * 安防圈删除
     */
    String SERCURITY_DELETE = BASE_URL + "/yaf_spc/spcircle/contentDelete";

    /**
     * 报修获取用户信息列表
     */
    String REPAIR_PERSONAL_INFO_LIST = BASE_URL + "/yaf_sys/receive/list";
    /**
     * 刪除用户信息
     */
    String REPAIR_PERSONAL_INFO_DELETE = BASE_URL + "/yaf_sys/receive/delete";
    /**
     * 设置默认用户信息
     */
    String REPAIR_PERSONAL_INFO_DEFAULT = BASE_URL + "/yaf_sys/receive/setDefault";

    /**
     * 报修增加个人信息
     */
    String REPAIR_PRESONAL_INFO_ADD = BASE_URL + "/yaf_sys/receive/create";
    /**
     * 报修增加个人信息修改
     */
    String REPAIR_PRESONAL_INFO_UPDATAE = BASE_URL + "/yaf_sys/receive/update";
    /**
     * 通讯录好友表
     */
    String ACCOUNT_POST = BASE_URL + YAF_SYS + "/accountmail/insertAccountMail";
}

package com.eanfang.apiservice;

import com.eanfang.BuildConfig;

import static com.eanfang.apiservice.BaseService.BASE_URL;

/**
 * Created by MrHou
 *
 * @on 2017/11/6  16:43
 * @email houzhongzhou@yeah.net
 * @desc 用户
 */

public interface UserApi {

    /**
     * 登录
     */
    String APP_LOGIN = BuildConfig.API_HOST + "/yaf_sys/sys/login";
    /**
     * 注册
     */
    String APP_REGISTER = BuildConfig.API_HOST + "/yaf_sys/account/register/";
    /**
     * 验证码登录
     */
    String APP_LOGIN_VERIFY = BuildConfig.API_HOST + "/yaf_sys/sys/login_verify";
    /**
     * 退出
     */
    String APP_LOGOUT = BuildConfig.API_HOST + "/yaf_sys/sys/logout";

    /**
     * 客户端收到的评价
     */
    String GET_CILENT_EVALUATE_LIST = BuildConfig.API_HOST + "/yaf_ent/clientEvaluate/list";

    /**
     * 技师端收到的评价
     */
    String GET_WORKER_EVALUATE_LIST = BuildConfig.API_HOST + "/yaf_shop/workerEvaluate/list";

    /**
     * 获取验证码
     */
    String GET_VERIFY_CODE = BuildConfig.API_HOST + "/yaf_sys/account/sendverify";

    /**
     * 个人资料修改
     * get
     */
    String USER_INFO_UPDATE = BASE_URL + "/yaf_sys/account/profile";

    /**
     * 查看修改后信息
     */
    String GET_USER_INFO = BASE_URL + "/yaf_sys/sys/userinfo";


    /**
     * 合作公司
     */
    String GET_COOPERATION_LIST = BuildConfig.API_HOST + "/yaf_shop/cooperation/list";
    /**
     * 审核合作业务
     */
    String GET_COOPERATION_AUDIT = BuildConfig.API_HOST + "/yaf_shop/cooperation/audit";
    /**
     * 组织结构
     */
    String GET_STAFFINCOMPANY_LISTTREE = BuildConfig.API_HOST + "/yaf_sys/org/staffincompany/listtree";
    /**
     * 分公司
     */
    String GET_BRANCH_OFFICE_LIST = BuildConfig.API_HOST + "/yaf_sys/org/company/listtopall";
    /**
     * 获取 我管理和归属的的所有公司
     */
    String GET_BRANCH_OFFICE_LIST_ALL = BuildConfig.API_HOST + "/yaf_sys/org/company/listtopallmananger";


    /**
     * 获得某个公司下  所有的 部门树  包括员工
     */
    String GET_BRANCH_OFFICE_LIST_TREE_ALL = BuildConfig.API_HOST + "/yaf_sys/org/departmentByCompany/listtreeall";
    /**
     * 外协单位
     */
    String GET_STAFFTEMP_OUTER_LIST = BuildConfig.API_HOST + "/yaf_sys/stafftemp/load_outer_assist";
    /**
     * 外部联系人
     */
    String GET_STAFFTEMP_LIST = BuildConfig.API_HOST + "/yaf_sys/stafftemp/list";

    /**
     * 搜索公司
     */
    String GET_WORKCOMPANY_LIST = BuildConfig.API_HOST + "/yaf_shop/workCompany/search";
    /**
     * 绑定公司
     */
    String GET_COOPERATION_CREATE = BuildConfig.API_HOST + "/yaf_shop/cooperation/create";
    /**
     * 签到
     */
    String GET_SIGN_ADD = BuildConfig.API_HOST + "/yaf_oa/sign/insert";
    /**
     * 签到次数，签退次数
     */
    String GET_SIGN_COUNT = BuildConfig.API_HOST + "/yaf_oa/sign/querySignCount";
    String GET_SIGN_LIST = BuildConfig.API_HOST + "/yaf_oa/sign/userList";
    String GET_SIGN_DETAIL = BuildConfig.API_HOST + "/yaf_oa/sign/detail";

    /**
     * 新增安防公司
     */
    String GET_ORGUNIT_SHOP_ADD = BuildConfig.API_HOST + "/yaf_sys/orgunit/shop/add";
    /**
     * 填写企业资料 技师端
     */
    String GET_ORGUNIT_SHOP_INSERT = BuildConfig.API_HOST + "/yaf_sys/orgunit/shop/insert";

    /**
     * 技师端安防公司完善资质认证
     */
    String GET_WORKER_COMPANY_QUALIFY = BuildConfig.API_HOST + "/yaf_sys/orgunit/shop/insertV2";
    /**
     * 填写系统类别
     */

    String GET_ORGUNIT_SHOP_ADD_SYS = BuildConfig.API_HOST + "/yaf_sys/company2basedata/companysys/";
    /**
     * 业务类型
     */
    String GET_ORGUNIT_SHOP_ADD_BIZ = BuildConfig.API_HOST + "/yaf_sys/company2basedata/companybiz/";
    /**
     * 服务区域
     */
    String GET_ORGUNIT_SHOP_ADD_AREA = BuildConfig.API_HOST + "/yaf_sys/company2basedata/companyarea/";

    /**
     * 新增企业
     */
    String GET_ORGUNIT_ENT_ADD = BuildConfig.API_HOST + "/yaf_sys/orgunit/ent/add";

    /**
     * 保存企业资料 客户端
     */
    String GET_ORGUNIT_ENT_INSERT = BuildConfig.API_HOST + "/yaf_sys/orgunit/ent/insert";

    /**
     * 企业安防公司认证
     */
    String GET_ORGUNIT_SEND_VERIFY = BuildConfig.API_HOST + "/yaf_sys/orgunit/sendverify/";

    /**
     * 获取技师信息
     */
    String GET_WORKER_INFO = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/loadprofile";
    /**
     * 填写技师信息
     */
    String GET_TECH_WORKER_ADD = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/insert";
    /**
     * 填写技师信息 实名认证
     */
    String GET_TECH_WORKER_ADD_V2 = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/insertV2";
    /**
     * 填写技师信息 证书填写
     */
    String GET_TECH_WORKER_ADD_CERTIFICATE = BuildConfig.API_HOST + "/yaf_shop/honorcertificate/insert";
    /**
     * 填写技师信息 证书列表
     */
    String GET_TECH_WORKER_ADD_CERTIFICATE_LIST = BuildConfig.API_HOST + "/yaf_shop/honorcertificate/list";
    /**
     * 填写技师信息 证书列表删除
     */
    String GET_TECH_WORKER_ADD_CERTIFICATE_DELETE = BuildConfig.API_HOST + "/yaf_shop/honorcertificate/delete";

    /**
     * 填写技师信息 证书列表更新
     */
    String GET_TECH_WORKER_ADD_CERTIFICATE_UPDATE = BuildConfig.API_HOST + "/yaf_shop/honorcertificate/update";
    /**
     * 填写技师信息 教育经历添加
     */
    String GET_TECH_WORKER_ADD_EDUCATION = BuildConfig.API_HOST + "/yaf_shop/educationexperience/insert";
    /**
     * 填写技师信息 教育经历删除
     */
    String GET_TECH_WORKER_EDUCATION_DELETE = BuildConfig.API_HOST + "/yaf_shop/educationexperience/delete";

    /**
     * 填写技师信息 教育经历List
     */
    String GET_TECH_WORKER_EDUCATION_LIST = BuildConfig.API_HOST + "/yaf_shop/educationexperience/list";

    /**
     * 填写技师信息 教育经历更新
     */
    String GET_TECH_WORKER_WORK_UPDATE = BuildConfig.API_HOST + "/yaf_shop/jobexperience/update";
    /**
     * 填写技师信息 工作经历添加
     */
    String GET_TECH_WORKER_ADD_WORK = BuildConfig.API_HOST + "/yaf_shop/jobexperience/insert";
    /**
     * 填写技师信息 工作经历删除
     */
    String GET_TECH_WORKER_WORK_DELETE = BuildConfig.API_HOST + "/yaf_shop/jobexperience/delete";

    /**
     * 填写技师信息 工作经历List
     */
    String GET_TECH_WORKER_WORK_LIST = BuildConfig.API_HOST + "/yaf_shop/jobexperience/list";

    /**
     * 填写技师信息 工作经历更新
     */
    String GET_TECH_WORKER_EDUCATION_UPDATE = BuildConfig.API_HOST + "/yaf_shop/educationexperience/update";

    /**
     * 加载系统类别
     */
    String GET_TECH_WORKER_SYS = BuildConfig.API_HOST + "/yaf_sys/basedata2user/list/";
    /**
     * 技师绑定系统类别
     */
    String POST_TECH_WORKER_SYS = BuildConfig.API_HOST + "/yaf_sys/basedata2user/workersys";

    /**
     * 技师绑定业务类型
     */
    String POST_TECH_WORKER_BIZ = BuildConfig.API_HOST + "/yaf_sys/basedata2user/workerbiz";

    /**
     * 技师绑定服务区域
     */
    String POST_TECH_WORKER_AREA = BuildConfig.API_HOST + "/yaf_sys/basedata2user/workerarea";

    /**
     * 提交认证
     */
    String POST_TECH_WORKER_SEND_VERIFY = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/sendverify";

    /**
     * 加载企业信息
     */
    String GET_COMPANY_ORG_INFO = BuildConfig.API_HOST + "/yaf_sys/orgunit/infobyorgid/";
    /**
     * 加载系统类别
     */
    String GET_COMPANY_ORG_SYS_INFO = BuildConfig.API_HOST + "/yaf_sys/company2basedata/list/";
    /**
     * 技师接单状态
     */
    String GET_WORKER_CHANGE = BuildConfig.API_HOST + "/yaf_shop/worker/change";

    /**
     * 根据手机号获取用户信息
     */
    String GET_USER_INFO_BY_PHONE = BuildConfig.API_HOST + "/yaf_sys/account/mobile/";
    /**
     * 技师上报位置
     */
    String POST_WORKER_SUBMIT_LOCATION = BuildConfig.API_HOST + "/yaf_shop/worker/submitLocation";

    /**
     * 客户端APP更新
     */
    String GET_CILENT_UPDATE_APP = BuildConfig.API_HOST + "/yaf_sys/update/updateClient";

    /**
     * 技师端APP更新
     */
    String GET_WORKER_UPDATE_APP = BuildConfig.API_HOST + "/yaf_sys/update/updateWork";

    /**
     * 获取好有列表
     */
    String POST_FRIENDS_LIST = BuildConfig.API_HOST + "/yaf_sys/outer2user/findFriends";


    /**
     * 查看是否是好友
     */
    String POST_CHECK_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/checkFriend";


    /**
     * 根據accid 獲得用戶信息
     */
    String GET_ACC_INFO = BuildConfig.API_HOST + "/yaf_sys/account/getAccInfo";
    /**
     * 根据手机号查用户信息
     */
    String GET_MOBILE_INFO = BuildConfig.API_HOST + "/yaf_sys/account/getAccInfo/mobile";

    /**
     * 获取好有列表
     */
    String POST_RONGY_TOKEN = BuildConfig.API_HOST + "/yaf_im/im/getToken";

    /**
     * 搜索好友
     */
    String POST_FIND_FRIEND = BuildConfig.API_HOST + "/yaf_sys/account/find";
    /**
     * 添加好友
     */
    String POST_ADD_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/insert";
    /**
     * /**
     * 添加好友推送
     */
    String POST_ADD_FRIEND_PUSH = BuildConfig.API_HOST + "/yaf_im/im/friendPush";
    /**
     * 同意添加
     */
    String POST_ACCEPT_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/accept";
    /**
     * 拒绝
     */
    String POST_REFUSE_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/refuse";
    /**
     * 删除好友
     */
    String POST_DELETE_FRIEND = BuildConfig.API_HOST + "/yaf_sys/outer2user/delete";
    /**
     * 删除好友
     */
    String POST_DELETE_FRIEND_PUSH = BuildConfig.API_HOST + "/yaf_im/im/deleteFriendPush";
    /**
     * 创建群组
     */
    String POST_CREAT_GROUP = BuildConfig.API_HOST + "/yaf_im/sysgroup/create";
    /**
     * 获取我的群组
     */
    String POST_GET_GROUP = BuildConfig.API_HOST + "/yaf_im/sysgroup/list";
    /**
     * 群成员列表
     */
    String POST_GROUP_NUM = BuildConfig.API_HOST + "/yaf_im/sysgroupuser/list";
    /**
     * 更新群组信息
     */
    String POST_UPDATA_GROUP = BuildConfig.API_HOST + "/yaf_im/sysgroup/update";
    /**
     * /**
     * 群组详情
     */
    String POST_GROUP_DETAIL_RY = BuildConfig.API_HOST + "/yaf_im/sysgroup/detail/ry";
    /**
     * /**
     * 群组详情
     */
    String POST_GROUP_DETAIL = BuildConfig.API_HOST + "/yaf_im/sysgroup/detail";
    /**
     * 退出群组
     */
    String POST_GROUP_QUIT = BuildConfig.API_HOST + "/yaf_im/sysgroup/quit";
    /**
     * /**
     * 退出群组
     */
    String POST_GROUP_KICKOUT = BuildConfig.API_HOST + "/yaf_im/sysgroup/kickOut";
    /**
     * 销毁群组
     */
    String POST_GROUP_DELETE = BuildConfig.API_HOST + "/yaf_im/sysgroup/delete";

    /**
     * 群成员添加
     */
    String POST_GROUP_JOIN = BuildConfig.API_HOST + "/yaf_im/sysgroupuser/join";
    /**
     * 查询没有在群组的成员
     */
    String POST_GROUP_NOJOIN = BuildConfig.API_HOST + "/yaf_sys/outer2user/findJoin";
    /**
     * 全员禁言
     */
    String POST_GROUP_GAG = BuildConfig.API_HOST + "/yaf_im/sysgroup/gag";
    /**
     * 全员解禁
     */
    String POST_GROUP_NOGAG = BuildConfig.API_HOST + "/yaf_im/sysgroup/unGag";
    /**
     * 根据账户id 查找用户
     */
    String POST_USER_INFO = BuildConfig.API_HOST + "/yaf_sys/account/info/";
    /**
     * 技师认证，资料完善状态查询
     */
    String POST_WORKER_AUTH_STATUS = BuildConfig.API_HOST + "/yaf_sys/techworkerverify/verifyStatus";
    /**
     * 修改手机号
     */
    String UPDATA_MOBILE = BuildConfig.API_HOST + "/yaf_sys/account/changeAppMobile";
    /**
     * 修改密码
     */
    String UPDATA_PASSWORD = BuildConfig.API_HOST + "/yaf_sys/account/changeAppkey";
    /**
     * 安防公司认证相关
     * */
    /**
     * 安防公司添加资质认证第一步详情
     */
    String FIRST_QUALIFY = BuildConfig.API_HOST + "/yaf_sys/orgunit/shop/detail/sp1";

    /**
     * 安防公司添加资质证书
     */
    String ADD_QUALIFY = BuildConfig.API_HOST + "/yaf_shop/aptitudecertificate/insert";
    /**
     * 安防公司修改资质证书
     */
    String UPDATA_QUALIFY = BuildConfig.API_HOST + "/yaf_shop/aptitudecertificate/update";
    /**
     * 安防公司查看资质证书列表
     */
    String LIST_QUALIFY = BuildConfig.API_HOST + "/yaf_shop/aptitudecertificate/list";
    /**
     * 安防公司查看资质证书列表 刪除
     */
    String DELETE_QUALIFY = BuildConfig.API_HOST + "/yaf_shop/aptitudecertificate/delete";

    /**
     * 安防公司荣誉 证书填写
     */
    String COMPANY_ADD_CERTIFICATE = BuildConfig.API_HOST + "/yaf_shop/glorycertificate/insert";
    /**
     * 安防公司荣誉 证书列表
     */
    String COMPANY_CERTIFICATE_LIST = BuildConfig.API_HOST + "/yaf_shop/glorycertificate/list";
    /**
     * 安防公司荣誉 证书列表删除
     */
    String COMPANY_CERTIFICATE_DELETE = BuildConfig.API_HOST + "/yaf_shop/glorycertificate/delete";

    /**
     * 安防公司荣誉 证书列表更新
     */
    String COMPANY_CERTIFICATE_UPDATE = BuildConfig.API_HOST + "/yaf_shop/glorycertificate/update";

    /**
     * 签到签退列表
     */
    String SIGN_LIST = BuildConfig.API_HOST + "/yaf_oa/sign/userListNew";
}
